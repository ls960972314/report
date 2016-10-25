package com.sypay.omp.per.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Properties文件读取工具
 * @author Bruce
 *
 */
public class PropertyReader {
	private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class);
	// 静态对象初始化在其它对象之前
	private static Hashtable<String, PropertiesConfiguration> 
		registerConfig = new Hashtable<String, PropertiesConfiguration>();
	private static Hashtable<String, Properties> register = new Hashtable<String, Properties>();

	/**
	 * 根据传入的配置文件路径，获取Properties
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties getProperties(String resourcePath) {
		Properties p = null;
		try {
			p = (Properties) register.get(resourcePath);
			if (p == null) {
				logger.debug("内存中没有该文件："+resourcePath + " 重新加载");
				reloadPropertiesToRegister(resourcePath);
				p = (Properties) register.get(resourcePath);
				logger.debug("重新加载 "+resourcePath + " 文件成功");
			}
		} catch (Exception e) {
			logger.debug("获取Properties对象 throws a "+e.toString());
		}
		return p;
	}

	/**
	 * 加载Properties
	 * @param fileName
	 * @throws IOException
	 */
	private static void reloadPropertiesToRegister(String resourcePath) throws IOException {
		InputStream is = null;
		Properties p = null;
		try {
			is = PropertyReader.class.getClassLoader().getResourceAsStream(resourcePath); ;
			p = new Properties();
			p.load(is);// 加载输入流
			register.put(resourcePath, p);// 将其存放于HashTable缓存
			
		} catch (Exception e) {
			logger.debug("加载Properties对象 throws a "+e.toString());
		} finally {
			is.close();// 关闭输入流
		}
	}
	
	/**
	 * 根据传入的配置文件路径，动态获取Properties
	 * @param resourcePath
	 * @return
	 */
	private static PropertiesConfiguration getDymaticProperties(String resourcePath) {
		InputStream is = null;// 定义输入流is
		PropertiesConfiguration p = null;
		try {
			// 将fileName存于一个HashTable
			p = (PropertiesConfiguration) registerConfig.get(resourcePath);
			if (p == null) {
				try {
					is = new FileInputStream(resourcePath);// 创建输入流
				} catch (Exception e) {
					if (resourcePath.startsWith("/"))
						// 用getResourceAsStream()方法用于定位并打开外部文件。
						is = PropertyReader.class.getResourceAsStream(resourcePath);
					else
						is = PropertyReader.class.getResourceAsStream("/" + resourcePath);
				}
				p = new PropertiesConfiguration();
				p.load(is);// 加载输入流
				// 设定为实时加载
				p.setReloadingStrategy(new FileChangedReloadingStrategy());
				registerConfig.put(resourcePath, p);// 将其存放于HashTable缓存
				is.close();// 关闭输入流
			}
		} catch (Exception e) {
			logger.debug("动态获取Properties对象 throws a "+e.toString());
		}
		return p;
	}
	
	/**
	 * 根据传入的配置文件路径 及 key，获取响应的Value
	 * 
	 * @param fileName
	 * @param strKey
	 * @return
	 */
	public static String getDymaticPropertyValue(String resourcePath, String strKey) {
		PropertiesConfiguration p = getDymaticProperties(resourcePath);
		try {
			return (String) p.getProperty(strKey);
		} catch (Exception e) {
			logger.debug("根据传入的配置文件路径 及 key，获取响应的Value throws a "+e.toString());
		}
		return null;
	}
	
}
