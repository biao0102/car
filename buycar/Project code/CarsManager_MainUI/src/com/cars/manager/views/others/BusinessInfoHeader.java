package com.cars.manager.views.others;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Manager;
import com.cars.manager.views.popupwindows.BusinessTypePopupWindow;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class BusinessInfoHeader implements OnClickListener, OnDismissListener, OnItemClickListener {

	Context mContext;

	private View mView;

	EditText mShanghuEdit, mBianhaoEdit;

	RelativeLayout mShanghuDropDown, mBianhaoSearch, mShanghuLayout;

	BusinessTypePopupWindow mPopupWindow;

	List<Manager> types = new ArrayList<Manager>();

	public BusinessInfoHeader(Context aContext) {
		this.mContext = aContext;
	}

	public View getView() {
		if (mView == null) {
			LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
			this.mView = aLayoutInflater.inflate(R.layout.view_shanghu_item_header_view, null);
		}

		initUI();

		initData();

		return mView;
	}

	private void initUI() {
		if (mView == null)
			return;

		mShanghuEdit = (EditText) mView.findViewById(R.id.edt_busness_type);
		mBianhaoEdit = (EditText) mView.findViewById(R.id.edt_busness_id);
		mShanghuDropDown = (RelativeLayout) mView.findViewById(R.id.rl_drop_down_layout);
		mBianhaoSearch = (RelativeLayout) mView.findViewById(R.id.rl_search_layout);
		mShanghuLayout = (RelativeLayout) mView.findViewById(R.id.rl_busness_type_layout);

		mShanghuDropDown.setOnClickListener(this);
		mBianhaoSearch.setOnClickListener(this);
	}

	private void initData() {
		types = new DBHelper(mContext).selectManagerAll();

		/* 添加全部负责人 */
		Manager aManager = new Manager();
		aManager.setManagerId("");
		aManager.setManagerName(mContext.getString(R.string.all_bussiness));
		this.types.add(0, aManager);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_drop_down_layout:
			showDropDownBuilder();
			break;
		case R.id.rl_search_layout:
			onSearchClick();
			break;
		default:
			break;
		}
	}

	private void showDropDownBuilder() {

		if (mPopupWindow == null) {
			mPopupWindow = new BusinessTypePopupWindow(mContext, mShanghuLayout, types, this, this);
		} else {
			mPopupWindow.showPopupWindow();
		}
	}

	private void onSearchClick() {
		if (mDoSearchShanghuListener != null) {
			if (TextUtil.isEmpty(mShanghuEdit.getText().toString().trim())) {
				mDoSearchShanghuListener.onDoSearchShangHu("", mBianhaoEdit.getText().toString().trim());
			} else {
				mDoSearchShanghuListener.onDoSearchShangHu(getManagerIdWithTheirName(), mBianhaoEdit.getText().toString().trim());
			}

		}
	}

	/* 根据输入的负责人名称获取负责人ID */
	private String getManagerIdWithTheirName() {

		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).getManagerName().equals(mShanghuEdit.getText().toString().trim())) {
				return types.get(i).getManagerId();
			}
		}

		return "";
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mBianhaoEdit.setText("");
		mShanghuEdit.setText(types.get(position).getManagerName());
		if (mPopupWindow != null) {
			mPopupWindow.dismissPopupWindow();
		}
		if (mDoSearchShanghuListener != null) {
			mDoSearchShanghuListener.onDoSearchShangHu(types.get(position).getManagerId(), "");
		}
	}

	@Override
	public void onDismiss() {

	}

	public interface DoSearchShanghuListener {
		public void onDoSearchShangHu(String managerId, String sId);
	}

	DoSearchShanghuListener mDoSearchShanghuListener;

	public void setmDoSearchShanghuListener(DoSearchShanghuListener mDoSearchShanghuListener) {
		this.mDoSearchShanghuListener = mDoSearchShanghuListener;
	}

}
