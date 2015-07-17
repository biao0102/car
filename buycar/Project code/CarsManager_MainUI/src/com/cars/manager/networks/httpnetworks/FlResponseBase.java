package com.cars.manager.networks.httpnetworks;

import org.json.JSONException;
import org.json.JSONObject;

import com.cars.manager.utils.RecordDataUtils;
import com.standard.kit.protocolbase.NetDataEngine;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.secret.SimpleCipher;

public abstract class FlResponseBase extends ResponseData {

	/**
	 * 执行JSON解析的对象
	 */
	protected JSONObject iRootJsonNode = null;

	protected DataCollection iDataSource = null;

	public FlResponseBase(DataCollection dataSource) {
		this.iDataSource = dataSource;
	}

	@Override
	public byte[] decode(byte[] aSecretData) {
		storeCache(aSecretData);
		byte[] pdata = null;
		try {
			pdata = SimpleCipher.decompressAfterdecrypt(SimpleCipher.cancelHead(aSecretData));
		} catch (Exception e1) {
			pdata = null;
		}
		return pdata;
	}

	@Override
	public void parse(byte[] aData) {
		iRootJsonNode = initJsonObject(aData);
		if (null == iRootJsonNode) {
			return;
		}
		try {
			fetchCommand();
			fetchResult();
			fetchData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (NetDataEngine.isRecordLog) {
			RecordDataUtils.responseRecordLog(command);
		}
	}

	protected abstract void fetchData();

	protected void fetchCommand() throws JSONException {
		if (iRootJsonNode.has("command")) {
			command = iRootJsonNode.getString("command");
		}
	}

	protected void fetchResult() throws JSONException {
		if (iRootJsonNode.has("result")) {
			JSONObject resultObject = iRootJsonNode.getJSONObject("result");
			if (resultObject != null) {
				code = resultObject.getInt("code");
				tips = resultObject.getString("tips");
			}
		}
	}

}
