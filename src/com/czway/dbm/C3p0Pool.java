package com.czway.dbm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.czway.util.classloader.ExtClassPathLoader;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Pool implements DataSourcePool {
	private ComboPooledDataSource datasource;

	static {
		ExtClassPathLoader.loadClassPath("E:/web/config/");
	}
	
	public C3p0Pool() {
		datasource = new ComboPooledDataSource();
	}
	
	public C3p0Pool(String scname){
		datasource =new ComboPooledDataSource(scname);		
	}
	
	public void changeSource(String scname){
		datasource.close();
		datasource=new ComboPooledDataSource(scname);
	}

	public static C3p0Pool getInstance() {
		return new C3p0Pool();
	}

	public Connection getConnection() {
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("无法从数据源获取连接 ", e);
		}
	}

	public static void main(String[] args) throws SQLException {
		Connection con = null;
		C3p0Pool cp=new C3p0Pool();
		cp.changeSource("web");
		try {
			con = cp.getConnection();
		ResultSet rst =	con.createStatement().executeQuery("select * from user;");
		while(rst.next()){
			System.out.println(rst.getString("name") );
		}
		} catch (Exception e) {
		} finally {
			if (con != null)
				con.close();
		}
	}


}