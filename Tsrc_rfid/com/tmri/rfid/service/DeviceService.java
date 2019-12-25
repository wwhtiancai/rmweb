package com.tmri.rfid.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Device;
import com.tmri.rfid.bean.DeviceStation;
import com.tmri.rfid.bean.Inventory;


/*
 *wuweihong
 *2015-10-22
 */
public interface DeviceService {
	Device fetchDeviceByXh(Long xh)throws Exception;
	PageList<Device> queryList(int pageIndex, int pageSize,String xh,
			String smbc, String dz) throws Exception;
	
	DeviceStation fetchDeviceStationByXh(Long xh)throws Exception;
	List<DeviceStation> queryList(Long sbxh) throws Exception;
	
	int deleteByXh(Long xh)throws Exception;
	
	int saveDevice(Device device) throws Exception;
	
	int saveDeviceStation(DeviceStation deviceStation) throws Exception;
	
	int deleteDeviceStation(Long sbxh) throws Exception;
	
	int deleteDevice(Long xh) throws Exception;

	int updateDevice(Device device) throws Exception;
	
    Device fetchByMac(String mac) throws Exception;
}
