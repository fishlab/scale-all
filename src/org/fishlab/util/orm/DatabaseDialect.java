package org.fishlab.util.orm;

import java.util.Map;

public interface DatabaseDialect {
	public Map<Class<?>,String> getTypeMapping();
//	public boolean isSensitive(String name);
}
