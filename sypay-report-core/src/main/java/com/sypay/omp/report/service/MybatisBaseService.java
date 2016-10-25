package com.sypay.omp.report.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
public interface MybatisBaseService {
	
	/**
	 * 将属性名转为字段名
	 * @param mapId
	 * 					resultMap的ID值
	 * 
	 * @param propertyName
	 * 					属性名
	 * 
	 * @return
	 */
	public String[] parseProperty2Column(String mapId, String... propertyName);
	
	/**
	 * 查询一个对象
	 * @param sqlId
	 * @param obj  参数
	 */
	public Object selectOne(String sqlId,Object obj);
	
	/**
	 * 查询对象列表，并分页
	 * @param sqlId
	 * @param obj 参数
	 * @param row 分页参数
	 */
	public List selectList(String sqlId,Object obj,RowBounds row);
	
	/**
	 *  查询对象列表
	 * @param sqlId
	 * @param obj 参数
	 * @throws IwfDefaultDaoException
	 */
	public List selectList(String sqlId,Object obj);
	
	/**
	 * 查询，返回map数据	
	 * @param sqlId 
	 * @param obj 参数可以为空
	 * @param mapKey 查询列中的字段
	 * @param rowBounds 分页对象
	 * @return map
	 * @throws IwfDefaultDaoException
	 */
	public Map<Object,Object> selectMap(String sqlId,Object obj,String mapKey,RowBounds rowBounds);
	
	/**
	 * 查询，返回map数据	
	 * @param sqlId 
	 * @param mapKey 查询列中的字段
	 * @return
	 * @throws IwfDefaultDaoException
	 */
	public Map<Object,Object> selectMap(String sqlId,String mapKey);

	/**
	 * 查询，返回map数据	
	 * @param sqlId 
	 * @param obj 参数可以为空
	 * @param mapKey 查询列中的字段
	 * @return map	
	 * @throws IwfDefaultDaoException
	 */
	public Map<Object,Object> selectMap(String sqlId,Object obj,String mapKey);

	/**
	 * 修改，返回修改成功行数
	 * @param string
	 * @param storeIdAndNameMapping
	 * @return
	 */
	public int update(String sqlId, Object obj);
	
	/**
	 * 删除
	 * @param sqlId
	 * @param obj
	 * @return
	 */
	public int delete(String sqlId, Object obj);
	
	/**
	 * 保存
	 * @param sqlId
	 * @param obj
	 * @return
	 */
	public int insert(String sqlId, Object obj);
}
