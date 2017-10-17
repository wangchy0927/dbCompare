package com.thunisoft.checksql;

public class SqlLog {

	private String text;
	private int num;
	

	public SqlLog(String text, int num) {
		super();
		this.text = text;
		this.num = num;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
