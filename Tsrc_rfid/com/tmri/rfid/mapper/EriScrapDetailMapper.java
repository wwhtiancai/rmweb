package com.tmri.rfid.mapper;

import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.EriScrapDetail;

/*
 *wuweihong
 *2015-11-12
 */
@Repository
public interface EriScrapDetailMapper extends BaseMapper<EriScrapDetail> {
	int deleteByBfdh(String bfdh) throws Exception;
}
