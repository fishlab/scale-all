package com.czway.util.configuration;

import java.util.Map;

public interface Configuration {
	public Map<?,?> getConfigurationMap(String s);
	public <T> T getConfigurationBean(Class<T> t);
	public <T> T getConfigurationBean(Class<T> t,String name);
	public <T> void writeConfiguratioin(Object c,String fn);

}
