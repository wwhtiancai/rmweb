package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;
/**
 * <p>Title:FRM_WS_IPXZµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jeff
 * @version 1.0
 */
public class FrmWsIpxz implements Serializable{
  private String jkid;
  private String fzjg;
  private String ip;
  private String dyxtlb;
  private String dyxtmc;
  private String bz;
  public String getJkid() { 
       return StringUtil.transBlank(jkid);
  }
  public void setJkid(String jkid) { 
       this.jkid = jkid;
  }
  public String getFzjg() { 
       return StringUtil.transBlank(fzjg);
  }
  public void setFzjg(String fzjg) { 
       this.fzjg = fzjg;
  }
  public String getIp() { 
       return StringUtil.transBlank(ip);
  }
  public void setIp(String ip) { 
       this.ip = ip;
  }
  public String getDyxtlb() { 
       return StringUtil.transBlank(dyxtlb);
  }
  public void setDyxtlb(String dyxtlb) { 
       this.dyxtlb = dyxtlb;
  }
  public String getDyxtmc() { 
       return StringUtil.transBlank(dyxtmc);
  }
  public void setDyxtmc(String dyxtmc) { 
       this.dyxtmc = dyxtmc;
  }
  public String getBz() { 
       return StringUtil.transBlank(bz);
  }
  public void setBz(String bz) { 
       this.bz = bz;
  }
 }
