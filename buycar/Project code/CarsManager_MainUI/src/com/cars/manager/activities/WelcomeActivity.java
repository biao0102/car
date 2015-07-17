package com.cars.manager.activities;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cars.manager.application.App;
import com.cars.manager.application.App.UpdatePPDBListener;
import com.cars.manager.db.chexingdb.SerachService;
import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Chexi;
import com.cars.manager.db.table.Chexing;
import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.networks.response.LoginResponse;
import com.cars.manager.networks.response.UpdateChexiResponse;
import com.cars.manager.networks.response.UpdateChexingResponse;
import com.cars.manager.networks.response.UpdateGYSResponse;
import com.cars.manager.networks.response.UpdateLocationResponse;
import com.cars.manager.networks.response.UpdateManagerResponse;
import com.cars.manager.networks.response.UpdatePinPaiResponse;
import com.cars.manager.networks.response.UpdateTabsResponse;
import com.cars.manager.networks.response.UpdateYanseResponse;
import com.cars.manager.networks.response.UpdateZhuangtaiResponse;
import com.cars.manager.utils.ActivityStack;
import com.cars.manager.utils.IntentUtil;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;
import com.standard.kit.text.TextUtil;
import com.standard.kit.thread.ThreadPoolUtil;

/**
 * 启动页
 * */
public class WelcomeActivity extends BaseTitleActivity implements NetDataCallBack, UpdatePPDBListener, Runnable {

	/* 登陆UI */
	/* 用户名 */
	EditText mUserNameEdt;
	/* 密码 */
	EditText mPasswordEdt;
	/* 输入框布局 */
	LinearLayout mEditLayout;
	/* 登录 */
	RelativeLayout mLoginIn;

	boolean mPinpaiUpdate = false, mChexiUpdate = false, mChexingUpdate = false, mManagerUpdate = false, mColorUpdate = false, mTabsUpdate = false,
			mGYSUpdate = false, mStateUpdate = false, mLocationUpdate = false;

