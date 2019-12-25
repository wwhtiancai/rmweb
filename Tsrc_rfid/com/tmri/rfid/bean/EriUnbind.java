package com.tmri.rfid.bean;

import java.util.Date;

import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.Vehicle;

/*
 * st
 * 2017-08-22
 */
public class EriUnbind {

	private String xh;
    private String tid;
    private String kh;
    private Long clxxbfid;
    private String czr;    //操作人
    private Date created_at;    //创建日期
    
    
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
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
	public Long getClxxbfid() {
		return clxxbfid;
	}
	public void setClxxbfid(Long clxxbfid) {
		this.clxxbfid = clxxbfid;
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
