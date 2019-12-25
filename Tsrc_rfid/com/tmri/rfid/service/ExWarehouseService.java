package com.tmri.rfid.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.ExWarehouseDetail;
import com.tmri.rfid.bean.ExWarehouseEntry;

/**
 * Created by st on 2015/9/28.
 */
public interface ExWarehouseService {

    String saveExWarehouse(ExWarehouseEntry exWarehouse,String detailList) throws Exception;
    
    int saveExWarehouseDetail(ExWarehouseDetail exWarehouseDetail) throws Exception;

    ExWarehouseEntry fetchByCkdh(String ckdh) throws Exception;

	PageList<ExWarehouseEntry> queryList(int pageIndex, int pageSize, Map<String,Object> map) throws Exception;

	ExWarehouseDetail fetchByDetailId(int dw, String detailId, String ckdh)
			throws Exception;

	int delDetailList(String bzhh) throws Exception;

	int delExWarehouse(String ckdh) throws Exception;

	List<ExWarehouseDetail> queryDetailListByCkdm(String ckdh) throws Exception;

	String getCkdh(String ssbm) throws Exception;

	void examine(String ckdh, int zt) throws Exception;

	void exportExwarehouse(String ckdh) throws Exception;

	int queryCount(Map<String, Object> condition) throws Exception;
	
}
