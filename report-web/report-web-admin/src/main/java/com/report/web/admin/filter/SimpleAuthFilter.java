package com.report.web.admin.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.report.web.admin.shiro.ShiroFilterUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 认证目前用户是否已经登陆
 * @author lishun
 * @since 2017年3月30日 上午11:43:30
 */
@Slf4j
public class SimpleAuthFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		log.info("SimpleAuthFilter isAccessAllowed [{}]", mappedValue);
		Map<String, String> resultMap = new HashMap<String, String>();
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			//判断是不是Ajax请求
			if (ShiroFilterUtils.isAjax(request) ) {
				log.debug("当前用户已经被踢出，并且是Ajax请求！");
				resultMap.put("user_status", "300");
				resultMap.put("message", "请重新登录！");
				out(response, resultMap);
			}
			return  Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		
		//先退出
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		/**
		 * 保存Request，用来保存当前Request，然后登录后可以跳转到当前浏览的页面。
		 * 比如：
		 * 我要访问一个URL地址，/admin/index.html，这个页面是要登录。然后要跳转到登录页面，但是登录后要跳转回来到/admin/index.html这个地址，怎么办？
		 * 传统的解决方法是变成/user/login.shtml?redirectUrl=/admin/index.html。
		 * shiro的解决办法不是这样的。需要：<code>WebUtils.getSavedRequest(request);</code>
		 * 							 然后：{@link UserLoginController.submitLogin(...)}中的<code>String url = WebUtils.getSavedRequest(request).getRequestUrl();</code>
		 * 如果还有问题，请咨询我。
		 */
		WebUtils.saveRequest(request);
		//再重定向
		WebUtils.issueRedirect(request, response, "/toLogin.htm");
		return false;
	}

	private void out(ServletResponse hresponse, Map<String, String> resultMap)
			throws IOException {
		hresponse.setCharacterEncoding("UTF-8");
		PrintWriter out = hresponse.getWriter();
		out.println(JSON.toJSONString(resultMap));
		out.flush();
		out.close();
	}
}
