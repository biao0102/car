package com.cars.manager.views.popupwindows;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.application.App;
import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Pinpais;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.adapters.CarTypesAdapter;
import com.cars.manager.views.adapters.PinpaiAdapter;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class CarTypeBuilder implements OnClickListener, OnChildClickListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	ExpandableListView mExpandableListView;

	ImageView mCloseImg;

	TextView mTitle;

	CarTypesAdapter mAdapter;

	/* 搜索框及搜索结果列表 */
	EditText mSearchkey;

	RelativeLayout mSearchBtLay;

	ListView mSearchResultList;

	PinpaiAdapter mSearchAdapter;

	ArrayList<Pinpais> searchResults = new ArrayList<Pinpais>();

	ArrayList<String> mGroupList = new ArrayList<String>();

	ArrayList<ArrayList<Pinpais>> mChlidList = new ArrayList<ArrayList<Pinpais>>();

	Pinpais emptyPinPai;

	public CarTypeBuilder(Activity context, View view) {
		mContext = context;
		this.view = view;
		initUI();

		initData();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_cartypies_builder_layout, null);

		mCloseImg = (ImageView) mView.findViewById(R.id.iv_builder_close);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);

		mExpandableListView = (ExpandableListView) mView.findViewById(R.id.exlist_cartypies_layout);
		mExpandableListView.setGroupIndicator(null);

		addHeaderView();

		mCloseImg.setOnClickListener(this);

		mExpandableListView.setOnChildClickListener(this);

		/* 搜索框及搜索结果列表 */
		mSearchkey = (EditText) mView.findViewById(R.id.edt_pinpai_id);

		mSearchBtLay = (RelativeLayout) mView.findViewById(R.id.rl_searchimg_layout);

		mSearchResultList = (ListView) mView.findViewById(R.id.list_cartypies_layout);

		mSearchResultList.setOnItemClickListener(this);

		mSearchBtLay.setOnClickListener(this);

		mSearchkey.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				String searchKey = s.toString();

				if (s.length() == 0) {
					showSearchList(false);
				} else if (isLetter(searchKey)) {// 按字母搜索
					doSearchWithCharcter(searchKey, false);
				} else {// 按名称搜索
					doSearchWithName(searchKey, false);
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
	}

	private void addHeaderView() {
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView text = new TextView(mContext);
		text.setLayoutParams(layoutParams);
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		text.setPadding(18, 0, 0, 0);
		text.setText("无");
		text.setTextSize(16);
		text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOnPinpaiSelectedListener != null) {
					mOnPinpaiSelectedListener.onSelected(emptyPinPai);
				}

				CarTypeBuilder.this.dismiss();
			}
		});
		mExpandableListView.addHeaderView(text);
	}

	private void initData() {

		emptyPinPai = new Pinpais();

		emptyPinPai.setName(mContext.getString(R.string.pinpai));
		emptyPinPai.setId("");

		mGroupList.addAll(App.getCharaterList());

		for (int i = 0; i < mGroupList.size(); i++) {
			mChlidList.add((App.pinpaiMap.get(mGroupList.get(i))));
		}

		mAdapter = new CarTypesAdapter(mContext, mGroupList, mChlidList);

		mExpandableListView.setAdapter(mAdapter);

		for (int i = 0; i < mAdapter.getGroupCount(); i++) {

			mExpandableListView.expandGroup(i);

		}
	}

	public CarTypeBuilder setTitle(String title) {
		mTitle.setText(title);
		return this;
	}

	public void show() {
		alertDialog = new Dialog(mContext, R.style.shareDialog);

		alertDialog.setContentView(mView,
				new LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 20), AppUtil.getHeight(mContext) - AppUtil.dip2px(mContext, 80)));

		alertDialog.getWindow().setGravity(Gravity.CENTER);

		alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.qhq_alert_transparent_bg);

		alertDialog.setCanceledOnTouchOutside(true);

		alertDialog.show();

		alertDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				AppUtil.hideSoftInput(mContext, mView);
			}
		});
	}

	public void dismiss() {
		if (alertDialog != null)
			alertDialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_builder_close:
			this.dismiss();
			break;
		case R.id.rl_searchimg_layout:
			onClickSearch();
			break;
		default:
			break;
		}
	}

	/**
	 * aData.getString("name")
	 * */

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		if (mOnPinpaiSelectedListener != null) {
			mOnPinpaiSelectedListener.onSelected(mChlidList.get(groupPosition).get(childPosition));
		}

		this.dismiss();

		return true;
	}

	public interface OnPinpaiSelectedListener {
		public void onSelected(Pinpais aPinpai);
	}

	OnPinpaiSelectedListener mOnPinpaiSelectedListener;

	public CarTypeBuilder setmOnPinpaiSelectedListener(OnPinpaiSelectedListener mOnPinpaiSelectedListener) {
		this.mOnPinpaiSelectedListener = mOnPinpaiSelectedListener;
		return this;
	}

	/* 点击搜索 */
	private void onClickSearch() {
		String keyword = mSearchkey.getText().toString().trim();
		if (TextUtil.isEmpty(keyword)) {
			SuperToast.show(mContext.getString(R.string.shuru_searchkey), mContext);
		} else if (isLetter(keyword)) {// 按字母搜索
			doSearchWithCharcter(keyword, true);
		} else {// 按名称搜索
			doSearchWithName(keyword, true);
		}
	}

	/* 按名称搜索 */
	private void doSearchWithName(String name, boolean isFromBt) {

		List<Pinpais> result = new DBHelper(mContext).selectPinpaisiWithName(name);

		if (result.isEmpty() && isFromBt) {
			SuperToast.show(mContext.getString(R.string.no_pinpai), mContext);
		} else {
			searchResults.clear();
			searchResults.addAll(result);
			loadSearchResultAdapter();
			showSearchList(true);
		}
	}

	/* 按字母搜索 */
	private void doSearchWithCharcter(String name, boolean isFromBt) {

		searchResults.clear();

		searchResults.addAll(new DBHelper(mContext).selectPinpaisiWithFirstChar(Character.toUpperCase(name.toCharArray()[0]) + ""));

		if (searchResults.isEmpty() && isFromBt) {
			SuperToast.show(mContext.getString(R.string.no_pinpai), mContext);
		} else {
			loadSearchResultAdapter();
			showSearchList(true);
		}
	}

	/* 是否是字母 */
	private boolean isLetter(String keyword) {

		if (keyword.length() == 1) {
			char[] chars = keyword.toCharArray();
			if (chars[0] >= 'A' && chars[0] <= 'Z' || chars[0] >= 'a' && chars[0] <= 'z') {
				return true;
			}
		}
		return false;
	}

	private void loadSearchResultAdapter() {
		if (mSearchAdapter == null) {
			mSearchAdapter = new PinpaiAdapter(mContext, searchResults);
			mSearchResultList.setAdapter(mSearchAdapter);
		} else {
			mSearchAdapter.notifyDataSetChanged();
		}
	}

	private void showSearchList(boolean isShow) {
		if (isShow) {
			mSearchResultList.setVisibility(View.VISIBLE);
			mExpandableListView.setVisibility(View.GONE);
		} else {
			mSearchResultList.setVisibility(View.GONE);
			mExpandableListView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mOnPinpaiSelectedListener != null) {
			mOnPinpaiSelectedListener.onSelected(searchResults.get(position));
		}

		this.dismiss();
	}
}
