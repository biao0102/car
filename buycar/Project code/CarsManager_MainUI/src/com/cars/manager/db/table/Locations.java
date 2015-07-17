package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "location")
public class Locations {

	@Id
	private String cityId;

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityId() {
		return cityId;
	}

	private String cityName;

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}
}
