package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.util.PageController;

public interface RoleManager {
	public Role getRole(String jsdh);
	
	public List getRoleList(Role role) throws Exception ;
	
	public List getRoleListByPagesize(Role frmRole,PageController controller) throws Exception ;
	
	public SysResult saveRole(Role frmRole,Log log) throws Exception;

	public SysResult removeRole(Role frmRole,Log log) throws Exception;
	
	public List getUsergrantroleList(String yhdh) throws Exception ;
	
	/**
	 * zhoujn 20100522 获取角色名称
	 */
	public String getJsmc(String jsdh) throws Exception;
	/**
	 * zhoujn 20100522 获取角色属性
	 */
	public String getJssx(String jssx) throws Exception;	
	
	public List getRoleList(String jsdh)throws Exception;
	
	//获取角色类型
	//获取当用户的自拥有角色，属于则是1否则是3
	public String getJslx(String jsdh,String yhdh) throws Exception;
	
	public SysResult saveUsermenuCancel(String glbm,Role frmRole,Log log) throws Exception;
}
