package com.czway.dbm;

public class Oracle11g extends AbstractDataSource{
	private static String Driverclass = "oracle.jdbc.driver.OracleDriver";
	
	private static String Djdbcurl = "jdbc:odbc:thin:localhost:1521/";

	static {
		loadDriver(Driverclass);
	}
	
	public Oracle11g(){
		this.user="";
		this.jdbcurl=Djdbcurl;
	}
	

}
