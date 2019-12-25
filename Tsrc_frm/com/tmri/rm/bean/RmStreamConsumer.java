package com.tmri.rm.bean;

import java.util.List;

/**
 * 流式计算consumer定义配置表
 * 
 * @author shiyl 2014-11-20
 */
public class RmStreamConsumer {
	private String gpkey;

	private String gpmc;

	private String tpkey;

	private String sxl;

	private String csgs;

	private String rwgz;

	private String rwsl;

	private String yxjb;

	private String jyw;

	private String gxsj;

	private String bz;

	private String yxjbmc;

	private String rwgzmc;

	private String jzjyw;

	private String jobid;

	// 扩展属性
	private List<RmStreamStatusBean> statusList;
	// 扩展属性,运行状态,1运行中，0未运行
	private int yxzt;
	// 扩展属性，任务数量总数
	private long rwslzs; 
	// 扩展属性，处理量总数
	private long cllzs; 
	// 扩展属性，积压量总数
	private long jylzs;
	
	public RmStreamConsumer() {

	}

	public RmStreamConsumer(String className, String jobid) {
		this.sxl = className;
		this.jobid = jobid;
	}

	public String getGpkey() {
		return gpkey;
	}

	public void setGpkey(String gpkey) {
		this.gpkey = gpkey;
	}

	public String getGpmc() {
		return gpmc;
	}

	public void setGpmc(String gpmc) {
		this.gpmc = gpmc;
	}

	public String getTpkey() {
		return tpkey;
	}

	public void setTpkey(String tpkey) {
		this.tpkey = tpkey;
	}

	public String getSxl() {
		return sxl;
	}

	public void setSxl(String sxl) {
		this.sxl = sxl;
	}

	public String getCsgs() {
		return csgs;
	}

	public void setCsgs(String csgs) {
		this.csgs = csgs;
	}

	public String getRwgz() {
		return rwgz;
	}

	public void setRwgz(String rwgz) {
		this.rwgz = rwgz;
	}

	public String getRwsl() {
		return rwsl;
	}

	public void setRwsl(String rwsl) {
		this.rwsl = rwsl;
	}

	public String getYxjb() {
		return yxjb;
	}

	public void setYxjb(String yxjb) {
		this.yxjb = yxjb;
	}

	public String getJyw() {
		return jyw;
	}

	public void setJyw(String jyw) {
		this.jyw = jyw;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getYxjbmc() {
		return yxjbmc;
	}

	public void setYxjbmc(String yxjbmc) {
		this.yxjbmc = yxjbmc;
	}

	public String getRwgzmc() {
		return rwgzmc;
	}

	public void setRwgzmc(String rwgzmc) {
		this.rwgzmc = rwgzmc;
	}

	public String getJzjyw() {
		return jzjyw;
	}

	public void setJzjyw(String jzjyw) {
		this.jzjyw = jzjyw;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public List<RmStreamStatusBean> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RmStreamStatusBean> statusList) {
		this.statusList = statusList;
	}
	
	public int getStatusSize(){
		if (statusList == null){
			return 0;
		}
		return statusList.size();
	}

	public int getYxzt() {
		return yxzt;
	}

	public void setYxzt(int yxzt) {
		this.yxzt = yxzt;
	}
	
	public String getYxztmc(){
		if (yxzt == 0){
			return "未运行";
		}
		return "运行中";
	}

	public long getRwslzs() {
		return rwslzs;
	}

	public void setRwslzs(long rwslzs) {
		this.rwslzs = rwslzs;
	}

	public long getCllzs() {
		return cllzs;
	}

	public void setCllzs(long cllzs) {
		this.cllzs = cllzs;
	}

	public long getJylzs() {
		return jylzs;
	}

	public void setJylzs(long jylzs) {
		this.jylzs = jylzs;
	}
}
