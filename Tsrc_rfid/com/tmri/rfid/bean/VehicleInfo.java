package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016/10/10.
 */
public class VehicleInfo {

    private String hpzl = ""; //号牌种类
    private String hphm = ""; //号牌号码
    private String cllx = ""; //车辆类型
    private String csys = ""; //车身颜色
    private String syxz = ""; //使用性质
    private String syr = ""; //机动车所有人
    private Date yxqz; //检验有效期止
    private Date qzbfqz; //强制报废期止
    private Date bxzzrq; //保险终止日期
    private Integer pl; //排量
    private String fzjg = ""; //发证机关
    private Integer hdzzl; //核定载质量
    private Integer hdzk; //核定载客
    private Integer zbzl;   //整备质量
    private Date ccrq; //出厂日期
    private String bz = ""; //备注
    private String clsbdh; //车辆识别代号
    private String clpp;    //车辆品牌
    private String clpp1;   //车辆品牌名称
    private Integer zzl; //总质量
    private Double gl; //功率
    private String jdcxh; //机动车序号，对应外部系统VEHICLE.XH
    private Date cjrq;      //创建日期
    private Date gxrq;      //更新日期
    private Integer zqyzl;  //准牵引质量
    private String lsh;
    private String xh; //序号
    private String xszzp;

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public String getCsys() {
        return csys;
    }

    public void setCsys(String csys) {
        this.csys = csys;
    }

    public String getSyxz() {
        return syxz;
    }

    public void setSyxz(String syxz) {
        this.syxz = syxz;
    }

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public Date getQzbfqz() {
        return qzbfqz;
    }

    public void setQzbfqz(Date qzbfqz) {
        this.qzbfqz = qzbfqz;
    }

    public Date getBxzzrq() {
        return bxzzrq;
    }

    public void setBxzzrq(Date bxzzrq) {
        this.bxzzrq = bxzzrq;
    }

    public Integer getPl() {
        return pl;
    }

    public void setPl(Integer pl) {
        this.pl = pl;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public Integer getHdzzl() {
        return hdzzl;
    }

    public void setHdzzl(Integer hdzzl) {
        this.hdzzl = hdzzl;
    }

    public Integer getHdzk() {
        return hdzk;
    }

    public void setHdzk(Integer hdzk) {
        this.hdzk = hdzk;
    }

    public Integer getZbzl() {
        return zbzl;
    }

    public void setZbzl(Integer zbzl) {
        this.zbzl = zbzl;
    }

    public Date getCcrq() {
        return ccrq;
    }

    public void setCcrq(Date ccrq) {
        this.ccrq = ccrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getClsbdh() {
        return clsbdh;
    }

    public void setClsbdh(String clsbdh) {
        this.clsbdh = clsbdh;
    }

    public String getClpp() {
        return clpp;
    }

    public void setClpp(String clpp) {
        this.clpp = clpp;
    }

    public String getClpp1() {
        return clpp1;
    }

    public void setClpp1(String clpp1) {
        this.clpp1 = clpp1;
    }

    public Integer getZzl() {
        return zzl;
    }

    public void setZzl(Integer zzl) {
        this.zzl = zzl;
    }

    public Double getGl() {
        return gl;
    }

    public void setGl(Double gl) {
        this.gl = gl;
    }

    public String getJdcxh() {
        return jdcxh;
    }

    public void setJdcxh(String jdcxh) {
        this.jdcxh = jdcxh;
    }

    public Date getCjrq() {
        return cjrq;
    }

    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public Integer getZqyzl() {
        return zqyzl;
    }

    public void setZqyzl(Integer zqyzl) {
        this.zqyzl = zqyzl;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXszzp() {
        return xszzp;
    }

    public void setXszzp(String xszzp) {
        this.xszzp = xszzp;
    }
}
