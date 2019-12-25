package com.tmri.rm.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.RoleMenu;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.PageController;

public interface PlsSysuserDao extends FrmDao {
	public List<SysUser> getSysuserList(SysUser user, PageController controller) throws DataAccessException;
	public SysUser getSysuser(String yhdh) throws Exception;
	public SysUser getPlsSysuser(String yhdh) throws Exception;
	public List<Role> getRole(String jsdh) throws Exception;
	public List<Role> getUserRole(SysUser user) throws Exception;
	public List<Role> getUserGrantRole(Role role) throws Exception;
	
	public List<Role> getRoleList() throws Exception;
	
	public int deleteUserRole(String yhdh) throws Exception;
	public int deleteUserGrantRole(String yhdh) throws Exception;
	public List<RoleMenu> getRoleMenus(String jsdh) throws Exception;
	public List<Function> getRoleMenuFunction(RoleMenu r) throws Exception;
	
	public List<SysUser> getPlsSysuserList(SysUser user, PageController controller) throws DataAccessException;
	//≤‚ ‘ ¬ŒÔøÿ÷∆
	public void  throwsEx() throws Exception;
	public void saveuser() throws Exception ;
	
	public List<Department> getPoliceDepartmentList(String sjbm) throws Exception;
}
