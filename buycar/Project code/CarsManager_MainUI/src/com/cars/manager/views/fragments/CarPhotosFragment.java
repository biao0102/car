package com.cars.manager.views.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.cars.manager.activities.PublishCarActivity;
import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.managers.CarPhotoManager;
import com.cars.manager.model.CarsImagesInfo;
import com.cars.manager.utils.PhotoUtil;
import com.cars.manager.views.adapters.PublishCarImgsAdapter;
import com.cars.manager.views.adapters.PublishCarImgsAdapter.PhotoDeleteInterface;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;

public class CarPhotosFragment extends Fragment implements OnItemClickListener, PhotoDeleteInterface {

	PublishCarActivity mActivity;

	GridView mGridView;

	PublishCarImgsAdapter mAdapter;

	ArrayList<CarsImagesInfo> mCarImagesList = new ArrayList<CarsImagesInfo>();

	private ArrayList<String> mImageNameList;

	private TypedArray mImageDefaultIconList;

	int mSelectedPosition;

	File mFile;

	Uri mUri;

	public int mWanzhengdu = 0;

	// 是否在删除模式
	boolean mDeleteState = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_publishcar_photo_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mActivity = (PublishCarActivity) getActivity();

		initUI();

		initData();

		CheckDeleteTv();
	}

	private void initUI() {
		mGridView = (GridView) getView().findViewById(R.id.gridview_publish_car_photos);
		mGridView.setOnItemClickListener(this);
	}

	private void initData() {
		if ("1".equals(mActivity.mIsFromUpdae)) {// 车辆的更新
			for (int i = 0; i < 21; i++) {
				CarsImagesInfo aCarsImagesInfo = new CarsImagesInfo();
				aCarsImagesInfo.setDesc("" + (i + 1));
				PhotoInfo aPhotoInfo = CarPhotoManager.getPhotoInfoWithIndex(mActivity.mForUpLoadCarInfo.getFiles(), i);
				aCarsImagesInfo.setPath(aPhotoInfo == null ? "" : aPhotoInfo.getUrl());
				aCarsImagesInfo.setPhotoId(aPhotoInfo == null ? "" : aPhotoInfo.getPhotoId());
				mCarImagesList.add(aCarsImagesInfo);
			}
		} else if ("1".equals(mActivity.mIsFromChange)) {// 对新增的修改
			for (int i = 0; i < 21; i++) {
				CarsImagesInfo aCarsImagesInfo = new CarsImagesInfo();
				aCarsImagesInfo.setDesc("" + (i + 1));
				PhotoInfo aPhotoInfo = CarPhotoManager.getPhotoInfoWithIndex(mActivity.mPhotoInfos, i);
				aCarsImagesInfo.setPath(aPhotoInfo == null ? "" : aPhotoInfo.getSdCardPath());
				mCarImagesList.add(aCarsImagesInfo);
			}
		} else {// 新增
			for (int i = 0; i < 21; i++) {
				CarsImagesInfo aCarsImagesInfo = new CarsImagesInfo();
				aCarsImagesInfo.setDesc("" + (i + 1));
				aCarsImagesInfo.setPath("");
				mCarImagesList.add(aCarsImagesInfo);
			}
		}

		mImageNameList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.photo_type_name)));
		mImageDefaultIconList = getResources().obtainTypedArray(R.array.photo_default_img);

		setAdapter();
	}

	public void changeDeleteState() {
		if (mDeleteState) {// 完成删除操作
			mDeleteState = false;
			mActivity.setRightTv(getString(R.string.title_shanchu));
		} else {
			mDeleteState = true;
			mActivity.setRightTv(getString(R.string.title_wancheng));
		}

		if (mAdapter != null) {
			mAdapter.setCanDelete(mDeleteState);
			mAdapter.refreshData(mCarImagesList);
		}
	}

	private void CheckDeleteTv() {
		if (mCarImagesList.isEmpty()) {
			setDeleteTv(false);
			return;
		}

		for (int i = 0; i < mCarImagesList.size(); i++) {
			if (!TextUtils.isEmpty(mCarImagesList.get(i).getPath())) {
				setDeleteTv(true);
				return;
			}
		}

		setDeleteTv(false);
	}

	/* 设置删除按钮 */

	private void setDeleteTv(boolean canDelete) {
		if (canDelete) {
			mActivity.setRightTvColor(getResources().getColor(R.color.white));
			mActivity.setRightTvEnable(true);
		} else {

			mDeleteState = false;

			if (mAdapter != null) {
				mAdapter.setCanDelete(mDeleteState);
			}

			mActivity.setRightTv(mActivity.getString(R.string.title_shanchu));
			mActivity.setRightTvColor(mActivity.getResources().getColor(R.color.light_gray));
			mActivity.setRightTvEnable(false);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mDeleteState) {
			SuperToast.show("请先完成删除模式", mActivity);
			return;
		}

		mSelectedPosition = position;

		mFile = new File(mActivity.getExternalFilesDir(null), System.currentTimeMillis() + ".jpg");
		mUri = Uri.fromFile(mFile);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
		startActivityForResult(intent, 2);
	}

	@Override
	public void onPhotoDelete(int index) {
		mCarImagesList.get(index).setPath("");
		mAdapter.refreshData(mCarImagesList);
		CheckDeleteTv();
	}

	private void setAdapter() {
		if (mAdapter == null) {
			mAdapter = new PublishCarImgsAdapter(mActivity, mCarImagesList, mImageNameList, mImageDefaultIconList);
			mAdapter.setmDeleteInterface(this);
			mGridView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}

	}

	@SuppressWarnings("static-access")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == mActivity.RESULT_OK) {
			if (requestCode == 2) {// 拍照回调
				PhotoUtil.startToCropPhoto(mActivity, mUri);
			} else {// 剪裁回调
				String url = "file://" + mUri.getPath();
				mCarImagesList.get(mSelectedPosition).setPath(url);
				mAdapter.refreshData(mCarImagesList);
				setDeleteTv(true);
			}
		} else if (requestCode == 3) {// 剪裁异常提示
			SuperToast.show(getString(R.string.crop_img_error_tip), mActivity);
		}
	}

	public void setmActivity(PublishCarActivity mActivity) {
		this.mActivity = mActivity;
	}

	/* 保存数据,用于上传 */
	public void saveCarInfo(String aPhotoInfoId) {
		for (int i = 0; i < 21; i++) {
			PhotoInfo aPhotoInfo = new PhotoInfo();

			if (!TextUtils.isEmpty(mCarImagesList.get(i).getPath())) {
				mWanzhengdu++;
			}

			aPhotoInfo.setSdCardPath(mCarImagesList.get(i).getPath());

			aPhotoInfo.setType(CarPhotoManager.photoTypes.get(i));

			aPhotoInfo.setPhotoInfoId(aPhotoInfoId);

			aPhotoInfo.setPhotoId(mCarImagesList.get(i).getPhotoId());

			aPhotoInfo.setIdType(aPhotoInfoId + CarPhotoManager.photoTypes.get(i));

			new DBHelper(mActivity).addUpdateCarPhoto(aPhotoInfo);
		}

		mWanzhengdu = (mWanzhengdu * 100) / 42;

	}

	/* 获取正前方图片 */
	public String getZhengqianfangImg() {
		return mCarImagesList.get(1).getPath();
	}

}
