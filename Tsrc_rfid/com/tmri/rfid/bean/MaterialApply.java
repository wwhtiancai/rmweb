package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by st on 2015/11/2.
 */
public class MaterialApply {

    private String dgdh;    //�������� ��Ʒ����+YYYYMMDD+3λ˳���
    private String cpdm;    //��Ʒ����
    private int sl;       //����
    private String jbr;     //������
    private Date dgrq;      //��������
    private int zt;         //״̬��1-�ύ��2-������3-ȡ��
    private String bz;      //��ע
    
    private int yrksl;   //���������
    
    private String cplb;    //��Ʒ���
    private String lbmc;    //�������
    private String cpmc;    //��Ʒ����
    private String jbrxm;    //����������
    private String bmmc;    //��������
    
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getLbmc() {
		return lbmc;
	}
	public void setLbmc(String lbmc) {
		this.lbmc = lbmc;
	}
	public String getCplb() {
		return cplb;
	}
	public void setCplb(String cplb) {
		this.cplb = cplb;
	}
	public String getCpmc() {
		return cpmc;
	}
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	
	public String getJbrxm() {
		return jbrxm;
	}
	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}
	public String getDgdh() {
		return dgdh;
	}
	public void setDgdh(String dgdh) {
		this.dgdh = dgdh;
	}
	public String getCpdm() {
		return cpdm;
	}
	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public Date getDgrq() {
		return dgrq;
	}
	public void setDgrq(Date dgrq) {
		this.dgrq = dgrq;
	}
	public int getZt() {
		return zt;
	}
	public void setZt(int zt) {
		this.zt = zt;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getYrksl() {
		return yrksl;
	}
	public void setYrksl(int yrksl) {
		this.yrksl = yrksl;
	}

    
}
