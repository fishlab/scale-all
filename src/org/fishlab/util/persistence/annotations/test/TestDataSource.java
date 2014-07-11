package org.fishlab.util.persistence.annotations.test;

import java.lang.annotation.Annotation;

import org.fishlab.util.persistence.annotations.Database;
import org.fishlab.util.persistence.annotations.Datafield;
import org.fishlab.util.persistence.annotations.Datatable;
import org.fishlab.util.persistence.annotations.Constraint;
import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestDataSource extends TestCase{
	@Test
	public void test001() throws SecurityException, NoSuchFieldException{
		String db="db01";
		String table="table01";

		@Database("db01") 
		@Datatable("table01")
		class Bean1{
			@Datafield("name01")
			@Constraint.Primary
			private String name; 
		}
		
		Annotation an0=Bean1.class.getAnnotation(Database.class);
		Annotation an1=Bean1.class.getAnnotation(Datatable.class);
		Annotation an2=Bean1.class.getDeclaredField("name").getAnnotation(Datafield.class);
		Annotation an3=Bean1.class.getDeclaredField("name").getAnnotation(Constraint.Primary.class);
		Assert.assertNotNull(an0);
		Assert.assertNotNull(an1);
		Assert.assertNotNull(an2);
		Assert.assertNotNull(an3);
		
		Assert.assertEquals(db,( (Database)an0).value());
		Assert.assertEquals(table, ((Datatable)an1).value());
		Assert.assertEquals("name01",((Datafield)an2).value());
		
	}

}
