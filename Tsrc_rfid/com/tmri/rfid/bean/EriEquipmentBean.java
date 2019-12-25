package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2016-2-15
 */
public class EriEquipmentBean {
	private long xh; //序号
    private String sbh; //设备号
	private String aqmkxh; //安全模块序号
    private String gy; //公钥
    private String glbm; //管理部门
    private String bz; //备注
    private int zt; //状态 0-未启用，1-启用 2- 禁用
    private String byzd; //备用字段
    private Date gxrq; //更新日期

	public long getXh() {
		return xh;
	}

	public void setXh(long xh) {
		this.xh = xh;
	}

	public String getSbh() {
		return sbh;
	}
	public void setSbh(String sbh) {
		this.sbh = sbh;
	}
	public String getGy() {
		return gy;
	}
	public void setGy(String gy) {
		this.gy = gy;
	}
	public String getGlbm() {
		return glbm;
	}
	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getZt() {
		return zt;
	}
	public void setZt(int zt) {
		this.zt = zt;
	}
	public String getByzd() {
		return byzd;
	}
	public void setByzd(String byzd) {
		this.byzd = byzd;
	}
	public Date getGxrq() {
		return gxrq;
	}
	public void setGxrq(Date gxrq) {
		this.gxrq = gxrq;
	}

	public String getAqmkxh() {
		return aqmkxh;
	}

	public void setAqmkxh(String aqmkxh) {
		this.aqmkxh = aqmkxh;
	}
}
