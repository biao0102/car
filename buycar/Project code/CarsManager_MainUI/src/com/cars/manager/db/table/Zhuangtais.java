package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "zhuangtai")
public class Zhuangtais {

	@Id
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	private String statusValue;

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getStatusValue() {
		return statusValue;
	}
}
