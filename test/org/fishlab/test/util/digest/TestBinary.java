package org.fishlab.test.util.digest;

import org.junit.Test;

public class TestBinary {
	@Test
	public void test(){
//		System.out.println(1>>0);
		String str=" ";
		byte []bt=str.getBytes();
//		bt=new byte[]{1,2,3,4};
		for(int i=0;i<bt.length;i++){
//			System.out.println((bt[i]>>1)&1);
			for(int j=7;j>=0;j--){
				if(((bt[i]>>j)&1)==1){
					System.out.print(1);
				}else{
					System.out.print(0);
				}
				
			}
			System.out.print(" ");
		}
		
	}
}
