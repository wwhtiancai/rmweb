package com.tmri.rm.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.rm.dao.RmDepartmentDao;
import com.tmri.rm.dao.jdbc.RmDataObjDaoJdbc;
import com.tmri.rm.service.RmDepartmentManager;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.dao.CommDao;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.service.impl.BaseServiceImpl;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

//extends RmManagerImpl 

@Service
public class RmDepartmentManagerImpl extends BaseServiceImpl implements
		RmDepartmentManager {
	@Autowired
	private RmDepartmentDao rmDepartmentDao;
	@Autowired
	private GSysparaCodeService gSysparaCodeService;
	@Autowired
	private GDepartmentService gDepartmentService;

	@Autowired
	private RmDataObjDaoJdbc rmDataObjDao;
	@Autowired
	private SLogDao sLogDao;
	@Autowired
	private CommDao commDao;

    @Autowired
    private GBasService gBasService;

	@SuppressWarnings("unchecked")
	public List<Department> getDepartmentList(Department department, RmLog log,
			PageController controller) throws Exception {
		String detphead = this.gDepartmentService
				.getDepartmentHeadSQL(department);
		List<Department> list = this.rmDepartmentDao.getDepartments(department,
				controller, detphead);
		for (Iterator<Department> it = list.iterator(); it.hasNext();) {
			Department bean = (Department) it.next();
			this.translateDepartment(bean);
			bean.setSjbmmc(this.translateParentDepartment(bean.getSjbm()));
		}
		return (List<Department>) sLogDao.saveRmLogForQuery(list, log);
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDepartmentFullList(Department department,String bzjwd,
			RmLog log, PageController controller) throws Exception {
		String detphead = this.gDepartmentService
				.getDepartmentHeadSQL(department);
		List<Department> list = this.rmDepartmentDao.getDepartmentsFull(
				department,bzjwd, controller, detphead);
		for (Iterator<Department> it = list.iterator(); it.hasNext();) {
			Department bean = (Department) it.next();
			this.translateDepartment(bean);
			bean.setSjbmmc(this.translateParentDepartment(bean.getSjbm()));
		}
		return (List<Department>) sLogDao.saveRmLogForQuery(list, log);
	}

	public Department getDepartment(String glbm, RmLog log) throws Exception {
		Department bean = this.rmDepartmentDao.getDepartment(glbm);
		bean.setSjbmmc(this.translateParentDepartment(bean.getSjbm()));
		return (Department) sLogDao.saveRmLogForQuery(
				translateDepartment(bean), log);
	}

    public Department getDepartment(String glbm) throws Exception {
        Department bean = this.rmDepartmentDao.getDepartment(glbm);
        bean.setSjbmmc(this.translateParentDepartment(bean.getSjbm()));
        translateDepartment(bean);
        return bean;
    }

	public Department getDistrict(String glbm) throws Exception {
		String xzqh = this.rmDepartmentDao.getDistrict(glbm);
		Department department = new Department();
		if (xzqh == null || xzqh.length() < 1) {
			department.setGlbm(glbm.substring(0, 6));
            // getXzqhmc-0050
            department.setBmmc(this.gBasService.getXzqhmc(department.getGlbm()));
		} else {
			String[] temps = xzqh.split(",");
			String r = "";
			for (String temp : temps) {
                // getXzqhmc-0050
				//gSysparaCodeService.getCodeValue("00","0033"
                r += "," + gBasService.getXzqhmc(temp);
			}
			if (r.length() > 1)
				r = r.substring(1);
			department.setGlbm(xzqh);
			department.setBmmc(r);
		}
		return department;
	}

	public String getPoliceStationTree(Department department) throws Exception {
		return this.rmDepartmentDao.getPoliceStationTree(department);
	}

	public Department getPoliceStation(String glbm, RmLog log) throws Exception {
		Department bean = this.rmDepartmentDao.getPoliceStation(glbm);
		if (null != bean) {
			// bean.setSjbmmc(this.translateParentPoliceStation(bean.getSjbm()));
			bean.setSjbmmc(this.rmDepartmentDao.getDepartment(bean.getSjbm())
					.getBmmc());
		}
		return (Department) sLogDao.saveRmLogForQuery(
				translatePoliceStation(bean), log);
	}

	/** 保存（表名为：PLS_DEPARTMENT）对象中的部门信息 */
	public DbResult saveDepartment(Department bean, RmLog log) throws Exception {
		DbResult result = null;
		result = this.rmDataObjDao.setOracleData(bean);
		if (result.getCode() == 1) {
			this.sLogDao.setRmLog(log);
			if (result.getCode() == 1) {
				result = this.rmDepartmentDao.saveDepartment();
				if (result.getCode() == 1) {
					this.rmDepartmentDao.update();
				} else {
					if ("exist".equals(result.getMsg())) {
						result.setMsg("该部门先前已被删除，但它仍存在系统数据库中！");
					}
				}
			}
		}
		if (result.getCode() < 0)
			throw new Exception("异常：" + result.getCode() + "，错误："
					+ result.getMsg());
		return result;
	}

	/** 删除（表名为：PLS_DEPARTMENT）对象中的部门信息 */
	public DbResult delDepartment(Department bean, RmLog log) throws Exception {
		DbResult result = null;
		result = this.rmDataObjDao.setOracleData(bean);
		if (result.getCode() == 1) {
			this.sLogDao.setRmLog(log);
			if (result.getCode() == 1) {
				result = this.rmDepartmentDao.delDepartment();
				if (result.getCode() == 1) {
					this.rmDepartmentDao.update();
				} else {
					if ("forbidden".equals(result.getMsg())) {
						result.setMsg("该部门存在下级部门，不能删除。请先处理其下级部门。");
					}
				}
			}
		}
		return result;
	}

	/** 获取公安管理部门的列表 */
	public List<Department> getPoliceList(Department department, RmLog log,
			PageController controller) throws Exception {
		String detphead = this.gDepartmentService
				.getDepartmentHeadSQL(department);
		List<Department> list = this.rmDepartmentDao.getPoliceList(department,
				controller, detphead);
		for (Department bean : list) {
			this.translateDepartment(bean);
			bean.setSjbmmc(this.translateParentDepartment(bean.getSjbm()));
		}
		return list;

	}

	private Department translateDepartment(Department bean) throws Exception {
		if (bean == null) {
			return null;
		} else {
			bean.setBmjbmc(this.gSysparaCodeService.getCodeValue("00", "0065",
					bean.getBmjb()));
			bean.setJzjbmc(this.gSysparaCodeService.getCodeValue("00", "6142",
					bean.getJzjb()));
			bean.setGltzmc(this.gSysparaCodeService.getCodeValue("05", "0144",
					bean.getGltz()));
			bean.setJflymc(this.gSysparaCodeService.getCodeValue("05", "0139",
					bean.getJfly()));
			bean.setYflymc(this.gSysparaCodeService.getCodeValue("05", "0138",
					bean.getYfly()));
			bean.setJrgawmc(this.gSysparaCodeService.getCodeValue("00", "0101",
					bean.getJrgaw()));
			bean.setLsgxmc(this.gSysparaCodeService.getCodeValue("05", "0018",
					bean.getLsgx()));
			bean.setJzjbmc(this.gSysparaCodeService.getCodeValue("63", "0150",
					bean.getJzlx()));
			if ("1".equals(bean.getJlzt())) {
				bean.setJlztmc("正常");
			} else {
				bean.setJlztmc("删除");
			}
			return bean;
		}
	}

	private String translateParentDepartment(String glbm) throws Exception {
		if (glbm == null) {
			return "";
		} else {
			String r = this.gDepartmentService.getDepartmentBmmc(glbm);
			if (r != null && r.length() == 12) {
				if ("010000000000".equals(r)) {
					return "公安部交通管理局";
				} else if (r.substring(2, 12).equals("0000000000")) {
					//gSysparaCodeService.getCodeValue("00","0033"
					return this.gBasService.getXzqhmc(r.substring(0, 6)) + "交警总队";
				} else if (r.substring(4, 10).equals("00000000")) {
					//gSysparaCodeService.getCodeValue("00","0033"
					return this.gBasService.getXzqhmc(r.substring(0, 6)) + "交警支队";
				} else {
					return r;
				}
			} else {
				return r;
			}
		}
	}

	private Department translatePoliceStation(Department bean) throws Exception {
		if (bean == null) {
			return null;
		} else {
			bean.setBmjbmc(this.gSysparaCodeService.getCodeValue("63", "0065",
					bean.getBmjb()));

			/*
			 * bean.setBmjbmc(this.gSysparaCodeService.getCodeValue("00",
			 * "0065", bean .getBmjb()));
			 */

			bean.setJzjbmc(this.gSysparaCodeService.getCodeValue("63", "0150",
					bean.getJzlx()));
			if ("1".equals(bean.getJlzt())) {
				bean.setJlztmc("正常");
			} else {
				bean.setJlztmc("删除");
			}
			return bean;
		}
	}

	private String translateParentPoliceStation(String glbm) throws Exception {
		if (glbm == null) {
			return "";
		} else {
			String r = this.gDepartmentService.getPoliceStation(glbm).getBmmc();
			if (r != null && r.length() == 12) {
				if ("010000000000".equals(r) || "000000000000".equals(r)) {
					return "公安部";
				} else if (r.substring(2, 12).equals("0000000000")) {
					//gSysparaCodeService.getCodeValue("00","0033"
					return this.gBasService.getXzqhmc(r.substring(0, 6))+ "公安厅";
				} else if (r.substring(4, 10).equals("00000000")) {
					//gSysparaCodeService.getCodeValue("00","0033"
					return this.gBasService.getXzqhmc(r.substring(0, 6))+ "公安局";
				} else {
					return r;
				}
			} else {
				return r;
			}
		}
	}

	public DepartmentInfo getDepartmentInfo(String glbm) throws Exception {
		DepartmentInfo departmentInfo = this.rmDepartmentDao.getDepartmentInfo(glbm);
		departmentInfo = this.translateBean(departmentInfo);
		return departmentInfo;
	}
	
	private DepartmentInfo translateBean(DepartmentInfo bean) throws Exception {
        if (bean != null) {
            if (StringUtil.checkBN(bean.getXzqh())) {
                // getXzqhmc-0050
                bean.setXzqhmc(this.gBasService.getXzqhmc(bean.getXzqh()));
            }
            if (StringUtil.checkBN(bean.getSjxzqh())) {
                bean.setSjxzqhmc(this.gBasService.getXzqhmc(bean.getSjxzqh()));
            }
            Department department = this.gDepartmentDao.getDepartment(bean.getGlbm());
            if (department != null) {
                String zdglbm = this.gDepartmentDao.getDldmGlbm(department);
                bean.setDlmc(this.gRoadDao.getRoadValue(bean.getDldm(), zdglbm));
                bean.setGlbmmc(department.getBmmc());
                bean.setLkmc(this.gRoadDao.getRoadSegName(zdglbm, bean.getDldm(), bean.getLkh()));
            } else {
                bean.setDlmc(bean.getDldm());
                bean.setLkmc(bean.getLkh());
            }
            if (StringUtil.checkBN(bean.getDllx())) {
                bean.setDllxmc(gSysparaCodeService.getCodeValue("00", "3124", bean.getDllx()));
            }
            if (StringUtil.checkBN(bean.getGlxzdj())) {
                bean.setGlxzdjmc(gSysparaCodeService.getCodeValue("00", "3116", bean.getGlxzdj()));
            }
        }
        return bean;
    }
}
