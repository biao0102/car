package com.cars.manager.views.popupwindows;

import java.util.ArrayList;

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

import com.cars.manager.db.chexingdb.SerachService;
import com.cars.manager.db.table.Chexing;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.adapters.ChexingAdapter;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class ChexingBuilder implements OnClickListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	ImageView mCloseImg;

	TextView mTitle;

	ChexingAdapter mAdapter;

	ListView mListView;

	/* 搜索框及搜索结果列表 */
	EditText mSearchkey;

	RelativeLayout mSearchBtLay;

	ArrayList<Chexing> mAllChexingList = new ArrayList<Chexing>();

	ArrayList<Chexing> mChexingList = new ArrayList<Chexing>();

	String mPinpaiId;

	public ChexingBuilder(Activity context, View view, String aPinpaiId) {
		mContext = context;
		this.view = view;

		mPinpaiId = aPinpaiId;

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
					mAllChexingList.clear();
					mAllChexingList.addAll(mChexingList);
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
		mChexingList = new SerachService(mContext).getChexingsWithBrandId(mPinpaiId);

		Chexing emptyChexing = new Chexing();
		emptyChexing.setId("");
		emptyChexing.setName("无");
		mChexingList.add(0, emptyChexing);

		mAllChexingList.clear();
		mAllChexingList.addAll(mChexingList);
		loadAdapter();
	}

	private void loadAdapter() {
		if (mAdapter == null) {
			mAdapter = new ChexingAdapter(mContext, mAllChexingList);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	public ChexingBuilder setTitle(String title) {
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
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mOnChexingSelectedListener != null) {
			mOnChexingSelectedListener.onSelected(mAllChexingList.get(position));
			this.dismiss();
		}
	}

	public interface OnChexingSelectedListener {
		public void onSelected(Chexing aChexing);
	}

	OnChexingSelectedListener mOnChexingSelectedListener;

	public ChexingBuilder setmOnChexingSelectedListener(OnChexingSelectedListener mOnChexingSelectedListener) {
		this.mOnChexingSelectedListener = mOnChexingSelectedListener;
		return this;
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
		ArrayList<Chexing> mResult = searchChexingWithName(name);
		if (mResult.isEmpty() && isFromBt) {
			SuperToast.show(mContext.getString(R.string.no_pinpai), mContext);
		} else {
			mAllChexingList.clear();
			mAllChexingList.addAll(mResult);
			loadAdapter();
		}
	}

	private ArrayList<Chexing> searchChexingWithName(String name) {
		ArrayList<Chexing> mResult = new ArrayList<Chexing>();
		for (int i = 0; i < mChexingList.size(); i++) {
			if (mChexingList.get(i).getName().contains(name)) {
				mResult.add(mChexingList.get(i));
			}
		}
		return mResult;
	}
}
