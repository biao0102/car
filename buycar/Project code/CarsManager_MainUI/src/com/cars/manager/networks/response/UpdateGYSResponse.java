package com.cars.manager.networks.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.Gongyingshang;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UpdateGYSResponse extends FlResponseBase {

	public ArrayList<Gongyingshang> mGongyingshangs = new ArrayList<Gongyingshang>();

	public String mTimeStamp;

	public UpdateGYSResponse(DataCollection dataSource) {
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
				Gongyingshang aGongyingshang = new Gongyingshang();
				aGongyingshang.setId(aData.getString("sid"));
				aGongyingshang.setName(aData.getString("sname"));
				mGongyingshangs.add(aGongyingshang);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
