package org.fishlab.util.persistence.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.fishlab.util.persistence.annotations.Constraint;
import org.fishlab.util.persistence.annotations.Database;
import org.fishlab.util.persistence.annotations.Datafield;
import org.fishlab.util.persistence.annotations.Datatable;
import org.fishlab.util.persistence.annotations.NotSet;

public class ClassInfoUtil {
	
	public static List<Field> getAllFields(final Class<?> clazz) {
		List<Field> lf=new ArrayList<Field>();
		Field[] fds;
		for (Class<?> superClass = clazz; superClass!=null&&superClass != Object.class; superClass = superClass.getSuperclass()) {
			fds=superClass.getDeclaredFields();	
			for (int i=0;i<fds.length;i++)
				lf.add( fds[i]);
		}
		return lf;
	}
	
	public static ClassInfo getInfo(Class<?> cl){
		ClassInfo cli=new ClassInfo();
		
		Database dtbs=cl.getAnnotation(Database.class);
		Datatable dttb=cl.getAnnotation(Datatable.class);
		if (dtbs!=null){
			cli.setDatabase(dtbs.value());
		}
		if (dttb!=null){
			cli.setDatatable(dttb.value());
		}else{
			cli.setDatatable(cl.getSimpleName().toLowerCase());
		}
		
		List<Field> fl=getAllFields(cl);
		boolean noid=true;
//		boolean nofd=true;
		List <String> flst=(List<String>) new ArrayList<String>(fl.size());
		for (int n=0;n<fl.size();n++){
			Field f=fl.get(n);
			Annotation[] ants=f.getAnnotations();
			String fdn=f.getName();
			boolean isid=false;
			boolean set=true;
			if (ants.length>0){
				for (int i=0;i<ants.length;i++){
					Annotation at=ants[i];
					if (at instanceof Datafield){
						fdn= ((Datafield)at).value();
					}else if (at instanceof NotSet){
						set=false;
					}
					else if (noid){
						if (at instanceof Constraint.Primary){
							isid=true;
							noid=false;
						}else if (at instanceof Constraint.Unique){
							isid=true;
							noid=false;
						}else if (at instanceof Constraint.Foreign){
						}
					}
					
				}
			}
			if (set){
				flst.add(fdn);
				if(isid){
					cli.setIdentifier(fdn);
				}
			}else{
				fl.remove(n);
			}

			
		}
		cli.setNamelist(flst);
		cli.setFieldlist(fl);
		return cli;
	}

}
