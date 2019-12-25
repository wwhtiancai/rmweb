package com.tmri.pub.bean.drv;

public class DrvHealth {
	private  String fzjg;
	
	public String getFzjg() {
		return fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getDaokufzjg() {
		return daokufzjg;
	}

	public void setDaokufzjg(String daokufzjg) {
		this.daokufzjg = daokufzjg;
	}

	public String getDaokubz() {
		return daokubz;
	}

	public void setDaokubz(String daokubz) {
		this.daokubz = daokubz;
	}

	private String daokufzjg;
	private String daokubz;

	private String xh;
	private String dabh = "";
	private String tjyy = "";
	private String sg = "";
	private String zsl = "";
	private String ysl = "";
	private String bsl = "";
	private String tl = "";
	private String sz = "";
	private String zxz = "";
	private String yxz = "";
	private String qgjb = "";
	private String tjrq = "";
	private String tjyymc = "";
	private String lsh = "";
	private String gxsj = "";
	private String sftj = "0";// 是否体检（判断不强制体检时，是否提交了体检证明）Y：体检，其他为非提交
	private String tjxxly = ""; // 体检信息来源1:体检信息表 2:体检信息中间表
	private String bsl_mc = "";
	private String tl_mc = "";
	private String sz_mc = "";
	private String zxz_mc = "";
	private String yxz_mc = "";
	private String qgjb_mc = "";

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getSftj() {
		return sftj;
	}

	public void setSftj(String sftj) {
		this.sftj = sftj;
	}

	public String getBsl() {
		return bsl;
	}

	public String getGxsj() {
		return gxsj;
	}

	public String getLsh() {
		return lsh;
	}

	public String getTl() {
		return tl;
	}

	public String getDabh() {
		return dabh;
	}

	public String getQgjb() {
		return qgjb;
	}

	public String getTjrq() {
		if (tjrq == null) {
			return "";
		} else {
			return tjrq;
		}
	}

	public String getZxz() {
		return zxz;
	}

	public String getSz() {
		return sz;
	}

	public String getTjyymc() {
		if (tjyymc == null) {
			return "";
		} else {
			return tjyymc;
		}
	}

	public String getYxz() {
		return yxz;
	}

	public void setTjyy(String tjyy) {
		this.tjyy = tjyy;
	}

	public void setBsl(String bsl) {
		this.bsl = bsl;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}

	public void setDabh(String dabh) {
		this.dabh = dabh;
	}

	public void setQgjb(String qgjb) {
		this.qgjb = qgjb;
	}

	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}

	public void setZxz(String zxz) {
		this.zxz = zxz;
	}

	public void setSz(String sz) {
		this.sz = sz;
	}

	public void setTjyymc(String tjyymc) {
		this.tjyymc = tjyymc;
	}

	public void setYxz(String yxz) {
		this.yxz = yxz;
	}

	public String getTjyy() {
		if (tjyy == null) {
			return "";
		} else {
			return tjyy;
		}
	}

	public String getBsl_mc() {
		return bsl_mc;
	}

	public void setBsl_mc(String bslMc) {
		bsl_mc = bslMc;
	}

	public String getTl_mc() {
		return tl_mc;
	}

	public void setTl_mc(String tlMc) {
		tl_mc = tlMc;
	}

	public String getSz_mc() {
		return sz_mc;
	}

	public void setSz_mc(String szMc) {
		sz_mc = szMc;
	}

	public String getZxz_mc() {
		return zxz_mc;
	}

	public void setZxz_mc(String zxzMc) {
		zxz_mc = zxzMc;
	}

	public String getYxz_mc() {
		return yxz_mc;
	}

	public void setYxz_mc(String yxzMc) {
		yxz_mc = yxzMc;
	}

	public String getQgjb_mc() {
		return qgjb_mc;
	}

	public void setQgjb_mc(String qgjbMc) {
		qgjb_mc = qgjbMc;
	}

	public String getSg() {
		return sg;
	}

	public void setSg(String sg) {
		this.sg = sg;
	}

	public String getZsl() {
		return zsl;
	}

	public void setZsl(String zsl) {
		this.zsl = zsl;
	}

	public String getYsl() {
		return ysl;
	}

	public void setYsl(String ysl) {
		this.ysl = ysl;
	}

	public String getTjxxly() {
		return tjxxly;
	}

	public void setTjxxly(String tjxxly) {
		this.tjxxly = tjxxly;
	}

}
