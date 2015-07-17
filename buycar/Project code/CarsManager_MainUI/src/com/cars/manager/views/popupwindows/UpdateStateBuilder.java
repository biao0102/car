package com.cars.manager.views.popupwindows;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.fragments.TimePickerFragment;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class UpdateStateBuilder implements OnClickListener, OnDateSetListener {

	Activity mContext;

	Fragment mFragment;

	View mView, view;

	ImageView mYSImg, mWSImg, mQMCImg, mJXSImg;

	RelativeLayout mSellPriceEditlay, mSellTimeEditLay;
	RelativeLayout mSellPriceLayout, mSellTimeLayout, mSellPathLayout;

	TextView mYSText, mWSText, mQMCText, mJXSText;

	EditText mPriceEdit;

	TextView mDateText;

	TextView mCancleLay, mSureLay;

	boolean mXiaoShou = true, mQuMaiChe = true;

	int sellChannel = 0;// 0去买车1经销商

	Dialog alertDialog;

	public UpdateStateBuilder(Activity context, Fragment fragment, View view) {
		mContext = context;
		this.view = view;
		this.mFragment = fragment;
		initUI();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_update_state_builder, null);

		mSellPriceEditlay = (RelativeLayout) mView.findViewById(R.id.rl_sell_price);
		mSellTimeEditLay = (RelativeLayout) mView.findViewById(R.id.rl_sell_time);
		mSellPriceLayout = (RelativeLayout) mView.findViewById(R.id.detail_info_line2);
		mSellTimeLayout = (RelativeLayout) mView.findViewById(R.id.detail_info_line3);
		mSellPathLayout = (RelativeLayout) mView.findViewById(R.id.detail_info_line4);

		mDateText = (TextView) mView.findViewById(R.id.edt_sell_time);
		mPriceEdit = (EditText) mView.findViewById(R.id.edt_sell_price);

		mCancleLay = (TextView) mView.findViewById(R.id.tv_base_cancle_left);
		mSureLay = (TextView) mView.findViewById(R.id.tv_base_cancle_right);

		mYSImg = (ImageView) mView.findViewById(R.id.iv_yishou);
		mWSImg = (ImageView) mView.findViewById(R.id.iv_weishou);
		mQMCImg = (ImageView) mView.findViewById(R.id.iv_qudao);
		mJXSImg = (ImageView) mView.findViewById(R.id.iv_jingxiaoshang);

		mYSText = (TextView) mView.findViewById(R.id.tv_yishou);
		mWSText = (TextView) mView.findViewById(R.id.tv_weishou);
		mQMCText = (TextView) mView.findViewById(R.id.tv_qumaiche);
		mJXSText = (TextView) mView.findViewById(R.id.tv_jingxiaoshang);

		mSellPriceEditlay.setOnClickListener(this);
		mSellTimeEditLay.setOnClickListener(this);
		mYSImg.setOnClickListener(this);
		mWSImg.setOnClickListener(this);
		mQMCImg.setOnClickListener(this);
		mJXSImg.setOnClickListener(this);
		mYSText.setOnClickListener(this);
		mWSText.setOnClickListener(this);
		mQMCText.setOnClickListener(this);
		mJXSText.setOnClickListener(this);
		mCancleLay.setOnClickListener(this);
		mSureLay.setOnClickListener(this);

		changePriceAndTimeLayVisible();
	}

	public UpdateStateBuilder setTitle(String title) {
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
		case R.id.rl_sell_price:// 销售价格
			break;
		case R.id.rl_sell_time:// 销售日期
			TimePickerFragment timePicker = new TimePickerFragment(this);
			timePicker.show(mFragment.getFragmentManager(), "");
			break;
		case R.id.iv_yishou:// 已售
		case R.id.tv_yishou:
			mXiaoShou = true;

			mYSImg.setBackgroundResource(R.drawable.img_selected);
			mWSImg.setBackgroundResource(R.drawable.img_noselected);

			changePriceAndTimeLayVisible();
			break;
		case R.id.iv_weishou:// 未售
		case R.id.tv_weishou:
			mXiaoShou = false;

			mYSImg.setBackgroundResource(R.drawable.img_noselected);
			mWSImg.setBackgroundResource(R.drawable.img_selected);

			changePriceAndTimeLayVisible();
			break;
		case R.id.iv_qudao:// 去买车
		case R.id.tv_qumaiche:
			mQuMaiChe = true;
			mQMCImg.setBackgroundResource(R.drawable.img_selected);
			mJXSImg.setBackgroundResource(R.drawable.img_noselected);
			break;
		case R.id.iv_jingxiaoshang:// 经销商
		case R.id.tv_jingxiaoshang:
			mQuMaiChe = false;
			mQMCImg.setBackgroundResource(R.drawable.img_noselected);
			mJXSImg.setBackgroundResource(R.drawable.img_selected);
			break;
		case R.id.tv_base_cancle_left:// 取消
			this.dismiss();
			break;
		case R.id.tv_base_cancle_right:// 确定
			onClickUpdateBt();
			break;
		default:
			break;
		}
	}

	/* 当修改销售状态，改变价格、时间、渠道布局的显示情况 */
	private void changePriceAndTimeLayVisible() {
		if (mXiaoShou) {
			mSellPriceLayout.setVisibility(View.VISIBLE);
			mSellTimeLayout.setVisibility(View.VISIBLE);
			mSellPathLayout.setVisibility(View.VISIBLE);
		} else {
			mSellPriceLayout.setVisibility(View.GONE);
			mSellTimeLayout.setVisibility(View.GONE);
			mSellPathLayout.setVisibility(View.GONE);
		}
	}

	private void onClickUpdateBt() {
		if (mXiaoShou) {
			if (TextUtil.isEmpty(mPriceEdit.getText().toString().trim()) || TextUtil.isEmpty(mDateText.getText().toString().trim())) {
				SuperToast.show(mContext.getString(R.string.empty_tip), mContext);
				return;
			}
		}

		if (mClickUpdateListener != null)
			mClickUpdateListener.onClickUpdate(mPriceEdit.getText().toString().trim(), mDateText.getText().toString().trim(), mXiaoShou, mQuMaiChe);

		this.dismiss();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mDateText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	}

	public interface OnClickUpdateListener {
		public void onClickUpdate(String price, String time, boolean isSell, boolean isQMC);
	}

	OnClickUpdateListener mClickUpdateListener;

	public void setmClickUpdateListener(OnClickUpdateListener mClickUpdateListener) {
		this.mClickUpdateListener = mClickUpdateListener;
	}
}
