package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cars.manager.db.table.Tabs;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class TabsAdapter extends BaseListAdapter<Tabs> {

	DisplayImageOptions actOptions;

	public TabsAdapter(Context context, List<Tabs> mData) {
		super(context, mData);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_tabs_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final Tabs aTabs = this.mDatas.get(position);
		ChexingHodler aHodler = (ChexingHodler) holder;
		aHodler.mTabName.setText(aTabs.getTabname());

		if (aTabs.isSelected()) {
			aHodler.mSelectedImg.setBackgroundResource(R.drawable.img_switch_selected);
		} else {
			aHodler.mSelectedImg.setBackgroundResource(R.drawable.img_switch_no_selected);
		}
	}

	@Override
	protected Holder getViewHolder(View convertView) {
		ChexingHodler aChexingHodler = new ChexingHodler();

		aChexingHodler.mTabName = (TextView) convertView.findViewById(R.id.tv_tabname);
		aChexingHodler.mSelectedImg = (ImageView) convertView.findViewById(R.id.iv_switch);

		return aChexingHodler;
	}

	public static class ChexingHodler implements Holder {
		TextView mTabName;
		ImageView mSelectedImg;
	}

	public void updateAdapter(List<Tabs> aDatas) {
		this.mDatas = aDatas;
		notifyDataSetChanged();
	}

}
