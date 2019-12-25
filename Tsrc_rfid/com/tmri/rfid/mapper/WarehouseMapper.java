package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.WarehouseEntry;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by st on 2015/9/28.
 */
@Repository
public interface WarehouseMapper extends BaseMapper<WarehouseEntry>{

	WarehouseEntry fetchByRkdh(String rkdh);
    
    List<WarehouseEntry> queryList(Map<String, Object> map);

    int deleteByRkdh(String rkdh);
    
    String getMaxRkdh(@Param("rkdh") String rkdh);
}
