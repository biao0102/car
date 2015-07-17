package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Table;
import com.standard.kit.text.TextUtil;

@Table(name = "gongyingshang")
public class Gongyingshang {
	private String id;// 品牌ID

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getGYSandID() {
		if (TextUtil.isEmpty(id)) {
			return name;
		} else {
			return id + "——" + name;
		}
	}
}
