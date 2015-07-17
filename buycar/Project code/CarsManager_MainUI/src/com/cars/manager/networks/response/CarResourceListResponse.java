package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.CarResource;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class CarResourceListResponse extends FlResponseBase {

	public String currentPage;

	public String totalPages;

	public ArrayList<CarResource> mCarResources = new ArrayList<CarResource>();

	public CarResourceListResponse(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected void fetchData() {

		try {
			if (iRootJsonNode.has("msg"))
				tips = iRootJsonNode.getString("msg");

			if (iRootJsonNode.has("status"))
				code = Integer.parseInt(iRootJsonNode.getString("status"));

			if (iRootJsonNode.has("totalPages"))
				totalPages = iRootJsonNode.getString("totalPages");

			if (iRootJsonNode.has("data")) {
				JSONObject dataObject = iRootJsonNode.getJSONObject("data");
				if (dataObject.has("currentPage"))
					currentPage = dataObject.getString("currentPage");

				if (dataObject.has("list")) {
					JSONArray list = dataObject.getJSONArray("list");
					praseBusinessList(list);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void praseBusinessList(JSONArray aList) {
		for (int i = 0; i < aList.length(); i++) {
			try {
				JSONObject jsonObject = (JSONObject) aList.get(i);
				CarResource aCarResource = new CarResource();
				if (jsonObject.has("addDate")) {
					aCarResource.setAddDate(jsonObject.getString("addDate"));
				}

				if (jsonObject.has("bargainPrice")) {
					aCarResource.setBargainPrice(jsonObject.getString("bargainPrice"));
				}

				if (jsonObject.has("bargainTime")) {
					aCarResource.setBargainTime(jsonObject.getString("bargainTime"));
				}

				if (jsonObject.has("brandName")) {
					aCarResource.setBrandName(jsonObject.getString("brandName"));
				}

				if (jsonObject.has("firstSignDate")) {
					aCarResource.setFirstSignDate(jsonObject.getString("firstSignDate"));
				}

				if (jsonObject.has("jizhiPrice")) {
					aCarResource.setJizhiPrice(jsonObject.getString("jizhiPrice"));
				}

				if (jsonObject.has("seriesName")) {
					aCarResource.setSeriesName(jsonObject.getString("seriesName"));
				}

				if (jsonObject.has("status")) {
					aCarResource.setStatus(jsonObject.getString("status"));
				}

				if (jsonObject.has("statusValue")) {
					aCarResource.setStatusValue(jsonObject.getString("statusValue"));
				}

				if (jsonObject.has("supplierName")) {
					aCarResource.setSupplierName(jsonObject.getString("supplierName"));
				}

				if (jsonObject.has("travlledDistance")) {
					aCarResource.setTravlledDistance(jsonObject.getString("travlledDistance"));
				}

				if (jsonObject.has("typeName")) {
					aCarResource.setTypeName(jsonObject.getString("typeName"));
				}

				if (jsonObject.has("vehicleNo")) {
					aCarResource.setVehicleNo(jsonObject.getString("vehicleNo"));
				}

				if (jsonObject.has("zhengqianfangImage")) {
					aCarResource.setZhengqianfangImage(jsonObject.getString("zhengqianfangImage"));
				}

				if (jsonObject.has("supplierId")) {
					aCarResource.setSupplierId(jsonObject.getString("supplierId"));
				}

				mCarResources.add(aCarResource);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
