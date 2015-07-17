package com.cars.manager.views.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cars.manager.db.table.Manager;
import com.cars.managers.R;

public class BusinessTypesAdapter extends BaseListAdapter<Manager> {

	public BusinessTypesAdapter(Context context, List<Manager> mDatas) {
		super(context, mDatas);
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_business_type_list_item;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, int position) {
		ViewHolder viewHolder = (ViewHolder) holder;
		viewHolder.userName.setText(mDatas.get(position).getManagerName());
	}

	@Override
	protected Holder getViewHolder(View convertView) {
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_type_name);
		return viewHolder;
	}

	class ViewHolder implements Holder {
		TextView userName;
	}

}
