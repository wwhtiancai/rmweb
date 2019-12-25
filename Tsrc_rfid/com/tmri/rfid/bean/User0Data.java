package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016-05-17.
 */
public class User0Data {

    private int cardType;
    private String kh;
    private String sf;
    private String fpdh;
    private String syxz;
    private Date ccrq;
    private String cllx;
    private int plOrGlSign;
    private String hphm;
    private String hpzl;
    private Date yxqz;
    private Date qzbfqz;
    private int hdzk;
    private int zzl;
    private String csys;
    private int pl;
    private int gl;
    private int zqyzl;

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getFpdh() {
        return fpdh;
    }

    public void setFpdh(String fpdh) {
        this.fpdh = fpdh;
    }

    public String getSyxz() {
        return syxz;
    }

    public void setSyxz(String syxz) {
        this.syxz = syxz;
    }

    public Date getCcrq() {
        return ccrq;
    }

    public void setCcrq(Date ccrq) {
        this.ccrq = ccrq;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public int getPlOrGlSign() {
        return plOrGlSign;
    }

    public void setPlOrGlSign(int plOrGlSign) {
        this.plOrGlSign = plOrGlSign;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
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

    public int getHdzk() {
        return hdzk;
    }

    public void setHdzk(int hdzk) {
        this.hdzk = hdzk;
    }

    public int getZzl() {
        return zzl;
    }

    public void setZzl(int zzl) {
        this.zzl = zzl;
    }

    public String getCsys() {
        return csys;
    }

    public void setCsys(String csys) {
        this.csys = csys;
    }

    public int getPl() {
        return pl;
    }

    public void setPl(int pl) {
        this.pl = pl;
    }

    public int getGl() {
        return gl;
    }

    public void setGl(int gl) {
        this.gl = gl;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "User0Data{" +
                "cardType='" + cardType + '\'' +
                ", kh='" + kh + '\'' +
                ", sf='" + sf + '\'' +
                ", fpdh='" + fpdh + '\'' +
                ", syxz='" + syxz + '\'' +
                ", ccrq=" + ccrq +
                ", cllx='" + cllx + '\'' +
                ", plOrGlSign=" + plOrGlSign +
                ", hphm='" + hphm + '\'' +
                ", hpzl='" + hpzl + '\'' +
                ", yxqz=" + yxqz +
                ", qzbfqz=" + qzbfqz +
                ", hdzk=" + hdzk +
                ", zzl=" + zzl +
                ", csys='" + csys + '\'' +
                ", pl=" + pl +
                ", gl=" + gl +
                '}';
    }
}
