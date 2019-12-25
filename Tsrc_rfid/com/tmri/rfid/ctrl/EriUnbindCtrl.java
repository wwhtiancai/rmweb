package com.tmri.rfid.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.rfid.service.*;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.common.CodeTableDefinition;
import com.tmri.rfid.common.CustomizeTaskStatus;
import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.ctrl.view.EriUnbindView;
import com.tmri.rfid.exception.OperationException;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.FuncUtil;

/*
 * st
 * 2017-08-21
 */
@Controller
@RequestMapping("/eri-unbind.frm")
public class EriUnbindCtrl extends BaseCtrl {

	private final static Logger LOG = LoggerFactory.getLogger(CustomizeTaskCtrl.class);

	@Resource
	private EriService eriService;
	@Resource
	private EriUnbindService unbindService;


	@RequestMapping(params = "method=query-unbind")
	public ModelAndView queryCustomize(HttpServletRequest request,
			@RequestParam(value = "kh", required = false) String kh,
			@RequestParam(value = "hphm", required = false) String hphm,
			@RequestParam(value = "hpzl", required = false) String hpzl,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {

        LOG.info(String.format("------> calling /eri-unbind.frm?method=query-unbind(%s,%s,%s,%s,%s)",
                kh, hphm, hpzl, page, pageSize));
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}

			Map<String, Object> condition = new HashMap<String, Object>();
			if (StringUtil.checkBN(kh)) {
				condition.put("kh", kh);
			}
			if (StringUtil.checkBN(hphm)) {
				condition.put("hphm", hphm);
			}
			if (StringUtil.checkBN(hpzl)) {
				condition.put("hpzl", hpzl);
			}
			
			PageList<EriUnbindView> queryList = unbindService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();

			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
			request.setAttribute("localFzjg", userSession.getDepartment().getFzjg());
			request.setAttribute("custStatus", CustomizeTaskStatus.values());
			request.setAttribute("custTypes", CustomizeTaskType.values());
			request.setAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("customize/unbindMain");
	}
	
	@RequestMapping(params = "method=create-unbind")
	public ModelAndView createUnbind(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			model.addAttribute("cert", gSysparaCodeService.getSysParaValue("00", "DSJZS"));
			model.addAttribute("latestVersion", gSysparaCodeService.getParaValue("00", "QZCXBB"));
			
			model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
			model.addAttribute("carTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));
			model.addAttribute("carColors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
			model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
			model.addAttribute("provinces", gSysparaCodeService.getCodes(CodeTableDefinition.PROVINCE));
            model.addAttribute("bdfzjg", gSysparaCodeService.getSysParaValue("00", "BDFZJG"));
			request.setAttribute("localFzjg", userSession.getDepartment().getFzjg().substring(0, 1));
			request.setAttribute("custStatus", CustomizeTaskStatus.values());
			request.setAttribute("custTypes", CustomizeTaskType.values());
			
		}catch (Exception e) {
			// TODO: handle exception
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("customize/createUnbind");
	}
	

	

	protected final String jspPath = "jsp_core/rfid/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
