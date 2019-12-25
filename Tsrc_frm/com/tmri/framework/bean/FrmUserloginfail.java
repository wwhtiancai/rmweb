package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_USERLOGINFAILµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmUserloginfail implements Serializable{
    private String yhdh;
    private String dlsj;
    private String ipdz;
    private String yhmm;
    private String bz;
    public String getYhdh(){
        return this.yhdh;
    }
    public void setYhdh(String yhdh1) {
        this.yhdh =yhdh1;
    }
    public String getDlsj(){
        return this.dlsj;
    }
    public void setDlsj(String dlsj1) {
        this.dlsj =dlsj1;
    }
    public String getIpdz(){
        return this.ipdz;
    }
    public void setIpdz(String ipdz1) {
        this.ipdz =ipdz1;
    }
    public String getYhmm(){
        return this.yhmm;
    }
    public void setYhmm(String yhmm1) {
        this.yhmm =yhmm1;
    }
    public String getBz(){
        return this.bz;
    }
    public void setBz(String bz1) {
        this.bz =bz1;
    }
}
