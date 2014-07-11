package org.fishlab.util.langs;

import com.czway.util.langs.MyRandom;

public class MyRandomTest {
	public static void main(String[] args) {
		MyRandom rd = new MyRandom();
		boolean b;
		int bt = 0;
		int bf = 0;
		// for (int i=0;i<1000;i++){
		// bt=rd.nextInt(100);
		// System.out.print(bt);
		// System.out.print(" ");
		// }
		//
		long t1,t2;
		t1=System.currentTimeMillis();
		for(int j=0;j<100;j++){
		for (int i = 0; i < 1000; i++) {
			b = rd.nextBoolean(200);
			if (b)
				bt++;
			else
				bf++;
		}
		System.out.println(bt / 1000.0);
		System.out.println(bf);
		bt=0;
		bf=0;
		}
		
		t2=System.currentTimeMillis();
		System.out.println("cost :"+(t2-t1)+"ms");
		
	}


}
