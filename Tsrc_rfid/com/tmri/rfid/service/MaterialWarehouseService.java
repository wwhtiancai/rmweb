package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.MaterialInventory;
import com.tmri.rfid.bean.MaterialWarehouse;
import com.tmri.rfid.bean.PackageCase;
import com.tmri.rfid.bean.WarehouseCount;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by st on 2015/11/6.
 */
public interface MaterialWarehouseService {

    void create(MaterialWarehouse materialWarehouse) throws Exception;

    /**
     * 获取入库单号
     * @param
     * @return
     * @throws Exception
     */
    String generateOrderNumber() throws Exception;

	PageList<MaterialWarehouse> queryList(int pageIndex, int pageSize, Map condition)
			throws Exception;

	MaterialWarehouse fetchByRKDH(String rkdh) throws Exception;

	void update(MaterialWarehouse materialWarehouse) throws Exception;

	void delete(String rkdh) throws Exception;

	void insertBatchEri(List<MaterialEri> materialEris) throws Exception;

	Map<String, Object> getMaterialEris(File file,String rkdh) throws Exception;

	void insertBatchInventory(List<MaterialInventory> materialInventorys) throws Exception;

	Map getMaterialEris(File f) throws Exception;

	List<PackageCase> getPackageCases(List<MaterialEri> materialEris);

	List<MaterialInventory> getMaterialInventorys(List<MaterialEri> list,String rkdh);

	List<PackageCase> queryWarehouseDetails(String rkdh);

	PageList<WarehouseCount> queryCountList(int pageIndex, int pageSize,
			Map condition) throws Exception;

	void createCount(WarehouseCount warehouseCount) throws Exception;

	WarehouseCount fetchByXH(String xh) throws Exception;

	int queryCount(Map<String, Object> condition) throws Exception;
    
}
