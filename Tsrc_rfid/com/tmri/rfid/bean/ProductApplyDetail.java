package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/10/14.
 */
public class ProductApplyDetail {

    private long id;
    private String lydh;
    private String bzh;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public String getBzh() {
        return bzh;
    }

    public void setBzh(String bzh) {
        this.bzh = bzh;
    }
}
