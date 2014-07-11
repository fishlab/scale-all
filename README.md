scale-all
=========


some code

```java
//java 代码
package org.fishlab.util.orm.mybatis.test;

import org.fishlab.test.jdbc.TimeTest;
import org.fishlab.util.orm.MappingFileGenerator;
import org.fishlab.util.orm.hibernate.HibernateMappingFileGenerator;
import org.fishlab.util.orm.mybatis.MybatisMappingFileGenerator;
import org.junit.Test;


public class TestMappingFileGenerator {
	private MappingFileGenerator mmf=new MybatisMappingFileGenerator();
	
	private MappingFileGenerator hmf=new HibernateMappingFileGenerator();
	@Test
	public void test(){
//		mmf.generate(TimeTestMapper.class);
		hmf.generate(TimeTest.class);
	}

}

```