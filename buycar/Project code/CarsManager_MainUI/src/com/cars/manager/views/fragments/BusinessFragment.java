package com.cars.manager.views.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cars.manager.activities.PublishCarActivity;
import com.cars.manager.db.table.Bisnessse;
import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.networks.response.DelBusinessResponse;
import com.cars.manager.networks.response.NoCarUpdateResponse;
import com.cars.manager.networks.response.ShanghuResponse;
import com.cars.manager.networks.response.UpdateBusinessInfoResponse;
import com.cars.manager.networks.response.UpdateBusinessResponse;
import com.cars.manager.views.adapters.BusinessListAdapter;
import com.cars.manager.views.others.BusinessInfoHeader;
import com.cars.manager.views.others.BusinessInfoHeader.DoSearchShanghuListener;
import com.cars.manager.views.popupwindows.MyTipsBuilder;
import com.cars.manager.views.popupwindows.UpdateBusinessBuilder;
import com.cars.manager.views.widgts.pullToRefresh.PullToRefreshFragmentListView;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;

/**
 * @author chengyanfang
 * 
 *         商户页
 * */
public class BusinessFragment extends PullToRefreshFragmentListView implements OnItemClickListener, OnClickListener, NetDataCallBack, DoSearchShanghuListener {
	BusinessListAdapter mAdapter;

	ArrayList<Bisnessse> aDatas = new ArrayList<Bisnessse>();

	ArrayList<Bisnessse> mDatas = new ArrayList<Bisnessse>();

	boolean mHasSelectOne = false;

	int mSelectedPosition = -1;

	LinearLayout mOperationLayout;

	Handler mHandler;

	TextView mBusiness_op1, mBusiness_op2, mBusiness_op3, mBusiness_op4, mBusiness_op5;

	int mCurrentPage = 1, mTotalPages;

	UpdateBusinessBuilder mDelBuilder;

	boolean mIsSearching = false;

	String mSId = "", mManagerId = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_bussiness_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mView = getView();

		mActivity = getActivity();

		mHandler = new Handler(mActivity.getMainLooper());

