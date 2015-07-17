package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "yanse")
public class Yanse {

	@Id
	private String color;

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	private String colorName;

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorName() {
		return colorName;
	}
}
