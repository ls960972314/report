package com.sypay.omp.report.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化工具
 * @author sfhq913
 *
 */
public class SerializeUtil {
	private static final Logger LOG = LoggerFactory
			.getLogger(SerializeUtil.class);
	/**
	 * 序列化
	 * @param object 序列号对象
	 * @return byte[]
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			LOG.error("serialize failed", e);
		}finally{
			try {
				if(oos!=null){
					
				oos.close();
				}
			} catch (IOException e) {
				LOG.error("close stream failed ", e);
				
			}
		}
		return null;
	}
	/**
	 * 反序列化
	 * @param bytes  反序列化的数据
	 * @return Object
	 */
	public static Object unserialize(byte[] bytes) {
		if(bytes==null){
			return null;
		}
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			LOG.error("unserialize failed ", e);
		}finally{
			try {
				if(ois!=null){
				ois.close();
				}
			} catch (IOException e) {
				LOG.error("closed stream failed", e);
				
			}
		}
		return null;
	}
	

}