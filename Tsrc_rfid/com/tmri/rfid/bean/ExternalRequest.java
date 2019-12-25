package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016-03-23.
 */
public class ExternalRequest {

    private long xh;
    private String qqmc;
    private String qqcs;
    private int zt;
    private String cljg;
    private Date cjsj;
    private Date gxsj;

    public long getXh() {
        return xh;
    }

    public void setXh(long xh) {
        this.xh = xh;
    }

    public String getQqmc() {
        return qqmc;
    }

    public void setQqmc(String qqmc) {
        this.qqmc = qqmc;
    }

    public String getQqcs() {
        return qqcs;
    }

    public void setQqcs(String qqcs) {
        this.qqcs = qqcs;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getCljg() {
        return cljg;
    }

    public void setCljg(String cljg) {
        this.cljg = cljg;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }
}
