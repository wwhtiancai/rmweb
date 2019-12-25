package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/11/27.
 */
public class EriCustomizeContent {

    private String sf;      //省份代码，江苏为32
    private Long sxh;        //标识卡号顺序号
    private String user0sbq;    //user0上半区内容
    private String user0xbq;    //user0下半区内容
    private String kh;          //标识卡号
    private String tid;         //标识TID
    private String ph;          //标识批号
    private String lsh;         //业务流水号
    private int zt;             //个性化状态
    private String u0xkl;    //user0区新写口令
    private String rootU0xkl; //user0区原写口令
    private String sdkl;        //标识锁定口令
    private Vehicle vehicle;    //个性化车辆信息
    private String protectKey;  //保护密钥
    private String sign;        //签名
    private String frame;       //数据帧
    private long gxhjlxh;
    private String u1xkl;
    private String u2xkl;
    private String u3xkl;
    private String u4xkl;
    private String u5xkl;


    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public Long getSxh() {
        return sxh;
    }

    public void setSxh(Long sxh) {
        this.sxh = sxh;
    }

    public String getUser0sbq() {
        return user0sbq;
    }

    public void setUser0sbq(String user0sbq) {
        this.user0sbq = user0sbq;
    }

    public String getUser0xbq() {
        return user0xbq;
    }

    public void setUser0xbq(String user0xbq) {
        this.user0xbq = user0xbq;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getU0xkl() {
        return u0xkl;
    }

    public void setU0xkl(String u0xkl) {
        this.u0xkl = u0xkl;
    }

    public String getRootU0xkl() {
        return rootU0xkl;
    }

    public void setRootU0xkl(String rootU0xkl) {
        this.rootU0xkl = rootU0xkl;
    }

    public String getSdkl() {
        return sdkl;
    }

    public void setSdkl(String sdkl) {
        this.sdkl = sdkl;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getProtectKey() {
        return protectKey;
    }

    public void setProtectKey(String protectKey) {
        this.protectKey = protectKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public long getGxhjlxh() {
        return gxhjlxh;
    }

    public void setGxhjlxh(long gxhjlxh) {
        this.gxhjlxh = gxhjlxh;
    }

    public String getU1xkl() {
        return u1xkl;
    }

    public void setU1xkl(String u1xkl) {
        this.u1xkl = u1xkl;
    }

    public String getU2xkl() {
        return u2xkl;
    }

    public void setU2xkl(String u2xkl) {
        this.u2xkl = u2xkl;
    }

    public String getU3xkl() {
        return u3xkl;
    }

    public void setU3xkl(String u3xkl) {
        this.u3xkl = u3xkl;
    }

    public String getU4xkl() {
        return u4xkl;
    }

    public void setU4xkl(String u4xkl) {
        this.u4xkl = u4xkl;
    }

    public String getU5xkl() {
        return u5xkl;
    }

    public void setU5xkl(String u5xkl) {
        this.u5xkl = u5xkl;
    }
}
