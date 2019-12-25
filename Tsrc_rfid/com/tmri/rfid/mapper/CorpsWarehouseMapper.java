package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.CorpsWarehouse;
import com.tmri.rfid.bean.WarehouseEntry;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author stone
 * @date 2016-3-4 ионГ11:56:00
 */
@Repository
public interface CorpsWarehouseMapper extends BaseMapper<CorpsWarehouse>{

	CorpsWarehouse fetchByRkdh(String rkdh);
    
    List<CorpsWarehouse> queryList(Map<String, Object> map);

    int deleteByRkdh(String rkdh);
    
    String getMaxRkdh(@Param("rkdh") String rkdh);
}
