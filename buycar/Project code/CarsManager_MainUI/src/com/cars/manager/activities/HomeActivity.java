package com.cars.manager.activities;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.utils.ActivityStack;
import com.cars.manager.utils.IntentUtil;
import com.cars.manager.views.fragments.BusinessFragment;
import com.cars.manager.views.fragments.BusinessFragment.OnOperation2ClickListener;
import com.cars.manager.views.fragments.CarResourcesFragment;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;

public class HomeActivity extends BaseTitleActivity implements OnOperation2ClickListener, NetDataCallBack {

	RelativeLayout mLeftTabLayout, mRightTabLayout;

	LinearLayout mTabLayout;

	TextView mLeftTabTv, mRightTabTv;

	Fragment mCurrentFragment;

	BusinessFragment mBusinessFragment;

	CarResourcesFragment mCarResourcesFragment;

	FragmentManager mFragmentManager;

	boolean hasDoRequest = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initUI();

		loadData();

		setSwipeBackEnable(false);
	}

	@Override
	protected void initUI() {
		super.initUI();
		setTitleTv(getString(R.string.title_shouye));

		setRightTv(getString(R.string.title_weishangchuan));

		setLeftSecondTv(getString(R.string.zhuxiao));

		mTabLayout = (LinearLayout) findViewById(R.id.rly_home_act_tab_layout);

		mLeftTabLayout = (RelativeLayout) findViewById(R.id.rl_left_tab);

		mRightTabLayout = (RelativeLayout) findViewById(R.id.rl_right_tab);

		mLeftTabTv = (TextView) findViewById(R.id.tv_left_tab);

		mRightTabTv = (TextView) findViewById(R.id.tv_right_tab);

		mLeftTabLayout.setOnClickListener(this);

		mRightTabLayout.setOnClickListener(this);

	}

	private void loadData() {

		/* 初始化 */
		mFragmentManager = getSupportFragmentManager();

		mBusinessFragment = new BusinessFragment();
		mBusinessFragment.setmOnOperation2ClickListener(this);

		mCarResourcesFragment = new CarResourcesFragment();

		if (UserInfoManager.isBusiness(this)) {
			mTabLayout.setVisibility(View.GONE);
			this.onRightTabClick();// 首次默认车源页
		} else {
			this.onLeftTabClick();// 首次默认家在商户页
			mTabLayout.setVisibility(View.VISIBLE);
		}

		// 日志纪录
		NetworkPrompt.requestLogRecord(this, this, (new DBHelper(this).selectUpdateCarInfoWithUuid(UserInfoManager.getUuidAndType(this))).size() + "", "open");

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!hasDoRequest) {
			hasDoRequest = true;
			if (UserInfoManager.isBusiness(this)) {
				mCarResourcesFragment.requestData();
			} else {
				mBusinessFragment.requestData();
			}
		}
	}

	/* 切换至商户页 */
	private void onLeftTabClick() {

		mLeftTabTv.setTextColor(getResources().getColor(R.color.red));
		mRightTabTv.setTextColor(getResources().getColor(R.color.black));

		if (mCurrentFragment == null) {
			FragmentTransaction transaction = mFragmentManager.beginTransaction();

			transaction.add(R.id.fragment_container, mBusinessFragment);

			transaction.commit();

			mCurrentFragment = mBusinessFragment;

		} else if (mCurrentFragment instanceof BusinessFragment) {
			return;
		} else {
			FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if (!mBusinessFragment.isAdded()) {
				transaction.hide(mCurrentFragment).add(R.id.fragment_container, mBusinessFragment).commit();
			} else {
				transaction.hide(mCurrentFragment).show(mBusinessFragment).commit();
			}

			mCurrentFragment = mBusinessFragment;
		}
	}

	/* 切换至车源页 */
	private void onRightTabClick() {

		mLeftTabTv.setTextColor(getResources().getColor(R.color.black));
		mRightTabTv.setTextColor(getResources().getColor(R.color.red));

		if (mCurrentFragment == null) {
			FragmentTransaction transaction = mFragmentManager.beginTransaction();

			transaction.add(R.id.fragment_container, mCarResourcesFragment);

			transaction.commit();

			mCurrentFragment = mCarResourcesFragment;

		} else if (mCurrentFragment instanceof CarResourcesFragment) {
			return;
		} else {
			FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if (!mCarResourcesFragment.isAdded()) {
				transaction.hide(mCurrentFragment).add(R.id.fragment_container, mCarResourcesFragment).commit();
				mCarResourcesFragment.setActivity(this);
				mCarResourcesFragment.requestData();
			} else {
				transaction.hide(mCurrentFragment).show(mCarResourcesFragment).commit();
			}

			mCurrentFragment = mCarResourcesFragment;

		}
	}

	/* 跳到未上传页 */
	@Override
	protected void onRightTvClick() {
		super.onRightTvClick();

		IntentUtil.forwordToActivity(this, NoUpdatedCarsList.class);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rl_left_tab:
			onLeftTabClick();
			break;
		case R.id.rl_right_tab:
			onRightTabClick();
			break;

		default:
			break;
		}
	}

	/**
	 * 退出应用
	 * */
	private long clickBackTime = 0;

	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (clickBackTime == 0 || System.currentTimeMillis() - clickBackTime > 2000) {
				SuperToast.show(getString(R.string.exit_tips), this);
				clickBackTime = System.currentTimeMillis();
			} else {
				ActivityStack.getActivityStack().popAllActivityExceptOne(HomeActivity.class);
				if (Build.VERSION.SDK_INT < 8) {
					super.finish();
					System.exit(0);
					manager.restartPackage(getPackageName());
				} else {
					super.finish();
					// manager.killBackgroundProcesses(getPackageName());
					System.exit(0);
				}
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void OnClickOpeartion2(String gysId, String gysName) {

		mLeftTabTv.setTextColor(getResources().getColor(R.color.black));
		mRightTabTv.setTextColor(getResources().getColor(R.color.red));

		if (mCurrentFragment instanceof CarResourcesFragment) {
			return;
		} else {
			FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if (!mCarResourcesFragment.isAdded()) {
				transaction.hide(mCurrentFragment).add(R.id.fragment_container, mCarResourcesFragment).commit();

				mCarResourcesFragment.setActivity(this);

			} else {
				transaction.hide(mCurrentFragment).show(mCarResourcesFragment).commit();
			}

			mCurrentFragment = mCarResourcesFragment;

			mCarResourcesFragment.requestDataWithGYSId(gysName, gysId);

		}
	}

	/* 注销 */
	@Override
	protected void onLeftSecondTvClick() {
		showProgressHUD(this, getString(R.string.logouting));
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				IntentUtil.forwordToActivity(HomeActivity.this, LoginInActivity.class);
				UserInfoManager.clearUserData(HomeActivity.this);
				HomeActivity.this.finish();
			}
		}, 300);
	}

	@Override
	public void onResult(ResponseData aData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(ResponseErrorInfo aErrorInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProtocolError(ResponseData aData) {
		// TODO Auto-generated method stub

	}
}
