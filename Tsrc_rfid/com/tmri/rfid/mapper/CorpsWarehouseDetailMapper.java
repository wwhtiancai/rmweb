package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.CorpsWarehouseDetail;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author stone
 * @date 2016-3-4 обнГ3:01:57
 */
@Repository
public interface CorpsWarehouseDetailMapper extends BaseMapper<CorpsWarehouseDetail>{

	CorpsWarehouseDetail fetchByDetailId(CorpsWarehouseDetail warehouseDetail);
	
	List<CorpsWarehouseDetail> queryList(Map<String, Object> map);

	int deleteByBzhh(String bzhh);

	void deleteByRkdh(String rkdh);

	List<CorpsWarehouseDetail> queryListByRkdh(Map<String, Object> map);
}
