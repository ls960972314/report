package com.report.common.dal.admin.entity.vo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("rawtypes")
@JsonInclude(Include.NON_NULL)
public class PageBean {
	
	private static final Class<List> DEFAULT_RESULT_LIST_TYPE = List.class;

	private static final Logger LOG = LoggerFactory.getLogger(PageBean.class);
	
	public static final int DEFAULT_PAGESIZE = 10;
	/**
	 * 每页数量
	 */
	private int pageSize = DEFAULT_PAGESIZE;
	/**
	 * 开始位置
	 */
	private int startIndex;
	/**
	 * 总数
	 */
	private long totalCount;
	/**
	 * 当前页数
	 */
	private int pageNo;
	/**
	 * 总页数
	 */
	private int totalPages;
	/**
	 * 排序字段
	 */
	private String sort;
	/**
	 * 排序类型，asc/desc
	 */
	private String order;
	/**
	 * 分页的结果
	 */
	
	private Object result;
	
	/**
	 * 泛型Class
	 */
	private Class clazz = DEFAULT_RESULT_LIST_TYPE;
	
	/**
	 * @param pageNo
	 * @param pageSize
	 * @param totalCount
	 */
	public PageBean() {
	}
	public PageBean(int pageNo, int pageSize, long totalCount) {
		this();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	public PageBean(int pageNo, int pageSize, long totalCount, Class resultList) {
		this();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.clazz = resultList;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		if(this.getPageNo()<=0){
			this.setStartIndex(0);
		}else if(this.getPageNo()>this.getTotalPages()){
			this.setStartIndex((this.getTotalPages()-1)*this.getPageSize());
		}else{
			this.setStartIndex((this.getPageNo()-1)*this.getPageSize());
		}
		return this.startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		int pages =  (int) (this.getTotalCount()/this.getPageSize());
		if(this.getTotalCount()%this.getPageSize()==0&&this.getTotalCount()!=0){
			this.setTotalPages(pages);
		}else{
			this.setTotalPages(pages+1);
		}
		return this.totalPages;
	}
	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 对查询结果进行重新计算
	 */
	public void resetPage(){
		if(0 == pageSize){
			pageSize = DEFAULT_PAGESIZE;
		}
		
		if(pageNo <= 0){
			pageNo = 1;
		}
		
		// 开始记录
		int startIndex = getStartIndex();
		this.setStartIndex(startIndex);
	}
	
	/**
	 * 对查询结果进行重新计算
	 */
	public void calculatePage(){
		if(0 == pageSize){
			pageSize = DEFAULT_PAGESIZE;
		}
		
		if(pageNo <= 0){
			pageNo = 1;
		}
		
		// 查询结果当前页为空，则查询前一页
		int size = 0;
		if(null != result){
			if(Collection.class.isAssignableFrom(clazz)){
				Collection col = (Collection) result;
				size = col.size();
			}else if(Map.class.isAssignableFrom(clazz)){
				Map map = (Map) result;
				size = map.size();
			}
		}
		if(totalCount > 0 && size == 0 && pageNo > 1){
			int pageNo = this.pageNo - 1;
			this.setPageNo(pageNo);
		}
		
		// 开始记录
		int startIndex = getStartIndex();
		this.setStartIndex(startIndex);
	}
	
//	/**
//	 * PO转VO
//	 * @param clazz
//	 * 			目标VO类
//	 * 
//	 */
//	public void convertPO2VO(Class clazz){
//		convertPO2VO(clazz, null);
//	}
//	
//	/**
//	 * PO转VO
//	 * @param clazz
//	 * 			目标VO类
//	 * 
//	 */
//	public void convertPO2VO(Class clazz, String[] ignoreProperties){
//		convertPO2VO(clazz, ignoreProperties, null);
//	}
//	
//	/**
//	 * 带转换器的PO转VO
//	 * @param clazz
//	 * 			目标VO类
//	 * 
//	 * @param convertors
//	 * 			转换器
//	 * 
//	 */
//	public void convertPO2VO(Class clazz, String[] ignoreProperties, Map<Class, Formatter> formatters){
//		if(null == clazz){
//			return;
//		}
//		if(null != result){
//			if(null == this.clazz){
//				this.clazz = DEFAULT_RESULT_LIST_TYPE;
//			}
//			if(DEFAULT_RESULT_LIST_TYPE.isAssignableFrom(this.clazz)){
//				List col = (List) result;
//				if(!col.isEmpty()){
//					List voList = new ArrayList(col.size());
//					for(Object po : col){
//						
//						Object vo;
//						try {
//							vo = clazz.newInstance();
//						} catch (Exception e) {
//							LOG.error("类：" + clazz.getName() + "使用默认构造器实例化对象异常！");
//							break;
//						}
//						BeanUtil.copyProperties(po, vo, ignoreProperties, formatters);
//						voList.add(vo);
//					}
//					this.setResult(voList);
//				}
//			}else if(Map.class.isAssignableFrom(this.clazz)){
//				Map map = (Map) result;
//				if(!map.isEmpty()){
//					Map voMap = new HashMap(map.size());
//					for(Object key : map.keySet()){
//						Object po = map.get(key);
//						Object vo;
//						try {
//							vo = clazz.newInstance();
//						} catch (Exception e) {
//							LOG.error("类：" + clazz.getName() + "使用默认构造器实例化对象异常！");
//							break;
//						}
//						BeanUtil.copyProperties(po, vo, ignoreProperties, formatters);
//						voMap.put(key, vo);
//					}
//					this.setResult(voMap);
//				}
//			}
//		}
//	}
	
	/**
	 * 转换为Mybatis的分页对象
	 * @return
	 */
	public RowBounds toRowBounds(){
		int startIndex = 0;
		if(this.getPageNo() > 0){
			startIndex = (this.getPageNo()-1)*this.getPageSize();
		}
		RowBounds rb = new RowBounds(startIndex, this.getPageSize());
		return rb;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