	String mPinpaiTimeStamp, mChexiTimeStamp, mChexingTimeStamp, mGYSTimeStamp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		setSwipeBackEnable(false);
		initUI();
		ThreadPoolUtil.getInstance().execute(this);
	}

	protected void initUI() {
		mUserNameEdt = (EditText) findViewById(R.id.edt_input_username);
		mPasswordEdt = (EditText) findViewById(R.id.edt_input_password);
		mLoginIn = (RelativeLayout) findViewById(R.id.rl_loginin_btlayout);
		mEditLayout = (LinearLayout) findViewById(R.id.fl_forum_login_edit_linearLayout);

		mLoginIn.setOnClickListener(this);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(200);
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					/* 更新品牌、车系、车型 */
					updateCarsInfo();
					/* 请求负责人、颜色、供应商、状态、地域 <定期查询> */
					updateCarsNeedsInfo();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void updateCarsInfo() {

		if (UserInfoManager.getIsFirstUpdate(this).equals("0")) {
			UserInfoManager.setIsFirstUpdate(this, "1");
			try {
				showProgressHUD(this, getString(R.string.getting), false);
			} catch (Exception e) {
			}
		} else {
			showProgressHUD(this, getString(R.string.loading), false);
		}

		/* 品牌更新 */
		if (UserInfoManager.getIsFirstUpdatePinpai(this).equals("0")) {// 第一次更新
			UserInfoManager.setIsFirstUpdatePinpai(this, "1");
			NetworkPrompt.requestUpdatePinPai(this, this, "");
		} else {// 之后每次增量跟新
			NetworkPrompt.requestUpdatePinPai(this, this, UserInfoManager.getPinpaiTimeStamp(this));
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 车系更新 */
		if (UserInfoManager.getIsFirstUpdateChexi(this).equals("0")) {// 第一次更新
			UserInfoManager.setIsFirstUpdateChexi(this, "1");
			NetworkPrompt.requestUpdateChexi(this, this, "");
		} else {// 之后每次增量跟新
			NetworkPrompt.requestUpdateChexi(this, this, UserInfoManager.getChexiTimeStamp(this));
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 车型更新 */
		if (UserInfoManager.getIsFirstUpdateChexing(this).equals("0")) {// 第一次更新
			UserInfoManager.setIsFirstUpdateChexing(this, "1");
			NetworkPrompt.requestUpdateChexing(this, this, "");
		} else {// 之后每次增量跟新
			NetworkPrompt.requestUpdateChexing(this, this, UserInfoManager.getChexingTimeStamp(this));
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* 一天请求一次 */
	private void updateCarsNeedsInfo() {
		if (UserInfoManager.getCarSecondInfoUpdateDay(this) != DateTimeUtil.getDay(System.currentTimeMillis())) {
			UserInfoManager.setCarSecondInfoUpdateDay(this, DateTimeUtil.getDay(System.currentTimeMillis()));

			/* 状态 */
			NetworkPrompt.requestUpdateZhuangtai(this, this);

			/* 地域 */
			NetworkPrompt.requestUpdateLocation(this, this);

			/* 颜色 */
			NetworkPrompt.requestUpdateYanse(this, this);

			/* 商户负责人 */
			NetworkPrompt.requestUpdateManager(this, this);

			/* 用途 */
			NetworkPrompt.requestUpdateTabs(this, this);

		} else {
			mManagerUpdate = true;
			mColorUpdate = true;
			mStateUpdate = true;
			mLocationUpdate = true;
			mTabsUpdate = true;
		}

		/* 供应商 */
		NetworkPrompt.requestUpdateGYS(this, this, UserInfoManager.getGYSTimeStamp(this));

	}

	@Override
	public void onResult(ResponseData aData) {
		/* 品牌更新 */
		if (aData instanceof UpdatePinPaiResponse) {
			UpdatePinPaiResponse response = (UpdatePinPaiResponse) aData;
			mPinpaiTimeStamp = response.mTimeStamp;
			App.updatePinPaiDB(response.mPinpais, this);
		}

		/* 车系更新 */
		if (aData instanceof UpdateChexiResponse) {
			UpdateChexiResponse response = (UpdateChexiResponse) aData;
			mChexiTimeStamp = response.mTimeStamp;
			updateChexiDB(response.mChexis);
		}
		/* 车型更新 */
		if (aData instanceof UpdateChexingResponse) {
			UpdateChexingResponse response = (UpdateChexingResponse) aData;
			mChexingTimeStamp = response.mTimeStamp;
			updateChexingDB(response.mChexings);
		}

		/* 状态 */
		if (aData instanceof UpdateZhuangtaiResponse) {
			UpdateZhuangtaiResponse response = (UpdateZhuangtaiResponse) aData;
			new DBHelper(this).addStatuses(response.mZhuangtais);
			mStateUpdate = true;
			checkCompleteInitData();
		}

		/* 供应商 */
		if (aData instanceof UpdateGYSResponse) {
			UpdateGYSResponse response = (UpdateGYSResponse) aData;
			new DBHelper(this).addGYS(response.mGongyingshangs);
			mGYSTimeStamp = response.mTimeStamp;
			UserInfoManager.setGYSTimeStamp(this, mGYSTimeStamp);
			mGYSUpdate = true;
			checkCompleteInitData();
		}

		/* 地域 */
		if (aData instanceof UpdateLocationResponse) {
			UpdateLocationResponse response = (UpdateLocationResponse) aData;
			new DBHelper(this).addLoactions(response.mLocations);
			mLocationUpdate = true;
			checkCompleteInitData();
		}

		/* 颜色 */
		if (aData instanceof UpdateYanseResponse) {
			UpdateYanseResponse response = (UpdateYanseResponse) aData;
			new DBHelper(this).addYanses(response.mYanses);
			mColorUpdate = true;
			checkCompleteInitData();
		}

		/* 用途 */
		if (aData instanceof UpdateTabsResponse) {
			UpdateTabsResponse response = (UpdateTabsResponse) aData;
			new DBHelper(this).addTabs(response.mTabs);
			mTabsUpdate = true;
			checkCompleteInitData();
		}

		/* 商户负责人 */
		if (aData instanceof UpdateManagerResponse) {
			UpdateManagerResponse response = (UpdateManagerResponse) aData;
			new DBHelper(this).addManager(response.mManagers);
			mManagerUpdate = true;
			checkCompleteInitData();
		}

		/* 请求登陆 */
		if (aData instanceof LoginResponse) {
			dismissProgressHUD();
			final LoginResponse aResponse = (LoginResponse) aData;
			UserInfoManager.setUuid(this, aResponse.userId);
			UserInfoManager.setCityId(this, aResponse.cityId);
			UserInfoManager.setType(this, aResponse.type);
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					SuperToast.show(aResponse.tips, WelcomeActivity.this);
					ActivityStack.getActivityStack().pushActivity(WelcomeActivity.this);
					IntentUtil.forwordToActivity(WelcomeActivity.this, HomeActivity.class);
				}
			});
		}
	}

	@Override
	public void onError(final ResponseErrorInfo aErrorInfo) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				SuperToast.show(aErrorInfo.mErrorTips, WelcomeActivity.this);
				dismissProgressHUD();
			}
		});
	}

	@Override
	public void onProtocolError(final ResponseData aData) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				SuperToast.show(aData.tips, WelcomeActivity.this);
				dismissProgressHUD();
			}
		});
	}

	/* 写入车系到数据库 */
	public void updateChexiDB(ArrayList<Chexi> aChexis) {
		if (UserInfoManager.getIsFirstUpdateChexiDB(this).equals("0")) {
			UserInfoManager.setIsFirstUpdateChexiDB(this, "1");
			try {
				new SerachService(this).saveChexi(aChexis);
				UserInfoManager.setChexiTimeStamp(this, mChexiTimeStamp);
			} catch (Exception e) {
			}
		} else {
			try {
				new SerachService(this).saveChexiWithSearch(aChexis);
				UserInfoManager.setChexiTimeStamp(this, mChexiTimeStamp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.mChexiUpdate = true;
		checkCompleteInitData();
	}

	/* 写入车型到数据库 */
	public void updateChexingDB(ArrayList<Chexing> aChexings) {
		if (UserInfoManager.getIsFirstUpdateChexingDB(this).equals("0")) {
			UserInfoManager.setIsFirstUpdateChexingDB(this, "1");
			try {
				new SerachService(this).saveChexing(aChexings);
				UserInfoManager.setChexingTimeStamp(this, mChexingTimeStamp);
			} catch (Exception e) {
			}
		} else {
			try {
				new SerachService(this).saveChexingWithSearch(aChexings);
				UserInfoManager.setChexingTimeStamp(this, mChexiTimeStamp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.mChexingUpdate = true;
		checkCompleteInitData();

	}

	@Override
	public void onUpdatePPComplete(boolean pinpai) {
		this.mPinpaiUpdate = pinpai;
		UserInfoManager.setPinpaiTimeStamp(this, mPinpaiTimeStamp);
		checkCompleteInitData();
	}

	private void checkCompleteInitData() {
		if (mChexiUpdate && mPinpaiUpdate && mChexingUpdate && mStateUpdate && mColorUpdate && mTabsUpdate && mGYSUpdate && mLocationUpdate && mManagerUpdate) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					dismissProgressHUD();
					if (UserInfoManager.isBusiness(WelcomeActivity.this)) {
						IntentUtil.forwordToActivity(WelcomeActivity.this, HomeActivity.class);
						ActivityStack.getActivityStack().pushActivity(WelcomeActivity.this);
					} else {
						mLoginIn.setVisibility(View.VISIBLE);
						mEditLayout.setVisibility(View.VISIBLE);
					}
				}
			});
		}
	}

	/* 登陆 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_loginin_btlayout:
			onLoginInClick();
			break;
		default:
			break;
		}
	}

	private void onLoginInClick() {

		if (TextUtil.isEmpty(mUserNameEdt.getText().toString().trim())) {
			SuperToast.show(getString(R.string.shuru_username), this);
		} else if (TextUtil.isEmpty(mPasswordEdt.getText().toString().trim())) {
			SuperToast.show(getString(R.string.shuru_password), this);
		} else {
			dismissProgressHUD();
			showProgressHUD(this, getString(R.string.loading), false);
			NetworkPrompt.requestLoginin(this, this, mUserNameEdt.getText().toString().trim(), mPasswordEdt.getText().toString().trim());
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
					System.exit(0);
				}
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

}
