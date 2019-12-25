package com.tmri.framework.bean;
/*
 * 任务描述类，表现运行时任务数据结构
 */
public class QTask {
	private String xtlb;
	private String rwid;
	private String rwmc;
	private String nextFireTime;
	private String previousFireTime;
	private String qtip;
	private String yxipdz;
	private int refireCount;
	private String zt;
	private String rwzt;  //任务定义状态
    private String sczxjgms;//上次执行结果描述
    private String jobResult; //task executed result
    private String jobFullResult; 
    
   

	public String getJobFullResult() {
		return jobFullResult;
	}

	public void setJobFullResult(String jobFullResult) {
		this.jobFullResult = jobFullResult;
	}

	public String getJobResult() {
		return jobResult;
	}

	public void setJobResult(String jobResult) {
		this.jobResult = jobResult;
	}

	public String getRwzt() {
		return rwzt;
	}

	public void setRwzt(String rwzt) {
		this.rwzt = rwzt;
	}

	public String getSczxjgms() {
		return sczxjgms;
	}

	public void setSczxjgms(String sczxjgms) {
		this.sczxjgms = sczxjgms;
	}

	public String getRwid() {
		return rwid;
	}

	public void setRwid(String rwid) {
		this.rwid = rwid;
	}

	public String getRwmc() {
		return rwmc;
	}

	public void setRwmc(String rwmc) {
		this.rwmc = rwmc;
	}

	public String getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getPreviousFireTime() {
		return previousFireTime;
	}

	public void setPreviousFireTime(String previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	public int getRefireCount() {
		return refireCount;
	}

	public void setRefireCount(int refireCount) {
		this.refireCount = refireCount;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getXtlb(){
		return xtlb;
	}

	public void setXtlb(String xtlb){
		this.xtlb=xtlb;
	}

	public String getQtip(){
		return qtip;
	}

	public void setQtip(String qtip){
		this.qtip=qtip;
	}

	public String getYxipdz(){
		return yxipdz;
	}

	public void setYxipdz(String yxipdz){
		this.yxipdz=yxipdz;
	}

}
