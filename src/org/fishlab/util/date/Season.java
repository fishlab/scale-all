package org.fishlab.util.date;

import java.util.Calendar;
//import java.util.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

public class Season {
	public final static String[] Monthdsp=
		{"初春","仲春","春末","初夏","盛夏","夏末","浅秋","深秋","秋末","初冬","深冬","晚冬"};	
	public final static String[] SolarCalendarMonthdsp=
		{"深冬","晚冬","初春","仲春","春末","初夏","盛夏","夏末","浅秋","深秋","秋末","初冬"};
	public static String getDsp() {
		Calendar c = Calendar.getInstance();
		return Season.getDsp(c);
	}
	private static String getDsp(Calendar c) {
		
		StringBuilder stb = new StringBuilder();
//		StringBuffer stb=new StringBuffer();
		Lunar lunar = new Lunar();
		lunar.getLunar(c);
		int month =lunar.month;
		int hour = c.get(Calendar.HOUR_OF_DAY);
//		stb.append("农历"+lunar.year+"年"+lunar.month+"月"+hour+"时->>");
//		getMonthdsp(stb, month);
		month--;
		stb.append(Season.Monthdsp[month]);
		stb.append("的");
		if (hour>=6&&hour<9) stb.append("清晨");
		else if(hour>=9&&hour<11) stb.append("上午");
		else if(hour>=11&&hour<14) stb.append("正午");
		else if(hour>=14&&hour<18) stb.append("下午");
		else if(hour>=18&&hour<20) stb.append("傍晚");
		else if(hour>=20&&hour<24) stb.append("晚上");
		else if(hour>=0&&hour<4) stb.append("深夜");
//		else if(hour>=4&&hour<6) stb.append("凌晨");
		else stb.append("凌晨");
		return stb.toString();
	}
	protected static void getMonthdsp(StringBuilder stb, int month) {
		switch(month){
		case 1:stb.append("晚冬");break;
		case 2:stb.append("初春");break;
		case 3:stb.append("仲春");break;
		case 4:stb.append("春末");break;
		case 5:stb.append("初夏");break;
		case 6:stb.append("盛夏");break;
		case 7:stb.append("夏末");break;
		case 8:stb.append("浅秋");break;
		case 9:stb.append("深秋");break;
		case 10:stb.append("秋末");break;
		case 11:stb.append("初冬");break;
		case 12:stb.append("深冬");break;
		}
	}

	public static void main(String args[]) {
		 Calendar c=Calendar.getInstance();
		 c.setTimeInMillis(1l);
		long t,t1,t2;
		t=t1=System.currentTimeMillis();
		System.out.println(getDsp());
		for (int i=0;i<1000;i++){
			c.setTimeInMillis(t);
//			getDsp(c);
			System.out.println(getDsp(c));
			t+=2122*52*60*i;}
		t2=System.currentTimeMillis();
		System.out.println("costTime："+(t2-t1));
	}
}
