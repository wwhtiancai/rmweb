package com.tmri.rm.ctrl;

import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tmri.framework.service.DepartmentManager;
import com.tmri.framework.service.SysuserManager;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.rm.service.RmDepartmentManager;
import com.tmri.share.frm.util.BeanUtil;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.DebugLog;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.MenuConstant;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

@Controller
@RequestMapping("/department.vmc")
public class RmDepartmentCtrl extends RmCtrl {

	public static final Logger LOG = LoggerFactory.getLogger(RmDepartmentCtrl.class);
	
	private final String XTLB = "00";

    private final String CDBH = "K010";
    
    private final String GNID_JJ = "l921"; // 交警
    
    private final String GNID_GA = "l922"; // 公安

	private final String GNID_QY = "l923"; // 企业

	@Autowired
	private RmDepartmentManager rmDepartmentManager;

	@Autowired
	private DepartmentManager departmentManager;

	@Autowired
	private SysuserManager sysuserManager;

	@RequestMapping(params = "method=queryDepartment")
	@SuppressWarnings("unchecked")
	public ModelAndView queryDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH));
			
			List<Function> funcList = this.gSystemService.getUserCxdhGnlb(
					request.getSession(), "00", MenuConstant.M_DepartmentCtrl);
			request.setAttribute("gnid", funcList);
			if (funcList == null) {
				request.setAttribute("gnidsize", 0);
			} else {
				request.setAttribute("gnidsize", funcList.size());
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "",
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", "framework/departmentMain");
	}

	@RequestMapping(params = "method=queryTraffic")
	public ModelAndView queryTraffic(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(),MenuConstant.F_DepartmentCtrl_Traffic);
			request.setAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_JJ));
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			List<Department> list = this.gDepartmentService.getXjDepartments(userSession.getDepartment().getGlbm(), true);
			request.setAttribute("glbm", list);
			gSystemService.doEndCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic, MenuConstant.P_open);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic,
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", "framework/trafficDepartmentMain");
	}

	@RequestMapping(params = "method=listTraffic")
	public ModelAndView listTraffic(HttpServletRequest request,
			HttpServletResponse response, Department department) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic);
			
			request.setAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_JJ));

			String bzjwd = request.getParameter("bzjwd");
			String wbzjwd = request.getParameter("wbzjwd");
			//0未标记1标注2全部
			String jwd_bj="2";
			if ("1".equals(bzjwd)){
				if("1".equals(wbzjwd)){
					jwd_bj = "2";
				}else{
					jwd_bj = "1";
				}
			}else if ("1".equals(wbzjwd)){
				jwd_bj = "0";
			}

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			List<Department> list = this.gDepartmentService.getXjDepartments(
					userSession.getDepartment().getGlbm(), true);
			request.setAttribute("glbm", list);
			Department bean = new Department();
			if (department.getGlbm() == null || department.getGlbm().equals("")) {
				department.setGlbm(userSession.getDepartment().getGlbm());
				department.setBmjb(userSession.getDepartment().getBmjb());
			} else {
				Department dept = this.gDepartmentService
						.getDepartment(department.getGlbm());
				department.setBmjb(dept.getBmjb());
			}

			bean.setGlbm(department.getGlbm());
			bean.setBmmc(department.getBmmc());
			request.setAttribute("department", bean);
			PageController controller = new PageController(request);
			List<Department> queryList = null;
			
			if ("1".equals(jwd_bj) || "0".equals(jwd_bj)) {
				queryList = this.rmDepartmentManager.getDepartmentFullList(
						department,jwd_bj,logService.getRmLog(request, getClass()
								.getName(),	MenuConstant.F_DepartmentCtrl_Traffic, null,
								null), controller);
			} else {
				queryList = this.rmDepartmentManager.getDepartmentList(
						department, logService.getRmLog(request, getClass()
								.getName(),MenuConstant.F_DepartmentCtrl_Traffic, null,
								null), controller);
			}
			
			//补充信息
			for (Department dept : queryList) {
				DepartmentInfo info = rmDepartmentManager
						.getDepartmentInfo(dept.getGlbm());
				if (info != null) {
					dept.setFzr(info.getFzr());
					dept.setLxr(info.getLxr());
					dept.setLxdh(info.getLxdh());
					dept.setCzhm(info.getCzhm());
					dept.setLxdz(info.getLxdz());
					dept.setJd(info.getJd());
					dept.setWd(info.getWd());
				}
			}
			gSystemService.setCheck(request, rmDepartmentManager.getClass()
					.getName(), queryList, "glbm");
			request.setAttribute("bzjwd", bzjwd);
			request.setAttribute("wbzjwd", wbzjwd);
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", controller);
			gSystemService.doEndCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic, MenuConstant.P_list);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic,
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", "framework/trafficDepartmentMain");
	}

	@RequestMapping(params = "method=detailTraffic")
	public ModelAndView detailTraffic(HttpServletRequest request,
			HttpServletResponse response) {
		String mav = "framework/trafficDepartmentEdit";
		try {
			gSystemService.doBeginCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic);
			request.setAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_JJ));
			
			String glbm = request.getParameter("glbm");
			if (glbm == null || glbm.length() < 1) {
				CommonResponse.setAlerts(CommonResponse.ErrorPost, request);
				return redirectMav("jsp_core/vmc", mav);
			}
			gSystemService.getCheck(request, rmDepartmentManager.getClass()
					.getName(), glbm);
			HashMap<String, String> keyMap = new HashMap<String, String>();
			keyMap.put("zjxx", glbm);
			Department department = this.rmDepartmentManager.getDepartment(
					glbm, this.logService.getRmLog(request, getClass()
							.getName(), MenuConstant.F_DepartmentCtrl_Traffic,
							null, keyMap));
			String edit = "";
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			if (userSession.getDepartment().getBmjb().equals(department.getBmjb())){
				edit = "1";
			}
			request.setAttribute("edit", edit);
			
			DepartmentInfo departmentInfo = new DepartmentInfo();
			BeanUtil.beanValueCopy(department, departmentInfo);
			
			DepartmentInfo info=this.rmDepartmentManager.getDepartmentInfo(glbm);
			if (info != null) {
				departmentInfo.setGlbm(info.getGlbm());
				departmentInfo.setFzr(info.getFzr());
				departmentInfo.setLxr(info.getLxr());
				departmentInfo.setLxdh(info.getLxdh());
				departmentInfo.setCzhm(info.getCzhm());
				departmentInfo.setLxdz(info.getLxdz());
				departmentInfo.setJd(info.getJd());
				departmentInfo.setWd(info.getWd());
				departmentInfo.setXzqh(info.getXzqh());
				departmentInfo.setDllx(info.getDllx());
				departmentInfo.setGlxzdj(info.getGlxzdj());
				departmentInfo.setDldm(info.getDldm());
				departmentInfo.setLkh(info.getLkh());
				departmentInfo.setDlms(info.getDlms());
                departmentInfo.setSjxzqh(info.getSjxzqh());
				
				departmentInfo.setDlmc(info.getDlmc());
				departmentInfo.setGlbmmc(info.getGlbmmc());
				departmentInfo.setLkmc(info.getLkmc());
				departmentInfo.setDllxmc(info.getDllxmc());
				departmentInfo.setGlxzdjmc(info.getGlxzdjmc());
                departmentInfo.setSjxzqhmc(info.getSjxzqhmc());
					
			}
			
			Department xzqh = this.rmDepartmentManager.getDistrict(department
					.getGlbm());
			request.setAttribute("bean", departmentInfo);
			request.setAttribute("xzqh", xzqh);

			//获取本地可管理行政区划
			String glbmhead="";
			if(department.getBmjb().equals("1")){
				glbmhead="";
			}else if(department.getBmjb().equals("2")){
				glbmhead=department.getGlbm().substring(0,2);
			}else {
				glbmhead=FuncUtil.getGlbmhead(glbm);
			}

			gSystemService.doEndCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic,
					MenuConstant.P_detail);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic,
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", mav);
	}

	@RequestMapping(params = "method=updateTraffic")
	public ModelAndView updateTraffic(HttpServletRequest request,
			HttpServletResponse response, DepartmentInfo departmentinfo) {
		ModelAndView view = null;
		try {
			gSystemService.doBeginCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic);
			RmLog log = this.logService.getRmLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic, null, null);
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			log.setGlbm(userSession.getDepartment().getGlbm());
			log.setYhdh(userSession.getSysuser().getYhdh());
			DebugLog.debug("更新部门行政区划，log日志的管理部门：" + log.getGlbm());

			DbResult result = new DbResult();
			result.setCode(1);
			view = CommonResponse.JSON(result);
			gSystemService.doEndCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic, MenuConstant.P_save);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Traffic,
					FuncUtil.transException(e));
			view = CommonResponse.JSON(e);
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(params = "method=queryPolice")
	public ModelAndView queryPolice(HttpServletRequest request,
			HttpServletResponse response) {
		return redirectMav("jsp_core/vmc", "framework/policeDepartmentMain");
	}

	@RequestMapping(params = "method=listPolice")
	public ModelAndView listPolices(HttpServletRequest request,
			HttpServletResponse response, Department department) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police);
			
			request.setAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_GA));

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			department.setGlbm(userSession.getDepartment().getGlbm());
			department.setBmjb(userSession.getDepartment().getBmjb());
			request.setAttribute("bmmc", department.getBmmc());
			PageController controller = new PageController(request);
			List<Department> queryList = this.rmDepartmentManager
					.getPoliceList(department, logService.getRmLog(request,
							getClass().getName(),
							MenuConstant.F_DepartmentCtrl_Police, null, null),
							controller);

			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", controller);
			gSystemService.doEndCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police, MenuConstant.P_list);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police,
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", "framework/policeDepartmentMain");
	}

	/**
	 * 新建公安用户部门
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=addPolice")
	public ModelAndView addPolice(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");
			
			request.setAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_GA));
			
			// request.setAttribute("jzlx",
			// this.gSysparaCodeService.getCodes("63","0150"));
			List<Code> tmpList = this.gSysparaCodeService
					.getCodes("63", "0150");
			List<Code> jzlist = new ArrayList<Code>();
			if (null != tmpList) {
				for (Code code : tmpList) {
					jzlist.add(code);
				}
			}
			Iterator<Code> it = jzlist.iterator();
			Code cd = null;
			while (it.hasNext()) {
				cd = (Code) it.next();
				if (cd.getDmz().equals("17")) {
					it.remove();
				}
			}
			request.setAttribute("jzlx", jzlist);

			String xtyxms = this.gSysparaCodeService.getSysParaValue("00",
					"XTYXMS");
			List<Department> list = new Vector<Department>();
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

			// 20140702 调整一下，改为支队可以建全部部门信息
			if ("4".equals(xtyxms)) {
				list.add(this.gDepartmentService.getDepartment("010000000000"));
			} else if ("3".equals(xtyxms)) {
				String zdbm = this.gDepartmentService.getZdGlbm(userSession
						.getDepartment());
				list.add(this.gDepartmentService.getDepartment(zdbm));
			} else if ("2".equals(xtyxms)) {
				Department d = userSession.getDepartment();
				if (d.getBmjb().equals("2") || d.getBmjb().equals("3")) {
					list = this.gDepartmentService.getXjDepartments(
							d.getGlbm(), true);
				} else if (d.getBmjb().equals("4")) {
					String zdbm = this.gDepartmentService.getOfficeSjbm(d);
					list.add(this.gDepartmentService.getDepartment(zdbm));
				} else {
					throw new Exception("大队级别用户可以创建部门!");
				}
			} else if ("1".equals(xtyxms)) {
				Department d = userSession.getDepartment();
				if (d.getBmjb().equals("3")) {
					list = this.gDepartmentService.getXjDepartments(
							d.getGlbm(), true);
				} else if (d.getBmjb().equals("4")) {
					String zdbm = this.gDepartmentService.getOfficeSjbm(d);
					list.add(this.gDepartmentService.getDepartment(zdbm));
				} else {
					throw new Exception("大队级别用户可以创建部门!");
				}
			} else {
				throw new Exception("系统运行模式未指定!");
			}

			request.setAttribute("bmlist", list);

			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_open);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "",
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", "framework/policeEdit");
	}

	@RequestMapping(params = "method=savePolice")
	public ModelAndView savePolice(HttpServletRequest request,
			HttpServletResponse response, Department command) {
		ModelAndView view = null;
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");

			String logs = "";
			logs = "保存";
			logs += ",管理部门:" + command.getGlbm();
			logs += ",部门名称:" + command.getBmmc();
			logs += ",警种:" + command.getJzlx();
			DbResult result = this.rmDepartmentManager.saveDepartment(command,
					this.logService.getRmLog(request, getClass().getName(),
							MenuConstant.F_DepartmentCtrl_Police, logs, null));

			view = CommonResponse.JSON(result);
			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_save);
		} catch (Exception e) {
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		return view;
	}

	@RequestMapping(params = "method=detailPolice")
	public ModelAndView detailPolice(HttpServletRequest request,
			HttpServletResponse response) {
		String mav = "framework/policeDetail";
		try {
			gSystemService.doBeginCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police);
			request.setAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_GA));
			
			request.setAttribute("jzlx",
					this.gSysparaCodeService.getCodes("63", "0150"));
			String glbm = request.getParameter("glbm");
			if (glbm == null || glbm.length() < 1) {
				CommonResponse.setAlerts(CommonResponse.ErrorPost, request);
				return redirectMav("jsp_core/vmc", mav);
			}
			Department department = this.rmDepartmentManager.getPoliceStation(
					glbm, this.logService.getRmLog(request, getClass()
							.getName(), MenuConstant.F_DepartmentCtrl_Police,
							null, null));
			request.setAttribute("bean", department);
			gSystemService
					.doEndCall(request, getClass().getName(),
							MenuConstant.F_DepartmentCtrl_Police,
							MenuConstant.P_detail);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police,
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", mav);
	}

	@RequestMapping(params = "method=syncGetBmjb")
	@ResponseBody
	public String syncGetBmjb(@RequestParam(value = "glbm") String glbm) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Department department = this.gDepartmentService.getDepartment(glbm);
			String sjbmjb = department.getBmjb();
			String sjjbmc = this.gSysparaCodeService.getCodeValue("00", "0065", sjbmjb);
			String bmjb = String.valueOf(Integer.valueOf(sjbmjb) + 1);
			String jbmc = this.gSysparaCodeService.getCodeValue("00", "0065", bmjb);
			String headNo = this.gDepartmentService.getDepartmentHeadSQL(department);
			int count = this.gDepartmentService.getXjDepartments(glbm, false).size() + 1;
			resultMap.put("sjjbmc", URLEncoder.encode(sjjbmc, "UTF-8"));
			resultMap.put("bmjb", bmjb);
			resultMap.put("jbmc", URLEncoder.encode(jbmc, "UTF-8"));
			resultMap.put("bmdm", EriUtil.appendZero(headNo + EriUtil.appendZero(count + "", 2), 12, 2));
			resultMap.put("resultId", "00");
		} catch (Exception e) {
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		return GsonHelper.getGson().toJson(resultMap);
	}

	@RequestMapping(params = "method=delPolice")
	public ModelAndView delPolice(HttpServletRequest request,
			HttpServletResponse response, Department bean) {
		ModelAndView view = null;
		try {
			gSystemService.doBeginCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police);
			if (bean.getGlbm() == null || bean.getGlbm().length() != 12) {
				return CommonResponse.JSON("管理部门不符合要求");
			}
			String logs = "";
			logs = "删除";
			logs += ",管理部门:" + bean.getGlbm();
			logs += ",部门名称:" + bean.getBmmc();
			logs += ",警种:" + bean.getJzlx();
			DbResult result = this.rmDepartmentManager.delDepartment(bean,
					this.logService.getRmLog(request, getClass().getName(),
							MenuConstant.F_DepartmentCtrl_Police, logs, null));
			view = CommonResponse.JSON(result);
			gSystemService.doEndCall(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police, MenuConstant.P_save);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police,
					FuncUtil.transException(e));
			view = CommonResponse.JSON(e);
		}
		return view;
	}

	@RequestMapping(params = "method=showDepartment")
	public ModelAndView showDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// gSystemService.doBeginCall(request,getClass().getName(),MenuConstant.F_DepartmentCtrl_Police);
			// gSystemService.doEndCall(request,getClass().getName(),MenuConstant.F_DepartmentCtrl_Police,MenuConstant.P_save);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police,
					FuncUtil.transException(e));
		}
		return redirectMav("jsp_core/vmc", "framework/deptAll");
	}

	@RequestMapping(params = "method=getDept")
	public ModelAndView getDept(HttpServletRequest request,
			HttpServletResponse response, Department cmd) {
		ModelAndView view = null;
		try {
			if (cmd.getGlbm() == null || cmd.getGlbm().length() != 12
					|| null == cmd.getJzlx() || cmd.getJzlx().length() != 2) {
				return CommonResponse.JSON("管理部门不符合要求");
			}
			Department dept = null;
			if (Constants.JJ.equals(cmd.getJzlx())) {
				dept = this.gDepartmentService.getDepartment(cmd.getGlbm());
			} else {
				dept = this.gDepartmentService.getPoliceStation(cmd.getGlbm());
			}
			StringBuffer json = null;
			if (dept == null) {
				json = new StringBuffer();
				json.append("{'code':'-1','message':'")
						.append(URLEncoder.encode("部门不存在！", "UTF-8"))
						.append("'}");
			} else {
				json = new StringBuffer();
				json.append("{'code':'1','message':{'jzlx':'");
				json.append(cmd.getJzlx()).append("','glbm':'")
						.append(dept.getGlbm()).append("','bmmc':'");
				json.append(URLEncoder.encode(dept.getBmmc(), "UTF-8")).append(
						"'}}");
			}

			view = CommonResponse.JsonAjax(json.toString());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(),
					MenuConstant.F_DepartmentCtrl_Police,
					FuncUtil.transException(e));
			view = CommonResponse.JSON(e);
		}
		return view;
	}

	@RequestMapping(params = "method=getDialogForPGIS")
	public ModelAndView getDialogForPGIS(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//request.setAttribute("pgisdz", this.gSysparaCodeService.getParaValue("00", "PGISDZ"));
            request.setAttribute("GISDTFWDZ1", this.gSysparaCodeService.getGispara("GISDTFWDZ1"));
			request.setAttribute("pgisxzqhb",
					this.gSysparaCodeService.getParaValue("00", "PGISXZQHB"));
			request.setAttribute("pgisxzqh", userSession.getDepartment()
					.getGlbm().substring(0, 6));
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "",
					FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("jsp_core/vmc", "framework/pgisDialog");
	}

    @RequestMapping(params = "method=queryThird")
    public ModelAndView queryThird(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_JJ));
            List<Department> list = this.gDepartmentService.getXjDepartments(UserState.getUser().getGlbm(), true);
            model.addAttribute("glbm", list);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(),
                    MenuConstant.F_DepartmentCtrl_Traffic,
                    FuncUtil.transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("jsp_core/vmc", "framework/thirdDepartmentMain");
    }

	/**
	 * 新建部门
	 *
	 * @param request HttpServletRequest
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=addTraffic")
	public ModelAndView addTraffic(HttpServletRequest request, Model model) {
		LOG.info("------> calling RmDepartmentCtrl.addTraffic");
		try {
			model.addAttribute("cxmc", gSystemService.getFunctionName(XTLB, CDBH, GNID_JJ));

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");

			Department d = userSession.getDepartment();
			List<Department> list = this.gDepartmentService.getXjDepartments(d.getGlbm(), true);
			model.addAttribute("bmlist", list);

		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			LOG.error("------> calling RmDepartmentCtrl.addTraffic error", e.getMessage());
		}
		return redirectMav("jsp_core/vmc", "framework/trafficDepartmentAdd");
	}

	@RequestMapping(params = "method=saveTraffic", method = RequestMethod.POST)
	@ResponseBody
	public String saveTraffic(HttpServletRequest request, Department command) {
		LOG.info("------> calling RmDepartmentCtrl.saveTraffic");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");

			Department d = userSession.getDepartment();
			command.setFzjg(d.getFzjg());
			int result = departmentManager.create(command);
			if (result > 0) {
				resultMap.put("resultId", "00");
			}
		} catch (Exception e) {
			LOG.error("------> calling RmDepartmentCtrl.saveTraffic error", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling RmDepartmentCtrl.saveTraffic result = " + result);
		return result;
	}

	@RequestMapping(params = "method=deleteTraffic", method = RequestMethod.POST)
	@ResponseBody
	public String deleteTraffic(@RequestParam(value = "glbm") String glbm) {
		LOG.info("------> calling RmDepartmentCtrl.deleteTraffic");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Department department = gDepartmentService.getDepartment(glbm);
			if ("1".equals(department.getBjcsbj())) {
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg", "该部门不允许删除");
			} else {
				SysUser condition = new SysUser();
				condition.setGlbm(glbm);
				List userList = sysuserManager.getSysusers(condition);
				if (userList != null && !userList.isEmpty()) {
					resultMap.put("resultId", "02");
					resultMap.put("resultMsg", "请先删除所有该部门的用户");
				} else {
					int result = departmentManager.delete(glbm);
					if (result > 0) {
						resultMap.put("resultId", "00");
					} else {
						resultMap.put("resultId", "03");
						resultMap.put("resultMsg", "删除失败");
					}
				}
			}
		} catch (Exception e) {
			LOG.error("------> calling RmDepartmentCtrl.saveTraffic error", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling RmDepartmentCtrl.saveTraffic result = " + result);
		return result;
	}
}
