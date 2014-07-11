package org.fishlab.util.persistence.impl;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.fishlab.util.persistence.Persistence;

import com.czway.dbm.AbstractDataSource;
import com.czway.dbm.MySQL;


public class SQLPersistence implements Persistence{
	private AbstractDataSource datasource;
	private ResultReader reader;
	private SQLMaker maker;
	
	public SQLPersistence(AbstractDataSource datasource,SQLMaker maker,ResultReader reader){
		this.datasource=datasource;
		this.maker=maker;
		this.reader=reader;
	}
	
	public SQLPersistence(){
		this.datasource=new MySQL("petmanager");
		this.datasource.init();
		this.maker=new MsSQLServerSQLMaker();
		this.reader=new SQLResultReader();
	}
	
	
	@Override
	public boolean insert(Object o) {
		String sql=this.maker.getInsert(o);
		boolean r=false;
		Statement stm=this.datasource.getStatement();
		try{
			stm.execute(sql);
			r=true;
			stm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public <T> List<T> select(Class<T> cl) {
		String qr=this.maker.getSelect(cl);
		Statement stm=this.datasource.getStatement();
		List <T> ls=null;
		try{
			ResultSet rst=stm.executeQuery(qr);
			ls=this.reader.readResultset(rst, cl);
			stm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ls;
	}
	@Override
	public <T> T load(Class<T> t,String condition) {
		String qr=this.maker.getSelect(t,condition);
		Statement stm=this.datasource.getStatement();
		T ret=null;
		try{
			ResultSet rst=stm.executeQuery(qr);
			ret=(T) this.reader.load(rst,t);
			stm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public <T> List<T> select(Class<T> cl, String condition) {
		String qr=this.maker.getSelect(cl,condition);
		Statement stm=this.datasource.getStatement();
		List <T> ls=null;
		try{
			ResultSet rst=stm.executeQuery(qr);
			ls=this.reader.readResultset(rst, cl);
			stm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public <T> int delete(Class<T> t, String condition) {
		Statement stm=this.datasource.getStatement();
		String sql=this.maker.getDelete(t,condition);
		int r=0;
		try{
			r=stm.executeUpdate(sql);
			stm.close();
		}catch(Exception e){
		}
		return r;
	}

	@Override
	public boolean delete(Object o) {
		Statement stm=this.datasource.getStatement();
		String sql=this.maker.getDelete(o);
		boolean r=false;
		try{
			r=stm.executeUpdate(sql)==1;
		}catch(Exception e){
		}
		return r;
	}

	@Override
	public boolean update(Object u) {
		Statement stm=this.datasource.getStatement();
		String sql=this.maker.getUpdate(u);
		int r=0;
		try{
			r=stm.executeUpdate(sql);
			stm.close();
		}catch(Exception e){
		}
		return r==1;
	}

	@Override
	public boolean update(Object u, String... names) {
		Statement stm=this.datasource.getStatement();
		String sql=this.maker.getUpdate(u,names);
		int r=0;
		try{
			r=stm.executeUpdate(sql);
			stm.close();
		}catch(Exception e){
		}
		return r==1;
	}
	
	

	public void close(){
		this.datasource.closeConnection();
	}

}
