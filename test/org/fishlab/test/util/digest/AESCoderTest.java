package org.fishlab.test.util.digest;

import org.fishlab.util.digest.AESCoder;
import org.junit.Test;

public class AESCoderTest {
	@Test
	public  void test() {
		try {
			// 初始化密钥
			String secretKey = AESCoder.initKeyString();
			System.out.println("密钥为:" + secretKey);
			String s = "我们的大中国";
			// 加密数据
			byte[] encryptData = AESCoder.encrypt(s.getBytes(), secretKey);
			// 解密数据
			byte[] data = AESCoder.decrypt(encryptData, secretKey);
			// 比较
			System.out.println(new String(data).equals(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}