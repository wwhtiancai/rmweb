package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016/2/29.
 */
public class EriReaderWriterActivation {

    private Long xh;
    private String dxqxh;
    private String ydxqxh;
    private String aqmkxh;
    private Date czrq;
    private Date wcrq;
    private int zt;
    private int sqzt;
    private String yhxx;
    private String czr;
    private String sbyy;

    private String activateResponse;

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getYdxqxh() {
        return ydxqxh;
    }

    public void setYdxqxh(String ydxqxh) {
        this.ydxqxh = ydxqxh;
    }

    public String getDxqxh() {
        return dxqxh;
    }

    public void setDxqxh(String dxqxh) {
        this.dxqxh = dxqxh;
    }

    public String getAqmkxh() {
        return aqmkxh;
    }

    public void setAqmkxh(String aqmkxh) {
        this.aqmkxh = aqmkxh;
    }

    public Date getCzrq() {
        return czrq;
    }

    public void setCzrq(Date czrq) {
        this.czrq = czrq;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public int getSqzt() {
        return sqzt;
    }

    public void setSqzt(int sqzt) {
        this.sqzt = sqzt;
    }

    public String getYhxx() {
        return yhxx;
    }

    public void setYhxx(String yhxx) {
        this.yhxx = yhxx;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getActivateResponse() {
        return activateResponse;
    }

    public void setActivateResponse(String activateResponse) {
        this.activateResponse = activateResponse;
    }

    public String getSbyy() {
        return sbyy;
    }

    public void setSbyy(String sbyy) {
        this.sbyy = sbyy;
    }
}
