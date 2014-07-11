package org.fishlab.util.persistence.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLResultReader extends ClassInfoCache implements ResultReader{

//	private Object typeConvert(Object o){		
//	}
	
	@Override
	public <T> List<T> readResultset(ResultSet rst, Class<T> cl) {
		List<T> lt=new ArrayList<T>();
		T t=null;
		try {
			while(rst.next()){
				t=readOne(rst, cl);
				if (t !=null) lt.add(t);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lt;
	}

private <T> T readOne(ResultSet rst, Class<T> cl) {
	ClassInfo cli=this.getClassInfo(cl);
	List<Field> fds=cli.getFieldlist();
	List<String> ls=cli.getNamelist();
	T t=null;
	try {
		t=cl.newInstance();
		for (int i=0;i<ls.size();i++){
			String n=ls.get(i);
			Field fd=fds.get(i);
			fd.setAccessible(true);
			Class <?> tp=fd.getType(); 					
			if (tp == int.class){//ֻд������������   δ�����...
				fd.set(t, rst.getInt(n));
			}else if (tp == long.class){
				fd.set(t, rst.getLong(n));
			}else if (tp ==float.class){
				fd.set(t, rst.getFloat(n));
			}else if (tp ==double.class){
				fd.set(t, rst.getDouble(n));
			}else if (tp == String.class){
				fd.set(t, rst.getString(n).trim());
			}else if (tp == Date.class){// java.util.Date 
				long dt=rst.getDate(n).getTime();
				dt+=rst.getTime(n).getTime();
				fd.set(t, new java.util.Date(dt));
			}else if (tp ==java.sql.Date.class){
				fd.set(t,rst.getDate(n));
			}else if (tp == boolean.class){
				fd.set(t, rst.getBoolean(n));
			}else {
			}
		}
	} catch (InstantiationException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return t;
}

	@Override
	public <T> T load(ResultSet rst, Class<T> cl) {
		T t=null;
		try {
			if (rst.next()){
				t=this.readOne(rst, cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

}
