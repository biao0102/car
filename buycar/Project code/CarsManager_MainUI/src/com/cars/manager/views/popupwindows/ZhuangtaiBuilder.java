package com.cars.manager.views.popupwindows;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Zhuangtais;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.adapters.ZhuangtaiAdapter;
import com.cars.managers.R;

public class ZhuangtaiBuilder implements OnClickListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	ImageView mCloseImg;

	TextView mTitle;

	RelativeLayout searchLayout;

	ZhuangtaiAdapter mAdapter;

	ListView mListView;

	List<Zhuangtais> mZhuangtaiList = new ArrayList<Zhuangtais>();

	public ZhuangtaiBuilder(Activity context, View view) {
		mContext = context;
		this.view = view;

		initUI();

		initData();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_chexi_builder_layout, null);

		searchLayout = (RelativeLayout) mView.findViewById(R.id.rl_search_layout);

		searchLayout.setVisibility(View.GONE);

		mCloseImg = (ImageView) mView.findViewById(R.id.iv_builder_close);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);

		mListView = (ListView) mView.findViewById(R.id.list_chexi_layout);

		mListView.setOnItemClickListener(this);

		mCloseImg.setOnClickListener(this);
	}

	private void initData() {
		mZhuangtaiList = new DBHelper(mContext).selectZhuangtaisAll();

		Zhuangtais nullZhuanttai = new Zhuangtais();
		nullZhuanttai.setStatus("");
		nullZhuanttai.setStatusValue("无");
		mZhuangtaiList.add(0, nullZhuanttai);

		mAdapter = new ZhuangtaiAdapter(mContext, mZhuangtaiList);
		mListView.setAdapter(mAdapter);
	}

	public ZhuangtaiBuilder setTitle(String title) {
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_builder_close:
			this.dismiss();
		default:
			break;
		}
		this.alertDialog.dismiss();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mOnZhuangtaiSelectedListener != null) {
			mOnZhuangtaiSelectedListener.onSelected(mZhuangtaiList.get(position));
			this.dismiss();
		}
	}

	public interface OnZhuangtaiSelectedListener {
		public void onSelected(Zhuangtais aZhuangtais);
	}

	OnZhuangtaiSelectedListener mOnZhuangtaiSelectedListener;

	public ZhuangtaiBuilder setmOnZhuangtaiSelectedListener(OnZhuangtaiSelectedListener aOnZhuangtaiSelectedListener) {
		this.mOnZhuangtaiSelectedListener = aOnZhuangtaiSelectedListener;
		return this;
	}
}
