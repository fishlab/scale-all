package org.fishlab.test.util.digest;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.fishlab.util.digest.EncrypAES;
import org.fishlab.util.digest.EncrypDES;
import org.fishlab.util.digest.EncrypDES3;
import org.fishlab.util.digest.EncrypMD5;
import org.fishlab.util.digest.EncrypRSA;
import org.junit.Test;

public class TestDigest {
	// @Test
	public void des() {
		EncrypDES de1;
		try {
			de1 = new EncrypDES();
			String msg = "郭XX-搞笑相声全集";
			byte[] encontent = de1.Encrytor(msg);
			byte[] decontent = de1.Decryptor(encontent);
			System.out.println("明文是:" + msg);
			System.out.println("加密后:" + new String(encontent));
			System.out.println("解密后:" + new String(decontent));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Test
	public void des3() {
		EncrypDES3 des;
		try {
			des = new EncrypDES3();
			String msg = "郭XX-搞笑相声全集";
			byte[] encontent = des.Encrytor(msg);
			byte[] decontent = des.Decryptor(encontent);
			System.out.println("明文是:" + msg);
			System.out.println("加密后:" + new String(encontent));
			System.out.println("解密后:" + new String(decontent));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Test
	public void aes() {
		try {
			EncrypAES de1 = new EncrypAES();
			String msg = "郭XX-搞笑相声全集";
			byte[] encontent = de1.Encrytor(msg);
			byte[] decontent = de1.Decryptor(encontent);
			System.out.println("明文是:" + msg);
			System.out.println("加密后:" + new String(encontent));
			System.out.println("解密后:" + new String(decontent));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
	public void rsa() {
		try {
			EncrypRSA rsa = new EncrypRSA();
			String msg = "郭XX-精品相声";
			// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器，密钥大小为1024位
			keyPairGen.initialize(1024);
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 得到私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

			// 用公钥加密
			byte[] srcBytes = msg.getBytes();
			byte[] resultBytes = rsa.encrypt(publicKey, srcBytes);

			// 用私钥解密
			byte[] decBytes = rsa.decrypt(privateKey, resultBytes);

			System.out.println("明文是:" + msg);
			System.out.println("加密后是:" + new String(resultBytes));
			System.out.println("解密后是:" + new String(decBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void md5() {
		try {
			String msg = "郭XX-精品相声技术";
			EncrypMD5 md5 = new EncrypMD5();
			byte[] resultBytes = md5.eccrypt(msg);
			System.out.println("明文是：" + msg);
			System.out.println("密文是：" + new String(resultBytes));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
