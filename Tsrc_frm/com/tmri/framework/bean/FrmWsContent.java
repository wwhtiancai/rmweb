package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_WS_CONTENTµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jeff
 * @version 1.0
 */
public class FrmWsContent implements Serializable{
  private String jkid;
  private String jkmc;
  private String jkzt;
  private String jklb;
  private String jklbmc;
  private String jksx;
  private String jksxmc;
  private String fzl;
  private String sxl;
  private String fhl;
  private String jdmc;
  private String gxsj;
  private String rzbj;
  private String bz;
  private String nbipxz;
  private String jyw;
  
  public String getNbipxz() {
	return nbipxz;
}
public void setNbipxz(String nbipxz) {
	this.nbipxz = nbipxz;
}
public String getJksxmc() {
	return jksxmc;
}
public void setJksxmc(String jksxmc) {
	this.jksxmc = jksxmc;
}
public String getJklbmc() {
	return jklbmc;
}
public void setJklbmc(String jklbmc) {
	this.jklbmc = jklbmc;
}
public String getJkid() { 
       return StringUtil.transBlank(jkid);
  }
  public void setJkid(String jkid) { 
       this.jkid = jkid;
  }
  public String getJkmc() { 
       return StringUtil.transBlank(jkmc);
  }
  public void setJkmc(String jkmc) { 
       this.jkmc = jkmc;
  }
  public String getJkzt() { 
       return StringUtil.transBlank(jkzt);
  }
  public void setJkzt(String jkzt) { 
       this.jkzt = jkzt;
  }
  public String getJklb() { 
       return StringUtil.transBlank(jklb);
  }
  public void setJklb(String jklb) { 
       this.jklb = jklb;
  }
  public String getJksx() { 
       return StringUtil.transBlank(jksx);
  }
  public void setJksx(String jksx) { 
       this.jksx = jksx;
  }
  public String getFzl() { 
       return StringUtil.transBlank(fzl);
  }
  public void setFzl(String fzl) { 
       this.fzl = fzl;
  }
  public String getSxl() { 
       return StringUtil.transBlank(sxl);
  }
  public void setSxl(String sxl) { 
       this.sxl = sxl;
  }
  public String getFhl() { 
       return StringUtil.transBlank(fhl);
  }
  public void setFhl(String fhl) { 
       this.fhl = fhl;
  }
  public String getJdmc() { 
       return StringUtil.transBlank(jdmc);
  }
  public void setJdmc(String jdmc) { 
       this.jdmc = jdmc;
  }
  public String getGxsj() { 
       return StringUtil.transBlank(gxsj);
  }
  public void setGxsj(String gxsj) { 
       this.gxsj = gxsj;
  }
  public String getRzbj() { 
       return StringUtil.transBlank(rzbj);
  }
  public void setRzbj(String rzbj) { 
       this.rzbj = rzbj;
  }
  public String getBz() { 
       return StringUtil.transBlank(bz);
  }
  public void setBz(String bz) { 
       this.bz = bz;
  }
  public String getJyw() { 
       return StringUtil.transBlank(jyw);
  }
  public void setJyw(String jyw) { 
       this.jyw = jyw;
  }
 }
