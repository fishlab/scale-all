package org.fishlab.test.date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.fishlab.util.date.Lunar;
import org.junit.Test;


public class TestLunar {
	@Test
	public void test() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		cal.setTimeZone(TimeZone.getDefault());
		System.out.println("公历日期:" + sdf.format(cal.getTime()));
		Lunar lunar = new Lunar();
		lunar.getLunar(cal);
		// System.out.print("农历日期:"+lunar.animalsYear());
		//
		System.out.print("农历日期:");
		System.out.print(lunar.year + "年 ");
		System.out.print(lunar.month + "月 ");
		System.out.print(Lunar.getChinaDayString(lunar.day));
		System.out.println("\n*************");
		System.out.println(lunar);
	}

}
