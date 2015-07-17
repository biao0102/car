package com.cars.manager.db.table;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "tabs")
public class Tabs {

	@Id
	private String tabid;

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	public String getTabid() {
		return tabid;
	}

	private String tabname;

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public String getTabname() {
		return tabname;
	}

	private boolean selected = false;

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
