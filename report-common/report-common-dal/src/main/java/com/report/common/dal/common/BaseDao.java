package com.report.common.dal.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.report.common.dal.query.entity.vo.PagerReq;

public interface BaseDao {

    Session getCurrentSession();

    Query getQuery(String hql);

    SQLQuery getSqlQuery(String sql);

    Serializable save(Object o);

    void delete(Object o);

    void update(Object o);

    void merge(Object o);

    boolean update(Class<?> clazz, Map<String, Object> paraMap);

    void saveOrUpdate(Object o);

    <T> T get(Class<T> entityClass, Long id);

    Object get(String hql);

    Object get(String hql, Map<String, Object> params);

    Object getBySql(String sql);

    Object getBySql(String sql, Map<String, Object> params);

    List find(String hql);

    <T> List<T> find(Class<T> entityClass);

    List find(String hql, Map<String, Object> params);


    List find(String hql, int page, int rows);

    List find(String hql, Map<String, Object> params, int page, int rows);

    List findBySql(String sql);

    List findBySql(String sql, Map<String, Object> params);

    List findBySql(String sql, int page, int rows);

    List findBySql(String sql, Map<String, Object> params, int page, int rows);

    int count(String hql);

    int count(String hql, Map<String, Object> params);

    int countBySql(String sql);

    int countBySql(String sql, Map<String, Object> params);

    int executeHql(String hql);

    int executeHql(String hql, Map<String, Object> params);

    int executeSql(String sql);

    int executeSql(String sql, Map<String, Object> params);
    
    List getMutiReportQueryData(PagerReq req);

}
