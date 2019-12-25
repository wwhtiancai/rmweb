package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/22.
 */
public class PackageBox {

    private String bzxh; //包装盒号
    private String qskh; //起始卡号
    private String zzkh; //终止卡号
    private String ssbm; //所属部门
    private String cpdm; //产品代码

    public String getBzxh() {
        return bzxh;
    }

    public void setBzxh(String bzxh) {
        this.bzxh = bzxh;
    }

    public String getQskh() {
        return qskh;
    }

    public void setQskh(String qskh) {
        this.qskh = qskh;
    }

    public String getZzkh() {
        return zzkh;
    }

    public void setZzkh(String zzkh) {
        this.zzkh = zzkh;
    }

    public String getSsbm() {
        return ssbm;
    }

    public void setSsbm(String ssbm) {
        this.ssbm = ssbm;
    }

    public String getCpdm() {
        return cpdm;
    }

    public void setCpdm(String cpdm) {
        this.cpdm = cpdm;
    }
}
