package com.cars.manager.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.managers.Config;
import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.networks.response.UpdateCarResponse;
import com.cars.manager.networks.response.UploadNewCarResponse;
import com.cars.manager.utils.CompressUtil;
import com.cars.manager.views.adapters.NoUpdateCaListAdapter;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;

public class NoUpdatedCarsList extends BaseTitleActivity implements OnItemClickListener, NetDataCallBack {

	ListView mListView;

	NoUpdateCaListAdapter mAdapter;

	List<UpdateCarInfo> mDatas = new ArrayList<UpdateCarInfo>();

	List<UpdateCarInfo> mSelectedDatas = new ArrayList<UpdateCarInfo>();

	int totalCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noupdatecars_layout);

		initUI();
		initData();
	}

	@Override
	protected void initUI() {
		super.initUI();
		initTitle();
		initBottomBar();

		mListView = (ListView) findViewById(R.id.listview_noupdate);
		mListView.setOnItemClickListener(this);

		setTitleTv(getString(R.string.title_weishangchuan));
		setLeftIv(R.drawable.img_base_title_back);
		setRightTv(getString(R.string.title_quxuan));
		setLeftSecondTv(getString(R.string.fanhui));

		setBottomVisible(true);
		setBottomLeftTv(getString(R.string.bottombar_kaishishangchuan));
		setBottomRightTv(getString(R.string.bottombar_xiugaicheyuan));

		changeBottomTextState();
	}

	/* 开始上传 */
	@Override
	protected void onBottomLeftTvClick() {
		super.onBottomLeftTvClick();

		totalCount = mSelectedDatas.size();

		selectTheFirstToUpdate();
	}

	/* 选择已选列表的第一个进行上传 */
	private void selectTheFirstToUpdate() {

		final UpdateCarInfo aSelectedCar = mSelectedDatas.get(0);

		try {
			dismissProgressHUD();
			showProgressHUD(NoUpdatedCarsList.this, getString(R.string.uploading, totalCount - mSelectedDatas.size() + 1, totalCount), false);
		} catch (Exception e) {
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				List<PhotoInfo> aSelectedCarPhotos = new DBHelper(NoUpdatedCarsList.this).selectPhotoInfoWithId(aSelectedCar.getTime());

				// 先判断是进行更新保存还是上传
				if (aSelectedCar.getSource().equals(Config.NEW_ADD)) {// 上传

					NetworkPrompt.requestUploadNewCar(NoUpdatedCarsList.this, NoUpdatedCarsList.this, aSelectedCar,
							CompressUtil.setCompressedPhotoInfoList(aSelectedCarPhotos));

				} else {// 更新保存

					NetworkPrompt.requestUploadUpdateCar(NoUpdatedCarsList.this, NoUpdatedCarsList.this, aSelectedCar, aSelectedCarPhotos);

				}
			}
		}).start();

	}

	/* 修改车源 */
	@Override
	protected void onBottomRightTvClick() {
		super.onBottomRightTvClick();
		Intent aIntent = new Intent(this, PublishCarActivity.class);
		aIntent.putExtra("mIsFromChange", "1");
		aIntent.putExtra("carInfo", mSelectedDatas.get(0));
		this.startActivityForResult(aIntent, 3);
	}

	@Override
	protected void onLeftSecondTvClick() {
		this.onLeftIvClick();
	}

	@Override
	protected void onLeftIvClick() {
		super.onLeftIvClick();
		this.finish();
	}

	@Override
	protected void initData() {
		super.initData();
		mDatas = new DBHelper(this).selectUpdateCarInfoWithUuid(UserInfoManager.getUuidAndType(this));
		mAdapter = new NoUpdateCaListAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
		checkUploadAllText();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (mSelectedDatas.remove(mDatas.get(position))) {

			mDatas.get(position).setIsSelected(false);

			mAdapter.updateDatas(mDatas);

			changeBottomTextState();

			checkUploadAllText();

			return;
		}

		mSelectedDatas.add(mDatas.get(position));

		mDatas.get(position).setIsSelected(true);

		changeBottomTextState();

		checkUploadAllText();

		mAdapter.updateDatas(mDatas);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 3 && arg1 == 31) {// 完成更新，更新数据列表
			mSelectedDatas.clear();
			reloadAdapter();
		}
	}

	private void reloadAdapter() {
		mDatas = new DBHelper(this).selectUpdateCarInfoWithUuid(UserInfoManager.getUuidAndType(this));
		mAdapter.updateDatas(mDatas);
		changeBottomTextState();
	}

	private void changeBottomTextState() {
		setBottomRightTvClickable(mSelectedDatas.size() == 1);

		setBottomLeftTvClickable(mSelectedDatas.size() > 0);

		setBottomVisible(mSelectedDatas.size() > 0);
	}

	@Override
	public void onResult(final ResponseData aData) {
		if (aData instanceof UpdateCarResponse) {// 修改车辆

			mHandler.post(new Runnable() {

				@Override
				public void run() {

					dismissProgressHUD();

					SuperToast.show(aData.tips, NoUpdatedCarsList.this);

				}
			});
			updateDBAndDealNextOne();
		} else if (aData instanceof UploadNewCarResponse) {// 上传基本信息
			updateDBAndDealNextOne();
		}

	}

	private void updateDBAndDealNextOne() {

		// 从数据库中删除数据
		new DBHelper(this).deleteUpdateCarInfo(mSelectedDatas.get(0));

		mSelectedDatas.remove(0);

		mHandler.post(new Runnable() {

			@Override
			public void run() {
				reloadAdapter();

				changeBottomTextState();

				// 如果还有数据，继续上传
				if (mSelectedDatas.size() > 0) {
					selectTheFirstToUpdate();
				} else {
					SuperToast.show("上传成功", NoUpdatedCarsList.this);
					dismissProgressHUD();
				}
			}
		});
	}

	@Override
	public void onError(ResponseErrorInfo aErrorInfo) {
		SuperToast.show(aErrorInfo.mErrorTips, this);
		dismissProgressHUD();
	}

	@Override
	public void onProtocolError(ResponseData aData) {
		SuperToast.show(aData.tips, this);
		dismissProgressHUD();
	}

	/* 全选 */
	@Override
	protected void onRightTvClick() {
		super.onRightTvClick();

		if (mDatas.isEmpty())
			return;

		mSelectedDatas.clear();

		mSelectedDatas.addAll(mDatas);

		for (int i = 0; i < mDatas.size(); i++) {
			mDatas.get(i).setIsSelected(true);
		}

		changeBottomTextState();

		checkUploadAllText();

		mAdapter.updateDatas(mDatas);
	}

	private void checkUploadAllText() {
		boolean canUploadAll = false;

		for (int i = 0; i < mDatas.size(); i++) {
			if (!mDatas.get(i).getIsSelected()) {
				canUploadAll = true;
				break;
			}
		}

		if (canUploadAll) {
			setRightTvColor(getResources().getColor(R.color.white));
		} else {
			setRightTvColor(getResources().getColor(R.color.light_gray));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetworkPrompt.requestLogRecord(this, this, mDatas.size() + "", "exit");
	}

}
