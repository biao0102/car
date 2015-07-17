package com.cars.manager.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.managers.Config;
import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.networks.response.UpdateCarResourceInfoResponse;
import com.cars.manager.utils.ActivityStack;
import com.cars.manager.utils.IntentUtil;
import com.cars.manager.views.fragments.CarDescFragment;
import com.cars.manager.views.fragments.CarNeedInfoFragment;
import com.cars.manager.views.fragments.CarPhotosFragment;
import com.cars.manager.views.popupwindows.MyTipsBuilder;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;
import com.standard.kit.text.TextUtil;

/**
 * @author chengyanfang
 * 
 *         录入车辆界面
 * */
public class PublishCarActivity extends BaseTitleActivity implements NetDataCallBack {

	private Fragment mCurrentFragment;

	private CarPhotosFragment mCarPhotosFragment;

	private CarNeedInfoFragment mCarNeedInfoFragment;

	private FragmentManager mFragmentManager;

	/* 保存车源信息 */
	public UpdateCarInfo mForUpLoadCarInfo;

	/* 车辆图片列表 */
	public ArrayList<PhotoInfo> mPhotoInfos;

	/* 是否来自于车辆更新 */
	public String mIsFromUpdae = "0";
	public String mStatusValue = "0";

	/* 如果试来自车辆更新的话，获取车辆编号 */
	public String mVehicleNo = "0";

