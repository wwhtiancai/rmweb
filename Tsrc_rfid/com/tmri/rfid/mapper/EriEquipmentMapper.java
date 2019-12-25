package com.tmri.rfid.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.EriEquipmentBean;


/*
 *wuweihong
 *2016-2-15
 */
@Repository
public interface EriEquipmentMapper extends BaseMapper<EriEquipmentBean>{
	int updateState(long zt);
	int getCidSequence();
}
