package com.sypay.omp.per.init;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 系统初始化角色监听器
 *
 */
public class InitSysRoleListener implements javax.servlet.ServletContextListener {

    private final Log logger = LogFactory.getLog(InitSysRoleListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
    }

}
