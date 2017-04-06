package com.report.web.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String upload2(@RequestParam String upPath ,HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		log.info("username[{}],upPath[{}]开始上传文件", SessionUtil.getUserInfo().getMember().getAccNo(), upPath);
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		log.debug("获取到项目根路径为rootPath[{}]", rootPath);
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				long pre = System.currentTimeMillis();
				MultipartFile file = multiRequest.getFile(iter.next());
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

		}
		return "file/success";
	}
}
