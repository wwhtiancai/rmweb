package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.RmVersion;

public interface JcbkManager {

	public List<RmVersion> getJcbkSfList(String yxms) throws Exception;
	
}
