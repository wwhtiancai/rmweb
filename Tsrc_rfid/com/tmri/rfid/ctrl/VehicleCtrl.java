package com.tmri.rfid.ctrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esri.aims.mtier.model.metadata.User;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.EriCustomizeRecord;
import com.tmri.rfid.common.CodeTableDefinition;
import com.tmri.rfid.common.EriStatus;
import com.tmri.rfid.service.EriCustomizeRecordService;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.util.DateUtil;
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
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.service.VehicleService;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;

/**
 * Created by Stone on 2015/9/15.
 */
@Controller
@RequestMapping("/vehicle.frm")
public class VehicleCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(VehicleCtrl.class);

	@Resource
	private VehicleService vehicleService;

	@Resource
	private EriService eriService;

    @Resource
    private EriCustomizeRecordService eriCustomizeRecordService;
    
    @RequestMapping(params = "method=query-vehicle")
    public ModelAndView queryVehicle(@RequestParam(value = "hpzl", required = false) String hpzl,
									 @RequestParam(value = "fzjg", required = false) String fzjg,
									 @RequestParam(value = "hphm", required = false) String hphm,
									 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
									 @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
									 Model model, HttpServletRequest request){

		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			if(StringUtil.checkBN(hpzl)){
                condition.put("hpzl", hpzl);
			}
			if(StringUtil.checkBN(hphm)){
                condition.put("hphm", hphm);
			}
			if(StringUtils.isNotEmpty(fzjg) && StringUtils.isNotEmpty(hphm)) {
				condition.put("fzjg", fzjg);
			}

            List<Vehicle> list = vehicleService.fetchByCondition(condition, new PageBounds(page, pageSize));
            PageList<Vehicle> pageList = (PageList<Vehicle>)list;
			model.addAttribute("queryList", pageList);
			model.addAttribute("controller", getPageInfo(pageList.getPaginator(), request));
			model.addAttribute("command", condition);
			model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
			model.addAttribute("vehicleTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));
			model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
			model.addAttribute("vehicleColors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
			model.addAttribute("provinces", gSysparaCodeService.getCodes(CodeTableDefinition.PROVINCE));
			model.addAttribute("bdfzjg", gSysparaCodeService.getSysParaValue("00", "BDFZJG"));
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			model.addAttribute("localFzjg", userSession.getDepartment().getFzjg());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("query/vehicleMain");
    }
    
    @RequestMapping(params = "method=edit-vehicle")
    public ModelAndView editVehicle(Model model, HttpServletRequest request,HttpServletResponse response,
									@RequestParam(value = "id", required = false) Long id){
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
			if (id != null) {
				Vehicle bean = this.vehicleService.fetchById(id);
				Eri eri = eriService.fetchByVehicle(bean);
                model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
                model.addAttribute("vechileTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));

                model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
				model.addAttribute("colors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
				model.addAttribute("eri", eri);
				model.addAttribute("eriStatus", EriStatus.values());
                List<EriCustomizeRecord> eriList = eriCustomizeRecordService.fetchByCondition(
                        MapUtilities.buildMap("hphm", bean.getHphm(), "hzpl", bean.getHpzl(), "fzjg", bean.getFzjg())
                );
                model.addAttribute("eriList", eriList);
				request.setAttribute("bean", bean);
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
    	
    	return redirectMav("query/vehicleEdit");
    }

    @RequestMapping(params = "method=create")
    public ModelAndView create(Model model, HttpServletRequest request){
        try {
            UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            model.addAttribute("localFzjg", Arrays.asList(gSysparaCodeService.getSysParaValue("00", "BDFZJG").split(",")));
            model.addAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
            model.addAttribute("carTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_TYPE));
            model.addAttribute("carColors", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_COLOR));
            model.addAttribute("usingPurposes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_USING_PURPOSE));
            model.addAttribute("provinces", gSysparaCodeService.getCodes(CodeTableDefinition.PROVINCE));
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }

        return redirectMav("query/vehicleCreate");
    }

    @RequestMapping(params = "method=create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestParam(value = "hpzl") String hpzl,
                         @RequestParam(value = "fzjg") String fzjg,
                         @RequestParam(value = "hphm") String hphm,
                         @RequestParam(value = "cllx") String cllx,
                         @RequestParam(value = "csys") String csys,
                         @RequestParam(value = "syr") String syr,
                         @RequestParam(value = "syxz") String syxz,
                         @RequestParam(value = "pl") int pl,
                         @RequestParam(value = "gl", required = false, defaultValue = "0") Double gl,
                         @RequestParam(value = "zzl", required = false, defaultValue = "0") int zzl,
                         @RequestParam(value = "zbzl", required = false, defaultValue = "0") int zbzl,
                         @RequestParam(value = "hdzzl", required = false, defaultValue = "0") int hdzzl,
                         @RequestParam(value = "hdzk", required = false, defaultValue = "0") int hdzk,
                         @RequestParam(value = "ccrq") String ccrq,
                         @RequestParam(value = "yxqz") String yxqz,
                         @RequestParam(value = "qzbfqz") String qzbfqz,
                         @RequestParam(value = "xszzp") String xszzp,
                         HttpServletRequest request){
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            Vehicle vehicle = new Vehicle();
            vehicle.setHpzl(hpzl);
            vehicle.setFzjg(fzjg);
            vehicle.setHphm(hphm);
            vehicle.setCllx(cllx);
            vehicle.setCsys(csys);
            vehicle.setSyr(syr);
            vehicle.setSyxz(syxz);
            vehicle.setPl(pl);
            vehicle.setGl(gl);
            vehicle.setZzl(zzl);
            vehicle.setZbzl(zbzl);
            vehicle.setHdzk(hdzk);
            vehicle.setHdzzl(hdzzl);
            vehicle.setCcrq(DateUtil.praseDate(ccrq));
            vehicle.setYxqz(DateUtil.praseDate(yxqz));
            vehicle.setQzbfqz(DateUtil.praseDate(qzbfqz));
            vehicle.setXszzp(xszzp);
            if (vehicleService.create(vehicle)) {
                resultMap.put("resultId", "00");
            } else {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "添加失败");
            }
        } catch (Exception e) {
            LOG.error("添加车辆异常", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);

        return result;
    }
	
    protected final String jspPath = "jsp_core/rfid/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
