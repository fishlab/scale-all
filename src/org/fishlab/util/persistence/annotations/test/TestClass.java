package org.fishlab.util.persistence.annotations.test;

import java.lang.reflect.Method;

import junit.framework.Assert;
import junit.framework.TestCase;

class A{
	public int t;
	
	public void t(String ss){
		System.out.println(ss.getClass().getSimpleName().toLowerCase());;
	}
}

public class TestClass extends TestCase{
	public void test01() throws Exception{
		Method m=A.class.getMethod("t", String.class);
		Object o=1;
		Assert.assertTrue(o.toString() .equals( "1") );
		m.invoke(new A(), "fghgfhfg");
		m.invoke(new A(), "11111");
//		Class<? extends Object> cl=new A().getClass();
		Assert.assertTrue(new A().getClass().getDeclaredFields().toString()!=A.class.getDeclaredFields().toString());
	}

}
