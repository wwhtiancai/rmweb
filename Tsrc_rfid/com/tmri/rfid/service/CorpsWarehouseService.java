package com.tmri.rfid.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.CorpsWarehouse;
import com.tmri.rfid.bean.CorpsWarehouseDetail;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.bean.WarehouseEntry;

/**
 * 
 * @author stone
 * @date 2016-3-4 ÏÂÎç2:47:43
 */
public interface CorpsWarehouseService {

    String saveWarehouse(CorpsWarehouse warehouse,String detailList) throws Exception;
    
    int saveWarehouseDetail(CorpsWarehouseDetail warehouseDetail) throws Exception;

    CorpsWarehouse fetchByRkdh(String rkdh) throws Exception;

	PageList<CorpsWarehouse> queryList(int pageIndex, int pageSize,Map<String,Object> condition) throws Exception;

	CorpsWarehouseDetail fetchByDetailId(CorpsWarehouseDetail warehouseDetail)
			throws Exception;

	int delDetailList(String bzhh) throws Exception;

	int delWarehouse(String rkdh) throws Exception;

	List<CorpsWarehouseDetail> queryDetailListByRkdh(String rkdh) throws Exception;

	String getRkdh(String ssbm) throws Exception;

    void importWarehouse(File f,String bz) throws Exception;
	
}
