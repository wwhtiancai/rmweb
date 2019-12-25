package com.tmri.framework.service;

import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.FrmUserprog;

public interface RoleprogManager {
	public List getFunctionList(FrmUserprog frmuserprog);
	public List getFunctionListByJsdh(FrmUserprog frmuserprog);
	public List getFunctionListByYhdh(FrmUserprog frmuserprog);
	
	public List getFoldList(FrmUserprog frmuserprog);
	public List getFoldListByJsdh(FrmUserprog frmuserprog);
	public List getFoldListByYhdh(FrmUserprog frmuserprog);
	
	
	public List getProgramList(FrmUserprog frmuserprog);
	public List getProgramListByJsdh2(FrmUserprog frmuserprog);
	public List getProgramListByYhdh(FrmUserprog frmuserprog);
	
	public List getProgramListByJsdh(String jsdh);
	
	/**
	 * 以属性方式合并菜单列表、功能列表到统一的目录列表中
	 * @param foldList 目录列表
	 * @param proList  菜单列表
	 * @param funList  功能列表
	 * @return
	 */
	public List unionFoldList(List foldList,List proList,List funList);
	
	//获取用户菜单
	//zhoujn 20100524
	public List getUserMenuListByYhdh(String yhdh);

	//zhoujn 20100524 获取维护用户重叠角色代号
	public List getRoleList(String yhdh,String whyh);	
	public Hashtable getXtlbUnionFoldList(List foldList,List proList,List funList);
	//根据目录构造信息
	public Hashtable getUnionFoldList(List foldList,List proList,List funList);
	
	public String getoutcdbh(Hashtable foldHt,List roleMenuList,String qxms,int irowsize);
	
	
	//获取用户操作权限
	public List getUserProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	//获取用户操作权限
	public List getUserFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException ;
	
	
	public List getFunctionListByYhdh(String jsdh);
	public List getProgramListByYhdh(String yhdh);
	public List getFoldListByYhdh(String yhdh);
	
	//排除功能参与构造
	public Hashtable getUnionFoldList(List foldList,List proList);
	//20110223用于首页面设置快捷菜单
	public String getDeskoutcdbh(Hashtable foldHt,List roleMenuList,String qxms,int irowsize);
	
	public List getUserDeskmenu(String yhdh)throws Exception;
}
