package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2015-11-13
 */
public class CustomizeTask {
    private Long xh;
    private String lsh;  //流水号
    private String sqr;    //	申请人
    private String lxdh; //联系电话
    private String jbr;        //经办人  --前台业务员姓名
    private Date sqrq;        //申请日期
    private int rwlx;    //任务类型 1-新申领，2-补领，3-换领
    private int zt;        //状态 1-提交，2-完成，3-取消，4-等待数据
    private String czr;  //操作人
    private Date wcrq;    //完成日期
    private Long clxxid; //车辆信息序号，对应RFID_VEHICLE.ID
    private Long yclxxid; //原车辆信息序号
    private String tid; //电子标识TID
    private String bz; //备注
    private String tpdz; // 图片地址

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

    public int getRwlx() {
        return rwlx;
    }

    public void setRwlx(int rwlx) {
        this.rwlx = rwlx;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
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

    public Long getClxxid() {
        return clxxid;
    }

    public void setClxxid(Long clxxid) {
        this.clxxid = clxxid;
    }

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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Long getYclxxid() {
        return yclxxid;
    }

    public void setYclxxid(Long yclxxid) {
        this.yclxxid = yclxxid;
    }

    public String getTpdz() {
        return tpdz;
    }

    public void setTpdz(String tpdz) {
        this.tpdz = tpdz;
    }


}
