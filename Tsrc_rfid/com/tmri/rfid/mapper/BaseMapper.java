package com.tmri.rfid.mapper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/15.
 */
@Repository
public interface BaseMapper<T> {

    T queryById(Object id);

    List<T> queryByCondition(Map condition);

    List<T> queryByCondition(Map condition, PageBounds pageBounds);

    List<T> queryAll();

    int create(T t);

    int update(T t);

    int updateByCondition(Map condition);

    int delete(T t);

    int deleteById(Object id);

    int count(Map condition);

    T querySingle(Map condition);

}
