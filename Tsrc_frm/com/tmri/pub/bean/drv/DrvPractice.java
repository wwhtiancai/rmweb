package com.tmri.pub.bean.drv;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>
 * Title:DRV_PRACTICEµÄ³Ö¾ÃÀà
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author long
 * @version 1.0
 */
public class DrvPractice implements Serializable {
	private String jlxh;
	private String xh;
	private String fzjg;
	private String lsh;
	private String zjcx;
	private String yzjcx;
	private String sxyxqs;
	private String sxyxqz;
	private String sxqljjf1;
	private String sxqljjf2;
	private String bhzjcx;
	private String sxzjcx;
	private String gxsj;
	private String jlzt;

	public String getJlxh() {
		return StringUtil.transBlank(jlxh);
	}

	public void setJlxh(String jlxh) {
		this.jlxh = jlxh;
	}

	public String getXh() {
		return StringUtil.transBlank(xh);
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getFzjg() {
		return StringUtil.transBlank(fzjg);
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getLsh() {
		return StringUtil.transBlank(lsh);
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getZjcx() {
		return StringUtil.transBlank(zjcx);
	}

	public void setZjcx(String zjcx) {
		this.zjcx = zjcx;
	}

	public String getYzjcx() {
		return StringUtil.transBlank(yzjcx);
	}

	public void setYzjcx(String yzjcx) {
		this.yzjcx = yzjcx;
	}

	public String getSxyxqs() {
		return StringUtil.transBlank(sxyxqs);
	}

	public void setSxyxqs(String sxyxqs) {
		this.sxyxqs = sxyxqs;
	}

	public String getSxyxqz() {
		return StringUtil.transBlank(sxyxqz);
	}

	public void setSxyxqz(String sxyxqz) {
		this.sxyxqz = sxyxqz;
	}

	public String getSxqljjf1() {
		return StringUtil.transBlank(sxqljjf1);
	}

	public void setSxqljjf1(String sxqljjf1) {
		this.sxqljjf1 = sxqljjf1;
	}

	public String getSxqljjf2() {
		return StringUtil.transBlank(sxqljjf2);
	}

	public void setSxqljjf2(String sxqljjf2) {
		this.sxqljjf2 = sxqljjf2;
	}

	public String getBhzjcx() {
		return StringUtil.transBlank(bhzjcx);
	}

	public void setBhzjcx(String bhzjcx) {
		this.bhzjcx = bhzjcx;
	}

	public String getSxzjcx() {
		return StringUtil.transBlank(sxzjcx);
	}

	public void setSxzjcx(String sxzjcx) {
		this.sxzjcx = sxzjcx;
	}

	public String getGxsj() {
		return StringUtil.transBlank(gxsj);
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getJlzt() {
		return StringUtil.transBlank(jlzt);
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}
}
