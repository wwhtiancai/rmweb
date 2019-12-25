package com.tmri.share.frm.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.tmri.share.frm.bean.RmLog;


public interface LogService {

	public void saveErrLog(HttpServletRequest request,String className,String gnid,String message);
	
	public RmLog getRmLog(HttpServletRequest request,String className,String gnid,String message,HashMap keyMap) throws Exception;
}
