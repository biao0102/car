package com.cars.manager.views.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cars.manager.db.table.Bisnessse;
import com.cars.managers.R;

public class BusinessListAdapter extends BaseAdapter<Bisnessse> {

	public BusinessListAdapter(Context context, ArrayList<Bisnessse> mDatas) {
		super(context, mDatas);
	}

	@Override
	protected int getResourceId() {
		return R.layout.view_shanghu_item_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final Bisnessse aBusinessItem = this.mDatas.get(position);
		BusinessViewHolder aHodler = (BusinessViewHolder) holder;

		aHodler.tvId.setText(aBusinessItem.getSid());
		aHodler.tvName.setText(aBusinessItem.getSname());
		aHodler.tvTel.setText(aBusinessItem.getCellphone());
		aHodler.tvChargeMan.setText(aBusinessItem.getChargeMan());
		aHodler.tvUpdateTime.setText(aBusinessItem.getUpdateTime());
		aHodler.tvAddress.setText(aBusinessItem.getAddress());

		if (aBusinessItem.getSelected()) {
			aHodler.ivSeletedImg.setBackgroundResource(R.drawable.img_business_selected);
		} else {
			aHodler.ivSeletedImg.setBackgroundResource(R.drawable.img_business_noselected);
		}

	}

	@Override
	protected Holder getViewHolder(View convertView) {
		BusinessViewHolder aBusinessViewHolder = new BusinessViewHolder();

		aBusinessViewHolder.tvId = (TextView) convertView.findViewById(R.id.text_2);
		aBusinessViewHolder.tvName = (TextView) convertView.findViewById(R.id.text_3);
		aBusinessViewHolder.tvTel = (TextView) convertView.findViewById(R.id.text_4);
		aBusinessViewHolder.tvChargeMan = (TextView) convertView.findViewById(R.id.text_5);
		aBusinessViewHolder.tvUpdateTime = (TextView) convertView.findViewById(R.id.text_6);
		aBusinessViewHolder.tvAddress = (TextView) convertView.findViewById(R.id.text_7);
		aBusinessViewHolder.ivSeletedImg = (ImageView) convertView.findViewById(R.id.text_1);

		return aBusinessViewHolder;
	}

	public static class BusinessViewHolder implements Holder {
		TextView tvId;

		TextView tvName;

		TextView tvTel;

		TextView tvChargeMan;

		TextView tvUpdateTime;

		TextView tvAddress;

		ImageView ivSeletedImg;
	}

	public void updateAdapter(ArrayList<Bisnessse> aDatas) {
		this.mDatas = aDatas;
		notifyDataSetChanged();
	}
}
