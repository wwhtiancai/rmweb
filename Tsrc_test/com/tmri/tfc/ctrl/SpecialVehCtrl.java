package com.tmri.tfc.ctrl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.MenuConstant;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;
import com.tmri.tfc.bean.TfcSpecial;
import com.tmri.tfc.service.SpecialVehService;

/**
 * @author Yangzm
 * @data: 2014-06-04 time: 下午04:25:26
 * 
 */
@Controller
@RequestMapping("/specialVeh.tfc")
public class SpecialVehCtrl extends TfcCtrl {
	@Autowired
	private SpecialVehService specialVehManager;

	@RequestMapping(params = "method=querySpecial")
	public ModelAndView querySpecial(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");
			// 号牌种类
			request.setAttribute("hpzl", this.gSysparaCodeService.getCodes(
					"00", "1007"));

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");

			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);

			PageController controller = new PageController(request);
			controller.setPageSize(20);

			TfcSpecial command = new TfcSpecial();

			Department dept = userSession.getDepartment();
			if (dept != null) {
				if (!StringUtil.checkBN(command.getGlbm())) {
					command.setGlbm(dept.getGlbm());
					command.setBmjb(dept.getBmjb());
				}
				else{
					command.setBmjb(gDepartmentService.getDepartment(command.getGlbm()).getBmjb());
				}
			}

			List<TfcSpecial> queryList = this.specialVehManager
					.getSpecialVehList(command, logService.getRmLog(request,
							getClass().getName(), "", "", null), controller);

			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", controller);

			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_open);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("query/specialVehMain");
	}

	@RequestMapping(params = "method=listSpecial")
	public ModelAndView listSpecial(HttpServletRequest request,
			HttpServletResponse response, TfcSpecial command) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");
			// 号牌种类
			request.setAttribute("hpzl", this.gSysparaCodeService.getCodes(
					"00", "1007"));

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");

			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);

			PageController controller = new PageController(request);
			controller.setPageSize(20);

			Department dept = userSession.getDepartment();
			if (dept != null) {
				if (!StringUtil.checkBN(command.getGlbm())) {
					command.setGlbm(dept.getGlbm());
					command.setBmjb(dept.getBmjb());
				}
				else{
					command.setBmjb(gDepartmentService.getDepartment(command.getGlbm()).getBmjb());
				}
			}

			StringBuffer logMsg = new StringBuffer("红名单信息查询，查询条件,号牌号码:"
					+ command.getHphm() + ",号牌种类:" + command.getHpzl()
					+ ",备注说明:" + command.getBzsm());

			List<TfcSpecial> queryList = this.specialVehManager
					.getSpecialVehList(command, logService.getRmLog(request,
							getClass().getName(), "", logMsg.toString(), null),
							controller);

			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", controller);

			request.setAttribute("command", command);

			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_open);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("query/specialVehMain");
	}

	@RequestMapping(params = "method=editSpecial")
	public ModelAndView editSpecial(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");
			// 号牌种类
			request.setAttribute("hpzl", this.gSysparaCodeService.getCodes(
					"00", "1007"));

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			Department dept = userSession.getDepartment();
			String selfbmjb = dept.getBmjb();

			String xh = request.getParameter("xh");
			if (xh != null && xh.length() > 0) {
				TfcSpecial bean = this.specialVehManager.getSpecial(xh);
				if (bean != null) {
					if (selfbmjb.equals(bean.getBmjb())) {
						request.setAttribute("bmjb", selfbmjb);
					}
				}
				request.setAttribute("bean", bean);
			}

			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_open);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("query/specialVehEdit");
	}

	@RequestMapping(params = "method=saveSpecial")
	public ModelAndView saveSpecial(HttpServletRequest request,
			HttpServletResponse response, TfcSpecial bean) {
		ModelAndView view = null;
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");

			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");

			Department dept = userSession.getDepartment();
			bean.setGlbm(dept.getGlbm());
			bean.setSjjb(dept.getBmjb());
			bean.setCjr(userSession.getYhdh());

			String logs = "车辆红名单保存，号牌号码:" + bean.getHphm() + ",号牌种类:"
					+ bean.getHpzl() + ",备注说明:" + bean.getBzsm();

			// String xtyxms = this.gSysparaCodeService.getParaValue("00",
			// "XTYXMS");
			DbResult result = this.specialVehManager.saveSpecial(bean,
					this.logService.getRmLog(request, getClass().getName(), "",
							logs, null));
			view = CommonResponse.JSON(result);

			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_save);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			view = CommonResponse.JSON(e);
		}
		return view;
	}

	@RequestMapping(params = "method=delSpecial")
	public ModelAndView delSpecial(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = null;
		try {
			gSystemService.doBeginCall(request, getClass().getName(), "");
			String logs = "车辆红名单删除";
			String xh = request.getParameter("xh");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("zjxx", xh);
			DbResult result = this.specialVehManager.delSpecial(xh,
					this.logService.getRmLog(request, getClass().getName(), "",
							logs, map));
			view = CommonResponse.JSON(result);

			gSystemService.doEndCall(request, getClass().getName(), "",
					MenuConstant.P_save);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			view = CommonResponse.JSON(e);
		}
		return view;
	}

}
