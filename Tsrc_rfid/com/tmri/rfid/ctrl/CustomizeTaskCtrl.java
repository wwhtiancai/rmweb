package com.tmri.rfid.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.rfid.bean.*;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
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
import com.tmri.rfid.common.CodeTableDefinition;
import com.tmri.rfid.common.CustomizeTaskStatus;
import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.ctrl.view.CustomizeTaskView;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.ora.bean.DbResult;

/*
 *wuweihong
 *2015-11-13
 */
@Controller
@RequestMapping("/customize-task.frm")
public class CustomizeTaskCtrl extends BaseCtrl {

	private final static Logger LOG = LoggerFactory.getLogger(CustomizeTaskCtrl.class);

	@Resource
	private CustomizeTaskService customizeTaskService;

	@Resource
	private EriService eriService;

	@Resource
	private VehicleService vehicleService;

	@Resource
	private VehicleLogService vehicleLogService;

	@Resource
	private ReviewRecordService reviewRecordService;

	@RequestMapping(params = "method=query-customize")
	public ModelAndView queryCustomize(HttpServletRequest request,
			@RequestParam(value = "lsh", required = false) String lsh,
			@RequestParam(value = "hphm", required = false) String hphm,
			@RequestParam(value = "ywlx", required = false) String ywlx,
			@RequestParam(value = "hpzl", required = false) String hpzl,
            @RequestParam(value = "zt", required = false) String zt,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {

        LOG.info(String.format("------> calling /customize-task.frm?method=query-customize(%s,%s,%s,%s,%s,%s,%s)",
                lsh, hphm, ywlx, hpzl, zt, page, pageSize));
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}

			Map<String, Object> condition = new HashMap<String, Object>();
			if (StringUtil.checkBN(lsh)) {
				condition.put("lsh", lsh);
			}
			if (StringUtil.checkBN(hphm)) {
				condition.put("hphm", hphm);
			}
			if (StringUtil.checkBN(hpzl)) {
				condition.put("hpzl", hpzl);
			}
			if (StringUtil.checkBN(ywlx)) {
				condition.put("rwlx", ywlx);
			}
			if (StringUtils.isNotEmpty(zt)) {
			    condition.put("zt", zt);
            }
			PageList<CustomizeTaskView> queryList = customizeTaskService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();

			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
			request.setAttribute("ywlx", ywlx);
			request.setAttribute("localFzjg", userSession.getDepartment().getFzjg());
			request.setAttribute("custStatus", CustomizeTaskStatus.values());
			request.setAttribute("custTypes", CustomizeTaskType.values());
			request.setAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("customize/customizeMain");
	}

