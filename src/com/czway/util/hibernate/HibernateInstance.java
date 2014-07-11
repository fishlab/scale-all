package com.czway.util.hibernate;

import java.io.File;

import org.fishlab.util.io.FileUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;

//not test yet
public class HibernateInstance{
	private  ThreadLocal<Session> session = new ThreadLocal<Session>();
	private SessionFactory sessionFactory;
	private Configuration cfg;
	public HibernateInstance(){
		this.defaultConfig();
	}
	
	@SuppressWarnings("deprecation")
	public HibernateInstance(String fn){
		File f=new File(fn);
		if (!f.exists()){
			try {
//				f = new File(ClassLoader.getSystemResource(fn).toURI());
				f= FileUtils.getFileFromClassPath(fn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if( f.exists()){
			try {
				this.cfg=new Configuration();
				this.cfg.configure(f);
				this.sessionFactory =this.cfg.buildSessionFactory();
			} catch (Throwable ex) {
				ex.printStackTrace();
				throw new ExceptionInInitializerError(ex);
			}	
		}else {
			System.err.println(fn+"文件不存在");
			System.out.println("使用默认配置文件");
			this.defaultConfig();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void defaultConfig(){
		try {
			this.cfg=new Configuration();
			this.cfg.configure();
			this.sessionFactory =this.cfg.buildSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}	
	}
	
	@SuppressWarnings("deprecation")
	public void addResource(String ...fn){
		File file;
		for(String f:fn){
//			file=new File(f);
			file=FileUtils.getFileFromClassPath(f);
			if(file.exists()){
				System.out.println("添加映射文件:"+f);
//				this.cfg=this.cfg.addResource(f);
				this.cfg.addResource(f);
			}else{
				System.err.println("映射文件不存在:"+f);
			}
		}
		this.sessionFactory=this.cfg.buildSessionFactory();
//		Session s=this.sessionFactory.openSession();
//		session.set(s);
	}

	public Session getSession() throws HibernateException {
		Session s = session.get();
		// Open a new Session, if this Thread has none yet
		if (s == null||!s.isOpen()) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	
	public void closeSession() throws HibernateException {
		Session s = session.get();
		if (s != null)
			s.close();
		session.set(null);
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/*
	 * 
List cats = sess.createSQLQuery(
" select {cat.*}, {kitten.*} from cats cat, cats kitten where kitten.mother = cat.id " )
.addEntity( " cat " , Cat. class )
.addJoin( " kitten " , " cat.kittens " )
.list();
	 */
	public static void main(String args[]){
//		HibernateInstance hu=new HibernateInstance("E:/A/hibernate.cfga.xml");
//		Session s=hu.getSession();
//		s.cre
//		s.g
		
	}
}
