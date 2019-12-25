package com.tmri.share.frm.bean;

public class FrmXzqhZhpt {
	private String xzqh;// VARCHAR2(10) N 行政区划
	private String qhmc;// VARCHAR2(128) Y 行政区划名称
	private String qhsm;// VARCHAR2(128) Y 行政区划说明
	private String gxsj;// DATE N sysdate 更新时间
	private String bz;// VARCHAR2(64) Y 备注
	public String getXzqh() {
		return xzqh;
	}
	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}
	public String getQhmc() {
		return qhmc;
	}
	public void setQhmc(String qhmc) {
		this.qhmc = qhmc;
	}
	public String getQhsm() {
		return qhsm;
	}
	public void setQhsm(String qhsm) {
		this.qhsm = qhsm;
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
	
	
}
