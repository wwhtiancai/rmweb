package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_TASK_LOGµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmLogRow implements Serializable{
    private String zxxh;
    private String azdm;
    private String bzid;
    private String sjlx;
    private String sjmc;
    private String zjz;
    private String jllx;
    private String flag;
    private String info;
    private String zxsj;
    private String csbj;
    
	public String getSjmc() {
		return sjmc;
	}
	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}

	public String getZxxh() {
		return zxxh;
	}
	public void setZxxh(String zxxh) {
		this.zxxh = zxxh;
	}
	public String getAzdm() {
		return azdm;
	}
	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}
	public String getBzid() {
		return bzid;
	}
	public void setBzid(String bzid) {
		this.bzid = bzid;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public String getZjz() {
		return zjz;
	}
	public void setZjz(String zjz) {
		this.zjz = zjz;
	}
	public String getJllx() {
		return jllx;
	}
	public void setJllx(String jllx) {
		this.jllx = jllx;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getZxsj() {
		return zxsj;
	}
	public void setZxsj(String zxsj) {
		this.zxsj = zxsj;
	}
	public String getCsbj() {
		return csbj;
	}
	public void setCsbj(String csbj) {
		this.csbj = csbj;
	}
    

}
