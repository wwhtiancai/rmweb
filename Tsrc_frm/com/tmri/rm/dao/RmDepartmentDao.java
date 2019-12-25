package com.tmri.rm.dao;

import java.util.List;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

public interface RmDepartmentDao extends FrmDao {
	/** 更新内存 */
	public void update();
	/** 获取所有交警管理部门的列表 */
	public List<Department> getDepartmentList(Department department,PageController controller) throws Exception;

	/** 获取交警管理部门的信息 */
	public Department getDepartment(String glbm) throws Exception;
	/** 获取交警管理部门的可管理行政区划信息 */
	public String getDistrict(String glbm) throws Exception;
	/** 获取公安管理部门的树状信息 */
	public String getPoliceStationTree(Department department) throws Exception;
	/** 获取公安管理部门的信息 */
	public Department getPoliceStation(String glbm) throws Exception;
	/** 保存公安部门信息表（表名为：PLS_DEPARTMENT）对象 */
	public DbResult saveDepartment() throws Exception;
	/** 删除公安部门信息表（表名为：PLS_DEPARTMENT）对象 */
	public DbResult delDepartment() throws Exception;
	
	public List<Department> getDepartments(Department department,PageController controller, String sqlHead) throws Exception;
	public List<Department> getDepartmentsFull(Department department,String bzjwd,PageController controller, String sqlHead) throws Exception;
	
	public List<Department> getPoliceList(Department department,PageController controller, String sqlHead) throws Exception;
	
	public DepartmentInfo getDepartmentInfo(String glbm) throws Exception;
	
}
