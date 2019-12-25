package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_NUM_SECTIONµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmNumSection implements Serializable{
    private String xtlb;
    private String bmqddh;
    private String glbm;
    private String fldmz;
    private String cjsj;
    private String ksbh;
    private String jsbh;
    private String sybj;
    private String syxl;
    public String getXtlb(){
        return this.xtlb;
    }
    public void setXtlb(String xtlb1) {
        this.xtlb =xtlb1;
    }
    public String getBmqddh(){
        return this.bmqddh;
    }
    public void setBmqddh(String bmqddh1) {
        this.bmqddh =bmqddh1;
    }
    public String getGlbm(){
        return this.glbm;
    }
    public void setGlbm(String glbm1) {
        this.glbm =glbm1;
    }
    public String getFldmz(){
        return this.fldmz;
    }
    public void setFldmz(String fldmz1) {
        this.fldmz =fldmz1;
    }
    public String getCjsj(){
        return this.cjsj;
    }
    public void setCjsj(String cjsj1) {
        this.cjsj =cjsj1;
    }
    public String getKsbh(){
        return this.ksbh;
    }
    public void setKsbh(String ksbh1) {
        this.ksbh =ksbh1;
    }
    public String getJsbh(){
        return this.jsbh;
    }
    public void setJsbh(String jsbh1) {
        this.jsbh =jsbh1;
    }
    public String getSybj(){
        return this.sybj;
    }
    public void setSybj(String sybj1) {
        this.sybj =sybj1;
    }
    public String getSyxl(){
        return this.syxl;
    }
    public void setSyxl(String syxl1) {
        this.syxl =syxl1;
    }
}
