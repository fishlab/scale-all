package org.fishlab.web.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class HibernateBaseDAO extends HibernateSessionService{
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> c,int id){
		Session s=this.sessionFactory.getCurrentSession();
		T t=(T) s.get(c, id);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> c){
		Session s=this.sessionFactory.getCurrentSession();
		Query qr=s.createQuery("from "+c.getName());
		List<T> ls=qr.list();
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> queryFor(Class<T> c,String hql){
		Session s=this.sessionFactory.getCurrentSession();
		Query qr=s.createQuery("from "+c.getName()+" where "+hql);
		List<T> ls=qr.list();
		return ls;
	}

}
