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

public class CarResourceListRequest extends FlRequestBase {

	public String cityId;// 城市ID
	public String currentPage;// 页数
	public String brandId;// 品牌ID
	public String seriesId;// 车系ID
	public String typeId;// 车型ID
	public String status;// 状态ID
	public String vin;// VIN码
	public String vehicleNo;// 车源编号
	public String supplierId;// 供应商

	public String modifyEmp;

	public CarResourceListRequest(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected JSONObject getCommandInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put("cityId", cityId);
			holder.put("currentPage", currentPage);
			holder.put("limit", 10);
			holder.put("brandId", brandId);
			holder.put("seriesId", seriesId);
			holder.put("typeId", typeId);
			holder.put("status", status);
			holder.put("vin", vin);
			holder.put("vehicleNo", vehicleNo);
			holder.put("supplierId", supplierId);

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
