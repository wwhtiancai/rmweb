package com.tmri.framework.bean;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_SYSRUNµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jiji
 * @version 1.0
 */
public class FrmSysrun implements Serializable{
  private String webip;
  private String rq;
  private String fwsd;
  private String xtlb;
  private String cdbh;
  private String gnid;
  private String czlx;
  private String fwcs;
  private String fwbhgcs;
  private String bcfwsj;
  private String bzfwsj;
  private String zfwsj;
  private String hgbj;
  private String bz;
  public String getWebip() { 
       return StringUtil.transBlank(webip);
  }
  public void setWebip(String webip) { 
       this.webip = webip;
  }
  public String getRq() { 
       return StringUtil.transBlank(rq);
  }
  public void setRq(String rq) { 
       this.rq = rq;
  }
  public String getFwsd() { 
       return StringUtil.transBlank(fwsd);
  }
  public void setFwsd(String fwsd) { 
       this.fwsd = fwsd;
  }
  public String getXtlb() { 
       return StringUtil.transBlank(xtlb);
  }
  public void setXtlb(String xtlb) { 
       this.xtlb = xtlb;
  }
  public String getCdbh() { 
       return StringUtil.transBlank(cdbh);
  }
  public void setCdbh(String cdbh) { 
       this.cdbh = cdbh;
  }
  public String getGnid() { 
       return StringUtil.transBlank(gnid);
  }
  public void setGnid(String gnid) { 
       this.gnid = gnid;
  }
  public String getCzlx() { 
       return StringUtil.transBlank(czlx);
  }
  public void setCzlx(String czlx) { 
       this.czlx = czlx;
  }
  public String getFwcs() { 
       return StringUtil.transBlank(fwcs);
  }
  public void setFwcs(String fwcs) { 
       this.fwcs = fwcs;
  }
  public String getFwbhgcs() { 
       return StringUtil.transBlank(fwbhgcs);
  }
  public void setFwbhgcs(String fwbhgcs) { 
       this.fwbhgcs = fwbhgcs;
  }
  public String getBcfwsj() { 
       return StringUtil.transBlank(bcfwsj);
  }
  public void setBcfwsj(String bcfwsj) { 
       this.bcfwsj = bcfwsj;
  }
  public String getBzfwsj() { 
       return StringUtil.transBlank(bzfwsj);
  }
  public void setBzfwsj(String bzfwsj) { 
       this.bzfwsj = bzfwsj;
  }
  public String getZfwsj() { 
       return StringUtil.transBlank(zfwsj);
  }
  public void setZfwsj(String zfwsj) { 
       this.zfwsj = zfwsj;
  }
  public String getHgbj() { 
       return StringUtil.transBlank(hgbj);
  }
  public void setHgbj(String hgbj) { 
       this.hgbj = hgbj;
  }
  public String getBz() { 
       return StringUtil.transBlank(bz);
  }
  public void setBz(String bz) { 
       this.bz = bz;
  }
 }
