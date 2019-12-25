package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_USERROLEµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class UserRole implements Serializable{
    private String yhdh;
    private String jsdh;
    public String getYhdh(){
        return this.yhdh;
    }
    public void setYhdh(String yhdh1) {
        this.yhdh =yhdh1;
    }
    public String getJsdh(){
        return this.jsdh;
    }
    public void setJsdh(String jsdh1) {
        this.jsdh =jsdh1;
    }
}
