package com.czway.util.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
@SuppressWarnings("deprecation")
public class HibernateUtil {
//	public static String CFGPATH="/home/wu/web/cfg/";
	
	public HibernateUtil(){}
	private static final SessionFactory sessionFactory;
	static {
		try {
			// Create the SessionFactory
			Configuration cfg=new Configuration();
			//development mode
			cfg.configure("hibernate.dev.cfg.xml");
			sessionFactory =cfg.buildSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			//log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	public static Session currentSession() throws HibernateException {
		Session s = session.get();
		// Open a new Session, if this Thread has none yet
		if (s == null||!s.isOpen()) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	public static void closeSession() throws HibernateException {
		Session s = session.get();
		session.set(null);
		if (s != null)
			s.close();
	}
}
