package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "pinpai")
public class Pinpais {

	private String id;// 品牌ID

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	private String name;// 品牌名称

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private String firstCharacter;// 首字母

	public void setFirstCharacter(String firstCharacter) {
		this.firstCharacter = firstCharacter;
	}

	public String getFirstCharacter() {
		return firstCharacter;
	}

}
