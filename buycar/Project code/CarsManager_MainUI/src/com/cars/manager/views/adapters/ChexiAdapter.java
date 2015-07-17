package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cars.manager.db.table.Chexi;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ChexiAdapter extends BaseListAdapter<Chexi> {

	DisplayImageOptions actOptions;

	public ChexiAdapter(Context context, List<Chexi> mData) {
		super(context, mData);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_chexi_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final Chexi aChexi = this.mDatas.get(position);
		ChexiHodler aHodler = (ChexiHodler) holder;
		aHodler.mChexiName.setText(aChexi.getName());
	}

	@Override
	protected Holder getViewHolder(View convertView) {
		ChexiHodler aChexiHodler = new ChexiHodler();

		aChexiHodler.mChexiName = (TextView) convertView.findViewById(R.id.tv_chexi);

		return aChexiHodler;
	}

	public static class ChexiHodler implements Holder {
		TextView mChexiName;
	}

}
