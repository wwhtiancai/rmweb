package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_WS_CONTROLµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jeff
 * @version 1.0
 */
public class FrmWsControl implements Serializable{
  private String jkxlh;
  private String xtlb;
  private String azdm;
  private String dyrjmc;
  private String dyrjkfdw;
  private String dyzdw;
  private String fzjg;
  private String kfwjk;
  private String ksip;
  private String jsip;
  private String jkqsrq;
  private String jkjzrq;
  private String bz;
  private String jyw;
  private String gxsj="";
  private String dyfzjg="";
  
  public String getDyfzjg() {
	return dyfzjg;
}
public void setDyfzjg(String dyfzjg) {
	this.dyfzjg = dyfzjg;
}
public String getXtlb() {
	return xtlb;
}
public void setXtlb(String xtlb) {
	this.xtlb = xtlb;
}
public String getGxsj() {
	return gxsj;
}
public void setGxsj(String gxsj) {
	this.gxsj = gxsj;
}
public String getAzdm() { 
       return StringUtil.transBlank(azdm);
  }
  public void setAzdm(String azdm) { 
       this.azdm = azdm;
  }
  public String getDyrjmc() { 
       return StringUtil.transBlank(dyrjmc);
  }
  public void setDyrjmc(String dyrjmc) { 
       this.dyrjmc = dyrjmc;
  }
  public String getDyrjkfdw() { 
       return StringUtil.transBlank(dyrjkfdw);
  }
  public void setDyrjkfdw(String dyrjkfdw) { 
       this.dyrjkfdw = dyrjkfdw;
  }
  public String getDyzdw() { 
       return StringUtil.transBlank(dyzdw);
  }
  public void setDyzdw(String dyzdw) { 
       this.dyzdw = dyzdw;
  }
  public String getFzjg() { 
       return StringUtil.transBlank(fzjg);
  }
  public void setFzjg(String fzjg) { 
       this.fzjg = fzjg;
  }
  public String getKfwjk() { 
       return StringUtil.transBlank(kfwjk);
  }
  public void setKfwjk(String kfwjk) { 
       this.kfwjk = kfwjk;
  }
  public String getKsip() { 
       return StringUtil.transBlank(ksip);
  }
  public void setKsip(String ksip) { 
       this.ksip = ksip;
  }
  public String getJsip() { 
       return StringUtil.transBlank(jsip);
  }
  public void setJsip(String jsip) { 
       this.jsip = jsip;
  }
  public String getJkqsrq() { 
       return StringUtil.transBlank(jkqsrq);
  }
  public void setJkqsrq(String jkqsrq) { 
       this.jkqsrq = jkqsrq;
  }
  public String getJkjzrq() { 
       return StringUtil.transBlank(jkjzrq);
  }
  public void setJkjzrq(String jkjzrq) { 
       this.jkjzrq = jkjzrq;
  }
  public String getBz() { 
       return StringUtil.transBlank(bz);
  }
  public void setBz(String bz) { 
       this.bz = bz;
  }
  public String getJkxlh() { 
       return StringUtil.transBlank(jkxlh);
  }
  public void setJkxlh(String jkxlh) { 
       this.jkxlh = jkxlh;
  }
  public String getJyw() { 
       return StringUtil.transBlank(jyw);
  }
  public void setJyw(String jyw) { 
       this.jyw = jyw;
  }
 }
