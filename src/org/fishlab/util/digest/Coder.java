package org.fishlab.util.digest;

import java.io.IOException;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基础加密组件
 * 
 * @version 1.0
 * @since 1.0
 */
//@SuppressWarnings("restriction")
public abstract class Coder {
//	public static final String KEY_MD5 = "MD5";
    public static final String KEY_MD2 = "MD2";
    public static final String KEY_MD5 = "MD5";
    
	public static final String KEY_SHA = "SHA";
    public static final String KEY_SHA1 = "SHA1";
    public static final String KEY_SHA256= "SHA-256";
    public static final String KEY_SHA384="SHA-384";
    public static final String KEY_SHA512 ="SHA-512"; 
	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5  
	 * HmacSHA1  
	 * HmacSHA256  
	 * HmacSHA384  
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
//		return (new BASE64Decoder()).decodeBuffer(key);
//		return BASE64DECODER.decodeBuffer(key);
		return FastBase64.decode(key);
	}
	private static final BASE64Decoder BASE64DECODER=new BASE64Decoder();
	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key)  {
//		return (new BASE64Encoder()).encodeBuffer(key);
//		return BASE64ENCODER.encodeBuffer(key);
		return FastBase64.encode(key);
	}
//	private static final BASE64Encoder BASE64ENCODER=new BASE64Encoder();
	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();

	}
	public static byte[] encrypt(byte[] data,final String algorithm) throws Exception{
		MessageDigest sha = MessageDigest.getInstance(algorithm);
		sha.update(data);
		return sha.digest();
	}
	
	public static MessageDigest getMessageDigest(final String algorithm) throws Exception{
		return MessageDigest.getInstance(algorithm);
	}
	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}

	private static final char[] HEX_CHAR ={ '0', '1', '2', '3', '4', '5', '6', '7','8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F' };

	public static final String toHexString(byte[] bytes) {
		char [] ch=new char[bytes.length*2];
		int j=0;
		for (int i = 0; i < bytes.length; i++) {
			byte ib = bytes[i];
			ch[j++]=HEX_CHAR[(ib >>> 4) & 0X0F];
			ch[j++]=HEX_CHAR[ib & 0X0F];
		}
		return new String(ch,0,j);
	}
}
