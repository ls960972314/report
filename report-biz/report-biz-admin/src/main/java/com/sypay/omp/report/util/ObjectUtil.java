package com.sypay.omp.report.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 判断实体类中字段为空工具类
 * 
 * @author dumengchao
 * @date 2013-12-04
 * 
 */
public class ObjectUtil {

	private final static Log logger = LogFactory.getLog(ObjectUtil.class);

	/**
	 * 判断实体类是否为空
	 * 
	 * @param bean
	 *            要判断的实体类
	 * @param ignoreVar
	 *            忽略的字段列表
	 * @return
	 */
	public static boolean isNull(Object bean, String... ignoreVar) {

		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor propertyDescriptors[] = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String propertyName = property.getName();

				if (!propertyName.equals("class")
						&& !isContains(propertyName, ignoreVar)) {
					Method readMethod = property.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result == null || "".equals(result)) {
						return true;
					}
					logger.debug(propertyName + " = " + result);
				}
			}
		} catch (Exception e) {
			logger.debug("exception");
			return false;
		}

		return false;
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
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		boolean flag = false;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = false;
			}
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * <p>
	 * Returns a default value if the object passed is <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.defaultIfNull(null, null)      = null
	 * ObjectUtils.defaultIfNull(null, "")        = ""
	 * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
	 * ObjectUtils.defaultIfNull("abc", *)        = "abc"
	 * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
	 * </pre>
	 * 
	 * @param object
	 *            the <code>Object</code> to test, may be <code>null</code>
	 * @param defaultValue
	 *            the default value to return, may be <code>null</code>
	 * @return <code>object</code> if it is not <code>null</code>, defaultValue
	 *         otherwise
	 */
	public static Object defaultIfNull(Object object, Object defaultValue) {
		return object != null ? object : defaultValue;
	}

	/**
	 * <p>
	 * Compares two objects for equality, where either one or both objects may
	 * be <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.equals(null, null)                  = true
	 * ObjectUtils.equals(null, "")                    = false
	 * ObjectUtils.equals("", null)                    = false
	 * ObjectUtils.equals("", "")                      = true
	 * ObjectUtils.equals(Boolean.TRUE, null)          = false
	 * ObjectUtils.equals(Boolean.TRUE, "true")        = false
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
	 * </pre>
	 * 
	 * @param object1
	 *            the first object, may be <code>null</code>
	 * @param object2
	 *            the second object, may be <code>null</code>
	 * @return <code>true</code> if the values of both objects are the same
	 */
	public static boolean equals(Object object1, Object object2) {
		if (object1 == object2) {
			return true;
		}
		if ((object1 == null) || (object2 == null)) {
			return false;
		}
		return object1.equals(object2);
	}

	/**
	 * <p>
	 * Compares two objects for inequality, where either one or both objects may
	 * be <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.notEqual(null, null)                  = false
	 * ObjectUtils.notEqual(null, "")                    = true
	 * ObjectUtils.notEqual("", null)                    = true
	 * ObjectUtils.notEqual("", "")                      = false
	 * ObjectUtils.notEqual(Boolean.TRUE, null)          = true
	 * ObjectUtils.notEqual(Boolean.TRUE, "true")        = true
	 * ObjectUtils.notEqual(Boolean.TRUE, Boolean.TRUE)  = false
	 * ObjectUtils.notEqual(Boolean.TRUE, Boolean.FALSE) = true
	 * </pre>
	 * 
	 * @param object1
	 *            the first object, may be <code>null</code>
	 * @param object2
	 *            the second object, may be <code>null</code>
	 * @return <code>false</code> if the values of both objects are the same
	 * @since 2.6
	 */
	public static boolean notEqual(Object object1, Object object2) {
		return ObjectUtils.equals(object1, object2) == false;
	}

	/**
	 * <p>
	 * Gets the hash code of an object returning zero when the object is
	 * <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.hashCode(null)   = 0
	 * ObjectUtils.hashCode(obj)    = obj.hashCode()
	 * </pre>
	 * 
	 * @param obj
	 *            the object to obtain the hash code of, may be
	 *            <code>null</code>
	 * @return the hash code of the object, or zero if null
	 * @since 2.1
	 */
	public static int hashCode(Object obj) {
		return (obj == null) ? 0 : obj.hashCode();
	}

	/**
	 * <p>
	 * Gets the toString that would be produced by <code>Object</code> if a
	 * class did not override toString itself. <code>null</code> will return
	 * <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.identityToString(null)         = null
	 * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
	 * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
	 * </pre>
	 * 
	 * @param object
	 *            the object to create a toString for, may be <code>null</code>
	 * @return the default toString text, or <code>null</code> if
	 *         <code>null</code> passed in
	 */
	public static String identityToString(Object object) {
		if (object == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		identityToString(buffer, object);
		return buffer.toString();
	}

	/**
	 * <p>
	 * Appends the toString that would be produced by <code>Object</code> if a
	 * class did not override toString itself. <code>null</code> will throw a
	 * NullPointerException for either of the two parameters.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
	 * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
	 * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
	 * </pre>
	 * 
	 * @param buffer
	 *            the buffer to append to
	 * @param object
	 *            the object to create a toString for
	 * @since 2.4
	 */
	public static void identityToString(StringBuffer buffer, Object object) {
		if (object == null) {
			throw new NullPointerException("Cannot get the toString of a null identity");
		}
		buffer.append(object.getClass().getName()).append('@')
				.append(Integer.toHexString(System.identityHashCode(object)));
	}

	/**
	 * <p>
	 * Appends the toString that would be produced by <code>Object</code> if a
	 * class did not override toString itself. <code>null</code> will return
	 * <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.appendIdentityToString(*, null)            = null
	 * ObjectUtils.appendIdentityToString(null, "")           = "java.lang.String@1e23"
	 * ObjectUtils.appendIdentityToString(null, Boolean.TRUE) = "java.lang.Boolean@7fa"
	 * ObjectUtils.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
	 * </pre>
	 * 
	 * @param buffer
	 *            the buffer to append to, may be <code>null</code>
	 * @param object
	 *            the object to create a toString for, may be <code>null</code>
	 * @return the default toString text, or <code>null</code> if
	 *         <code>null</code> passed in
	 * @since 2.0
	 * @deprecated The design of this method is bad - see LANG-360. Instead, use
	 *             identityToString(StringBuffer, Object).
	 */
	public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
		if (object == null) {
			return null;
		}
		if (buffer == null) {
			buffer = new StringBuffer();
		}
		return buffer.append(object.getClass().getName()).append('@')
				.append(Integer.toHexString(System.identityHashCode(object)));
	}
	
	/**
	 * 对象转字符串
	 * @param obj
	 * 			目标对象
	 * 
	 * @return
	 */
	public static String toString(Object obj){
		return toString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**
	 * 对象转字符串
	 * @param obj
	 * 			目标对象
	 * 
	 * @param style
	 * 			@see org.apache.commons.lang.builder.ToStringStyle
	 * 
	 * @return
	 */
	public static String toString(Object obj, ToStringStyle style){
		if(null == obj){
			return "";
		}
		
		StringBuilder defaultObjReg = new StringBuilder();
		defaultObjReg.append("^[\\w\\.]*");
		defaultObjReg.append(obj.getClass().getSimpleName());
		defaultObjReg.append("@");
		defaultObjReg.append("[a-zA-Z0-9]+");
		defaultObjReg.append("$");
		
		// 未重写toString
		if(String.valueOf(obj).matches(defaultObjReg.toString())){
			return ReflectionToStringBuilder.toString(obj,
					style);
		}
		
		return obj.toString();
	}

	/**
	 * <p>
	 * Gets the <code>toString</code> of an <code>Object</code> returning a
	 * specified text if <code>null</code> input.
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.toString(null, null)           = null
	 * ObjectUtils.toString(null, "null")         = "null"
	 * ObjectUtils.toString("", "null")           = ""
	 * ObjectUtils.toString("bat", "null")        = "bat"
	 * ObjectUtils.toString(Boolean.TRUE, "null") = "true"
	 * </pre>
	 * 
	 * @see StringUtils#defaultString(String,String)
	 * @see String#valueOf(Object)
	 * @param obj
	 *            the Object to <code>toString</code>, may be null
	 * @param nullStr
	 *            the String to return if <code>null</code> input, may be null
	 * @return the passed in Object's toString, or nullStr if <code>null</code>
	 *         input
	 * @since 2.0
	 */
	public static String toString(Object obj, String nullStr) {
		String result = toString(obj);
		return StringUtils.isBlank(result) ? nullStr : result;
	}

	// Min/Max
	// -----------------------------------------------------------------------
	/**
	 * Null safe comparison of Comparables.
	 * 
	 * @param c1
	 *            the first comparable, may be null
	 * @param c2
	 *            the second comparable, may be null
	 * @return <ul>
	 *         <li>If both objects are non-null and unequal, the lesser object.
	 *         <li>If both objects are non-null and equal, c1.
	 *         <li>If one of the comparables is null, the non-null object.
	 *         <li>If both the comparables are null, null is returned.
	 *         </ul>
	 */
	public static Object min(Comparable c1, Comparable c2) {
		return (compare(c1, c2, true) <= 0 ? c1 : c2);
	}

	/**
	 * Null safe comparison of Comparables.
	 * 
	 * @param c1
	 *            the first comparable, may be null
	 * @param c2
	 *            the second comparable, may be null
	 * @return <ul>
	 *         <li>If both objects are non-null and unequal, the greater object.
	 *         <li>If both objects are non-null and equal, c1.
	 *         <li>If one of the comparables is null, the non-null object.
	 *         <li>If both the comparables are null, null is returned.
	 *         </ul>
	 */
	public static Object max(Comparable c1, Comparable c2) {
		return (compare(c1, c2, false) >= 0 ? c1 : c2);
	}

	/**
	 * Null safe comparison of Comparables. {@code null} is assumed to be less
	 * than a non-{@code null} value.
	 * 
	 * @param c1
	 *            the first comparable, may be null
	 * @param c2
	 *            the second comparable, may be null
	 * @return a negative value if c1 < c2, zero if c1 = c2 and a positive value
	 *         if c1 > c2
	 * @since 2.6
	 */
	public static int compare(Comparable c1, Comparable c2) {
		return compare(c1, c2, false);
	}

	/**
	 * Null safe comparison of Comparables.
	 * 
	 * @param c1
	 *            the first comparable, may be null
	 * @param c2
	 *            the second comparable, may be null
	 * @param nullGreater
	 *            if true <code>null</code> is considered greater than a Non-
	 *            <code>null</code> value or if false <code>null</code> is
	 *            considered less than a Non-<code>null</code> value
	 * @return a negative value if c1 < c2, zero if c1 = c2 and a positive value
	 *         if c1 > c2
	 * @see java.util.Comparator#compare(Object, Object)
	 * @since 2.6
	 */
	public static int compare(Comparable c1, Comparable c2, boolean nullGreater) {
		if (c1 == c2) {
			return 0;
		} else if (c1 == null) {
			return (nullGreater ? 1 : -1);
		} else if (c2 == null) {
			return (nullGreater ? -1 : 1);
		}
		return c1.compareTo(c2);
	}
	
	/**
	 * 去除对象的字符串属性头尾空格
	 * @param o
	 */
	public static Object trim(Object o){
		if(null == o){
			return o;
		}
		
		Object result = o;
		if(o instanceof String){
			result = ((String) o).trim();
		} else {
			
			// 基本数据类型
			if(ObjectUtil.isBasicDataType(o.getClass())){
				return o;
			}
			if(o instanceof Map){
				Map temp = (Map) o;
				for(Object key : temp.keySet()){
					Object value = temp.get(key);
					temp.put(key, trim(value));
				}
				result = temp;
			} else {
				
				Class<? extends Object> clazz = o.getClass();
				String[] properties = BeanUtil.getPropertyNames(clazz);
				if(null != properties && properties.length > 0){
					for(String property : properties){
						Object value = BeanUtil.getPropertyValue(o, property);
						if(null != value){
							BeanUtil.copyProperty(o, property, trim(value));
						}
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 判断对象是否是基本数据类型
	 * @return
	 */
	public static boolean isBasicDataType(Class clazz){
		boolean result = false;
		if(clazz.isPrimitive()){
			result = true;
		}else if(String.class.equals(clazz)){
			return result;
		}else{
			
			try {
				Field[] fields = clazz.getFields();
				if(null != fields){
					for(Field field : fields){
						if("TYPE".equals(field.getName())){
							result = ((Class) field.get(null)).isPrimitive();
							break;
						}
					}
				}
			} catch (Exception e) {
				logger.error("类" + clazz.getName() + "不是基本数据类型", e);
			}
		}
		return result;
	}
	
	/**
	 * 获取集合的元素类型
	 * @param collection
	 * @return
	 */
	public static List<Class<?>> getParameterClass(Class<?> genericClass){
		List<Class<?>> clazz = Collections.emptyList();
		Type type = genericClass.getGenericSuperclass();
		if(null != type && type instanceof ParameterizedType){
			ParameterizedType pt = (ParameterizedType) type;
			Type[] types = pt.getActualTypeArguments();
			if(null != types && types.length > 0){
				clazz = new ArrayList<Class<?>>(types.length);
				for(Type temp : types){
					clazz.add((Class<?>) temp);
				}
			}
		} else{
			Type[] interfaces = genericClass.getGenericInterfaces();
			if(null != interfaces && interfaces.length > 0){
				for(Type interfaceType : interfaces){
					if(interfaceType instanceof ParameterizedType){
						ParameterizedType pt = (ParameterizedType) interfaceType;
						Type[] types = pt.getActualTypeArguments();
						if(null != types && types.length > 0){
							clazz = new ArrayList<Class<?>>(types.length);
							for(Type temp : types){
								clazz.add((Class<?>) temp);
							}
						}
					} else{
						clazz.addAll(getParameterClass((Class<?>) type));
					}
				}
			}
		}
		return clazz;
	}
	
}
