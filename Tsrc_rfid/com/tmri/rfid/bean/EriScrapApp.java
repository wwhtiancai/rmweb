package com.tmri.rfid.bean;

import java.util.Date;


/*
 *wuweihong
 *2015-11-11
 */
public class EriScrapApp  {
	private String xh;
    private String bfdh; //���ϵ���
    private String bfyy; //����ԭ��
    private int zt; //״̬��1-�ύ��2-��ɣ�3-ȡ��
    private Date qqrq; //��������
    private Date wcrq; //�������
    private String jbr; //������
    private String czr;    //������
    private String bz;    //��ע
    private String scr;    //�����
    private Date shrq;    //�������
    private String shbz;    //��˱�ע
	public String getBfdh() {
		return bfdh;
	}
	public void setBfdh(String bfdh) {
		this.bfdh = bfdh;
	}
	public String getBfyy() {
		return bfyy;
	}
	public void setBfyy(String bfyy) {
		this.bfyy = bfyy;
	}
	public int getZt() {
		return zt;
	}
	public void setZt(int zt) {
		this.zt = zt;
	}
	public Date getQqrq() {
		return qqrq;
	}
	public void setQqrq(Date qqrq) {
		this.qqrq = qqrq;
	}
	public Date getWcrq() {
		return wcrq;
	}
	public void setWcrq(Date wcrq) {
		this.wcrq = wcrq;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Date getShrq() {
		return shrq;
	}
	public void setShrq(Date shrq) {
		this.shrq = shrq;
	}
	public String getShbz() {
		return shbz;
	}
	public void setShbz(String shbz) {
		this.shbz = shbz;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getScr() {
		return scr;
	}

	public void setScr(String scr) {
		this.scr = scr;
	}
}
