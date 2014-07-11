package org.fishlab.util.persistence.impl;

import java.util.Hashtable;
import java.util.Map;

public class ClassInfoCache {
	private static final  Map<Class<?>,ClassInfo> classinfo=new Hashtable<Class<?>,ClassInfo>();

	protected ClassInfo getClassInfo(Class<?> cl){
		ClassInfo cli=classinfo.get(cl);
		if (cli==null){
			cli=ClassInfoUtil.getInfo(cl);
			classinfo.put(cl, cli);
		}
		return cli;
	}
	
}
