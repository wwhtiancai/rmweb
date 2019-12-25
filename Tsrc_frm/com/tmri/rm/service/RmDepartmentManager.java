package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;


public interface RmDepartmentManager {
	/** ��ȡ���н��������ŵ��б� */
	public List<Department> getDepartmentList(Department department,RmLog log,PageController controller) throws Exception;
	/** ��ȡ���н��������ŵ��б�(��ע��γ�ȵ�) */
	public List<Department> getDepartmentFullList(Department department,String bzjwd,RmLog log,PageController controller) throws Exception;
	/** ��ȡ���������ŵ���Ϣ */
	public Department getDepartment(String glbm,RmLog log) throws Exception;
	/** ��ȡ���������ŵĿɹ�������������Ϣ */
	public Department getDistrict(String glbm) throws Exception;
	
	/** ��ȡ���������ŵ��б� */
	public List<Department> getPoliceList(Department department,RmLog log,PageController controller) throws Exception;
	
	/** ��ȡ���������ŵ���״��Ϣ */
	public String getPoliceStationTree(Department department) throws Exception;
	/** ��ȡ���������ŵ���Ϣ */
	public Department getPoliceStation(String glbm,RmLog log) throws Exception;
	/** ���棨����Ϊ��PLS_DEPARTMENT�������еĲ�����Ϣ */
	public DbResult saveDepartment(Department bean,RmLog log) throws Exception;
	/** ɾ��������Ϊ��PLS_DEPARTMENT�������еĲ�����Ϣ */
	public DbResult delDepartment(Department bean,RmLog log) throws Exception;
	
	public DepartmentInfo getDepartmentInfo(String glbm) throws Exception;

    public Department getDepartment(String glbm) throws Exception;
}
