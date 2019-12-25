package com.tmri.framework.bean;

import java.io.Serializable;

/**
 * <p>Title:FRM_SYSUSERµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class SysUserFinger implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String yhdh;
  private String zwtz1;
  private String zwtz2;
  private String zwtz3;
	public String getYhdh(){
		return yhdh;
	}
	public void setYhdh(String yhdh){
		this.yhdh=yhdh;
	}
	public String getZwtz1(){
		return zwtz1;
	}
	public void setZwtz1(String zwtz1){
		this.zwtz1=zwtz1;
	}
	public String getZwtz2(){
		return zwtz2;
	}
	public void setZwtz2(String zwtz2){
		this.zwtz2=zwtz2;
	}
	public String getZwtz3(){
		return zwtz3;
	}
	public void setZwtz3(String zwtz3){
		this.zwtz3=zwtz3;
	}
  
}
