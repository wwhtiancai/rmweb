package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016/1/18.
 */
public class EncryptorIndex {

    private long xh;
    private int index;
    private String encryptorId;
    private int zt;
    private Date lockTime;

    public long getXh() {
        return xh;
    }

    public void setXh(long xh) {
        this.xh = xh;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getEncryptorId() {
        return encryptorId;
    }

    public void setEncryptorId(String encryptorId) {
        this.encryptorId = encryptorId;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }
}
