package com.tmri.rfid.service;

import java.util.List;

import com.tmri.rfid.bean.EriOperation;

/**
 * 
 * @author stone
 * @date 2016-3-16 ионГ11:09:01
 */
public interface EriOperationService {
	
	List<EriOperation> getCustomizeRecordByTid(String tid) throws Exception;

	List<EriOperation> queryRkByTid(String tid) throws Exception;

	List<EriOperation> queryCkByTid(String tid) throws Exception;

	List<EriOperation> queryZdrkByTid(String tid) throws Exception;

	List<EriOperation> queryLyByTid(String tid) throws Exception;

	List<EriOperation> queryBfByTid(String tid) throws Exception;

}
