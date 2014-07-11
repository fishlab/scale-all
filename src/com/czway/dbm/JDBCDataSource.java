package com.czway.dbm;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public interface JDBCDataSource{
	
	public void init();
	
	public Connection getConnection();
	public Statement getStatement();
	public PreparedStatement getPreStatement(String sql);
	
	public void closeConnection();
	
	
}
