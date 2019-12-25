package com.tmri.share.frm.bean;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_CTRLPARAµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jiji
 * @version 1.0
 */
public class FrmCtrlpara implements Serializable{
  private String azdm;
  private String gjz;
  private String csmc;
  private String csz;
  private String jyw;
  private String gxsj;
  public String getAzdm() { 
       return StringUtil.transBlank(azdm);
  }
  public void setAzdm(String azdm) { 
       this.azdm = azdm;
  }
  public String getGjz() { 
       return StringUtil.transBlank(gjz);
  }
  public void setGjz(String gjz) { 
       this.gjz = gjz;
  }
  public String getCsmc() { 
       return StringUtil.transBlank(csmc);
  }
  public void setCsmc(String csmc) { 
       this.csmc = csmc;
  }
  public String getCsz() { 
       return StringUtil.transBlank(csz);
  }
  public void setCsz(String csz) { 
       this.csz = csz;
  }
  public String getJyw() { 
       return StringUtil.transBlank(jyw);
  }
  public void setJyw(String jyw) { 
       this.jyw = jyw;
  }
  public String getGxsj() { 
       return StringUtil.transBlank(gxsj);
  }
  public void setGxsj(String gxsj) { 
       this.gxsj = gxsj;
  }
 }
