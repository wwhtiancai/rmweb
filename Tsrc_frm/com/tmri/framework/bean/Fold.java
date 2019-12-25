package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

/**
 * <p>
 * Title:FRM_FOLDµÄ³Ö¾ÃÀà
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author long
 * @version 1.0
 */
public class Fold implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mldh;
	private String mlmc;
	private String sjmldh;
	private String sxh;
	private String bz;
	private String level;
	private String mllb;
	
	public String getMllb() {
		return mllb;
	}

	public void setMllb(String mllb) {
		this.mllb = mllb;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	private List proList;

	public List getProList() {
		return proList;
	}

	public void setProList(List proList) {
		this.proList = proList;
	}

	public String getMldh() {
		return this.mldh;
	}

	public void setMldh(String mldh1) {
		this.mldh = mldh1;
	}

	public String getMlmc() {
		return this.mlmc;
	}

	public void setMlmc(String mlmc1) {
		this.mlmc = mlmc1;
	}

	public String getSjmldh() {
		return this.sjmldh;
	}

	public void setSjmldh(String sjmldh1) {
		this.sjmldh = sjmldh1;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz1) {
		this.bz = bz1;
	}

	public String getSxh() {
		return sxh;
	}

	public void setSxh(String sxh) {
		this.sxh = sxh;
	}
}
