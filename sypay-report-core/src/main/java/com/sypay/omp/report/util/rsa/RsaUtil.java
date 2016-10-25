package com.sypay.omp.report.util.rsa;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


/**
 * RSA工具类
 * 用同一套公钥秘钥进行签名验签和加解密
 * 公钥加密，私钥解密
 * 私钥签名，公钥解签名
 * 
 * @author 887961 
 * @Date 2016年10月11日 下午5:26:17
 */
public class RsaUtil {
	
	/** 指定加密算法为RSA */
    private static final String ALGORITHM = "RSA";
    /** 密钥长度，用来初始化 */
    private static final int KEYSIZE = 1024;
    
    /** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /**
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;
    
    private static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLYir4F8QNwAZo9xwRZYBJr9FtkIQKI+89k5sWUha5G5j4NBDlXbnOoZwdy2vjbfY7nMzpZ0rJIWCJGB2J22jBmxDPb//nZy5MogwIuIwlsSSakojZQf+9LWsYp++jUcbyOBVnEvHrVgELU0D44kN6rocAHs1oKKCv1MJITc6+JwIDAQAB";
    private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAItiKvgXxA3ABmj3HBFlgEmv0W2QhAoj7z2TmxZSFrkbmPg0EOVduc6hnB3La+Nt9juczOlnSskhYIkYHYnbaMGbEM9v/+dnLkyiDAi4jCWxJJqSiNlB/70taxin76NRxvI4FWcS8etWAQtTQPjiQ3quhwAezWgooK/UwkhNzr4nAgMBAAECgYBRGdSwuHteGM5iAu60/J9RwNiV7NG3aMNKy/kgWrFYEcPHH9x46Tk1DvncqEFletUcSJc+c9ijASW/f2Wokh74KtX2eUGL+h9NRr1ow6MrVCsAafWi/8IJn+oj60dhnqS4jrOzgGqencgNOh/jQOPgP9Eef4ylHOFflRdgFdtV0QJBAPxuCxli8lz3qG7Z7V6PgyTNkkt0p0b5ZH48jPtNAc6ISSIoBJ/2MA9hmJcKsIK5lAo54UnOUw7hdw0lwHNXGDkCQQCNWtMc6Wn2NK9lxrQPRkRkKkOLF2lE3xBm68Mp/47GizRnpahmw6Qt5KLIDAGYjCtLZpGQuIHd3gve/NNiOMlfAkA7iDhV5GAETOQMQJkak/350AGQihJpSqfInb8iBSdH7hxRUd7FWuo6tmmmNX8rVVj0kHHxVheAiuvozd51VTUBAkAibY6hqhAO0UIvDbhpR/n8U3bj963CAnpiNuXv4riJjvoSjHx5mwjvzJIBGzuARhN9Z9voXafzu9M0Wmu5ro8NAkAskU891LVD0RZNS16Hggm4xB0iVVwiBgavYc+vxqR1FXV6O2/lufIp5aMohbaZk2nj7ZAnSC1rYxp6zIbiVKkn";
    
    
    public static PrivateKey getPrivateKey() throws Exception{
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(PRIVATE_KEY)); 
        KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);  
        PrivateKey privkey = keyf.generatePrivate(priPKCS8);
        return privkey;
    }
    
    public static PublicKey getPublicKey() throws Exception{
    	X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(PUBLIC_KEY)); 
        KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);  
        PublicKey publicKey = keyf.generatePublic(pubX509);
        return publicKey;
    }
    
    
    /**
     * 加密方法
     * @param source 源数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String source) throws Exception {
    	byte[] b1 = encryptByPublicKey(source.getBytes("utf-8"));
        return Base64.encodeBase64String(b1);
    }
    
    public static byte[] encryptByPublicKey(byte[] data)  
            throws Exception {   
        Key publicK = getPublicKey();  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;  
    }  
    /**
     * 解密算法
     * @param cryptograph    密文
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph) throws Exception {
    	byte[] b = decryptByPrivateKey(Base64.decodeBase64(cryptograph));
        return new String(b);
    }
    
    
    public static byte[] decryptByPrivateKey(byte[] encryptedData)  
            throws Exception {   
        Key privateK = getPrivateKey(); 
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.DECRYPT_MODE, privateK);  
        int inputLen = encryptedData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  

    /**
	 * 使用私钥文件证书签名，需要传入私钥对象
	 * 
	 * @param srcData
	 *            签名原文
	 * @param key
	 *            私钥对象
	 * @return 签名后的数据
	 * @throws Exception
	 */
	public static String signHex(String srcData, PrivateKey key) throws Exception {
		try {
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(key);
			signature.update(srcData.getBytes("UTF-8"));
			byte[] signedData = signature.sign();
			return byte2HexStr(signedData);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 验证签名 每次调用都传入公钥对象
	 * 
	 * @param srcData
	 *            原文
	 * @param signedData
	 *            签名后的数据
	 * @param publicKey
	 *            公钥对象
	 * @return
	 */
	public static boolean verify(String srcData, String signedData, PublicKey publicKey) {
		try {
			Signature sign = Signature.getInstance("SHA1withRSA");
			sign.initVerify(publicKey);
			sign.update(srcData.getBytes("UTF-8"));
			return sign.verify(hexStr2Bytes(signedData));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    /**
     * 生成密钥对
     * 加密
     * 解密
     * 签名
     * 验签
     * @throws Exception
     */
    private static void generateKeyPair() throws Exception {
        
        
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        keyPairGenerator.initialize(KEYSIZE);
        /** 生成密匙对 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        /** 得到公钥 */
        PublicKey publicKey = keyPair.getPublic();
        
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        System.out.println("生成新公钥:" + publicKeyStr);
        /** 得到私钥 */
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        System.out.println("生成新私钥:" + privateKeyStr);
        
        /** 得到公私钥对象 */
        X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));  
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr)); 
        
        KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);  
        PublicKey pubkey2 = keyf.generatePublic(pubX509);  
        PrivateKey privkey2 = keyf.generatePrivate(priPKCS8);
        
