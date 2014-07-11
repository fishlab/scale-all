package org.fishlab.test.util.orm;

import org.fishlab.util.orm.SubTableNamePrefixHandler;
import org.junit.Test;

public class TestSubTableNamePrefixHandler {
	SubTableNamePrefixHandler subTableNamePrefixHandler=new SubTableNamePrefixHandler(10,'_');
	@Test
	public void s(){
		System.out.println(this.subTableNamePrefixHandler.getPrefix("ha"));
	}
}
