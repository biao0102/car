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

public class LoginRequest extends FlRequestBase {

	public String loginName;
	public String password;

	public LoginRequest(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected JSONObject getCommandInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put("loginName", loginName);
			holder.put("password", password);
			String timeStamp = DateTimeUtil.getCurrentTime();
			holder.put("ts", timeStamp);
			holder.put("verifyString", MD5Util.string2MD5(Config.MD5_KEY + timeStamp));
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
