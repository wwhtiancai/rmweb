package com.tmri.rfid.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.CorpsWarehouse;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.bean.WarehouseEntry;

/**
 * Created by st on 2015/9/28.
 */
public interface WarehouseService {

    int saveWarehouse(WarehouseEntry warehouse) throws Exception;
    
    int saveWarehouseDetail(WarehouseDetail warehouseDetail) throws Exception;

    WarehouseEntry fetchByRkdh(String rkdh) throws Exception;

	PageList<WarehouseEntry> queryList(int pageIndex, int pageSize,Map<String,Object> condition) throws Exception;

	WarehouseDetail fetchByDetailId(WarehouseDetail warehouseDetail)
			throws Exception;

	int delDetailList(String bzhh) throws Exception;

	int delWarehouse(String rkdh) throws Exception;

	List<WarehouseDetail> queryDetailListByRkdh(String rkdh) throws Exception;

	String getRkdh(String ssbm) throws Exception;

	String saveCorpsWarehouse(WarehouseEntry warehouse, String detailList)
			throws Exception;

	void importWarehouse(File f, String bz) throws Exception;
	
}
