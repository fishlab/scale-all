package org.fishlab.test.util.template;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
//import org.apache.velocity.
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;


public class TestVelocity {
	@Test
	public void createCode()
			throws Exception {
			VelocityEngine velocityEngine = new VelocityEngine();
	        velocityEngine.setProperty("input.encoding", "UTF-8");
	        velocityEngine.setProperty("output.encoding", "UTF-8");
			velocityEngine.setProperty(
					"classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			velocityEngine.addProperty(
					VelocityEngine.RESOURCE_LOADER,"classpath");
	        velocityEngine.init();
	        Template template = velocityEngine.getTemplate("x.ff");
	        VelocityContext velocityContext = new VelocityContext();
//	        velocityContext.put("bean", bean);
	        Map<String,String> s=new HashMap<String,String>();
	        s.put("name", "wanghhh");
	        velocityContext.put("map", s);
	        StringWriter stringWriter = new StringWriter();
	        template.merge(velocityContext, stringWriter);
	        System.out.println( stringWriter.toString() );
	    }
}
