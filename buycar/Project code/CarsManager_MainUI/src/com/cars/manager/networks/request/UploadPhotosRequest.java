package com.cars.manager.networks.request;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.managers.Config;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlRequestBase;
import com.cars.manager.utils.CompressUtil;
import com.cars.manager.utils.MD5Util;
import com.cars.manager.utils.RecordDataUtils;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.protocolbase.NetDataEngine;

public class UploadPhotosRequest extends FlRequestBase {

	public List<PhotoInfo> mPhotoInfos;// 车辆图片信息

	public String vehicleNo;// 车辆id

	public UploadPhotosRequest(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected JSONObject getCommandInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put("vehicleNo", vehicleNo);
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

	private JSONArray getPhotoListInfo() {
		JSONArray mPhotoInfosHolder = new JSONArray();

		for (int i = 0; i < mPhotoInfos.size(); i++) {
			try {
				JSONObject aPhotoInfo = new JSONObject();
				aPhotoInfo.put("type", mPhotoInfos.get(i).getType());
				aPhotoInfo.put("image", mPhotoInfos.get(i).getImage());
				aPhotoInfo.put("name", mPhotoInfos.get(i).getIdType() + ".jpg");
				mPhotoInfosHolder.put(aPhotoInfo);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		return mPhotoInfosHolder;
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
