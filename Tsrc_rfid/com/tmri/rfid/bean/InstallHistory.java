package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2017/4/25.
 */
public class InstallHistory {

    private Long xh;
    private String tid;
    private String kh;
    private String hphm;
    private String hpzl;
    private Date azrq;
    private String azr;

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
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

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public Date getAzrq() {
        return azrq;
    }

    public void setAzrq(Date azrq) {
        this.azrq = azrq;
    }

    public String getAzr() {
        return azr;
    }

    public void setAzr(String azr) {
        this.azr = azr;
    }
}
