package org.fishlab.util.orm.jdbc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.fishlab.util.orm.MappingFileGenerator;
import org.fishlab.util.orm.PrefixHandler;
import org.fishlab.util.orm.SubTableNamePrefixHandler;

import com.czway.util.bean.BeanAccess;

/**just for oracle at present
 */
public class OracleDatatableMappingFileGenerator implements MappingFileGenerator{
//	public static final String Drop="drop table if exists T;\n";
	private PrefixHandler prefix=new SubTableNamePrefixHandler(2,'x');
	public static final String Create="create table T (";
	public static Map<Class<?>,String> Typemapping;
	
	static {
		Typemapping=new HashMap<Class<?>,String>();
		Typemapping.put(java.lang.String.class, "char(?)");
		Typemapping.put(int.class, "number(10,0)");
		Typemapping.put(short.class, "number(5,0)");
		Typemapping.put(long.class, "number(19,0)");
		Typemapping.put(boolean.class, "number(1)");
		Typemapping.put(float.class, "float");
		Typemapping.put(double.class, "float(24)");
		Typemapping.put(char.class, "char(1)");
		Typemapping.put(byte.class, "number(3)");
		Typemapping.put(java.sql.Date.class, "date");
		Typemapping.put(java.sql.Time.class, "time");
		Typemapping.put(java.sql.Timestamp.class, "timestamp");
		Typemapping.put(java.util.Date.class, "date");
	}
	
	
	public  String toSQL(Class<?> cl){
		StringBuilder stb=new StringBuilder();
		String cname=cl.getName();
		int p0=cname.lastIndexOf(".");
		if (p0!=-1){
			p0++;
			cname=cname.substring(p0).toLowerCase();
		}
//		stb.append("set names \"UTF8\";\n");
//		stb.append(Drop.replace("T", cname));
		stb.append(Create.replace("T", cname));
		Vector<Field []> vf=BeanAccess.getDeclaredFields(cl);
		String fdname=null;
		char fc;
		String pre=this.prefix.getPrefix(cname)+"_";
		for (Field[] fd:vf)
		for (Field f:fd){
			fdname=f.getName();
			if(fdname.equals("serialVersionUID"))
				continue;
			fc=fdname.charAt(0);
			if (!(fc>='a'&&fc<='z')) 
				continue;
			stb.append("\n\t");
			if (fdname.equals("id")){
				stb.append(pre+fdname);
				stb.append(" ");
				stb.append(Typemapping.get(f.getType()) );
				stb.append(" primary key,");
				continue;
			}
			else stb.append(pre+fdname);
			stb.append(" ");
			stb.append(Typemapping.get(f.getType()) );
			stb.append(" null,");
		}
		stb.deleteCharAt(stb.length()-1);
		stb.append("\n);");
		
		stb.append(this.creatSequence(cname.toUpperCase()+"_ID_SEQ"));
		return stb.toString();
//		s(stb);
	}

	private Object creatSequence(String seqName) {
		return  "\ncreate sequence "+seqName+  
			    "\nINCREMENT BY 1"+ 
			    "\nSTART WITH 1"+ 
			    "\nNOMAXVALUE"+
			    "\nNOCYCLE"+    
			    "\nNOCACHE;";
	}

	@Override
	public void generate(Class<?> c) {
		if(this.outputPath==null){
			this.outputPath="gen-oracle";
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
