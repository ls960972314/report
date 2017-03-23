package com.report.facade.entity.vo;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.report.facade.DaoException;

public class MultiDataSource implements DataSource, ApplicationContextAware {
	private final  Logger log = LoggerFactory.getLogger(MultiDataSource.class);
	private ApplicationContext applicationContext = null;
	private DataSource dataSource = null;

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public Connection getConnection(String arg0, String arg1) throws SQLException {
		return getDataSource().getConnection(arg0, arg1);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		getDataSource().setLogWriter(arg0);
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		getDataSource().setLoginTimeout(arg0);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public DataSource getDataSource(String dataSourceName) {
		try {
			if ((dataSourceName == null) || (dataSourceName.equals(""))) {
				dataSourceName = SpObserver.defaultDataBase;
			}
			return ((DataSource) this.applicationContext.getBean(dataSourceName));
		} catch (NoSuchBeanDefinitionException ex) {
			log.error("getDataSource exception:", ex);
			throw new DaoException("There is not the dataSource <name:" + dataSourceName + "> in the applicationContext!");
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		String sp = SpObserver.getSp();
		return getDataSource(sp);
	}


	 /* JDBC_4_ANT_KEY_BEGIN */
	public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("MultiDataSource is not a wrapper.");
    }

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		 return false;
	}
	/* JDBC_4_ANT_KEY_END */

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}