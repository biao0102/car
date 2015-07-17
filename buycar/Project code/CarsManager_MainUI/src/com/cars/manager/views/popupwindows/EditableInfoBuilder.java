package com.cars.manager.views.popupwindows;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class EditableInfoBuilder implements OnClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	TextView mTitle, mLeftBt, mRightBt;

	EditText mMessage;

	RelativeLayout mLeftRelayout, mRightRelayout;

	OnClickListener mLeftBtClickListener, mRightBtClickListener;

	public EditableInfoBuilder(Activity context, View view) {
		mContext = context;
		this.view = view;
		initUI();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_editable_info_builder_layout, null);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);
		mMessage = (EditText) mView.findViewById(R.id.tv_builder_message);
		mLeftBt = (TextView) mView.findViewById(R.id.tv_left_tab);
		mRightBt = (TextView) mView.findViewById(R.id.tv_right_tab);

		mLeftRelayout = (RelativeLayout) mView.findViewById(R.id.rl_left_tab);
		mRightRelayout = (RelativeLayout) mView.findViewById(R.id.rl_right_tab);
		mLeftRelayout.setOnClickListener(this);
		mRightRelayout.setOnClickListener(this);
	}

	public EditableInfoBuilder setTitle(String title) {
		mTitle.setText(title);
		return this;
	}

	public EditableInfoBuilder setMessage(String message) {
		if (!message.equals(mTitle.getText().toString().trim())) {
			mMessage.setText(message);
			mMessage.setSelection(message.length());
		}
		return this;
	}

	/**
	 * @param defaultTitle
	 *            :当为True时，输入框为空返回Title,否则返回空
	 * */
	public String getMessage(boolean defaultTitle) {
		if (TextUtil.isEmpty(mMessage.getText().toString().trim())) {
			if (defaultTitle) {
				return mTitle.getText().toString().trim();
			} else {
				return "";
			}
		} else {
			return mMessage.getText().toString().trim();
		}
	}

	/**
	 * 只允许输入数字
	 * */
	public EditableInfoBuilder setMessageDigitalOnly() {

		mMessage.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable edt) {
				try {
					String temp = edt.toString();

					if (TextUtil.isEmpty(temp)) {
						return;
					}

					if (!isDigital(edt.toString())) {
						SuperToast.show("格式有误", mContext);
						int len = edt.toString().length();
						edt.delete(len - 1, len);
					}

					int posDot = temp.indexOf(".");
					if (posDot <= 0)
						return;
					if (temp.length() - posDot - 1 > 2) {
						edt.delete(posDot + 3, posDot + 4);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		return this;
	}

	/**
	 * 不能包含汉字
	 * */
	public EditableInfoBuilder setMessageNoChinese() {
		mMessage.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					if (!isLetter(s.toString())) {
						SuperToast.show("不能包含汉字", mContext);
						mMessage.setText(mMessage.getText().toString().substring(0, mMessage.getText().toString().length() - count));
						mMessage.setSelection(mMessage.getText().toString().length());
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		return this;
	}

	public EditableInfoBuilder setLeftBtText(String btText) {
		mLeftBt.setText(btText);
		return this;
	}

	public EditableInfoBuilder setRightBtText(String btText) {
		mRightBt.setText(btText);
		return this;
	}

	public EditableInfoBuilder setLeftBtClickListener(OnClickListener aOnClickListener) {
		this.mLeftBtClickListener = aOnClickListener;
		return this;
	}

	public EditableInfoBuilder setRightBtClickListener(OnClickListener aOnClickListener) {
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
				this.dismiss();
			}
			break;
		case R.id.rl_right_tab:
			if (mRightBtClickListener != null) {
				mRightBtClickListener.onClick(v);
				AppUtil.hideSoftInput(mContext, mMessage);
				this.dismiss();
			}
			break;
		default:
			break;
		}
	}

	public static boolean isLetter(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}

	public static boolean isDigital(String str) {
		String regex = "^[0-9.]+$";
		return str.matches(regex);
	}
}
