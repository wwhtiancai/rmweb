package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;
/**
 * <p>Title:FRM_WS_LOGµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jeff
 * @version 1.0
 */
public class FrmWsLog implements Serializable{
  private String fwsj;
  private String jkxlh;
  private String jkid;
  private String jkmc;
  private String ip;
  private String fwbj;
  private String fhxx;
  private String jkdysj;
  private String jkdysj1;
  private String xtlb;
  
  
  public String getXtlb() {
	return xtlb;
}
public void setXtlb(String xtlb) {
	this.xtlb = xtlb;
}
public String getJkmc() {
	return jkmc;
}
public void setJkmc(String jkmc) {
	this.jkmc = jkmc;
}
public String getJkdysj() {
	return jkdysj;
}
public void setJkdysj(String jkdysj) {
	this.jkdysj = jkdysj;
}
public String getJkdysj1() {
	return jkdysj1;
}
public void setJkdysj1(String jkdysj1) {
	this.jkdysj1 = jkdysj1;
}
public String getFwsj() { 
       return StringUtil.transBlank(fwsj);
  }
  public void setFwsj(String fwsj) { 
       this.fwsj = fwsj;
  }
  public String getJkxlh() { 
       return StringUtil.transBlank(jkxlh);
  }
  public void setJkxlh(String jkxlh) { 
       this.jkxlh = jkxlh;
  }
  public String getJkid() { 
       return StringUtil.transBlank(jkid);
  }
  public void setJkid(String jkid) { 
       this.jkid = jkid;
  }
  public String getIp() { 
       return StringUtil.transBlank(ip);
  }
  public void setIp(String ip) { 
       this.ip = ip;
  }
  public String getFwbj() { 
       return StringUtil.transBlank(fwbj);
  }
  public void setFwbj(String fwbj) { 
       this.fwbj = fwbj;
  }
  public String getFhxx() { 
       return StringUtil.transBlank(fhxx);
  }
  public void setFhxx(String fhxx) { 
       this.fhxx = fhxx;
  }
 }
