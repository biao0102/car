package com.cars.manager.views.popupwindows;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.cars.manager.db.table.Manager;
import com.cars.manager.managers.AnimationManager;
import com.cars.manager.views.adapters.BusinessTypesAdapter;
import com.cars.managers.R;

/**
 * @author chengyanfang
 * 
 */
public class BusinessTypePopupWindow {

	private Context mContext;

	private PopupWindow mPopupWindow;

	private View parent;

	private List<Manager> list;

	private OnDismissListener mDismissListener;

	private OnItemClickListener mItemClickListener;

	public BusinessTypePopupWindow(Context mContext, View parent, List<Manager> list, OnDismissListener mDismissListener, OnItemClickListener mItemClickListener) {
		this.mContext = mContext;
		this.parent = parent;
		this.list = list;
		this.mDismissListener = mDismissListener;
		this.mItemClickListener = mItemClickListener;
		createPopupWindow(parent);
	}

	public void showPopupWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.showAsDropDown(parent);
		}
	}

	public void dismissPopupWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	@SuppressWarnings("deprecation")
	private void createPopupWindow(View parent) {
		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(createMyUI(), parent.getWidth(), LayoutParams.WRAP_CONTENT);
		}
		mPopupWindow.setFocusable(true);// 使其聚集
		mPopupWindow.setOutsideTouchable(true);// 设置是否允许在外点击消失
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());// 响应返回键必须的语句。
		mPopupWindow.setAnimationStyle(AnimationManager.getPopWindowAnimationStyle());
		mPopupWindow.update();
		mPopupWindow.setOnDismissListener(mDismissListener);
		mPopupWindow.showAsDropDown(parent);
	}

	private View createMyUI() {
		View view = View.inflate(mContext, R.layout.view_business_types_pop_listview, null);
		ListView mListView = (ListView) view.findViewById(R.id.fl_forum_pop_listview);
		mListView.setAdapter(new BusinessTypesAdapter(mContext, list));
		mListView.setOnItemClickListener(mItemClickListener);
		return view;
	}
}
