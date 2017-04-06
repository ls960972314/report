package com.report.web.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.report.common.dal.admin.entity.vo.MenuCell;
import com.report.common.model.SessionUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 用户目录controller
 * @author lishun
 * @since 2017年4月5日 下午5:55:09
 */
@Slf4j
@Controller
public class MenuController {
	
	/**
	 * 加载报表目录
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/loadMenuByPriv.htm")
    @ResponseBody
    public List<MenuCell> loadMenuByPriv() {
    	log.debug("username[{}] loadMenuByPriv", SessionUtil.getUserInfo().getMember().getAccNo());
    	return SessionUtil.getUserInfo().getReportMenuList();
    }
    
    /**
	 * 跳转至报表后台管理页面
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "/toPermission.htm")
    @RequestMapping(value = "/toPermission.htm")
    public ModelAndView toPermission(HttpServletRequest request) {
		log.debug("username[{}] toPermission", SessionUtil.getUserInfo().getMember().getAccNo());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("permission");
        return modelAndView;
    }
}
