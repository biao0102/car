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

public class UpdateCarResourceRequest extends FlRequestBase {

	public String vehicleNo;// 车辆编号
	public String modifyEmp;// 操作人
	public String supplierId;// 商户编号
	public String status;// 状态:状态
	public String saleStatus;// 销售状态
	public String bargainPrice;// 成交价
	public String bargainTime;// 成交时间
	public String saleChannel;// 经销商

	public UpdateCarResourceRequest(DataCollection dataSource) {
		super(dataSource);
	}

	@Override
	protected JSONObject getCommandInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put("vehicleNo", vehicleNo);
			holder.put("modifyEmp", modifyEmp);
			holder.put("supplierId", supplierId);
			holder.put("status", status);
			holder.put("saleStatus", saleStatus);
			holder.put("bargainPrice", bargainPrice);
			holder.put("bargainTime", bargainTime);
			holder.put("saleChannel", saleChannel);

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
			if (tCommandInfo != null) {
				return tCommandInfo.toString().getBytes("UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
