package com.tmri.rfid.ctrl.view;

import com.tmri.rfid.bean.EriScrapApp;

/*
 *wuweihong
 *2016-1-6
 */
public class EriScrapView extends EriScrapApp{
    private String bfdh; //±¨·Ïµ¥ºÅ
    private String tid; //tid
    private String kh;    //¿¨ºÅ
	public String getBfdh() {
		return bfdh;
	}
	public void setBfdh(String bfdh) {
		this.bfdh = bfdh;
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
}
