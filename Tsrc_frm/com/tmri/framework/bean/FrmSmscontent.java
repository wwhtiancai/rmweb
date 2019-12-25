package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_SMS_CONTENTµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:tmri </p>
 * @author jianghl
 * @version 1.0
 */
public class FrmSmscontent implements Serializable{
  private String xxbh;
  private String jsglbm;
  private String jsyhdh;
  private String xxdm;
  private String cjsj;
  private String xxnr;
  private String bj;
  private String fsglbm;
  private String fsr;
  private String bz;
  private String yhdh;
  private String dxlx;
  private String xxbt;
  public String getXxbt(){
		return xxbt;
	}
	public void setXxbt(String xxbt){
		this.xxbt=xxbt;
	}
	public String getXxbh() { 
       return StringUtil.transBlank(xxbh);
  }
  public void setXxbh(String xxbh) { 
       this.xxbh = xxbh;
  }
  public String getJsglbm() { 
       return StringUtil.transBlank(jsglbm);
  }
  public void setJsglbm(String jsglbm) { 
       this.jsglbm = jsglbm;
  }
  public String getJsyhdh() { 
       return StringUtil.transBlank(jsyhdh);
  }
  public void setJsyhdh(String jsyhdh) { 
       this.jsyhdh = jsyhdh;
  }
  public String getXxdm() { 
       return StringUtil.transBlank(xxdm);
  }
  public void setXxdm(String xxdm) { 
       this.xxdm = xxdm;
  }
  public String getCjsj() { 
       return StringUtil.transBlank(cjsj);
  }
  public void setCjsj(String cjsj) { 
       this.cjsj = cjsj;
  }
  public String getXxnr() { 
       return StringUtil.transBlank(xxnr);
  }
  public void setXxnr(String xxnr) { 
       this.xxnr = xxnr;
  }
  public String getBj() { 
       return StringUtil.transBlank(bj);
  }
  public void setBj(String bj) { 
       this.bj = bj;
  }
  public String getFsglbm() { 
       return StringUtil.transBlank(fsglbm);
  }
  public void setFsglbm(String fsglbm) { 
       this.fsglbm = fsglbm;
  }
  public String getFsr() { 
       return StringUtil.transBlank(fsr);
  }
  public void setFsr(String fsr) { 
       this.fsr = fsr;
  }
  public String getBz() { 
       return StringUtil.transBlank(bz);
  }
  public void setBz(String bz) { 
       this.bz = bz;
  }
  public String getYhdh() { 
       return StringUtil.transBlank(yhdh);
  }
  public void setYhdh(String yhdh) { 
       this.yhdh = yhdh;
  }
  public String getDxlx() { 
       return StringUtil.transBlank(dxlx);
  }
  public void setDxlx(String dxlx) { 
       this.dxlx = dxlx;
  }
 }
