package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "chexing")
public class Chexing {
	@Id
	public String id;// 品牌ID

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String brandId;

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandId() {
		return brandId;
	}

	public String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
