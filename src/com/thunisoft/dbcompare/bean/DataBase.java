package com.thunisoft.dbcompare.bean;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 构造方法里面需要传递参数:<br>
 * 	&nbsp&nbsp&nbsp&nbsp	用户名、密码、链接串和驱动类
 * */
public class DataBase {

	private String user;//用户名
	private String pwd;//密码
	private String url;//链接串
	private String classforName;//驱动类
	private ResourceBundle localResource ;
	private String dbNum;
	

	public DataBase(String dbNum){
		this.dbNum = dbNum;
		this.localResource = getLocalResource();
		this.user = getDBUser();
		this.pwd = getDBPwd();
		this.url = getDBUrl();
		this.classforName = getDBClassforName();
	}
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClassforName() {
		return classforName;
	}
	public void setClassforName(String classforName) {
		this.classforName = classforName;
	}
	
	public  String getDBUrl(){
        return localResource.getString(dbNum+".url");
	}
	public  String getDBClassforName(){
        return localResource.getString("db.classforName");
	}
	public  String getDBUser(){ 
        return localResource.getString(dbNum+".user");
	}
	public  String getDBPwd(){
        return localResource.getString(dbNum+".pwd");
	}
	
	public void setLocalResource(ResourceBundle localResource) {
		this.localResource = localResource;
	}

	private  ResourceBundle getLocalResource(){
		Locale locale = Locale.getDefault();  
        ResourceBundle localResource = ResourceBundle.getBundle("com/thunisoft/dbcompare/dbConfig", locale); 
        return localResource;
	}


	public String getDbNum() {
		return dbNum;
	}


	public void setDbNum(String dbNum) {
		this.dbNum = dbNum;
	}
	
}
