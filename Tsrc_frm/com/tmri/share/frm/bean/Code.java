package com.tmri.share.frm.bean;

import com.tmri.share.frm.util.StringUtil;


/**
 * 
 * <p>
 * Title: tmriframe
 * </p>
 * 
 * <p>
 * Description: 对应代码表code
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author lhq
 * @version 1.0
 */
public class Code {
	private String xtlb = "";
	private String dmlb = ""; // 代码类别
	private String dmz = ""; // 代码值
	private String dmsm1 = ""; // 代码说明1
	private String dmsm2 = ""; // 代码说明2
	private String dmsm3 = ""; // 代码说明3
	private String dmsm4 = ""; // 代码说明4
	private String dmsx = "";// 代码属性
	private String zt = "";// 状态
	private String ywdx = "";// 调用对象（系统类别）
	private Long sxh;// 顺序号
	private String bz;
    private Long dmz2;  //用于存储到电子标识中的值

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getYwdx() {
		return ywdx;
	}

	public void setYwdx(String ywdx) {
		this.ywdx = ywdx;
	}

	public String getDmlb() {
		return dmlb;
	}

	public String getDmsm1() {
		return dmsm1;
	}

	public String getDmsm2() {
		return dmsm2;
	}

	public String getDmsm3() {
		return dmsm3;
	}

	public String getDmsm4() {
		return dmsm4;
	}

	public String getDmz() {
		return dmz;
	}

	public String getZt() {
		return zt;
	}

	public String getDmsx() {
		return dmsx;
	}

	public void setDmlb(String dmlb) {
		this.dmlb = dmlb;
	}

	public void setDmsm1(String dmsm1) {
		this.dmsm1 = dmsm1;
	}

	public void setDmsm2(String dmsm2) {
		this.dmsm2 = dmsm2;
	}

	public void setDmsm3(String dmsm3) {
		this.dmsm3 = dmsm3;
	}

	public void setDmsm4(String dmsm4) {
		this.dmsm4 = dmsm4;
	}

	public void setDmz(String dmz) {
		this.dmz = dmz;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public void setDmsx(String dmsx) {
		this.dmsx = dmsx;
	}

	public String getXtlb() {
		return xtlb;
	}

	public void setXtlb(String xtlb) {
		this.xtlb = xtlb;
	}

	public Long getSxh() {
		return sxh;
	}

	public void setSxh(Long sxh) {
		this.sxh = sxh;
	}

    public Long getDmz2() {
        return dmz2;
    }

    public void setDmz2(Long dmz2) {
        this.dmz2 = dmz2;
    }
}
