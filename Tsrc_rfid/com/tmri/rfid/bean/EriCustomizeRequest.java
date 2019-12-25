package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016-03-23.
 * 用于在专网环境下生成个性化请求，同步到公安网后被处理并生成相应的个性化任务（EriCustomizeTask)
 * 本表只可由专网环境进行内容修改，公安网只被动接受请求并处理。
 */
public class EriCustomizeRequest {

    private long xh;
    private String hphm;
    private String hpzl;
    private String fzjg;
    private String lsh;
    private String glbm;
    private String tid;
    private String kh;
    private int zt;
    private String sbyy;
    private String cjr;
    private Date cjsj;
    private Date gxsj;
    private int ywlx;
    private Long qqxh;

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

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public long getXh() {
        return xh;
    }

    public void setXh(long xh) {
        this.xh = xh;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getGlbm() {
        return glbm;
    }

    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }

    public String getSbyy() {
        return sbyy;
    }

    public void setSbyy(String sbyy) {
        this.sbyy = sbyy;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public int getYwlx() {
        return ywlx;
    }

    public void setYwlx(int ywlx) {
        this.ywlx = ywlx;
    }

    public Long getQqxh() {
        return qqxh;
    }

    public void setQqxh(Long qqxh) {
        this.qqxh = qqxh;
    }
}
