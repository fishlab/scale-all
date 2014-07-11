package com.czway.dbm;

import java.sql.SQLException;

public class Hsqldb extends AbstractDataSource{
	protected static  DataSourcePool dspool;
	protected static String Duser = "sa";
	protected static String Dpassword = "";
//	protected static String Djdbcurl = "jdbc:hsqldb:.";
//	protected static String Djdbcurl = "jdbc:hsqldb:hsql:/localhost/mydb";
	protected static String Djdbcurl = "jdbc:hsqldb:.mysql";
	protected static String Ddbname = "mydb";

	private static String Driverclass = "org.hsqldb.jdbcDriver";
	

	static {
		loadDriver(Driverclass);
	}
	
	public static void setDataSourcePool(DataSourcePool dsp){
		MySQL.dspool=dsp;
	}

	public static void setDefaultUser(String duser) {
		Duser = duser;
	}

	public static void setDefaultPassword(String dpwd) {
		Dpassword = dpwd;
	}
	
	public static MySQL getInstance(){
		if (MySQL.dspool!=null)
			return new MySQL(MySQL.dspool);
		else
			return new MySQL();
	}

	
	public Hsqldb(DataSourcePool dsp){
		super(dsp);
	}
	
	public Hsqldb() {
		this.user = Duser;
		this.password = Dpassword;
		this.jdbcurl = Djdbcurl;
		this.dbname = Ddbname;
	}

	public Hsqldb(String dbname) {
		this.user = Duser;
		this.password = Dpassword;
		this.jdbcurl = Djdbcurl;
		this.dbname = dbname;
	}

	public Hsqldb(String dbname, String user, String pwd) {
		this.user = user;
		this.password = pwd;
		this.dbname = dbname;
	}
	

	public static void main(String[] a) {
		Hsqldb hs = new Hsqldb();
		hs.init();
		hs.executeSQL("CREATE TABLE TA ("
					  + " i int,"
					  + " s varchar(20)"
					+");");	
//		hs.executeSQLFile("P:/home/wu/init.sql");
//		ms.excuteFromFile("P:/home/wu/init1.sql");
//		ms.excuteFromFile("P:/home/wu/init2.sql");
//		ms.excuteFromFile("P:/home/wu/insert.sql");
//		ms.close();
		System.out.println(hs instanceof JDBCOperation);
		hs.close();
		
	}

	public void close() {
		try {
			this.stm.close();
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
