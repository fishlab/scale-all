package org.fishlab.util.orm.mybatis.test;

import org.fishlab.test.jdbc.TimeTest;

public interface TimeTestMapper {
	public boolean insert(TimeTest tt);
	public TimeTest getTimeTest(int id);
	public boolean delete(int id);
	public int update(TimeTest tt);
}
