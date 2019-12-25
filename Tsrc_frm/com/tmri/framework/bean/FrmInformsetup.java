package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_INFORM_SETUPµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jianghl
 * @version 1.0
 */
public class FrmInformsetup implements Serializable{
  private String xxdm;
  private String xxmc;
  private String glbm;
  private String bmmc;
  private String yhdh;
  private String yhdhmc;
  private String txsj;
  private String txsjmc;
  private String zt;
  private String bz;
  private String baxjbm;
  private String bmjb;
  private String txzq;
  private String sctxsj;
  public String getTxzq(){
		return txzq;
	}
	public void setTxzq(String txzq){
		this.txzq=txzq;
	}
	public String getXxdm() { 
       return StringUtil.transBlank(xxdm);
  }
  public void setXxdm(String xxdm) { 
       this.xxdm = xxdm;
  }
  public String getGlbm() { 
       return StringUtil.transBlank(glbm);
  }
  public void setGlbm(String glbm) { 
       this.glbm = glbm;
  }
  public String getYhdh() { 
       return StringUtil.transBlank(yhdh);
  }
  public void setYhdh(String yhdh) { 
       this.yhdh = yhdh;
  }
  public String getTxsj() { 
       return StringUtil.transBlank(txsj);
  }
  public void setTxsj(String txsj) { 
       this.txsj = txsj;
  }
  public String getZt() { 
       return StringUtil.transBlank(zt);
  }
  public void setZt(String zt) { 
       this.zt = zt;
  }
  public String getBz() { 
       return StringUtil.transBlank(bz);
  }
  public void setBz(String bz) { 
       this.bz = bz;
  }
	public String getBaxjbm(){
		return baxjbm;
	}
	public void setBaxjbm(String baxjbm){
		this.baxjbm=baxjbm;
	}
	public String getBmjb(){
		return bmjb;
	}
	public void setBmjb(String bmjb){
		this.bmjb=bmjb;
	}
	public String getXxmc(){
		return xxmc;
	}
	public void setXxmc(String xxmc){
		this.xxmc=xxmc;
	}
	public String getBmmc(){
		return bmmc;
	}
	public void setBmmc(String bmmc){
		this.bmmc=bmmc;
	}
	public String getYhdhmc(){
		return yhdhmc;
	}
	public void setYhdhmc(String yhdhmc){
		this.yhdhmc=yhdhmc;
	}
	public String getTxsjmc(){
		return txsjmc;
	}
	public void setTxsjmc(String txsjmc){
		this.txsjmc=txsjmc;
	}
	public String getSctxsj(){
		return sctxsj;
	}
	public void setSctxsj(String sctxsj){
		this.sctxsj=sctxsj;
	}

 }
