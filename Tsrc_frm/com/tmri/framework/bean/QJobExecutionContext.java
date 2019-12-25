package com.tmri.framework.bean;

public class QJobExecutionContext {
	 private String rwid;
     private String jobRunTime;
     private String fireTime;
     private String name;
     private String jobResult; //task executed result
     private String jobFullResult; 
     
     
	public String getRwid() {
		return rwid;
	}
	public void setRwid(String rwid) {
		this.rwid = rwid;
	}
	public String getJobRunTime() {
		return jobRunTime;
	}
	public void setJobRunTime(String jobRunTime) {
		this.jobRunTime = jobRunTime;
	}
	public String getFireTime() {
		return fireTime;
	}
	public void setFireTime(String fireTime) {
		this.fireTime = fireTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobResult() {
		return jobResult;
	}
	public void setJobResult(String jobResult) {
		this.jobResult = jobResult;
	}
	public String getJobFullResult() {
		return jobFullResult;
	}
	public void setJobFullResult(String jobFullResult) {
		this.jobFullResult = jobFullResult;
	}

     
     
     
}
