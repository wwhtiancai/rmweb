package com.tmri.rfid.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.common.EriReaderWriterRegisterStatus;
import com.tmri.rfid.util.GsonHelper;
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
import com.tmri.rfid.bean.EriEquipmentBean;
import com.tmri.rfid.common.EquipmentType;
import com.tmri.rfid.service.EriEquipmentService;
import com.tmri.rfid.service.impl.ExchangeServiceImpl;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

import java.util.HashMap;
import java.util.Map;

/*
 *wuweihong
 *2016-2-15
 */
@Controller
@RequestMapping("/eri-equipment.frm")
public class EriEquipmentCtrl extends BaseCtrl {

	private final static Logger LOG = LoggerFactory.getLogger(EriEquipmentCtrl.class);

	@Resource
	private EriEquipmentService eriEquipmentService;
	 @Resource
	 private ExchangeServiceImpl exchangeServiceImpl;
	@RequestMapping(params = "method=query-eri-equipment")
	public ModelAndView queryEriEquipment(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "xh", required = false) Long xh,
			@RequestParam(value = "sbh", required = false) String sbh,
			@RequestParam(value = "glbm", required = false) String glbm,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {

		PageList<EriEquipmentBean> queryList = null;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			// Department dept = userSession.getDepartment();
			// String selfbmjb = dept.getBmjb();
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			EriEquipmentBean eriEquipmentBean = new EriEquipmentBean();
			if (xh != null) {
				eriEquipmentBean.setXh(xh);
			}
			if (StringUtil.checkBN(sbh)) {
				eriEquipmentBean.setSbh(sbh);
			}
			if (StringUtil.checkBN(glbm)) {
				eriEquipmentBean.setGlbm(glbm);
			}
			queryList = eriEquipmentService.queryList(page, pageSize, xh, sbh, glbm);
			Paginator paginator = queryList.getPaginator();
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("statusList", EriReaderWriterRegisterStatus.values());
			request.setAttribute("command", eriEquipmentBean);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("equipment/equipmentMain");
	}

	@RequestMapping(params = "method=edit-eri-equipment")
	public ModelAndView editEquipment(Model model, HttpServletRequest request, HttpServletResponse response,
									  @RequestParam(value = "xh", required = true) Long xh) {

		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			// Department dept = userSession.getDepartment();
			// String selfbmjb = dept.getBmjb();
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			EriEquipmentBean eriEquipmentBean = this.eriEquipmentService.queryById(xh);
			request.setAttribute("bean", eriEquipmentBean);
			request.setAttribute("statusList", EriReaderWriterRegisterStatus.values());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("equipment/equipmentEdit");
	}

	@RequestMapping(params = "method=save-equipment", method = RequestMethod.POST)
	@ResponseBody
	public String saveEquipment(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "xh", required = true) Long xh,
			@RequestParam(value = "bz", required = false) String bz,
		    @RequestParam(value = "zt", required = true) String zt) {
		LOG.info(String.format("------> calling /eri-equipment.frm?method=save-equipment xh = %s, bz = %s, zt = %s", xh, bz, zt));
		Map<String, String> resultMap = new HashMap<String, String>();
		try {

			EriEquipmentBean eriEquipmentBean = eriEquipmentService.queryById(xh);

			if (StringUtil.checkBN(bz)) {
				eriEquipmentBean.setBz(bz);
			}
			eriEquipmentBean.setZt(Integer.valueOf(zt));
			if(eriEquipmentService.update(eriEquipmentBean)) {
				resultMap.put("resultId", "00");
				resultMap.put("resultMsg", "提交成功");
			} else {
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg", "提交失败");
			}
		} catch (Exception e) {
			LOG.error("save eriEquipment result fail ", e);
			resultMap.put("resultId", "02");
			resultMap.put("resultMsg", "提交异常(" + e.getMessage() + ")");
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling /eri-equipment.frm?method=save-equipment result = " + result);
		return result;
	}

	@RequestMapping(params = "method=del-eri-equipment")
	public ModelAndView delEquipment(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "xh", required = false) String xh) {
		ModelAndView view;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			DbResult result = new DbResult();
			int code = eriEquipmentService.delete(xh);
			result.setCode(code);
			if (code == 0) {
				result.setMsg("删除失败");
			} else {
				result.setMsg("删除成功");
			}

			view = CommonResponse.JSON(result);
		} catch (Exception e) {
			LOG.error("delete equipment result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		return view;
	}

	protected final String jspPath = "jsp_core/rfid/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
