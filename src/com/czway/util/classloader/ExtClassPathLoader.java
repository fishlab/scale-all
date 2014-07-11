package com.czway.util.classloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public final class ExtClassPathLoader {
	/** URLClassLoader的addURL方法 */
	private static Method addURL = initAddMethod();

	/** 初始化方法 */
	private static final Method initAddMethod() {
		try {
			Method add = URLClassLoader.class.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			add.setAccessible(true);
			return add;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static URLClassLoader system = (URLClassLoader) ClassLoader
			.getSystemClassLoader();

	/**
	 * 循环遍历目录，找出所有的JAR包
	 */
	private static final void loopFiles(File file, List<File> files) {
		if (file.isDirectory()) {
			File[] tmps = file.listFiles();
			for (File tmp : tmps) {
				loopFiles(tmp, files);
			}
		} else {
			if (file.getAbsolutePath().endsWith(".jar")
					|| file.getAbsolutePath().endsWith(".zip")) {
				files.add(file);
			}
		}
	}
	
	public static void loadJarFile(String str) {
		File f=new File(str);
		if (f.exists()){
			loadFile(f);
		}
	}

	/**
	 * <pre>
	 * 加载JAR文件
	 * </pre>
	 * @param file
	 */
	public static final void loadFile(File file) {
		try {
			addURL.invoke(system, new Object[] { 
					file.toURI().toURL() 
					});
//			System.out.println("加载JAR包：" + file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这是我自己添加的方法，用于启动其他程序
	 * 
	 * @param className
	 *            类名
	 * @param methodName
	 *            其他程序的运行接口
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static void runClass(String className, String methodName)
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {

		Class<?> classType = Class.forName(className);
		Object obj = classType.newInstance();
		Method method = classType
				.getDeclaredMethod(methodName, (Class<?>) null);
		method.invoke(obj, (Class<?>) null);
	}

	/**
	 * <pre>
	 * 从一个目录加载所有JAR文件
	 * </pre>
	 * @param path
	 */
	public static final void loadJarPath(String path) {
		List<File> files = new ArrayList<File>();
		File lib = new File(path);
		loopFiles(lib, files);
		for (File file : files) {
			loadFile(file);
		}
	}
	
	public static final boolean loadClassPath(String dir){
		File f=new File (dir);
		if (f.exists() ){
			if(f.isDirectory() )
				loadFile(f);
			else
				f=f.getParentFile();
		}
		else{
			return false;
		}
		return true;
	}
//test
	public static void main(String ags[]) throws URISyntaxException, IOException, ClassNotFoundException {
//		loadJarPath("F:/"); // 根据jar文件夹路径，遍历加载jar文件
//		loadFilePath("E:/A");
//		loadClassPath("E:/A");
		Class <?> c=Class.forName("org.w3c.dom.Document");
		URL r=ClassLoader.getSystemClassLoader().getResource("");
		System.out.println(r.toURI().toString());
		System.out.println(c.getPackage());

	}


}