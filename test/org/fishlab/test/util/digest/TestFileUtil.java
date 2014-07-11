package org.fishlab.test.util.digest;

import java.io.File;

import org.fishlab.util.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;


public class TestFileUtil {
	@Test
	public void test(){
		File f=FileUtils.getClasspathFile("x.ff");
		System.out.println(f.getAbsolutePath());
//		Assert.assertNotNull();
	}

}
