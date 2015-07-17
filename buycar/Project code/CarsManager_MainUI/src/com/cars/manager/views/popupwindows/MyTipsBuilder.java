package com.cars.manager.views.popupwindows;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.utils.AppUtil;
import com.cars.managers.R;

public class MyTipsBuilder implements OnClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	TextView mTitle, mMessage, mLeftBt, mRightBt;

	RelativeLayout mLeftRelayout, mRightRelayout;

	OnClickListener mLeftBtClickListener, mRightBtClickListener;

	public MyTipsBuilder(Activity context, View view) {
		mContext = context;
		this.view = view;
		initUI();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_mybuilder_layout, null);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);
		mMessage = (TextView) mView.findViewById(R.id.tv_builder_message);
		mLeftBt = (TextView) mView.findViewById(R.id.tv_left_tab);
		mRightBt = (TextView) mView.findViewById(R.id.tv_right_tab);

		mLeftRelayout = (RelativeLayout) mView.findViewById(R.id.rl_left_tab);
		mRightRelayout = (RelativeLayout) mView.findViewById(R.id.rl_right_tab);
		mLeftRelayout.setOnClickListener(this);
		mRightRelayout.setOnClickListener(this);
	}

	public MyTipsBuilder setTitle(String title) {
		mTitle.setText(title);
		return this;
	}

	public MyTipsBuilder setMessage(String message) {
		mMessage.setText(message);
		return this;
	}

	public MyTipsBuilder setLeftBtText(String btText) {
		mLeftBt.setText(btText);
		return this;
	}

	public MyTipsBuilder setRightBtText(String btText) {
		mRightBt.setText(btText);
		return this;
	}

	public MyTipsBuilder setLeftBtClickListener(OnClickListener aOnClickListener) {
		this.mLeftBtClickListener = aOnClickListener;
		return this;
	}

	public MyTipsBuilder setRightBtClickListener(OnClickListener aOnClickListener) {
		this.mRightBtClickListener = aOnClickListener;
		return this;
	}

	public void show() {
		alertDialog = new Dialog(mContext, R.style.shareDialog);

		alertDialog.setContentView(mView, new LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 100), LayoutParams.WRAP_CONTENT));

		alertDialog.getWindow().setGravity(Gravity.CENTER);

		alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.qhq_alert_transparent_bg);

		alertDialog.setCanceledOnTouchOutside(true);

		alertDialog.show();
	}

	public void dismiss() {
		if (alertDialog != null)
			alertDialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_left_tab:
			if (mLeftBtClickListener != null) {
				mLeftBtClickListener.onClick(v);
			}
			break;
		case R.id.rl_right_tab:
			if (mRightBtClickListener != null) {
				mRightBtClickListener.onClick(v);
			}
			break;
		default:
			break;
		}
		this.alertDialog.dismiss();
	}
}
