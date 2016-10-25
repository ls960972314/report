package com.sypay.omp.report.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javassist.Modifier;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class BeanUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

    // 默认格式化器缓存
    private static Map<Class, Formatter> formatterMap = new HashMap<Class, Formatter>();

    // 转换器
    private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

    static {

        // // 默认Double精确到小数点后两位
        // formatterMap.put(Double.class, new AmountFormatter());

        // 默认使用Apache的转换器
        ConvertUtilsBean convertUtilsBean = BeanUtilsBean.getInstance().getConvertUtils();
        try {
            Field field = ConvertUtilsBean.class.getDeclaredField("converters");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Map<Class, Converter> beanUtilsMap = (Map<Class, Converter>) field.get(convertUtilsBean);
            converterMap.putAll(beanUtilsMap);
        } catch (Exception e) {
            LOG.error("获取类ConvertUtilsBean的属性converters异常！", e);
        }
    }

    /**
     * 注册转换器
     * 
     * @param converter
     * @param targetType
     */
    public void register(Converter converter, Class targetType) {
        converterMap.put(targetType, converter);
    }

    /**
     * 数据转换
     * 
     * @param source
     *            源数据
     * 
     * @param targetType
     *            目标类型
     * 
     * @return
     */
    public static Object convert(Object source, Class targetType) {
        Object result = source;
        if (null == targetType) {
            return result;
        }
        Converter converter = converterMap.get(targetType);
        if (null != converter) {
            result = converter.convert(targetType, source);
        }
        return result;
    }

    /**
     * 对象属性拷贝(浅)
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, false);
    }

    /**
     * 对象属性拷贝（使用转换器）
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param ignoreProperties
     *            排除属性
     * 
     * @param formattersByClass
     *            按类格式化器
     * 
     * @param formattersByClass
     *            按名称格式化器
     * 
     * @param isDeep
     *            是否深拷贝
     */
    public static void copyProperties(Object source, Object target, Map<Class, Formatter> formattersByClass, Map<String, Formatter> formattersByName, Map<Class, Converter> convertersByClass,
            Map<String, Converter> convertersByName) {
        copyProperties(source, target, null, formattersByClass, formattersByName, convertersByClass, convertersByName);
    }

    /**
     * 对象属性拷贝
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param isDeep
     *            是否深拷贝
     */
    public static void copyProperties(Object source, Object target, boolean isDeep) {
        copyProperties(source, target, null, isDeep);
    }

    /**
     * 对象属性拷贝
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param ignoreProperties
     *            排除属性
     * 
     * @param isDeep
     *            是否深拷贝
     * 
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties, boolean isDeep) {
        copyProperties(source, target, ignoreProperties, null, null, null, null, false);
    }

    /**
     * 对象属性拷贝(浅)
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param ignoreProperties
     *            排除属性
     * 
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties) {
        copyProperties(source, target, ignoreProperties, null);
    }

    /**
     * 对象属性拷贝（使用转换器）
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param ignoreProperties
     *            排除属性
     * 
     * @param formattersByClass
     *            按类格式化器
     * 
     * @param isDeep
     *            是否深拷贝
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties, Map<Class, Formatter> formattersByClass) {
        copyProperties(source, target, ignoreProperties, formattersByClass, null, null, null);
    }

    /**
     * 对象属性拷贝（使用转换器）
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param ignoreProperties
     *            排除属性
     * 
     * @param formattersByClass
     *            按类格式化器
     * 
     * @param formattersByClass
     *            按名称格式化器
     * 
     * @param isDeep
     *            是否深拷贝
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties, Map<Class, Formatter> formattersByClass, Map<String, Formatter> formattersByName,
            Map<Class, Converter> convertersByClass, Map<String, Converter> convertersByName) {
        copyProperties(source, target, ignoreProperties, formattersByClass, formattersByName, convertersByClass, convertersByName, false);
    }

    /**
     * 对象属性拷贝（使用转换器）
     * 
     * @param source
     *            源对象
     * 
     * @param target
     *            目标对象
     * 
     * @param ignoreProperties
     *            排除属性
     * 
     * @param formatters
     *            格式化器
     * 
     * @param isDeep
     *            是否深拷贝
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties, Map<Class, Formatter> formattersByClass, Map<String, Formatter> formattersByName,
            Map<Class, Converter> convertersByClass, Map<String, Converter> convertersByName, boolean isDeep) {
        if (null == source || null == target) {
            return;
        }

        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        for (PropertyDescriptor pd : pds) {
            Object value = null;
            String name = pd.getName();
            if ("class".equals(name) || (ignoreProperties != null && ignoreList.contains(name))) {
                continue;
            }

            if (source instanceof Map) {
                value = ((Map) source).get(name);
            } else {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), name);
                if (null == sourcePd) {
                    continue;
                }
                Method readMethod = sourcePd.getReadMethod();
                if (null != readMethod) {
                    try {
                        value = readMethod.invoke(source, null);
                    } catch (Exception e) {
                        LOG.error("Could not get property:" + name + " from class:" + source.getClass(), e);
                        continue;
                    }
                } else {
                    Field sourceField = null;
                    try {
                        sourceField = source.getClass().getDeclaredField(name);
                    } catch (Exception e) {
                        LOG.error("Could not get property:" + name + " from class:" + source.getClass(), e);
                        continue;
                    }
                    if (!sourceField.isAccessible()) {
                        sourceField.setAccessible(true);
                    }
                    try {
                        value = sourceField.get(source);
                    } catch (Exception e) {
                        LOG.error("Could not get property:" + name + " from class:" + source.getClass(), e);
                        continue;
                    }
                }
            }

            // 设值
            copyProperty(target, name, value, formattersByClass, formattersByName, convertersByClass, convertersByName, isDeep);
        }
    }

    /**
     * 对目标对象设值
     * 
     * @param target
     *            目标对象
     * 
     * @param name
     *            目标属性
     * 
     * @param value
     *            目标值
     * 
     */
    public static void copyProperty(Object target, String name, Object value) {
        copyProperty(target, name, value, null, null, null, null, false);
    }

    /**
     * 对目标对象设值
     * 
     * @param target
     *            目标对象
     * 
     * @param name
     *            目标属性名
     * 
     * @param value
     *            目标值
     * 
     * @param formattersByClass
     *            根据类型格式化器
     * 
     * @param formattersByName
     *            根据名称格式化器
     * 
     * @param isDeep
     *            是否深拷贝
     * 
     */
    public static void copyProperty(Object target, String name, Object value, Map<Class, Formatter> formattersByClass, Map<String, Formatter> formattersByName,
            Map<Class, Converter> convertersByClass, Map<String, Converter> convertersByName, boolean isDeep) {
        if (null == target || "class".equals(name)) {
            return;
        }

        Class targetType = Object.class;
        if (target instanceof Map) {

            if (null != value) {
                Type[] types = target.getClass().getGenericInterfaces();
                if (null != types && types.length > 0) {
                    if (types[0] instanceof ParameterizedType) {

                        // 执行强制类型转换
                        ParameterizedType parameterizedType = (ParameterizedType) types[0];

                        // 获取泛型类型的泛型参数
                        Type[] argTypes = parameterizedType.getActualTypeArguments();
                        if (null != argTypes && argTypes.length > 1) {
                            targetType = argTypes[1].getClass();
                        }
                    }
                }

                // 数据转换
                value = convertValue(name, value, formattersByClass, formattersByName, convertersByClass, convertersByName, isDeep, value.getClass(), targetType);
            }
            ((Map) target).put(name, value);
        } else {

            PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), name);
            if (null == pd) {
                return;
            }
            if (null != value) {
                targetType = pd.getPropertyType();

                // 数据转换
                value = convertValue(name, value, formattersByClass, formattersByName, convertersByClass, convertersByName, isDeep, value.getClass(), targetType);
            }
            Method writeMethod = pd.getWriteMethod();
            if (null != writeMethod) {
                try {
                    writeMethod.invoke(target, value);
                } catch (Exception e) {
                    LOG.error("Could not copy propertie=" + name + ",value=" + value + " to target ", e);
                }
            } else {
                Field field = null;
                try {
                    field = target.getClass().getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    LOG.error("class:" + target.getClass() + " has not field:" + name, e);
                    return;
                }
                if (!Modifier.isFinal(field.getModifiers()) || (Modifier.isFinal(field.getModifiers()) && null == value)) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    try {
                        field.set(target, value);
                    } catch (Exception e) {
                        LOG.error("Could not copy properties from source to target", e);
                    }
                }
            }
        }

    }

    /**
     * 数据转换
     * 
     * @param name
     *            目标属性名
     * 
     * @param value
     *            目标值
     * 
     * @param formattersByClass
     *            根据类型格式化器
     * 
     * @param formattersByName
     *            根据名称格式化器
     * 
     * @param convertersByClass
     *            根据类型转换器
     * 
     * @param convertersByName
     *            根据名称转换器
     * 
     * @param isDeep
     *            是否深拷贝
     * 
     * @param srcType
     *            原类型
     * 
     * 
     * 
     * @param targetType
     * @return
     */
    private static Object convertValue(String name, Object value, Map<Class, Formatter> formattersByClass, Map<String, Formatter> formattersByName, Map<Class, Converter> convertersByClass,
            Map<String, Converter> convertersByName, boolean isDeep, Class srcType, Class targetType) {
        if (null != value) {

            // 根据类型格式化器
            Formatter formatterByClass = null;

            // 优先取传入的格式化器
            if (null != formattersByClass && !formattersByClass.isEmpty()) {
                formatterByClass = formattersByClass.get(value.getClass());
            }
            if (null == formatterByClass) {
                formatterByClass = formatterMap.get(value.getClass());
            }
            if (null != formatterByClass) {
                value = formatterByClass.format(value);
            }

            // 根据名称格式化器
            Formatter formatterByName = null;

            // 优先取传入的格式化器
            if (null != formattersByName && !formattersByName.isEmpty()) {
                formatterByName = formattersByName.get(name);
            }
            if (null != formatterByName) {
                value = formatterByName.format(value);
            }

            // 数据类型转换
            if (!srcType.equals(targetType) && !targetType.isAssignableFrom(srcType)) {

                // 根据类型转换器
                Converter converterByClass = null;

                // 优先取传入的格式化器
                if (null != convertersByClass && !convertersByClass.isEmpty()) {
                    converterByClass = convertersByClass.get(targetType);
                }
                if (null != converterByClass) {
                    value = converterByClass.convert(targetType, value);
                }

                // 根据名称转换器
                Converter converterByName = null;

                // 优先取传入的格式化器
                if (null != convertersByName && !convertersByName.isEmpty()) {
                    converterByName = convertersByName.get(name);
                }
                if (null != converterByName) {
                    value = converterByName.convert(targetType, value);
                }

                if (null == converterByClass && null == formatterByName) {
                    value = convert(value, targetType);
                }
            }

            if (isDeep) {
                value = deepClone(value);
            }
        }
        return value;
    }

    /**
     * 克隆对象(浅)
     * 
     * @param source
     * @return
     */
    public static Object clone(Object source) {
        return clone(source, false);
    }

    /**
     * 克隆对象(深)
     * 
     * @param source
     * @return
     */
    public static Object deepClone(Object source) {
        return clone(source, true);
    }

    /**
     * 克隆对象(浅)
     * 
     * @param source
     *            源对象
     * 
     * @param isDeep
     *            是否深拷贝
     * 
     * @return
     */
    public static Object clone(Object source, boolean isDeep) {
        Object cloneObj = null;
        if (null != source) {
            Class<? extends Object> clazz = source.getClass();
            if (clazz.isInterface()) {
                return cloneObj;
            }
            if (!ObjectUtil.isBasicDataType(clazz)) {
                if (Collection.class.isAssignableFrom(clazz)) {
                    try {
                        Collection collection = (Collection) source;
                        Collection cloneCol = newCollection(collection);
                        if (null != cloneCol) {
                            Iterator it = collection.iterator();
                            if (it.hasNext()) {
                                cloneCol.add(clone(it.next(), isDeep));
                            }
                            cloneObj = cloneCol;
                        }
                    } catch (Exception e) {
                        LOG.error("类" + clazz + "拷贝异常！", e);
                    }
                } else if (Map.class.isAssignableFrom(clazz)) {
                    try {
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        if (null != constructor) {
                            if (!constructor.isAccessible()) {
                                constructor.setAccessible(true);
                            }
                            Map cloneMap = (Map) constructor.newInstance();
                            Map map = (Map) source;
                            for (Object key : map.keySet()) {
                                cloneMap.put(clone(key, isDeep), clone(map.get(key), isDeep));
                            }
                            cloneObj = cloneMap;
                        }
                    } catch (Exception e) {
                        LOG.error("类" + clazz + "拷贝异常！", e);
                    }
                } else if (clazz.isArray()) {
                    Object[] sourceArray = (Object[]) source;
                    cloneObj = Array.newInstance(clazz.getComponentType(), sourceArray.length);
                    Object[] cloneArray = (Object[]) cloneObj;
                    for (int i = 0; i < cloneArray.length; i++) {
                        cloneArray[i] = clone(sourceArray[i], isDeep);
                    }

                } else if (String.class.equals(clazz)) {
                    cloneObj = source;
                } else {
                    if (isDeep) {
                        try {
                            cloneObj = JsonUtil.jsonToObject(JsonUtil.toJson(source), clazz);
                        } catch (Exception e) {
                            LOG.error("类" + clazz + "json转换异常！", e);
                        }
                    } else if (source instanceof Cloneable) {
                        try {
                            Method method = clazz.getMethod("clone", null);
                            cloneObj = method.invoke(source, null);
                        } catch (Exception e) {
                            LOG.error("类" + clazz + "拷贝异常！", e);
                        }
                    } else {
                        try {
                            try {
                                Constructor<?> constructor = clazz.getDeclaredConstructor();
                                if (null != constructor) {
                                    if (!constructor.isAccessible()) {
                                        constructor.setAccessible(true);
                                    }
                                    cloneObj = constructor.newInstance();
                                }
                            } catch (Exception e) {
                                LOG.error("类" + clazz + "调用默认构造器异常！", e);
                            }
                            if (null == cloneObj) {
                                cloneObj = JsonUtil.jsonToObject(JsonUtil.toJson(source), clazz);
                            }
                            copyProperties(source, cloneObj);
                        } catch (Exception e) {
                            LOG.error("类" + clazz + "拷贝异常！", e);
                        }
                    }
                }
            } else {
                cloneObj = source;
            }
        }
        return cloneObj;
    }

    /**
     * 实例化一个对象
     * 
     * @param clazz
     *            类
     * 
     * @param argus
     *            构造函数
     * 
     * @return
     */
    public static Object newInstance(Class<?> clazz, Object... args) {
        Object result = null;
        try {
            Constructor<?> constructor = null;
            if (null == args || args.length == 0) {
                constructor = clazz.getDeclaredConstructor();
            } else {
                Class<?>[] argClasses = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (null == arg) {
                        argClasses[i] = String.class;
                    } else {
                        argClasses[i] = arg.getClass();
                    }
                }
                constructor = clazz.getDeclaredConstructor(argClasses);
            }
            if (null != constructor) {
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                result = constructor.newInstance(args);
            }
        } catch (Exception e) {
            LOG.error("类" + clazz + "调用构造器异常！", e);
        }
        return result;
    }

    /**
     * 新建一个空的集合
     * 
     * @param collection
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static Collection<?> newCollection(Collection<?> collection) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Collection<?> resultCol = null;
        Class<? extends Object> clazz = collection.getClass();
        if (clazz.isInterface()) {
            return resultCol;
        }
        Constructor<?> constructor = clazz.getDeclaredConstructor(Integer.TYPE);
        if (null != constructor) {
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            resultCol = (Collection<?>) constructor.newInstance(collection.size());
        } else {
            constructor = clazz.getDeclaredConstructor();
            if (null != constructor) {
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                resultCol = (Collection<?>) constructor.newInstance();
            }
        }
        return resultCol;
    }

    /**
     * 序列化对象,然后再取出
     * 
     * @param source
     * @return
     */
    public static Object serialize(Object source) {
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Object target = null;

        try {
            out = new ObjectOutputStream(memoryBuffer);
            out.writeObject(source);
            out.flush();
            in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
            target = in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) try {
                out.close();
                out = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (in != null) try {
                in.close();
                in = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return target;
    }

    /**
     * 获取对象的静态属性值
     * 
     * @param obj
     *            目标对象
     * 
     * @param propertyName
     *            属性名称
     * 
     * @return
     */
    public static Object getStaticPropertyValue(Class<?> clazz, String propertyName) {
        Object value = null;
        if (null == clazz || StringUtils.isBlank(propertyName)) {
            return value;
        }

        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, propertyName);
        Method readMethod = null;
        if (null != pd) {
            readMethod = pd.getReadMethod();
        }
        if (null != readMethod) {
            try {
                if (!readMethod.isAccessible()) {
                    readMethod.setAccessible(true);
                }
                value = readMethod.invoke(clazz, null);
            } catch (Exception e) {
                LOG.error("类" + clazz + "调用方法" + readMethod.getName() + "异常！", e);
            }
        } else {
            Field[] fields = clazz.getDeclaredFields();
            if (null != fields && fields.length > 0) {
                for (Field field : fields) {
                    if (propertyName.equals(field.getName())) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        try {
                            value = field.get(clazz);
                        } catch (IllegalArgumentException e) {
                            LOG.error("属性：" + field + "参数非法！", e);
                        } catch (IllegalAccessException e) {
                            LOG.error("属性：" + field + "访问非法！", e);
                        }
                        break;
                    }
                }
            }
        }
        return value;
    }

    /**
     * 获取对象的属性值
     * 
     * @param obj
     *            目标对象
     * 
     * @param propertyName
     *            属性名称
     * 
     * @return
     */
    public static Object getPropertyValue(Object obj, String propertyName) {
        Object value = null;
        if (null == obj || StringUtils.isBlank(propertyName)) {
            return value;
        }

        if (propertyName.indexOf(".") > -1) {
            String[] propertyNames = propertyName.split("\\.");
            for (String name : propertyNames) {
                obj = getPropertyValue(obj, name);
            }
            value = obj;
        } else {
            Class<? extends Object> clazz = obj.getClass();
            PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, propertyName);
            Method readMethod = null;
            if (null != pd) {
                readMethod = pd.getReadMethod();
            }
            if (null != readMethod) {
                try {
                    if (!readMethod.isAccessible()) {
                        readMethod.setAccessible(true);
                    }
                    value = readMethod.invoke(obj, null);
                } catch (Exception e) {
                    LOG.error("类" + clazz + "调用方法" + readMethod.getName() + "异常！", e);
                }
            } else {
                Field[] fields = clazz.getDeclaredFields();
                if (null != fields && fields.length > 0) {
                    for (Field field : fields) {
                        if (propertyName.equals(field.getName())) {
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            try {
                                value = field.get(obj);
                            } catch (IllegalArgumentException e) {
                                LOG.error("属性：" + field + "参数非法！", e);
                            } catch (IllegalAccessException e) {
                                LOG.error("属性：" + field + "访问非法！", e);
                            }
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    /**
     * 获取类的属性
     * 
     * @param clazz
     * @return
     */
    public static String[] getPropertyNames(Class clazz) {
        String[] names = null;
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        if (null != pds && pds.length > 0) {
            List<String> list = new ArrayList<String>();
            names = new String[pds.length];
            for (int i = 0; i < pds.length; i++) {
                String name = pds[i].getName();
                if ("class".equals(name)) {
                    continue;
                }
                list.add(name);
            }
            names = list.toArray(new String[list.size()]);
        }
        return names;
    }

    /**
     * 对象转换成Map
     * 
     * @param obj
     * @return
     */
    public static Map toMap(Object obj) {
        Map map = Collections.emptyMap();
        if (null == obj) {
            return map;
        }

        if (obj instanceof Map) {
            return (Map) obj;
        }

        try {
            map = org.apache.commons.beanutils.BeanUtils.describe(obj);
        } catch (Exception e) {
            LOG.error("对象：" + obj + "转换Map异常！", e);
        }

        if (null != map && map.containsKey("class")) {
            map.remove("class");
        }
        return map;
    }

    /**
     * 判断类是否基本数据类型与封装类
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }

        if (clazz.isPrimitive() || String.class.isAssignableFrom(clazz)) {
            return true;
        }

        Object type = getStaticPropertyValue(clazz, "TYPE");
        if (null != type && type instanceof Class) {
            Class<?> classType = (Class<?>) type;
            if (classType.isPrimitive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否基本数据类型与封装类
     * @param obj
     * @return
     */
    public static boolean isPrimitive(Object obj) {
        if (null == obj) {
            return false;
        }

        if (obj.getClass().isPrimitive() || obj instanceof String) {
            return true;
        }

        Object type = getPropertyValue(obj, "TYPE");
        if (null != type && type instanceof Class) {
            Class<?> classType = (Class<?>) type;
            if (classType.isPrimitive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较相同类型的对象的不同属性
     * @param one
     * @param another
     * @return
     */
    public static List<String> compare(Object one, Object another) {
        List<String> resultList = Collections.emptyList();
        Class<? extends Object> clazz = one.getClass();
        if (null == one || null == another || !clazz.equals(another.getClass())) {
            return resultList;
        }

        resultList = new ArrayList<String>();
        String[] names = getPropertyNames(clazz);
        for (String name : names) {
            Object value1 = getPropertyValue(one, name);
            Object value2 = getPropertyValue(another, name);
            if ((null != value1 && (null == value2 || !value1.equals(value2))) || (null != value2 && (null == value1 || !value2.equals(value1)))) {
                resultList.add(name);
            }

        }
        return resultList;
    }

    private interface Formatter {

        public Object format(Object source);
    }

    private class AmountFormatter implements Formatter {

        private static final String DEFAULT_FORMAT = "0.00";

        // 格式
        private String format = DEFAULT_FORMAT;

        /**
         * 时间转换
         */
        public Object format(Object source) {
            Object dest = null;
            DecimalFormat df = new DecimalFormat(format);
            if (null != source) {
                dest = df.format(source);
            }
            return dest;
        }

    }

}
