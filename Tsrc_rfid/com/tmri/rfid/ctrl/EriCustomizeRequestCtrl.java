package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.EriCustomizeRequest;
import com.tmri.rfid.bean.OperationLog;
import com.tmri.rfid.common.CodeTableDefinition;
import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.common.EriCustomizeRequestStatus;
import com.tmri.rfid.service.CustomizeRequestService;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-25.
 */
@Controller
@RequestMapping("/customize-request.frm")
public class EriCustomizeRequestCtrl extends BaseCtrl {

    @Resource
    private CustomizeRequestService customizeRequestService;

    @Resource
    private GSysparaCodeService gSysparaCodeService;

    @Resource
    private OperationLogService operationLogService;

    private ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/customizeRequest", mav);
    }

    @RequestMapping(params = "method=list")
    public ModelAndView list(@RequestParam(value = "kh", required = false) String kh,
                             @RequestParam(value = "lsh", required = false) String lsh,
                             @RequestParam(value = "hpzl", required = false) String hpzl,
                             @RequestParam(value = "hphm", required = false) String hphm,
                             @RequestParam(value = "fzjg", required = false) String fzjg,
                             @RequestParam(value = "zt", required = false) Integer zt,
                             @RequestParam(value = "autoRefresh", required = false) String autoRefresh,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                             Model model, HttpServletRequest request) {
        try {
            UserSession userSession = (UserSession) org.springframework.web.util.WebUtils.getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Map<Object, Object> condition = new HashMap<Object, Object>();
            if (StringUtils.isNotEmpty(kh)) {
                condition.put("kh", kh);
            }
            if (StringUtils.isNotEmpty(lsh)) {
                condition.put("lsh", lsh);
            }
            if (StringUtils.isNotEmpty(hphm)) {
                condition.put("hphm", hphm);
            }
            if (StringUtils.isNotEmpty(hphm) && StringUtils.isNotEmpty(fzjg)) {
                condition.put("fzjg", fzjg);
            }
            if (StringUtils.isNotEmpty(hpzl)) {
                condition.put("hpzl", hpzl);
            }
            if (zt != null) {
                condition.put("zt", zt);
            }
            List<EriCustomizeRequest> requestList = customizeRequestService.fetchByCondition(condition, page, pageSize);
            PageList<EriCustomizeRequest> pageList = (PageList<EriCustomizeRequest>) requestList;
            Paginator paginator = pageList.getPaginator();
            model.addAttribute("controller", getPageInfo(paginator, request));
            model.addAttribute("requestList", requestList);
            Map<Integer, String> statusDescMap = new HashMap<Integer, String>();
            for (EriCustomizeRequestStatus status : EriCustomizeRequestStatus.values()) {
                statusDescMap.put(status.getStatus(), status.getDesc());
            }
            Map<String, String> hpzlDescMap = new HashMap<String, String>();
            for (Code code : (List<Code>)gSysparaCodeService.getCodes("00", "1007")) {
                hpzlDescMap.put(code.getDmz(), code.getDmsm1());
            }
            model.addAttribute("statusDescMap", statusDescMap);
            model.addAttribute("hpzlDescMap", hpzlDescMap);
            model.addAttribute("condition", condition);
            model.addAttribute("localFzjg", userSession.getDepartment().getFzjg());
            model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
            model.addAttribute("custTypes", CustomizeTaskType.values());
            model.addAttribute("autoRefresh", autoRefresh);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("main");
    }

    @RequestMapping(params = "method=edit")
    public ModelAndView edit(@RequestParam(value = "qqxh", required = true)  Long qqxh,
                             Model model, HttpServletRequest request) {
        try {
            List<OperationLog> operationLogs = operationLogService
                    .fetchByCondition(MapUtilities.buildMap("gjy", qqxh, "czmcs", new String[] {
                            "CUSTOMIZE", "CREATE_CUSTOMIZE_REQUEST", "FETCH_CUSTOMIZE_DATA", "UPDATE_CUSTOMIZE_RESULT"
                    }));
            model.addAttribute("operationLogs", operationLogs);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("edit");
    }

}
