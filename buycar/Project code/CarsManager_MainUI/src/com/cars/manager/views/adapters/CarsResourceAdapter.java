package com.cars.manager.views.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.cars.manager.db.table.CarResource;
import com.cars.manager.managers.DisplayOptionManager;
import com.cars.manager.utils.AppUtil;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.standard.kit.text.TextUtil;

public class CarsResourceAdapter extends BaseListAdapter<CarResource> {

	DisplayImageOptions actOptions;

	boolean mShowWanzhengdu = false;

	public CarsResourceAdapter(Context context, List<CarResource> mData, boolean showwzd) {
		super(context, mData);
		options = DisplayOptionManager.getRoundedDisplayOptions(R.drawable.car_photo_2);
		this.mShowWanzhengdu = showwzd;
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_test;
		// return R.layout.adapter_homecars_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final CarResource aCarResource = this.mDatas.get(position);
		CarInfoHodler aHodler = (CarInfoHodler) holder;

		// 车身正前方
		LayoutParams lps = (LayoutParams) aHodler.mCarPhoto.getLayoutParams();
		lps.width = (AppUtil.getWidth(mContext) - 56) / 4;
		lps.height = (lps.width * 76) / 144;
		aHodler.mCarPhoto.setLayoutParams(lps);
		ImageLoader.getInstance().displayImage(aCarResource.getZhengqianfangImage(), aHodler.mCarPhoto, options);

		// 车名
		aHodler.mCarName.setText(aCarResource.getBrandName());

		// 报价
		aHodler.mCarPrice.setText(mContext.getString(R.string.car_price, aCarResource.getJizhiPrice()));

		// 车型
		aHodler.mCarType.setText(aCarResource.getBrandName());

		// 车系
		aHodler.mCarXinghao.setText(aCarResource.getSeriesName());

		// 入库时间
		aHodler.mCarRukuTime.setText(mContext.getString(R.string.car_ruku, aCarResource.getAddDate()));

		// 车型
		aHodler.mCarSize.setText(aCarResource.getTypeName());

		// 上牌时间
		aHodler.mCarShangpaiTime.setText(mContext.getString(R.string.car_shangpai, aCarResource.getFirstSignDate()));

		// 已跑公里数
		aHodler.mCarRunLenght.setText(mContext.getString(R.string.car_lucheng, aCarResource.getTravlledDistance()));

		// 销售状态
		aHodler.mCarSellState.setText(aCarResource.getStatusValue());

		// 地址
		aHodler.mCarLocation.setText(aCarResource.getSupplierName());

		// 成交价
		if (!TextUtil.isEmpty(aCarResource.getBargainPrice())) {
			aHodler.mCarDealPrice.setText(mContext.getString(R.string.car_privace_cj, aCarResource.getBargainPrice()));
		} else {
			aHodler.mCarDealPrice.setVisibility(View.GONE);
		}

		// 成交时间
		if (!TextUtil.isEmpty(aCarResource.getBargainTime())) {
			aHodler.mCarDealTime.setText(mContext.getString(R.string.car_time_cj, aCarResource.getBargainTime()));
		} else {
			aHodler.mCarDealTime.setVisibility(View.GONE);
		}
		aHodler.mSelectIcon.setBackgroundResource(R.drawable.img_business_noselected);

		if (mShowWanzhengdu) {
			aHodler.mWanzhengduLayout.setVisibility(View.VISIBLE);
		} else {
			aHodler.mWanzhengduLayout.setVisibility(View.GONE);
		}

		if (aCarResource.getSelected()) {
			aHodler.mSelectIcon.setBackgroundResource(R.drawable.img_business_selected);
		} else {
			aHodler.mSelectIcon.setBackgroundResource(R.drawable.img_business_noselected);
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

	public void updateAdapter(ArrayList<CarResource> aDatas) {
		this.mDatas = aDatas;
		notifyDataSetChanged();
	}

}