package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/10/16.
 */
public class ProductionTask {

    private String rwh;
    private String rwdm;
    private int zt;
    private Date jhrq;
    private String jbr;
    private String bz;
    private Date ksrq;
    private Date wcrq;
    private String czr;
    private String czrxm;

    public String getRwh() {

        return rwh;
    }

    public void setRwh(String rwh) {
        this.rwh = rwh;
    }

    public String getRwdm() {
        return rwdm;
    }

    public void setRwdm(String rwdm) {
        this.rwdm = rwdm;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public Date getJhrq() {
        return jhrq;
    }

    public void setJhrq(Date jhrq) {
        this.jhrq = jhrq;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getKsrq() {
        return ksrq;
    }

    public void setKsrq(Date ksrq) {
        this.ksrq = ksrq;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getCzrxm() {
        return czrxm;
    }

    public void setCzrxm(String czrxm) {
        this.czrxm = czrxm;
    }
}
