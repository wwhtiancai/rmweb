package com.tmri.framework.dao;

import java.util.List;

import com.tmri.framework.bean.SysInitObj;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.ora.bean.DbResult;

public interface SysParaDao  extends FrmDao{
	
	public List getDepparasShow(String xgjb,String gnlbString);	
	
	public SysResult saveSysPara();
	
	public SysResult saveDepPara();
	public SysResult saveDepParaNolog();
	
	public SysResult saveDepParaglxzqh();
	
	public void setSysParaToMem(SysPara sysPara);
	
	public void setSysParaValueToMem(SysparaValue sysparavalue);
		
	public List getFgzrwhList(String qsrq,String jsrq,String ywlb);
	
	public DbResult doSysInit(SysInitObj sysInitObj) throws Exception;
	
}
