package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2015-11-11
 */
public class EriScrapDetail {
	private Integer xh; //��� 	
    private String bfdh; //���ϵ���
    private String tid; //������
    private String kh;    //����
    private Date gxrq; //�������
	private int zt;
	public Integer getXh() {
		return xh;
	}
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	public String getBfdh() {
		return bfdh;
	}
	public void setBfdh(String bfdh) {
		this.bfdh = bfdh;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getKh() {
		return kh;
	}
	public void setKh(String kh) {
		this.kh = kh;
	}
	public Date getGxrq() {
		return gxrq;
	}
	public void setGxrq(Date gxrq) {
		this.gxrq = gxrq;
	}

	public int getZt() {
		return zt;
	}

	public void setZt(int zt) {
		this.zt = zt;
	}
}
