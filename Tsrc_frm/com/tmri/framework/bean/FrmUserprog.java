package com.tmri.framework.bean;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

public class FrmUserprog  implements Serializable{
    private String yhdh;
    private String yxsjk;
    private String cxsx;
    private String jsdh;
    private String jssx;
    
	public String getJssx() {
		return StringUtil.transBlank(this.jssx);
	}
	public void setJssx(String jssx) {
		this.jssx = jssx;
	}
	public String getYhdh() {
		return StringUtil.transBlank(this.yhdh);
	}
	public void setYhdh(String yhdh) {
		this.yhdh = yhdh;
	}
	public String getYxsjk() {
		return StringUtil.transBlank(this.yxsjk);
	}
	public void setYxsjk(String yxsjk) {
		this.yxsjk = yxsjk;
	}
	public String getCxsx() {
		return StringUtil.transBlank(this.cxsx);
	}
	public void setCxsx(String cxsx) {
		this.cxsx = cxsx;
	}
	public String getJsdh() {
		return StringUtil.transBlank(this.jsdh);
	}
	public void setJsdh(String jsdh) {
		this.jsdh = jsdh;
	}
	
	private String xtlb;
	public String getXtlb() {
		return StringUtil.transBlank(this.xtlb);
	}
	public void setXtlb(String xtlb) {
		this.xtlb = xtlb;
	}
	
    

}
