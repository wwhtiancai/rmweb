package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.util.PageController;

public interface RoleDao {
	
	public List getRoleList(Role frmRole) throws SQLException;
	public SysResult saveRole(Role role,List rolemenuList);
	public List getRoleListByPagesize(Role frmRole,PageController controller) throws Exception ;
	public SysResult removeRole();
	public List getUsergrantroleList(String yhdh) throws Exception ;
	
	//zhoujn 20100522 获取角色名称
	public Role getRole(String jsdh) throws DataAccessException;
	
	//根据角色代号获取所有角色
	public List getRoleList(String jsdh) throws Exception;
	
	//获取其子角色
	public List getChildRoleList(String jsdh);
	
	//获取角色类型
	//获取当用户的自拥有角色，属于则是1否则是3
	public String getJslx(String jsdh,String yhdh) throws Exception;
	
	public SysResult saveUsermenuCancel(String glbm,List rolemenuList)throws Exception;
}
