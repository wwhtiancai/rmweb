package com.tmri.framework.service;

import java.util.List;
import java.util.Map;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.ora.bean.DbResult;

public interface DepartmentManager{

	public Department getDepartment(String glbm) throws Exception;
	public DbResult savekeyWorkConfig(List list);
	public Map getCheckboxSate();
	
	
	public String getDepartmentTreeStr(Department department);

	int create(Department department);

	int delete(String glbm);

}
