package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.ExWarehouseEntry;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by st on 2015/9/28.
 */
@Repository
public interface ExWarehouseMapper extends BaseMapper<ExWarehouseEntry>{

	ExWarehouseEntry fetchByCkdh(String ckdh);
    
    List<ExWarehouseEntry> queryList(Map<String, Object> map);

    int deleteByCkdh(String ckdh);

    String getMaxCkdh(@Param("ckdh") String ckdh);

	void examine(@Param("ckdh") String ckdh,@Param("zt") String zt);

	int queryCount(Map<String, Object> condition);
}
