package com.thunisoft.dbcompare.bean;

import java.util.HashSet;
import java.util.Set;

public class Table {
	
	private String name;
	private Set<Column> columnList = new HashSet<Column>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Column> getColumnList() {
		return columnList;
	}
	public void setColumnList(Set<Column> columnList) {
		this.columnList = columnList;
	}
}
