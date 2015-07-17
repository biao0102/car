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
import com.cars.manager.db.table.Gongyingshang;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.adapters.GongyingshangAdapter;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class GongyingshangBuilder implements OnClickListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	ImageView mCloseImg;

	TextView mTitle;

	GongyingshangAdapter mAdapter;

	ListView mListView;

	List<Gongyingshang> mAllGongyingshangList = new ArrayList<Gongyingshang>();

	List<Gongyingshang> mGongyingshangList = new ArrayList<Gongyingshang>();
	/* 搜索的商户 */
	List<Gongyingshang> mResultGYSs = new ArrayList<Gongyingshang>();

	EditText mSearchId;

	RelativeLayout mSearchLayout;

	boolean mShowItemId = true;

	Gongyingshang nullGongyingshang;

	public GongyingshangBuilder(Activity context, View view) {
		mContext = context;
		this.view = view;

		initUI();

		initData();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_gys_builder_layout, null);

		mCloseImg = (ImageView) mView.findViewById(R.id.iv_builder_close);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);

		mListView = (ListView) mView.findViewById(R.id.list_chexi_layout);

		mSearchId = (EditText) mView.findViewById(R.id.edt_search_id);

		mSearchLayout = (RelativeLayout) mView.findViewById(R.id.rl_search_click_layout);

		mListView.setOnItemClickListener(this);

		mCloseImg.setOnClickListener(this);

		mSearchLayout.setOnClickListener(this);

		mSearchId.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() == 0) {
					mAllGongyingshangList.clear();
					mAllGongyingshangList.addAll(mGongyingshangList);
					loadAdapter();
				} else {
					mResultGYSs.clear();
					mResultGYSs.add(nullGongyingshang);

					mResultGYSs.addAll(new DBHelper(mContext).selectGongyingshangWithName(s.toString()));
					loadSearchedList();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void initData() {
		mGongyingshangList = new DBHelper(mContext).selectGYSAll();

		nullGongyingshang = new Gongyingshang();
		nullGongyingshang.setId("");
		nullGongyingshang.setName("无");
		mGongyingshangList.add(0, nullGongyingshang);

		mAllGongyingshangList.addAll(mGongyingshangList);
		loadAdapter();
	}

	private void loadAdapter() {
		if (mAdapter == null) {
			mAdapter = new GongyingshangAdapter(mContext, mAllGongyingshangList, mShowItemId);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	public GongyingshangBuilder setTitle(String title) {
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
		case R.id.rl_search_click_layout:
			doSearchWithId();
			break;
		default:
			break;
		}
	}

	/* 根据ID搜索 */
	private void doSearchWithId() {
		if (TextUtil.isEmpty(mSearchId.getText().toString().trim())) {
			SuperToast.show(mContext.getString(R.string.shuru_shid), mContext);
			return;
		}

		String keyId = mSearchId.getText().toString().trim();
		mSearchId.setText("");

		if (checkIsNum(keyId)) {// 按商户ID检索
			mResultGYSs.clear();
			mResultGYSs.add(nullGongyingshang);
			mResultGYSs.add(new DBHelper(mContext).selectGongyingshangWithId(keyId));
		} else {// 按商户名称检索
			mResultGYSs.clear();
			mResultGYSs.add(nullGongyingshang);
			mResultGYSs.addAll(new DBHelper(mContext).selectGongyingshangWithName(keyId));
		}
		loadSearchedList();
	}

	private void loadSearchedList() {
		mAllGongyingshangList.clear();
		mAllGongyingshangList.addAll(mResultGYSs);
		mAdapter.updateData(mAllGongyingshangList);
	}

	private boolean checkIsNum(String string) {
		return string.matches("[0-9]+");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mOnGYSSelectedListener != null) {
			mOnGYSSelectedListener.onSelected(mAllGongyingshangList.get(position));
			this.dismiss();
		}
	}

	public interface OnGYSSelectedListener {
		public void onSelected(Gongyingshang aGongyingshang);
	}

	OnGYSSelectedListener mOnGYSSelectedListener;

	public GongyingshangBuilder setOnGYSSelectedListener(OnGYSSelectedListener aOnGYSSelectedListener) {
		this.mOnGYSSelectedListener = aOnGYSSelectedListener;
		return this;
	}

	public GongyingshangBuilder setShowListId(boolean show) {
		this.mShowItemId = show;
		return this;
	}
}
