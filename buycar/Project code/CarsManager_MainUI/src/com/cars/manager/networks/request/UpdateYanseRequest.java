package com.cars.manager.networks.request;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.managers.Config;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.httpnetworks.FlRequestBase;
import com.cars.manager.utils.MD5Util;
import com.cars.manager.utils.RecordDataUtils;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.protocolbase.NetDataEngine;

public class UpdateYanseRequest extends FlRequestBase {

	public String modifyEmp;

	public UpdateYanseRequest(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected JSONObject getCommandInfo() {
		JSONObject holder = new JSONObject();
		try {
			/* 加密和操作人ID */
			String timeStamp = DateTimeUtil.getCurrentTime();
			holder.put("ts", timeStamp);
			holder.put("verifyString", MD5Util.string2MD5(Config.MD5_KEY + timeStamp));
			holder.put("modifyEmp", modifyEmp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder;
	}

	@Override
	public byte[] getDataBytes() {
		if (NetDataEngine.isRecordLog) {
			RecordDataUtils.requestRecordLog(mAction);
		}
		try {
			JSONObject tCommandInfo = getCommandInfo();
			return tCommandInfo.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
