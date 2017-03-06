package com.sypay.omp.report.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private final static Log logger = LogFactory.getLog(JsonUtil.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * @Title: toJson
	 * @Description: 将对象转换为Json数据
	 * @param @param obj
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String toJson(Object obj) {
		StringWriter sw = new StringWriter();

		try {
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			mapper.writeValue(gen, obj);
			gen.close();
		} catch (JsonGenerationException e) {
			logger.error("", e);

		} catch (JsonMappingException e) {
			logger.error("", e);

		} catch (IOException e) {
			logger.error("", e);

		}
		String json = sw.toString();

		return json;
	}

	/**
	 * @Title: toJson
	 * @Description: 将对象转换为Json数据
	 * @param @param obj
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	/*
	 * @SuppressWarnings("deprecation") public static String objectToJson(Object
	 * obj,String filterClass,String ... field) { ObjectMapper mapper = new
	 * ObjectMapper(); StringWriter sw = new StringWriter();
	 * 
	 * try { JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
	 * FilterProvider filters = new
	 * SimpleFilterProvider().addFilter(filterClass,
	 * SimpleBeanPropertyFilter.filterOutAllExcept(field));
	 * mapper.filteredWriter(filters).writeValue(gen, obj); gen.close(); } catch
	 * (JsonGenerationException e) { logger.error("", e);
	 * 
	 * } catch (JsonMappingException e) { logger.error("", e);
	 * 
	 * } catch (IOException e) { logger.error("", e);
	 * 
	 * } String json = sw.toString();
	 * 
	 * return json; }
	 */

	/**
	 * 将JSON字符串 转换为对象
	 * 
	 * @author weiyuanhua
	 * @date 2010-11-18 下午02:52:13
	 * @param jsonStr
	 *            JSON字符串
	 * @param beanClass
	 *            泛型对象
	 * @param field
	 *            对象中需要忽略的属性
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr, Class<T> beanClass) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		return mapper.readValue(jsonStr, beanClass);
	}

	public static Object jsonToObject(String jsonStr, String encoding, Class<?> beanClass) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		return mapper.readValue(jsonStr.getBytes(encoding), beanClass);
	}

	public static Object jsonToCollection(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		JavaType javaType = getCollectionType(collectionClass, elementClasses);
		return mapper.readValue(jsonStr, javaType);
	}

	public static Object jsonToCollection(String jsonStr, String encoding, Class<?> collectionClass, Class<?>... elementClasses) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		JavaType javaType = getCollectionType(collectionClass, elementClasses);
		return mapper.readValue(jsonStr.getBytes(encoding), javaType);
	}

	/**
	 * 获取集合的JavaType
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isBlank(jsonStr)) {
			return Collections.emptyMap();
		}
		return mapper.readValue(jsonStr, Map.class);
	}

	/*
	 * public static void main(String[] args) { BasePayInfo payInfo = null;
	 * payInfo = new CreditPayInfo(); System.out.println(payInfo.getClass());
	 * 
	 * payInfo = new MobilePayInfo(); System.out.println(payInfo.getClass());
	 * 
	 * payInfo = new WegPayInfo(); System.out.println(payInfo.getClass());
	 * 
	 * payInfo = new QueryPayInfo(); System.out.println(payInfo.getClass()); }
	 */
	/**
	 * 把对象转换成json数据
	 * 
	 * @param bean
	 * @param ignoreVar
	 * @return
	 */
	public static String toJson(Object bean, String... ignoreVar) {

		BeanInfo beanInfo = null;
		StringBuilder sBuilder = null;
		try {
			sBuilder = new StringBuilder();
			beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
			sBuilder.append("{");
			for (PropertyDescriptor property : propertyDescriptors) {
				String propertyName = property.getName();
				if (!propertyName.equals("class") && !isContains(propertyName, ignoreVar)) {
					Method readMethod = property.getReadMethod();
					String result = (String) readMethod.invoke(bean, new Object[0]);
					if (result == null) {
						result = "";
					}
					sBuilder.append("\"" + propertyName + "\":\"" + result + "\",");
					logger.debug("\"" + propertyName + "\":\"" + result + "\"");
				}
			}
			String temp = sBuilder.toString();
			if (temp.length() > 0) {
				String result = temp.substring(0, temp.lastIndexOf(","));
				return result + "}";
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.debug("exception" + e.getMessage());
			return null;
		}
	}

	/**
	 * 判断被忽略的字段是否等于当前字段
	 * 
	 * @param propertyName
	 * @param ignoreVar
	 * @return
	 */
	private static boolean isContains(String propertyName, String[] ignoreVar) {
		if (ignoreVar != null && ignoreVar.length > 0) {
			for (String str : ignoreVar) {
				if (propertyName.equals(str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Json响应
	 * @param callback
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	public static void jsonResponse(HttpServletResponse response, Object data) {
		response.setContentType("text/html;charset=UTF-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(toJson(data));
		} catch (Exception e) {
			logger.error("jsonp响应写入失败！ 数据：" + data, e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("输出流关闭异常！", e);
				}
				writer = null;
			}
		}
	}

}
