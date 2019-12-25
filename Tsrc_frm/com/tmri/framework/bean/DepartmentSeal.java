package com.tmri.framework.bean;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class DepartmentSeal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MultipartFile yztp;
	private String glbm;
	private String yzlx;
	public MultipartFile getYztp() {
		return yztp;
	}
	public void setYztp(MultipartFile yztp) {
		this.yztp = yztp;
	}
	public String getGlbm() {
		return glbm;
	}
	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}
	public String getYzlx() {
		return yzlx;
	}
	public void setYzlx(String yzlx) {
		this.yzlx = yzlx;
	}
}
