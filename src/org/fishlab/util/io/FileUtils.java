package org.fishlab.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import com.czway.util.text.EncodingUtils;

public class FileUtils {
	public static File getClasspathFile(String fn){
		File f=null;
		try {
			f=new File(Thread.currentThread().getContextClassLoader().getResource(fn).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return f;
	}
	public static File getFileFromClassPath(String fn){
		File f0,f=new File(fn);
		f0=f;
		if (f.exists()) 
			return f;
		else{
			try {
//				ClassLoader.getSystemClassLoader();
//				System.out.println("futils."+fn);
//				System.getSecurityManager().g
				f=new File(Thread.currentThread().getContextClassLoader().getResource(fn).toURI());
//				f=new File (ClassLoader.getSystemClassLoader().getResource(fn).toURI()  );
			} catch (Exception e) {
				//file maybe not exist
				f=f0;
			}
			
		}
		return f;
	}
	
	public static String convertToString(InputStream is) {
		InputStreamReader reader=null;
		reader = new InputStreamReader(is);	
		StringBuilder sb = new StringBuilder();
		char ch[]=new char[2048];
		int i;
		try {
			while ( (i = reader.read(ch) ) != -1 ) {
				sb.append(ch,0,i );
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String convertToString(InputStream is,long size,String charset) {
		InputStreamReader reader=null;
		try {
			reader = new InputStreamReader(is,charset);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder((int) size);
		char ch[]=new char[2048];
		int i;
		try {
			while ( (i = reader.read(ch) ) != -1 ) {
				sb.append(ch,0,i );
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	public static String convertToString(String fn){
		File f=new File(fn);
		String r="";
		if(f.exists() ){
			InputStream is=null;
			try {
				is=new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (is!=null)
				r = convertToString(is,f.length(),EncodingUtils.stringCoding(is));
		}	
		return r;
	}
	
	public static String convertToString(String fn,String charset){
		File f=new File(fn);
		String r="";
		if(f.exists() ){
			InputStream is=null;
			try {
				is=new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (is!=null)
				r = convertToString(is,f.length(),charset);
		}	
		return r;
	}
	

	public static void main(String[] args) {
//		long t1,t2;
//		t1=System.currentTimeMillis();
//		for(int i=0;i<1000;i++)
//			convertToString("e:/a/init.sql");
//		System.out.println(convertToString("e:/a/init.sql"));
//		String s="";
//		assert s=="1";
//		t2=System.currentTimeMillis();
//		System.out.println("cost:"+(t2-t1));
		System.out.println(FileUtils.getFileFromClassPath("hbm/forum.hbm.xml").length());
	}
	
}