package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/25.
 */
public class ProductApply {

    private String lydh;    //���õ���
    private String lyr;     //������
    private String lybm;    //���ò���
    private String glbm;    //������
    private Date lyrq;      //��������
    private String bz;      //��ע

    private String lybmmc;  //���ò�������
    private String glbmmc;  //����������
    
    private String jbr; //������

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public String getLyr() {
        return lyr;
    }

    public void setLyr(String lyr) {
        this.lyr = lyr;
    }

    public String getLybm() {
        return lybm;
    }

    public void setLybm(String lybm) {
        this.lybm = lybm;
    }

    public String getGlbm() {
        return glbm;
    }

    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }

    public Date getLyrq() {
        return lyrq;
    }

    public void setLyrq(Date lyrq) {
        this.lyrq = lyrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getLybmmc() {
        return lybmmc;
    }

    public void setLybmmc(String lybmmc) {
        this.lybmmc = lybmmc;
    }

    public String getGlbmmc() {
        return glbmmc;
    }

    public void setGlbmmc(String glbmmc) {
        this.glbmmc = glbmmc;
    }

	public String getJbr() {
		return jbr;
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
    
}
