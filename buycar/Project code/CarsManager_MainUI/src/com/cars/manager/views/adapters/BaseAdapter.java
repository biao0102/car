package com.cars.manager.views.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cars.manager.views.widgts.progressHudView.ProgressHUD;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public abstract class BaseAdapter<T> extends ArrayAdapter<T> {

	protected DisplayImageOptions options;

	protected ArrayList<T> mDatas = new ArrayList<T>();
	protected Context mContext;

	protected LayoutInflater mLayoutInflater;

	private ProgressHUD mProgressHUD;

	public BaseAdapter(Context context, ArrayList<T> mDatas) {
		super(context, 0);
		this.mContext = context;
		this.mDatas = mDatas;

		mLayoutInflater = LayoutInflater.from(mContext);
	}

	protected void setDefaultDrawable(int resId) {
	}

	@Override
	public int getCount() {
		if (mDatas == null) {
			return 0;
		}
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView);
	}

	protected View getView(final int position, View convertView) {
		final Holder aHolder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(getResourceId(), null);
			aHolder = (Holder) getViewHolder(convertView);
			convertView.setTag(aHolder);
		} else {
			aHolder = (Holder) convertView.getTag();
		}
		setViewData(convertView, aHolder, position);
		return convertView;
	}

	protected void showProgressHUD() {
		mProgressHUD = ProgressHUD.show(mContext, mContext.getString(R.string.app_name), true, null);
	}

	protected void dismissProgressHUD() {
		mProgressHUD.dismiss();
	}

	protected abstract int getResourceId();

	protected abstract void setViewData(View convertView, Holder holder, int position);

	protected abstract Holder getViewHolder(View convertView);

	public void cleanViewMap() {
		this.mDatas.clear();
	}

	public interface Holder {

	}

	final protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		}
	};

	protected void ShowTips(final String tips) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SuperToast.show(tips, mContext);
			}
		});
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null) {
			super.unregisterDataSetObserver(observer);
		}
	}

	// *******************以下两个成员主要就是为了实现触摸用户头像时变暗的效果****************//

	public void changeLight(View imageView, int brightness) {
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness,// 改变亮度
				0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		ImageView imageViewtemp = (ImageView) imageView;
		imageViewtemp.setColorFilter(new ColorMatrixColorFilter(cMatrix));
	}

	public OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			// 按下时变暗显示，松开和取消触摸时正常显示
			case MotionEvent.ACTION_UP:
				changeLight(view, 0);
				break;
			case MotionEvent.ACTION_DOWN:
				changeLight(view, -50);
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_CANCEL:
				changeLight(view, 0);
				break;
			default:
				break;
			}
			return false;
		}
	};

}
