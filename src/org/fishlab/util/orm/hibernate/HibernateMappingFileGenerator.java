package org.fishlab.util.orm.hibernate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.fishlab.util.orm.MappingFileGenerator;

import com.czway.util.bean.AttributeUtil;

/**just for mysql at present
 */
public class HibernateMappingFileGenerator implements MappingFileGenerator {
	public static String HbmHeader=
"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
"<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n"+
"\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n";
	public static String Hbmpackage="<hibernate-mapping package=\"P\">\n";
//	public static String Hbmclass="    <class name=\"C\" table=\"D\">\n";
	public static String Hbmcahce="\t<cache usage=\"read-write\"/>\n";
	public static String Hbmid="\t<id name=\"id\" column=\"id\">\n"+
			"\t    <generator class=\"identity\"/>\n"+
			"\t</id>\n";
	
	public static String Hbmidr="\t<id name=\"I\" column=\"C\" type=\"T\">\n"+
			"\t    <generator class=\"identity\"/>\n"+
			"\t</id>\n";
	public static String Hbmproprty="\t<property name=\"webuid\" column=\"webuid\"/>\n";
    public static String Hbmclassend="    </class>\n";
    
	public String toHbm(Class<?> cl){
		StringBuilder stb=new StringBuilder();
		String cpackage = null,cname=cl.getName();
		int p0=cname.lastIndexOf(".");
		if (p0!=-1){
			cpackage=cname.substring(0,p0);
			p0++;
			cname=cname.substring(p0);
		}
		stb.append(HbmHeader);
		stb.append(Hbmpackage.replace("P", cpackage));
//		stb.append(Hbmclass.replace("C", cname).replace("D", cname.toLowerCase()));
		stb.append("\t<class name=\""+cname+"\" table=\""+cname.toLowerCase()+"\">\n");
		stb.append(Hbmcahce);
//		stb.append(Hbmid);
		//
//		Vector<Field []> vf=BeanAccess.getDeclaredFields(cl);
//		Field[] fd=cl.getDeclaredFields();
//		Field[] fd=cl.getFields();
//		String fdname=null;
		List<Method> mth=AttributeUtil.getSetters(cl);
		List<String> setters=AttributeUtil.getSetterNames(mth);
//		AttributeUtil
		char fc;
//		for (Field[] fd:vf)
//		for (Field f:fd){
		for (int i=0;i<setters.size();i++){
			if (setters.get(i).equals("id")){
				setters.remove(i);
				Method m=mth.remove(i);
				Class<?>[] pm=m.getParameterTypes();
				if (pm.length==1)
				stb.append(
				Hbmidr
				.replace("I", "id")
				.replace("C", "id")
				.replace("T", pm[0].getName())
				);
			}
		}
		
		for(int i=0;i<mth.size();i++){
			Method m=mth.get(i);
			Class<?>[] pm=m.getParameterTypes();
//			cname=f.getName();
			cname=setters.get(i);
			fc=cname.charAt(0);
			if (pm.length==1&&!cname.equals("id")&&fc>='a'&&fc<='z')
				stb.append("\t<property name=\""+cname+"\" column=\""+cname+"\" type=\""+pm[0].getName()+"\"/>\n");
		}
		stb.append(Hbmclassend);
		stb.append("</hibernate-mapping>");
		return stb.toString();
	}

	@Override
	public void generate(Class<?> c) {
		if(this.outputPath==null){
			this.outputPath="gen-hibernate";
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
			FileWriter fw=new FileWriter (this.outputPath+"/"+c.getSimpleName()+".hbm.xml");
			fw.write(toHbm(c));
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
