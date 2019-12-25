package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/22.
 */
public class PackageCase {

    private String bzxh; //包装箱号
    private String qshh; //起始盒号
    private String zzhh; //终止盒号
    private String bzxxh; //包装箱型号

    public String getBzxh() {
        return bzxh;
    }

    public void setBzxh(String bzxh) {
        this.bzxh = bzxh;
    }

    public String getQshh() {
        return qshh;
    }

    public void setQshh(String qshh) {
        this.qshh = qshh;
    }

    public String getZzhh() {
        return zzhh;
    }

    public void setZzhh(String zzhh) {
        this.zzhh = zzhh;
    }

    public String getBzxxh() {
        return bzxxh;
    }

    public void setBzxxh(String bzxxh) {
        this.bzxxh = bzxxh;
    }
}
