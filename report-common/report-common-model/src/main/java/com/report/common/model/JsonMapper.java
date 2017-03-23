package com.report.common.model;

import java.io.IOException;
import java.text.DateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper. 封装不同的输出风格,
 * 使用不同的builder函数创建实例.
 * @author lishun
 * @version 1.0
 */
public class JsonMapper 
{

	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

	private ObjectMapper mapper;

	public JsonMapper() 
	{

		this(Inclusion.NON_NULL, null, false);
	}

	public JsonMapper(Inclusion inclusion) 
	{
		this(inclusion, null, false);
	}

	public JsonMapper(Inclusion inclusion, DateFormat df)
	{
		this(inclusion, df, false);
	}
	
	public JsonMapper(Inclusion inclusion, DateFormat df, boolean replaceNull) 
	{
		mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
//		mapper.setSerializationInclusion(inclusion);
//		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
//		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		// 禁止使用int代表Enum的order()來反序列化Enum
//		mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
		// 允许单引号
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.setDateFormat(df);
		if (replaceNull) {
			// null 转换为 ""
			mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() 
			{
				@Override
				public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException 
				{
					jgen.writeString("");
				}

			});
		}
	}

	/**
	 * 创建输出全部属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNormalMapper() 
	{
		return new JsonMapper(Inclusion.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonNullMapper() 
	{
		return new JsonMapper(Inclusion.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonDefaultMapper() 
	{
		return new JsonMapper(Inclusion.NON_DEFAULT);
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonEmptyMapper() 
	{
		return new JsonMapper(Inclusion.NON_EMPTY);
	}

	/**
	 * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) 
	{
		try 
		{
			return mapper.writeValueAsString(object);
		} 
		catch (IOException e) 
		{
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函数constructParametricType构造类型.
	 * 
	 * @see #constructParametricType(Class, Class...)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) 
	{
		if (StringUtils.isEmpty(jsonString)) 
		{
			return null;
		}
		try 
		{
			return mapper.readValue(jsonString, clazz);
		} 
		catch (IOException e) 
		{
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 * 
	 * @see #constructParametricType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) 
	{
		if (StringUtils.isEmpty(jsonString)) 
		{
			return null;
		}
		try
		{
			return (T) mapper.readValue(jsonString, javaType);
		} 
		catch (IOException e) 
		{
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 构造泛型的Type如List<MyBean>,
	 * 则调用constructParametricType(ArrayList.class,MyBean.class)
	 * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) 
	{
		return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	/**
	 * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
	 */
	@SuppressWarnings("unchecked")
	public <T> T update(T object, String jsonString) 
	{
		try 
		{
			return (T) mapper.readerForUpdating(object).readValue(jsonString);
		} 
		catch (JsonProcessingException e) 
		{
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} 
		catch (IOException e) 
		{
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}

	/**
	 * 输出JSONP格式数据.
	 */
	public String toJsonP(String functionName, Object object) 
	{
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 设定是否使用Enum的toString函数來读写Enum, 为False时候使用Enum的name()函数來读写Enum, 默认为False.
	 * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
	 */
	public void setEnumUseToString(boolean value) 
	{
//		mapper.configure(SerializationConfig.thiWRITE_ENUMS_USING_TO_STRING, value);
//		mapper.configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, value);
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() 
	{
		return mapper;
	}
}
