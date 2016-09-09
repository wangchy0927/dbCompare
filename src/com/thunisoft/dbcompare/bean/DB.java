package com.thunisoft.dbcompare.bean;

import java.util.Set;

public class DB {

	
	private Set<Table> tableList;
	
	
	public Set<Table> getTableList() {
		return tableList;
	}
	public void setTableList(Set<Table> tableList) {
		this.tableList = tableList;
	}
}
