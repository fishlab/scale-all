package org.fishlab.test.util.date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.fishlab.util.date.DateUtil;
import org.fishlab.util.date.PrettyDateFormat;

import junit.framework.TestCase;


public class TestDateUtil extends TestCase{
	
	@SuppressWarnings("deprecation")
	public void test1(){
		DateFormat df=new PrettyDateFormat("##HH:mm:dd", "yy-MM-dd HH:mm:dd",Locale.CHINA);
//		DateFormat df=new SimpleDateFormat("#yy-MM-dd a HH:mm:dd",Locale.ITALIAN);
		System.out.println(df.format(System.currentTimeMillis()-1000*5000));
		Date dt=DateUtil.getDayBeginningOf(-1);
		System.out.println(Locale.ENGLISH.toString());
		System.out.println(dt.toLocaleString());
	}

}
