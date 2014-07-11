package org.fishlab.util.persistence.impl;

public interface SQLMaker {
	public String getSelect(Class<?> cl);

	public String getSelect(Class<?> cl, String condition);

	public String getInsert(Object obj);

	public String getUpdate(Object obj);

	public String getUpdate(Object obj, String... names);

	public String getDelete(Object obj);

	public String getDelete(Class<?> cl, String condition);
	
	
}
