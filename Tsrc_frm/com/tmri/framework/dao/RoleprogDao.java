package com.tmri.framework.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.FrmUserprog;



public interface RoleprogDao {
	public List getFunctionList(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFunctionListByJsdh(FrmUserprog frmuserprog) throws DataAccessException ;
	public List getFunctionListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFoldList(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFoldListByJsdh(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	public List getProgramList(FrmUserprog frmuserprog) throws DataAccessException;
	public List getProgramListByJsdh2(FrmUserprog frmuserprog) throws DataAccessException;
	public List getProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	
	public List getProgramListByJsdh(String jsdh) throws DataAccessException;
	public List getUserMenu(String yhdh) throws DataAccessException;
	//zhoujn 20100524 获取维护用户重叠角色代号
	public List getRoleList(String yhdh,String whyh) throws DataAccessException;
	
	public String getXtlbmc(String xtlb);
	public String getMlmc(String mlbh);
	
	//自由角色，功能id
	public List getFunctionListByYhdh(String yhdh) throws DataAccessException;
	//根据自由权限获取用户的fold
	public List getFoldListByYhdh(String yhdh) throws DataAccessException;
	//自由权限
	public List getProgramListByYhdh(String yhdh) throws DataAccessException;
	
	public List getUserDeskmenu(String yhdh)throws DataAccessException;
	
	
	//获取用户操作权限
	public List getUserProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	//获取用户操作权限
	public List getUserFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException ;
	
	//根据foldlist构造一级目录,获取所有的目录结构
	public List getAllfoldlist(List foldlist);
	public List getChildfoldlist(List foldlist,String sjmldh) ;
}
