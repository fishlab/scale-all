package org.fishlab.test.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.fishlab.util.date.PrettyDateFormat;
import org.junit.Test;

public class DateFormatTest {
	@Test
	public void test() throws Exception {
		long curTime = System.currentTimeMillis();
		format(curTime, "#aH点", "yy-MM-dd a H点");
		format(curTime, "##a H点", "yy-MM-dd a H点");
		format(curTime, "# HH:mm:dd", "yy-MM-dd a HH:mm:dd");
		format(curTime, "# a HH:mm:dd", "yy-MM-dd HH:mm:dd");
		format(curTime, "## HH:mm", "yy-MM-dd HH:mm");
		format(curTime, "## a HH:mm", "yy-MM-dd a HH:mm");
		format(curTime, "##", "yyyy-MM-dd");
		format(curTime, "@", "yyyy-MM-dd HH:mm:dd");
		
	}

	public static void format(long curTime, String format, String fullFormat) {
		System.out.println("    format: " + format);
		System.out.println("fullFormat: " + fullFormat);
		System.out.println();
		// curTime-=1000*100;
		Date date2 = new Date(curTime + 30 * 1000L); // 30秒前
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		System.out.print(sdf.format(date2) + " 格式化为 : ");
		System.out.println(new PrettyDateFormat(format, fullFormat)
				.format(curTime));

		date2 = new Date(curTime - 3600 * 1000L * 6); // 六小时前
		System.out.print(sdf.format(date2) + " 格式化为 : ");
		
		System.out.println(new PrettyDateFormat(format, fullFormat)
				.format(date2));

		date2 = new Date(curTime - 3600 * 1000L * 20); // 20小时前
		System.out.print(sdf.format(date2) + " 格式化为 : ");
		System.out.println(new PrettyDateFormat(format, fullFormat)
				.format(date2));

		date2 = new Date(curTime - 3600 * 1000L * 54); // 54小时前
		System.out.print(sdf.format(date2) + " 格式化为 : ");
		System.out.println(new PrettyDateFormat(format, fullFormat)
				.format(date2));

		date2 = new Date(curTime - 3600 * 1000L * 78); // 78小时前
		System.out.print(sdf.format(date2) + " 格式化为 : ");
		System.out.println(new PrettyDateFormat(format, fullFormat)
				.format(date2));
		System.out.println("========================================================");

	}

}