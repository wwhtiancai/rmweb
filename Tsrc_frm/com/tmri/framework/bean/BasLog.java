package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 ** zhoujn 自动生成
 **/
public class BasLog implements Serializable {
	public BasLog(){
		
	}
	public BasLog(String bmdm,String bz,
			String clsj,String czlx,String cznr,String ip,String jbr){
		this.bmdm=bmdm;
		this.bz=bz;
		this.clsj=clsj;
		this.czlx=czlx;
		this.cznr=cznr;
		this.ip=ip;
		this.jbr=jbr;
		
	}
	private String bmdm;

	public String getBmdm() {
		return StringUtil.transBlank(bmdm);
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	private String clsj;

	public String getClsj() {
		return StringUtil.transBlank(clsj);
	}

	public void setClsj(String clsj) {
		this.clsj = clsj;
	}

	private String czlx;

	public String getCzlx() {
		return StringUtil.transBlank(czlx);
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	private String cznr;

	public String getCznr() {
		return StringUtil.transBlank(cznr);
	}

	public void setCznr(String cznr) {
		this.cznr = cznr;
	}

	private String jbr;

	public String getJbr() {
		return StringUtil.transBlank(jbr);
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}

	private String sm;

	public String getSm() {
		return StringUtil.transBlank(sm);
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	private String bz;

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	private String ip;

	public String getIp() {
		return StringUtil.transBlank(ip);
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}