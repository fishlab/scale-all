package com.czway.dbm;

import java.sql.Connection;

public interface DataSourcePool {
	public Connection getConnection();
	public void changeSource(String scname);
}
