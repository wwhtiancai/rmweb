package com.tmri.share.frm.bean;
/**
 *
 * <p>Title: tmriframe</p>
 *
 * <p>Description: 对应代码类别表codetype</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author lhq
 * @version 1.0
 */
public class Codetype {
	private String xtlb = "";
  private String dmlb = ""; //代码类别
  private String lbsm = ""; //类别说明
  private String dmcd ; //代码长度
  private String lbsx = "";//类别属性
  private String lbbz = "";//类别标准
  private String bz = ""; //备注
  private String dmsx;//代码顺序
  private String dmlx;//代码类型
  private String jznc;//加载内存
 
public String getDmcd(){
		return dmcd;
	}

	public void setDmcd(String dmcd){
		this.dmcd=dmcd;
	}

	public String getDmsx(){
		return dmsx;
	}

	public void setDmsx(String dmsx){
		this.dmsx=dmsx;
	}

	public String getDmlx(){
		return dmlx;
	}

	public void setDmlx(String dmlx){
		this.dmlx=dmlx;
	}

public void setDmlb(String dmlb) {
	this.dmlb = dmlb;
}

public String getDmlb() {
    return dmlb;
  }

  public String getLbsm() {
    return lbsm;
  }

  public String getBz() {
    return bz;
  }

  public String getLbbz() {
    return lbbz;
  }

  public String getLbsx() {
    return lbsx;
  }
  public void setLbsm(String lbsm) {
    this.lbsm = lbsm;
  }

  public void setLbsx(String lbsx) {
    this.lbsx = lbsx;
  }

  public void setLbbz(String lbbz) {
    this.lbbz = lbbz;
  }

  public void setBz(String bz) {
    this.bz = bz;
  }

	public String getXtlb(){
		return xtlb;
	}

	public void setXtlb(String xtlb){
		this.xtlb=xtlb;
	}

	public String getJznc(){
		return jznc;
	}

	public void setJznc(String jznc){
		this.jznc=jznc;
	}
}
