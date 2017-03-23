package com.report.common.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * Json结果
 * 
 * @author Alone
 * @createDate 2014-06-21
 * @version 1.0
 */
public class JsonResult 
{
	/**
	 * 返回无数据的成功结果
	 * 
	 * @return JSON结果
	 */
	public static Object success() 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", GlobalResultStatus.SUCCESS.getCode());
		return map;
	}

	/**
	 * 返回成功结果
	 * 
	 * @param data 数据节点对象
	 * @return JSON结果
	 */
	public static Object success(Object data) 
	{
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("code", GlobalResultStatus.SUCCESS.getCode());
		map.put("data", data);
		return map;
	}

	/**
	 * 返回成功结果
	 * 
	 * @param data
	 *            数据节点对象
	 * @return JSON结果
	 */
	public static Object success(Map<String, Object> data) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", GlobalResultStatus.SUCCESS.getCode());
		map.put("data", data);
		return map;
	}
	
	/**
	 * 返回日期处理成功结果
	 * @param data 数据节点对象
	 * @return JSON结果
	 */
	public static String success(Object data, String format)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", GlobalResultStatus.SUCCESS.getCode());
		if (data != null)
		{
			map.put("data", data);
		}
		JsonMapper mapper = new JsonMapper(Inclusion.NON_NULL, new SimpleDateFormat(format));
		return mapper.toJson(map);
	}
	
	/**
	 * 报表返回格式日期处理成功结果
	 * @param data 数据节点对象
	 * @return JSON结果
	 */
	public static String reportSuccess(Object data, String format)
	{
		JsonMapper mapper = new JsonMapper(Inclusion.NON_NULL, new SimpleDateFormat(format));
		return mapper.toJson(data);
	}

	/**
	 * 返回成功结果
	 * 
	 * @param list
	 *            数据列表
	 * @return JSON结果
	 */
	public static Object success(List<?> list) 
	{
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("code", GlobalResultStatus.SUCCESS.getCode());
		map.put("data", list);
		return map;
	}


	/**
	 * 返回失败结果
	 * 
	 * @param status 结果说明信息
	 * @return JSON结果
	 */
	public static Object fail(ResultStatus status) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", status.getCode());
		map.put("msg", status.getMsg());
		return map;
	}

	/**
	 * 返回失败结果
	 * 
	 * @param status 结果说明信息
	 * @param data 数据节点对象
	 * @return JSON结果
	 */
	public static Object fail(ResultStatus status, Object data) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", status.getCode());
		map.put("msg", status.getMsg());
		map.put("data", data);
		return map;
	}
}