package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UpdateCarResourceInfoResponse extends FlResponseBase {

	public UpdateCarInfo mUpdateCarInfo;

	public UpdateCarResourceInfoResponse(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected void fetchData() {

		try {
			if (iRootJsonNode.has("msg"))
				tips = iRootJsonNode.getString("msg");

			if (iRootJsonNode.has("status"))
				code = Integer.parseInt(iRootJsonNode.getString("status"));

			if (iRootJsonNode.has("data")) {
				mUpdateCarInfo = praseCarInfo(iRootJsonNode.getJSONObject("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private UpdateCarInfo praseCarInfo(JSONObject object) {
		UpdateCarInfo aUpdateCarInfo = new UpdateCarInfo();

		try {
			if (object.has("brandId")) {
				aUpdateCarInfo.setBrandId(object.getString("brandId"));
			}

			if (object.has("color")) {
				aUpdateCarInfo.setColor(object.getString("color"));
			}

			if (object.has("firstSignDate")) {
				aUpdateCarInfo.setFirstSignDate(object.getString("firstSignDate"));
			}

			if (object.has("jizhiPrice")) {
				aUpdateCarInfo.setJizhiPrice(object.getString("jizhiPrice"));
			}

			if (object.has("seriesId")) {
				aUpdateCarInfo.setSeriesId(object.getString("seriesId"));
			}

			if (object.has("supplierId")) {
				aUpdateCarInfo.setSupplierId(object.getString("supplierId"));
			}

			if (object.has("travlledDistance")) {
				aUpdateCarInfo.setTravlledDistance(object.getString("travlledDistance"));
			}

			if (object.has("typeId")) {
				aUpdateCarInfo.setTypeId(object.getString("typeId"));
			}

			if (object.has("vehicleNo")) {
				aUpdateCarInfo.setVehicleNo(object.getString("vehicleNo"));
			}

			if (object.has("vin")) {
				aUpdateCarInfo.setVin(object.getString("vin"));
			}

			if (object.has("tabs")) {
				aUpdateCarInfo.setTabId(object.getString("tabs"));
			}

			if (object.has("status")) {
				aUpdateCarInfo.setStatus(object.getString("status"));
			}

			if (object.has("files")) {
				aUpdateCarInfo.setFiles(prasePhotoFile(object.getJSONArray("files")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return aUpdateCarInfo;
	}

	private ArrayList<PhotoInfo> prasePhotoFile(JSONArray photoes) {
		ArrayList<PhotoInfo> aPhotoInfos = new ArrayList<PhotoInfo>();
		try {
			for (int i = 0; i < photoes.length(); i++) {
				PhotoInfo aPhotoInfo = new PhotoInfo();
				aPhotoInfo.setPhotoId(((JSONObject) photoes.get(i)).getString("photoId"));
				aPhotoInfo.setType(((JSONObject) photoes.get(i)).getString("type"));
				aPhotoInfo.setUrl(((JSONObject) photoes.get(i)).getString("url"));
				aPhotoInfos.add(aPhotoInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return aPhotoInfos;
	}
}
