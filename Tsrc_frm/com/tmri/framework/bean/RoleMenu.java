package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_ROLEMENUµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class RoleMenu implements Serializable{
    private String jsdh;
    private String xtlb;
    private String cdbh;
    private String gnlb;
    public String getJsdh(){
        return this.jsdh;
    }
    public void setJsdh(String jsdh1) {
        this.jsdh =jsdh1;
    }
    public String getXtlb(){
        return this.xtlb;
    }
    public void setXtlb(String xtlb1) {
        this.xtlb =xtlb1;
    }
    public String getGnlb(){
        return this.gnlb;
    }
    public void setGnlb(String gnlb1) {
        this.gnlb =gnlb1;
    }
	public String getCdbh() {
		return cdbh;
	}
	public void setCdbh(String cdbh) {
		this.cdbh = cdbh;
	}
}
