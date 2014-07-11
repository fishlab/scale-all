package org.fishlab.test.util.digest;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.fishlab.util.digest.Coder;
import org.junit.Test;
public class CoderTest {
	String inputStr = "== Cracked by imxylz (imxylz@gmail.com) from JavaRebel 2.0-M1, 2008 ==";
	byte[] inputData = inputStr.getBytes();
//	@Test
	public void test() throws Exception {
		System.err.println("原文:\n" + inputStr);
		byte[] inputData = inputStr.getBytes();
		String code = Coder.encryptBASE64(inputData);
		System.err.println("BASE64加密后:\n" + code);
		byte[] output = Coder.decryptBASE64(code);
		String outputStr = new String(output);
		System.err.println("BASE64解密后:\n" + outputStr);
		String key = Coder.initMacKey();
		System.err.println("Mac密钥:\n" + key);

		BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));
		System.err.println("MD5:\n" + md5.toString(32));
		System.err.println("MD5 Hex:\n"+Coder.toHexString(Coder.encryptMD5(inputData)));
		BigInteger sha = new BigInteger(Coder.encryptSHA(inputData));
		System.err.println("SHA:\n" + sha.toString(2));
		System.err.println("SHA:\n" + Coder.encryptBASE64(Coder.encryptSHA(inputData)));
		BigInteger mac = new BigInteger(Coder.encryptHMAC(inputData, inputStr));
		System.err.println("HMAC:\n" + mac.toString(16));
	}
	@Test
	public void testSHA() throws Exception{
		byte[] enc=Coder.encryptHMAC(inputData,"AsasdWDSDfcsdGSDFGFASD");
//		Coder.d
		System.out.println(enc.length);
		String sha=null;
		long t1=System.currentTimeMillis();
		for(int i=0;i<1;i++)
		 sha=Coder.toHexString(enc);
		long t2=System.currentTimeMillis();
		System.out.println(t2-t1);
		System.out.println(sha.length());
		System.out.println(Coder.encryptBASE64(enc));
		
		
//		System.out.println(Coder.encryptBASE64("admin:admin".getBytes()));
	}
}
