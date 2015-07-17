package com.cars.manager.networks.request;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.application.App;
import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.managers.Config;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlRequestBase;
import com.cars.manager.utils.CompressUtil;
import com.cars.manager.utils.MD5Util;
import com.cars.manager.utils.PhotoUtil;
import com.cars.manager.utils.RecordDataUtils;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.protocolbase.NetDataEngine;
import com.standard.kit.text.TextUtil;

public class UploadChangedCarRequest extends FlRequestBase {

	public UpdateCarInfo mUpdateCarInfo;// 车辆基本信息

	public List<PhotoInfo> mPhotoInfos;// 车辆图片信息

	public String modifyEmp;// 操作人id

	public UploadChangedCarRequest(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected JSONObject getCommandInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put("vehicleNo", mUpdateCarInfo.getVehicleNo());
			holder.put("modifyEmp", modifyEmp);
			holder.put("vin", mUpdateCarInfo.getVin());
			holder.put("supplierId", mUpdateCarInfo.getSupplierId());
			holder.put("brandId", mUpdateCarInfo.getBrandId());
			holder.put("seriesId", mUpdateCarInfo.getSeriesId());
			holder.put("typeId", mUpdateCarInfo.getTypeId());
			holder.put("tabs", mUpdateCarInfo.getTabId());
			holder.put("jizhiPrice", mUpdateCarInfo.getJizhiPrice());
			holder.put("firstSignDate", mUpdateCarInfo.getFirstSignDate());
			holder.put("travlledDistance", mUpdateCarInfo.getTravlledDistance());
			holder.put("color", mUpdateCarInfo.getColor());
			holder.put("files", getPhotoListInfo());

			/* 加密和操作人ID */
			String timeStamp = DateTimeUtil.getCurrentTime();
			holder.put("ts", timeStamp);
			holder.put("verifyString", MD5Util.string2MD5(Config.MD5_KEY + timeStamp));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder;
	}

	/**
	 * 更新保存图像规则： 1.原图像无改变，不进行上传； 2.原图像有改变，上传PhotoId和图像base64字符串
	 * 3.原先没有图像，此时上传type、图像base64字符串、图像名字
	 * */
	private JSONArray getPhotoListInfo() {
		JSONArray mPhotoInfosHolder = new JSONArray();

		for (int i = 0; i < mPhotoInfos.size(); i++) {
			// 判断原先是否有图像
			if (TextUtil.isEmpty(mPhotoInfos.get(i).getPhotoId())) {// 原先无图像
				// 判断是否有新图像上传
				if (!TextUtil.isEmpty(mPhotoInfos.get(i).getSdCardPath())) {// 有新图像
					addNewUploadPhotoInfo(mPhotoInfosHolder, i);
				}
			} else// 原先有图像
			{
				// 判断图像是否修改
				if (!mPhotoInfos.get(i).getSdCardPath().startsWith("http://")) {// 原图像已修改
					addChangedPhotoInfo(mPhotoInfosHolder, i);
				}
			}

		}

		return mPhotoInfosHolder;
	}

	/* 添加新上传的图像 */
	private void addNewUploadPhotoInfo(JSONArray aPhotoInfosHolder, int i) {
		try {
			JSONObject aPhotoInfo = new JSONObject();
			aPhotoInfo.put("type", mPhotoInfos.get(i).getType());
			aPhotoInfo.put("image", PhotoUtil.getBitmapBase64String(App.mApp, mPhotoInfos.get(i).getSdCardPath()));
			aPhotoInfo.put("name", mPhotoInfos.get(i).getIdType() + ".jpg");
			aPhotoInfosHolder.put(aPhotoInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/* 添加修改后的的图像 */
	private void addChangedPhotoInfo(JSONArray aPhotoInfosHolder, int i) {
		try {
			JSONObject aPhotoInfo = new JSONObject();
			aPhotoInfo.put("photoId", mPhotoInfos.get(i).getPhotoId());
			aPhotoInfo.put("image", PhotoUtil.getBitmapBase64String(App.mApp, mPhotoInfos.get(i).getSdCardPath()));
			aPhotoInfosHolder.put(aPhotoInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] getDataBytes() {
		if (NetDataEngine.isRecordLog) {
			RecordDataUtils.requestRecordLog(mAction);
		}
		JSONObject tCommandInfo = getCommandInfo();
		if (tCommandInfo != null) {
			return CompressUtil.compress(tCommandInfo.toString());
		}
		return null;
	}

}
