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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Tabs;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.adapters.TabsAdapter;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class Tabuilder implements OnClickListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	ImageView mCloseImg;

	EditText mSelectedText;

	RelativeLayout mSureLayout;

	TextView mTitle;

	TabsAdapter mAdapter;

	ListView mListView;

	List<Tabs> mTabsList = new ArrayList<Tabs>();

	ArrayList<Tabs> mSelectedTabs = new ArrayList<Tabs>();

	public Tabuilder(Activity context, View view, ArrayList<Tabs> aSelectedTabs) {
		mContext = context;
		this.view = view;

		this.mSelectedTabs = aSelectedTabs;

		initUI();

		initData();
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_tabs_builder_layout, null);

		mCloseImg = (ImageView) mView.findViewById(R.id.iv_builder_close);

		mSureLayout = (RelativeLayout) mView.findViewById(R.id.rl_searchimg_layout);

		mTitle = (TextView) mView.findViewById(R.id.tv_builder_title);

		mSelectedText = (EditText) mView.findViewById(R.id.edt_pinpai_id);

		mListView = (ListView) mView.findViewById(R.id.list_chexi_layout);

		mListView.setOnItemClickListener(this);

		mCloseImg.setOnClickListener(this);

		mSureLayout.setOnClickListener(this);
	}

	private void initData() {
		mTabsList = new DBHelper(mContext).selectTabsAll();

		if (mTabsList == null || mSelectedTabs == null) {
			return;
		}

		for (int i = 0; i < mSelectedTabs.size(); i++) {
			for (int j = 0; j < mTabsList.size(); j++) {
				if (mSelectedTabs.get(i).getTabid().equals(mTabsList.get(j).getTabid())) {
					mTabsList.get(j).setSelected(true);
				}
			}
		}

		updateSelectedText();

		mAdapter = new TabsAdapter(mContext, mTabsList);
		mListView.setAdapter(mAdapter);
	}

	public Tabuilder setTitle(String title) {
		mTitle.setText(title);
		return this;
	}

	public void show() {
		alertDialog = new Dialog(mContext, R.style.shareDialog);

		alertDialog.setContentView(mView,
				new LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 20), AppUtil.getHeight(mContext) - AppUtil.dip2px(mContext, 80)));

		alertDialog.getWindow().setGravity(Gravity.CENTER);

		alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.qhq_alert_transparent_bg);

		alertDialog.setCanceledOnTouchOutside(false);

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
			break;
		case R.id.rl_searchimg_layout:
			if (mOnClickSureCallback != null) {
				mOnClickSureCallback.onClickSure(getSelectedList());
			}
			this.dismiss();
			break;

		default:
			break;
		}
		this.alertDialog.dismiss();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (mTabsList.get(position).isSelected()) {
			mTabsList.get(position).setSelected(false);
		} else {
			mTabsList.get(position).setSelected(true);
		}

		mAdapter.updateAdapter(mTabsList);

		updateSelectedText();
	}

	private void updateSelectedText() {

		String selectedTabName = "";

		for (int i = 0; i < mTabsList.size(); i++) {
			if (mTabsList.get(i).isSelected()) {
				selectedTabName = selectedTabName + mTabsList.get(i).getTabname() + "、";
			}
		}

		if (TextUtil.isEmpty(selectedTabName)) {
			mSelectedText.setText("");
		} else {
			mSelectedText.setText(selectedTabName.substring(0, selectedTabName.length() - 1));
		}
	}

	private ArrayList<Tabs> getSelectedList() {
		ArrayList<Tabs> selectedList = new ArrayList<Tabs>();
		for (int i = 0; i < mTabsList.size(); i++) {
			if (mTabsList.get(i).isSelected()) {
				selectedList.add(mTabsList.get(i));
			}
		}
		return selectedList;
	}

	public interface OnClickSureCallback {
		public void onClickSure(ArrayList<Tabs> selectedList);
	}

	OnClickSureCallback mOnClickSureCallback;

	public Tabuilder setmOnClickSureCallback(OnClickSureCallback mOnClickSureCallback) {
		this.mOnClickSureCallback = mOnClickSureCallback;
		return this;
	}
}
