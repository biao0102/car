package com.cars.manager.networks.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.db.table.Bisnessse;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UpdateBusinessInfoResponse extends FlResponseBase {

	public Bisnessse mBisnessse;

	public UpdateBusinessInfoResponse(DataCollection dataSource) {
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
				JSONObject dataObject = iRootJsonNode.getJSONObject("data");
				praseBusiness(dataObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void praseBusiness(JSONObject aBusiness) {
		try {
			mBisnessse = new Bisnessse();
			if (aBusiness.has("sid")) {
				mBisnessse.setSid(aBusiness.getString("sid"));
			}
			if (aBusiness.has("sname")) {
				mBisnessse.setSname(aBusiness.getString("sname"));
			}
			if (aBusiness.has("linkName")) {
				mBisnessse.setLinkMan(aBusiness.getString("linkName"));
			}
			if (aBusiness.has("remark")) {
				mBisnessse.setRemark(aBusiness.getString("remark"));
			}
			if (aBusiness.has("address")) {
				mBisnessse.setAddress(aBusiness.getString("address"));
			}
			if (aBusiness.has("managerName")) {
				mBisnessse.setChargeMan(aBusiness.getString("managerName"));
			}
			if (aBusiness.has("cellphone")) {
				mBisnessse.setCellphone(aBusiness.getString("cellphone"));
			}
			if (aBusiness.has("managerId")) {
				mBisnessse.setManagerId(aBusiness.getString("managerId"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
