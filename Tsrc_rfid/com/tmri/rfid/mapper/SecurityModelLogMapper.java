package com.tmri.rfid.mapper;

import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.SecurityModelLog;
/*
 *wuweihong
 *2015-11-4
 */
@Repository
public interface SecurityModelLogMapper extends BaseMapper<SecurityModelLog>{
	int generateSequence();
}
