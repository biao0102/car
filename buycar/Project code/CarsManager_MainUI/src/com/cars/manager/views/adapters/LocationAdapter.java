package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cars.manager.db.table.Locations;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class LocationAdapter extends BaseListAdapter<Locations> {

	DisplayImageOptions actOptions;

	public LocationAdapter(Context context, List<Locations> mData) {
		super(context, mData);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_chexi_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final Locations aLocations = this.mDatas.get(position);
		ChexingHodler aHodler = (ChexingHodler) holder;
		aHodler.mChexiName.setText(aLocations.getCityName());
	}

	@Override
	protected Holder getViewHolder(View convertView) {
		ChexingHodler aChexingHodler = new ChexingHodler();

		aChexingHodler.mChexiName = (TextView) convertView.findViewById(R.id.tv_chexi);

		return aChexingHodler;
	}

	public static class ChexingHodler implements Holder {
		TextView mChexiName;
	}

}
