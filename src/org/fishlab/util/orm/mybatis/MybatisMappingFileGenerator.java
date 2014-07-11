package org.fishlab.util.orm.mybatis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.fishlab.util.orm.MappingFileGenerator;

import com.czway.util.bean.AttributeUtil;


public class MybatisMappingFileGenerator implements MappingFileGenerator {
	private VelocityEngine velocityEngine;
	private MyBatisSQLMaker sqlMaker;
	private String outputPath;

	public MybatisMappingFileGenerator() {
		this.velocityEngine = new VelocityEngine();
		this.velocityEngine.setProperty(VelocityEngine.INPUT_ENCODING, "UTF-8");
		this.velocityEngine
				.setProperty(VelocityEngine.OUTPUT_ENCODING, "UTF-8");
		this.velocityEngine.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		this.velocityEngine.addProperty(VelocityEngine.RESOURCE_LOADER,
				"classpath");
		this.velocityEngine.init();
		this.sqlMaker = new MyBatisSQLMaker();
	}

//	private List<Method> getMethods(Class<?> clazz){
//		List<Method> mt=new ArrayList<Method>();
//		for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
////			mt.add(clazz.get)
//		}
//		return mt;
//	}
	private List<Map<String, Object>> lm=new ArrayList<Map<String, Object>>();
	@Override
	public void generate(Class<?> mapper) {
		VelocityContext vc = new VelocityContext();
		vc.put("mapperClass", mapper.getName());
	
		List<Map<String, Object>> lo=new ArrayList<Map<String, Object>>();
		
		Method[] methods = mapper.getMethods();
		
		for( TypeVariable<?> tp:mapper.getTypeParameters()){
			System.out.println(tp.getName());
		};
		for(Method m:methods){
			lo.add(this.getOperationMap(mapper,m));
		}
		vc.put("resultMap", lm);
		vc.put("operation", lo);
		Template template = velocityEngine.getTemplate("org/fishlab/util/orm/mybatis/MapperTemplate");
//		StringWriter sw = new StringWriter();
		if(this.outputPath==null)
			this.outputPath="gen-mybatis";
		File d=new File(this.outputPath);
		boolean f=true;
		if(!d.exists()){
			f=d.mkdir();
		}
		if(!f){
			return;
		}
		FileWriter fw;
		try {
			fw = new FileWriter(this.outputPath+"/"+mapper.getSimpleName()+".xml");
			template.merge(vc,fw);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lm.clear();
	}
	
	private Map<String,Object> getResultMap(Class<?> c){
		Map<String,Object> m=new HashMap<String,Object>();
		
		m.put("resultType", c.getName());
		List<String> setters=AttributeUtil.getSetterNames(c);
		m.put("resultList", setters);
		m.put("resultMapId",  c.getSimpleName()+"ResultMap");
		return m;
	}
	private Map<String,Object> getOperationMap(Class<?> c,Method mt){
		Map<String,Object> m=new HashMap<String,Object>();
		String mn=mt.getName();
		Class<?> rc=mt.getReturnType();
		if(rc==Object.class){
			try {
				rc=this.getClass().getClassLoader().loadClass(this.getActualType(c, mt.getGenericReturnType()));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		m.put("id", mn);
		Class<?>[] pc= mt.getParameterTypes();
		
		String sn=rc.getSimpleName();
		if(pc.length==1){
			//
			if(pc[0]==Object.class){
				try {
					pc[0]=this.getClass().getClassLoader().loadClass(this.getActualType(c, mt.getGenericParameterTypes()[0]));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
				//
			
			m.put("parameterType",pc[0].getName());
			if (mn.contains("insert")){
				m.put("name", "insert");
				m.put("sql", this.sqlMaker.getInsert(pc[0]));
				this.addResultMapIfNotExists(pc[0]);
			}else if(mn.contains("select")){//xxx
				m.put("name", "select");
				m.put("sql", this.sqlMaker.getSelect(pc[0]));
				m.put("resultType",rc.getName());
				m.put("resultMap", sn+"ResultMap");
				this.addResultMapIfNotExists(rc);
			}else if(mn.contains("get")){
				m.put("name", "select");
				m.put("sql", this.sqlMaker.getSelect(rc));
				m.put("resultType",rc.getName());
				m.put("resultMap", sn+"ResultMap");
				this.addResultMapIfNotExists(rc);
			}else if(mn.contains("update")){
				m.put("name", "update");
				m.put("sql", this.sqlMaker.getUpdateSet(pc[0]));
			}else if(mn.contains("del")||mn.contains("delete")){
				m.put("name", "delete");
				m.put("sql", this.sqlMaker.getDelete(pc[0]));
			}
		}else{
			if (mn.contains("get")){
				m.put("name", "select");
				m.put("sql", this.sqlMaker.getSelect(rc));
				m.put("resultType",rc.getName());
				m.put("resultMap", sn+"ResultMap");
				this.addResultMapIfNotExists(rc);
			}
			//m.put("sql","");
		}
		return m;
	}
	
	private void addResultMapIfNotExists(Class<?> c){
		String rt=c.getName();
		for(int i=0;i<this.lm.size();i++){
			Map<String,Object> m=this.lm.get(i);
			if(m.get("resultType")==rt){
				return;
			}
		}
		this.lm.add(this.getResultMap(c));
	}
	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	//new addition
	private String spliteClass(Type t) {
		String typestr = t.toString();
		int p = typestr.indexOf(' ');
		if (p != -1)
			return typestr.substring(p + 1);
		return typestr;
	}
	
	private String spliteGenericClass(Type t) {
		String typestr = t.toString();
		int p = typestr.indexOf('<');
		if (p != -1)
			return typestr.substring(0,p);
		return typestr;
	}

	private String getActualType(Class<?> c, Type mt) {
		String type = null;
//		Type mt = m.getGenericReturnType();
		if (mt instanceof TypeVariable) {
			Type[] gin = c.getGenericInterfaces();
			String tvar = ((TypeVariable<?>) mt).getName();// like T
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			for (int i = 0; i < gin.length; i++) {
				Type gt = gin[i];
//				System.out.println(gt.toString());
				try {
					System.out.println("load##"+ this.spliteGenericClass(gt));
					Class<?> ic = cl.loadClass(this.spliteGenericClass(gt));
					for (TypeVariable<?> tv : ic.getTypeParameters()) {
						System.out.println(tvar+"::"+tv.getName());
						if (tv.getName().equals(tvar)) {
							if (gt instanceof ParameterizedType) {
								for (Type t1 : ((ParameterizedType) gt)
										.getActualTypeArguments()) {
									type = this.spliteClass(t1);
									return type;
								}
							}

						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

		} else {
			type = this.spliteClass(mt);
		}

		return type;
	}
}
