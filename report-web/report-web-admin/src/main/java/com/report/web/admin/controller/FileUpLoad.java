package com.report.web.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.report.common.dal.common.utils.StringUtil;
import com.report.common.model.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 上传文件Controller
 * @author lishun
 * @since 2017年3月30日 上午11:58:53
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileUpLoad {

	@RequestMapping(value = "/file.htm")
	public String resource() {
		return "file/fileUpLoadView";
	}

	/**
	 * 向当前运行的war中上传文件
	 * @param upPath
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/upload")
	public String upload2(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
//	@RequestParam String upPath ,
		log.info("upPath:[{}][{}]", request.getParameter("upPath"), request.getAttribute("upPath"));
		String upPath = "/tpl/";
		log.info("username[{}],upPath[{}]开始上传文件", SessionUtil.getUserInfo().getMember().getAccNo(), upPath);
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		log.debug("获取到项目根路径为rootPath[{}]", rootPath);
		
		
		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
		
		
		Iterator<String> itr = multipartRequest.getFileNames();  
	    MultipartFile file = null; 
	    while (itr.hasNext()) {  
	    	file = multipartRequest.getFile(itr.next());
	        long pre = System.currentTimeMillis();
			if (file != null) {
				if (StringUtils.isNotBlank(file.getOriginalFilename())) {
					String fileName = StringUtil.combinationString(upPath, "/", file.getOriginalFilename());
					String path = rootPath + fileName;
					File localFile = new File(path);
					file.transferTo(localFile);
				}
			}
			long finaltime = System.currentTimeMillis();
			log.info("上传文件耗时:{}" , (finaltime - pre));
	    }
		return "file/success";
	}
}
