package com.tmri.share.frm.dao;

import java.util.List;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.SysUser;

public interface GSystemDao {
	public String[] getMenuId(String className, String methodName);
	
	/** 取得该部门的可管理行政区划 */
	public String getDistrictByDepartment(String glbm) throws Exception;
	
	public String getCdmc(String xtlb,String cdbh);
	
	/** 取得交警用户列表 */
	public List<SysUser> getUsers(String glbm) throws Exception;
	/** 取得交警用户封装类 */
	public SysUser getPoliceman(String yhdh) throws Exception;
	/** 取得公安用户列表 */
	public List<SysUser> getPolicemen(String glbm) throws Exception;
	/** 取得公安用户封装类 */
	public SysUser getUser(String yhdh) throws Exception;
	
	/** 取得该部门所在管理部门的可管理行政区划 */	
	public String getDistrictByDepartmentInScope(Department department) throws Exception;
	
	public String getProgramName(String xtlb, String cdbh) throws Exception;
	
	public String getFunctionName(String xtlb, String cdbh, String gnid) throws Exception;
	
	public String getUrl(String xtlb, String cdbh) throws Exception;	
	
	public Function getOneFunction(String xtlb,String cdbh,String gnid);
	
}
