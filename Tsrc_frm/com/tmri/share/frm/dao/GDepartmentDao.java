package com.tmri.share.frm.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.share.frm.bean.Department;


public interface GDepartmentDao {

	/**
	 * 获取单个管理部门
	 * @param glbm
	 * @return
	 */
	public Department getDepartment(String glbm);
	
	public String getDepartmentBmmc(String glbm) throws Exception;
	
	public String getFzjgByglbm(String glbm) throws Exception;
	
	public List<Department> getXjDepartmentBySjbm(String glbm) throws Exception;
	
	public List<Department> getAllXjDepartmentBySjbm(String glbm) throws Exception;
	
	/**
	 * 获取科室的上级管理部门代码
	 * @param dept
	 * @return
	 */
	public String getOfficeSjbm(Department dept);
	
	public String getDldmGlbm(Department dept);
	
	public String getZdGlbm(Department dept);
	
	public String getGlbmByFzjg(String fzjg);
	
	/**
	 * 返回管理部门头
	 */
	public String getDepartmentHeadSQL(Department department) throws Exception;

    public String getGlbmByHeadSQL(String glbmHead) throws Exception;
	public List<Department> getPoliceStationList(String glbm) throws Exception;

	public Department getPoliceStation(String glbm) throws Exception;
	public String getPoliceStationForshort(String glbm) throws Exception;
	public String getPoliceStationValue(String glbm) throws Exception;
	
	public List<Department> getOraDepartments() throws SQLException;
	
	public String getDepartmentSTDBmmc(String glbm) throws Exception;
	
}
