package com.report.common.dal.admin.util;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

public class MybatisUtil {
	
	private static SqlSessionFactory factory;
	private static Configuration config;
	
	public static void setSqlSessionFactory(SqlSessionFactory factory)
	{
		MybatisUtil.factory = factory;
		MybatisUtil.config = MybatisUtil.factory.getConfiguration();
	}
	
	/**
	 * 将JavaBean中的属性名转换为数据库表中的列名
	 * 
	 * @param resultMapId 格式为：&lt;Mapper配置文件的namespace&gt;.&lt;resultMap的ID&gt;
	 * @param propertyName JavaBean的属性名
	 * @return 数据库表中对应的列名
	 */
	public static String propertyName2ColumnName(String resultMapId, String propertyName)
	{
		if(resultMapId == null)
		{
			throw new NullPointerException("resultMapId不能为空！");
		}
		
		if(propertyName == null)
		{
			throw new NullPointerException("propertyName不能为空！");
		}
		
		for(ResultMap resultMap : config.getResultMaps())
		{
			if(resultMapId.equals(resultMap.getId()))
			{
				for(ResultMapping mapping : resultMap.getResultMappings())
				{
					if(propertyName.equals(mapping.getProperty()))
					{
						return mapping.getColumn();
					}
				}
				
				break;
			}
		}
		
		return null;
	}
}
