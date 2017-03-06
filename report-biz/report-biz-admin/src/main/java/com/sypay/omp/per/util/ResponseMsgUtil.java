package com.sypay.omp.per.util;

import java.util.Map;
import java.util.Properties;

import com.sypay.omp.per.common.BoxPayConstants;
import com.sypay.omp.per.model.page.AjaxJson;


/**
 * 响应前端封装返回信息工具类
 *
 */
public class ResponseMsgUtil {
	private static Properties properties = null;
	private static String PROPERTIES_PATH_zhCN = "/properties/messages_zh_CN.properties";
	
	static {
		if (null == properties) {
			properties = PropertyReader.getProperties(PROPERTIES_PATH_zhCN);
		}
	}
	public static void main (String args[]) {
		properties = PropertyReader.getProperties(PROPERTIES_PATH_zhCN);
	}
	/**
	 * 返回正常过程结果AjaxJson
	 * @param ajaxJson
	 * @param result
	 * @return
	 */
	public static AjaxJson returnMsg(AjaxJson ajaxJson,Map<String, Object> result){
		MapUtil.getObjectValue(BoxPayConstants.ResultParam.CODE, result);
		int result_status = Integer.valueOf(MapUtil.getObjectValue(BoxPayConstants.ResultParam.CODE, result));
		boolean succ = result_status==BoxPayConstants.ResultMsg.RESULT_MSG_SUCC ? true : false ;
		String msgValue;
		
		//异常消息
		if(result_status == BoxPayConstants.ResultMsg.RESULT_MSG_EXCP){
			msgValue = getValue("RESULT_MSG_"+String.valueOf(BoxPayConstants.ResultMsg.RESULT_MSG_EXCP));
		}
		//非异常
		else{
			String remark = MapUtil.getObjectValue(BoxPayConstants.ResultParam.REMARK,result);
			//传入为数字代码则转译
			if(NumberUtil.isNumeric(remark)){
				msgValue = getValue("RESULT_MSG_"+remark);
			}else{
				msgValue = remark;
			}
		}
		ajaxJson.setErrorInfo(msgValue);
		return ajaxJson;
	}
	
	/**
	 * 返回请求数据不完整消息
	 * @param ajaxJson
	 * @return
	 */
	public static AjaxJson returnRequestErrDataMsg(AjaxJson ajaxJson){
		returnFalseMsg(ajaxJson, BoxPayConstants.ResultMsg.RESULT_MSG_ERR_REQUEST_DATA);
		return ajaxJson;
	}
	
	/**
	 * 给定返回信息
	 * @param ajaxJson
	 * @param constantsResult
	 * @return
	 */
	public static AjaxJson returnFalseMsg(AjaxJson ajaxJson, int constantsResult){
		ajaxJson.setErrorInfo(getValue("RESULT_MSG_" + constantsResult));
		return ajaxJson;
	}
	
	/**
	 * 
	 * @param constantsResult
	 * @return
	 */
	public static String returnMsg(int constantsResult){
		return getValue("RESULT_MSG_" + constantsResult);
	}
	
	/**
	 * 获取key的value
	 * @param key
	 * @return
	 */
	private static String getValue(String key){
		return properties.getProperty(key);
	}
	
}
