package org.fishlab.util.persistence.annotations.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class TestAnno extends TestCase{
	public @interface As{
		public static enum Grade {
			dsf(1),unus(1), EXCELLENT(1), SATISFACTORY(11), UNSATISFACTORY (1145);
				@SuppressWarnings("unused")
				private int soccer;
				private  Grade(int t){
					this.soccer=t;
				}
			};
		String[] value();
		
	} 
	
	@As({"dsf","unus"})
	private void ss(){
		
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Date dt=new Date();
		//As.Grade.valueOf("dsf");
		String sss=new String("fgh");
		dt.toGMTString();
		DateFormat df=DateFormat.getInstance();
		//System.out.println(df.f);
		Annotation[] ans= TestAnno.class.getAnnotations();
		//System.out.println(ans.length);
		for (Annotation an:ans){
		}
		/*
		List<Pet> list=new LinkedList<Pet>();
		Pet pet=new Dog();
		pet.set
		list.add();
		*/
		
		Method[] mth=TestAnno.class.getDeclaredMethods();
		for (Method m :mth){
			Annotation[] at=m.getDeclaredAnnotations();
			for (Annotation an:at){
				System.out.println(an.getClass().getName());
			}
			System.out.println(m.getName());
			
		}

	}

}
