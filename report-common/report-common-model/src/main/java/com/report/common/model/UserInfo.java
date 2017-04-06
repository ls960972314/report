package com.report.common.model;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.constant.Constants.MenuType;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MenuCell;
import com.report.common.dal.common.utils.VerificationUtil;

import lombok.Data;

/**
 * 用户信息VO
 * @author lishun
 * @since 2017年3月30日 上午10:18:33
 */
@Data
public class UserInfo {
	
	/** 用户信息 */
	private Member member;
	
	/** 用户所属组Code */
	private String groupCode;
	
	/** 角色Code List集合 */
	private List<String> roleCodeList;
	
	/** 角色Code Set集合 */
	private Set<String>  roleCodeSet;
	
	/** 资源URL Set集合 */
	private Set<String> permissionCodeSet;
	
	/** 权限资源列表 */
	private List<MenuCell> menuList;
	
	/** 用户名 */
	public String getMemberName() {
		return StringUtils.isBlank(member.getName()) ? member.getAccNo() : member.getName();
	}
	
	/** 发送邮件时图片存储路径 */
	public String getImagePath() {
		return StringUtils.isBlank(member.getAccNo()) ? String.valueOf(Math.random()) : member.getAccNo();
	}
	
	/** 是否为管理员 */
	public boolean isAdmin() {
		return roleCodeList.contains(Constants.ADMIN_ACCOUNT);
	}
	
	/** 获得权限管理后台资源集合 */
	public List<MenuCell> getAdminMenuList() {
		if(!VerificationUtil.paramIsNull(menuList)) {
            for(MenuCell m: menuList) {
                if(MenuType.PERMISSION.equals(m.getMenuType())) {
                	return m.getChildren();
                }
            }
        }
		return Lists.newArrayList();
	}
	
	/** 获得报表平台资源集合 */
	public List<MenuCell> getReportMenuList() {
		if(!VerificationUtil.paramIsNull(menuList)) {
            for(MenuCell m: menuList) {
                if(MenuType.REPORT.equals(m.getMenuType())) {
                	return Lists.newArrayList(m);
                }
            }
        }
		return Lists.newArrayList();
	}
}
