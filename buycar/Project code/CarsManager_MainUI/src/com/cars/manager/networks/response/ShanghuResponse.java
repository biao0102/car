package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.Bisnessse;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class ShanghuResponse extends FlResponseBase {

	public String currentPage;

	public String totalPages;

	public ArrayList<Bisnessse> mBisnessses = new ArrayList<Bisnessse>();

	public ShanghuResponse(DataCollection dataSource) {
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
				JSONObject aBusiness = (JSONObject) aList.get(i);
				Bisnessse aBisnessse = new Bisnessse();
				if (aBusiness.has("sid")) {
					aBisnessse.setSid(aBusiness.getString("sid"));
				}
				if (aBusiness.has("sname")) {
					aBisnessse.setSname(aBusiness.getString("sname"));
				}
				if (aBusiness.has("address")) {
					aBisnessse.setAddress(aBusiness.getString("address"));
				}
				if (aBusiness.has("cellphone")) {
					aBisnessse.setCellphone(aBusiness.getString("cellphone"));
				}
				if (aBusiness.has("modifyTime")) {
					aBisnessse.setUpdateTime(aBusiness.getString("modifyTime"));
				}
				if (aBusiness.has("managerName")) {
					aBisnessse.setChargeMan(aBusiness.getString("managerName"));
				}
				if (aBusiness.has("managerId")) {
					aBisnessse.setManagerId(aBusiness.getString("managerId"));
				}
				mBisnessses.add(aBisnessse);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
