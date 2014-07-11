package com.czway.util.hibernate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Hibernate {
//	static {
//		new HibernateUtil();
//	}
	
	@SuppressWarnings("unchecked")
	public static <T>List<T> executeHQL(String hql){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		Query qr=s.createQuery(hql);
		List<T> ls=qr.list();
		try{
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			s.close();
		}
		return  ls;	
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getById(Class<T> c,int id){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		Query qr=s.createQuery("from "+c.getName()+" where id="+id);
		List<T> ls=qr.list();
		try{
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			s.close();
		}
		
		if(ls.size()>0)
			return  ls.get(0);
		else 
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T>List<T> gets(Class<T> c){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		Query qr=s.createQuery("from "+c.getName());
		List<T> ls=qr.list();
		try{
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			s.close();
		}
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public static <T>List<T> gets(Class<T> c,int begin,int num){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		List<T> ls=null;
		Query qr=null;
		try{
			qr=s.createQuery("from "+c.getName());
			qr.setFirstResult(begin);
			qr.setMaxResults(num);
			ls=qr.list();
			ts.commit();
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}finally{
			s.close();
		}
		return ls;
	}
	
	public static <T> void update(T t){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		try{
			s.update(t);
			ts.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			s.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void update(Class<?> c,int id,T t){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		try{
			T t0=(T) s.load(c.getName(),id);
			t0=t;
			s.update(t0);
			ts.commit();
		}catch (Exception e){
			ts.rollback();
		}finally{
			s.close();
		}
	}
	
	public static <T> T insert(T t){
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		try{
			s.save(t);
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			s.close();
		}
		return t;
	}
	
	public static int executeSqlUpdate(String sql){
		int u=0;
		Session s=HibernateUtil.currentSession();
		Transaction ts=s.beginTransaction();
		SQLQuery qr=s.createSQLQuery(sql);
		u=qr.executeUpdate();
		try{
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			s.close();
		}
		return u;
	}
}
