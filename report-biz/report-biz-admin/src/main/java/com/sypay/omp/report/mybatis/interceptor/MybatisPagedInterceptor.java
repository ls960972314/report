package com.sypay.omp.report.mybatis.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Intercepts({@Signature(type = StatementHandler.class, method ="prepare", args = {Connection.class})}) 
public class MybatisPagedInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(MybatisPagedInterceptor.class);
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget(); 
		if(statementHandler instanceof RoutingStatementHandler){
			RoutingStatementHandler routeStatementHandler = (RoutingStatementHandler)statementHandler;
			MetaObject metaObject = SystemMetaObject.forObject(routeStatementHandler);
			int offset = Integer.valueOf(String.valueOf(metaObject.getValue("delegate.rowBounds.offset")));
			int limit = Integer.valueOf(String.valueOf(metaObject.getValue("delegate.rowBounds.limit")));
			if(offset == RowBounds.NO_ROW_OFFSET && limit == RowBounds.NO_ROW_LIMIT){
				return invocation.proceed();
			}
			String sql = String.valueOf(metaObject.getValue("delegate.boundSql.sql")).trim();
			if(StringUtils.isBlank(sql) || !sql.toLowerCase().startsWith("select")){
				return invocation.proceed();
			}
			String pagedSql = this.buildPagedSql(sql, offset, limit);
			metaObject.setValue("delegate.boundSql.sql", pagedSql);
			metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		}else{
			return invocation.proceed(); 
		}
		return invocation.proceed();
	}

	private String buildPagedSql(String sql, int offset,int limit){
		StringBuilder pagedSql =new StringBuilder(sql.length()+50);
		pagedSql.append("select ulccc.* from (");
		pagedSql.append(sql);
		pagedSql.append(")  ulccc  limit ").append(offset + ",").append((offset+limit));
		logger.debug("paged sql ：{}",pagedSql);
		return pagedSql.toString();
	}
	
	@Override
	public Object plugin(Object target) {
		 // 褰撶洰鏍囩被鏄疭tatementHandler绫诲瀷鏃讹紝鎵嶅寘瑁呯洰鏍囩被锛屽惁鑰呯洿鎺ヨ繑鍥炵洰鏍囨湰韬�鍑忓皯鐩爣琚唬鐞嗙殑  
	    // 娆℃暟  
	    if (target instanceof StatementHandler) {  
	        return Plugin.wrap(target, this);  
	    } else {  
	        return target;  
	    }  
	}

	@Override
	public void setProperties(Properties arg0) {
		
	}

}
