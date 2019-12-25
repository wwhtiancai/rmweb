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

	private Long xh; // ��� ͨ��SEQ_RFID_DEVICE����
	private String sbmc; // �豸����
	private String dz; // ��ַ
	private Long zt; // ״̬(0-δ������1-��Ч��2-ʧЧ)
	private String ip; // IP��ַ
	private String mac; // MAC��ַ
	private Date cjrq; // ��������
	private Date sxrq; // ʧЧ����

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