        /** 加密  */
        String source = "我是加密数据";
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubkey2);
        byte[] data = source.getBytes("utf-8");
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        String sourceEncode = Base64.encodeBase64String(encryptedData);
        System.out.println("加密后字符串为:" + sourceEncode);
        /** 解密 */
        byte[] sourceEncodeBytes = Base64.decodeBase64(sourceEncode);
        Cipher cipher1 = Cipher.getInstance(ALGORITHM); 
        cipher1.init(Cipher.DECRYPT_MODE, privkey2);  
        int inputLen1 = sourceEncodeBytes.length;
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();  
        int offSet1 = 0;  
        byte[] cache1;  
        int i1 = 0;  
        // 对数据分段解密  
        while (inputLen1 - offSet1 > 0) {  
            if (inputLen1 - offSet1 > MAX_DECRYPT_BLOCK) {  
                cache1 = cipher1.doFinal(sourceEncodeBytes, offSet1, MAX_DECRYPT_BLOCK);  
            } else {  
                cache1 = cipher1.doFinal(sourceEncodeBytes, offSet1, inputLen1 - offSet1);  
            }  
            out1.write(cache1, 0, cache1.length);  
            i1++;  
            offSet1 = i1 * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out1.toByteArray();  
        out1.close();
        String sourceDecode = new String(decryptedData);
        System.out.println("解密后字符串为:" + sourceDecode);
        /** 签名服务 */
        String signStr = "我是签名数据";
        Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(privkey2);
		signature.update(signStr.getBytes("UTF-8"));
		byte[] signedData = signature.sign();
		String signedStr = byte2HexStr(signedData);
		System.out.println("签名的字符串为:" + signedStr);
		/** 验证签名 */
		Signature sign = Signature.getInstance("SHA1withRSA");
		sign.initVerify(publicKey);
		sign.update(signStr.getBytes("UTF-8"));
		boolean signBoolean = sign.verify(hexStr2Bytes(signedStr));
		System.out.println("验证签名是否正确:" + signBoolean);
    }
	
    
    /**
	 * byte字节数据转换为16进制字符串
	 * 
	 * @param b
	 *            需要转换的字节数据
	 * @return 16进制字符串
	 */
	private static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	/**
	 * 16进制字符串转为字节数组
	 * 
	 * @param src
	 *            16进制字符串
	 * @return 字节数组
	 */
	private static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}
	
    public static void main(String[] args) throws Exception{
    	generateKeyPair();
	}

}
