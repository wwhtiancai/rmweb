package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/25.
 */
public class ProductApply {

    private String lydh;    //领用单号
    private String lyr;     //领用人
    private String lybm;    //领用部门
    private String glbm;    //管理部门
    private Date lyrq;      //领用日期
    private String bz;      //备注

    private String lybmmc;  //领用部门名称
    private String glbmmc;  //管理部门名称
    
    private String jbr; //经办人

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
