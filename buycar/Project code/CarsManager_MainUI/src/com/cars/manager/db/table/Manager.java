package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "manager")
public class Manager {

	@Id
	private String managerId;

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerId() {
		return managerId;
	}

	private String managerName;

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return managerName;
	}
}
