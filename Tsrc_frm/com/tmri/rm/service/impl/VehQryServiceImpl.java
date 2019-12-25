package com.tmri.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rm.bean.Vehicle;
import com.tmri.rm.dao.VehQryDao;
import com.tmri.rm.service.VehQryService;

@Service
public class VehQryServiceImpl implements VehQryService{
	@Autowired
	private VehQryDao vehQryDao;

	public boolean isValidRequest(String requestIp) throws Exception {
		return this.vehQryDao.getStreamMachineCount(requestIp) > 0;
	}
	
	
	public List<Vehicle> getVehList() throws Exception {
		return this.vehQryDao.getVehList();
	}

	public void updateVeh(Vehicle vehicle) throws Exception {
		this.vehQryDao.updateVeh(vehicle);
	}
}
