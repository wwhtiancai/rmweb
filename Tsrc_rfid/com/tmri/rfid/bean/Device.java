package com.tmri.rfid.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

/*
 *wuweihong
 *2015-10-22
 */
public class Device {

	private Long xh; // 序号 通过SEQ_RFID_DEVICE生成
	private String sbmc; // 设备名称
	private String dz; // 地址
	private Long zt; // 状态(0-未就绪，1-生效，2-失效)
	private String ip; // IP地址
	private String mac; // MAC地址
	private Date cjrq; // 创建日期
	private Date sxrq; // 失效日期

	public Long getXh() {
		return xh;
	}

	public void setXh(Long xh) {
		this.xh = xh;
	}

	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public long getZt() {
		return zt;
	}

	public void setZt(long zt) {
		this.zt = zt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Date getCjrq() {
		return cjrq;
	}

	public void setCjrq(Date cjrq) {
		this.cjrq = cjrq;
	}

	public Date getSxrq() {
		return sxrq;
	}

	public void setSxrq(Date sxrq) {
		this.sxrq = sxrq;
	}


}