	/* 是否来自于未上传车辆的修改 */
	public String mIsFromChange = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publishcar_layout);
		initUI();
		initData();
		setSwipeBackEnable(false);
	}

	@Override
	protected void initUI() {
		super.initUI();

		setTitleTv(getString(R.string.title_paizhao));
		setLeftIv(R.drawable.img_base_title_back);
		setLeftSecondTv(getString(R.string.quxiao));
		setRightTv(getString(R.string.title_shanchu));
		setTitleRightTvShow(true);

		initBottomBar();
		setBottomLeftTv(getString(R.string.bottombar_tiaoguo));
		setBottomRightTv(getString(R.string.bottombar_baocun));
		setBottomRightTvColor(getResources().getColor(R.color.color_e22319));
	}

	@Override
	protected void initData() {
		super.initData();

		mFragmentManager = getSupportFragmentManager();

		mCarPhotosFragment = new CarPhotosFragment();

		mCarNeedInfoFragment = new CarNeedInfoFragment();

		mCarPhotosFragment.setmActivity(this);

		mCarNeedInfoFragment.setmActivity(this);

		loadData();
	}

	private void loadData() {

		mIsFromUpdae = (String) getIntent().getStringExtra("isFromUpdae");

		mVehicleNo = (String) getIntent().getStringExtra("vehicleNo");
		mStatusValue = (String) getIntent().getStringExtra("statusValue");

		mIsFromChange = (String) getIntent().getStringExtra("mIsFromChange");

		if (!TextUtil.isEmpty(mIsFromUpdae) && "1".equals(mIsFromUpdae)) {// 网络更新

			showProgressHUD(this, getString(R.string.loading));

			NetworkPrompt.requestUpdateCarResourceInfo(this, this, mVehicleNo);

		} else if (!TextUtil.isEmpty(mIsFromChange) && "1".equals(mIsFromChange)) {// 未上传更新
			mForUpLoadCarInfo = (UpdateCarInfo) getIntent().getSerializableExtra("carInfo");

			mPhotoInfos = new DBHelper(PublishCarActivity.this).selectPhotoInfoWithId(mForUpLoadCarInfo.getTime());

			showCarInfoFragment();
		} else// 增加新车源
		{
			/* 如果是从商户页传过来的，要获取供应商信息显示 */
			if (!TextUtil.isEmpty(getIntent().getStringExtra("bussinessId"))) {
				mForUpLoadCarInfo = new UpdateCarInfo();
				mForUpLoadCarInfo.setSupplierId(getIntent().getStringExtra("bussinessId"));
			}

			showCarInfoFragment();
		}
	}

	private void showCarInfoFragment() {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();

		transaction.add(R.id.fragment_container, mCarPhotosFragment);

		transaction.commit();

		mCurrentFragment = mCarPhotosFragment;
	}

	/* 删除 */
	@Override
	protected void onRightTvClick() {
		if (!mCarPhotosFragment.isHidden()) {
			mCarPhotosFragment.changeDeleteState();
		}
	}

	/* 取消或上一步 */
	@Override
	protected void onLeftSecondTvClick() {
		this.onLeftIvClick();
	}

	/* 取消或跳过 */
	@Override
	protected void onBottomLeftTvClick() {

		if (mCurrentFragment instanceof CarPhotosFragment) {// 跳过
			this.onBottomRightTvClick();
		} else {// 取消
			showNoticeDialog();
		}

	}

	/* 返回 */
	@Override
	protected void onLeftIvClick() {

		FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		/* 退出 */
		if (mCurrentFragment instanceof CarPhotosFragment) {
			showNoticeDialog();
		}
		/* 必填－拍照 */
		else if (mCurrentFragment instanceof CarNeedInfoFragment) {
			if (!mCarPhotosFragment.isAdded()) {
				transaction.hide(mCurrentFragment).add(R.id.fragment_container, mCarPhotosFragment).commit();
			} else {
				transaction.hide(mCurrentFragment).show(mCarPhotosFragment).commit();
			}

			mCurrentFragment = mCarPhotosFragment;

			setTitleRightTvShow(true);

			setTitleTv(getString(R.string.title_paizhao));

			setBottomLeftTv(getString(R.string.bottombar_tiaoguo));

			setBottomRightTv(getString(R.string.bottombar_baocun));

			setLeftSecondTv(getString(R.string.bottombar_quxiao));
		}
		/* 描述－必填 */
		else if (mCurrentFragment instanceof CarDescFragment) {
			if (!mCarNeedInfoFragment.isAdded()) {
				transaction.hide(mCurrentFragment).add(R.id.fragment_container, mCarNeedInfoFragment).commit();
			} else {
				transaction.hide(mCurrentFragment).show(mCarNeedInfoFragment).commit();
			}

			mCurrentFragment = mCarNeedInfoFragment;

			setTitleRightTvShow(false);

			setTitleTv(getString(R.string.title_bitian));

			setBottomLeftTv(getString(R.string.bottombar_quxiao));

			setBottomRightTv(getString(R.string.bottombar_next));

			setLeftSecondTv(getString(R.string.title_shangyibu));
		}
	}

	/* 保存/下一步 */
	@Override
	protected void onBottomRightTvClick() {

		FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		/* 拍照－必填信息 */
		if (mCurrentFragment instanceof CarPhotosFragment) {

			if (!mCarNeedInfoFragment.isAdded()) {
				transaction.hide(mCurrentFragment).add(R.id.fragment_container, mCarNeedInfoFragment).commit();
			} else {
				transaction.hide(mCurrentFragment).show(mCarNeedInfoFragment).commit();
			}

			mCurrentFragment = mCarNeedInfoFragment;

			setTitleRightTvShow(false);

			setTitleTv(getString(R.string.title_bitian));

			setBottomLeftTv(getString(R.string.bottombar_quxiao));

			setBottomRightTv(getString(R.string.bottombar_baocun));

			setLeftSecondTv(getString(R.string.title_shangyibu));
		}

		/* 必填－未上传页面 */
		else if (mCurrentFragment instanceof CarNeedInfoFragment) {
			if (!TextUtil.isEmpty(mIsFromUpdae) && "1".equals(mIsFromUpdae)) {// 车辆更新

				updateCarSoruceInfo();

				IntentUtil.forwordToActivity(this, NoUpdatedCarsList.class);

				ActivityStack.getActivityStack().popAllActivityExceptOne(PublishCarActivity.class);

			} else if (!TextUtil.isEmpty(mIsFromChange) && "1".equals(mIsFromChange)) {// 为上传车辆修改

				updateCarResourceToDB();

				setResult(31);

			} else// 添加新车辆
			{
				saveCarResourceToDB();

				IntentUtil.forwordToActivity(this, NoUpdatedCarsList.class);

				ActivityStack.getActivityStack().popAllActivityExceptOne(PublishCarActivity.class);
			}

			this.finish();
		}

		/* 描述信息 － 保存完成页面 */
		else if (mCurrentFragment instanceof CarDescFragment) {
			SuperToast.show("跳转到保存页面", this);
		}

	}

	/* 取消上传提示 */
	private void showNoticeDialog() {
		MyTipsBuilder aBuilder = new MyTipsBuilder(this, mCurrentFragment.getView());

		aBuilder.setTitle(getString(R.string.wenxintishi))
				.setMessage("1".equals(mIsFromUpdae) || "1".equals(mIsFromChange) ? getString(R.string.quxiaoupdate) : getString(R.string.quxiaoupluru))
				.setLeftBtText(getString(R.string.queding)).setRightBtText(getString(R.string.quxiao)).setLeftBtClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						PublishCarActivity.this.finish();
					}
				}).setRightBtClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				}).show();
	}

	/* 保存未上传车辆到数据库 */
	private void saveCarResourceToDB() {

		if (mForUpLoadCarInfo == null)
			mForUpLoadCarInfo = new UpdateCarInfo();

		mCarNeedInfoFragment.saveCarInfo(mForUpLoadCarInfo);

		mForUpLoadCarInfo.setTime(System.currentTimeMillis() + "");

		mForUpLoadCarInfo.setSource(Config.NEW_ADD);

		mForUpLoadCarInfo.setZhengqianfangImg(mCarPhotosFragment.getZhengqianfangImg());

		mCarPhotosFragment.saveCarInfo(mForUpLoadCarInfo.getTime());

		mForUpLoadCarInfo.setWanzhengdu(mCarPhotosFragment.mWanzhengdu + mCarNeedInfoFragment.getWanzhengduCount());

		try {
			new DBHelper(this).addUpdateCarInfo(mForUpLoadCarInfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/* 更新未上传车辆到数据库 */
	private void updateCarResourceToDB() {

		if (mForUpLoadCarInfo == null)
			mForUpLoadCarInfo = new UpdateCarInfo();

		// 保存基本信息
		mCarNeedInfoFragment.saveCarInfo(mForUpLoadCarInfo);

		// 保存正前方图片
		mForUpLoadCarInfo.setZhengqianfangImg(mCarPhotosFragment.getZhengqianfangImg());

		// 当前时间戳未PhotoId的前半段，保存图片信息到数据库
		mCarPhotosFragment.saveCarInfo(mForUpLoadCarInfo.getTime());

		// 设置完整度
		mForUpLoadCarInfo.setWanzhengdu(mCarPhotosFragment.mWanzhengdu + mCarNeedInfoFragment.getWanzhengduCount());

		new DBHelper(this).updateUpdateCarInfo(mForUpLoadCarInfo);
	}

	/* 更新车源然后保存到数据库 */
	private void updateCarSoruceInfo() {
		if (mForUpLoadCarInfo == null)
			mForUpLoadCarInfo = new UpdateCarInfo();

		// 保存基本信息
		mCarNeedInfoFragment.saveCarInfo(mForUpLoadCarInfo);

		mForUpLoadCarInfo.setStatusName(mStatusValue);

		// 保存正前方图片
		mForUpLoadCarInfo.setZhengqianfangImg(mCarPhotosFragment.getZhengqianfangImg());

		// 根据来源进行不同的保存
		mForUpLoadCarInfo.setSource(Config.UPDATE);

		// 当前时间戳未PhotoId的前半段，保存图片信息到数据库
		mForUpLoadCarInfo.setTime(System.currentTimeMillis() + "");
		mCarPhotosFragment.saveCarInfo(mForUpLoadCarInfo.getTime());

		// 设置完整度
		mForUpLoadCarInfo.setWanzhengdu(mCarPhotosFragment.mWanzhengdu + mCarNeedInfoFragment.getWanzhengduCount());

		new DBHelper(this).addUpdateCarInfo(mForUpLoadCarInfo);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2 || requestCode == 3 && mCarPhotosFragment != null) {// 图片编辑
			mCarPhotosFragment.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mCurrentFragment instanceof CarPhotosFragment) {
				this.onLeftSecondTvClick();
			} else {
				showNoticeDialog();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onResult(ResponseData aData) {
		if (aData instanceof UpdateCarResourceInfoResponse) {
			mForUpLoadCarInfo = ((UpdateCarResourceInfoResponse) aData).mUpdateCarInfo;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					dismissProgressHUD();
					showCarInfoFragment();
				}
			});
		}
	}

	@Override
	public void onError(ResponseErrorInfo aErrorInfo) {

	}

	@Override
	public void onProtocolError(ResponseData aData) {

	}

}
