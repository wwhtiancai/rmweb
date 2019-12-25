package com.tmri.share.frm.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.share.frm.bean.Department;


public interface GDepartmentDao {

	/**
	 * ��ȡ����������
	 * @param glbm
	 * @return
	 */
	public Department getDepartment(String glbm);
	
	public String getDepartmentBmmc(String glbm) throws Exception;
	
	public String getFzjgByglbm(String glbm) throws Exception;
	
	public List<Department> getXjDepartmentBySjbm(String glbm) throws Exception;
	
	public List<Department> getAllXjDepartmentBySjbm(String glbm) throws Exception;
	
	/**
	 * ��ȡ���ҵ��ϼ������Ŵ���
	 * @param dept
	 * @return
	 */
	public String getOfficeSjbm(Department dept);
	
	public String getDldmGlbm(Department dept);
	
	public String getZdGlbm(Department dept);
	
	public String getGlbmByFzjg(String fzjg);
	
	/**
	 * ���ع�����ͷ
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
