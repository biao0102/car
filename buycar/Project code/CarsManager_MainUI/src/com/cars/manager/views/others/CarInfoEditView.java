package com.cars.manager.views.others;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cars.managers.R;

/**
 * @author chengyanfang
 * 
 *         自定义车辆编辑View
 * */
public class CarInfoEditView {

	Context mContext;

	private View mView;

	/* 信息名称 */
	private TextView mInfoNameTv;

	/* 信息内容编辑框 */
	private EditText mInfoContentEtv;

	/* 内容清除按钮 */
	private ImageView mInfoContentClearIv;

	public CarInfoEditView(Context aContext) {
		this.mContext = aContext;
	}

	public View getView() {
		if (mView == null) {
			LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
			this.mView = aLayoutInflater.inflate(R.layout.view_carinfo_editview, null);
		}

		initUI();

		return mView;
	}

	private void initUI() {
		if (mView == null)
			return;

		mInfoNameTv = (TextView) mView.findViewById(R.id.tv_carinfoEdit_infoname);
		mInfoContentEtv = (EditText) mView.findViewById(R.id.etv_carinfoEdit_infocontent);
		mInfoContentClearIv = (ImageView) mView.findViewById(R.id.iv_carinfoEdit_clearbt);

		/* EditView 监听 */
		mInfoContentEtv.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(mInfoContentEtv.getText().toString())) {
					mInfoContentClearIv.setVisibility(View.GONE);
				} else {
					mInfoContentClearIv.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		/* 清除按钮监听 */
		mInfoContentClearIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mInfoContentEtv.setText("");
			}
		});

	}

	public String getCarInfoName() {
		return mInfoNameTv.getText().toString();
	}

	public void setCarInfoName(String name) {
		mInfoNameTv.setText(name);
	}

	public String getCarInfoContent() {
		return mInfoContentEtv.getText().toString();
	}

	public void setCarInfoContentSelection(int length) {
		mInfoContentEtv.setSelection(length);
	}
}
