package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.Vehicle;

public interface VehQryService {

	boolean isValidRequest(String requestIp) throws Exception;
	
	public List<Vehicle> getVehList() throws Exception ;
	
	public void updateVeh(Vehicle vehicle) throws Exception;

}
