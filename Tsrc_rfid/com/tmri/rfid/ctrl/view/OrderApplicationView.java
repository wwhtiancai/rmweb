package com.tmri.rfid.ctrl.view;

import com.tmri.rfid.bean.OrderApplication;

import java.util.Date;

/**
 * Created by Joey on 2015/9/29.
 */
public class OrderApplicationView extends OrderApplication {

    private String bmmc;    //部门名称
    private String lbmc;    //类别名称
    private String cpmc;    //产品名称
    private String ztms;    //状态描述

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getLbmc() {
        return lbmc;
    }

    public void setLbmc(String lbmc) {
        this.lbmc = lbmc;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getZtms() {
        return ztms;
    }

    public void setZtms(String ztms) {
        this.ztms = ztms;
    }
}
