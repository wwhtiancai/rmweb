package com.tmri.rm.dao;

import com.tmri.framework.bean.UserRole;
import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.ora.bean.DbResult;

public interface RmDataObjDao {
	public DbResult setUserRole(UserRole userRole) throws Exception;
	public DbResult setUserGrantrole(UserRole userRole) throws Exception;
	public DbResult setTrafficDepartment(Department bean) throws Exception;
	//public DbResult setTrafficDepartmentInfo(DepartmentInfo bean) throws Exception;
	public DbResult setSysuser(SysUser bean) throws Exception;
	public DbResult setPlsSysuser(SysUser bean) throws Exception;
}
