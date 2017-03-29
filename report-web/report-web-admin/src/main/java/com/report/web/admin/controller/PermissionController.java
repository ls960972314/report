package com.report.web.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class PermissionController {

    @RequestMapping(value = "/toPermission.htm")
    public String toLogin(HttpServletRequest request) {
        if (request.getSession().getAttribute("menuList") == null) {
            return "redirect:main.htm";
        }
        
        return "permission";
    }

}
