package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;


public interface RmDepartmentManager {
	/** 获取所有交警管理部门的列表 */
	public List<Department> getDepartmentList(Department department,RmLog log,PageController controller) throws Exception;
	/** 获取所有交警管理部门的列表(标注经纬度的) */
	public List<Department> getDepartmentFullList(Department department,String bzjwd,RmLog log,PageController controller) throws Exception;
	/** 获取交警管理部门的信息 */
	public Department getDepartment(String glbm,RmLog log) throws Exception;
	/** 获取交警管理部门的可管理行政区划信息 */
	public Department getDistrict(String glbm) throws Exception;
	
	/** 获取公安管理部门的列表 */
	public List<Department> getPoliceList(Department department,RmLog log,PageController controller) throws Exception;
	
	/** 获取公安管理部门的树状信息 */
	public String getPoliceStationTree(Department department) throws Exception;
	/** 获取公安管理部门的信息 */
	public Department getPoliceStation(String glbm,RmLog log) throws Exception;
	/** 保存（表名为：PLS_DEPARTMENT）对象中的部门信息 */
	public DbResult saveDepartment(Department bean,RmLog log) throws Exception;
	/** 删除（表名为：PLS_DEPARTMENT）对象中的部门信息 */
	public DbResult delDepartment(Department bean,RmLog log) throws Exception;
	
	public DepartmentInfo getDepartmentInfo(String glbm) throws Exception;

    public Department getDepartment(String glbm) throws Exception;
}
