package com.report.web.admin.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.report.web.admin.shiro.ShiroFilterUtils;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author lishun
 * @since 2017年3月29日 下午2:26:24
 */
@Slf4j
public class PermissionFilter extends AccessControlFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		log.info("PermissionFilter isAccessAllowed mappedValue[{}]", mappedValue);
		//先判断带参数的权限判断
		Subject subject = getSubject(request, response);
		if(null != mappedValue){
			String[] arra = (String[])mappedValue;
			for (String permission : arra) {
				if(subject.isPermitted(permission)){
					return Boolean.TRUE;
				}
			}
		}
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		//获取URI
		String uri = httpRequest.getRequestURI();
		//获取basePath
		String basePath = httpRequest.getContextPath();
		if(null != uri && uri.startsWith(basePath)){
			uri = uri.replace(basePath, "");
		}
		if(subject.isPermitted(uri)){
			return Boolean.TRUE;
		}
		if(ShiroFilterUtils.isAjax(request)){
			log.debug("当前用户没有登录，并且是Ajax请求！");
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put("login_status", "300");
			resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u8BE5\u8D44\u6E90\u6743\u9650");
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
			Subject subject = getSubject(request, response);  
	        if (null == subject.getPrincipal()) {
	        	//表示没有登录，重定向到登录页面  
	            saveRequest(request);  
	            WebUtils.issueRedirect(request, response, ShiroFilterUtils.LOGIN_URL);
	        } else {  
	            if (StringUtils.hasText(ShiroFilterUtils.UNAUTHORIZED)) {
	            	//如果有未授权页面跳转过去  
	                WebUtils.issueRedirect(request, response, ShiroFilterUtils.UNAUTHORIZED);  
	            } else {//否则返回401未授权状态码  
	                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);  
	            }
	        }  
		return Boolean.FALSE;
	}

}
