package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.ElectronicCertificate;
import com.tmri.rfid.bean.SecurityModel;
import com.tmri.rfid.common.SecurityModelType;
import com.tmri.rfid.service.ElectronicCertificateService;
import com.tmri.rfid.service.SecurityModelService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.FuncUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/20.
 */
@Controller
@RequestMapping("/security-model.frm")
public class SecurityModelCtrl extends BaseCtrl{
	
	private final static Logger LOG = LoggerFactory.getLogger(SecurityModelCtrl.class);

	@Resource
	private SecurityModelService securityModelService;
	 @Resource
	private ElectronicCertificateService electronicCertificateService;
	
    @RequestMapping(params = "method=generate-sequence")
    @ResponseBody
    public String generateSequence(@RequestParam(value = "model", required = true) String model,
                                   @RequestParam(value = "type", required = true) String type,
                                   @RequestParam(value = "version", required = true) int version) {
        LOG.info(String.format("------> calling /security-model.frm?method=generate-sequence model=%s,tye=%s,version=%s",
                model,type,version));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (model.length() != 1 || type.length() != 1 || version > 99) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "参数错误，型号为1个字符，类型为1个字符，版本为2位10进制数");
            } else {
                String xh = securityModelService.generateSequence(model, type, version);
                resultMap.put("resultId", "00");
                resultMap.put("xh", xh);
            }
        } catch (Exception e) {
            LOG.error("生成序号失败", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", "生成序号失败" + e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call /security-model.frm?method=generate-sequence result = " + result);
        return result;
    }
	
    @RequestMapping(params = "method=init-model")
    @ResponseBody
    public String initModel(@RequestParam(value = "xh", required = true) String xh,
                           @RequestParam(value = "lx", required = false) String lx,
                           @RequestParam(value = "qulx", required = false) String qulx,
                           @RequestParam(value = "xp1gy", required = false) String xp1gy,
                           @RequestParam(value = "xp2gy", required = false) String xp2gy,
                           @RequestParam(value = "cagysyh", required = false) String cagysyh,
                           @RequestParam(value = "cagy", required = false) String cagy,
                           @RequestParam(value = "xp1mybb", required = false) String xp1mybb,
                           @RequestParam(value = "xp1yhcxbb", required = false) String xp1yhcxbb,
                           @RequestParam(value = "xp2yhcxbb", required = false) String xp2yhcxbb,
                           @RequestParam(value = "stm32gjbb", required = false) String stm32gjbb,
                           @RequestParam(value = "ccrq", required = false) String ccrq,
                           @RequestParam(value = "dlbbb", required = false) String dlbbb,
                           @RequestParam(value = "cshrq", required = false) String cshrq,
                           @RequestParam(value = "czr", required = false) String czr) {
        LOG.info(String.format("------> calling /security-model.frm?method=init-model, xh=%s, lx = %s, qulx = %s, xp1gy = %s, xp2gy = %s," +
                        "cagysyh = %s, cagy = %s, xp1mybb = %s, xp1yhcxbb = %s, xp2yhcxbb = %s, stm32gjbb = %s, ccrq = %s, dlbbb = %s, cshrq = %s, czr = %s",
                xh, lx, qulx, xp1gy, xp2gy, cagysyh, cagy, xp1mybb, xp1yhcxbb, xp2yhcxbb, stm32gjbb, ccrq, dlbbb, cshrq, czr));
        try {
            return securityModelService.initModel(xh, lx, qulx, xp1gy, xp2gy, cagysyh, cagy, xp1mybb, xp1yhcxbb,
                    xp2yhcxbb, stm32gjbb, DateUtil.praseDate(ccrq,"yyyy-MM-dd"), dlbbb, cshrq, czr);
        } catch (Exception e) {
            LOG.error("初始化失败", e);
            return "Exception: " + e.getMessage();
        }
    }
    
    @RequestMapping(params = "method=update-model")
    @ResponseBody
    public String updateModel(@RequestParam(value = "xh", required = true) String xh,
                           @RequestParam(value = "lx", required = false) String lx,
                           @RequestParam(value = "qulx", required = false) String qulx,
                           @RequestParam(value = "xp1gy", required = false) String xp1gy,
                           @RequestParam(value = "xp2gy", required = false) String xp2gy,
                           @RequestParam(value = "cagysyh", required = false) String cagysyh,
                           @RequestParam(value = "cagy", required = false) String cagy,
                           @RequestParam(value = "xp1mybb", required = false) String xp1mybb,
                           @RequestParam(value = "xp1yhcxbb", required = false) String xp1yhcxbb,
                           @RequestParam(value = "xp2yhcxbb", required = false) String xp2yhcxbb,
                           @RequestParam(value = "stm32gjbb", required = false) String stm32gjbb,
                           @RequestParam(value = "ccrq", required = false) String ccrq,
                           @RequestParam(value = "dlbbb", required = false) String dlbbb,
                           @RequestParam(value = "cshrq", required = false) String cshrq,
                           @RequestParam(value = "czr", required = false) String czr) {
        LOG.info(String.format("------> calling /security-model.frm?method=update-model, xh=%s, lx = %s, qulx = %s, xp1gy = %s, xp2gy = %s," +
                        "cagysyh = %s, cagy = %s, xp1mybb = %s, xp1yhcxbb = %s, xp2yhcxbb = %s, stm32gjbb = %s, ccrq = %s, dlbbb = %s, cshrq = %s, czr = %s",
                xh, lx, qulx, xp1gy, xp2gy, cagysyh, cagy, xp1mybb, xp1yhcxbb, xp2yhcxbb, stm32gjbb, ccrq, dlbbb, cshrq, czr));
        try {
            return securityModelService.updateModel(xh, lx, qulx, xp1gy, xp2gy, cagysyh, cagy, xp1mybb, xp1yhcxbb,
                    xp2yhcxbb, stm32gjbb, DateUtil.praseDate(ccrq,"yyyy-MM-dd"), dlbbb, cshrq, czr);
        } catch (Exception e) {
            LOG.error("升级失败", e);
            return "Exception: " + e.getMessage();
        }
    }
    
    @RequestMapping(params = "method=query-model")
    @ResponseBody
    public String queryModel(@RequestParam(value = "xh", required = true) String xh) {
        try {
        	 LOG.info(String.format("------> calling /security-model.frm?method=query-model, xh=%s",
             xh));
        	 SecurityModel securityModel = securityModelService.queryById(xh);
        	 if(securityModel != null){
        		 return GsonHelper.getGson().toJson(
                         MapUtilities.buildMap("resultId", "00", "model", securityModel)); 
        	 }else{
        		 return GsonHelper.getGson().toJson(
                         MapUtilities.buildMap("resultId", "01", "resultMsg", "没有查到相关安全模块数据！")); 
        	 }
        } catch (Exception e) {
            LOG.error("查询失败", e);
            return GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "99", "resultMsg", e.getMessage())); 
        }
    }
    
    
    protected final String jspPath = "jsp_core/rfid/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
    
    @RequestMapping(params = "method=list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "xh", required = false) String xh,
            @RequestParam(value = "lx", required = false) String lx,
            @RequestParam(value = "ccrqks", required = false) String ccrqks,
            @RequestParam(value = "ccrqjs", required = false) String ccrqjs,
            @RequestParam(value = "cshrqks", required = false) String cshrqks,
            @RequestParam(value = "cshrqjs", required = false) String cshrqjs,
            @RequestParam(value = "czr", required = false) String czr,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize){

        LOG.info("------> display security-model.frm?method=list");
		try {
			UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);
            
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(xh)) {
                condition.put("xh", xh);
            }
            if (StringUtils.isNotEmpty(lx)) {
                condition.put("lx", lx);
            }
            if (StringUtils.isNotEmpty(czr)) {
                condition.put("czr", czr);
            }
            if (StringUtils.isNotEmpty(ccrqks)) {
                condition.put("ccrqks", ccrqks);
            }
            if (StringUtils.isNotEmpty(ccrqjs)) {
                condition.put("ccrqjs", ccrqjs);
            }
            if (StringUtils.isNotEmpty(cshrqks)) {
                condition.put("cshrqks", cshrqks);
            }
            if (StringUtils.isNotEmpty(cshrqjs)) {
                condition.put("cshrqjs", cshrqjs);
            }

			PageList<SecurityModel> queryList = securityModelService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			
			request.setAttribute("securityModelType",SecurityModelType.values());
			
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("condition", condition);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("query security list result fail ", e);
			e.printStackTrace();
		}
		
		return redirectMav("securityModel/main");
	}
    
    @RequestMapping(params = "method=detail")
    public ModelAndView editWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "xh", required = false) String xh){
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}

			if (StringUtils.isNotEmpty(xh)) {
				SecurityModel bean = this.securityModelService.queryById(xh);
				request.setAttribute("bean", bean);

				request.setAttribute("securityModelType",SecurityModelType.values());
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("securityModel/detail");
	}
    
    @RequestMapping(params = "method=query-cret-model")
    @ResponseBody
    public String queryCretModel(@RequestParam(value = "ssztbh", required = true) String ssztbh,
    		@RequestParam(value = "zsbh", required = true) String zsbh,
    		@RequestParam(value = "zt", required = false,defaultValue = "1") String zt) {
        try {
        	 LOG.info(String.format("------> calling /security-model.frm?method=query-cret-model, xh=%s,zt",
        			 ssztbh));
        	 Map<String, Object> condition = new HashMap<String, Object>();
             if (StringUtils.isNotEmpty(ssztbh)) {
                 condition.put("ssztbh", ssztbh);
                 condition.put("zt", zt);
             }
             if (StringUtils.isNotEmpty(zsbh)) {
                 condition.put("zsbh", zsbh);
             }
        	 List<ElectronicCertificate> certificates = electronicCertificateService.fetchByCondition(condition);
        	 if(!certificates.isEmpty()){
        		 return GsonHelper.getGson().toJson(
                         MapUtilities.buildMap("resultId", "00", "cert", certificates.get(0))); 
        	 }else{
        		 return GsonHelper.getGson().toJson(
                         MapUtilities.buildMap("resultId", "01", "resultMsg", "没有查到相关安全模块数据！")); 
        	 }
        } catch (Exception e) {
            LOG.error("查询失败", e);
            return GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "99", "resultMsg", e.getMessage())); 
        }
    }
}
