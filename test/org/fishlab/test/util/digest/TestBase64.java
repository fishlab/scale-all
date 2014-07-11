package org.fishlab.test.util.digest;

import org.fishlab.util.digest.Coder;
import org.junit.Test;

public class TestBase64 {
	@Test
	public void test(){
		byte[] key="10x1asd4as".getBytes();
		
		for(int i=0;i<100;i++){
			key[0]=(byte) i;
			try {
				String en=Coder.encryptBASE64(key);
				System.out.println(en);
				System.out.println(new String(Coder.decryptBASE64(en)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
