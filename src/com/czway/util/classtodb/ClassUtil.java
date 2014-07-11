package com.czway.util.classtodb;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.czway.util.bean.BeanAccess;

/**just for mysql at present
 */
public class ClassUtil {
	public static final String Drop="drop table if exists T;\n";
	public static final String Create="create table if not exists T (";
	
	public static Map<Class<?>,String> Typemapping;
	
	static {
		Typemapping=new HashMap<Class<?>,String>();
		Typemapping.put(java.lang.String.class, "char(100)");
		Typemapping.put(int.class, "int");
		Typemapping.put(short.class, "smallint");
		Typemapping.put(long.class, "bigint");
		Typemapping.put(boolean.class, "tinyint(1)");
		Typemapping.put(float.class, "float");
		Typemapping.put(double.class, "double");
		Typemapping.put(char.class, "char(1)");
		Typemapping.put(byte.class, "tinyint");
		Typemapping.put(java.sql.Date.class, "date");
		Typemapping.put(java.sql.Time.class, "time");
		Typemapping.put(java.sql.Timestamp.class, "timestamp");
	}
	
	public static void s(Object o){
		System.out.println(o);
	}
	
	public static String toSQL(Class<?> cl){
		StringBuilder stb=new StringBuilder();
		String cname=cl.getName();
		int p0=cname.lastIndexOf(".");
		if (p0!=-1){
			p0++;
			cname=cname.substring(p0).toLowerCase();
		}
//		stb.append("use "+Dbname+";\nset names \"UTF8\";\n");
		stb.append("set names \"UTF8\";\n");
		stb.append(Drop.replace("T", cname));
		stb.append(Create.replace("T", cname));
//		s(cname);
//		Field[] fd=cl.getFields();
		Vector<Field []> vf=BeanAccess.getDeclaredFields(cl);
//		Field[] fd=cl.getDeclaredFields();
		String fdname=null;
		char fc;
		
		for (Field[] fd:vf)
		for (Field f:fd){
			fdname=f.getName();
			fc=fdname.charAt(0);
			if (!(fc>='a'&&fc<='z')) 
				continue;
			stb.append("\n\t");
			if (fdname=="id"){
				stb.append(fdname);
				stb.append(" ");
				stb.append(Typemapping.get(f.getType()) );
				stb.append(" unsigned not null auto_increment primary key,");
				continue;
			}
			else stb.append(fdname);
			stb.append(" ");
			stb.append(Typemapping.get(f.getType()) );
			stb.append(" null,");
		}
		stb.deleteCharAt(stb.length()-1);
		stb.append("\n)ENGINE=InnoDB CHARSET=utf8;");
		return stb.toString();
//		s(stb);
	}
	public static String HbmHeader="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
			"<!DOCTYPE hibernate-mapping PUBLIC\n"+
			"\t\"-//Hibernate/Hibernate Configuration DTD 3.0//EN\"\n"+
			"\t\"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd\">\n";
	public static String Hbmpackage="<hibernate-mapping package=\"P\">\n";
	public static String Hbmclass="    <class name=\"C\" table=\"D\">\n";
	public static String Hbmcahce="\t<cache usage=\"read-write\"/>\n";
	public static String Hbmid="\t<id name=\"id\" column=\"id\">\n"+
			"\t    <generator class=\"identity\"/>\n"+
			"\t</id>\n";
	public static String Hbmproprty="\t<property name=\"webuid\" column=\"webuid\"/>\n";
    public static String Hbmclassend="    </class>\n";
    
	public static String toHbm(Class<?> cl){
		StringBuilder stb=new StringBuilder();
		String cpackage = null,cname=cl.getName();
		int p0=cname.lastIndexOf(".");
		if (p0!=-1){
			cpackage=cname.substring(0,p0);
			p0++;
			cname=cname.substring(p0);
		}
		stb.append(ClassUtil.HbmHeader);
		stb.append(ClassUtil.Hbmpackage.replace("P", cpackage));
		stb.append(ClassUtil.Hbmclass.replace("C", cname).replace("D", cname.toLowerCase()));
		stb.append(ClassUtil.Hbmcahce);
		stb.append(ClassUtil.Hbmid);
		//
		Vector<Field []> vf=BeanAccess.getDeclaredFields(cl);
//		Field[] fd=cl.getDeclaredFields();
//		Field[] fd=cl.getFields();
//		String fdname=null;
		char fc;
		for (Field[] fd:vf)
		for (Field f:fd){
			cname=f.getName();
			fc=cname.charAt(0);
			if (!cname.equals("id")&&fc>='a'&&fc<='z')
				stb.append("\t<property name=\""+cname+"\" column=\""+cname+"\"/>\n");
		}
		stb.append(ClassUtil.Hbmclassend);
		stb.append("</hibernate-mapping>");
		return stb.toString();
	}
	public static void writeToHbmFile(Class<?> c,String fn){
		try {
			FileWriter fw=new FileWriter (fn);
			fw.write(toHbm(c));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToSQLFile(Class<?> c,String fn){
		try {
			FileWriter fw=new FileWriter (fn);
			fw.write(toSQL(c));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
