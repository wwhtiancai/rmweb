package com.tmri.framework.bean;
/**
 * zhoujn  ÓÃ»§Ç©Ãû
 */
import java.io.Serializable;
import java.sql.*;

import org.springframework.web.multipart.MultipartFile;

import com.tmri.share.frm.util.StringUtil;


public class SysUserSeal implements Serializable {
	private MultipartFile qmtp;
	private String yhdh;
	public MultipartFile getQmtp() {
		return qmtp;
	}
	public void setQmtp(MultipartFile qmtp) {
		this.qmtp = qmtp;
	}
	public String getYhdh() {
		return yhdh;
	}
	public void setYhdh(String yhdh) {
		this.yhdh = yhdh;
	}
	
	private Blob zp;
	public Blob getZp() {
		return zp;
	}
	public void setZp(Blob zp) {
		this.zp = zp;
	}
	
	private String xh;
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	
	private String msg;
	private long code;
	public String getMsg() {
		return StringUtil.transBlank(msg);
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}


	
	
}
