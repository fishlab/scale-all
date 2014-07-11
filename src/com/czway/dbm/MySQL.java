package com.czway.dbm;

import java.sql.SQLException;

public class MySQL extends AbstractDataSource {
	protected static DataSourcePool dspool;

	protected static String Duser = "root";
	protected static String Dpassword = "123456";
	private static String Djdbcurl = "jdbc:mysql://127.0.0.1:3306/";
	protected static String Ddbname = "web";

	private static String Driverclass = "com.mysql.jdbc.Driver";

	static {
		loadDriver(Driverclass);
	}

	public static void setDataSourcePool(DataSourcePool dsp) {
		MySQL.dspool = dsp;
	}

	public static void setDefaultUser(String duser) {
		Duser = duser;
	}

	public static void setDefaultPassword(String dpwd) {
		Dpassword = dpwd;
	}

	public static MySQL getInstance() {
		MySQL ms = null;
		if (MySQL.dspool != null) {
			ms = new MySQL(MySQL.dspool);
			ms.init();
			return ms;
		} else {
			ms = new MySQL();
			ms.init();
			return ms;
		}
	}

	public static MySQL getInstance(String dbname) {
		MySQL ms = null;
		if (MySQL.dspool != null) {
			ms = new MySQL(MySQL.dspool);
			ms.init();
			return ms;
		} else {
			ms = new MySQL(dbname);
			ms.init();
			return ms;
		}
	}

	public MySQL(DataSourcePool dsp) {
		super(dsp);
	}

	public MySQL() {
		this.user = Duser;
		this.password = Dpassword;
		this.jdbcurl = Djdbcurl;
		this.dbname = Ddbname;
	}

	public MySQL(String dbname) {
		this.user = Duser;
		this.password = Dpassword;
		this.jdbcurl = Djdbcurl;
		this.dbname = dbname;
	}

	public MySQL(String dbname, String user, String pwd) {
		this.user = user;
		this.jdbcurl= Djdbcurl;
		this.password = pwd;
		this.dbname = dbname;
	}

	public void excuteFromFile(String fn) {
		this.executeSQLFile(fn);
	}

	public void close() {
		try {
			this.stm.close();
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void execute(String sql) {
		try {
			this.stm.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
