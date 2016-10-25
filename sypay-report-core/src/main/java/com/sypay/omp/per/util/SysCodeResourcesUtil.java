package com.sypay.omp.per.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sypay.omp.per.model.PackageResourceModel;

/**
 * 
 * @author dumengchao
 *
 * @date 2014年11月26日
 */
public class SysCodeResourcesUtil {

    private final static Log logger = LogFactory.getLog(SysCodeResourcesUtil.class);

    public static final int UPDATE_FLAG = 1;
    public static final int SAVE_FLAG = 2;
    public static final int DELETE_FLAG = 3;

    // 缓存各个系统资源 key:sysCode value: list<PackageResourceModel>
    //	public static Map<String, List<PackageResourceModel>> sysCodeResourcesMap = new HashMap<String, List<PackageResourceModel>>();

    /**
     * 查询所有资源
     * @param sysCodeMap 
     * @param resourceIDs
     * @return
     */
    public static List<PackageResourceModel> findResourceListFromCache(Map<String, List<PackageResourceModel>> sysCodeResourcesMap) {
        return findResourceListFromCache(sysCodeResourcesMap, null, true);
    }

    /**
     * 查询所有资源并回显选择状态
     * @param resourceIDs
     * @return
     */
    public static List<PackageResourceModel> findResourceListAndCheckSelectStatus(Map<String, List<PackageResourceModel>> sysCodeResourcesMap, List<Long> resourceIDs) {
        return findResourceListFromCache(sysCodeResourcesMap, resourceIDs, true);
    }

    /**
     * 查询拥有权限的资源并回显选择状态
     * @param resourceIDs
     * @return
     */
    public static List<PackageResourceModel> findResourceListForPrivAndCheckSelectStatus(Map<String, List<PackageResourceModel>> sysCodeResourcesMap, List<Long> resourceIDs) {
        return findResourceListFromCache(sysCodeResourcesMap, resourceIDs, false);
    }

    /**
     * 查询资源列表
     * @param resourceIDs
     * @param queryAllFlag 如果为true，则将查询所有的资源；如果为false，则只查询指定角色所拥有的资源
     * @return
     */
    public static List<PackageResourceModel> findResourceListFromCache(Map<String, List<PackageResourceModel>> sysCodeResourcesMap, List<Long> resourceIDs, boolean queryAllFlag) {

        // 菜单结果
        List<PackageResourceModel> resourceModelList = Collections.emptyList();

        Map<Long, PackageResourceModel> permissionMap = new LinkedHashMap<Long, PackageResourceModel>();
        List<PackageResourceModel> packageResourceModelList = Collections.emptyList();

        for (Entry<String, List<PackageResourceModel>> entry : sysCodeResourcesMap.entrySet()) {
            packageResourceModelList = entry.getValue();
            if (null == packageResourceModelList || packageResourceModelList.size() == 0) {
                continue;
            }

            if (queryAllFlag) {
                for (PackageResourceModel cell : packageResourceModelList) {
                    if (!permissionMap.containsKey(cell.getId())) {
                        permissionMap.put(cell.getId(), cell);
                    }
                }
            } else {
                for (PackageResourceModel cell : packageResourceModelList) {
                    if (!permissionMap.containsKey(cell.getId()) && resourceIDs.contains(cell.getId())) {
                        permissionMap.put(cell.getId(), cell);
                    }
                }
            }
        }

        resourceModelList = setResourceStyle(resourceModelList, permissionMap, resourceIDs);

        return resourceModelList;
    }

    /**
     * 设置resource父子关系格式
     * @param resourceModelList
     * @param permissionMap
     * @param resourceIDs
     * @return
     */
    private static List<PackageResourceModel> setResourceStyle(List<PackageResourceModel> resourceModelList, Map<Long, PackageResourceModel> permissionMap, List<Long> resourceIDs) {
        List<PackageResourceModel> permissions = new ArrayList<PackageResourceModel>(permissionMap.values());

        if (!permissions.isEmpty()) {
            resourceModelList = new ArrayList<PackageResourceModel>();
            for (int i = 0; i < permissions.size(); i++) {
                PackageResourceModel permission = permissions.get(i);

                // 第一层级菜单
                Long rootId = permission.getpId();
                if (null == rootId || 0 == rootId) {
                    PackageResourceModel rootMenu = new PackageResourceModel();

                    Long menuId = setPermissionMenu(permission, rootMenu);
                    // 获取第二层级菜单
                    List<PackageResourceModel> grandsons = getChild(menuId, permissions);
                    rootMenu.setChildren(grandsons);
                    resourceModelList.add(rootMenu);
                }
            }
            if (!resourceModelList.isEmpty()) {
                Collections.sort(resourceModelList);
            }
        }
        // 设置选中状态
        setSelectedNodeStatus(resourceModelList, resourceIDs);

        return resourceModelList;
    }

