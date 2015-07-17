package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Table;
import com.standard.kit.text.TextUtil;

@Table(name = "bisnessse")
public class Bisnessse {

	private String sid;// 商户ID

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSid() {
		if (TextUtil.isEmpty(sid)) {
			return "暂无";
		}
		return sid;
	}

	private String sname;// 商户名称

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSname() {
		if (TextUtil.isEmpty(sname)) {
			return "暂无";
		}
		return sname;
	}

	private String cellphone;// 电话

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCellphone() {
		if (TextUtil.isEmpty(cellphone)) {
			return "暂无";
		}
		return cellphone;
	}

	private String address;// 地址

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		if (TextUtil.isEmpty(address)) {
			return "暂无";
		}
		return address;
	}

	private String chargeMan;// 负责人

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public String getChargeMan() {
		if (TextUtil.isEmpty(chargeMan)) {
			return "暂无";
		}
		return chargeMan;
	}

	private String managerId;// 负责人ID

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerId() {
		return managerId;
	}

	private String updateTime;// 上次更新时间

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		if (TextUtil.isEmpty(updateTime)) {
			return "暂无";
		}
		return updateTime;
	}

	private boolean selected;

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean getSelected() {
		return selected;
	}

	private String linkMan;// 联系人

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkMan() {
		return linkMan;
	}

	private String remark;// 备注

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

}
