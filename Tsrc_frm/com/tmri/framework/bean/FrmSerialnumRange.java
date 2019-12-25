package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_SERIALNUM_RANGEµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmSerialnumRange implements Serializable{
    private String fzjg;
    private String zjbhlx;
    private String qsbh;
    private String jsbh;
    public String getFzjg(){
        return this.fzjg;
    }
    public void setFzjg(String fzjg1) {
        this.fzjg =fzjg1;
    }
    public String getZjbhlx(){
        return this.zjbhlx;
    }
    public void setZjbhlx(String zjbhlx1) {
        this.zjbhlx =zjbhlx1;
    }
    public String getQsbh(){
        return this.qsbh;
    }
    public void setQsbh(String qsbh1) {
        this.qsbh =qsbh1;
    }
    public String getJsbh(){
        return this.jsbh;
    }
    public void setJsbh(String jsbh1) {
        this.jsbh =jsbh1;
    }
}
