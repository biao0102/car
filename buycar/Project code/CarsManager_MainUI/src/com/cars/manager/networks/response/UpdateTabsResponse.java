package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.Tabs;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UpdateTabsResponse extends FlResponseBase {

	public ArrayList<Tabs> mTabs = new ArrayList<Tabs>();

	public UpdateTabsResponse(DataCollection dataSource) {
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
				Tabs aTabs = new Tabs();
				aTabs.setTabid(aData.getString("tab"));
				aTabs.setTabname(aData.getString("tabName"));
				mTabs.add(aTabs);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
