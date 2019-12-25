package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_USERMENUµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class UserMenu implements Serializable{
    private String yhdh;
    private String cdbh;
    private String gnlb;
    private String mldh;
    private String xtlb;
    public String getYhdh(){
        return this.yhdh;
    }
    public void setYhdh(String yhdh1) {
        this.yhdh =yhdh1;
    }
    public String getMldh(){
        return this.mldh;
    }
    public void setMldh(String mldh1) {
        this.mldh =mldh1;
    }
	public String getCdbh() {
		return cdbh;
	}
	public void setCdbh(String cdbh) {
		this.cdbh = cdbh;
	}
	public String getGnlb(){
		return gnlb;
	}
	public void setGnlb(String gnlb){
		this.gnlb=gnlb;
	}
	public String getXtlb(){
		return xtlb;
	}
	public void setXtlb(String xtlb){
		this.xtlb=xtlb;
	}
}
