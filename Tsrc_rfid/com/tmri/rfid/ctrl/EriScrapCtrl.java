package com.tmri.rfid.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.rfid.common.EriScrapDetailStatus;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.util.GsonHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.EriScrapApp;
import com.tmri.rfid.bean.EriScrapDetail;
import com.tmri.rfid.common.EriScrapStatus;
import com.tmri.rfid.dao.EriScrapDao;
import com.tmri.rfid.service.EriScrapService;
import com.tmri.rfid.service.impl.EriScrapServiceImpl;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *wuweihong
 *2015-11-11
 */
@Controller
@RequestMapping("/eri-scrap.frm")
public class EriScrapCtrl extends BaseCtrl {

	private final static Logger LOG = LoggerFactory.getLogger(EriScrapCtrl.class);

	@Resource
	private EriScrapService eriScrapService;

	@Resource
	private RuntimeProperty runtimeProperty;
    
	@Autowired
	private EriScrapDao eriScrapDao;



	@RequestMapping(params = "method=query-scrap")
	public ModelAndView queryScrap(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bfdh", required = false) String bfdh, @RequestParam(value = "bfyy", required = false) String bfyy,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {

		PageList<EriScrapApp> queryList = null;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			// Department dept = userSession.getDepartment();
			// String selfbmjb = dept.getBmjb();
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			EriScrapApp eriScrapDetail = new EriScrapApp();
			if (StringUtil.checkBN(bfdh)) {
				eriScrapDetail.setBfdh(bfdh);
			}
			if (StringUtil.checkBN(bfyy)) {
				eriScrapDetail.setBfdh(bfyy);
			}
			queryList = eriScrapService.queryScrapList(page, pageSize, bfdh, bfyy);
			Paginator paginator = queryList.getPaginator();
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("eriScrapStatus", EriScrapStatus.values());
			request.setAttribute("command", eriScrapDetail);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("eriScrap/eriScrapMain");
	}

