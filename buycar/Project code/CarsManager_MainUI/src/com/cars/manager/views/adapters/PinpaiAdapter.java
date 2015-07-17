package com.cars.manager.views.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cars.manager.db.table.Pinpais;
import com.cars.managers.R;

public class PinpaiAdapter extends BaseAdapter<Pinpais> {

	public PinpaiAdapter(Activity context, ArrayList<Pinpais> names) {
		super(context, names);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_chexi_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final ViewHolder aHolder = (ViewHolder) holder;
		final Pinpais aPinpais = getItem(position);
		aHolder.mNameView.setText(aPinpais.getName());
	}

	@Override
	protected Holder getViewHolder(View v) {
		ViewHolder vh = new ViewHolder();
		vh.mNameView = (TextView) v.findViewById(R.id.tv_chexi);
		return vh;
	}

	static class ViewHolder implements Holder {
		TextView mNameView;
	}

}
