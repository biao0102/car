package com.cars.manager.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cars.manager.utils.ButtonUtils;
import com.cars.managers.R;

/**
 * @author chengyanfang
 * 
 *         处理Title元素
 * */
public class BaseTitleActivity extends BaseActivity implements OnClickListener {

	/* Title */
	private TextView mTitleLeftTv;

	private TextView mTitleRightTv;

	private TextView mTitleRightSecondTv;

	private TextView mTitleLeftSecondTv;

	private TextView mTitleTitleTv;

	private ImageView mTitleLeftiv;

	private ImageView mTitleRightiv;

	/* Bottom Bar */
	private LinearLayout mBottomBarLayout;

	private TextView mBottomBarLeftTv;

	private TextView mBottomBarRightTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initTitle() {
		super.initTitle();
		mTitleLeftTv = (TextView) findViewById(R.id.tv_basetitle_lefttext);
		mTitleLeftiv = (ImageView) findViewById(R.id.iv_basetitle_leftimg);
		mTitleRightTv = (TextView) findViewById(R.id.tv_basetitle_righttext);
		mTitleRightSecondTv = (TextView) findViewById(R.id.tv_basetitle_second_righttext);
		mTitleLeftSecondTv = (TextView) findViewById(R.id.tv_basetitle_second_lefttext);
		mTitleRightiv = (ImageView) findViewById(R.id.iv_basetitle_rightimg);
		mTitleTitleTv = (TextView) findViewById(R.id.tv_basetitle_titletext);

		mTitleLeftTv.setOnClickListener(this);
		mTitleLeftiv.setOnClickListener(this);
		mTitleRightTv.setOnClickListener(this);
		mTitleRightSecondTv.setOnClickListener(this);
		mTitleLeftSecondTv.setOnClickListener(this);
		mTitleRightiv.setOnClickListener(this);
	}

	protected void initBottomBar() {
		mBottomBarLayout = (LinearLayout) findViewById(R.id.ll_base_bottom_layou);

		mBottomBarLeftTv = (TextView) findViewById(R.id.tv_base_bottom_bar_left);
		mBottomBarRightTv = (TextView) findViewById(R.id.tv_base_bottom_bar_right);

		mBottomBarLeftTv.setOnClickListener(this);
		mBottomBarRightTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/* Title */
		case R.id.tv_basetitle_lefttext:
			onLeftTvClick();
			break;
		case R.id.iv_basetitle_leftimg:
			onLeftIvClick();
			break;
		case R.id.tv_basetitle_righttext:
			onRightTvClick();
			break;
		case R.id.tv_basetitle_second_righttext:
			onRightSecondTvClick();
			break;
		case R.id.tv_basetitle_second_lefttext:
			onLeftSecondTvClick();
			break;
		case R.id.iv_basetitle_rightimg:
			onRightIvClick();
			break;
		/* Bottom Bar */
		case R.id.tv_base_bottom_bar_left:
			onBottomLeftTvClick();
			break;
		case R.id.tv_base_bottom_bar_right:
			if (!ButtonUtils.isFastDoubleClick()) {
				onBottomRightTvClick();
			}
			break;
		default:
			break;
		}
	}

	/* Title */
	protected void onLeftTvClick() {

	}

	protected void onLeftIvClick() {

	}

	protected void onRightTvClick() {

	}

	protected void onRightSecondTvClick() {

	}

	protected void onLeftSecondTvClick() {

	}

	protected void onRightIvClick() {

	}

	protected void setTitleTv(String titleTv) {
		mTitleTitleTv.setText(titleTv);
	}

	protected void setLeftTv(String leftTv) {
		mTitleLeftTv.setText(leftTv);
	}

	public void setRightTv(String rightTv) {
		mTitleRightTv.setText(rightTv);
	}

	public void setRightSecondTv(String rightTv) {
		mTitleRightSecondTv.setText(rightTv);
	}

	public void setLeftSecondTv(String rightTv) {
		mTitleLeftSecondTv.setText(rightTv);
	}

	public void setRightTvColor(int rightColor) {
		mTitleRightTv.setTextColor(rightColor);
	}

	public void setRightSecondTvColor(int rightColor) {
		mTitleRightSecondTv.setTextColor(rightColor);
	}

	public void setRightTvEnable(boolean rightClickable) {
		mTitleRightTv.setEnabled(rightClickable);
	}

	public void setRightSecondTvEnable(boolean rightClickable) {
		mTitleRightSecondTv.setEnabled(rightClickable);
	}

	public void setLeftIv(int leftIvId) {
		mTitleLeftiv.setBackgroundResource(leftIvId);
	}

	public void setRightIv(int rightIvId) {
		mTitleRightiv.setBackgroundResource(rightIvId);
	}

	public void setTitleLeftTvShow(boolean shouldShow) {
		if (shouldShow) {
			mTitleLeftTv.setVisibility(View.VISIBLE);
		} else {
			mTitleLeftTv.setVisibility(View.GONE);
		}
	}

	public void setTitleLeftIvShow(boolean shouldShow) {
		if (shouldShow) {
			mTitleLeftiv.setVisibility(View.VISIBLE);
		} else {
			mTitleLeftiv.setVisibility(View.GONE);
		}
	}

	protected void setTitleRightTvShow(boolean shouldShow) {
		if (shouldShow) {
			mTitleRightTv.setVisibility(View.VISIBLE);
		} else {
			mTitleRightTv.setVisibility(View.GONE);
		}
	}

	protected void setTitleRightSecondTvShow(boolean shouldShow) {
		if (shouldShow) {
			mTitleRightSecondTv.setVisibility(View.VISIBLE);
		} else {
			mTitleRightSecondTv.setVisibility(View.GONE);
		}
	}

	protected void setTitleRightIvShow(boolean shouldShow) {
		if (shouldShow) {
			mTitleRightiv.setVisibility(View.VISIBLE);
		} else {
			mTitleRightiv.setVisibility(View.GONE);
		}
	}

	/* Bottom Bar */
	protected void onBottomLeftTvClick() {

	}

	protected void onBottomCenterTvClick() {

	}

	protected void onBottomRightTvClick() {

	}

	protected void setBottomLeftTv(String leftTv) {
		mBottomBarLeftTv.setText(leftTv);
	}

	protected void setBottomRightTv(String rightTv) {
		mBottomBarRightTv.setText(rightTv);
	}

	protected void setBottomLeftTvColor(int color) {
		mBottomBarLeftTv.setTextColor(color);
	}

	protected void setBottomRightTvColor(int color) {
		mBottomBarRightTv.setTextColor(color);
	}

	protected void setBottomRightTvClickable(boolean clickable) {
		mBottomBarRightTv.setClickable(clickable);

		if (clickable) {
			mBottomBarRightTv.setTextColor(getResources().getColor(R.color.black));
		} else {
			mBottomBarRightTv.setTextColor(getResources().getColor(R.color.color_cccccc));
		}
	}

	protected void setBottomLeftTvClickable(boolean clickable) {
		mBottomBarLeftTv.setClickable(clickable);

		if (clickable) {
			mBottomBarLeftTv.setTextColor(getResources().getColor(R.color.black));
		} else {
			mBottomBarLeftTv.setTextColor(getResources().getColor(R.color.color_cccccc));
		}
	}

	protected void setBottomVisible(boolean visible) {
		if (visible) {
			mBottomBarLayout.setVisibility(View.VISIBLE);
		} else {
			mBottomBarLayout.setVisibility(View.GONE);
		}
	}

}
