package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_NOWORKDAYµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmNoworkday implements Serializable{
    private String sdate;
    private String bj;
    private String ywlb;
    public String getSdate(){
        return this.sdate;
    }
    public void setSdate(String sdate1) {
        this.sdate =sdate1;
    }
    public String getBj(){
        return this.bj;
    }
    public void setBj(String bj1) {
        this.bj =bj1;
    }
    public String getYwlb(){
        return this.ywlb;
    }
    public void setYwlb(String ywlb1) {
        this.ywlb =ywlb1;
    }
}
