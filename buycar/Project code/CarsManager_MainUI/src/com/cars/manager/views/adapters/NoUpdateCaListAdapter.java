package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.managers.Config;
import com.cars.manager.managers.DisplayOptionManager;
import com.cars.manager.utils.AppUtil;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NoUpdateCaListAdapter extends BaseListAdapter<UpdateCarInfo> {

	DisplayImageOptions actOptions;

	public NoUpdateCaListAdapter(Context context, List<UpdateCarInfo> mData) {
		super(context, mData);
		options = DisplayOptionManager.getRoundedDisplayOptions(R.drawable.car_photo_2);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_test;
		// return R.layout.adapter_homecars_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final UpdateCarInfo aCarResource = this.mDatas.get(position);
		CarInfoHodler aHodler = (CarInfoHodler) holder;

		// 车身正前方
		LayoutParams lps = (LayoutParams) aHodler.mCarPhoto.getLayoutParams();
		lps.width = (AppUtil.getWidth(mContext) - 56) / 4;
		lps.height = (lps.width * 76) / 144;
		aHodler.mCarPhoto.setLayoutParams(lps);

		ImageLoader.getInstance().displayImage(aCarResource.getZhengqianfangImg(), aHodler.mCarPhoto, options);

		// 车名
		aHodler.mCarName.setText(aCarResource.getBrandName());

		// 报价
		aHodler.mCarPrice.setText(mContext.getString(R.string.car_price, aCarResource.getJizhiPrice()));

		// 车型
		aHodler.mCarType.setText(aCarResource.getBrandName());

		// 车系
		aHodler.mCarXinghao.setText(aCarResource.getSeriesName());

		// 入库时间
		aHodler.mCarRukuTime.setText(mContext.getString(R.string.car_ruku, aCarResource.getFirstSignDate()));

		// 车型
		aHodler.mCarSize.setText(aCarResource.getTypeName());

		// 上牌时间
		aHodler.mCarShangpaiTime.setText(mContext.getString(R.string.car_shangpai, aCarResource.getFirstSignDate()));

		// 已跑公里数
		aHodler.mCarRunLenght.setText(mContext.getString(R.string.car_lucheng, aCarResource.getTravlledDistance()));

		// 销售状态
		/* 规则：上传新的时候你就显示为待审核就行了，我后台是默认添加为待审核状态的，如果是更新的车辆，本来是什么就显示成什么 */

		if (aCarResource.getSource().equals(Config.NEW_ADD)) {
			aHodler.mCarSellState.setText(mContext.getString(R.string.daishenhe));
		} else {
			aHodler.mCarSellState.setText(aCarResource.getStatusName());
		}

		// 地址
		aHodler.mCarLocation.setText(aCarResource.getSupplierName());

		// 完整度
		aHodler.mWanzhengduLayout.setVisibility(View.VISIBLE);
		setWanzhengdu(aCarResource, aHodler);

		// 车辆选择状态
		if (aCarResource.getIsSelected()) {
			aHodler.mSelectIcon.setBackgroundResource(R.drawable.img_business_selected);
		} else {
			aHodler.mSelectIcon.setBackgroundResource(R.drawable.img_business_noselected);
		}

	}

	private void setWanzhengdu(UpdateCarInfo aUpdateCarInfo, CarInfoHodler aHodler) {
		if (aUpdateCarInfo.getWanzhengdu() <= 20) {
			aHodler.mWanzhengdu1.setBackgroundResource(R.drawable.bg_red);
			aHodler.mWanzhengdu2.setBackgroundResource(R.drawable.bg_gray);
			aHodler.mWanzhengdu3.setBackgroundResource(R.drawable.bg_gray);
			aHodler.mWanzhengdu4.setBackgroundResource(R.drawable.bg_gray);
			aHodler.mWanzhengdu5.setBackgroundResource(R.drawable.bg_gray);
		} else if (aUpdateCarInfo.getWanzhengdu() <= 40) {
			aHodler.mWanzhengdu1.setBackgroundResource(R.drawable.bg_red);
			aHodler.mWanzhengdu2.setBackgroundResource(R.drawable.bg_red);
			aHodler.mWanzhengdu3.setBackgroundResource(R.drawable.bg_gray);
			aHodler.mWanzhengdu4.setBackgroundResource(R.drawable.bg_gray);
			aHodler.mWanzhengdu5.setBackgroundResource(R.drawable.bg_gray);
		} else if (aUpdateCarInfo.getWanzhengdu() <= 60) {
			aHodler.mWanzhengdu1.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu2.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu3.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu4.setBackgroundResource(R.drawable.bg_gray);
			aHodler.mWanzhengdu5.setBackgroundResource(R.drawable.bg_gray);
		} else if (aUpdateCarInfo.getWanzhengdu() <= 80) {
			aHodler.mWanzhengdu1.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu2.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu3.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu4.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu5.setBackgroundResource(R.drawable.bg_gray);
		} else {
			aHodler.mWanzhengdu1.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu2.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu3.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu4.setBackgroundResource(R.drawable.bg_blue);
			aHodler.mWanzhengdu5.setBackgroundResource(R.drawable.bg_blue);
		}
	}

	@Override
	protected Holder getViewHolder(View convertView) {
		CarInfoHodler aCarInfoHodler = new CarInfoHodler();

		aCarInfoHodler.mCarPhoto = (ImageView) convertView.findViewById(R.id.iv_adapter_homecars_caricon);

		aCarInfoHodler.mCarName = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_carname);

		aCarInfoHodler.mCarPrice = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_carprice);

		aCarInfoHodler.mCarType = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_carcategory);

		aCarInfoHodler.mCarXinghao = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_carxinghao);

		aCarInfoHodler.mCarRukuTime = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_rukutime);

		aCarInfoHodler.mCarSize = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_carsize);

		aCarInfoHodler.mCarShangpaiTime = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_uploadtime);

		aCarInfoHodler.mCarRunLenght = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_runlength);

		aCarInfoHodler.mCarSellState = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_sellstate);

		aCarInfoHodler.mCarLocation = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_locate);

		aCarInfoHodler.mCarDealPrice = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_chengjiaoprice);

		aCarInfoHodler.mCarDealTime = (TextView) convertView.findViewById(R.id.tv_adapter_homecars_chengjiaotime);

		aCarInfoHodler.mSelectIcon = (ImageView) convertView.findViewById(R.id.iv_selected_icon);

		aCarInfoHodler.mWanzhengduLayout = (RelativeLayout) convertView.findViewById(R.id.wanzhengdu_layout);

		aCarInfoHodler.mWanzhengdu1 = (ImageView) convertView.findViewById(R.id.iv_wanzhengdu_1);
		aCarInfoHodler.mWanzhengdu2 = (ImageView) convertView.findViewById(R.id.iv_wanzhengdu_2);
		aCarInfoHodler.mWanzhengdu3 = (ImageView) convertView.findViewById(R.id.iv_wanzhengdu_3);
		aCarInfoHodler.mWanzhengdu4 = (ImageView) convertView.findViewById(R.id.iv_wanzhengdu_4);
		aCarInfoHodler.mWanzhengdu5 = (ImageView) convertView.findViewById(R.id.iv_wanzhengdu_5);
		return aCarInfoHodler;
	}

	public static class CarInfoHodler implements Holder {
		TextView mCarName;
		TextView mCarPrice;
		TextView mCarType;
		TextView mCarXinghao;
		TextView mCarRukuTime;
		TextView mCarSize;
		TextView mCarShangpaiTime;
		TextView mCarRunLenght;
		TextView mCarSellState;
		TextView mCarLocation;
		TextView mCarDealPrice;
		TextView mCarDealTime;

		ImageView mCarPhoto;
		ImageView mSelectIcon;
		ImageView mWanzhengdu1;
		ImageView mWanzhengdu2;
		ImageView mWanzhengdu3;
		ImageView mWanzhengdu4;
		ImageView mWanzhengdu5;

		RelativeLayout mWanzhengduLayout;
	}

	public void updateAdapter(ListView aListView, boolean isSelected, int index) {
		int firstPosition = aListView.getFirstVisiblePosition();
		int lastPosition = aListView.getLastVisiblePosition();
		if (index < firstPosition && index > lastPosition) {
			return;
		}

		View v = aListView.getChildAt(index - firstPosition);
		if (v == null) {
			return;
		}

		CarInfoHodler mHolder = (CarInfoHodler) v.getTag();
		if (mHolder != null) {
			if (isSelected) {
				mHolder.mSelectIcon.setBackgroundResource(R.drawable.img_business_selected);
			} else
				mHolder.mSelectIcon.setBackgroundResource(R.drawable.img_business_noselected);
		}
	}

	public void updateDatas(List<UpdateCarInfo> aDatas) {
		mDatas = aDatas;
		this.notifyDataSetChanged();
	}

}