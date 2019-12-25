package com.tmri.rm.bean;

import java.util.List;

public class AzdVersion {
	private String ip;
	private List<VersionInfo> versionInfoList;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<VersionInfo> getVersionInfoList() {
		return versionInfoList;
	}
	public void setVersionInfoList(List<VersionInfo> versionInfoList) {
		this.versionInfoList = versionInfoList;
	}
}
