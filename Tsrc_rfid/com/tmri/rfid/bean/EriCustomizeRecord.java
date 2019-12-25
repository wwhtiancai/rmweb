package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/15.
 */
public class EriCustomizeRecord {

    private Long id;
    private String tid;
    private String lsh;
    private int zt;
    private Date gxhrq;
    private String gxhczr;
    private String sbyy;
    private Long clxxbfid;
    private Long qqxh;
    private String kh;
    
    private String hpzl;
    private String fzjg;
    private String hphm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public Date getGxhrq() {
        return gxhrq;
    }

    public void setGxhrq(Date gxhrq) {
        this.gxhrq = gxhrq;
    }

    public String getGxhczr() {
        return gxhczr;
    }

    public void setGxhczr(String gxhczr) {
        this.gxhczr = gxhczr;
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

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

    public String getSbyy() {
        return sbyy;
    }

    public void setSbyy(String sbyy) {
        this.sbyy = sbyy;
    }

    public Long getClxxbfid() {
        return clxxbfid;
    }

    public void setClxxbfid(Long clxxbfid) {
        this.clxxbfid = clxxbfid;
    }

    public Long getQqxh() {
        return qqxh;
    }

    public void setQqxh(Long qqxh) {
        this.qqxh = qqxh;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }
}
