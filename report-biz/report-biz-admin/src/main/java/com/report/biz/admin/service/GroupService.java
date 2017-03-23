package com.report.biz.admin.service;
import java.util.List;
import java.util.Map;

import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.model.AjaxJson;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;

/**   
 *
 * @Description: 组服务类
 * @date 2014-10-14 14:10:26
 * @version V1.0   
 *
 */
public interface GroupService {

	/**
	 * 查找列表
	 * @param pageHelper 
	 * 
	 * @param model 中间类
	 * @param pageHelper 分页对象
	 */
	DataGrid findGroups(PageHelper pageHelper, GroupModel groupModel);

	/**
	 * 更新对象
	 * 
	 * @param model
	 */
	int updateGroup(GroupModel groupModel, Long currentMemberId, String memberIp);

	/**
	 * 保存对象
	 * 
	 * @param model
	 */
	int saveGroup(GroupModel groupModel, Long currentMemberId, String memberIp);
	
	/**
	 * 删除对象
	 * 
	 * @param id
	 * @return
	 */
	AjaxJson deleteGroupById(Long id, Long currentMemberId, String memberIp);
	
	List<Group> findAllGroups();

	List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId);

	boolean isGroupCodeExists(GroupModel groupModel);

	String getGroupCodeByMemberId(Long memberId);

	boolean isSameGroupCode(Long id, String groupCode);
}
