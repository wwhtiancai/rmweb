package com.tmri.share.frm.service;

import java.util.List;

import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;


public interface GDepartmentService {


	public Department getDepartment(String glbm) throws Exception ;

	public String getDepartmentBmmc(String glbm) throws Exception ;
	
	public String getFzjgByglbm(String glbm) throws Exception;

	public List<Department> getXjDepartments(String glbm, boolean includeself) throws Exception ;

	/**
	 * 判断管理部门是否为总队、支队、大队 不列出科室
	 * 
	 * @param glbm
	 * @param i
	 * @return
	 */
	public boolean checkNotOffice(String glbm, int i) ;

	public String getOfficeSjbm(Department dept) throws Exception ;

	public String getDldmGlbm(Department dept) throws Exception ;

	public String getZdGlbm(Department dept) throws Exception ;

	public String getDepartmentHeadSQL(Department department) throws Exception ;
	
	public String getGlbmByFzjg(String fzjg);

	// 获取下级所有部门
	public List<Department> getAllXjDepartments(String glbm, boolean includeself)
			throws Exception ;
	
    public Department getSjDepartment(String glbm) throws Exception;

    public List<Department> getAllXjDepartments(String glbm,
            boolean includeself, boolean showoffice)
            throws Exception ;
	
	public String getDepartmentName(String glbm) throws Exception ;
	
	public String getDepartmentYzmc(String glbm) throws Exception;

	public List<Department> getPoliceDepartment(String glbm,boolean includeself) throws Exception;
	public Department getPoliceStation(String glbm) throws Exception;
	public String getPoliceStationForshort(String glbm) throws Exception;
	public String getPolieStationValue(String glbm) throws Exception;
	
    public String getDepartmentSTDBmmc(String glbm);

    public String getGlbmByHeadSQL(String glbmHead) throws Exception;

    public String getBmmcByHeadSQL(String glbmHead) throws Exception;
	public List<Code> getDistrict(String glbm) throws Exception;
	
    public String getXzqhSql(String glbm) throws Exception;

    public String getXzqhSql(String glbm, String table) throws Exception;

}
