package com.tmri.rfid.mapper;


import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.EriScrapApp;


/*
 *wuweihong
 *2015-11-11
 */
@Repository
public interface EriScrapAppMapper extends BaseMapper<EriScrapApp>{
	EriScrapApp queryByBfdh(String bfdh) throws Exception;
}
