package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2016-2-15
 */
public class EriEquipmentBean {
	private long xh; //���
    private String sbh; //�豸��
	private String aqmkxh; //��ȫģ�����
    private String gy; //��Կ
    private String glbm; //������
    private String bz; //��ע
    private int zt; //״̬ 0-δ���ã�1-���� 2- ����
    private String byzd; //�����ֶ�
    private Date gxrq; //��������

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
