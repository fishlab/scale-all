package com.czway.dbm;

import java.sql.ResultSet;

public interface JDBCOperation {
	
	public void executeSQL(String sql);
	public void executeUpdate(String sql);
	public ResultSet executeQuery(String sql);
	
	public void addSQL(String sql);
	public void executeSQLFile(String fn);
	

}
