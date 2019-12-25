package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016/10/11.
 */
public class ReviewRecord {

    private Long xh;
    private String ywdm;
    private Long ywxh;
    private int zt;
    private String yj;
    private String shrdh;
    private Date shsj;

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getYwdm() {
        return ywdm;
    }

    public void setYwdm(String ywdm) {
        this.ywdm = ywdm;
    }

    public Long getYwxh() {
        return ywxh;
    }

    public void setYwxh(Long ywxh) {
        this.ywxh = ywxh;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getYj() {
        return yj;
    }

    public void setYj(String yj) {
        this.yj = yj;
    }

    public String getShrdh() {
        return shrdh;
    }

    public void setShrdh(String shrdh) {
        this.shrdh = shrdh;
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }
}
