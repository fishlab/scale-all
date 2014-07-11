package org.fishlab.util.persistence.impl.test;

import java.text.DateFormat;
import java.util.Date;


import org.fishlab.util.persistence.impl.SQLMaker;
import org.fishlab.util.persistence.impl.StandardSQLMaker;
import junit.framework.TestCase;

public class TestSQLMaker extends TestCase{
	
	public void testSelect01(){
		char[] ch=new char[]{'2','3'};
		DateFormat df=DateFormat.getDateTimeInstance();
		
		Date dt=new Date( );
		Date d=java.sql.Date.valueOf("2012-12-14");
		System.out.println(df.format(dt));
		System.out.println(d.toString()+" [date.toString()]");
		SQLMaker sm=new StandardSQLMaker();
		TestClass o=new TestClass();
		o.setName("1025");
		o.setYear(1027);
		o.ddd="dddgfh";
		
		System.out.println(sm.getSelect(TestClass.class,"name=1"));
		System.out.println(sm.getInsert(o));
		System.out.println(sm.getUpdate(o));
		System.out.println(sm.getUpdate(o,"ddd","yr"));
		System.out.println(sm.getDelete(o)) ;
		
		System.out.println(sm.getDelete(o.getClass(),"name='gdf'")) ;
		
	}

}
