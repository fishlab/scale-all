package org.fishlab.util.orm.jdbc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.fishlab.util.orm.MappingFileGenerator;

import com.czway.util.bean.BeanAccess;

/**just for mysql at present
 */
public class MysqlDatatableMappingFileGernerator implements MappingFileGenerator{
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
		Typemapping.put(java.util.Date.class, "datetime");
	}
	
	
	public  String toSQL(Class<?> cl){
		StringBuilder stb=new StringBuilder();
		String cname=cl.getName();
		int p0=cname.lastIndexOf(".");
		if (p0!=-1){
			p0++;
			cname=cname.substring(p0).toLowerCase();
		}
		stb.append("set names \"UTF8\";\n");
		stb.append(Drop.replace("T", cname));
		stb.append(Create.replace("T", cname));
		Vector<Field []> vf=BeanAccess.getDeclaredFields(cl);
		String fdname=null;
		char fc;
		
		for (Field[] fd:vf)
		for (Field f:fd){
			fdname=f.getName();
			if(fdname.equals("serialVersionUID"))
				continue;
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

	@Override
	public void generate(Class<?> c) {
		if(this.outputPath==null){
			this.outputPath="gen-mysql";
		}
		File d=new File(this.outputPath);
		boolean f=true;
		if(!d.exists()){
			f=d.mkdir();
		}
		if(!f){
			return;
		}
		try {
			FileWriter fw=new FileWriter (this.outputPath+"/"+c.getSimpleName().toLowerCase()+".sql");
			fw.write(toSQL(c));
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String outputPath;
	@Override
	public void setOutputPath(String path) {
		this.outputPath=path;
	}

}
