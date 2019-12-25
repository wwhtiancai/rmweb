package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.DeviceStation;


/*
 *wuweihong
 *2015-10-27
 */
@Repository
public interface DeviceStationMapper extends BaseMapper<DeviceStation>{
	int deleteBySbxh(Long sbxh);
}
