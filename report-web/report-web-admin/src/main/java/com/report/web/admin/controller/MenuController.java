package com.report.web.admin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.common.dal.admin.constant.Constants.MenuType;
import com.report.common.dal.admin.entity.vo.MenuCell;
import com.report.common.dal.common.PermissionUtil;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
    private final  Log log = LogFactory.getLog(MenuController.class);
    
    @RequestMapping("/loadMenuByPriv.htm") 
    @ResponseBody
    public List<MenuCell> loadMenuByPriv() throws Exception { 
        List<MenuCell> menuList = PermissionUtil.getMenuList();
        if(menuList != null && menuList.size() > 0) {
            for(MenuCell m: menuList) {
                if(MenuType.REPORT.equals(m.getMenuType())) {
                    List<MenuCell> list = new ArrayList<MenuCell>();
                    list.add(m);
                    return list;
                }
            }
        }
    	return Collections.emptyList();
    }
}