	@RequestMapping(params = "method=edit-Scrap")
	public ModelAndView editScrap(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bfdh", required = false) String bfdh) {
		try {
			if (StringUtil.checkBN(bfdh)) {
				request.setAttribute("bean", eriScrapService.queryList(bfdh));
				request.setAttribute("list", eriScrapService.queryDetailList(bfdh));
			}
			request.setAttribute("eriScrapStatus", EriScrapStatus.values());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("eriScrap/eriScrapEdit");
	}

	@RequestMapping(params = "method=save-Scrap")
	@ResponseBody
	public String saveScrap(HttpServletRequest request, HttpServletResponse response,
							@RequestParam(value = "bfdh", required = false) String bfdh,
							@RequestParam(value = "jbr", required = false) String jbr,
							@RequestParam(value = "czr", required = false) String czr,
							@RequestParam(value = "bz", required = false) String bz,
							@RequestParam(value = "flag", required = false) boolean flag,
							@RequestParam(value = "bfyy", required = false) String bfyy,
							@RequestParam(value = "detailList", required = false) String detailList) {
		LOG.info(String.format("------> calling /eri-scrap.frm?method=save-scrap bfdh = %s, jbr = %s, czr= %s, bz = %s, flag = %s, bfyy = %s, detailList = %s",
						bfdh, jbr, czr, bz, flag, bfyy, detailList));
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		try {
			EriScrapApp eriScrapApp = new EriScrapApp();
			if (StringUtil.checkBN(bfdh)) {
				eriScrapApp.setBfdh(bfdh);
			}
			if (StringUtil.checkBN(bfyy)) {
				eriScrapApp.setBfyy(bfyy);
			}
			eriScrapApp.setZt(EriScrapStatus.SUBMIT.getStatus());
			if (StringUtil.checkBN(jbr)) {
				eriScrapApp.setJbr(jbr);
			}
			if (StringUtil.checkBN(czr)) {
				eriScrapApp.setCzr(czr);
			}
			if (StringUtil.checkBN(bz)) {
				eriScrapApp.setBz(bz);
			}
			boolean result = true;
			if (!flag) {
				String seqNum = eriScrapDao.getSeqId();
				eriScrapApp.setXh(seqNum);
				int code = eriScrapService.creatEriScrapApp(eriScrapApp);
				if (code == 0) {
					resultMap.put("resultId", "01");
					resultMap.put("resultMsg", "保存失败");
					result = false;
				} else {
					JSONArray ja = JSONArray.fromObject(detailList);
					for (int i = 0; i < ja.size(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);
						EriScrapDetail eriScrapDetail = new EriScrapDetail();
						eriScrapDetail.setTid(jo.get("tid").toString());
						eriScrapDetail.setKh(jo.get("kh").toString());
						eriScrapDetail.setGxrq(new Date());
						eriScrapDetail.setBfdh(eriScrapApp.getBfdh());
						eriScrapDetail.setZt(EriScrapDetailStatus.SUBMIT.getStatus());
						eriScrapService.creatEriScrapDetail(eriScrapDetail);
					}
				}
			} else {
				int code = eriScrapService.updateEriScrapApp(eriScrapApp);
				if (code == 0) {
					resultMap.put("resultId", "01");
					resultMap.put("resultMsg", "保存失败");
					result = false;
				} else {
					JSONArray ja = JSONArray.fromObject(detailList);
					for (int i = 0; i < ja.size(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);
						EriScrapDetail eriScrapDetail = new EriScrapDetail();
						eriScrapDetail.setTid(jo.get("tid").toString());
						eriScrapDetail.setKh(jo.get("kh").toString());
						eriScrapDetail.setBfdh(eriScrapApp.getBfdh());
						eriScrapDetail.setGxrq(new Date());
						eriScrapDetail.setZt(EriScrapDetailStatus.SUBMIT.getStatus());
						eriScrapService.creatEriScrapDetail(eriScrapDetail);
					}
				}
			}
			if (result) {
				resultMap.put("resultId", "00");
				resultMap.put("resultMsg", "保存成功");
			} else {
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg", "保存卡号失败");
			}
		} catch (Exception e) {
			LOG.error("save eriScrap result fail ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info(String.format("------> calling /eri-scrap.frm?method=save-Scrap result = %s", result));
		return result;
	}

	@RequestMapping(params = "method=del-eriScrapDetail")
	@ResponseBody
	public String dealDeviceSeat(@RequestParam(value = "xh", required = true) String xh) {
		LOG.info(String.format("------> calling /eri-scrap.frm?method=del-eriScrapDetail xh = %s", xh));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int code = this.eriScrapService.deleteEriScrapDetail(xh);
			if (code == 0) {
				resultMap.put("resultMsg", "删除失败");
				resultMap.put("resultId", "01");
			} else {
				resultMap.put("resultMsg","删除成功");
				resultMap.put("resultId", "00");
			}

		} catch (Exception e) {
			LOG.error("del rfid_eri_scrap_detail result fail ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", "异常:" + e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info(String.format("------> calling /eri-scrap.frm?method=del-eriScrapDetail result = %s", result));
		return result;
	}

	@RequestMapping(params = "method=delete-Scrap")
	@ResponseBody
	public String deleteScrap(@RequestParam(value = "bfdh", required = false) String bfdh) {
		LOG.info(String.format("------> calling /eri-scrap.frm?method=delete-Scrap bfdh = %s", bfdh));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int code = eriScrapService.deleteEriScrapApp(bfdh);
			if (code == 0) {
				resultMap.put("resultMsg", "删除失败");
				resultMap.put("resultId", "01");
			} else {
				int detailCode = eriScrapService.deleteEriScrapAppByBfdh(bfdh);
				if (detailCode == 0) {
					resultMap.put("resultMsg", "删除单号失败");
					resultMap.put("resultId", "02");
				} else {
					resultMap.put("resultMsg","删除成功");
					resultMap.put("resultId", "00");
				}
			}
		} catch (Exception e) {
			LOG.error("delete eriScrap result fail ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", "异常:" + e.getMessage());
		}

		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info(String.format("------> calling /eri-scrap.frm?method=delete-Scrap result = %s", result));
		return result;
	}

	@RequestMapping(params = "method=query-scrap-check")
	public ModelAndView queryScrapCheck(@RequestParam(value = "bfdh", required = false) String bfdh,
										@RequestParam(value = "bfyy", required = false) String bfyy,
										@RequestParam(value = "page", required = false, defaultValue = "1") int page,
										@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
										Model model, HttpServletRequest request) {

		Map<Object, Object> condition = new HashMap<Object, Object>();
		try {
			if (StringUtil.checkBN(bfdh)) {
				condition.put("bfdh", bfdh);
			}
			if (StringUtil.checkBN(bfyy)) {
				condition.put("bfyy", bfyy);
			}
			condition.put("zt", 1);
			List<EriScrapApp> list = eriScrapService.fetchByCondition(condition, page, pageSize);
			PageList<EriScrapApp> pageList = (PageList<EriScrapApp>) list;
			Paginator paginator = pageList.getPaginator();
			model.addAttribute("controller", getPageInfo(paginator, request));
			model.addAttribute("queryList", list);
			model.addAttribute("eriScrapStatus", EriScrapStatus.values());
			model.addAttribute("command", condition);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("eriScrap/eriScrapCheckMain");
	}

	@RequestMapping(params = "method=edit-scrap-check")
	public ModelAndView editScrapCheck(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bfdh", required = false) String bfdh) {
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			if (StringUtil.checkBN(bfdh)) {
				request.setAttribute("bean", eriScrapService.queryList(bfdh));
				request.setAttribute("list", eriScrapService.queryDetailList(bfdh));
			}
			request.setAttribute("eriScrapStatus", EriScrapStatus.values());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("eriScrap/eriScrapCheckEdit");
	}

	@RequestMapping(params = "method=save-scrap-check")
	public ModelAndView saveScrapCheck(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bfdh", required = false) String bfdh, @RequestParam(value = "scr", required = false) String scr,
			@RequestParam(value = "shbz", required = false) String shbz, @RequestParam(value = "check", required = false) String check) {
		ModelAndView view;
		try {
			DbResult result = new DbResult();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mFile = multipartRequest.getFile("file_upload");
			if (mFile != null) {
				String myPath = runtimeProperty.getFileFolder() + "/scrap/";
				if (!EriScrapServiceImpl.uploadFile(bfdh, myPath, mFile)) {
					result.setCode(0);
					result.setMsg("图片上传失败！");
					view = CommonResponse.JSON(result);
					return view;
				}
			}
			SysUser sysUser = UserState.getUser();
			EriScrapApp eriScrapApp = new EriScrapApp();
			if (StringUtil.checkBN(bfdh)) {
				eriScrapApp.setBfdh(bfdh);
			}
			if (StringUtil.checkBN(scr)) {
				eriScrapApp.setBfdh(scr);
			}
			if (StringUtil.checkBN(shbz)) {
				eriScrapApp.setShbz(shbz);
			}
			eriScrapApp.setScr(sysUser.getXm());
			if (check.equals("1")) {
				// 审核通过
				eriScrapApp.setZt(EriScrapStatus.APPROVED.getStatus());
				eriScrapApp.setShrq(new Date());
				int code = eriScrapService.updateEriScrapApp(eriScrapApp);
				result.setCode(code);
				if (code == 0) {
					result.setMsg("提交失败");
				} else {
					result.setMsg("提交成功");
				}
			}
			if (check.equals("0")) {
				// 审核不通过
				eriScrapApp.setZt(EriScrapStatus.REJECT.getStatus());
				eriScrapApp.setShrq(new Date());
				int code = eriScrapService.updateEriScrapApp(eriScrapApp);
				result.setCode(code);
				if (code == 0) {
					result.setMsg("提交失败");
				} else {
					result.setMsg("提交成功");
				}
			}
			view = CommonResponse.JSON(result);
		} catch (Exception e) {
			LOG.error("save eriScrap result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}

		return view;
	}

	@RequestMapping(params = "method=finish")
	@ResponseBody
	public String finish(@RequestParam(value = "bfdh", required = true) String bfdh) {
		LOG.info(String.format("------> calling /eri-scrap.frm?method=finish bfdh = %s", bfdh));
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			if (eriScrapService.finish(bfdh)) {
				resultMap.put("resultId", "00");
				resultMap.put("resultMsg", "提交成功");
			} else {
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg", "提交失败");
			}
		} catch (Exception e) {
			LOG.error("------> calling /eri-scrap.frm?method=finish fail", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", "异常:" + e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling /eri-scrap.frm?method=finish result = " + result);
		return result;
	}

	protected final String jspPath = "jsp_core/rfid/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
