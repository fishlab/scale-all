package org.fishlab.test.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**date format thread local implementation of thread safety
 * 
 * */
public class DateFormatFactory {
	private static final Map<String, ThreadLocal<SimpleDateFormat>> pool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	private static final Object lock = new Object();

	public static SimpleDateFormat getDateFormat(String pattern) {
		ThreadLocal<SimpleDateFormat> tl = pool.get(pattern);
		if (tl == null) {
			synchronized (lock) {
				tl = pool.get(pattern);
				if (tl == null) {
					final String p = pattern;
					tl = new ThreadLocal<SimpleDateFormat>() {
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(p);
						}
					};
					pool.put(p, tl);
				}
			}
		}
		return tl.get();
	}

	public static Date toDate(String dateStr, String pattern) {
		try {
			return getDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}

}