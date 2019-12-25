package com.tmri.rfid.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public class BaseServiceImpl implements Serializable {

    private static final long serialVersionUID = 1293567786956029903L;
    
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected SqlSessionFactoryBean sqlSessionFactory;

    /**
     * 查询分页数据
     * 
     * @param mapperClass
     * @param sqlId
     * @param sqlParameter
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    protected List<?> getPageList(Class<?> mapperClass, String sqlId,
            Object sqlParameter, int pageIndex, int pageSize) throws Exception {
        SqlSession session = null;
        try {
            SqlSessionFactory sessionFactory = sqlSessionFactory.getObject();
            session = SqlSessionUtils.getSqlSession(sessionFactory);
            if (pageIndex <= 0) {
                pageIndex = 1;
            }
            if (pageSize <= 0) {
                pageSize = 10;
            }
            PageBounds pageBounds = new PageBounds(pageIndex, pageSize);
            return session.selectList(mapperClass.getName() + "." + sqlId,
                    sqlParameter, pageBounds);
        } finally {
            session.close();
        }

    }

}
