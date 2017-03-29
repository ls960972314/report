package com.report.web.admin.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiroFilterUtils {
	final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;
	//登录页面
	public static String LOGIN_URL = "/toLogin.htm";
	//踢出登录提示
	public static String KICKED_OUT = "/toLogin.htm";
	//没有权限提醒
	public static String UNAUTHORIZED = "/toLogin.htm";
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * response 输出JSON
	 * @param hresponse
	 * @param resultMap
	 * @throws IOException
	 */
	public static void out(ServletResponse response, Map<String, String> resultMap){
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			log.error("输出JSON报错", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
