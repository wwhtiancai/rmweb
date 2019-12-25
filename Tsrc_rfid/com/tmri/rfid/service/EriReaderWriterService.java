package com.tmri.rfid.service;

import com.tmri.rfid.bean.EriReaderWriter;
import com.tmri.rfid.bean.EriReaderWriterActivation;
import com.tmri.rfid.common.EriReaderWriterRegisterStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by st on 2015/12/3.
 */
public interface EriReaderWriterService {
	
	EriReaderWriterActivation activate (String buffer,String caPubkeyIndex) throws Exception ;

	void activateResult(String dxqxh, int zt, int sqzt, String sbyy) throws Exception;

	EriReaderWriter fetchByXh(String dxqxh) throws Exception;

	boolean update(EriReaderWriter eriReaderWriter) throws Exception;

	List<EriReaderWriter> fetchByCondition(Map condition) throws Exception;
}
