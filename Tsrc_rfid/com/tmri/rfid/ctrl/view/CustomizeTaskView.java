package com.tmri.rfid.ctrl.view;

import java.util.Date;

import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.Vehicle;

/*
 *wuweihong
 *2015-11-16
 */
public class CustomizeTaskView extends Vehicle {

    private String lsh;    //��ˮ��
    private String sqr;    //������
    private String lxdh;    //��ϵ�绰
    private String jbr;    //������
    private Date sqrq;    //��������
    private long rwlx;    //��������
    private long zt;    //״̬
    private String czr;    //������
    private Date wcrq;    //�������
    private String bz; //��ע
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
