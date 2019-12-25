package com.tmri.rfid.ctrl.view;

import java.util.Date;

import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.Vehicle;

/*
 *wuweihong
 *2015-11-16
 */
public class CustomizeTaskView extends Vehicle {

    private String lsh;    //流水号
    private String sqr;    //申请人
    private String lxdh;    //联系电话
    private String jbr;    //经办人
    private Date sqrq;    //申请日期
    private long rwlx;    //任务类型
    private long zt;    //状态
    private String czr;    //操作人
    private Date wcrq;    //完成日期
    private String bz; //备注
    private String tid;
    private String kh;
    private String tpdz;

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public Date getSqrq() {
        return sqrq;
    }

    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    public long getRwlx() {
        return rwlx;
    }

    public void setRwlx(long rwlx) {
        this.rwlx = rwlx;
    }

    public long getZt() {
        return zt;
    }

    public void setZt(long zt) {
        this.zt = zt;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    @Override
    public String getBz() {
        return bz;
    }

    @Override
    public void setBz(String bz) {
        this.bz = bz;
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

    public String getTpdz() {
        return tpdz;
    }

    public void setTpdz(String tpdz) {
        this.tpdz = tpdz;
    }
}
