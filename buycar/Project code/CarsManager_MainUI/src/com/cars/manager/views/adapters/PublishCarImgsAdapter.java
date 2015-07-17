package com.cars.manager.views.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.cars.manager.managers.DisplayOptionManager;
import com.cars.manager.model.CarsImagesInfo;
import com.cars.manager.utils.AppUtil;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author chengyanfang
 * 
 *         发布车辆时图片列表
 * 
 * */
public class PublishCarImgsAdapter extends BaseAdapter<CarsImagesInfo> {

	private boolean canDelete = false;
	private TypedArray mIcons;
	ArrayList<String> mNames;
	int mWidht;
	int mHeith;

	public PublishCarImgsAdapter(Activity context, ArrayList<CarsImagesInfo> mUserGameInfos, ArrayList<String> names, TypedArray icons) {
		super(context, mUserGameInfos);
		options = DisplayOptionManager.getDefaultDisplayOptions(R.drawable.img_photo_default_bg);
		this.mIcons = icons;
		this.mNames = names;
		mWidht = (AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 40)) / 3;
		mHeith = (mWidht * 148) / 222;
	}

	@Override
	protected int getResourceId() {
		return R.layout.adapter_carphotos_item_layout;
	}

	@Override
	protected void setViewData(View convertView, Holder holder, final int position) {
		final ViewHolder aHolder = (ViewHolder) holder;
		final CarsImagesInfo aCarImagsInfo = getItem(position);

		LayoutParams lps = (LayoutParams) aHolder.mIconView.getLayoutParams();
		lps.width = mWidht;
		lps.height = mHeith;
		aHolder.mIconView.setLayoutParams(lps);

		if (TextUtils.isEmpty(aCarImagsInfo.getPath())) {
			aHolder.mIconView.setImageDrawable(mIcons.getDrawable(position));
		} else {
			aHolder.mBufferView.setVisibility(View.GONE);
			ImageLoader.getInstance().displayImage(aCarImagsInfo.getPath(), aHolder.mIconView, options);
		}
		aHolder.mNameView.setText(mNames.get(position));

		/* 删除 */
		if (canDelete && !TextUtils.isEmpty(aCarImagsInfo.getPath())) {
			aHolder.mDeleteView.setVisibility(View.VISIBLE);
		} else {
			aHolder.mDeleteView.setVisibility(View.GONE);
		}
		aHolder.mDeleteView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDeleteInterface != null) {
					mDeleteInterface.onPhotoDelete(position);
				}
			}
		});
	}

	@Override
	protected Holder getViewHolder(View v) {
		ViewHolder vh = new ViewHolder();
		vh.mIconView = (ImageView) v.findViewById(R.id.iv_carsphoto_adapter);
		vh.mBufferView = (ImageView) v.findViewById(R.id.iv_photo_bg);
		vh.mDeleteView = (ImageView) v.findViewById(R.id.iv_delete_photo_bg);
		vh.mNameView = (TextView) v.findViewById(R.id.tv_carsphoto_name);
		return vh;
	}

	static class ViewHolder implements Holder {
		ImageView mIconView;
		ImageView mBufferView;
		ImageView mDeleteView;
		TextView mNameView;
	}

	public void refreshData(ArrayList<CarsImagesInfo> aUserGameInfos) {
		mDatas = aUserGameInfos;
		notifyDataSetChanged();
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	private PhotoDeleteInterface mDeleteInterface;

	public void setmDeleteInterface(PhotoDeleteInterface mDeleteInterface) {
		this.mDeleteInterface = mDeleteInterface;
	}

	public interface PhotoDeleteInterface {
		public void onPhotoDelete(int index);
	}

}
