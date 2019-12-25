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

		// ���������񶼲�����
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
		
		// ��ʼ��frmcode
		initFrmCode();
	}


	/**
	 * ���ؽ���������Ϣ
	 */
	public void initDepartments() throws Exception {
		System.out.println("������Ϣ����.....");
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
		SystemCache.getInstance().putCachServiceList(Constants.MEM_DEPARTMENT, "������Ϣ");
		System.out.println("������Ϣ���ؽ���");
	}

	/**
	 * ���ش󹫰�������Ϣ
	 */
	public void initPlsDepartments() throws Exception {
		System.out.println("�������ֲ�����Ϣ����.....");
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
		SystemCache.getInstance().putCachServiceList(Constants.MEM_POLICESTATION, "�������ֲ�����Ϣ");
		System.out.println("�������ֲ�����Ϣ���ؽ���");
	}

	/**
	 * �˵���Ͷ�Ӧ��ϵ��Ϣ
	 */
	public void initProgramRelation() throws Exception {
		System.out.println("�˵����ϵ��Ϣ����.....");
		// ȡ��ǰϵͳ���
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
		SystemCache.getInstance().putCachServiceList(Constants.MEM_RELATION, "�˵����ϵ��Ϣ");
		System.out.println("�˵����ϵ��Ϣ���ؽ���");
	}

	/**
	 * ����
	 */
	public void initFuntions() throws Exception {
		System.out.println("�˵����ܱ���Ϣ����.....");
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
		SystemCache.getInstance().putCachServiceList(Constants.MEM_FRM_FUNCTION.toLowerCase(), "�˵����ܱ���Ϣ");
		System.out.println("�˵����ܱ���Ϣ���ؽ���");
	}

	public void initSysParaValue() throws Exception {
		System.out.println("���Ų�����Ϣ����.....");
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
		SystemCache.getInstance().putCachServiceList(Constants.MEM_SYSPARAVALUE.toLowerCase(), "���Ų�����Ϣ");
		System.out.println("���Ų�����Ϣ���ؽ���");
	}

	public void initRoaditems() throws Exception {
		System.out.println("��·������Ϣ����.....");
		List<Roaditem> list = this.webCacheDao.getAllRoaditems();
		if (list != null && list.size() > 0) {
			SystemCache.getInstance().remove(Constants.MEM_ROAD);
			SystemCache.getInstance().reg(Constants.MEM_ROAD, list);
		}
		SystemCache.getInstance().putCachServiceList(Constants.MEM_ROAD.toLowerCase(), "��·������Ϣ");
		System.out.println("��·������Ϣ���ؽ���");
	}

	public void initSysParas() throws Exception {
		System.out.println("ϵͳ������Ϣ����.....");
		String key = null;
		List sysparas = this.webCacheDao.getAllSysParas();
		Hashtable tab = new Hashtable();
		SysPara sysPara = null;
		for (int i = 0; i < sysparas.size(); i++) {
			sysPara = (SysPara) sysparas.get(i);
			key = "syspara:" + sysPara.getXtlb() + "_" + sysPara.getGjz();
			tab.put(key, sysPara);

			// xuxd 1212 �����Ƿ�Ϊdebugģʽ
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

		// ����ssl
		SysPara para = (SysPara) tab.get("syspara:00_SSLPORT");
		String tmp = "";
		if (para != null)
			tmp = para.getMrz();
		if (tmp == null || tmp.toLowerCase().equals("null")) {
			tmp = "";
		}

		Constants.SYS_SSLPORT = NetUtil.transSSL(tmp);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_SYSPARA.toLowerCase(), "ϵͳ������Ϣ");
		System.out.println(Constants.SYS_SSLPORT);
		System.out.println("ϵͳ������Ϣ���ؽ���");
	}

	private void initPermitKeys() throws Exception {
		PermitUtil.init(gSysparaCodeService);
		// ȱʡ����
		SystemCache.getInstance().reg(Constants.SYS_SYSTEM_XTLB, PermitUtil.XTLB_CORE);

		String localAzdm = "";
		try {
			SafeMachineInfo machineInfo = null;
			if (null != machineInfo) {
				// ���ð�װ����Ҫ������ǰ�棬����Ĵ����õ��˰�װ����
				PermitUtil.setLocalAzdm(machineInfo.getAzdm());
				localAzdm = machineInfo.getAzdm();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		// ����ϵͳ���
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
		System.out.println("�������Ϣ����.....");
		// �����ڴ洦��
		Hashtable tab = new Hashtable();
		SystemCache.getInstance().remove("code");
		SystemCache.getInstance().reg("code", tab);
		System.out.println("�������Ϣ���ؽ���");
		SystemCache.getInstance().putCachServiceList(MEM_FRM_CODE, "�������Ϣ");

	}
}
