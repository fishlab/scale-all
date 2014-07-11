package org.fishlab.util.configuration.test;

import java.util.Map;

import junit.framework.TestCase;

import com.czway.util.xml.XmlUtils;

public class TestXmlUtils extends TestCase{
	public void test(){
		Map<String,Object> mos=XmlUtils.dom2Map("S:/ie6.html");
		System.out.println(mos);
		
	}
	
	public static void main(String a[]){
		new TestXmlUtils().test();
	}
}
