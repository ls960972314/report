package com.sypay.omp.report.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MD5工具类
 * @author lishun
 *
 */
public class MD5 {
	protected static final Log logger = LogFactory.getLog(MD5.class);

	static MessageDigest mDigest = null;

	static {
		/* 获取消息摘要MessageDigest抽象类的实例 */
		try {
			mDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.debug("获取MD5 digest 异常");
		}
	}

	public String encodePassword(String rawPass, Object salt) {
		logger.debug(rawPass + "========================");
		return getMD5String(rawPass);
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);
		logger.debug(pass1 + "========================");
		return pass1.equals(pass2);
	}

	public static String getMD5String(String source) {
		return encodeStr(source);
	}

	/* 加密 */
	private static String encodeStr(String source) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return result.toUpperCase();
	}

	/**
	 * MD5 加密 - 调用被动注册接口时用
	 * 
	 * @param info 需要MD5加密的字符串
	 * @return
	 */
	public static String encryptToMD5(byte[] info) {
		/* MessageDegist计算摘要后 得到的是Byte数组 */
		byte[] digesta = null;
		/* 添加需要进行计算摘要的对象（字节数组）*/
		mDigest.update(info);
		/* 计算摘要 */
		digesta = mDigest.digest();
		/* 将字节数组转换为String并返回 */
		return bytes2Hex(digesta);
	}

	/**
	 * 将2进制字节数组转换为16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytes2Hex(byte[] bytes) {
		/* 16进制结果 */
		String hex = "";
		/* 存放byte字节对象的临时变量 */
		String temp = "";

		/* 对字节数组元素进行处理 */
		for (int i = 0; i < bytes.length; i++) {
			/* byte的取值范围是从-127-128，需要& 0xFF (11111111) 使得byte原来的负值变成正的 */
			temp = Integer.toHexString(bytes[i] & 0xFF);
			/* 长度为1，那么需要补充一位 0 */
			if (temp.length() == 1) {
				hex = hex + "0" + temp;
			} else {
				/* 长度为2，直接拼接即可 */
				hex = hex + temp;
			}
		}
		/* 返回大写的字符串 */
		return hex.toUpperCase();
	}

	public static void main(String[] args) {
        System.out.println(getMD5String("123456"));
    }
}
