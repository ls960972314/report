package com.sypay.omp.report.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.alibaba.fastjson.JSON;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.dataBase.MultiDataSource;
import com.sypay.omp.report.dataBase.SpObserver;
import com.sypay.omp.report.queryrule.Condition;
import com.sypay.omp.report.queryrule.PagerReq;
import com.sypay.omp.report.util.BeanUtil;
import com.sypay.omp.report.util.StringUtil;
import com.sypay.omp.report.web.ContextHelper;

public class BaseDaoImpl implements BaseDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List getMutiReportQueryData(PagerReq req) {
    	List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
    	List list = null;
    	Session session = null;
    	try {
			MultiDataSource multiDataSource = ContextHelper.getInstance().getBean("dataSource");
			multiDataSource.setDataSource(multiDataSource.getDataSource(SpObserver.getSp()));
			session = sessionFactory.openSession();
	        Query query = session.createSQLQuery(req.getBaseSql());
	        for (Condition con : conList) {
	            query.setParameter(con.getName(), con.getValue());
	        }
	        query.setFirstResult((req.getPage() - 1) * req.getRows());
	        query.setMaxResults(req.getRows());
	        list = query.list();
		} catch (Exception e) {
			logger.error("getMutiReportQueryData Exception", e);
		} finally {
             session.close();
        }
    	return list;
        
    }
    
    public Session getCurrentSession() {
    	try {
			MultiDataSource multiDataSource = ContextHelper.getInstance().getBean("dataSource");
			multiDataSource.setDataSource(multiDataSource.getDataSource(SpObserver.getSp()));
		} catch (Exception e) {
			logger.error("getCurrentSession setMultiDataSource Exception", e);
		}
    	
        Session session = null;
        Object value = TransactionSynchronizationManager.getResource(this.sessionFactory);
        if (null == value) {
            session = sessionFactory.openSession();
        } else if (value instanceof SessionHolder) {
        	SessionHolder sessionHolder = null;
        	
        	if (StringUtil.isEmpty(SpObserver.getSp()) || (StringUtil.isNotEmpty(SpObserver.getSp()) && SpObserver.getSp().equals(SpObserver.defaultDataBase))) {
        		sessionHolder = (SessionHolder) value;
                session = sessionHolder.getSession();
        	} else {
        		session = sessionFactory.getCurrentSession();
        	}
            if (null == session) {
                session = sessionFactory.openSession();
                BeanUtil.copyProperty(sessionHolder, "session", session);
            }
        } else {
            session = this.sessionFactory.getCurrentSession();
        }
        return session;
    }

    public Query getQuery(String hql) {
        return this.getCurrentSession().createQuery(hql);
    }

    public SQLQuery getSqlQuery(String sql) {
        return this.getCurrentSession().createSQLQuery(sql);
    }

    public Serializable save(Object o) {
        Session currentSession = this.getCurrentSession();
        Serializable id = null;
        if (null != currentSession) {
            try {
                id = currentSession.save(o);
            } catch (Exception e) {
                logger.error("保存对象异常！object=" + o, e);
            } finally {
                // currentSession.close();
            }
        }

        return id;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> entityClass, Long id) {
        Session currentSession = this.getCurrentSession();
        Object obj = null;
        if (null != currentSession) {
            try {
                obj = currentSession.get(entityClass, id);
            } catch (Exception e) {
                logger.error("根据ID查询对象异常！class=" + entityClass + ", id=" + id, e);
            } finally {
                // currentSession.close();
            }
        }
        return (T) obj;
    };

    public Object get(String hql) {
        return this.get(hql, null);
    }

    public Object get(String hql, Map<String, Object> params) {
        List<?> resultList = this.find(hql, params);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public Object getBySql(String sql) {
        return this.getBySql(sql, null);
    }

    public Object getBySql(String sql, Map<String, Object> params) {
        List<?> resultList = this.findBySql(sql, params);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public void delete(Object o) {
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            try {
                currentSession.delete(o);
            } catch (Exception e) {
                logger.error("删除对象异常！obj=" + o, e);
            } finally {
                // currentSession.close();
            }
        }
    }

    public void merge(Object o) {
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            try {
                currentSession.merge(o);
            } catch (Exception e) {
                logger.error("修改对象异常！obj=" + o, e);
            } finally {
                // currentSession.close();
            }
        }
    }

    public void update(Object o) {
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            try {
                currentSession.update(o);
            } catch (Exception e) {
                logger.error("修改对象异常！obj=" + o, e);
            } finally {
                // currentSession.close();
            }
        }
    }

    public int update(String hql, Map<String, Object> params) {
        int result = 0;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession && StringUtils.isNotBlank(hql)) {
            Query q = currentSession.createQuery(hql);
            try {
                setParameter(params, q);
                result = q.executeUpdate();
            } catch (HibernateException e) {
                logger.error("修改对象异常！hql=" + hql + ", params=" + params, e);
            } finally {
                // currentSession.close();
            }
        }
        return result;
    }

    /**
     * 设置参数
     * @param params
     * @param q
     */
    private void setParameter(Map<String, Object> params, Query q) {
        if (null != params && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                boolean isList = false;
                if (null != value) {
                    if (value.getClass().isArray()) {
                        isList = true;
                        q.setParameterList(key, (Object[]) value);
                    } else if (value instanceof Collection) {
                        isList = true;
                        q.setParameterList(key, (Collection<?>) value);
                    }
                }

                if (!isList) {
                    q.setParameter(key, value);
                }
            }
        }
    }

    /**
     * 修改表记录
     * @param clazz
     *                  目标类
     * 
     * @param paraMap
     *                  目标字段键值对
     * 
     * @return
     */
    public boolean update(Class<?> clazz, Map<String, Object> paraMap) {
        return this.update(clazz, paraMap);
    }

    public void saveOrUpdate(Object o) {
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            try {
                currentSession.saveOrUpdate(o);
            } catch (Exception e) {
                logger.error("保存或修改对象异常！o=" + o, e);
            } finally {
                // currentSession.close();
            }
        }
    }

    /**
     * 查询
     * @param hql
     * 
     * @return
     */
    public List<?> find(String hql) {
        List<?> resultList = null;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Query q = currentSession.createQuery(hql);
            try {
                resultList = q.list();
            } catch (HibernateException e) {
                logger.error("查询对象异常！hql=" + hql, e);
            } finally {
                // currentSession.close();
            }
        }
        return resultList;
    }

    /**
     * 查询
     * @param entityClass
     *                  实体类
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> entityClass) {
        List<T> resultList = null;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Criteria criteria = currentSession.createCriteria(entityClass);
            try {
                resultList = criteria.list();
            } catch (HibernateException e) {
                logger.error("查询对象异常！class=" + entityClass, e);
            } finally {
                // currentSession.close();
            }
        }
        return resultList;
    }

    /**
     * 分页查询
     * @param hql
     * 
     * @param page
     *                  页数
     * 
     * @param rows
     *                  每页最大记录数
     * 
     * @return
     */
    public List<?> find(String hql, int page, int rows) {
        return this.find(hql, null, page, rows);
    }

    /**
     * 查询
     * @param hql
     * 
     * @param params
     *                  查询条件
     * 
     * @return
     */
    public List<?> find(String hql, Map<String, Object> params) {
        List<?> resultList = null;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Query q = currentSession.createQuery(hql);
            try {
                setParameter(params, q);
                resultList = q.list();
            } catch (HibernateException e) {
                logger.error("查询对象异常！hql=" + hql + ", params=" + params, e);
            } finally {
                // currentSession.close();
            }

        }
        return resultList;
    }

    /**
     * 分页查询
     * @param hql
     * 
     * @param params
     *                  查询条件
     * 
     * @param page
     *                  页数
     * 
     * @param rows
     *                  每页最大记录数
     * 
     * @return
     */
    public List<?> find(String hql, Map<String, Object> params, int page, int rows) {
        List<?> resultList = Collections.emptyList();
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Query q = currentSession.createQuery(hql);
            try {
                setParameter(params, q);
                resultList = q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
            } catch (HibernateException e) {
                logger.error("分页查询对象异常！hql=" + hql + ", params=" + params + ", page=" + page + ", rows=" + rows, e);
            } finally {
                // currentSession.close();
            }
        }
        return resultList;
    }

    public List<?> findBySql(String sql) {
        return this.findBySql(sql, null);
    }

    public List<?> findBySql(String sql, Map<String, Object> params) {
        List<?> resultList = Collections.emptyList();
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            SQLQuery q = currentSession.createSQLQuery(sql);
            try {
                setParameter(params, q);
                resultList = q.list();
            } catch (HibernateException e) {
                logger.error("根据SQL查询对象异常！sql=" + sql + ", params=" + params, e);
            } finally {
                // currentSession.close();
            }
        }
        return resultList;
    }

    public List<?> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        List<?> resultList = Collections.emptyList();
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            SQLQuery q = currentSession.createSQLQuery(sql);
            try {
                setParameter(params, q);
                resultList = q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
            } catch (HibernateException e) {
                logger.error("根据SQL分页查询对象异常！sql=" + sql + ", params=" + params + ", page=" + page + ", rows=" + rows, e);
            } finally {
                // currentSession.close();
            }
        }
        return resultList;
    }

    public List<?> findBySql(String sql, int page, int rows) {
        return this.findBySql(sql, null, page, rows);
    }

    public int count(String hql) {
        return this.count(hql, null);
    }

    public int count(String hql, Map<String, Object> params) {
        int count = 0;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Query q = currentSession.createQuery(prepareCountHql(hql));
            try {
                setParameter(params, q);
                count = Integer.valueOf(String.valueOf(q.uniqueResult()));
            } catch (NumberFormatException e) {
                logger.error("数据类型转换异常！", e);
            } catch (HibernateException e) {
                logger.error("统计对象异常！hql=" + hql + ", params=" + params, e);
            } finally {
                // currentSession.close();
            }
        }
        return count;
    }

    public int countBySql(String sql) {
        return this.countBySql(sql, null);
    }

    public int countBySql(String sql, Map<String, Object> params) {
        int result = 0;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            SQLQuery q = currentSession.createSQLQuery(prepareCountHql(sql));
            try {
                setParameter(params, q);
                result = Integer.valueOf(String.valueOf(q.uniqueResult()));
            } catch (HibernateException e) {
                logger.error("统计对象异常！sql=" + sql + ", params=" + params, e);
            } finally {}
        }
        return result;
    }

    public int executeSql(String sql) {
        return this.executeSql(sql, null);
    }

    public int executeSql(String sql, Map<String, Object> params) {
        int result = 0;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Query q = currentSession.createSQLQuery(sql);
            try {
                setParameter(params, q);
                result = q.executeUpdate();
            } catch (HibernateException e) {
                logger.error("统计对象异常！sql=" + sql + ", params=" + params, e);
            } finally {
                // currentSession.close();
            }
        }
        return result;
    }

    public int executeHql(String hql) {
        return this.executeHql(hql, null);
    }

    public int executeHql(String hql, Map<String, Object> params) {
        int result = 0;
        Session currentSession = this.getCurrentSession();
        if (null != currentSession) {
            Query q = currentSession.createQuery(hql);
            try {
                setParameter(params, q);
                result = q.executeUpdate();
            } catch (HibernateException e) {
                logger.error("统计对象异常！hql=" + hql + ", params=" + params, e);
            } finally {
                // currentSession.close();
            }
        }
        return result;
    }

    private String prepareCountHql(String orgHql) {
        String countHql = "select count (*) " + removeSelect(removeOrders(orgHql));
        return countHql;
    }

    private static String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        return hql.substring(beginPos);
    }

    private static String removeOrders(String hql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
