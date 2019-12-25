package com.tmri.cache.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.Roaditem;
import com.tmri.share.frm.bean.SysparaValue;

public interface WebCacheDao {

	
	public List<Function> getAllFunction() throws SQLException;
	public List<Program> getAllPrograms(String xtlbs) throws SQLException;
	public List<SysparaValue> getAllSysPara_values() throws SQLException;
	public List<Roaditem> getAllRoaditems() throws SQLException;
	public List<Department> getAllPlsDepartments() throws SQLException;
	public List getAllSysParas() throws Exception;
}
