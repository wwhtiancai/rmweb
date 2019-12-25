package com.tmri.rm.ctrl;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.service.SysuserManager;
import com.tmri.rfid.util.GsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.tmri.framework.bean.Role;
import com.tmri.rm.service.PlsSysuserManager;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.DebugLog;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.MenuConstant;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Controller
@RequestMapping("/sysuser.vmc")
public class SysuserCtrl extends RmCtrl {

	public static final Logger LOG = LoggerFactory.getLogger(SysuserCtrl.class);

	@Autowired
	private PlsSysuserManager plsSysuserManager;

	@Autowired
	private SysuserManager sysuserManager;


    private String cxdh=Constants.MENU_YHGL;

	/**
	 * 用户管理首次页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=fwdPlsSysUserFrame")
	public ModelAndView fwdPlsSysUserFrame(HttpServletRequest request, HttpServletResponse response){
		try{
			List<Function> funcList = this.gSystemService.getUserCxdhGnlb(request.getSession(), "00", MenuConstant.M_SysuserCtrl);
			request.setAttribute("gnid", funcList);
			request.setAttribute("gnidsize", funcList.size());
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
			e.printStackTrace();
		}
		return redirectMav("jsp_core/vmc","framework/SysuserFrame");
	}
	
	/**
	 * 交警用户管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=fwdSysUserFrameMain")
	public ModelAndView fwdSysUserFrameMain(HttpServletRequest request, HttpServletResponse response){
		return redirectMav("jsp_core/vmc","framework/SysuserFrameMain");
	}
	
	/**
	 * 大公安用户管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=fwdPlsSysUserFrameMain")
	public ModelAndView fwdPlsSysUserFrameMain(HttpServletRequest request, HttpServletResponse response){
		try {
			UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
			String glbmHead = gDepartmentService.getDepartmentHeadSQL(userSession.getDepartment());
			List<Department> bmList = this.plsSysuserManager.getPoliceDepartmentList(glbmHead);
			request.setAttribute("bmList",bmList);
			List<Role> roleList = this.plsSysuserManager.getRoleList();
			request.setAttribute("roleList",roleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirectMav("jsp_core/vmc","framework/PlsSysuserMain");
	}
	
	
	@RequestMapping(params = "method=queryPlsSysUser")
	public ModelAndView queryPlsSysUser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA);
        	RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, null);
			PageController controller = new PageController(request);
			
			if(!StringUtil.checkBN(cmd.getGlbm())) {
				UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
				String detphead = this.gDepartmentService.getDepartmentHeadSQL(userSession.getDepartment());
				cmd.setGlbm(detphead+"%");
			}
			List<SysUser> list = this.plsSysuserManager.getPlsSysuserList(cmd, controller, log);
			request.setAttribute("queryList", list);
			request.setAttribute("controller", controller);
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA, MenuConstant.P_list);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		// return redirectMav("jsp_core/vmc","framework/PlsSysuserMain");
		return redirectMav("jsp_core/vmc","framework/PlsSysuserList");
	}
	
	
	@RequestMapping(params = "method=fwdSysUserMain")
	public ModelAndView fwdSysUserMain(HttpServletRequest request, HttpServletResponse response){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			String glbm = request.getParameter("glbm");
			UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
			SysUser currentUser = userSession.getSysuser();
			request.setAttribute("czyh", currentUser);
			request.setAttribute("dept", this.gDepartmentService.getDepartment(glbm));
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_JJ, MenuConstant.P_open);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), MenuConstant.F_SysuserCtrl_JJ, FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/SysuserMain");
	}
	
	@RequestMapping(params = "method=fwdPlsSysUserMain")
	public ModelAndView fwdPlsSysUserMain(HttpServletRequest request, HttpServletResponse response){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA);
			String glbm = request.getParameter("glbm");
			System.out.println(glbm);
			if (StringUtil.checkBN(glbm)) {
				UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
				glbm = userSession.getDepartment().getGlbm();
			}
			request.setAttribute("dept", this.gDepartmentService.getPoliceStation(glbm));
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA, MenuConstant.P_open);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/PlsSysuserMain");
	}
	
	@RequestMapping(params = "method=querySysUser")
	public ModelAndView querySysUser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_JJ);
			//包含下级判断
            Department dept = gDepartmentService.getDepartment(cmd.getGlbm());
            String glbmlike = cmd.getGlbm();
            if ("1".equals(cmd.getBhxj())) {
                glbmlike = this.gDepartmentService.getDepartmentHeadSQL(dept);
            }
        	cmd.setGlbm(glbmlike);
        	RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, null);
			PageController controller = new PageController(request);
			controller.setPageSize(17);
			List<SysUser> list = this.plsSysuserManager.getSysuserList(cmd, controller, log);
			request.setAttribute("queryList", list);
			request.setAttribute("controller", controller);
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_JJ, MenuConstant.P_list);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/SysuserList");
	}
	
	@RequestMapping(params = "method=editSysuser")
	public ModelAndView editSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
			
			List<Code> sfmjCodeList = this.gSysparaCodeService.getCodes("63", "6101");
			List<Code> ztList = this.gSysparaCodeService.getCodes("63", "6100");
			List<Code> xtglyCodeList = this.gSysparaCodeService.getCodes("00", "9105");
			request.setAttribute("sfmjCodeList", sfmjCodeList);
			request.setAttribute("ztCodeList", ztList);
			request.setAttribute("xtglyCodeList", xtglyCodeList);
			
			String yhsqms = this.gSysparaCodeService.getParaValue("00", "YHSQMS");
			request.setAttribute("yhsqms", yhsqms);
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, null);
			SysUser user = this.plsSysuserManager.getSysuser(cmd, Constants.JJ, log);
			Department dept = this.gDepartmentService.getDepartment(user.getGlbm());
			
			request.setAttribute("user", user);
			request.setAttribute("dept", dept);
			request.setAttribute("cglbm", userSession.getDepartment().getGlbm());

			if ("1".equals(userSession.getSysuser().getXtgly())) {
				request.setAttribute("editable", true);
			}

			//获取当前登录用户的可管理权限
			Role curUserRoleQC = new Role();
			curUserRoleQC.setYhdh(userSession.getYhdh());
			curUserRoleQC.setGlbm(userSession.getDepartment().getGlbm());
			curUserRoleQC.setJscj(dept.getBmjb());
			List<Role> curUserGrantRoleList = this.plsSysuserManager.getUserGrantRole(curUserRoleQC);
			HashMap<String, Role> curUserGrantRoleMap = new HashMap<String, Role>();
			if(null != curUserGrantRoleList && curUserGrantRoleList.size() > 0){
				for(Role t : curUserGrantRoleList){
					curUserGrantRoleMap.put(t.getJsdh(), t);
				}
			}

			//设置需配置用户可操作角色
			List<Role> userRoleList = this.plsSysuserManager.getUserRole(cmd, null);
			List<Role> userRoleByCurList = new ArrayList<Role>();
			List<Role> userRoleByOthList = new ArrayList<Role>();
			List<Role> userRoleCanByCurList = new ArrayList<Role>();
			HashMap<String, Role> userRoleMap = new HashMap<String, Role>();
			if(null != userRoleList && userRoleList.size() > 0){
				for(Role t : userRoleList){
					userRoleMap.put(t.getJsdh(), t);
					if(curUserGrantRoleMap.containsKey(t.getJsdh())){
						userRoleByCurList.add(t);
					}else{
						userRoleByOthList.add(t);
					}
				}
			}
			for(Role r : curUserGrantRoleList){
				if(!userRoleMap.containsKey(r.getJsdh())){
					userRoleCanByCurList.add(r);
				}
			}
			if(userRoleByCurList != null && userRoleByCurList.size() == 0){
				userRoleByCurList = null;
			}
			if(userRoleByOthList != null && userRoleByOthList.size() == 0){
				userRoleByOthList = null;
			}
			if(userRoleCanByCurList != null && userRoleCanByCurList.size() == 0){
				userRoleCanByCurList = null;
			}
			request.setAttribute("userRoleByCurList", userRoleByCurList);
			request.setAttribute("userRoleByOthList", userRoleByOthList);
			request.setAttribute("userRoleCanByCurList", userRoleCanByCurList);


			//设置用户可管理权限
			Role userRoleQC = new Role();
			userRoleQC.setYhdh(cmd.getYhdh());
			userRoleQC.setGlbm(cmd.getGlbm());
			List<Role> userGrantRoleList = this.plsSysuserManager.getUserGrantRole(userRoleQC);
			List<Role> userGrantRoleByCurList = new ArrayList<Role>();
			List<Role> userGrantRoleByOthList = new ArrayList<Role>();
			List<Role> userGrantRoleCanByCurList = new ArrayList<Role>();
			HashMap<String, Role> userGrantRoleMap = new HashMap<String, Role>();
			if(null != userGrantRoleList && userGrantRoleList.size() > 0){
				for(Role t : userGrantRoleList){
					userGrantRoleMap.put(t.getJsdh(), t);
					if(curUserGrantRoleMap.containsKey(t.getJsdh())){
						userGrantRoleByCurList.add(t);
					}else{
						userGrantRoleByOthList.add(t);
					}
				}
			}
			for(Role r : curUserGrantRoleList){
				if(!userGrantRoleMap.containsKey(r.getJsdh())){
					userGrantRoleCanByCurList.add(r);
				}
			}
			if(userGrantRoleByCurList != null && userGrantRoleByCurList.size() == 0){
				userGrantRoleByCurList = null;
			}
			if(userGrantRoleByOthList != null && userGrantRoleByOthList.size() == 0){
				userGrantRoleByOthList = null;
			}
			if(userGrantRoleCanByCurList != null && userGrantRoleCanByCurList.size() == 0){
				userGrantRoleCanByCurList = null;
			}
			request.setAttribute("userGrantRoleByCurList", userGrantRoleByCurList);
			request.setAttribute("userGrantRoleByOthList", userGrantRoleByOthList);
			request.setAttribute("userGrantRoleCanByCurList", userGrantRoleCanByCurList);

			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_list);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/SysuserEdit");
	}

	@RequestMapping(params = "method=createSysuser")
	public ModelAndView createSysuser(@RequestParam(value = "glbm") String glbm,
									  HttpServletRequest request){
		try{

			UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
			SysUser operator = userSession.getSysuser();

			List<Code> sfmjCodeList = this.gSysparaCodeService.getCodes("63", "6101");
			List<Code> ztList = this.gSysparaCodeService.getCodes("63", "6100");
			List<Code> xtglyCodeList = this.gSysparaCodeService.getCodes("00", "9105");
			request.setAttribute("sfmjCodeList", sfmjCodeList);
			request.setAttribute("ztCodeList", ztList);
			request.setAttribute("xtglyCodeList", xtglyCodeList);

			String yhsqms = this.gSysparaCodeService.getParaValue("00", "YHSQMS");
			request.setAttribute("yhsqms", yhsqms);
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, null);
			Department dept = this.gDepartmentService.getDepartment(glbm);

			request.setAttribute("dept", dept);
			request.setAttribute("cglbm", userSession.getDepartment().getGlbm());

			//获取当前登录用户的可管理权限
			Role curUserRoleQC = new Role();
			curUserRoleQC.setYhdh(userSession.getYhdh());
			curUserRoleQC.setGlbm(userSession.getDepartment().getGlbm());
			curUserRoleQC.setJscj(dept.getBmjb());
			List<Role> curUserGrantRoleList = this.plsSysuserManager.getUserGrantRole(curUserRoleQC);
			HashMap<String, Role> curUserGrantRoleMap = new HashMap<String, Role>();
			if(null != curUserGrantRoleList && curUserGrantRoleList.size() > 0){
				for(Role t : curUserGrantRoleList){
					curUserGrantRoleMap.put(t.getJsdh(), t);
				}
			}

			//设置需配置用户可操作角色
			List<Role> userRoleList = this.plsSysuserManager.getUserRole(userSession.getSysuser(), null);
			List<Role> userRoleByCurList = new ArrayList<Role>();
			List<Role> userRoleByOthList = new ArrayList<Role>();
			List<Role> userRoleCanByCurList = new ArrayList<Role>();
			HashMap<String, Role> userRoleMap = new HashMap<String, Role>();
			if(null != userRoleList && userRoleList.size() > 0){
				for(Role t : userRoleList){
					userRoleMap.put(t.getJsdh(), t);
					if(curUserGrantRoleMap.containsKey(t.getJsdh())){
						userRoleByCurList.add(t);
					}else{
						userRoleByOthList.add(t);
					}
				}
			}
			for(Role r : curUserGrantRoleList){
				if(!userRoleMap.containsKey(r.getJsdh())){
					userRoleCanByCurList.add(r);
				}
			}
			if(userRoleByCurList != null && userRoleByCurList.size() == 0){
				userRoleByCurList = null;
			}
			if(userRoleByOthList != null && userRoleByOthList.size() == 0){
				userRoleByOthList = null;
			}
			if(userRoleCanByCurList != null && userRoleCanByCurList.size() == 0){
				userRoleCanByCurList = null;
			}
			request.setAttribute("userRoleByCurList", userRoleByCurList);
			request.setAttribute("userRoleByOthList", userRoleByOthList);
			request.setAttribute("userRoleCanByCurList", userRoleCanByCurList);


			//设置用户可管理权限
			Role userRoleQC = new Role();
			userRoleQC.setYhdh(operator.getYhdh());
			userRoleQC.setGlbm(operator.getGlbm());
			List<Role> userGrantRoleList = this.plsSysuserManager.getUserGrantRole(userRoleQC);
			List<Role> userGrantRoleByCurList = new ArrayList<Role>();
			List<Role> userGrantRoleByOthList = new ArrayList<Role>();
			List<Role> userGrantRoleCanByCurList = new ArrayList<Role>();
			HashMap<String, Role> userGrantRoleMap = new HashMap<String, Role>();
			if(null != userGrantRoleList && userGrantRoleList.size() > 0){
				for(Role t : userGrantRoleList){
					userGrantRoleMap.put(t.getJsdh(), t);
					if(curUserGrantRoleMap.containsKey(t.getJsdh())){
						userGrantRoleByCurList.add(t);
					}else{
						userGrantRoleByOthList.add(t);
					}
				}
			}
			for(Role r : curUserGrantRoleList){
				if(!userGrantRoleMap.containsKey(r.getJsdh())){
					userGrantRoleCanByCurList.add(r);
				}
			}
			if(userGrantRoleByCurList != null && userGrantRoleByCurList.size() == 0){
				userGrantRoleByCurList = null;
			}
			if(userGrantRoleByOthList != null && userGrantRoleByOthList.size() == 0){
				userGrantRoleByOthList = null;
			}
			if(userGrantRoleCanByCurList != null && userGrantRoleCanByCurList.size() == 0){
				userGrantRoleCanByCurList = null;
			}
			request.setAttribute("userGrantRoleByCurList", userGrantRoleByCurList);
			request.setAttribute("userGrantRoleByOthList", userGrantRoleByOthList);
			request.setAttribute("userGrantRoleCanByCurList", userGrantRoleCanByCurList);

			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_list);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/SysuserCreate");
	}

	@RequestMapping(params = "method=createSysuser", method = RequestMethod.POST)
	@ResponseBody
	public String createSysuser(HttpServletRequest request, HttpServletResponse response, SysUser user){
		LOG.info("------> calling SysuserCtrl.createSysuser user = " + user);
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try{

			int result = this.sysuserManager.create(user);
			if (result > 0) {
				resultMap.put("resultId", "00");
			} else {
				resultMap.put("resultId", "01");
			}

		}catch(Exception e){
			LOG.error("------>calling SysuserCtrl.createSysuser fail", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------>calling SysuserCtrl.createSysuser result = " + result);
		return result;
	}

    @RequestMapping(params = "method=remove", method = RequestMethod.POST)
    @ResponseBody
    public String remove(HttpServletRequest request, HttpServletResponse response, SysUser user){
        LOG.info("------> calling SysuserCtrl.remove user = " + user);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        try{
            String ip=FuncUtil.getRemoteIpdz(request);
            String cznr="删除用户,用户代号为"+user.getYhdh();
            Log log=new Log(user.getGlbm(),user.getYhdh(),"",cxdh,"",cznr,ip,"");
            SysResult returninfo=this.sysuserManager.removeSysuser(user,log);
            if (returninfo.getFlag() == 2) {
                resultMap.put("resultId", "00");
                resultMap.put("resultMsg", returninfo.getRetdesc());
            } else {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", returninfo.getRetdesc());
            }
        }catch(Exception e){
            LOG.error("------>calling SysuserCtrl.remove fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------>calling SysuserCtrl.remove result = " + result);
        return result;
    }

	
	//解锁
	@RequestMapping(params = "method=unlockSysuser")
	public void unlockSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			StringBuffer logBuf = new StringBuffer("用户解锁");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", logBuf.toString(), map);
			
			DbResult dr = this.plsSysuserManager.unlockSysuser(cmd, log);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
		}	
		CommonResponse.pageRedirect("doSaveSysuserReturns", json.toString(), response);
	}
	
	@RequestMapping(params = "method=saveSysuser")
	public void saveSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			StringBuffer logBuf = new StringBuffer("变更交警用户IP信息");
			logBuf.append(",YHDH:").append(cmd.getYhdh());
			logBuf.append(",IPKS:").append(cmd.getIpks());
			logBuf.append(",IPJS:").append(cmd.getIpjs());
			logBuf.append(",GDIP:").append(cmd.getGdip());
			logBuf.append(",GDIP1:").append(cmd.getGdip1());
			logBuf.append(",GDIP2:").append(cmd.getGdip2());
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", logBuf.toString(), map);
			
			DbResult dr = this.plsSysuserManager.updateSysuser(cmd, log);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
		}	
		CommonResponse.pageRedirect("doSaveSysuserReturns", json.toString(), response);
	}
	
	@RequestMapping(params = "method=saveSysuserRole")
	public void saveSysuserRole(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		String json = this.saveRole(request, cmd, Constants.JJ);
		CommonResponse.pageRedirect("doSaveSysuserRoleReturns", json.toString(), response);
	}
	
	@RequestMapping(params = "method=resetSysuserPwd")
	public void resetSysuserPwd(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", "重置用户密码,用户代号:" + cmd.getYhdh(), map);
			DbResult dr = this.plsSysuserManager.updateSysuserPwd(cmd, log);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
		}	
		CommonResponse.pageRedirect("doResetSysuserPwdReturns", json.toString(), response);
	}
	
	@RequestMapping(params = "method=newPlsSysuser")
	public ModelAndView newPlsSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA);
			
			UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
			
			List<Code> sfmjCodeList = this.gSysparaCodeService.getCodes("63", "6101");
			List<Code> ztCodeList = this.gSysparaCodeService.getCodes("63", "6100");
			List<Code> xtglyCodeList = this.gSysparaCodeService.getCodes("00", "9105");
			request.setAttribute("sfmjCodeList", sfmjCodeList);
			request.setAttribute("ztCodeList", ztCodeList);
			request.setAttribute("xtglyCodeList", xtglyCodeList);
			
			String yhsqms = this.gSysparaCodeService.getParaValue("00", "YHSQMS");
			request.setAttribute("yhsqms", yhsqms);
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, null);
			SysUser user = this.plsSysuserManager.getSysuser(cmd, null, log);
			Department dept = this.gDepartmentService.getDepartment(user.getGlbm());
			
			request.setAttribute("user", user);
			request.setAttribute("dept", dept);
			request.setAttribute("cglbm", userSession.getDepartment().getGlbm());
			
			//获取当前登录用户的可管理权限
			Role curUserRoleQC = new Role();
			curUserRoleQC.setYhdh(userSession.getYhdh());
			curUserRoleQC.setGlbm(userSession.getDepartment().getGlbm());
			curUserRoleQC.setJscj(dept.getBmjb());
			List<Role> curUserGrantRoleList = this.plsSysuserManager.getUserGrantRole(curUserRoleQC);
			HashMap<String, Role> curUserGrantRoleMap = new HashMap<String, Role>();
			if(null != curUserGrantRoleList && curUserGrantRoleList.size() > 0){
				for(Role t : curUserGrantRoleList){
					curUserGrantRoleMap.put(t.getJsdh(), t);
				}
			}
			
			//设置需配置用户可操作角色
			List<Role> userRoleList = this.plsSysuserManager.getUserRole(cmd, null);
			List<Role> userRoleByCurList = new ArrayList<Role>();
			List<Role> userRoleByOthList = new ArrayList<Role>();
			List<Role> userRoleCanByCurList = new ArrayList<Role>();
			HashMap<String, Role> userRoleMap = new HashMap<String, Role>();
			if(null != userRoleList && userRoleList.size() > 0){
				for(Role t : userRoleList){
					userRoleMap.put(t.getJsdh(), t);
					if(curUserGrantRoleMap.containsKey(t.getJsdh())){
						userRoleByCurList.add(t);
					}else{
						userRoleByOthList.add(t);
					}
				}
			}
			for(Role r : curUserGrantRoleList){
				if(!userRoleMap.containsKey(r.getJsdh())){
					userRoleCanByCurList.add(r);
				}
			}
			if(userRoleByCurList != null && userRoleByCurList.size() == 0){
				userRoleByCurList = null;
			}
			if(userRoleByOthList != null && userRoleByOthList.size() == 0){
				userRoleByOthList = null;
			}
			if(userRoleCanByCurList != null && userRoleCanByCurList.size() == 0){
				userRoleCanByCurList = null;
			}
			request.setAttribute("userRoleByCurList", userRoleByCurList);
			request.setAttribute("userRoleByOthList", userRoleByOthList);
			request.setAttribute("userRoleCanByCurList", userRoleCanByCurList);
			
			
			//设置用户可管理权限
			Role userRoleQC = new Role();
			userRoleQC.setYhdh(cmd.getYhdh());
			userRoleQC.setGlbm(cmd.getGlbm());
			List<Role> userGrantRoleList = this.plsSysuserManager.getUserGrantRole(userRoleQC);
			List<Role> userGrantRoleByCurList = new ArrayList<Role>();
			List<Role> userGrantRoleByOthList = new ArrayList<Role>();
			List<Role> userGrantRoleCanByCurList = new ArrayList<Role>();
			HashMap<String, Role> userGrantRoleMap = new HashMap<String, Role>();
			if(null != userGrantRoleList && userGrantRoleList.size() > 0){
				for(Role t : userGrantRoleList){
					userGrantRoleMap.put(t.getJsdh(), t);
					if(curUserGrantRoleMap.containsKey(t.getJsdh())){
						userGrantRoleByCurList.add(t);
					}else{
						userGrantRoleByOthList.add(t);
					}
				}
			}
			for(Role r : curUserGrantRoleList){
				if(!userGrantRoleMap.containsKey(r.getJsdh())){
					userGrantRoleCanByCurList.add(r);
				}
			}
			if(userGrantRoleByCurList != null && userGrantRoleByCurList.size() == 0){
				userGrantRoleByCurList = null;
			}
			if(userGrantRoleByOthList != null && userGrantRoleByOthList.size() == 0){
				userGrantRoleByOthList = null;
			}
			if(userGrantRoleCanByCurList != null && userGrantRoleCanByCurList.size() == 0){
				userGrantRoleCanByCurList = null;
			}
			request.setAttribute("userGrantRoleByCurList", userGrantRoleByCurList);
			request.setAttribute("userGrantRoleByOthList", userGrantRoleByOthList);
			request.setAttribute("userGrantRoleCanByCurList", userGrantRoleCanByCurList);
			
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA, MenuConstant.P_list);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/PlsSysuserEdit");
	}
	
	@RequestMapping(params = "method=editPlsSysuser")
	public ModelAndView editPlsSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA);
			
			UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
			
			List<Code> sfmjCodeList = this.gSysparaCodeService.getCodes("63", "6101");
			List<Code> ztList = this.gSysparaCodeService.getCodes("63", "6100");
			List<Code> xtglyCodeList = this.gSysparaCodeService.getCodes("00", "9105");
			request.setAttribute("sfmjCodeList", sfmjCodeList);
			
			for(int i=0;i<ztList.size();i++) {
				if(ztList.get(i).getDmz().equals("3")) {
					ztList.remove(i);
				}
			}
			
			request.setAttribute("ztCodeList", ztList);
			request.setAttribute("xtglyCodeList", xtglyCodeList);
			
			String yhsqms = this.gSysparaCodeService.getParaValue("00", "YHSQMS");
			request.setAttribute("yhsqms", yhsqms);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, map);
			
			String modal = request.getParameter("modal");
			request.setAttribute("modal", modal);
			
			if(!StringUtil.checkBN(cmd.getGlbm())){
				throw new Exception("请选择管理部门！");
			}
			
			Department dept = this.gDepartmentService.getPoliceStation(cmd.getGlbm());
			request.setAttribute("dept", dept);
			
			List<Role> roleList = this.plsSysuserManager.getRoleList();
			request.setAttribute("roleList",roleList);

			if(StringUtil.checkBN(modal)){
				if("new".equals(modal)){
					//获取当前登录用户的可管理权限
					Role curUserRoleQC = new Role();
					curUserRoleQC.setYhdh(userSession.getYhdh());
					curUserRoleQC.setGlbm(userSession.getDepartment().getGlbm());
					curUserRoleQC.setJscj(dept.getBmjb());
					List<Role> curUserGrantRoleList = this.plsSysuserManager.getUserGrantRole(curUserRoleQC);
					
					//设置需配置用户可操作角色
					List<Role> userRoleCanByCurList = curUserGrantRoleList;
					if(userRoleCanByCurList != null && userRoleCanByCurList.size() == 0){
						userRoleCanByCurList = null;
					}
					request.setAttribute("userRoleCanByCurList", userRoleCanByCurList);
					
					//设置用户可管理权限
					List<Role> userGrantRoleCanByCurList = curUserGrantRoleList;
					if(userGrantRoleCanByCurList != null && userGrantRoleCanByCurList.size() == 0){
						userGrantRoleCanByCurList = null;
					}
					request.setAttribute("userGrantRoleCanByCurList", userGrantRoleCanByCurList);
				}else if("edit".equals(modal)){
					SysUser user = this.plsSysuserManager.getSysuser(cmd, null, log);
					request.setAttribute("user", user);
					List<Role> plsRoleList = this.plsSysuserManager.getUserRole(cmd, null);
					request.setAttribute("plsRoleList",plsRoleList);
					
				}else{
					throw new Exception("不支持的模式！");
				}
			}else{
				throw new Exception("不支持的方法！");
			}
			request.setAttribute("cglbm", userSession.getDepartment().getGlbm());
			
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA, MenuConstant.P_list);
		}catch(Exception e){
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e,request);
		}
		return redirectMav("jsp_core/vmc","framework/PlsSysuserEdit");
	}
	
	@RequestMapping(params = "method=savePlsSysuser")
	public void savePlsSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		response.setContentType("text/html;charset=GB2312");
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA);
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", null, null);
			System.out.println(cmd.getXm());
			DbResult dr = null;
			String modal = request.getParameter("modal");
			if(StringUtil.checkBN(modal)){
				if("new".equals(modal)){
					dr = this.plsSysuserManager.savePlsSysuser(cmd, log);
				}else if("edit".equals(modal)){
					dr = this.plsSysuserManager.updatePlsSysuser(cmd, log);
				}else{
					throw new Exception("不支持的模式！");
				}
			}else{
				throw new Exception("不支持的方法！");
			}
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), MenuConstant.F_SysuserCtrl_DGA, MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
			e.printStackTrace();
		}	
		CommonResponse.pageRedirect("doSaveSysuserReturns", json.toString(), response);
	}
	
	@RequestMapping(params = "method=resetPlsSysuserPwd")
	public void resetPlsSysuserPwd(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", "重置用户密码,用户代号:" + cmd.getYhdh(), map);
			DbResult dr = this.plsSysuserManager.updatePlsSysuserPwd(cmd, log);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
		}	
		CommonResponse.pageRedirect("doResetPlsSysuserPwdResults", json.toString(), response);
	}
	
	@RequestMapping(params = "method=savePlsSysuserRole")
	public void savePlsSysuserRole(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			String [] kczqx = request.getParameterValues("kczqx");
			
			StringBuffer logBuf = new StringBuffer("设置用户权限");
			logBuf.append(",GLBM:").append(cmd.getGlbm());
			logBuf.append(",YHDH:").append(cmd.getYhdh());
			logBuf.append(",XTGLY:").append(cmd.getXtgly());
			logBuf.append(",KGLQX:");
			logBuf.append(",KCZQX:");
			if(null != kczqx && kczqx.length > 0){
				for(String s : kczqx){
					logBuf.append("-").append(s);
				}
			}else{
				logBuf.append("-");
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", logBuf.toString(), map);
			
			DbResult dr = this.plsSysuserManager.savePlsrole(cmd, kczqx);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
			e.printStackTrace();
		}	
		String rs = json.toString();
		CommonResponse.pageRedirect("doSaveuserroleReturns", rs, response);
	}
	
	
	@RequestMapping(params = "method=deletePlsSysuser")
	public void deletePlsSysuser(HttpServletRequest request, HttpServletResponse response, SysUser cmd){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", "删除公安用户：" + cmd.getYhdh() + ";身份证：" + cmd.getSfzmhm(), map);
			DbResult dr = this.plsSysuserManager.deletePlsSysuser(cmd, log);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
		}
		CommonResponse.pageRedirect("doDelPlsSysuser", json.toString(), response);
	}
	
	@RequestMapping(params = "method=queryDepartment")
	public ModelAndView queryDepartment(HttpServletRequest request, HttpServletResponse response){
		StringBuffer json = new StringBuffer();
		try{
			String glbm = request.getParameter("glbm");
			String jzlx = request.getParameter("jzlx");
			Department dept = null;
			if(StringUtil.checkBN(jzlx)){
				if(Constants.JJ.equals(jzlx)){
					//交警警种
					dept = this.gDepartmentService.getDepartment(glbm);
				}else{
					//大公安
					dept = this.gDepartmentService.getPoliceStation(glbm);
				}
			}else{
				//交警警种
				dept = this.gDepartmentService.getDepartment(glbm);
			}
			if(null == dept){
				json.append("{'code':'0','msg':'unfound department'}");
			}else{
				json.append("{'code':'1','msg':{'glbm':'").append(dept.getGlbm()).append("','bmmc':'").append(URLEncoder.encode(dept.getBmmc(), "utf-8")).append("'}}");
			}
		}catch(Exception e){
			String msg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", msg);
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'query exception'}");
		}
		
		return CommonResponse.JsonAjax(json.toString());
	}
	
	@RequestMapping(params = "method=saveRole")
	private String saveRole(HttpServletRequest request, SysUser cmd, String jzlx){
		StringBuffer json = new StringBuffer();
		try{
			this.gSystemService.doBeginCall(request, this.getClass().getName(), "");
			String [] kglqx = null;
			if(Constants.YES.equals(cmd.getXtgly())){
				kglqx = request.getParameterValues("kglqx");
			}
			String [] kczqx = request.getParameterValues("kczqx");
			
			StringBuffer logBuf = new StringBuffer("设置用户权限");
			logBuf.append(",GLBM:").append(cmd.getGlbm());
			logBuf.append(",YHDH:").append(cmd.getYhdh());
			logBuf.append(",XTGLY:").append(cmd.getXtgly());
			logBuf.append(",KGLQX:");
			if(null != kglqx && kglqx.length > 0){
				for(String s : kglqx){
					logBuf.append("-").append(s);
				}
			}else{
				logBuf.append("-");
			}
			logBuf.append(",KCZQX:");
			if(null != kczqx && kczqx.length > 0){
				for(String s : kczqx){
					logBuf.append("-").append(s);
				}
			}else{
				logBuf.append("-");
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", cmd.getYhdh());
			RmLog log = this.logService.getRmLog(request, this.getClass().getName(), "", logBuf.toString(), map);
			
			DbResult dr = this.plsSysuserManager.saveUserrole(cmd, kglqx, kczqx, log, jzlx);
			json.append("{'code':'").append(dr.getCode()).append("','msg':'").append(URLEncoder.encode(dr.getMsg(), "utf-8")).append("'}");
			this.gSystemService.doEndCall(request, this.getClass().getName(), "", MenuConstant.P_save);
		}catch(Exception e){
			String emsg = FuncUtil.transException(e);
			logService.saveErrLog(request, getClass().getName(), "", emsg);
			String emm = "";
			try{
				emm = URLEncoder.encode(emsg, "utf-8");
			}catch(Exception e1){
				emm = "unknow exception";
			}
			json.delete(0, json.length());
			json.append("{'code':'-1','msg':'").append(emm).append("'}");
			e.printStackTrace();
		}	
		DebugLog.debug(json.toString());
		return json.toString();
	}
}
