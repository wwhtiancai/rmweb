package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.WarehouseDetail;
import org.springframework.stereotype.Repository;

/**
 * Created by st on 2015/9/28.
 */
@Repository
public interface WarehouseDetailMapper extends BaseMapper<WarehouseDetail>{

	WarehouseDetail fetchByDetailId(WarehouseDetail warehouseDetail);
	
	List<WarehouseDetail> queryList(Map<String, Object> map);

	int deleteByBzhh(String bzhh);

	void deleteByRkdh(String rkdh);

	List<WarehouseDetail> queryListByRkdh(Map<String, Object> map);
	
}
