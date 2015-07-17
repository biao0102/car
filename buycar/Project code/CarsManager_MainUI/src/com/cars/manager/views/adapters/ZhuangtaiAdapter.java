package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cars.manager.db.table.Zhuangtais;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ZhuangtaiAdapter extends BaseListAdapter<Zhuangtais> {

	DisplayImageOptions actOptions;

	public ZhuangtaiAdapter(Context context, List<Zhuangtais> mData) {
		super(context, mData);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_chexi_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final Zhuangtais aZhuangtais = this.mDatas.get(position);
		ChexingHodler aHodler = (ChexingHodler) holder;
		aHodler.mChexiName.setText(aZhuangtais.getStatusValue());
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
