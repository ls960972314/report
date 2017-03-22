package com.sypay.omp.per.util;

import java.util.Properties;


/**
 * 响应前端封装返回信息工具类
 *
 */
public class ResponseMsgUtil {
	private static Properties properties = null;
	private static String PROPERTIES_PATH_zhCN = "/properties/messages_zh_CN.properties";
	
	static {
		if (null == properties) {
			properties = PropertyReader.getProperties(PROPERTIES_PATH_zhCN);
		}
	}
	/**
	 * 
	 * @param constantsResult
	 * @return
	 */
	public static String returnMsg(int constantsResult){
		return getValue("RESULT_MSG_" + constantsResult);
	}
	
	/**
	 * 获取key的value
	 * @param key
	 * @return
	 */
	private static String getValue(String key){
		return properties.getProperty(key);
	}
	
}
