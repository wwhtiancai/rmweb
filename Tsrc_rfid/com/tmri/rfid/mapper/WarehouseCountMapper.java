package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.WarehouseCount;

import org.springframework.stereotype.Repository;


/**
 * 
 * @author stone
 * @date 2016-5-18 обнГ3:44:21
 */
@Repository
public interface WarehouseCountMapper extends BaseMapper<WarehouseCount> {

    List<WarehouseCount> queryList(Map<String, Object> map);

	WarehouseCount fetchByXH(String xh);

	int queryCount(Map<String, Object> condition);
    
}
