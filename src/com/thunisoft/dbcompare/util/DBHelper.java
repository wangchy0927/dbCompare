package com.thunisoft.dbcompare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.thunisoft.dbcompare.bean.DataBase;

/**
 * 数据库工具类,提供自动打开数据库连接、执行Insert和关闭数据库连接操作
 * */
public class DBHelper {
	
	private Connection conn = null;
	private Statement stmt = null;
	private DataBase db = null;
	
	public DBHelper(String dbNum){
		db = new DataBase(dbNum);
	}
	
	/**
	 * 初始化类的时候自动获取数据库链接
	 * */
	public void getConnection(){
		try {
			Class.forName(db.getClassforName()).newInstance();
			if (conn == null || conn.isClosed())
				conn = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPwd());
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println("打开数据库链接失败，请检查数据库连接是否正确:url="+db.getUrl()+" user="+db.getUser()
					+" pwd="+db.getPwd());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 设置手动提交
	 * */
	public void setAutoCommitToFalse(){
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				System.out.println("设置conn.setAutoCommit(false)失败");
				e.printStackTrace();
				System.exit(0);//遇到错误程序直接退出
			}
	}
	
	/**
	 * 手动提交
	 * */
	public void commit(){
		try {
			System.out.println("commit");
			conn.commit();
		} catch (SQLException e) {
			System.out.println("手动提交失败");
			e.printStackTrace();
			System.exit(0);//遇到错误程序直接退出
		}
	}
	
	/**
	 * 对提供的sql执行insert操作
	 * @param sql insert语句
	 * */
	public  ResultSet execQuerySQL(String sql){	
		try {
			//System.out.println(sql);
			//stmt.executeUpdate(sql);
			ResultSet result = stmt.executeQuery(sql);
			return result;
		} catch (Exception e) {
			System.out.println("执行Sql:"+sql+" 失败");
			e.printStackTrace();
			System.exit(0);//遇到错误程序直接退出
		}
		return null;
	}
	
	public String getDBName(){
		String arr[] = db.getUrl().split("/");
		return " '"+arr[arr.length-1]+"'";
	}
	
	/**
	 * 关闭数据库链接
	 * */
	public void closedConn(){
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("执行关闭数据库链接失败");
			e.printStackTrace();
		}
		
	}
}
