package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "cars")
public class Cars {

	private String id;// id

	private String icon;// 图片路径

	private String name;// 名称

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
