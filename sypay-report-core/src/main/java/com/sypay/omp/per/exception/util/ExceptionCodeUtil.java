package com.sypay.omp.per.exception.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author dumengchao
 * @date 2014-10-14
 */
public class ExceptionCodeUtil {

	private static Map<String, String> errorMessageMap = new ConcurrentHashMap<String, String>();

	private static final String ERROR_CODE = "/error_code.properties";

	static {
		InputStream errorMessageStream = ExceptionCodeUtil.class
				.getResourceAsStream(ERROR_CODE);
		Properties properties = new Properties();
		try {
			properties.load(errorMessageStream);
		} catch (IOException e) {

		}

		String errorNo = null;
		String errorMessage = null;
		for (Map.Entry<?, ?> entry : properties.entrySet()) {
			if (null != entry.getKey() && null != entry.getValue()) {
				errorNo = entry.getKey().toString();
				errorMessage = entry.getValue().toString();
				errorMessageMap.put(errorNo, errorMessage);
			}
		}
	}


	public static String getErrorMessge(String errorNo) {
		String pattern = errorMessageMap.get(errorNo);
		if (pattern != null) {
			return pattern;
		}
		return "";
	}

}
