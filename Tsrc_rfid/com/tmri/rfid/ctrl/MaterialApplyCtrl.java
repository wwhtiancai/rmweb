package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.MaterialApply;
import com.tmri.rfid.common.MaterialApplyStatus;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.MaterialApplyService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.JsonView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by st on 2015/10/30.
 */
@Controller
@RequestMapping("/material-apply.rfid")
public class MaterialApplyCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(MaterialApplyCtrl.class);

    @Resource
    private MaterialApplyService materialApplyService;
    @Resource
    private ProductCategoryService productCategoryService;

    public ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/materialApply/", mav);
    }

    @RequestMapping(params = "method=list")
    public ModelAndView list(@RequestParam(value = "type", required = true) int type,
							 @RequestParam(value = "jbrxm", required = false) String jbrxm,
                             @RequestParam(value = "zt", required = false) String zt,
                             @RequestParam(value = "cplb", required = false) String cplb,
                             @RequestParam(value = "cpdm", required = false) String cpdm,
                             @RequestParam(value = "slks", required = false) String slks,
                             @RequestParam(value = "sljs", required = false) String sljs,
                             @RequestParam(value = "dgrqks", required = false) String dgrqks,
                             @RequestParam(value = "dgrqjs", required = false) String dgrqjs,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                             Model model, HttpServletRequest request) {
        LOG.info(String.format("------> display /materials-apply.rfid?method=list(jbrxm=%s,zt=%s,cplb=%s,cpdm=%s,dgrqks=%s,dgrqjs=%s",
        		jbrxm, zt, cplb, cpdm, dgrqks, dgrqjs));
        try {
        	model.addAttribute("type",type);//1-订购申请详情 2-带交付的详情查询 3-带撤销的详情查询
        	
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("type", type);
            if (StringUtils.isNotEmpty(jbrxm)) {
                condition.put("jbrxm", jbrxm.trim());
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", zt);
            }else{
            	if(type == 2 || type == 3){
            		condition.put("zt", 1);
            	}
            }
            if (StringUtils.isNotEmpty(cplb)) {
                condition.put("cplb", cplb);
            }
            if (StringUtils.isNotEmpty(cpdm)) {
                condition.put("cpdm", cpdm);
            }
            if (StringUtils.isNotEmpty(slks)) {
                condition.put("slks", Integer.parseInt(slks));
            }
            if (StringUtils.isNotEmpty(sljs)) {
                condition.put("sljs", Integer.parseInt(sljs));
            }
            if (StringUtils.isNotEmpty(dgrqks)) {
                condition.put("dgrqks", dgrqks);
            }
            if (StringUtils.isNotEmpty(dgrqjs)) {
                condition.put("dgrqjs", dgrqjs);
            }
            PageList<MaterialApply> queryList = materialApplyService.queryList(page, pageSize, condition);
            Paginator paginator = queryList.getPaginator();
            
            model.addAttribute("queryList", queryList);
            model.addAttribute("controller", getPageInfo(paginator, request));
            
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("productCategories", productCategoryService.fetchAll());
            model.addAttribute("materialApplyStatus", MaterialApplyStatus.values());
            model.addAttribute("condition", condition);
            List<Department> list = this.gDepartmentService.getAllXjDepartments(
                    dept.getGlbm(), true);
            model.addAttribute("xjbm", list);
        } catch (Exception e) {
            LOG.error("------> display /material-apply.rfid?method=list fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("main");
    }

    @RequestMapping(params = "method=create")
    public ModelAndView create(Model model, HttpServletRequest request) {
        LOG.info("------> display /material-apply.rfid?method=create");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("productCategories", productCategoryService.fetchAll());
            model.addAttribute("vestCapacity", InventoryService.VEST_CAPACITY);
            model.addAttribute("boxCapacity", InventoryService.BOX_CAPACITY);
        } catch (Exception e) {
            LOG.error("display create material apply page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("create");
    }

    @RequestMapping(params = "method=save", method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam(value = "cpdm", required = true) String cpdm,
    		MaterialApply materialApply, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-apply.rfid?method=save(bean=%s)",
                ToStringBuilder.reflectionToString(materialApply)));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
            	return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            //Department dept = userSession.getDepartment();
            
            SysUser sysUser = userSession.getSysuser();
            materialApply.setDgdh(materialApplyService.generateOrderNumber(cpdm));
            materialApply.setJbr(sysUser.getYhdh());
            materialApply.setZt(MaterialApplyStatus.SUBMIT.getStatus());
            materialApply.setSl(materialApply.getSl()*InventoryService.VEST_CAPACITY*InventoryService.BOX_CAPACITY);
            
            materialApplyService.create(materialApply);
            result = GsonHelper.getBaseResultGson("00", "创建成功");
        } catch (Exception e) {
            LOG.error("------> post /material-apply.rfid?method=save exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(params = "method=view")
    public ModelAndView view(@RequestParam(value = "type") String type,
    					@RequestParam(value = "dgdh") String dgdh, Model model,
                             HttpServletRequest request) {
        LOG.info("------> display /material-apply.rfid?method=view");
        try {
        	model.addAttribute("type", type);
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("materialApplyStatus", MaterialApplyStatus.values());
            model.addAttribute("materialApply", materialApplyService.fetchByDGDH(dgdh));
            
        } catch (Exception e) {
            LOG.error("display edit material apply page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("view");
    }

    @RequestMapping(params = "method=edit")
    public ModelAndView edit(@RequestParam(value = "dgdh") String dgdh, Model model,
                             HttpServletRequest request) {
        LOG.info("------> display /material-apply.rfid?method=edit");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("productCategories", productCategoryService.fetchAll());
            model.addAttribute("materialApplyStatus", MaterialApplyStatus.values());
            model.addAttribute("materialApply", materialApplyService.fetchByDGDH(dgdh));
            
        } catch (Exception e) {
            LOG.error("display edit material apply page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("edit");
    }

    @RequestMapping(params = "method=edit", method = RequestMethod.POST)
    public ModelAndView edit(MaterialApply materialApply, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-apply.rfid?method=edit(bean=%s)",
                ToStringBuilder.reflectionToString(materialApply)));
        ModelAndView view = new ModelAndView(JsonView.instance);
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            materialApplyService.update(materialApply);
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("00", "更新成功"));
        } catch (Exception e) {
            LOG.error("------> post /material-apply.rfid?method=save exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("99", e.getMessage()));
        }
        return view;
    }
    
    @RequestMapping(params = "method=delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "dgdh") String dgdh, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-apply.rfid?method=delete(dgdh=%s)", dgdh));
        ModelAndView view = new ModelAndView(JsonView.instance);
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            materialApplyService.delete(dgdh);
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("00", "取消成功"));
        } catch (Exception e) {
            LOG.error("------> post /material-apply.rfid?method=delete exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("99", e.getMessage()));
        }
        return view;
    }
    
    @RequestMapping(params = "method=delivery", method = RequestMethod.POST)
    @ResponseBody
    public String delivery(@RequestParam(value = "dgdh") String dgdh, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-apply.rfid?method=delivery(dgdh=%s)", dgdh));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
            	return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            materialApplyService.delivery(dgdh);
            result = GsonHelper.getBaseResultGson("00", "交付成功");
        } catch (Exception e) {
            LOG.error("------> post /material-apply.rfid?method=delivery exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
}
