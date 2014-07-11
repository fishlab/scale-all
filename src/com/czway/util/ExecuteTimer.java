package com.czway.util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecuteTimer{
	
	public static long getCostTime(Object o,String method,Object p,int times){
		long t1,t2;
		Method m;
		t1=System.currentTimeMillis();
		try {
			m=o.getClass().getDeclaredMethod(method, p.getClass());
			for(int i=0;i<times;i++)
				m.invoke(o, p);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		t2=System.currentTimeMillis();
		return t2-t1;
	}

}
