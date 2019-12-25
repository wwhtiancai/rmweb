package com.tmri.rm.service;

import java.util.List;

import com.tmri.framework.bean.Role;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

public interface PlsSysuserManager {
	public List<SysUser> getSysuserList(SysUser user, PageController controller, RmLog log) throws Exception;
	public SysUser getSysuser(SysUser user, String jzlx, RmLog log) throws Exception;
	public DbResult updateSysuser(SysUser user, RmLog log) throws Exception;
	public DbResult updateSysuserPwd(SysUser user, RmLog log) throws Exception;
	public SysUser translateSysUser(SysUser bean) throws Exception;
	public SysUser getSysuser(String yhdh, RmLog log) throws Exception;
	public SysUser getPlsSysuser(String yhdh, RmLog log) throws Exception;
	
	public List<Role> getRole(String jsdh, RmLog log) throws Exception;
	public List<Role> getUserRole(SysUser user, RmLog log) throws Exception;
	public List<Role> getUserGrantRole(Role role) throws Exception;
	
	public List<Role> getRoleList() throws Exception;
	
	public DbResult saveUserrole(SysUser user, String[] kglqx, String[] kczqx, RmLog log, String jzlx) throws Exception;
	
	public DbResult savePlsrole(SysUser user,String[] kczqx) throws Exception;
	
	public List<SysUser> getPlsSysuserList(SysUser user, PageController controller, RmLog log) throws Exception;
	public DbResult savePlsSysuser(SysUser user, RmLog log) throws Exception;
	public DbResult updatePlsSysuser(SysUser user, RmLog log) throws Exception;
	public DbResult updatePlsSysuserPwd(SysUser user, RmLog log) throws Exception;
	
	public List<Department> getPoliceDepartmentList(String sjbm) throws Exception;

	
	public DbResult deletePlsSysuser(SysUser user, RmLog log) throws Exception;
	
	public DbResult unlockSysuser(SysUser user, RmLog log) throws Exception;
}
