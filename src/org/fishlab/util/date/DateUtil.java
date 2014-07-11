package org.fishlab.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**return the day's beginning date before or after today<br/>
	 * such as param -1 references beiginning of yesterday <br/>
	 * and param 1 references beiginning of tomarrow
	 * */
	public static Date getDayBeginningOf(int d){
		long dec=d*24*60*60*1000L;
		Calendar cl=Calendar.getInstance();
		cl.setTimeInMillis(System.currentTimeMillis()+dec);
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE ,0);
		cl.set(Calendar.SECOND ,0);
		cl.set(Calendar.MILLISECOND ,0);
		return cl.getTime();
	}

}
