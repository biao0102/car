package com.cars.manager.views.popupwindows;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Locations;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.adapters.LocationAdapter;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class LocationBuilder implements OnClickListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	ImageView mCloseImg;

	TextView mTitle;

	LocationAdapter mAdapter;

	ListView mListView;

	EditText mSearchkey;

	RelativeLayout mSearchBtLay;

	ArrayList<Locations> mAllLocationsList = new ArrayList<Locations>();

	List<Locations> mLocationsList = new ArrayList<Locations>();

	public LocationBuilder(Activity context, View view) {
		mContext = context;
		this.view = view;

		initUI();

		initData();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_chexi_builder_layout, null);

		mCloseImg = (ImageView) mView.findViewById(R.id.iv_builder_close);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);

		mListView = (ListView) mView.findViewById(R.id.list_chexi_layout);

		mListView.setOnItemClickListener(this);

		mCloseImg.setOnClickListener(this);

		/* 搜索框及搜索结果列表 */
		mSearchkey = (EditText) mView.findViewById(R.id.edt_pinpai_id);

		mSearchBtLay = (RelativeLayout) mView.findViewById(R.id.rl_searchimg_layout);

		mSearchBtLay.setOnClickListener(this);

		mSearchkey.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() == 0) {
					mAllLocationsList.clear();
					mAllLocationsList.addAll(mLocationsList);
					loadAdapter();
				} else {
					onClickSearch(false);
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

	private void initData() {

		mLocationsList = new DBHelper(mContext).selectLoactionAll();

		Locations nullLocation = new Locations();
		nullLocation.setCityId(UserInfoManager.getCityId(mContext));
		nullLocation.setCityName(mContext.getString(R.string.wu));
		mLocationsList.add(0, nullLocation);

		mAllLocationsList.addAll(mLocationsList);

		loadAdapter();
	}

	private void loadAdapter() {
		if (mAdapter == null) {
			mAdapter = new LocationAdapter(mContext, mAllLocationsList);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	public LocationBuilder setTitle(String title) {
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
	}

	public void dismiss() {
		if (alertDialog != null)
			alertDialog.dismiss();
		
		AppUtil.hideSoftInput(mContext, mView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_builder_close:
			this.dismiss();
			break;
		case R.id.rl_searchimg_layout:
			onClickSearch(true);
			break;
		default:
			break;
		}
		this.alertDialog.dismiss();
	}

	/* 点击搜索 */
	private void onClickSearch(boolean isFromBt) {
		String keyword = mSearchkey.getText().toString().trim();
		if (TextUtil.isEmpty(keyword) && isFromBt) {
			SuperToast.show(mContext.getString(R.string.shuru_searchkey), mContext);
		} else {// 按名称搜索
			doSearchWithName(keyword, isFromBt);
		}
	}

	/* 按名称搜索 */
	private void doSearchWithName(String name, boolean isFromBt) {
		ArrayList<Locations> mResult = searchLocationWithName(name);
		if (mResult.isEmpty() && isFromBt) {
			SuperToast.show(mContext.getString(R.string.no_pinpai), mContext);
		} else {
			mAllLocationsList.clear();
			mAllLocationsList.addAll(mResult);
			loadAdapter();
		}
	}

	private ArrayList<Locations> searchLocationWithName(String name) {
		ArrayList<Locations> mResult = new ArrayList<Locations>();
		for (int i = 0; i < mLocationsList.size(); i++) {
			if (mLocationsList.get(i).getCityName().contains(name)) {
				mResult.add(mLocationsList.get(i));
			}
		}
		return mResult;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mOnLocationSelectedListener != null) {
			mOnLocationSelectedListener.onSelected(mAllLocationsList.get(position));
			this.dismiss();
		}
	}

	public interface OnLocationSelectedListener {
		public void onSelected(Locations aLocations);
	}

	OnLocationSelectedListener mOnLocationSelectedListener;

	public LocationBuilder setOnLocationSelectedListener(OnLocationSelectedListener aOnLocationSelectedListener) {
		this.mOnLocationSelectedListener = aOnLocationSelectedListener;
		return this;
	}
}
