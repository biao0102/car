package com.cars.manager.networks.httpnetworks;

import org.json.JSONObject;

import com.standard.kit.protocolbase.RequestData;

public abstract class FlRequestBase extends RequestData {

	public DataCollection dataSource = null;

	/**
	 * 构造器
	 * 
	 * @param dataSource
	 */
	public FlRequestBase(DataCollection dataSource) {
		this.dataSource = dataSource;
	}

	protected abstract JSONObject getCommandInfo();

	@Override
	public String getServerUrl() {
		return this.mUrl;
	}

	@Override
	public byte[] encode() {
//		 return SimpleCipher.ecryptAfterCompressData(getDataBytes());
		return getDataBytes();
	}

	@Override
	public byte[] getDataBytes() {
		return null;
	}

}
