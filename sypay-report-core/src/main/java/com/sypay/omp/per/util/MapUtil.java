package com.sypay.omp.per.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtil {

    /**获取 Map<String, String>String值
     * @param key
     * @param map
     * @return
     */
    public static String getStringValue(String key, Map<String, String> map) {
        if (null != map) {
            HashMap<String, String> inMap = (HashMap<String, String>) map;
            for (Entry<String, String> en : inMap.entrySet()) {
                if (en.getKey().equals(key)) {
                    return String.valueOf(en.getValue());
                }

            }
        }
        return "";
    }

    /**
     * 获取 Map<String, Object>Object值
     * @param key
     * @param map
     * @return
     */
    public static String getObjectValue(String key, Map<String, Object> map) {
        if (null != map) {
            HashMap<String, Object> inMap = (HashMap<String, Object>) map;
            for (Entry<String, Object> en : inMap.entrySet()) {
                if (en.getKey().equals(key)) {
                    return String.valueOf(en.getValue());
                }

            }
        }

        return "";
    }

    /**
     * Map<String, Object> 转为 Map<String, String>
     * @param map
     * @return
     */
    public static Map<String, String> tranObj2Str(Map<String, Object> map) {
        if (null != map) {
            HashMap<String, Object> inMap = (HashMap<String, Object>) map;
            Map<String, String> outMap = new HashMap<String, String>();
            for (Entry<String, Object> en : inMap.entrySet()) {
                outMap.put(en.getKey(), String.valueOf(en.getValue()));
            }
            return outMap;
        }
        return null;
    }

    /**
     * Map<String, String> 转为 Map<String, Object>
     * @param map
     * @return
     */
    public static Map<String, Object> tranStr2Obj(Map<String, String> map) {
        if (null != map) {
            HashMap<String, String> inMap = (HashMap<String, String>) map;
            Map<String, Object> outMap = new HashMap<String, Object>();
            for (Entry<String, String> en : inMap.entrySet()) {
                outMap.put(en.getKey(), en.getValue());
            }
            return outMap;
        }
        return null;
    }
}
