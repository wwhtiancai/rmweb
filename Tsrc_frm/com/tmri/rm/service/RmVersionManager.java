package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.RmLastVersion;
import com.tmri.rm.bean.RmPkgVersion;
import com.tmri.rm.bean.RmVersion;
import com.tmri.rm.bean.RmWebVersion;

/**
@author yangzm
@data: 2013-7-1  time: ÏÂÎç07:39:17

 */
public interface RmVersionManager {
	
	public List<RmVersion> getAzdmIpGjzList(RmVersion bean) throws Exception;	
	
	public RmVersion getRmVersion(String azdm,String ip,String gjz) throws Exception;
	
	public List<RmLastVersion> getLastVersionList() throws Exception;	
	
	public RmLastVersion getLastVersion(String gjz) throws Exception;
	
	public List<RmVersion> getSfList() throws Exception;

	public List<RmVersion> getDsList(String sfdm) throws Exception;

	public List<RmWebVersion> getWebList(String fzjg) throws Exception;

	public List<RmPkgVersion> getPkgList(String fzjg) throws Exception;
}
