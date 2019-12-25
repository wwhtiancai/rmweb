package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016-03-24.
 */
public class OperationLog {

    private long xh;
    private String czmc;
    private String gjz;
    private String xxnr;
    private int jg;
    private String czr;
    private Date cjsj;

    public long getXh() {
        return xh;
    }

    public void setXh(long xh) {
        this.xh = xh;
    }

    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc;
    }

    public String getGjz() {
        return gjz;
    }

    public void setGjz(String gjz) {
        this.gjz = gjz;
    }

    public String getXxnr() {
        return xxnr;
    }

    public void setXxnr(String xxnr) {
        this.xxnr = xxnr;
    }

    public int getJg() {
        return jg;
    }

    public void setJg(int jg) {
        this.jg = jg;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
}
