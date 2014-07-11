package org.fishlab.util.persistence;

import java.util.List;

public interface Persistence {
	public boolean insert(Object o);

	public <T> T load(Class<T> t, String condition);

	public <T> List<T> select(Class<T> cl);

	public <T> List<T> select(Class<T> cl, String condition);

	public <T> int delete(Class<T> t, String condition);

	public boolean delete(Object o);

	public boolean update(Object u);

	public boolean update(Object u, String... names);
	
	
	public void close();

	

}
