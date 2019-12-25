package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;


/**
 * <p>
 * Title:FRM_ROLE的持久类
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
public class Role implements Serializable {
	private String jsdh;
	private String jsmc;
	private String jssx;
	private String bz;
	private String cxdh;
	private String xtlb;

	//zhoujn 20100520
	private String  jscj   ;
	private String  sjjsdh ;
	private String  yhdh   ;
	
	private String 	jssxmc;
	//zhoujn 20100625角色类型
	private String  jslx;
	private String  bmmc;
	private String  glbm;
	
	private String  tcdbh;
	private String  tgnid;
	private String  txtlb;
	
	//zhoujn 20111221 新增用于处理czqx
	private String czqx;
	
	public String getCzqx() {
		return StringUtil.transBlank(czqx);
	}

	public void setCzqx(String czqx) {
		this.czqx = czqx;
	}

	public String getTxtlb() {
		return StringUtil.transBlank(txtlb);
	}

	public void setTxtlb(String txtlb) {
		this.txtlb = txtlb;
	}

	public String getTcdbh() {
		return StringUtil.transBlank(tcdbh);
	}

	public void setTcdbh(String tcdbh) {
		this.tcdbh = tcdbh;
	}

	public String getTgnid() {
		return StringUtil.transBlank(tgnid);
	}

	public void setTgnid(String tgnid) {
		this.tgnid = tgnid;
	}

	public String getGlbm() {
		return StringUtil.transBlank(glbm);
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getBmmc() {
		return StringUtil.transBlank(bmmc);
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getJslx() {
		return StringUtil.transBlank(jslx);
	}

	public void setJslx(String jslx) {
		this.jslx = jslx;
	}

	public String getJssxmc() {
		return StringUtil.transBlank(jssxmc);
	}

	public void setJssxmc(String jssxmc) {
		this.jssxmc = jssxmc;
	}

	public String getYhdh() {
		return StringUtil.transBlank(yhdh);
	}

	public void setYhdh(String yhdh) {
		this.yhdh = yhdh;
	}

	public String getJscj() {
		return StringUtil.transBlank(jscj);
	}

	public void setJscj(String jscj) {
		this.jscj = jscj;
	}

	public String getSjjsdh() {
		return StringUtil.transBlank(sjjsdh);
	}

	public void setSjjsdh(String sjjsdh) {
		this.sjjsdh = sjjsdh;
	}

	public String getXtlb() {
		return StringUtil.transBlank(xtlb);
	}

	public void setXtlb(String xtlb) {
		this.xtlb = xtlb;
	}

	public String getJsmc() {
		return StringUtil.transBlank(jsmc);
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getJsdh() {
		return StringUtil.transBlank(this.jsdh);
	}

	public void setJsdh(String jsdh1) {
		this.jsdh = jsdh1;
	}

	public String getJssx() {
		return StringUtil.transBlank(this.jssx);
	}

	public void setJssx(String jssx1) {
		this.jssx = jssx1;
	}

	public String getBz() {
		return StringUtil.transBlank(this.bz);
	}

	public void setBz(String bz1) {
		this.bz = bz1;
	}

	public String getCxdh() {
		return StringUtil.transBlank(cxdh);
	}

	public void setCxdh(String cxdh) {
		this.cxdh = cxdh;
	}
}