	@RequestMapping(params = "method=edit-customize")
	public ModelAndView editCustomize(Model model, HttpServletRequest request, HttpServletResponse response,
									  @RequestParam(value = "xh", required = false) String xh,
									  @RequestParam(value = "ywlx") String ywlx) {
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			model.addAttribute("cert", gSysparaCodeService.getSysParaValue("00", "DSJZS"));
			model.addAttribute("latestVersion", gSysparaCodeService.getParaValue("00", "QZCXBB"));
			if (Integer.valueOf(ywlx) == CustomizeTaskType.MODIFY_PASSWORD.getType()) {
				return redirectMav("customize/modifyPassword");
			}
			if (StringUtil.checkBN(xh)) {
				CustomizeTask customizeTask = customizeTaskService.fetchByXh(Long.valueOf(xh));
                if (customizeTask.getTid() != null) {
                    Eri eri = eriService.fetchByTid(customizeTask.getTid());
                    model.addAttribute("eri", eri);
                }
				model.addAttribute("task", customizeTask);
                Vehicle newVehicle = vehicleService.fetchById(customizeTask.getClxxid());
                if (newVehicle != null) {
                    model.addAttribute("bean", vehicleService.translate(newVehicle));
                }
                if (Integer.valueOf(ywlx) == CustomizeTaskType.MODIFY.getType() && customizeTask.getYclxxid() != null) {
                    Vehicle oldVehicle = vehicleService.fetchById(customizeTask.getYclxxid());
                    if (oldVehicle != null) {
                        model.addAttribute("old", vehicleService.translate(oldVehicle));
                    }
                    Eri boundEri = eriService.fetchByVehicle(oldVehicle);
                    if (boundEri != null) {
                        model.addAttribute("oldEri", boundEri);
                    }
                }
				if (Integer.valueOf(ywlx) == CustomizeTaskType.MODIFY.getType()
                        || Integer.valueOf(ywlx) == CustomizeTaskType.CHANGE.getType()) {
					List<ReviewRecord> reviewRecords = reviewRecordService.fetch("CUSTOMIZE_" + customizeTask.getRwlx(), customizeTask.getXh());
					model.addAttribute("reviewList", reviewRecords);
				}
			}
			model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
			model.addAttribute("carTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));
			model.addAttribute("carColors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
			model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
			model.addAttribute("provinces", gSysparaCodeService.getCodes(CodeTableDefinition.PROVINCE));
            model.addAttribute("bdfzjg", gSysparaCodeService.getSysParaValue("00", "BDFZJG"));
			request.setAttribute("localFzjg", userSession.getDepartment().getFzjg().substring(0, 1));
			request.setAttribute("custStatus", CustomizeTaskStatus.values());
			request.setAttribute("custTypes", CustomizeTaskType.values());
			model.addAttribute("ywlx", ywlx);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		if (Integer.valueOf(ywlx) == CustomizeTaskType.MODIFY.getType()) {
            return redirectMav("customize/modifyEdit");
        } else if (Integer.valueOf(ywlx) == CustomizeTaskType.CHANGE.getType()) {
            return redirectMav("customize/changeEdit");
        } else if (Integer.valueOf(ywlx) == CustomizeTaskType.ANNUAL_INSPECTION.getType()) {
            return redirectMav("customize/annualInspectionEdit");
        } else if (Integer.valueOf(ywlx) == 99) {
            return redirectMav("customize/batchCustomize");
        } else {
            return redirectMav("customize/customizeEdit");
        }
	}

	@RequestMapping(params = "method=select-customize")
	public ModelAndView selectCustomize(
            Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "hpzl", required = false) String hpzl, 
			@RequestParam(value = "hphm", required = false) String hphm,
			@RequestParam(value = "rwlx", required = false) Long rwlx,
            @RequestParam(value = "fzjg", required = false) String fzjg,
			@RequestParam(value = "lsh", required = false) String lsh) {
		ModelAndView view;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			String msg = "";
			// 后期调借口
			Vehicle bean = vehicleService.synchronize(lsh, fzjg, hphm, hpzl,null);
			List<CustomizeTask> CustomizeTaskList =null;
			CustomizeTask customizeTask =null;
			if (bean!=null) {
				Map<String, Object> conditionCust = new HashMap<String, Object>();
				conditionCust.put("clxxid", bean.getId());
				CustomizeTaskList = this.customizeTaskService.queryList(conditionCust);
				if(CustomizeTaskList.size()>0){
					for(int i=0;i<CustomizeTaskList.size();i++){
						if(CustomizeTaskList.get(i).getZt()==CustomizeTaskStatus.SUBMIT.getStatus()){
							customizeTask = CustomizeTaskList.get(i);
							msg = "有个性化任务正在进行！";
							break;
						}
						if(CustomizeTaskList.get(i).getZt()==CustomizeTaskStatus.DONE.getStatus()
                                &&rwlx==CustomizeTaskType.NEW.getType()){
							customizeTask = CustomizeTaskList.get(i);
							msg = "已办理过个性化业务，请转至补领或换领！";
							break;
						}
					}
  				} else if (CustomizeTaskList.size()==0&&rwlx!=CustomizeTaskType.NEW.getType()){
					msg = "未办理过个性化业务，请转至申领！";
					rwlx = 1l;
				}
			}else{
				msg = "没有查到对应的车辆信息！";
			}
			model.addAttribute("bean", bean);
			model.addAttribute("localFzjg", userSession.getDepartment().getFzjg());
			model.addAttribute("msg", msg);
			model.addAttribute("rwlx", rwlx);
			model.addAttribute("list", customizeTask);
			model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
			model.addAttribute("carTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));
			model.addAttribute("carColors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
			model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
            model.addAttribute("bdfzjg", gSysparaCodeService.getSysParaValue("00","BDFZJG"));
			request.setAttribute("custStatus", CustomizeTaskStatus.values());
			request.setAttribute("custTypes", CustomizeTaskType.values());
			model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("customize/customizeEdit");
	}

	@RequestMapping(params = "method=save-customize")
	public ModelAndView saveCustomize(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "clxxid", required = false) Long clxxid,
                                      @RequestParam(value = "lxdh", required = false) String lxdh,
                                      @RequestParam(value = "rwlx", required = false) Integer rwlx,
                                      @RequestParam(value = "syr", required = false) String syr) {
		ModelAndView view;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			DbResult result = new DbResult();
			if (clxxid != null) {
				CustomizeTask customizeTask = customizeTaskService.create(null, clxxid, lxdh, syr, rwlx, userSession.getSysuser().getYhdh(),
                        CustomizeTaskStatus.SUBMIT.getStatus(), null, null, null);
				if (customizeTask != null) {
					result.setMsg("提交成功");
				} else {
					result.setMsg("提交失败");
				}
			}
			
			view = CommonResponse.JSON(result);
		} catch (Exception e) {
			LOG.error("save customizeTask result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		return view;
	}

	
	@RequestMapping(params = "method=cancel")
	@ResponseBody
	public String cancel(@RequestParam(value = "xh") Long xh) {
		LOG.info("------> calling /customize-task.frm?method=cancel xh=" + xh);
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
            eriService.saveCustomizeResult(xh, 0, "取消任务", null);
            customizeTaskService.updateByCondition(MapUtilities.buildMap("cond_xh", xh, "zt", CustomizeTaskStatus.CANCEL.getStatus()));
            resultMap.put("resultId", "00");
		} catch (Exception e) {
			LOG.error("delete customizeTask result fail ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", "异常: " + e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling /customize-task.frm?method=cancel result=" + result);
		return result;
	}

	@RequestMapping(params = "method=modify", method = RequestMethod.POST)
	@ResponseBody
	public String modify(@RequestParam(value = "lsh", required = false) String lsh,
						 @RequestParam(value = "hpzl", required = false) String hpzl,
						 @RequestParam(value = "hphm", required = false) String hphm,
						 @RequestParam(value = "tid") String tid,
                         @RequestParam(value = "sqr", required = false) String sqr,
                         @RequestParam(value = "lxdh", required = false) String lxdh,
						 @RequestParam(value = "bz", required = false) String bz) {
		LOG.info(String.format(
				"------> calling POST /customize-task.frm?method=modify, tid = %s, lsh = %s, hpzl = %s, hphm = %s",
				tid, lsh, hpzl, hphm));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(lsh) && (StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(hphm))) {
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg", "输入参数有误");
			} else {
				List<CustomizeTask> customizeTasks = customizeTaskService.queryList(MapUtilities.buildMap(
						"tid", tid, "zts", new int[] {CustomizeTaskStatus.SUBMIT.getStatus(),
										CustomizeTaskStatus.PENDING.getStatus()}));
				if (customizeTasks.size() > 0) {
					resultMap.put("resultId", "01");
					resultMap.put("resultMsg", "该电子标识已存在更改业务");
				} else {
					CustomizeTask customizeTask = customizeTaskService.createModify(lsh, FuncUtil.getFzjg(hphm), hpzl, hphm.substring(1),
							tid, sqr, lxdh, bz);
					if (customizeTask != null) {
						resultMap.put("resultId", "00");
					} else {
						resultMap.put("resultId", "02");
						resultMap.put("resultMsg", "提交失败");
					}
				}
			}
		} catch (OperationException e) {
			LOG.error("customize failed ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
			resultMap.put("errorCode", e.getCode());
		} catch (Exception e) {
			LOG.error("customize failed ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling /customize-task.frm?method=modify result = " + result);
		return result;
	}

    @RequestMapping(params = "method=change", method = RequestMethod.POST)
    @ResponseBody
    public String change(@RequestParam(value = "kh", required = false) String kh,
                         @RequestParam(value = "ykh") String ykh,
                         @RequestParam(value = "sqr", required = false) String sqr,
                         @RequestParam(value = "lxdh", required = false) String lxdh,
                         @RequestParam(value = "bz", required = false) String bz) {
        LOG.info(String.format(
                "------> calling POST /customize-task.frm?method=change, kh = %s, ykh = %s, sqr = %s, lxdh = %s, bz = %s",
               kh, ykh, sqr, lxdh, bz));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            CustomizeTask customizeTask = customizeTaskService.createChange(ykh, kh, sqr, lxdh, bz);
            resultMap.put("resultId", "00");
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /customize-task.frm?method=modify result = " + result);
        return result;
    }

    @RequestMapping(params = "method=annual-inspection", method = RequestMethod.POST)
    @ResponseBody
    public String annualInspection(@RequestParam(value = "kh") String kh,
                                   @RequestParam(value = "sqr", required = false) String sqr,
                                   @RequestParam(value = "lxdh", required = false) String lxdh,
                                   @RequestParam(value = "bz", required = false) String bz) {
        LOG.info(String.format(
                "------> calling POST /customize-task.frm?method=annual-inspection, kh = %s, sqr = %s, lxdh = %s, bz = %s",
                kh, sqr, lxdh, bz));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            CustomizeTask customizeTask = customizeTaskService.createAnnualInspection(kh, sqr, lxdh, bz);
            resultMap.put("resultId", "00");
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /customize-task.frm?method=annual-inspection result = " + result);
        return result;
    }


	@RequestMapping(params = "method=review-list")
	public ModelAndView reviewList(@RequestParam(value = "lsh", required = false) String lsh,
							       @RequestParam(value = "hphm", required = false) String hphm,
							       @RequestParam(value = "hpzl", required = false) String hpzl,
                                   @RequestParam(value = "ywlx", required = true) String ywlx,
							       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
							       @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
							       HttpServletRequest request, HttpServletResponse response) {
		LOG.info(String.format("------> calling /customize-task.frm?method=review-list(%s,%s,%s,%s,%s,%s)",
				lsh, hphm, hpzl, ywlx, page, pageSize));
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

			Map<String, Object> condition = new HashMap<String, Object>();
			if (StringUtil.checkBN(lsh)) {
				condition.put("lsh", lsh);
			}
			if (StringUtil.checkBN(hphm)) {
				condition.put("hphm", userSession.getDepartment().getFzjg().substring(1) + hphm);
			}
			if (StringUtil.checkBN(hpzl)) {
				condition.put("hpzl", hpzl);
			}
			condition.put("zt", CustomizeTaskStatus.PENDING.getStatus());
            condition.put("rwlx", Integer.valueOf(ywlx));
			PageList<CustomizeTaskView> queryList = customizeTaskService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();

			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
			request.setAttribute("ywlx", Integer.valueOf(ywlx));
			request.setAttribute("localFzjg", userSession.getDepartment().getFzjg());
			request.setAttribute("custStatus", CustomizeTaskStatus.values());
			request.setAttribute("custTypes", CustomizeTaskType.values());
			request.setAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("customize/reviewMain");
	}

	@RequestMapping(params = "method=audit", method = RequestMethod.GET)
	public ModelAndView audit(@RequestParam(value = "xh") String xh,
							  Model model, HttpServletRequest request) {
		LOG.info(String.format("------> calling /customize-task.frm?method=audit xh = %s", xh));
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			CustomizeTask customizeTask = customizeTaskService.fetchByXh(Long.valueOf(xh));
            VehicleInfo oldVehicle = vehicleService.fetchById(customizeTask.getYclxxid());
            model.addAttribute("oldVehicle", oldVehicle);
            if (customizeTask.getTid() != null) {
                Eri eri = eriService.fetchByTid(customizeTask.getTid());
                model.addAttribute("newEri", eri);
            }
            if (customizeTask.getRwlx() == CustomizeTaskType.CHANGE.getType()) {
                Eri oldEri = eriService.fetchByVehicle(oldVehicle);
                model.addAttribute("oldEri", oldEri);
            }
            if (customizeTask.getRwlx() == CustomizeTaskType.MODIFY.getType()) {
                model.addAttribute("newVehicle", vehicleService.fetchById(customizeTask.getClxxid()));
            }
			model.addAttribute("task", customizeTask);
			model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
			model.addAttribute("carTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));
			model.addAttribute("carColors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
			model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
			model.addAttribute("provinces", gSysparaCodeService.getCodes(CodeTableDefinition.PROVINCE));
			model.addAttribute("bdfzjg", gSysparaCodeService.getSysParaValue("00", "BDFZJG"));
			model.addAttribute("localFzjg", userSession.getDepartment().getFzjg().substring(0, 1));
			model.addAttribute("custStatus", CustomizeTaskStatus.values());
			model.addAttribute("custTypes", CustomizeTaskType.values());
			model.addAttribute("ywlx", customizeTask.getRwlx());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("customize/audit");
	}

	@RequestMapping(params = "method=audit", method = RequestMethod.POST)
	@ResponseBody
	public String audit(@RequestParam(value = "xh") Long xh,
						@RequestParam(value = "jg") int jg,
						@RequestParam(value = "shyj", required = false) String shyj) {
		LOG.info(String.format(
				"------> calling POST /customize-task.frm?method=audit, xh = %s, jg = %s, shyj = %s", xh, jg, shyj));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			customizeTaskService.review(xh, jg, shyj);
			resultMap.put("resultId", "00");
		} catch (OperationException e) {
			LOG.error("audit failed ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
			resultMap.put("errorCode", e.getCode());
		} catch (Exception e) {
			LOG.error("audit failed ", e);
			resultMap.put("resultId", "99");
			resultMap.put("resultMsg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		LOG.info("------> calling /customize-task.frm?method=audit result = " + result);
		return result;
	}

	@RequestMapping(params = "method=console")
	public ModelAndView console() {
		return redirectMav("console");
	}

	protected final String jspPath = "jsp_core/rfid/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
