package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/11/13.
 */
public class ElectronicCertificate {

    private Long xh;        //电子证书序号
    private String zsnr;    //证书内容
    private Date scrq;      //生成日期
    private int zt;         //状态， 0-无效，1-有效
    private int ssztlx;     //所属主体类型，1-安全模块，2-读写设备
    private String ssztbh;  //所属主体编号
    private int zsbh;       //证书编号，当同一主体有多个证书时使用

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getZsnr() {
        return zsnr;
    }

    public void setZsnr(String zsnr) {
        this.zsnr = zsnr;
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public int getSsztlx() {
        return ssztlx;
    }

    public void setSsztlx(int ssztlx) {
        this.ssztlx = ssztlx;
    }

    public String getSsztbh() {
        return ssztbh;
    }

    public void setSsztbh(String ssztbh) {
        this.ssztbh = ssztbh;
    }

    public int getZsbh() {
        return zsbh;
    }

    public void setZsbh(int zsbh) {
        this.zsbh = zsbh;
    }
}
