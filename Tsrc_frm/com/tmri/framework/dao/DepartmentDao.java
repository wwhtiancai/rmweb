package com.tmri.framework.dao;

import java.util.List;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.ora.bean.DbResult;

public interface DepartmentDao{
	
	public DbResult savekeyWorkConfig(java.util.Map map);
	public void do_program_jyw();
	public List getCheckboxStateList();
	
	public String getDepartmentTreeStr(Department department);

}
