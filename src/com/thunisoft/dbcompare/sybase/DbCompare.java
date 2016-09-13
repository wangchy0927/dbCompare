package com.thunisoft.dbcompare.sybase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.thunisoft.dbcompare.bean.Column;
import com.thunisoft.dbcompare.bean.DB;
import com.thunisoft.dbcompare.bean.DBNUM;
import com.thunisoft.dbcompare.bean.Table;
import com.thunisoft.dbcompare.util.DBHelper;
import com.thunisoft.dbcompare.util.SQL;

public class DbCompare {

	private ResultSet result;
	private DBHelper help;

	public static void main(String[] args) throws SQLException {
		DbCompare compare = new DbCompare();
		DB db1 = compare.getStructureByDBNum(DBNUM.DBNUM1);
		DB db2 = compare.getStructureByDBNum(DBNUM.DBNUM2);
		compare.dbCompare(db1,db2);
	}

	/**
	 * 获取数据库的所有表结构
	 * @param dbNum ：数据库编号
	 * */
	private DB getStructureByDBNum(String dbNum) throws SQLException {
		help = new DBHelper(dbNum);
		help.getConnection();
		DB db = new DB();
		String dbName = help.getDBName();
		Set<Table> tableList = getTables(help);
		System.out.println("表个数:"+tableList.size());
		System.out.println("Start~~~获取:"+dbName+"的表结构");
		for(Table table : tableList){
			Set<Column> columnList = new HashSet<Column>();
			String tableID = table.getId()+"";
			result = help.execQuerySQL(SQL.SYBASE_GETCOLUMNS_SQL.replace(SQL.SYBASE_TABLEID, tableID));
			while(result.next()){
				Column column = new Column();
				column.setName(result.getString("COLUMN_NAME"));
				column.setType(result.getString("COLUMN_TYPE"));
				columnList.add(column);
				table.setColumnList(columnList);
			}
		}
		System.out.println("End~~~获取:"+dbName+"的表结构");
		result.close();
		db.setTableList(tableList);
		//help.closedConn();
		return db;
	}

	/**
	 * 根据数据库连接获取库所有的表
	 * @param help :数据库连接
	 * */
	private Set<Table> getTables(DBHelper help) throws SQLException {
		String dbName = help.getDBName();
		//拼接查询表的sql
		result = help.execQuerySQL(SQL.SYBASE_GETTABLES_SQL);
		Set<Table> tableList = new HashSet<Table>();

		System.out.println("Start~~~获取数据库:" + dbName + "所拥有表");
		while (result.next()) {
			Table table = new Table();
			String tableName = result.getString("TABLE_NAME");
			String tableId = result.getString("TABLE_ID");
			table.setName(tableName);
			table.setId(tableId);
			tableList.add(table);
		}
		System.out.println("End~~~获取数据库:" + dbName + "表个数为:" + tableList.size());
		result.close();
		
		return tableList;
	}

	/**
	 * 对2个数据库表结构进行最终的对比
	 * */
	private void dbCompare(DB db1,DB db2) {
		int db1TableNum = db1.getTableList().size();
		int db2TableNum = db2.getTableList().size();
		System.out.println("~~~~~~~~~~~~~~~~~~~开始对比~~~~~~~~~~~~~~~~~~~~~~~~~");
		if(!(db1TableNum==db2TableNum))
			System.out.println("数据库表个数验证失败:------db1:"+db1TableNum+",db2:"+db2TableNum);
		Set<Table> table1List = db1.getTableList();
		Set<Table> table2List = db2.getTableList();
		//用于记录相同Table的临时set
		Set<Table> tmpSet1 = new HashSet<Table>();
		Set<Table> tmpSet2 = new HashSet<Table>();
		for(Table table1 : table1List){
			String tableName = table1.getName();
			for(Table table2 : table2List){
				if(table2.getName().equals(tableName)){
					compamreCol(table1,table2);
					tmpSet1.add(table1);
					tmpSet2.add(table2);
				}
			}		
		}
		//移除相同的Table
		table1List.removeAll(tmpSet1);
		table2List.removeAll(tmpSet2);
		compareTable(table1List,table2List);
	}
	
	/**
	 * 列出每个set中剩余的Table
	 * */
	private void compareTable(Set<Table> table1,Set<Table> table2){
		int tableNum1 = table1.size();
		int tableNum2 = table2.size();
		if(tableNum1>0){
			System.out.print("数据库db1中含有额外的表:");
			for(Table table : table1){
				System.out.print(table.getName()+" ");
			}
			System.out.println();
		}
		
		if(tableNum2>0){
			System.out.print("数据库db2中含有额外的表:");
			for(Table table : table2){
				System.out.print(table.getName()+" ");
			}
			System.out.println();
		}
	}
	
	private void compamreCol(Table table1,Table table2){
		int columnNum1 = table1.getColumnList().size();
		int columnNum2 = table2.getColumnList().size();
		String tableName = table1.getName();
		if(!(columnNum1==columnNum2))
			System.out.println("数据库表"+tableName+"的字段个数验证失败:-----db1."+tableName+":"+columnNum1+" db2."+tableName+":"+columnNum2);
		//用户记录相同的Column的临时set
		Set<Column> tmpCol1 = new HashSet<Column>();
		Set<Column> tmpCol2 = new HashSet<Column>();
		for(Column column1 : table1.getColumnList()){
			String colName1 = column1.getName();
			for(Column column2 : table2.getColumnList()){
				if(column2.getName().equals(colName1)){
					if(!column2.getType().equals(column1.getType())){
						System.out.println("数据库表:"+tableName+"的列:"+colName1+"的类型验证失败:db1."+tableName+":"+column1.getType()+" db2."+tableName+":"+column2.getType());
					}
					//相同的字段 加入到临时set
					tmpCol1.add(column1);
					tmpCol2.add(column2);
				}
			}
		}
		//移出相同的字段
		table2.getColumnList().removeAll(tmpCol2);
		table1.getColumnList().removeAll(tmpCol1);
		if(table1.getColumnList().size()>0){
			System.out.println("数据库db1表"+tableName+"包含多余的字段：");
			for(Column col:table1.getColumnList())
				System.out.println("name:"+col.getName()+" type:"+col.getType());
		}
		if(table2.getColumnList().size()>0){
			System.out.println("数据库db2表"+tableName+"包含多余的字段：");
			for(Column col:table2.getColumnList())
				System.out.println("name:"+col.getName()+" type:"+col.getType());
		}
	}
	
}
