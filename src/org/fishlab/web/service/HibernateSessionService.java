package org.fishlab.web.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class HibernateSessionService {
	protected SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	
}
