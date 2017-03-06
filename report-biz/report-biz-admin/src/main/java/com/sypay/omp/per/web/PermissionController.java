package com.sypay.omp.per.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录成功入口
 * @author dumengchao
 *
 */
@Controller
public class PermissionController {

    private final Log logger = LogFactory.getLog(PermissionController.class);

    @RequestMapping(value = "/toPermission.htm")
    public String toLogin(HttpServletRequest request) {
        if (request.getSession().getAttribute("menuList") == null) {
            return "redirect:main.htm";
        }
        
        return "permission";
    }

}
