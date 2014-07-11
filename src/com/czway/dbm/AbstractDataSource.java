package com.czway.dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.fishlab.util.io.FileUtils;


public abstract class AbstractDataSource implements JDBCDataSource,JDBCOperation{
	protected String user;
	protected String password;
	protected String dbname;
	protected String jdbcurl;

	
	protected Connection connection;
	public Statement stm;
	
	
	
	protected static void loadDriver(String name){
		try {
			Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
	}
	
	protected AbstractDataSource(){
		
	}
	
	protected AbstractDataSource(DataSourcePool dsp){
		this.connection=dsp.getConnection();
	}
	
	
	public void init(){
		this.stm=this.getStatement();
	}
	
	
	private void connect(){
		try {
			this.connection=DriverManager.getConnection(
					this.jdbcurl+this.dbname,this.user,this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		if (this.connection==null) 
			this.connect();
		return this.connection;
	}
	
	public void closeConnection(){
		if (this.connection!=null)
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public Statement getStatement(){
		Connection c=this.getConnection();
		Statement s=null;
		try {
			s=c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public PreparedStatement getPreStatement(String sql){
		Connection c=this.getConnection();		
		PreparedStatement p=null;
		try {
			p = c.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	

	public void executeSQL(String sql) {
		try {
			this.stm.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeUpdate(String sql) {
		try {
			this.stm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) {
		try {
			return 
					this.stm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addSQL(String sql) {
		try {
			this.stm.addBatch(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeSQLFile(String fn) {
		String stb = FileUtils.convertToString(fn,"UTF8");
		int p0, p1;
		try {
			for (p0 = 0; (p1 = stb.indexOf(";", p0)) != -1; p0 = p1 + 1) {
				this.stm.addBatch(stb.substring(p0, p1));
			}
			this.stm.executeBatch();
			this.stm.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}
