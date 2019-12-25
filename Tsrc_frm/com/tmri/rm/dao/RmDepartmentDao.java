package com.tmri.rm.dao;

import java.util.List;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

public interface RmDepartmentDao extends FrmDao {
	/** �����ڴ� */
	public void update();
	/** ��ȡ���н��������ŵ��б� */
	public List<Department> getDepartmentList(Department department,PageController controller) throws Exception;

	/** ��ȡ���������ŵ���Ϣ */
	public Department getDepartment(String glbm) throws Exception;
	/** ��ȡ���������ŵĿɹ�������������Ϣ */
	public String getDistrict(String glbm) throws Exception;
	/** ��ȡ���������ŵ���״��Ϣ */
	public String getPoliceStationTree(Department department) throws Exception;
	/** ��ȡ���������ŵ���Ϣ */
	public Department getPoliceStation(String glbm) throws Exception;
	/** ���湫��������Ϣ������Ϊ��PLS_DEPARTMENT������ */
	public DbResult saveDepartment() throws Exception;
	/** ɾ������������Ϣ������Ϊ��PLS_DEPARTMENT������ */
	public DbResult delDepartment() throws Exception;
	
	public List<Department> getDepartments(Department department,PageController controller, String sqlHead) throws Exception;
	public List<Department> getDepartmentsFull(Department department,String bzjwd,PageController controller, String sqlHead) throws Exception;
	
	public List<Department> getPoliceList(Department department,PageController controller, String sqlHead) throws Exception;
	
	public DepartmentInfo getDepartmentInfo(String glbm) throws Exception;
	
}
