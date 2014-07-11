package org.fishlab.test.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.fishlab.util.date.PrettyDateFormat;
import org.junit.Test;

public class MainTest {
	private final static PrettyDateFormat simpleDateFormat = 
			new PrettyDateFormat("##aH点", "yy-MM-dd a H点");
	private   static int dd=0;
//	new FastDateFormat();
	/**
	 * @param args
	 * @throws IOException
	 */
	@Test
	public void main() {
		Task t1 = new Task(new Date());
		Task t2 = new Task(new Date());
		for(int i=0;i<1;i++)
		new Thread(t1).start();


	}

	private static void p(Object o) {
		System.out.println(dd++ +" thread id= " + Thread.currentThread().getId()
				+ " : " + o);
	}

	private static class Task implements Runnable {
		private Date date;

		private Task(Date date) {
//			this.pattern = patt
			this.date=date;
		}

		@Override
		public void run() {
			for (int i = 0; i < 200; i++) {
				try {
//					Date date = simpleDateFormat.parse(pattern);
					String format=simpleDateFormat.format(date);
					p(format);
				} catch (Exception e) {
					p(e);
				}
			}
		}
	}
}