package com.tmri.rfid.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tmri.rfid.bean.EriOperation;
import com.tmri.rfid.mapper.EriOperationMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.EriOperationService;

/**
 * 
 * @author stone
 * @date 2016-3-16 ÉÏÎç11:26:06
 */
@Service
public class EriOperationServiceImpl extends BaseServiceImpl implements EriOperationService {
	
    private final static Logger LOG = LoggerFactory.getLogger(EriOperationServiceImpl.class);

    @Resource
    private EriOperationMapper eriOperationMapper;
    
	@Override
	public List<EriOperation> getCustomizeRecordByTid(String tid) throws Exception {
		return eriOperationMapper.getCustomizeRecordByTid(tid);
	}

	@Override
	public List<EriOperation> queryRkByTid(String tid) throws Exception {
		// TODO Auto-generated method stub
		return eriOperationMapper.queryRkByTid(tid);
	}

	@Override
	public List<EriOperation> queryCkByTid(String tid) throws Exception {
		// TODO Auto-generated method stub
		return eriOperationMapper.queryCkByTid(tid);
	}

	@Override
	public List<EriOperation> queryZdrkByTid(String tid) throws Exception {
		// TODO Auto-generated method stub
		return eriOperationMapper.queryZdrkByTid(tid);
	}

	@Override
	public List<EriOperation> queryLyByTid(String tid) throws Exception {
		// TODO Auto-generated method stub
		return eriOperationMapper.queryLyByTid(tid);
	}

	@Override
	public List<EriOperation> queryBfByTid(String tid) throws Exception {
		return eriOperationMapper.queryBfByTid(tid);
	}

}
