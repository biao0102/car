package com.cars.manager.views.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cars.manager.activities.PublishCarActivity;
import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.CarResource;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.networks.response.CarResourceListResponse;
import com.cars.manager.networks.response.UpdateCarResourceResponse;
import com.cars.manager.utils.IntentUtil;
import com.cars.manager.views.adapters.CarsResourceAdapter;
import com.cars.manager.views.others.CarSoruceHeader;
import com.cars.manager.views.others.CarSoruceHeader.OnClickSearchBt;
import com.cars.manager.views.popupwindows.UpdateStateBuilder;
import com.cars.manager.views.popupwindows.UpdateStateBuilder.OnClickUpdateListener;
import com.cars.manager.views.widgts.pullToRefresh.PullToRefreshFragmentListView;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;
import com.standard.kit.text.TextUtil;

/**
 * @author chengyanfang
 * 
 *         车源页
 * */
public class CarResourcesFragment extends PullToRefreshFragmentListView implements OnItemClickListener, OnClickListener, NetDataCallBack, OnClickSearchBt,
		OnClickUpdateListener {

	ArrayList<CarResource> mCarResources = new ArrayList<CarResource>();

	ArrayList<CarResource> aCarResources = new ArrayList<CarResource>();

	CarsResourceAdapter mAdapter;

	int mCurrentPage = 1, mTotalPages;

	boolean mHasSelectOne = false;

	int mSelectedPosition = -1;

	LinearLayout mOperationLayout;

	Handler mHandler;

	TextView mBusiness_op1, mBusiness_op2, mBusiness_op3;

	boolean isFromSearch = false;

	boolean isFromBusiness = false;

	CarSoruceHeader mHeaderView;

	/* 搜索条件 */
	String mCityId = "", mBrandId = "", mSeriesId = "", mStatus = "", mTypeId = "", mVin = "", mVehicleNo = "", mSupplierId = "", mSupplierName = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_carsource_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mView = getView();

		mActivity = getActivity();

		mHandler = new Handler(mActivity.getMainLooper());

		initUI();

		initData();
	}

	public void setActivity(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(mActivity.getMainLooper());
	}

	@Override
	protected void initUI() {
		super.initUI();
		mHeaderView = new CarSoruceHeader(mActivity);
		mHeaderView.setmOnClickSearchBt(this);
		mListView.addHeaderView(mHeaderView.getView());

		mListView.setOnItemClickListener(this);

		/* operations */
		mOperationLayout = (LinearLayout) mView.findViewById(R.id.ll_carsource_operation_lay);

		mBusiness_op1 = (TextView) mView.findViewById(R.id.tv_business_op_1);
		mBusiness_op2 = (TextView) mView.findViewById(R.id.tv_business_op_2);
		mBusiness_op3 = (TextView) mView.findViewById(R.id.tv_business_op_3);

		mBusiness_op1.setOnClickListener(this);
		mBusiness_op2.setOnClickListener(this);
		mBusiness_op3.setOnClickListener(this);
	}

	private void initData() {
		if (UserInfoManager.isBusiness(mActivity)) {
			mCityId = UserInfoManager.getCityId(mActivity);
			mSupplierId = UserInfoManager.getUuid(mActivity);
		}
	}

	public void requestData() {
		if (TextUtil.isEmpty(mCityId)) {
			mCityId = UserInfoManager.getCityId(mActivity);
		}
		showProgressHUD(mActivity, mActivity.getString(R.string.pull_to_refresh_getmore));
		NetworkPrompt.requestCarList(mActivity, this, mCurrentPage + "", mCityId, mBrandId, mSeriesId, mTypeId, mStatus, mVin, mVehicleNo, mSupplierId);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		position = position - 2;

		if (position == -1) {// header点击不处理
			return;
		}

		if (mHasSelectOne) {

			mCarResources.get(mSelectedPosition).setSelected(false);

			mAdapter.updateAdapter(mCarResources);

			if (mSelectedPosition == position) {
				mSelectedPosition = -1;
				mHasSelectOne = false;
				showOperationLay(false);
				return;
			}
		}
		mHasSelectOne = true;
		mSelectedPosition = position;
		mCarResources.get(position).setSelected(true);
		mAdapter.updateAdapter(mCarResources);

		showOperationLay(true);
	}

	@Override
	protected void refresh() {
		super.refresh();
		mCurrentPage = 1;
		requestData();
	}

	@Override
	protected void loadMore() {
		super.loadMore();
		mCurrentPage++;
		requestData();
	}

	@Override
	public void onResult(ResponseData aData) {
		dismissProgressHUD();
		if (aData instanceof CarResourceListResponse) {
			CarResourceListResponse aResponse = (CarResourceListResponse) aData;
			aCarResources = aResponse.mCarResources;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					loadData();
				}
			});
		}

		if (aData instanceof UpdateCarResourceResponse) {
			final UpdateCarResourceResponse aResponse = (UpdateCarResourceResponse) aData;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					mSelectedPosition = -1;
					mHasSelectOne = false;
					showOperationLay(false);
					refresh();
					SuperToast.show(aResponse.tips, mActivity);
				}
			});
		}
	}

	@Override
	public void onError(ResponseErrorInfo aErrorInfo) {
		dismissProgressHUD();
		SuperToast.show(aErrorInfo.mErrorTips, mActivity);
	}

	@Override
	public void onProtocolError(ResponseData aData) {
		SuperToast.show(aData.tips, mActivity);
	}

	private void loadData() {
		onRefreshComplete();
		dismissProgressHUD();

		if (mCurrentPage == 1) {
			mCarResources.clear();
			mHasSelectOne = false;
			mSelectedPosition = -1;
			showOperationLay(false);
		}

		if (!aCarResources.isEmpty()) {

			if (mCurrentPage == 1) {// 刷新清除
				mCarResources.clear();
			}

			mCarResources.addAll(aCarResources);

		} else {

			if (isFromSearch) {
				SuperToast.show(getString(R.string.data_load_complete_search), mActivity);
			} else {
				SuperToast.show(getString(R.string.data_load_complete), mActivity);
			}

		}

		loadAdapter();

		isFromSearch = false;

		if (isFromBusiness) {
			mHeaderView.setXJCLName(mSupplierName, mSupplierId);
			isFromBusiness = false;
		}
	}

	private void loadAdapter() {
		if (mAdapter == null) {
			mAdapter = new CarsResourceAdapter(mActivity, mCarResources, false);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_business_op_1:
			onOperation1Click();
			break;
		case R.id.tv_business_op_2:
			onOperation2Click();
			break;
		case R.id.tv_business_op_3:
			onOperation3Click();
			break;
		default:
			break;
		}
	}

	private void showOperationLay(boolean isShow) {
		if (isShow) {
			mOperationLayout.setVisibility(View.VISIBLE);
		} else {
			mOperationLayout.setVisibility(View.GONE);
		}
	}

	/* 上架车辆 */
	public void onOperation1Click() {
		IntentUtil.forwordToActivity(mActivity, PublishCarActivity.class);
	}

	/* 更新状态 */
	public void onOperation2Click() {
		UpdateStateBuilder aBuilder = new UpdateStateBuilder(mActivity, this, mView);
		aBuilder.setmClickUpdateListener(this);
		aBuilder.show();
	}

	/* 修改信息 */
	public void onOperation3Click() {
		if (isCanChange()) {
			Intent aIntent = new Intent(mActivity, PublishCarActivity.class);
			aIntent.putExtra("isFromUpdae", "1");
			aIntent.putExtra("vehicleNo", mCarResources.get(mSelectedPosition).getVehicleNo());
			aIntent.putExtra("statusValue", mCarResources.get(mSelectedPosition).getStatusValue());
			this.startActivity(aIntent);
		} else {
			SuperToast.show(mActivity.getString(R.string.already_changed_car_tip), mActivity);
		}

	}

	/* 已经发生修改的车辆，保存到未上传列表中，还可以修改这个车辆，再次保存到未上传列表中<解决方法：提示“该车辆已经发生修改，请上传后再做此操作”> */
	private boolean isCanChange() {

		List<UpdateCarInfo> noUpdateList = new DBHelper(mActivity).selectUpdateCarInfoAll();

		for (int i = 0; i < noUpdateList.size(); i++) {
			if (mCarResources.get(mSelectedPosition).getVehicleNo().equals(noUpdateList.get(i).getVehicleNo())) {
				return false;
			}
		}

		return true;

	}

	@Override
	public void onClickSearch(String brandId, String seriesId, String typeId, String status, String vin, String vehicleNo, String supplierId, String cityId) {

		mCurrentPage = 1;
		this.mCityId = cityId;
		this.mBrandId = brandId;
		this.mSeriesId = seriesId;
		this.mTypeId = typeId;
		this.mVin = vin;
		this.mVehicleNo = vehicleNo;
		this.mSupplierId = supplierId;
		this.isFromSearch = true;
		this.mStatus = status;
		this.requestData();
	}

	public void requestDataWithGYSId(String gysName, String gysId) {
		this.mSupplierId = gysId;
		this.mSupplierName = gysName;
		this.mStatus = new DBHelper(mActivity).selectWeiShouZhuangtais().getStatus();
		this.mCityId = UserInfoManager.getCityId(mActivity);
		this.isFromBusiness = true;
		this.isFromSearch = true;
		mCurrentPage = 1;
		requestData();
	}

	/* 从商户页跳转过来,直接进行搜索 */
	public void requestXiajiaCheliang(String gysName, String gysId) {
		if (mHeaderView != null) {
			mHeaderView.setXJCLName(gysName, gysId);
		}
	}

	@Override
	public void onClickUpdate(String price, String time, boolean isSell, boolean isQMC) {
		CarResource aCarResource = mCarResources.get(mSelectedPosition);
		if (isSell) {
			NetworkPrompt.requestUpdateCarResourceState(mActivity, this, aCarResource.getVehicleNo(), UserInfoManager.getUuid(mActivity),
					aCarResource.getSupplierId(), aCarResource.getStatus(), "1", price, time, isQMC ? "0" : "1");
		} else {
			NetworkPrompt.requestUpdateCarResourceState(mActivity, this, aCarResource.getVehicleNo(), UserInfoManager.getUuid(mActivity),
					aCarResource.getSupplierId(), aCarResource.getStatus(), "0", "", "", "");
		}
		showProgressHUD(mActivity, getString(R.string.loading));
	}
}
