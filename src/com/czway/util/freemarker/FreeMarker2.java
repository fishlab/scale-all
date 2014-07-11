package com.czway.util.freemarker;

import java.util.*;
import java.io.*;
import freemarker.template.*;

public class FreeMarker2 {
	private Configuration cfg;

	public void init() throws Exception {
		// 初始化FreeMarker配置
		// 创建一个Configuration实例
		cfg = new Configuration();
		// 设置FreeMarker的模版文件位置
		cfg.setDirectoryForTemplateLoading(new File("E:/web/templates"));
	}

	public void process() throws Exception {
		long t1,t2;
		t1=System.currentTimeMillis();
		Map<String,Object> root = new HashMap<String,Object>();
		List<String> l=new ArrayList<String>(); 
		l.add("123456");
		l.add("147852369");
		l.add("asdqwert");
		root.put("name", "FreeMarker!");
		root.put("msg", "您已经完成了第一个FreeMarker的示例");
		root.put("list", l);
		Template t = cfg.getTemplate("f.ftl");
//		t = cfg.getTemplate("f.ftl");
		
		t.process(root, new OutputStreamWriter(System.err));
	
		t2=System.currentTimeMillis();
		
		System.out.println("time:"+(t2-t1)+" millions");

	}

	public static void main(String[] args) throws Exception {
		FreeMarker2 hf = new FreeMarker2();
		hf.init();
		hf.process();
		StringWriter stw=new StringWriter(1000);
		stw.write("102458", 0, 3);
		System.out.println(stw.toString());
	}
}