		initUI();
	}

	@Override
	protected void initUI() {
		super.initUI();
		BusinessInfoHeader aHeaderView = new BusinessInfoHeader(mActivity);
		aHeaderView.setmDoSearchShanghuListener(this);
		mListView.addHeaderView(aHeaderView.getView());

		ImageView aFooterView = new ImageView(mActivity);
		mListView.addFooterView(aFooterView);

		mListView.setOnItemClickListener(this);

		/* operations */
		mOperationLayout = (LinearLayout) mView.findViewById(R.id.ll_business_operation_layout);

		mBusiness_op1 = (TextView) mView.findViewById(R.id.tv_business_op_1);
		mBusiness_op2 = (TextView) mView.findViewById(R.id.tv_business_op_2);
		mBusiness_op3 = (TextView) mView.findViewById(R.id.tv_business_op_3);
		mBusiness_op4 = (TextView) mView.findViewById(R.id.tv_business_op_4);
		mBusiness_op5 = (TextView) mView.findViewById(R.id.tv_business_op_5);

		mBusiness_op1.setOnClickListener(this);
		mBusiness_op2.setOnClickListener(this);
		mBusiness_op3.setOnClickListener(this);
		mBusiness_op4.setOnClickListener(this);
		mBusiness_op5.setOnClickListener(this);
	}

	public void requestData() {
		showProgressHUD(mActivity, getString(R.string.pull_to_refresh_getmore));
		NetworkPrompt.requestShanghu(mActivity, this, mSId, mManagerId, mCurrentPage + "");
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
	public void onResult(final ResponseData aData) {

		dismissProgressHUD();

		/* 商户列表 */
		if (aData instanceof ShanghuResponse) {
			final ShanghuResponse aResponse = (ShanghuResponse) aData;
			aDatas = aResponse.mBisnessses;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					loadData();
				}
			});
		}

		/* 删除商户 */
		if (aData instanceof DelBusinessResponse) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					mDatas.remove(mSelectedPosition);
					mSelectedPosition = -1;
					mHasSelectOne = false;
					showOperationLay(false);
					mAdapter.notifyDataSetChanged();
				}
			});
		}

		/* 获取更新商户信息 */
		if (aData instanceof UpdateBusinessInfoResponse) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					UpdateBusinessInfoResponse res = (UpdateBusinessInfoResponse) aData;

					mDelBuilder = new UpdateBusinessBuilder(mActivity, mView, res.mBisnessse, BusinessFragment.this);
					mDelBuilder.show();
				}
			});
		}
		/* 保存商户信息 */
		if (aData instanceof UpdateBusinessResponse) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					UpdateBusinessResponse res = (UpdateBusinessResponse) aData;
					refresh();
					mSelectedPosition = -1;
					mHasSelectOne = false;
					showOperationLay(false);
					SuperToast.show(res.tips, mActivity);
				}
			});
		}

		/* 无车辆更新信息 */
		if (aData instanceof NoCarUpdateResponse) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					NoCarUpdateResponse res = (NoCarUpdateResponse) aData;
					SuperToast.show(res.tips, mActivity);
				}
			});
		}
	}

	private void loadData() {

		onRefreshComplete();

		/* 搜索中获取数据 */
		if (mIsSearching) {
			mDatas.clear();

			mSelectedPosition = -1;

			mHasSelectOne = false;

			showOperationLay(false);

			mDatas.addAll(aDatas);

			loadAdapter();

			SuperToast.show(getString(R.string.search_complete), mActivity);

			mIsSearching = false;

			return;
		}

		/* 正常加载 上下拉获取数据 */
		if (!aDatas.isEmpty()) {

			if (mCurrentPage == 1) {// 刷新清除
				mDatas.clear();

				mSelectedPosition = -1;

				mHasSelectOne = false;

				showOperationLay(false);
			}

			mDatas.addAll(aDatas);

			loadAdapter();

		} else {
			SuperToast.show(getString(R.string.data_load_complete), mActivity);
			return;
		}
	}

	private void loadAdapter() {
		if (mAdapter == null) {
			mAdapter = new BusinessListAdapter(mActivity, mDatas);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int aposition, long id) {

		int position = aposition - 2;

		if (mHasSelectOne) {
			// 去掉上次选中的item
			mDatas.get(mSelectedPosition).setSelected(false);
			mAdapter.updateAdapter(mDatas);

			// 点击已经选中的item取消后返回
			if (mSelectedPosition == position) {
				mHasSelectOne = false;
				showOperationLay(false);
				return;
			}
		}
		mDatas.get(position).setSelected(true);
		mAdapter.updateAdapter(mDatas);
		mHasSelectOne = true;
		mSelectedPosition = position;

		showOperationLay(true);
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
		Intent aIntent = new Intent(mActivity, PublishCarActivity.class);
		aIntent.putExtra("bussinessId", mDatas.get(mSelectedPosition).getSid());
		mActivity.startActivity(aIntent);
	}

	/* 下架车辆 */
	public void onOperation2Click() {
		if (mOnOperation2ClickListener != null) {
			mOnOperation2ClickListener.OnClickOpeartion2(mDatas.get(mSelectedPosition).getSid(), mDatas.get(mSelectedPosition).getSname());
		}
	}

	/* 商户无车源更新 */
	public void onOperation3Click() {
		showProgressHUD(mActivity, getString(R.string.operating));
		NetworkPrompt.requestNoCarUpdate(mActivity, this, mDatas.get(mSelectedPosition).getSid());
	}

	/* 更新用户 */
	public void onOperation4Click() {
		showProgressHUD(mActivity, getString(R.string.operating), false);
		NetworkPrompt.requestUpdateBusinessInfo(mActivity, this, mDatas.get(mSelectedPosition).getSid());
	}

	/* 删除用户 */
	public void onOperation5Click() {
		final MyTipsBuilder aBuilder = new MyTipsBuilder(mActivity, mView).setTitle(getString(R.string.wenxintishi))
				.setMessage(getString(R.string.querenshanchu)).setLeftBtText(getString(R.string.queding)).setRightBtText(getString(R.string.quxiao));

		aBuilder.setLeftBtClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showProgressHUD(mActivity, mActivity.getString(R.string.operating));
				NetworkPrompt.requestDelBusiness(mActivity, BusinessFragment.this, UserInfoManager.getUuid(mActivity), mDatas.get(mSelectedPosition).getSid());
				aBuilder.dismiss();
			}
		}).setRightBtClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				aBuilder.dismiss();
			}
		});

		aBuilder.show();
	}

	/* 点击保存商户信息 */
	private void onClickSaveBusinessInfo() {
		NetworkPrompt.requestUpdateBusines(mActivity, BusinessFragment.this, mDelBuilder.getToSavedBisness());
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
		case R.id.tv_business_op_4:
			onOperation4Click();
			break;
		case R.id.tv_business_op_5:
			onOperation5Click();
			break;
		case R.id.tv_base_bottom_bar_right:
			onClickSaveBusinessInfo();
			break;
		default:
			break;
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

	@Override
	public void onDoSearchShangHu(String managerId, String sId) {
		if (mIsSearching)
			return;
		mIsSearching = true;
		mSId = sId;
		mManagerId = managerId;
		refresh();
	}

	public interface OnOperation2ClickListener {
		public void OnClickOpeartion2(String gysId, String gysName);
	}

	OnOperation2ClickListener mOnOperation2ClickListener;

	public void setmOnOperation2ClickListener(OnOperation2ClickListener mOnOperation2ClickListener) {
		this.mOnOperation2ClickListener = mOnOperation2ClickListener;
	}
}
