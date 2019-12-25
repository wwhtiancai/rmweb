package com.tmri.cache.service.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.cache.dao.WebCacheDao;
import com.tmri.cache.service.WebCacheService;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.cache.service.BaseDataCacheService;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.Roaditem;
import com.tmri.share.frm.bean.SafeMachineInfo;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.DebugLog;
import com.tmri.share.frm.util.NetUtil;
import com.tmri.share.frm.util.PermitUtil;

@Service
public class WebCacheServiceImpl implements WebCacheService {
	public static String MEM_FRM_CODE = "frm_code";
	
	@Autowired
	private GSysparaCodeService gSysparaCodeService;

	@Autowired
	private GDepartmentDao gDepartmentDao;

	@Autowired
	private WebCacheDao webCacheDao;
	
	@Autowired
	private BaseDataCacheService baseDataCacheService;
	
	public void init() throws Exception {
		baseDataCacheService.loadBaseData();
		initPermitKeys();

		// ？？？任务都不加载
		if (SystemCache.getInstance().getRecg_jqmc().equals("")) {
			initFuntions();
			initDepartments();
			initProgramRelation();
		}

		initSysParaValue();
		initPlsDepartments();
		String xtyxms = this.gSysparaCodeService.getParaValue("00", "XTYXMS");
		if (xtyxms.equals("1") || xtyxms.equals("2") || xtyxms.equals("3")) {
			initRoaditems();
		}
		
		// 初始化frmcode
		initFrmCode();
	}


