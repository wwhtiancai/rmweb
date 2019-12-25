package com.tmri.rfid.ctrl.view;

import java.util.Date;

import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.Vehicle;

/*
 * st
 * 2017-08-21
 */
public class EriUnbindView {

    private String tid;
    private String kh;
    private String fzjg;
    private String hpzl;
    private String hphm;
    private String czr;    //操作人
	
    private Date created_at;    //创建日期

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getKh() {
		return kh;
	}

	public void setKh(String kh) {
		this.kh = kh;
	}

	public String getFzjg() {
		return fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
    
}
