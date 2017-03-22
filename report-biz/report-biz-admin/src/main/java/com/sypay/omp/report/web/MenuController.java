package com.sypay.omp.report.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.common.dal.admin.constant.Constants.MenuType;
import com.sypay.omp.per.model.MenuCell;
import com.sypay.omp.per.util.PermissionUtil;
import com.sypay.omp.report.service.ReportService;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final  Log log = LogFactory.getLog(MenuController.class);
    @Autowired
    ReportService reportService;

    
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
