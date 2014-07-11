package com.czway.util.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AttributeUtil {
	public static List<String> getSetterNames(Class<?> c) {
		List<Method> ls = BeanAccess.getAllMethods(c);
		List<String> ns = new ArrayList<String>();
		for (Method m : ls) {
			String n = m.getName();
			int l = n.length();
			if (l > 3 && n.startsWith("set")) {
				char[] tmp = new char[l - 3];
				char t = n.charAt(3);
				if (t >= 65 && t <= 90) {
					t += 32;
				}
				tmp[0] = t;
				for (int i = 1; i < l - 3; i++) {
					tmp[i] = n.charAt(i + 3);
				}
				ns.add(new String(tmp));
			}
		}
		return ns;

	}
	
	public static List<String> getSetterNames(List<Method> methods) {
//		List<Method> ls = BeanAccess.getAllMethods(c);
		List<String> ns = new ArrayList<String>();
		for (Method m : methods) {
			String n = m.getName();
			int l = n.length();
			if (l > 3 && n.startsWith("set")) {
				char[] tmp = new char[l - 3];
				char t = n.charAt(3);
				if (t >= 65 && t <= 90) {
					t += 32;
				}
				tmp[0] = t;
				for (int i = 1; i < l - 3; i++) {
					tmp[i] = n.charAt(i + 3);
				}
				ns.add(new String(tmp));
			}
		}
		return ns;

	}
	
	public static List<Method> getSetters(Class<?> c) {
		List<Method> ls = BeanAccess.getAllMethods(c);
		List<Method> ms = new ArrayList<Method>();
		for (Method m : ls) {
			String n = m.getName();
			int l = n.length();
			if (l > 3 && n.startsWith("set")) {
				ms.add(m);
			}
		}
		return ms;

	}
}
