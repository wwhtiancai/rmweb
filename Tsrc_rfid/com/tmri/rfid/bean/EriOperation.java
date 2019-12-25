package com.tmri.rfid.bean;

import java.util.Date;

/**
 * 
 * @author stone
 * @date 2016-3-16 上午10:37:48
 */
public class EriOperation {

	private int type; // 1-原料入库 2-初始化 3-公安部入库 4-公安部出库 5-总队入库 6-支队领用 7-个性化
    private String dh; //原料入库单号、公安部入库单号、公安部出库单号、总队入库单号、领用单号
    private String jbr; 
    private String jbrxm;
    private Date rq;
    private String ssbm;
    private String bmmc;
    private String bz;
    
    private String hphm;
    private String hpzl;
    private String fzjg;
    private int gxhzt; //个性化状态
    
    private String lyr;//领用人
    private String glbmmc;//管理部门名称
    private Long id;
    
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public String getJbrxm() {
		return jbrxm;
	}
	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public String getSsbm() {
		return ssbm;
	}
	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getHpzl() {
		return hpzl;
	}
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public int getGxhzt() {
		return gxhzt;
	}
	public void setGxhzt(int gxhzt) {
		this.gxhzt = gxhzt;
	}
	public String getLyr() {
		return lyr;
	}
	public void setLyr(String lyr) {
		this.lyr = lyr;
	}
	public String getGlbmmc() {
		return glbmmc;
	}
	public void setGlbmmc(String glbmmc) {
		this.glbmmc = glbmmc;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
