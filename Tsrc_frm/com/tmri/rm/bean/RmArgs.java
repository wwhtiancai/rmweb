package com.tmri.rm.bean;

public class RmArgs {
	private String gjz;
	private String csz;
	
	public RmArgs(){
		
	}
	
	public RmArgs(String gjz, String csz){
		this.gjz = gjz;
		this.csz = csz;
	}
	
	public String getGjz() {
		return gjz;
	}

	public void setGjz(String gjz) {
		this.gjz = gjz;
	}

	public String getCsz() {
		return csz;
	}

	public void setCsz(String csz) {
		this.csz = csz;
	}

}
