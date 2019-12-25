package com.tmri.framework.bean;

import java.io.Serializable;

/**
 * <p> * Title: SYS_DESKTOP_LINKSµÄ³Ö¾ÃÀà * </p>
 * 
 * <p> * Description: * </p>
 * 
 * <p> * Copyright: Copyright (c) 2010 * </p>
 * 
 * <p> * Company: Tmri * </p>
 * 
 * @author wengyf
 */
public class DesktopLinks implements Serializable {
	private static final long serialVersionUID = 1l;
	private String xh;
	private String ljfl;
	private String ljmc;
	private String ljdz;
	private String gxsj;
	private String bz;
    private String cjjg;
    private String dzs;
    private String mcs;
	public String getXh(){
		return xh;
	}
	public void setXh(String xh){
		this.xh=xh;
	}
	public String getLjfl(){
		return ljfl;
	}
	public void setLjfl(String ljfl){
		this.ljfl=ljfl;
	}
	public String getLjmc(){
		return ljmc;
	}
	public void setLjmc(String ljmc){
		this.ljmc=ljmc;
	}
	public String getLjdz(){
		return ljdz;
	}
	public void setLjdz(String ljdz){
		this.ljdz=ljdz;
	}
	public String getGxsj(){
		return gxsj;
	}
	public void setGxsj(String gxsj){
		this.gxsj=gxsj;
	}
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz=bz;
	}

    public String getDzs() {
        return dzs;
    }

    public void setDzs(String dzs) {
        this.dzs = dzs;
    }

    public String getMcs() {
        return mcs;
    }

    public void setMcs(String mcs) {
        this.mcs = mcs;
    }

    public String getCjjg() {
        return cjjg;
    }

    public void setCjjg(String cjjg) {
        this.cjjg = cjjg;
    }
}

