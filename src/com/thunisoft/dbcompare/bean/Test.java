package com.thunisoft.dbcompare.bean;

public class Test {

	public static void main(String[] args) {
		DataBase db = new DataBase(DBNUM.DBNUM1);
		System.out.println(db.getClassforName());
	}

}
