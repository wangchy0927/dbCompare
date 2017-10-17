package com.thunisoft.checksql;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		//String filePath = "D:\\bmkt\\2017\\laxt_jdbc_ip_port.log";
		if(args.length!=1)
			logger.info("请出入sql文件的路径");
		else{
			String filePath = args[0];
			logger.info("即将读取sql文件目录为:"+filePath);
			logger.info("------------------------------开始检查------------------------------");
			File file = new File(filePath);
			List<SqlLog> logList = Util.getLogListFromFile(file);
			for(SqlLog log : logList){
				if(Util.checkInSqlRegex(log.getText(), 100))
					logger.info("存在sql中in的元素超过100个的情况----行数:"+log.getNum()+" SQL:"+log.getText());
				if(Util.checkSelectSqlRegex(log.getText()))
					logger.info("存在sql中包含select * 的语句----行数:"+log.getNum()+" SQL:"+log.getText());
			}
			logger.info("------------------------------开始结束------------------------------");
		}
		
	}
}
