package com.tmri.share.frm.bean;
/**
 *
 * <p>Title: tmriframe</p>
 *
 * <p>Description: ��Ӧ��������codetype</p>
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
  private String dmlb = ""; //�������
  private String lbsm = ""; //���˵��
  private String dmcd ; //���볤��
  private String lbsx = "";//�������
  private String lbbz = "";//����׼
  private String bz = ""; //��ע
  private String dmsx;//����˳��
  private String dmlx;//��������
  private String jznc;//�����ڴ�
 
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
