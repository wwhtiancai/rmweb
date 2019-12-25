package com.tmri.framework.bean;

public class QSchedulerMetaData {
	private String runningSince = "";
	private int numJobsExecuted;
	private String zt = "";// µ÷¶ÈÆ÷×´Ì¬

	public String getRunningSince() {
		return runningSince;
	}

	public void setRunningSince(String runningSince) {
		this.runningSince = runningSince;
	}

	public int getNumJobsExecuted() {
		return numJobsExecuted;
	}

	public void setNumJobsExecuted(int numJobsExecuted) {
		this.numJobsExecuted = numJobsExecuted;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

}
