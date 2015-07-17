package com.cars.manager.networks.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class LoginResponse extends FlResponseBase {

	public String userId;
	public String cityId;
	/* 用户类型 1:内部用户；2:商户 */
	public String type;

	public LoginResponse(DataCollection dataSource) {
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
				JSONObject result = iRootJsonNode.getJSONObject("data");
				userId = result.getString("userId");
				cityId = result.getString("cityId");
				type = result.getString("type");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
