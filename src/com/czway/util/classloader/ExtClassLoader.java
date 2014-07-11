package com.czway.util.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;


public class ExtClassLoader extends URLClassLoader {
	private String basedir;

	public ExtClassLoader(URL[] urls, URLClassLoader parent,String basedir) {
		super(urls, parent);
		this.basedir=basedir;
	}
	
    protected Class<?> findClass(String name) throws ClassNotFoundException {
    	Class<?> clazz = null; 
    	
    	try{
    	clazz = super.findClass(name);
    	}catch(ClassNotFoundException ex){
	    	if( clazz == null ) 
	    		clazz = _findClass(name); 
	    	if( clazz == null )
	    		throw ex;
    	}
    	return clazz; 
    }
    
    private Class<?> _findClass(String name) throws ClassNotFoundException {
    	Class<?> clazz = null; 
    	String filePath = this.basedir + name.replace(".", File.separator) + ".class";
    	try{
    		FileInputStream fileInputStream = new FileInputStream(new File(filePath));
    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		int size, SIZE = 4096; 
    		byte[] buffer = new byte[SIZE]; 
    		while( (size = fileInputStream.read(buffer)) > 0 ){
    			outputStream.write(buffer, 0, size); 
    		}
    		fileInputStream.close(); 
    		byte[] classBytes = outputStream.toByteArray(); 
    		outputStream.close();
    		
    		clazz = defineClass(name, classBytes, 0, classBytes.length);
    	}catch(Exception ex){
    		throw new ClassNotFoundException(name);
    	}
    	return clazz; 
    }
    
    public static void main(String[] args) throws Exception {
    	URLClassLoader classLoader = (URLClassLoader)Thread.currentThread().getContextClassLoader();
    	ExtClassLoader myClassLoader = new ExtClassLoader( classLoader.getURLs(), classLoader,"E:/A/" ); 
    	Class<?> c1 = myClassLoader.loadClass("java.lang.Runnable"); 
    	System.out.println(c1); 
    	Class<?> c2 = myClassLoader.loadClass("DrawArcs"); 
    	System.out.println(c2); 
    }
 
}
