package org.fishlab.util.orm;

public interface MappingFileGenerator {
	public void generate(Class<?> mapper);
	public void setOutputPath(String path);
}
