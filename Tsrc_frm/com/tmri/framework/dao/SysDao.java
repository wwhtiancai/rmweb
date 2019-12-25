package com.tmri.framework.dao;

import java.util.List;

import com.tmri.framework.bean.SysResult;

public interface SysDao{

	public SysResult saveFrmErrLog() throws Exception;
	public SysResult saveFrmSysrun() throws Exception;
	public SysResult saveFrmQueryerrlog() throws Exception;
	public SysResult saveFrmRbspLog() throws Exception;
	// 根据SQL语句返回List的方法。
	public List queryForList(String strSql,Class bClass);
}