package com.cars.manager.networks.response;

import org.json.JSONException;

import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlResponseBase;

public class UploadNewCarResponse extends FlResponseBase {

	public String vehicleNo;

	public UploadNewCarResponse(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected void fetchData() {

		try {
			if (iRootJsonNode.has("msg"))
				tips = iRootJsonNode.getString("msg");
			if (iRootJsonNode.has("vehicleNo"))
				vehicleNo = iRootJsonNode.getString("vehicleNo");
			if (iRootJsonNode.has("status"))
				code = Integer.parseInt(iRootJsonNode.getString("status"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}