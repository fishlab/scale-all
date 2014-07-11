package org.fishlab.util.persistence.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.text.DateFormat;
import java.util.List;

public class StandardSQLMaker extends ClassInfoCache implements SQLMaker{
	
	@Override
	public String getSelect(Class<?> cl) {
		ClassInfo cli=this.getClassInfo(cl);
		StringBuilder stb=new StringBuilder(32);
		stb.append("select ");
		List<String> ls=cli.getNamelist();
		if (ls.size()>0){
			stb.append(ls.get(0) );
			for (int j=1;j<ls.size();j++){
				stb.append(","+ls.get(j));
			}
		}
		stb.append(" from "+cli.getDatatable()+";" );
		return stb.toString();
	}

	@Override
	public String getSelect(Class<?> cl, String condition) {
		ClassInfo cli=this.getClassInfo(cl);
		StringBuilder stb=new StringBuilder(32);
		stb.append("select ");
		List<String> ls=cli.getNamelist();
		if (ls.size()>0){
			stb.append(ls.get(0) );
			for (int j=1;j<ls.size();j++){
				stb.append(","+ls.get(j));
			}
		}
		stb.append(" from "+cli.getDatatable()+" where "+condition +";");
		return stb.toString();
	}

	private DateFormat dateformat=DateFormat.getDateTimeInstance();
	protected String typeConvert(Object o){
		if (o==null) 
			return "null";
		if (o instanceof Integer||o instanceof Short||o instanceof Long||o instanceof Float||o instanceof Double)
			return o.toString();
		if (o instanceof String)
			return "'"+o+"'";
		if (o instanceof Date)
			return "'"+this.dateformat.format((Date)o)+"'";
		if (o instanceof Boolean){
			return (Boolean)o?"1":"0";
		}
		if (o instanceof java.sql.Date){
			return ((java.sql.Date)o).toString();
		}
		return "null";
		
	}
	
	protected String insertWithoutIdentifier(Object obj,ClassInfo cli,int p0){
		StringBuilder stb=new StringBuilder(64);
		stb.append("insert into "+cli.getDatatable()+"(");
		List<String> ls=cli.getNamelist();
		List<Field> fds=cli.getFieldlist();
		boolean nf=false;
		for (int i=0;i<ls.size();i++){
			if (i!=p0){
				if (nf){
					stb.append(","+ls.get(i));
				}else{
					stb.append(ls.get(i));
					nf=true;
				}
			}
		}
		nf=false;
		stb.append(") values(");
		for(int i=0;i<fds.size();i++){
			if (i!=p0){
				try{
					Field fd=fds.get(i);
					fd.setAccessible(true);
					Object o=fd.get(obj);
					if (nf){
						stb.append(","+this.typeConvert(o));
					}else{
						stb.append(this.typeConvert(o));
						nf=true;
					}
				}catch(Exception e){
				}
			}
		}
		stb.append(");");
		return stb.toString();
	}
	protected String inertWithIdentifier(Object obj,ClassInfo cli){
		StringBuilder stb=new StringBuilder(64);
		stb.append("insert into "+cli.getDatatable()+"(");
		List<String> ls=cli.getNamelist();
		if (ls.size()>0){
			stb.append(ls.get(0) );
			for (int j=1;j<ls.size();j++){
				stb.append(","+ls.get(j));
			}
		}
		stb.append(") values(");
		List<Field> fds=cli.getFieldlist();
		if (fds.size()>0){
			try {
				Field fd=fds.get(0);
				Object o=null;
				fd.setAccessible(true);
				o=fd.get(obj);
				stb.append(this.typeConvert(o));
				for (int i=1;i<fds.size();i++){
					fd=fds.get(i);
					fd.setAccessible(true);
					o=fd.get(obj);
					stb.append(","+this.typeConvert(o));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		stb.append(");");
		return stb.toString();
	}
	
	@Override
	public String getInsert(Object obj) {
		ClassInfo cli=this.getClassInfo(obj.getClass());
		int p0=cli.getNamelist().indexOf(cli.getIdentifier());
		if (p0!=-1){
			Field fd= cli.getFieldlist().get(p0);
			fd.setAccessible(true);
			try {
				Object o=fd.get(obj);
				String cv=this.typeConvert(o);
				if (cv.equals("0")||cv.equals("null") ){
					return this.insertWithoutIdentifier(obj, cli,p0);
				};
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return this.inertWithIdentifier(obj, cli);
	}

	@Override
	public String getUpdate(Object obj) {
		ClassInfo cli=this.getClassInfo(obj.getClass());
		StringBuilder stb=new StringBuilder(32);
		stb.append("update "+cli.getDatatable()+" set ");
		List<String> ls=cli.getNamelist();
		List<Field> fds=cli.getFieldlist();
		String id=cli.getIdentifier();
		if (ls.size()>0){
			try{
				boolean nf=false;
				for (int j=0;j<ls.size();j++){
					if (ls.get(j).equals(id))
						continue;
					Field fd=fds.get(j);
					fd.setAccessible(true);
					Object o=fd.get(obj);
					if (nf)
						stb.append(","+ls.get(j)+"="+this.typeConvert(o));
					else{
						stb.append(ls.get(j)+"="+this.typeConvert(o) );
						nf=true;
					}
				}
			}catch(Exception e){
			}
		}
		stb.append(" where "+this.idConstraint(obj)+";");
		return stb.toString();
	}

	
//	private boolean inNames(String s,String [] ss){
//		for (int i=0;i<ss.length;i++){
//			if (ss[i].equals(s)) return true;
//		}
//		return false;
//	}
	
	private String idConstraint(Object obj){
		ClassInfo cli=this.getClassInfo(obj.getClass());
		String idf=cli.getIdentifier();
		String rt="";
		List<String> ls=cli.getNamelist();
		List<Field> fds=cli.getFieldlist();
		int p0=ls.indexOf(idf);
		if (p0!=-1){
			try{
				Field fd=fds.get(p0);
				fd.setAccessible(true);
				Object o=fd.get(obj);
				rt=idf+"="+this.typeConvert(o);
			}catch(Exception e){
				
			}
		}
		return rt;
	}
	
	@Override
	public String getUpdate(Object obj, String... names) {
		ClassInfo cli=this.getClassInfo(obj.getClass());
		StringBuilder stb=new StringBuilder(32);
		stb.append("update "+cli.getDatatable()+" set ");
		List<String> ls=cli.getNamelist();
		List<Field> fds=cli.getFieldlist();
		if (ls.size()>0){
			try{
				boolean nf=false;
				Object o=null;
				for (int i=0;i<names.length;i++){
					int p0=ls.indexOf(names[i]);
					if (p0!=-1){
						Field fd=fds.get(p0);
						fd.setAccessible(true);
						o=fd.get(obj);
						if (nf){
							stb.append(","+ls.get(p0)+"="+this.typeConvert(o));
						}else {
							stb.append(ls.get(p0)+"="+this.typeConvert(o));
							nf=true;
						}
					}
				}
			}catch(Exception e){
			}
		}
		stb.append(" where "+this.idConstraint(obj)+";");
		return stb.toString();
	}

	@Override
	public String getDelete(Object obj) {
		ClassInfo cli=this.getClassInfo(obj.getClass());
		return("delete from "+cli.getDatatable()+" where "+this.idConstraint(obj)+";");
	}

	@Override
	public String getDelete(Class<?> cl, String condition) {
		ClassInfo cli=this.getClassInfo(cl);
		return "delete from "+cli.getDatatable()+" where "+condition+";";
	}

}
