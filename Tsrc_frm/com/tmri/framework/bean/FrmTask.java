package com.tmri.framework.bean;
import java.sql.Date;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_TASK的持久类 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmTask implements Serializable{
    private String xtlb;
    private String rwid;
    private String rwmc;
    private String zxsj;
    private String sjsm;
    private String rwzt;
    private String implc;
    private String qtip;
    private String yxipdz;
    private String bz;
    private String zdzxsj;
    
    private String currentRunningIp;//补充属性，当前任务运行的IP地址
    
	public String getCurrentRunningIp() {
		return currentRunningIp;
	}
	public void setCurrentRunningIp(String currentRunningIp) {
		this.currentRunningIp = currentRunningIp;
	}
	public String getXtlb() {
		return xtlb;
	}
	public void setXtlb(String xtlb) {
		this.xtlb = xtlb;
	}
	public String getRwid() {
		return rwid;
	}
	public void setRwid(String rwid) {
		this.rwid = rwid;
	}
	public String getRwmc() {
		return rwmc;
	}
	public void setRwmc(String rwmc) {
		this.rwmc = rwmc;
	}
	public String getZxsj() {
		return zxsj;
	}
	public void setZxsj(String zxsj) {
		this.zxsj = zxsj;
	}
	public String getSjsm() {
		return sjsm;
	}
	public void setSjsm(String sjsm) {
		this.sjsm = sjsm;
	}
	public String getRwzt() {
		return rwzt;
	}
	public void setRwzt(String rwzt) {
		this.rwzt = rwzt;
	}
	public String getImplc() {
		return implc;
	}
	public void setImplc(String implc) {
		this.implc = implc;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public String getYxipdz() {
		return yxipdz;
	}
	public void setYxipdz(String yxipdz) {
		this.yxipdz = yxipdz;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getZdzxsj() {
		return zdzxsj;
	}
	public void setZdzxsj(String zdzxsj) {
		this.zdzxsj = zdzxsj;
	}
   
}
