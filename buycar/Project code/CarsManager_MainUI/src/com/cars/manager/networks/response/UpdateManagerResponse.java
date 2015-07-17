package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.Manager;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UpdateManagerResponse extends FlResponseBase {

	public ArrayList<Manager> mManagers = new ArrayList<Manager>();

	public UpdateManagerResponse(DataCollection dataSource) {
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
				JSONArray list = iRootJsonNode.getJSONArray("data");
				praseBusinessList(list);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void praseBusinessList(JSONArray aList) {
		for (int i = 0; i < aList.length(); i++) {
			try {
				JSONObject aData = (JSONObject) aList.get(i);
				Manager aManager = new Manager();
				aManager.setManagerId(aData.getString("managerId"));
				aManager.setManagerName(aData.getString("managerName"));
				mManagers.add(aManager);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
