package com.sypay.omp.per.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.model.MenuCell;
import com.sypay.omp.per.model.PermissionCell;
import com.sypay.omp.per.service.ResourceService;

/**
 * 
 * @author dumengchao
 * @date 20141018
 * 
 */
public class PermissionUtil {

    private static final Logger logger = LoggerFactory.getLogger(PermissionUtil.class);

    private static ResourceService resourceService;


    /**
     * 获取会员的权限列表
     * 
     * @return
     */
    public static List<MenuCell> getMenuList() {
        HttpSession session = SessionUtil.getHttpSession();
        
        if(session.getAttribute(Constants.REPORT_MENU_LIST) != null) {
            return (List<MenuCell>) session.getAttribute(Constants.REPORT_MENU_LIST);
        }
        
        Long memberId = Long.valueOf(String.valueOf(session.getAttribute(Constants.SESSION_LOGIN_MEMBER_ID)));
        List<PermissionCell> permissions = resourceService.findPermissionCellByMemberId(memberId);

        return packSortedMenus(permissions);
    }

    private static List<MenuCell> packSortedMenus(List<PermissionCell> permissions) {
        // 菜单结果
        List<MenuCell> menus = Collections.emptyList();
        if (!permissions.isEmpty()) {
            menus = new ArrayList<MenuCell>();
            for (int i = 0; i < permissions.size(); i++) {
                PermissionCell permission = permissions.get(i);

                // 第一层级菜单
                Long rootId = permission.getpId();
                if (null == rootId || 0 == rootId) {
                    MenuCell rootMenu = new MenuCell();
                    Long id = permission.getId();
                    rootMenu.setId(id);
                    rootMenu.setCode(permission.getResourceCode());
                    rootMenu.setText(permission.getName());
                    rootMenu.setIcon(permission.getIcon());
                    rootMenu.setUrl(permission.getResourceAction());
                    rootMenu.setOrderBy(permission.getOrderBy());
                    rootMenu.setResourceType(permission.getResourceType());
                    rootMenu.setPId(permission.getpId());
                    rootMenu.setDescription(permission.getDescription());
                    rootMenu.setMenuType(permission.getSysCode());
                    // 获取第二层级菜单
                    List<MenuCell> grandsons = getChild(id, permissions);
                    rootMenu.setChildren(grandsons);
                    menus.add(rootMenu);
                }
            }
            if (!menus.isEmpty()) {
                Collections.sort(menus);
            }
        }
        return menus;
    }

    /**
     * 获取子菜单列表
     * 
     * @param parentId
     * @param permissions
     * @return
     */
    private static List<MenuCell> getChild(Long parentId, List<PermissionCell> permissions) {
        List<MenuCell> children = Collections.emptyList();
        if (null == permissions || permissions.isEmpty()) {
            return children;
        }
        children = new ArrayList<MenuCell>();
        for (PermissionCell permission : permissions) {
            if (String.valueOf(parentId).equals(String.valueOf(permission.getpId()))) {
                MenuCell menu = new MenuCell();
                Long menuId = permission.getId();
                menu.setId(menuId);
                menu.setCode(permission.getResourceCode());
                menu.setText(permission.getName());
                menu.setIcon(permission.getIcon());
                menu.setUrl(permission.getResourceAction());
                menu.setOrderBy(permission.getOrderBy());
                menu.setResourceType(permission.getResourceType());
                menu.setPId(permission.getpId());
                menu.setDescription(permission.getDescription());
                menu.setMenuType(permission.getSysCode());
                // 循环查询子菜单列表
                List<MenuCell> grandsons = getChild(menuId, permissions);
                menu.setChildren(grandsons);
                children.add(menu);
            }
        }

        // 根据orderBy字段排序
        Collections.sort(children);
        return children;
    }

    public static void setResourceService(ResourceService resourceService) {
        PermissionUtil.resourceService = resourceService;
    }

}
