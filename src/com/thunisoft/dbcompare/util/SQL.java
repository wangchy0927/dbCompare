package com.thunisoft.dbcompare.util;

public class SQL {
	
	public final static String MYSQL_GETTABLES_SQL = "SELECT TABLE_NAME from information_schema.`TABLES` where TABLE_SCHEMA ={tableschema}";
	public final static String MYSQL_GETCOLUMNS_SQL = "SELECT COLUMN_NAME,COLUMN_TYPE FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA ={tableschema} and TABLE_NAME={tablename}";
	public final static String MYSQL_TABLESCHEMA = "{tableschema}";
	public final static String MYSQL_TABLENAMA = "{tablename}";
	
	public final static String SYBASE_GETTABLES_SQL = "SELECT name as TABLE_NAME,id as TABLE_ID FROM sysobjects WHERE type = 'U'";
	public final static String SYBASE_GETCOLUMNS_SQL = "SELECT name as COLUMN_NAME,type as COLUMN_TYPE FROM syscolumns WHERE id = {id}";
	public final static String SYBASE_TABLEID = "{id}"; 
}
