package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.FrmNoworkday;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SysInitObj;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.ora.bean.DbResult;

public interface SysParaManager {
	
	public SysResult saveSyspara(SysPara syspara,Log log) throws Exception;
	
	public SysResult saveDepSysparaJb(SysPara syspara,Log log) throws Exception;
	
	public SysResult saveDeppara(SysparaValue sysparavalue,Log log) throws Exception;
	
	public List getFgzrwhList(String qsrq,String jsrq,String ywlb);
	
	public SysResult saveNoworkday(FrmNoworkday noworkday,Log log) throws Exception;
	
	public SysResult setInitNoworkday(FrmNoworkday noworkday,Log log) throws Exception;
	
	public DbResult saveSysInit(SysInitObj sysInitObj) throws Exception;
	
	public List getDepparasShow(String xgjb,String gnlbString);
	
	public List getDepparasShow(String xgjb,String glbm,String gnlbString) throws Exception;
}
