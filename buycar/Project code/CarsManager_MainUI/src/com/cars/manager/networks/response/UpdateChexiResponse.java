package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.Chexi;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UpdateChexiResponse extends FlResponseBase {

	public ArrayList<Chexi> mChexis = new ArrayList<Chexi>();

	public String mTimeStamp;

	public UpdateChexiResponse(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected void fetchData() {

		try {
			if (iRootJsonNode.has("msg"))
				tips = iRootJsonNode.getString("msg");

			if (iRootJsonNode.has("status"))
				code = Integer.parseInt(iRootJsonNode.getString("status"));

			if (iRootJsonNode.has("timeStamp"))
				mTimeStamp = iRootJsonNode.getString("timeStamp");

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
				Chexi aChexi = new Chexi();
				aChexi.setId(aData.getString("id"));
				aChexi.setName(aData.getString("name"));
				aChexi.setBrandId(aData.getString("brandId"));
				mChexis.add(aChexi);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
