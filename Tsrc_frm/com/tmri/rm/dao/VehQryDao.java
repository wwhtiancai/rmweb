package com.tmri.rm.dao;

import java.util.List;

import com.tmri.rm.bean.Vehicle;

public interface VehQryDao {
	int getStreamMachineCount(String requestIp) throws Exception;
	public List<Vehicle> getVehList() throws Exception ;
	public void updateVeh(Vehicle vehicle) throws Exception;
}
