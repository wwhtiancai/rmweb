package com.tmri.rfid.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmri.rfid.util.MapUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Device;
import com.tmri.rfid.bean.DeviceStation;
import com.tmri.rfid.mapper.DeviceMapper;
import com.tmri.rfid.mapper.DeviceStationMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.DeviceService;

/*
 *wuweihong
 *2015-10-22
 */
@Service
public class DeviceServiceImpl extends BaseServiceImpl implements DeviceService{

	@Autowired
    private DeviceMapper deviceMapper;
	
	@Autowired
    private DeviceStationMapper deviceStationMapper;
	
	@Override
	public PageList<Device> queryList(int pageIndex, int pageSize, String xh,
			String smbc, String dz) throws Exception {
//		deviceMapper.fetchDeviceByXh(xh);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("xh", xh);
		map.put("smbc", smbc);
		map.put("dz", dz);
		return (PageList<Device>) getPageList(DeviceMapper.class, "queryByCondition",
	    		map, pageIndex, pageSize);
	}

	@Override
	public Device fetchDeviceByXh(Long xh) {
		// TODO Auto-generated method stub
		return deviceMapper.queryById(xh);
	}

	@Override
	public DeviceStation fetchDeviceStationByXh(Long xh) {
		// TODO Auto-generated method stub
		return deviceStationMapper.queryById(xh);
	}

	@Override
	public List<DeviceStation> queryList(Long sbxh) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sbxh", sbxh);
		return deviceStationMapper.queryByCondition(map);
	}

	@Override
	public int deleteByXh(Long xh) throws Exception {
		// TODO Auto-generated method stub
		return deviceStationMapper.deleteById(xh);
	}

	@Override
	public int saveDevice(Device device) throws Exception {
		// TODO Auto-generated method stub
		return deviceMapper.create(device);
	}

	@Override
	public int saveDeviceStation(DeviceStation deviceStation) throws Exception {
		// TODO Auto-generated method stub
		return deviceStationMapper.create(deviceStation);
	}

	@Override
	public int deleteDeviceStation(Long sbxh)
			throws Exception {
		return deviceStationMapper.deleteBySbxh(sbxh);
	}

	@Override
	public int deleteDevice(Long xh) throws Exception {
		// TODO Auto-generated method stub
		return deviceMapper.deleteById(xh);
	}

    @Override
    public Device fetchByMac(String mac) throws Exception {
        List<Device> deviceList = deviceMapper.queryByCondition(MapUtilities.buildMap("mac", mac));
        if (deviceList == null || deviceList.isEmpty()) {
            return null;
        } else if (deviceList.size() > 1) {
            throw new Exception("当前MAC地址绑定了多台设备");
        } else {
            return deviceList.get(0);
        }
    }

	@Override
	public int updateDevice(Device device) throws Exception {
		// TODO Auto-generated method stub
		return deviceMapper.update(device);
	}

}
