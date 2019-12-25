package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/21.
 */
public class EriInitializeRecord {

    private Long id;
    private String tid;
    private int zt;
    private Date cshrq;
    private String cshczr;
    private Long sbh;
    private Long gwh;
    private String kh;
    private String sbyy;
    private String sf;
    private String ph;
    private int klx;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public Date getCshrq() {
        return cshrq;
    }

    public void setCshrq(Date cshrq) {
        this.cshrq = cshrq;
    }

    public String getCshczr() {
        return cshczr;
    }

    public void setCshczr(String cshczr) {
        this.cshczr = cshczr;
    }

    public Long getSbh() {
        return sbh;
    }

    public void setSbh(Long sbh) {
        this.sbh = sbh;
    }

    public Long getGwh() {
        return gwh;
    }

    public void setGwh(Long gwh) {
        this.gwh = gwh;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getSbyy() {
        return sbyy;
    }

    public void setSbyy(String sbyy) {
        this.sbyy = sbyy;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public int getKlx() {
        return klx;
    }

    public void setKlx(int klx) {
        this.klx = klx;
    }
}
