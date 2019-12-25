package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.EriEquipmentBean;
import com.tmri.rfid.common.EriReaderWriterRegisterStatus;

import java.util.List;
import java.util.Map;

/*
 *wuweihong
 *2016-2-15
 */
public interface EriEquipmentService {
	PageList<EriEquipmentBean> queryList(int pageIndex, int pageSize,Long xh,
			String sbh, String glbm) throws Exception;
	int updateEquipmentState(long zt) throws Exception; 
	EriEquipmentBean queryById(Long xh);
	boolean update(EriEquipmentBean eriEquipmentBean) throws Exception;
	boolean create(EriEquipmentBean eriEquipmentBean);
	int delete(String xh);
	EriEquipmentBean fetchByAqmkxhAndGlbm(String aqmkxh, String glbm);
	EriEquipmentBean fetchByAqmkxh(String aqmkxh);
	EriEquipmentBean register(String aqmkxh, String glbm) throws Exception;
	EriReaderWriterRegisterStatus checkRegisterStatus(String aqmkxh) throws Exception;
	List<EriEquipmentBean> fetchByCondition(Map condition);
}