    private static void setSelectedNodeStatus(List<PackageResourceModel> resourceModelList, List<Long> resourceIDs) {
        if (resourceIDs != null && resourceIDs.size() > 0) {
            for (PackageResourceModel pr : resourceModelList) {
                if (pr.getChildren() != null && pr.getChildren().size() > 0) {
                    setSelectedNodeStatus(pr.getChildren(), resourceIDs);
                } else if (hasPrivForResource(pr.getId(), resourceIDs)) {
                    pr.setChecked(true);
                }
            }
        }
    }

    private static boolean hasPrivForResource(Long targetId, List<Long> resourceIDs) {
        if (resourceIDs == null || resourceIDs.size() == 0) {
            return false;
        }
        for (Long rId : resourceIDs) {
            if (targetId.equals(rId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取子菜单列表
     * 
     * @param parentId
     * @param permissions
     * @return
     */
    private static List<PackageResourceModel> getChild(Long parentId, List<PackageResourceModel> permissions) {
        List<PackageResourceModel> children = Collections.emptyList();
        if (null == permissions || permissions.isEmpty()) {
            return children;
        }
        children = new ArrayList<PackageResourceModel>();
        for (PackageResourceModel permission : permissions) {
            if (String.valueOf(parentId).equals(String.valueOf(permission.getpId()))) {
                PackageResourceModel menu = new PackageResourceModel();
                Long menuId = setPermissionMenu(permission, menu);
                // 循环查询子菜单列表
                List<PackageResourceModel> grandsons = getChild(menuId, permissions);
                menu.setChildren(grandsons);
                children.add(menu);
            }
        }

        // 根据orderBy字段排序
        Collections.sort(children);
        return children;
    }

    private static Long setPermissionMenu(PackageResourceModel permission, PackageResourceModel menu) {
        Long menuId = permission.getId();
        menu.setId(menuId);
        menu.setResourceCode(permission.getResourceCode());
        menu.setText(permission.getName());
        menu.setName(permission.getName());
        menu.setResourceAction(permission.getResourceAction());
        menu.setOrderBy(permission.getOrderBy());
        menu.setCreateTime(permission.getCreateTime());
        menu.setUpdateTime(permission.getUpdateTime());
        menu.setDescription(permission.getDescription());
        menu.setpId(permission.getpId());
        menu.setResourceType(permission.getResourceType());
        menu.setSysCode(permission.getSysCode());
        return menuId;
    }

    public static void putResource(Map<String, List<PackageResourceModel>> sysCodeResourcesMap, Map<String, Object> m) {

        Object sysCode = m.get("SYS_CODE") == null ? m.get("sys_code") : m.get("SYS_CODE");
        if (sysCode == null) {
            return;
        }
        List<PackageResourceModel> packageResourceModelList = new ArrayList<PackageResourceModel>();

        PackageResourceModel packageResourceModel = null;
        if (!sysCodeResourcesMap.containsKey(sysCode.toString())) {
            packageResourceModel = setResourceModelValue(m);
            packageResourceModelList.add(packageResourceModel);
            sysCodeResourcesMap.put(packageResourceModel.getSysCode(), packageResourceModelList);
        } else {
            packageResourceModelList = sysCodeResourcesMap.get(sysCode.toString());
            packageResourceModel = setResourceModelValue(m);
            packageResourceModelList.add(packageResourceModel);
            sysCodeResourcesMap.put(sysCode.toString(), packageResourceModelList);
        }
    }

    private static PackageResourceModel setResourceModelValue(Map<String, Object> m) {
        PackageResourceModel packageResourceModel = new PackageResourceModel();
        packageResourceModel.setId(Long.valueOf(String.valueOf(m.get("id"))));
        packageResourceModel.setResourceAction(m.get("resource_action") == null ? null : String.valueOf(m.get("resource_action")));
        packageResourceModel.setResourceCode(m.get("resource_code") == null ? null : String.valueOf(m.get("resource_code")));
        packageResourceModel.setName(String.valueOf(m.get("name")));
        packageResourceModel.setResourceType(m.get("resource_type") == null ? null : String.valueOf(m.get("resource_type")));
        packageResourceModel.setpId(m.get("p_id") == null ? null : Long.valueOf(String.valueOf(m.get("p_id"))));
        packageResourceModel.setDescription(m.get("description") == null ? null : String.valueOf(m.get("description")));
        packageResourceModel.setCreateTime(m.get("create_time") == null ? null : (Date) m.get("create_time"));
        packageResourceModel.setUpdateTime(m.get("update_time") == null ? null : (Date) m.get("update_time"));
        packageResourceModel.setOrderBy(m.get("order_by") == null ? null : Integer.parseInt(String.valueOf(m.get("order_by"))));
        packageResourceModel.setSysCode(m.get("sys_code") == null ? null : String.valueOf(m.get("sys_code")));
        return packageResourceModel;
    }
    
    private static PackageResourceModel setResourceModelValue_oracle(Map<String, Object> m) {
        PackageResourceModel packageResourceModel = new PackageResourceModel();
        packageResourceModel.setId(Long.valueOf(String.valueOf(m.get("ID"))));
        packageResourceModel.setResourceAction(m.get("RESOURCE_ACTION") == null ? null : String.valueOf(m.get("RESOURCE_ACTION")));
        packageResourceModel.setResourceCode(m.get("RESOURCE_CODE") == null ? null : String.valueOf(m.get("RESOURCE_CODE")));
        packageResourceModel.setName(String.valueOf(m.get("NAME")));
        packageResourceModel.setResourceType(m.get("RESOURCE_TYPE") == null ? null : String.valueOf(m.get("RESOURCE_TYPE")));
        packageResourceModel.setpId(m.get("P_ID") == null ? null : Long.valueOf(String.valueOf(m.get("P_ID"))));
        packageResourceModel.setDescription(m.get("DESCRIPTION") == null ? null : String.valueOf(m.get("DESCRIPTION")));
        packageResourceModel.setCreateTime(m.get("CREATE_TIME") == null ? null : (Date) m.get("CREATE_TIME"));
        packageResourceModel.setUpdateTime(m.get("UPDATE_TIME") == null ? null : (Date) m.get("UPDATE_TIME"));
        packageResourceModel.setOrderBy(m.get("ORDER_BY") == null ? null : Integer.parseInt(String.valueOf(m.get("ORDER_BY"))));
        packageResourceModel.setSysCode(m.get("SYS_CODE") == null ? null : String.valueOf(m.get("SYS_CODE")));
        return packageResourceModel;
    }

    //	/**
    //	 * 重新加载缓存 
    //	 * @param sysCode
    //	 * @param resource
    //	 * @param editFlag 1：update 2:save 3:删除
    //	 */
    //	public static void reloadCache(String sysCode, Resource resource, int editFlag) {
    //		List<PackageResourceModel> resourceModelList = sysCodeResourcesMap.get(sysCode);
    //		if(resourceModelList == null || resourceModelList.size() == 0) {
    //			logger.debug("resource 不存在sysCode=" + sysCode);
    //			resourceModelList = new ArrayList<PackageResourceModel>();
    //		}
    //		
    //		if(editFlag == UPDATE_FLAG) {
    //			logger.debug("【更新】resource 更新缓存数据 resourceId=" + resource.getId() + ", sysCode=" + sysCode);
    //			for(PackageResourceModel pr: resourceModelList) {
    //				if(pr.getId().equals(resource.getId())) {
    //					setResource(resource, pr);
    //					break;
    //				}
    //			}
    //		} else if(editFlag == SAVE_FLAG) {
    //			logger.debug("【保存】resource 更新缓存数据 resourceId=" + resource.getId() + ", sysCode=" + sysCode);
    //			PackageResourceModel packageResourceModel = new PackageResourceModel();
    //			setResource(resource, packageResourceModel);
    //			resourceModelList.add(packageResourceModel);
    //			sysCodeResourcesMap.put(sysCode, resourceModelList);
    //		} else if(editFlag == DELETE_FLAG) {
    //			logger.debug("【删除】resource 更新缓存数据 resourceId=" + resource.getId() + ", sysCode=" + sysCode);
    //			Iterator<PackageResourceModel> iterator = resourceModelList.iterator();
    //			 while(iterator.hasNext()) {
    //				 PackageResourceModel pr = iterator.next();
    //				 if(pr.getId().equals(resource.getId())) {
    //					 iterator.remove();
    //					 break;
    //				 }
    //			 }
    //		}
    //	}

    //	private static void setResource(Resource source, PackageResourceModel target) {
    //		target.setId(source.getId());
    //		target.setResourceCode(source.getResourceCode());
    //		target.setName(source.getName());
    //		target.setText(source.getName());
    //		target.setResourceAction(source.getResourceAction());
    //		target.setResourceType(source.getResourceType());
    //		target.setCreateTime(source.getCreateTime());
    //		target.setUpdateTime(source.getUpdateTime());
    //		target.setDescription(source.getDescription());
    //		target.setSysCode(source.getSysCode());
    //		target.setOrderBy(source.getOrderBy());
    //		if(source.getParent() != null) {
    //			target.setpId(source.getParent().getId());
    //		}
    //	}

}
