package com.tmri.rfid.bean;

import java.util.Date;

/**
 * ±êÇ©Õ³Ìù¼ÇÂ¼
 * @author stone
 * @date 2016-3-30 ÉÏÎç10:15:04
 */
public class EriPastedLog {

    private String tid;
    private String kh;
    private String hphm;
    private String lyr;
    private String bz;
    private Date lyrq;
    
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
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getLyr() {
		return lyr;
	}
	public void setLyr(String lyr) {
		this.lyr = lyr;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Date getLyrq() {
		return lyrq;
	}
	public void setLyrq(Date lyrq) {
		this.lyrq = lyrq;
	}
    
}
