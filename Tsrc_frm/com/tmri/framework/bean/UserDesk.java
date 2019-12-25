package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_USERDESKµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class UserDesk implements Serializable{
    private String yhdh;
    private String cdbh;
    private String xtlb;
    private String bz;
    private String cxmc;
    private String mldh;
    private String mlmc;
    private String tbmc;
    
    public String getYhdh(){
        return this.yhdh;
    }
    public void setYhdh(String yhdh1) {
        this.yhdh =yhdh1;
    }
    public String getXtlb(){
        return this.xtlb;
    }
    public void setXtlb(String xtlb1) {
        this.xtlb =xtlb1;
    }
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getCxmc() {
		return cxmc;
	}
	public void setCxmc(String cxmc) {
		this.cxmc = cxmc;
	}
	public String getMldh() {
		return mldh;
	}
	public void setMldh(String mldh) {
		this.mldh = mldh;
	}
	public String getMlmc() {
		return mlmc;
	}
	public void setMlmc(String mlmc) {
		this.mlmc = mlmc;
	}
	public String getCdbh() {
		return cdbh;
	}
	public void setCdbh(String cdbh) {
		this.cdbh = cdbh;
	}
	public String getTbmc() {
		return tbmc;
	}
	public void setTbmc(String tbmc) {
		this.tbmc = tbmc;
	}
}
