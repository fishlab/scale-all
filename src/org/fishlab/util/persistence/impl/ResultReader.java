package org.fishlab.util.persistence.impl;

import java.util.List;
import java.sql.ResultSet;

public interface ResultReader {
	public <T> List<T> readResultset(ResultSet rst,Class<T> cl);
	public <T> T load(ResultSet rst,Class<T> cl);
}
