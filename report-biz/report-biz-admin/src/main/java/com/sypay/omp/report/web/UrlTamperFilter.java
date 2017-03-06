package com.sypay.omp.report.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.sypay.omp.report.util.MD5;

/**
 * URL防篡改，加入mac参数来验证。<br/>
 * 目前没有加上。
 * @author lishun
 *
 */
public class UrlTamperFilter extends GenericFilterBean {
	//需要做防篡改处理的URL
	private static Map<String, String[]> urlMap = new HashMap<String, String[]>();
	static{
		/* key为url，value为url参数 */
		urlMap.put("/report/report", new String[]{"qid"});
		urlMap.put("/report/reportShowQueryData", new String[]{"qid"});
	}
	private static AntPathMatcher urlMatcher = new AntPathMatcher();

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestURI = request.getRequestURI();
		String sessionId = request.getSession(true).getId();
		
		for(String url:urlMap.keySet()) {
			if(urlMatcher.match(request.getContextPath()+url, requestURI)) {
				StringBuffer sb = new StringBuffer();
				String[] paraNames = urlMap.get(url);
				for(String paraName:paraNames){
					sb.append(paraName+"="+request.getParameter(paraName));
				}
				sb.append(url);
				sb.append(sessionId);
				
				String newMac = MD5.getMD5String(sb.toString());
				String mac = request.getParameter("mac");
				if(newMac.equals(mac)){
					HttpServletResponseWrapperURL wrapper = new HttpServletResponseWrapperURL(
							(HttpServletResponse) response, request.getContextPath(), sessionId);
					chain.doFilter(request, wrapper);
					return ;
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, 
							"Authentication Failed: 不合法的地址");
					return;
				}
			}
		}
		HttpServletResponseWrapperURL wrapper = new HttpServletResponseWrapperURL(
				(HttpServletResponse) response,request.getContextPath(),sessionId);
		chain.doFilter(request, wrapper);
	}

	private class HttpServletResponseWrapperURL extends
			HttpServletResponseWrapper {
		private String contextPath = null;
		private String sessionId =null;
		public HttpServletResponseWrapperURL(HttpServletResponse response,
				String contextPath,String sessionId) {
			super(response);
			this.contextPath = contextPath;
			this.sessionId = sessionId;
		}

		/* 覆写getWriter()方法，返回自定义的PrintWriterURL对象 */
		public PrintWriter getWriter() throws IOException {
			return new PrintWriterURL(super.getWriter(), contextPath, sessionId);
		}
	}

	private class PrintWriterURL extends PrintWriter {
		private String contextPath = null;
		private String sessionId = null;
		public PrintWriterURL(Writer out,String contextPath,String sessionId) {
			super(out);
			this.contextPath = contextPath;
			this.sessionId = sessionId;
		}
		
		public void write(char buf[], int off, int len) {
			char[] newbuf = Arrays.copyOfRange(buf, off, off + len);
			String source = String.copyValueOf(newbuf);
			for(String url:urlMap.keySet()){
				StringBuffer sb = new StringBuffer();
				Pattern p = Pattern.compile("(.*?['\"])([^'\"]*"+url+"\\?[^'\"]*)", 
						Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
				Matcher m = p.matcher(source);	
				while (m.find()) {
					String g1 = m.group(1);
					String g2 = m.group(2);
					String mac = makeMac(contextPath,sessionId,g2);
					if(StringUtils.isNotEmpty(mac)){
						g2 = g2.substring(0, g2.indexOf("?")+1)
								+"mac="+mac+"&"+g2.substring(g2.indexOf("?")+1);
					}
					m.appendReplacement(sb,g1+g2);
				}
				m.appendTail(sb);
				source = sb.toString();
			}
			newbuf = source.toCharArray();
			super.write(newbuf, 0, newbuf.length);
		}
	}
	
	public static String makeMac(String contextPath ,String sessionId, String url){
		if(StringUtils.isEmpty(url)){
			return "";
		}
		
		if(url.startsWith(contextPath)){
			url = url.substring(contextPath.length());
		}
		
		String uri = "";
		String query = "";
		if(url.indexOf("?")!=-1){   
            uri = url.substring(0, url.indexOf("?")); 
            query = url.substring(url.indexOf("?")+1);
        } 
		if(StringUtils.isEmpty(query)||StringUtils.isEmpty(query)){
			return "";
		}
		String[] paraNames = urlMap.get(uri);
		Map<String, String> paraMap = new HashMap(); 
		String[] kvs = query.split("&");
		for(String kvStr : kvs){
			String[] kv = kvStr.split("=");
			if(kv==null || kv.length<2){
				continue;
			}
			paraMap.put(kv[0], kv[1]);
		}
		
		StringBuffer sb = new StringBuffer();
		if(paraNames==null){
			return "";
		}
		
		for(String paraName:paraNames){
			if(StringUtils.isEmpty(paraMap.get(paraName))){
				return "";
			}
			sb.append(paraName+"="+paraMap.get(paraName));
		}

		sb.append(uri);
		sb.append(sessionId);
		
		
		return MD5.getMD5String(sb.toString());
	}
	
	public static void main(String[] args) {
		String source = "<script type=\"text/javascript\">" +
				"jQuery(\"#list11\").jqGrid({" +
				"url:\"/omp/report/report?qid=11&row=10\",datatype: \"json\","+
				"\n<script type=\"text/javascript\">" +
				"jQuery(\"#list02\").jqGrid({" +
				"url:\"/omp/report/report1?qid=11&row=10\",datatype: \"json\",";
		
		for(String url:urlMap.keySet()){
			StringBuffer sb = new StringBuffer();
			Pattern p = Pattern.compile("(.*?['\"])([^'\"]*"+url+"\\?[^'\"]*)", 
					Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE);
			Matcher m = p.matcher(source);	
			while (m.find()) {
				String g1 = m.group(1);
				String g2 = m.group(2);
				System.out.println(g2);
				String mac = makeMac("/omp","",g2);
				if(StringUtils.isNotEmpty(mac)){
					g2 = g2.substring(0, g2.indexOf("?")+1)
							+"mac="+mac+"&"+g2.substring(g2.indexOf("?")+1);
				}
				m.appendReplacement(sb,g1+g2);
			}
			m.appendTail(sb);
			source = sb.toString();
		}
		System.out.println(source);
	}

}
