package com.tmri.rm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.RoleMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.rm.dao.PlsSysuserDao;
import com.tmri.rm.dao.jdbc.RmDataObjDaoJdbc;
import com.tmri.rm.service.PlsSysuserManager;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.service.impl.BaseServiceImpl;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;


@Service
public class PlsSysuserManagerImpl extends BaseServiceImpl implements PlsSysuserManager {
	@Autowired
	private PlsSysuserDao plsSysuserDao;
	@Autowired
	private RmDataObjDaoJdbc rmDataObjDao;
	@Autowired
	private GSysparaCodeService gSysparaCodeService;
	@Autowired
	private GDepartmentService gDepartmentService;
	@Autowired
	private SLogDao sLogDao;

	public List<SysUser> getSysuserList(SysUser user,
			PageController controller, RmLog log) throws Exception {
		this.sLogDao.saveRmLog(log);
		List<SysUser> list = null;
		list = this.plsSysuserDao.getSysuserList(user, controller);
		for (SysUser bean : list) {
			this.translateSysUser(bean);
		}
		return list;
	}
	
	public List<Department> getPoliceDepartmentList(String glbmHead) throws Exception {
		return this.plsSysuserDao.getPoliceDepartmentList(glbmHead);
	}


	//	this.plsSysuserDao.saveuser();
//	this.plsSysuserDao.throwsEx();
	public DbResult updateSysuser(SysUser user, RmLog log) throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.updateFrm_Sysuser");
		}
		return dr;
	}
	
	public DbResult unlockSysuser(SysUser user, RmLog log) throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.unlockFrm_Sysuser");
		}
		return dr;
	}	

	public DbResult updateSysuserPwd(SysUser user, RmLog log) throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.resetFrm_Sysuser_Pwd");
		}
		return dr;
	}

	public SysUser getSysuser(SysUser user, String jzlx, RmLog log)
			throws Exception {
		SysUser bean = null;
		if (Constants.JJ.equals(jzlx)) {
			// 交警
			bean = this.getSysuser(user.getYhdh(), log);
			if (null != bean) {
				translateSysUser(bean);
			}
		} else {
			// 公安
			bean = this.getPlsSysuser(user.getYhdh(), log);
			if (null != bean) {
				translatePlsSysuser(bean);
			}
		}
		return bean;
	}

	public SysUser getSysuser(String yhdh, RmLog log) throws Exception {
		SysUser bean = null;
		this.sLogDao.saveRmLog(log);
		bean = this.plsSysuserDao.getSysuser(yhdh);
		return bean;
	}

	public SysUser getPlsSysuser(String yhdh, RmLog log) throws Exception {
		SysUser bean = null;
		this.sLogDao.saveRmLog(log);
		bean = this.plsSysuserDao.getPlsSysuser(yhdh);
		return bean;
	}

	public SysUser translateSysUser(SysUser bean) throws Exception {
		if (bean == null) {
			return null;
		}
		bean.setBmmc(this.gDepartmentService.getDepartmentBmmc(bean.getGlbm()));
		bean.setZtmc(this.gSysparaCodeService.getCodeValue("63", "6100", bean.getZt()));
		bean.setXtglymc(this.gSysparaCodeService.getCodeValue("00", "9105", bean
				.getXtgly()));

		if (StringUtil.checkBN(bean.getMmyxq())) {
			int idx = bean.getMmyxq().indexOf(' ');
			if (idx > 0) {
				bean.setMmyxq(bean.getMmyxq().substring(0, idx));
			}
		}
		if (StringUtil.checkBN(bean.getZhyxq())) {
			int idx = bean.getZhyxq().indexOf(' ');
			if (idx > 0) {
				bean.setZhyxq(bean.getZhyxq().substring(0, idx));
			}
		}
		if (StringUtil.checkBN(bean.getZjdlsj())) {
			int idx = bean.getZjdlsj().indexOf('.');
			if (idx > 0) {
				bean.setZjdlsj(bean.getZjdlsj().substring(0, idx));
			}
		}

		return bean;
	}

	public SysUser translatePlsSysuser(SysUser bean) throws Exception {
		if (bean == null) {
			return null;
		}
		bean.setBmmc(this.gDepartmentService.getPoliceStationForshort(bean.getGlbm()));
		bean.setZtmc(this.gSysparaCodeService.getCodeValue("63", "6100", bean.getZt()));
		bean.setXtglymc(this.gSysparaCodeService.getCodeValue("00", "9105", bean
				.getXtgly()));

		if (StringUtil.checkBN(bean.getZhyxq())) {
			if (bean.getZhyxq().length() > 10) {
				bean.setZhyxq(bean.getZhyxq().substring(0, 10));
			}
		}
		if (StringUtil.checkBN(bean.getMmyxq())) {
			if (bean.getMmyxq().length() > 10) {
				bean.setMmyxq(bean.getMmyxq().substring(0, 10));
			}
		}
		if (StringUtil.checkBN(bean.getZjdlsj())) {
			if (bean.getZjdlsj().length() > 19) {
				bean.setZjdlsj(bean.getZjdlsj().substring(0, 19));
			}
		}
		return bean;
	}

	public List<Role> getRole(String jsdh, RmLog log) throws Exception {
		List<Role> list = null;
		if (null != log) {
			this.sLogDao.saveRmLog(log);
			list = this.plsSysuserDao.getRole(jsdh);
		} else {
			list = this.plsSysuserDao.getRole(jsdh);
		}
		return list;
	}
	
	public List<Role> getRoleList() throws Exception {
		return this.plsSysuserDao.getRoleList();
	}
	
	

	public List<Role> getUserRole(SysUser user, RmLog log) throws Exception {
		List<Role> list = null;
		if (null != log) {
			this.sLogDao.saveRmLog(log);
			list = this.plsSysuserDao.getUserRole(user);
		} else {
			list = this.plsSysuserDao.getUserRole(user);
		}
		return list;
	}

	public List<Role> getUserGrantRole(Role role) throws Exception {
		return this.plsSysuserDao.getUserGrantRole(role);
	}
	
	
	public DbResult savePlsrole(SysUser user, String[] kczqx) throws Exception {
		DbResult dr = null;
		this.plsSysuserDao.deleteUserRole(user.getYhdh());	
		if (null != kczqx && kczqx.length > 0) {
			UserRole userRole = new UserRole();
			userRole.setYhdh(user.getYhdh());
			for (String s : kczqx) {
				userRole.setJsdh(s);
				dr = this.rmDataObjDao.setUserRole(userRole);
				if (dr.getCode() != 1) {
					String msg = dr.getMsg();
					if (!StringUtil.checkBN(msg)) {
						msg = "设置用户可操作权限异常！";
					}
					throw new RuntimeException(msg);
				}
				dr = this.plsSysuserDao.save("RM_PKG.insertPls_Userrole");
				if (dr.getCode() != 1) {
					String msg = dr.getMsg();
					if (!StringUtil.checkBN(msg)) {
						msg = "设置用户权限异常！";
					}
					throw new RuntimeException(msg);
				}
			}
		} else {									// add by lucheng  2014.5.26
			dr = new DbResult();
			dr.setCode(1);
			dr.setMsg("权限删除!");
		}
		
		return dr;
	}


	public DbResult saveUserrole(SysUser user, String[] kglqx, String[] kczqx,
			RmLog log, String jzlx) throws Exception {
		HashMap<String, RoleMenu> validMap = new HashMap<String, RoleMenu>();
		// 判断可控制权限不能为空
		if (kczqx == null || kczqx.length == 0){
			DbResult result = new DbResult();
			result.setCode(0);
			result.setMsg("可操作权限不能为空！");
			return result;
		}
		for (String k : kczqx) {
			List<RoleMenu> l = this.plsSysuserDao.getRoleMenus(k);
			for (RoleMenu r : l) {
				String key = r.getXtlb() + "-" + r.getCdbh();
				RoleMenu cr = validMap.get(key);
				if (cr != null) {
					String[] gs = r.getGnlb().split(",");
					if (gs != null && gs.length > 0) {
						for (String g : gs) {
							if (cr.getGnlb().indexOf(g) < 0) {
								cr.setGnlb(cr.getGnlb() + "," + g);
							}
						}
					}
					validMap.put(key, cr);
				} else {
					validMap.put(key, r);
				}
			}
		}
		RoleMenu bkMenu = validMap.get("63-R111");
		if (bkMenu != null) {
			if (StringUtil.checkBN(bkMenu.getGnlb())) {
				if (bkMenu.getGnlb().indexOf("l011") >= 0) {
					if (bkMenu.getGnlb().indexOf("l013") >= 0
							|| bkMenu.getGnlb().indexOf("l014") >= 0) {
						throw new RuntimeException("请不要将布控申请、审核、审批同时授权给同一个用户！");
					}
				}
				if (bkMenu.getGnlb().indexOf("l013") >= 0
						&& bkMenu.getGnlb().indexOf("l014") >= 0) {
					throw new RuntimeException("请不要将布控审核、审批同时授权给同一个用户！");
				}
			}
		}
		if (validMap.containsKey("61-A010") && validMap.containsKey("61-A011")) {
			throw new RuntimeException("请不要将卡口备案和审核同时授权给同一个用户！");
		}

		this.plsSysuserDao.deleteUserRole(user.getYhdh());
		this.plsSysuserDao.deleteUserGrantRole(user.getYhdh());
		DbResult dr = new DbResult();
		dr.setCode(1);
		dr.setMsg("保存成功");
		if (Constants.YES.equals(user.getXtgly()) && null != kglqx
				&& kglqx.length > 0) {
			UserRole userRole = new UserRole();
			userRole.setYhdh(user.getYhdh());
			for (String s : kglqx) {
				userRole.setJsdh(s);
				dr = this.rmDataObjDao.setUserGrantrole(userRole);
				if (dr.getCode() != 1) {
					String msg = dr.getMsg();
					if (!StringUtil.checkBN(msg)) {
						msg = "设置用户权限异常！";
					}
					throw new RuntimeException(msg);
				}
				if (Constants.JJ.equals(jzlx)) {
					dr = this.plsSysuserDao
							.save("RM_PKG.insertFrm_User_Grantrole");
				} else {
					dr = this.plsSysuserDao
							.save("RM_PKG.insertPls_User_Grantrole");
				}

				if (dr.getCode() != 1) {
					String msg = dr.getMsg();
					if (!StringUtil.checkBN(msg)) {
						msg = "设置用户可管理权限异常！";
					}
					throw new RuntimeException(msg);
				}
			}
		}

		if (null != kczqx && kczqx.length > 0) {
			UserRole userRole = new UserRole();
			userRole.setYhdh(user.getYhdh());
			for (String s : kczqx) {
				userRole.setJsdh(s);
				dr = this.rmDataObjDao.setUserRole(userRole);
				if (dr.getCode() != 1) {
					String msg = dr.getMsg();
					if (!StringUtil.checkBN(msg)) {
						msg = "设置用户可操作权限异常！";
					}
					throw new RuntimeException(msg);
				}
				if (Constants.JJ.equals(jzlx)) {
					dr = this.plsSysuserDao.save("RM_PKG.insertFrm_Userrole");
				} else {
					dr = this.plsSysuserDao.save("RM_PKG.insertPls_Userrole");
				}

				if (dr.getCode() != 1) {
					String msg = dr.getMsg();
					if (!StringUtil.checkBN(msg)) {
						msg = "设置用户权限异常！";
					}
					throw new RuntimeException(msg);
				}
			}
		}
		this.sLogDao.saveRmLog(log);
		if (Constants.JJ.equals(jzlx)) {
			this.rmDataObjDao.setSysuser(user);
			dr = this.plsSysuserDao.save("RM_PKG.updateFrm_Sysuser_xtgly_qxms");
		} else {
			this.rmDataObjDao.setPlsSysuser(user);
			dr = this.plsSysuserDao.save("RM_PKG.updatePls_Sysuser_xtgly_qxms");
		}
		return dr;
	}

	public List<SysUser> getPlsSysuserList(SysUser user,
			PageController controller, RmLog log) throws Exception {
		List<SysUser> list = null;
		this.sLogDao.saveRmLog(log);
		
//		String detphead = this.gDepartmentService.getDepartmentHeadSQL(this.gDepartmentDao.getDepartment(user.getGlbm()));
//		user.setGlbm(detphead);
		list = this.plsSysuserDao.getPlsSysuserList(user, controller);
		for (SysUser bean : list) {
			this.translatePlsSysuser(bean);
		}
		return list;
	}

	public DbResult savePlsSysuser(SysUser user, RmLog log) throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setPlsSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.savePls_Sysuser");
		}
		return dr;
	}

	public DbResult updatePlsSysuser(SysUser user, RmLog log) throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setPlsSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.updatePls_Sysuser");
		}
		return dr;
	}

	public DbResult updatePlsSysuserPwd(SysUser user, RmLog log)
			throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setPlsSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.resetPls_Sysuser_Pwd");
		}
		return dr;
	}

	public DbResult deletePlsSysuser(SysUser user, RmLog log) throws Exception {
		DbResult dr = null;
		this.sLogDao.saveRmLog(log);
		dr = this.rmDataObjDao.setPlsSysuser(user);
		if (dr != null && dr.getCode() == 1) {
			dr = this.plsSysuserDao.save("RM_PKG.delPls_Sysuser");
		}
		return dr;
	}
}
