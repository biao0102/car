package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cars.manager.db.table.Gongyingshang;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class GongyingshangAdapter extends BaseListAdapter<Gongyingshang> {

	DisplayImageOptions actOptions;

	boolean mShowItemId = true;

	public GongyingshangAdapter(Context context, List<Gongyingshang> mData, boolean showItemId) {
		super(context, mData);
		this.mShowItemId = showItemId;
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_chexi_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final Gongyingshang aGongyingshang = this.mDatas.get(position);
		ChexingHodler aHodler = (ChexingHodler) holder;
		if (mShowItemId) {
			aHodler.mChexiName.setText(aGongyingshang.getGYSandID());
		} else {
			aHodler.mChexiName.setText(aGongyingshang.getName());
		}
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

	public void updateData(List<Gongyingshang> aData) {
		this.mDatas = aData;
		notifyDataSetChanged();
	}

}
