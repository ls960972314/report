package com.report.common.dal.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.report.common.dal.common.exception.ReportException;

/**
 * 校验工具类
 * @author 李文锴
 * @since 2016/12/26.
 */
public class VerificationUtil {

    private VerificationUtil(){}

    /**
     * 校验集合是否为空，包含null或无数据
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        if(null == collection || collection.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 检查集合是否不为空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

    /**
     * 校验Map是否为null或无数据
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map){
        if(null == map || map.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 校验Map是否为null或无数据
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map map){
        return !isEmpty(map);
    }

    /**
     * 校验字符串是否为null或“”
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(null == str || "".equals(str)){
            return true;
        }
        return false;
    }

    public static boolean hasEmpty(String... strs){
        if(strs == null) return true;

        for (String str : strs) {
            if(isEmpty(str)) return true;
        }
        return false;
    }

    public static boolean hasNull(Object... objs){
        if (objs == null) return true;

        for (Object obj : objs) {
            if(null == obj) return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断两个字符串是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 判断两个整型是否相等
     * @param i1
     * @param i2
     * @return
     */
    public static boolean equals(Long i1, Long i2){
        return i1 == null ? i2 == null : i1.longValue() == i2.longValue();
    }

    /**
     * 判断两个字符串是否不相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEquals(String str1, String str2){
        return !equals(str1, str2);
    }

    /**
     * 判断两个整型是否不相等
     * @param i1
     * @param i2
     * @return
     */
    public static boolean notEquals(Long i1, Long i2) {
        return !equals(i1, i2);
    }

    /**
     * 判断A是否小于B，如果为空则设置为0
     * @param a
     * @param b
     * @return
     */
    public static boolean lessThen(Long a, Long b){
        if(a == null) a = 0L;
        if(b == null) b = 0L;
        return a < b;
    }

    /**
     * 判断A是否小于B，如果为空则设置为0
     * @param a
     * @param b
     * @return
     */
    public static boolean lessThen(Integer a, Integer b){
        if(a == null) a = 0;
        if(b == null) b = 0;
        return a < b;
    }

    /**
     * 判断str是否在compareStrs中
     * @param str
     * @param compareStrs
     * @return
     */
    public static boolean isIn(String str, String... compareStrs){
        for (String s : compareStrs){
            if(equals(str, s)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断str不在compareStrs中
     * @param str
     * @param compareStrs
     * @return
     */
    public static boolean isNotIn(String str, String... compareStrs){
        return !isIn(str, compareStrs);
    }

    /**
     * 判断str是否在compareList列表中
     * @param str
     * @param compareList
     * @return
     */
    public static boolean isIn(String str, List<String> compareList){
        if(isEmpty(compareList)) return false;

        for (String cstr : compareList){
            if(equals(cstr, str)) return true;
        }

        return false;
    }

    /**
     * 判断字符串是否在集合中
     * @param str
     * @param compareSet
     * @return
     */
    public static boolean isIn(String str, Set<String> compareSet){
        if(isEmpty(compareSet)) return false;

        for (String cstr : compareSet){
            if(equals(cstr, str)) return true;
        }

        return false;
    }

    /**
     * 如果存在就从集合中滤除
     * @param str
     * @param compareSet
     * @return
     */
    public static boolean isInFilter(String str, Set<String> compareSet){
        if(isEmpty(compareSet)) return false;

        Iterator<String> interator = compareSet.iterator();
        while (interator.hasNext()){
            String cstr = interator.next();
            if(equals(cstr, str)){
                interator.remove();
                return true;
            }
        }

        return false;
    }


    /**
     * 判断str是否在compareList列表中
     * @param str
     * @param compareList
     * @return
     */
    public static boolean isNotIn(String str, List<String> compareList){
        return !isIn(str, compareList);
    }

    /**
     * 判断字符串是否在集合中
     * @param str
     * @param compareSet
     * @return
     */
    public static boolean isNotIn(String str, Set<String> compareSet){
        return !isIn(str, compareSet);
    }

    /**
	 * 字符串是否是时间格式
	 * 
	 * @param dateStr
	 * @param format
	 *            要格式化的样式
	 * @return 是时间格式 true 否则 false
	 */
	public static boolean isDateParam(String format, String... dateStrs) {
		if (paramIsNull(format, dateStrs)) { return false; }
		for (String dateStr : dateStrs) {
			if (!isDateParam(format, dateStr)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isDateParam(String format, String dateStr) {
		if (paramIsNull(dateStr)) {
			return false;
		}
		try {
			DateUtil.stringToDate(dateStr, format);
		} catch (ReportException e) {
			return false;
		}
		return true;
	}

	/**
	 * <pre>
	 * 判断多个参数是否为空
	 * 参数可传多个,目前支持类型:Long,Integer,String,List,Set,Map
	 * 其他类型直接返回obj == null
	 * </pre>
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean paramIsNull(Object... objs) {
		if (null == objs) { return true; }
		for (Object obj : objs) {
			if (paramIsNull(obj)) {
				return true;
			}
		}
		return false;
	}

	private static boolean paramIsNull(Object obj) {
		if (obj instanceof Integer || obj instanceof Long) {
			return obj == null;
		}
		if (obj instanceof String) {
			return StringUtils.isBlank((String) obj);
		}
		if (obj instanceof List) {
			return ((List) obj).isEmpty();
		}
		if (obj instanceof Set) {
			return ((Set) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		return obj == null;
	}
}
