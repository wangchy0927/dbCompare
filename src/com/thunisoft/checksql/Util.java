package com.thunisoft.checksql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	
	public static void main(String[] args){
		String selectSql = " 2016-04-18 16:13:33 [WARN ] [qtp2077440619-1605] jdbc.sqltiming - sElect * from teset";
		System.out.println(checkSelectSqlRegex(selectSql));
		
		/*String inSql = "select * from table where id in (1 , '"+2+"' , 3 , 4 , 5 )";
		System.out.println(checkInSqlRegex(inSql,3));*/
	}
	
	
	public static boolean checkSelectSqlRegex(String sql) {
		//String selectReg = ".(?i)select \\*";
		String selectReg = ".*(?i)select \\*";
		Pattern p = Pattern.compile(selectReg);
		if (p.matcher(sql).find())
			return true;
		else
			return false;
	}
	
	public static boolean checkInSqlRegex(String sql,int inNum) {
		String inReg = " (?i)in\\s*[(]([^()]+)[)]";//匹配0或者多个空格
		int inSum = inNum;
		boolean flag = false;
		Pattern p = Pattern.compile(inReg);
		Matcher m = p.matcher(sql);
		while (m.find()) {
			int length = m.group(1).split(",").length;
			if ( length>= inSum) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public static List<SqlLog> getLogListFromFile(File file) {
		List<SqlLog> logList = new ArrayList<SqlLog>();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		String lineTxt = null;
		int lineNum = 0;
		int tempNum = 0;
		int countNum = 0;
		StringBuffer sb = new StringBuffer();
		try {
			read = new InputStreamReader(new FileInputStream(file), "GBK");
			bufferedReader = new BufferedReader(read);
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineNum ++;	
				if(lineTxt.startsWith("201")){
					if(tempNum!=0){
						logList.add(new SqlLog(sb.toString(),lineNum-countNum-1));
					}
					countNum = 0;
					sb = new StringBuffer();
					sb = sb.append(lineTxt);
					tempNum ++;				
				}else if(lineTxt.contains("com.")||lineTxt.contains("org.")||lineTxt.isEmpty()){
					if(!sb.toString().equals("")){
						logList.add(new SqlLog(sb.toString(),lineNum-countNum-1));
					}
					countNum ++;
				}else {
					countNum ++;
					sb = sb.append(lineTxt);
				}			
			}
			logList.add(new SqlLog(sb.toString(),lineNum-countNum-1));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				read.close();
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return logList;
	}

}
