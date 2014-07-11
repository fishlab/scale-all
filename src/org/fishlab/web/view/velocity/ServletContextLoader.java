package org.fishlab.web.view.velocity;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class ServletContextLoader extends ClasspathResourceLoader{
	private ServletContext servletContext;
	public ServletContextLoader(ServletContext servletContext){
		this.servletContext=servletContext;
		String cp=this.servletContext.getRealPath("/");
		System.err.println("realpath = "+cp);
	}
	
	 public synchronized InputStream getResourceStream(String name) throws ResourceNotFoundException {
		 System.err.println(name);
		 if ((name == null) || (name.length() == 0)) {
	            throw new ResourceNotFoundException("No template name provided");
	        }

	        if (name.startsWith("/")) {
	            name = name.substring(1);
	        }
	        
	        return null;
	        

	    }

}
