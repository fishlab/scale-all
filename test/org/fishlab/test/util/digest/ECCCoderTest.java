package org.fishlab.test.util.digest;

import static org.junit.Assert.*;  

import java.math.BigInteger;  
import java.security.spec.ECFieldF2m;  
import java.security.spec.ECParameterSpec;  
import java.security.spec.ECPoint;  
import java.security.spec.ECPrivateKeySpec;  
import java.security.spec.ECPublicKeySpec;  
import java.security.spec.EllipticCurve;  
import java.util.Map;  

import org.fishlab.util.digest.ECCCoder;
import org.junit.Test;  

/** 
 *  
 * @author 梁栋 
 * @version 1.0 
 * @since 1.0 
 */  
public class ECCCoderTest {  

    @Test  
    public void test() throws Exception {  
        String inputStr = "abc";  
        byte[] data = inputStr.getBytes();  

        Map<String, Object> keyMap = ECCCoder.initKey();  

        String publicKey = ECCCoder.getPublicKey(keyMap);  
        String privateKey = ECCCoder.getPrivateKey(keyMap);  
        System.err.println("公钥: \n" + publicKey);  
        System.err.println("私钥： \n" + privateKey);  

        byte[] encodedData = ECCCoder.encrypt(data, publicKey);  

        byte[] decodedData = ECCCoder.decrypt(encodedData, privateKey);  

        String outputStr = new String(decodedData);  
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);  
        assertEquals(inputStr, outputStr);  
    }  
}  
