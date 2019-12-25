package com.tmri.share.frm.dao;

import java.util.List;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.SysUser;

public interface GSystemDao {
	public String[] getMenuId(String className, String methodName);
	
	/** ȡ�øò��ŵĿɹ����������� */
	public String getDistrictByDepartment(String glbm) throws Exception;
	
	public String getCdmc(String xtlb,String cdbh);
	
	/** ȡ�ý����û��б� */
	public List<SysUser> getUsers(String glbm) throws Exception;
	/** ȡ�ý����û���װ�� */
	public SysUser getPoliceman(String yhdh) throws Exception;
	/** ȡ�ù����û��б� */
	public List<SysUser> getPolicemen(String glbm) throws Exception;
	/** ȡ�ù����û���װ�� */
	public SysUser getUser(String yhdh) throws Exception;
	
	/** ȡ�øò������ڹ����ŵĿɹ����������� */	
	public String getDistrictByDepartmentInScope(Department department) throws Exception;
	
	public String getProgramName(String xtlb, String cdbh) throws Exception;
	
	public String getFunctionName(String xtlb, String cdbh, String gnid) throws Exception;
	
	public String getUrl(String xtlb, String cdbh) throws Exception;	
	
	public Function getOneFunction(String xtlb,String cdbh,String gnid);
	
}
