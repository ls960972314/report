package com.sypay.omp.report.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sypay.omp.report.service.MybatisBaseService;

public class MybatisBaseServiceImpl implements MybatisBaseService {

    private static final Logger logger = LoggerFactory.getLogger(MybatisBaseServiceImpl.class);

    protected SqlSession sqlSession;

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

    public String[] parseProperty2Column(String mapId, String... propertyName) {
        String[] columnName = null;
        if (StringUtils.isBlank(mapId) || null == propertyName || propertyName.length == 0) {
            return columnName;
        }
        columnName = new String[propertyName.length];
        Configuration configuration = sqlSession.getConfiguration();
        if (null != configuration) {
            ResultMap resultMap = configuration.getResultMap(mapId);
            if (null != resultMap) {
                List<ResultMapping> resultMappings = resultMap.getResultMappings();
                for (int i = 0; i < propertyName.length; i++) {
                    if (null != resultMappings && !resultMappings.isEmpty()) {
                        for (ResultMapping rm : resultMappings) {
                            if (propertyName[i].equals(rm.getProperty())) {
                                columnName[i] = rm.getColumn();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return columnName;
    }

    public Object selectOne(String sqlId, Object obj) {
        try {
            if (obj == null) {
                return sqlSession.selectOne(sqlId);
            } else {
                return sqlSession.selectOne(sqlId, obj);
            }
        } catch (Exception e) {
            logger.error("select one error", e);
        }
        return null;
    }

    public List selectList(String sqlId, Object obj, RowBounds row) {
        try {
            return sqlSession.selectList(sqlId, obj, row);
        } catch (Exception e) {
            logger.error("select list error", e);
        }
        return null;
    }

    public List selectList(String sqlId, Object obj) {
        try {
            if (obj == null) {
                return sqlSession.selectList(sqlId);
            } else {
                return sqlSession.selectList(sqlId, obj);
            }
        } catch (Exception e) {
            logger.error("select list error", e);
        }

        return null;
    }

    public Map<Object, Object> selectMap(String sqlId, Object obj, String mapKey, RowBounds rowBounds) {
        try {
            if (obj == null) {
                return this.selectMap(sqlId, mapKey);
            }
            return sqlSession.selectMap(sqlId, obj, mapKey, rowBounds);
        } catch (Exception e) {
            logger.error("select Map error", e);
        }

        return null;
    }

    public Map<Object, Object> selectMap(String sqlId, Object obj, String mapKey) {
        try {
            if (obj == null) {
                return this.selectMap(sqlId, mapKey);
            }
            return sqlSession.selectMap(sqlId, obj, mapKey);
        } catch (Exception e) {
            logger.error("select Map error", e);
        }
        return null;
    }

    public Map<Object, Object> selectMap(String sqlId, String mapKey) {
        try {
            return sqlSession.selectMap(sqlId, mapKey);
        } catch (Exception e) {
            logger.error("select Map error", e);
        }
        return null;
    }

    public int delete(String sqlId, Object obj) {
        try {
            return sqlSession.delete(sqlId, obj);
        } catch (Exception e) {
            logger.error("delte  error", e);
        }
        return -1;
    }

    public int insert(String sqlId, Object obj) {
        try {
            return sqlSession.insert(sqlId, obj);
        } catch (Exception e) {
            logger.error("save  error", e);
        }
        return -1;
    }

    public int update(String sqlId, Object obj) {
        return sqlSession.update(sqlId, obj);
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

}
