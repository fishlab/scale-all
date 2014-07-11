package org.fishlab.test.util.template;

import java.util.*;
import java.io.*;

import org.junit.Test;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.*;

public class TestFreeMarker {
	private Configuration cfg;

	public void init() throws Exception {
		// 初始化FreeMarker配置
		// 创建一个Configuration实例
		cfg = new Configuration();
		// 设置FreeMarker的模版文件位置
		cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));
		// cfg.setDirectoryForTemplateLoading(new File("E:/web/templates"));
	}

	public void process() throws Exception {
		long t1, t2;
		t1 = System.currentTimeMillis();
		Map<String, Object> root = new HashMap<String, Object>();
		List<String> l = new ArrayList<String>();
		l.add("123456");
		l.add("147852369");
		l.add("asdqwert");
		root.put("name", "FreeMarker!");
		root.put("msg", "您已经完成了第一个FreeMarker的示例");
		root.put("list", l);
		Template t = cfg.getTemplate("x.ff");
		// t = cfg.getTemplate("f.ftl");
		t.process(root, new OutputStreamWriter(System.err));
		t2 = System.currentTimeMillis();
		System.out.println("time:" + (t2 - t1) + " millions");
	}

	@Test
	public void test() throws Exception {
		init();
		process();
		StringWriter stw = new StringWriter(1000);
		stw.write("102458", 0, 3);
		System.out.println(stw.toString());
	}
}