	/**
	 * 加载交警部门信息
	 */
	public void initDepartments() throws Exception {
		System.out.println("部门信息加载.....");
		String key = null;
		String key1 = null;
		List departments = this.gDepartmentDao.getOraDepartments();
		Department department = null;
		Hashtable tab = new Hashtable();
		for (int i = 0; i < departments.size(); i++) {
			department = (Department) departments.get(i);
			key = "department:" + department.getGlbm();
			tab.put(key, department);
			key1 = "department_xj:" + department.getSjbm();
			List list = (List) tab.get(key1);
			if (list == null) {
				list = new Vector();
			}
			list.add(department);
			tab.put(key1, list);
		}
		SystemCache.getInstance().remove(Constants.MEM_DEPARTMENT);
		SystemCache.getInstance().reg(Constants.MEM_DEPARTMENT, tab);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_DEPARTMENT, "部门信息");
		System.out.println("部门信息加载结束");
	}

	/**
	 * 加载大公安部门信息
	 */
	public void initPlsDepartments() throws Exception {
		System.out.println("其他警种部门信息加载.....");
		String key = null;
		String key1 = null;
		List departments = this.webCacheDao.getAllPlsDepartments();
		Department department = null;
		Hashtable tab = new Hashtable();
		for (int i = 0; i < departments.size(); i++) {
			department = (Department) departments.get(i);
			key = "policestation:" + department.getGlbm();
			tab.put(key, department);
			key1 = "policestation_xj:" + department.getSjbm();
			List list = (List) tab.get(key1);
			if (list == null) {
				list = new Vector();
			}
			list.add(department);
			tab.put(key1, list);
		}
		SystemCache.getInstance().remove(Constants.MEM_POLICESTATION);
		SystemCache.getInstance().reg(Constants.MEM_POLICESTATION, tab);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_POLICESTATION, "其他警种部门信息");
		System.out.println("其他警种部门信息加载结束");
	}

	/**
	 * 菜单表和对应关系信息
	 */
	public void initProgramRelation() throws Exception {
		System.out.println("菜单表关系信息加载.....");
		// 取当前系统类别
		String xtlbs = (String) SystemCache.getInstance().getValue(Constants.SYS_SYSTEM_XTLB);
		List<Program> list = this.webCacheDao.getAllPrograms(xtlbs);

		HashMap<String, Program> map = new HashMap<String, Program>();
		if (list != null && list.size() > 0) {
			String s = "";
			String cn = "";
			String mn = "";
			for (Program p : list) {
				if (p.getBz() != null && p.getBz().length() > 0) {
					if (p.getBz().indexOf(";") != -1) {
						String[] array = p.getBz().split(";");
						for (String pp : array) {
							cn = pp.substring(0, pp.indexOf('('));
							mn = pp.substring(pp.indexOf('(') + 1, pp.indexOf(')'));
							if (cn.length() > 0 && mn.length() > 0) {
								String[] ary = mn.split(",");
								for (String t : ary) {
									s = cn + "_" + t;
									map.put(s, p);
								}
							}
						}
					} else {
						cn = p.getBz().substring(0, p.getBz().indexOf("("));
						mn = p.getBz().substring(p.getBz().indexOf("(") + 1, p.getBz().indexOf(")"));
						if (cn.length() > 0 && mn.length() > 0) {
							String[] ary = mn.split(",");
							for (String t : ary) {
								s = cn + "_" + t;
								map.put(s, p);
							}
						}
					}
				}
			}
		}
		SystemCache.getInstance().remove(Constants.MEM_RELATION);
		SystemCache.getInstance().reg(Constants.MEM_RELATION, map);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_RELATION, "菜单表关系信息");
		System.out.println("菜单表关系信息加载结束");
	}

	/**
	 * 功能
	 */
	public void initFuntions() throws Exception {
		System.out.println("菜单功能表信息加载.....");
		String key = null;
		List functions = this.webCacheDao.getAllFunction();
		Hashtable tab = new Hashtable();
		Function function = null;
		for (int i = 0; i < functions.size(); i++) {
			function = (Function) functions.get(i);
			key = "function:" + function.getXtlb() + "-" + function.getGnid();
			tab.put(key, function);
		}
		SystemCache.getInstance().remove(Constants.MEM_FRM_FUNCTION);
		SystemCache.getInstance().reg(Constants.MEM_FRM_FUNCTION, tab);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_FRM_FUNCTION.toLowerCase(), "菜单功能表信息");
		System.out.println("菜单功能表信息加载结束");
	}

	public void initSysParaValue() throws Exception {
		System.out.println("部门参数信息加载.....");
		String key = null;
		Hashtable tab = new Hashtable();
		List sysparas = this.webCacheDao.getAllSysPara_values();
		SysparaValue sysparaValue = null;
		for (int i = 0; i < sysparas.size(); i++) {
			sysparaValue = (SysparaValue) sysparas.get(i);
			key = "sysparavalue:" + sysparaValue.getXtlb() + "_" + sysparaValue.getGlbm() + "_" + sysparaValue.getGjz();
			tab.put(key, sysparaValue.getCsz());
		}
		SystemCache.getInstance().remove(Constants.MEM_SYSPARAVALUE);
		SystemCache.getInstance().reg(Constants.MEM_SYSPARAVALUE, tab);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_SYSPARAVALUE.toLowerCase(), "部门参数信息");
		System.out.println("部门参数信息加载结束");
	}

	public void initRoaditems() throws Exception {
		System.out.println("道路代码信息加载.....");
		List<Roaditem> list = this.webCacheDao.getAllRoaditems();
		if (list != null && list.size() > 0) {
			SystemCache.getInstance().remove(Constants.MEM_ROAD);
			SystemCache.getInstance().reg(Constants.MEM_ROAD, list);
		}
		SystemCache.getInstance().putCachServiceList(Constants.MEM_ROAD.toLowerCase(), "道路代码信息");
		System.out.println("道路代码信息加载结束");
	}

	public void initSysParas() throws Exception {
		System.out.println("系统参数信息加载.....");
		String key = null;
		List sysparas = this.webCacheDao.getAllSysParas();
		Hashtable tab = new Hashtable();
		SysPara sysPara = null;
		for (int i = 0; i < sysparas.size(); i++) {
			sysPara = (SysPara) sysparas.get(i);
			key = "syspara:" + sysPara.getXtlb() + "_" + sysPara.getGjz();
			tab.put(key, sysPara);

			// xuxd 1212 加载是否为debug模式
			if (sysPara.getGjz().equalsIgnoreCase("isdebug")) {
				if (sysPara.getMrz().equals("1")) {
					DebugLog.setDebug(true);
				} else {
					DebugLog.setDebug(false);
				}
			}
		}
		SystemCache.getInstance().remove(Constants.MEM_SYSPARA);
		SystemCache.getInstance().reg(Constants.MEM_SYSPARA, tab);

		// 加载ssl
		SysPara para = (SysPara) tab.get("syspara:00_SSLPORT");
		String tmp = "";
		if (para != null)
			tmp = para.getMrz();
		if (tmp == null || tmp.toLowerCase().equals("null")) {
			tmp = "";
		}

		Constants.SYS_SSLPORT = NetUtil.transSSL(tmp);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_SYSPARA.toLowerCase(), "系统参数信息");
		System.out.println(Constants.SYS_SSLPORT);
		System.out.println("系统参数信息加载结束");
	}

	private void initPermitKeys() throws Exception {
		PermitUtil.init(gSysparaCodeService);
		// 缺省配置
		SystemCache.getInstance().reg(Constants.SYS_SYSTEM_XTLB, PermitUtil.XTLB_CORE);

		String localAzdm = "";
		try {
			SafeMachineInfo machineInfo = null;
			if (null != machineInfo) {
				// 设置安装代码要放在最前面，下面的处理用到了安装代码
				PermitUtil.setLocalAzdm(machineInfo.getAzdm());
				localAzdm = machineInfo.getAzdm();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		// 加载系统类别
		// zhoujn 20140808
		try {
			SafeMachineInfo machineInfo = null;
			if (null != machineInfo) {
				String xtlbs = PermitUtil.XTLB_CORE;
				String system_xtlb = null;
				if (system_xtlb != null && !system_xtlb.equals("")) {
					xtlbs = xtlbs + "," + system_xtlb;
				}
				SystemCache.getInstance().remove(Constants.SYS_SYSTEM_XTLB);
				SystemCache.getInstance().reg(Constants.SYS_SYSTEM_XTLB, xtlbs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getCurrentHostServerName(ServletContext sc) {
		String r = "";
		if (sc != null) {
			String sname = sc.getServerInfo();
			if (sname.toLowerCase().indexOf("tomcat") > -1) {
				r = sname;
			} else if (sname.toLowerCase().indexOf("websphere") > -1) {
				r = (String) sc.getAttribute("com.ibm.websphere.servlet.application.host");
			} else {
				r = sname;
			}
		}
		return r;
	}
	
	
	public void initFrmCode() throws Exception {
		System.out.println("代码表信息加载.....");
		// 加载内存处理
		Hashtable tab = new Hashtable();
		SystemCache.getInstance().remove("code");
		SystemCache.getInstance().reg("code", tab);
		System.out.println("代码表信息加载结束");
		SystemCache.getInstance().putCachServiceList(MEM_FRM_CODE, "代码表信息");

	}
}
