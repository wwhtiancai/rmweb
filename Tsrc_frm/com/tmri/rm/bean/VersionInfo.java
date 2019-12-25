package com.tmri.rm.bean;

public class VersionInfo {
	private String versionKey;
	private String version;
	private String lastVersion;
	private String status;
	
	public String getVersionKey() {
		return versionKey;
	}
	public void setVersionKey(String versionKey) {
		this.versionKey = versionKey;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLastVersion() {
		return lastVersion;
	}
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
