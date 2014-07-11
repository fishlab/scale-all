package org.fishlab.util.persistence.impl.test;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import org.fishlab.util.persistence.annotations.Constraint;
import org.fishlab.util.persistence.annotations.Datafield;
import org.fishlab.util.persistence.annotations.Datatable;
import org.fishlab.util.persistence.impl.ClassInfo;
import org.fishlab.util.persistence.impl.ClassInfoUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

class A{
//	@Constraint.Unique
	@Datafield("username")
	private String name;
	@Constraint.Primary
	protected String ddd;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

@Datatable("testtable")
class TestClass extends A{
	@Datafield("yr")
	private int year;

	protected boolean sss=true;
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}

public class TestClassInfoUtil extends TestCase{
	@Test
	public void _testt1(){
		List<Field> lf=ClassInfoUtil.getAllFields(TestClass.class);
		Assert.assertEquals(3,lf.size() );
	}
	
	public void test02(){
		ClassInfo cli=ClassInfoUtil.getInfo(TestClass.class);
		Assert.assertEquals("username", cli.getIdentifier());
		Assert.assertEquals("testclass",cli.getDatatable() );
		for (Field s:cli.getFieldlist()){
			System.out.println(s.getName());
		}
	}

}
