package com.report.common.dal.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;

/**
 * 字符串相关工具类
 * @author lishun
 * @since 2017年2月21日 下午4:04:10
 */
public class StringUtil {

	private StringUtil() {}
	
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
	 * 将所有字符串拼装成一个字符串
	 * @param strs 字符串列表
	 * @return
	 */
	public static String combinationString(String ...strs) {
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {sb.append(str);}
		return sb.toString();
	}
	
	/**
	 * <pre>
	 * 根据文件全名得到文件名
	 * e.g 
	 * 		String fileName = "D://test/test.txt";
	 * 		System.out.println(StringUtil.getFileName(fileName));
	 * 输出:
	 * 		test.txt
	 * </pre>
	 * @param fileName	文件名全称
	 * @return
	 */
	public static String getFileName(String fileName) {
		return fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
	}
	
	/**
	 * <pre>
	 * 从map类型的字符串中根据key获得value的值,不存在返回空字符串
	 * map类型的字符串示例: 1|1000,2|2000
	 * 分隔符为  , 和 |
	 * </pre>
	 * @param mapString
	 * @param key
	 * @return
	 */
	public static String getKeyFromMapString(String mapString, String key) {
		return getKeyFromMapStringBase(mapString, key, ",", "|");
	}
	
	/**
	 * <pre>
	 * 从map类型的字符串中根据key获得value的值,不存在返回空字符串
	 * map类型的字符串示例: 1|1000,2|2000
	 * 分隔符为  spliter1 和 spliter2
	 * 示例中为, 和 |
	 * </pre>
	 * @param mapString
	 * @param key
	 * @return
	 */
	public static String getKeyFromMapStringBase(String mapString, String key, String spliter1, String spliter2) {
		if (StringUtils.isBlank(mapString) || StringUtils.isBlank(key)) {
			return "";
		}
		Map<String, String> map = Splitter.on(spliter1).withKeyValueSeparator(spliter2).split(mapString);
		return StringUtils.isBlank(map.get(key)) ? "" : map.get(key);
	}
	
	/**
	 * <pre>
	 * 将map类型的字符串转换成Map集合
	 * map类型的字符串示例: 1|1000,2|2000
	 * 分隔符为  spliter1 和 spliter2
	 * 示例中为, 和 |
	 * </pre>
	 * @param mapString
	 * @param key
	 * @return
	 */
	public static Map<String, String> mapStringBase2Map(String mapString, String spliter1, String spliter2) {
		if (StringUtils.isBlank(mapString)) {
			return new HashMap<>();
		}
		Map<String, String> map = Splitter.on(spliter1).withKeyValueSeparator(spliter2).split(mapString);
		return map;
	}
}
