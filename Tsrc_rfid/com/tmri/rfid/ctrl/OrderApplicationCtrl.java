package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.OrderApplication;
import com.tmri.rfid.bean.ProductCategory;
import com.tmri.rfid.common.OrderApplicationStatus;
import com.tmri.rfid.ctrl.view.OrderApplicationView;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.OrderApplicationService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.MenuConstant;
import com.tmri.share.ora.bean.DbResult;
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
 * Created by Joey on 2015/9/23.
 */
@Controller
@RequestMapping("/order-application.frm")
public class OrderApplicationCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(OrderApplicationCtrl.class);

    public ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/orderApplication", mav);
    }

    @Resource
    private OrderApplicationService orderApplicationService;

    @Resource
    private ProductCategoryService productCategoryService;

    /**
     *
     * @param sqbm
     * @param jbr
     * @param zt
     * @param cplb
     * @param cpdm
     * @param type 1-∂©µ•≤È—Ø£¨2-∂©µ•…Û∫À
     * @param page
     * @param pageSize
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(params = "method=list")
    public ModelAndView list(@RequestParam(value = "sqbm", required = false) String sqbm,
                             @RequestParam(value = "jbr", required = false) String jbr,
                             @RequestParam(value = "zt", required = false) String zt,
                             @RequestParam(value = "cplb", required = false) String cplb,
                             @RequestParam(value = "cpdm", required = false) String cpdm,
                             @RequestParam(value = "type", defaultValue = "1") int type,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "30") int pageSize,
                             Model model, HttpServletRequest request) {
        LOG.info("------> display order-application.frm?method=list");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(sqbm)) {
                condition.put("sqbm", sqbm);
            }
            if (StringUtils.isNotEmpty(jbr)) {
                condition.put("jbr", jbr);
            }
            if (2 == type) {
                condition.put("zt", OrderApplicationStatus.SUBMIT.getStatus());
            } else {
                if (StringUtils.isNotEmpty(zt)) {
                    condition.put("zt", Integer.valueOf(zt));
                }
            }
            if (StringUtils.isNotEmpty(cplb)) {
                condition.put("cplb", Integer.valueOf(cplb));
            }
            if (StringUtils.isNotEmpty(cpdm)) {
                condition.put("cpdm", cpdm);
            }
            List<OrderApplicationView> orderApplications = orderApplicationService.list(condition, page, pageSize);
            PageList<OrderApplicationView> pageList = (PageList<OrderApplicationView>)orderApplications;
            Paginator paginator = pageList.getPaginator();

            List<Department> list = this.gDepartmentService.getAllXjDepartments(
                    userSession.getDepartment().getGlbm(), true);
            model.addAttribute("xjbm", list);
            model.addAttribute("queryList", pageList);
            model.addAttribute("controller", getPageInfo(paginator, request));
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("productCategories", productCategoryService.fetchAll());
            model.addAttribute("orderApplicationStatus", OrderApplicationStatus.values());
            model.addAttribute("condition", condition);
            model.addAttribute("type", type);
        } catch (Exception e) {
            LOG.error("display edit order application page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("main");
    }

    @RequestMapping(params="method=create")
    public ModelAndView create(Model model, HttpServletRequest request) {
        LOG.info("------> display order-application.frm?method=create");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("vestCapacity", InventoryService.VEST_CAPACITY);
            model.addAttribute("boxCapacity", InventoryService.BOX_CAPACITY);
            model.addAttribute("productCategories", productCategoryService.fetchAll());
        } catch (Exception e) {
            LOG.error("display edit order application page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("create");
    }

    @RequestMapping(params="method=create", method = RequestMethod.POST)
    @ResponseBody
    public String create(HttpServletRequest request, OrderApplication bean) {
        LOG.info("------> call order-application.frm?method=create(" + ToStringBuilder.reflectionToString(bean) + ")");
        try {
            gSystemService.doBeginCall(request, getClass().getName(), "");

            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");

            SysUser sysUser = userSession.getSysuser();
            bean.setSqbm(sysUser.getGlbm());
            bean.setJbr(sysUser.getXm());
            bean.setSqdh(orderApplicationService.generateOrderNumber(sysUser.getGlbm()));
            bean.setZt(OrderApplicationStatus.SUBMIT.getStatus());
            bean.setSl(bean.getSl()*InventoryService.VEST_CAPACITY*InventoryService.BOX_CAPACITY);

            orderApplicationService.create(bean);

            gSystemService.doEndCall(request, getClass().getName(), "",
                    MenuConstant.P_save);

            String result = GsonHelper.getGson().toJson(MapUtilities.buildMap("resultId", "00"));
            LOG.info("------> call order-application.frm?method=create result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("ÃÌº”∂©π∫…Í«Î ß∞‹", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }
    
    @RequestMapping(params="method=fetchbydgdh")
    @ResponseBody
    public String fetchbydgdh(HttpServletRequest request, @RequestParam(value = "dgdh", required = true) String dgdh) {
        LOG.info("------> call order-application.frm?method=fetchbydgdh(" + dgdh + ")");
        try {
        	UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
            	return GsonHelper.getBaseResultGson("99", "«Î÷ÿ–¬µ«¬º£°");
            }
        	
        	OrderApplication bean = orderApplicationService.fetchBySQDH(dgdh);
            String result = "";
            if(bean != null){
            	result = GsonHelper.getGson().toJson(MapUtilities.buildMap(
                        "resultId", "00", "resultMsg", bean));
            }else{
            	result = GsonHelper.getGson().toJson(MapUtilities.buildMap(
                        "resultId", "01", "resultMsg", "’“≤ªµΩ∏√∂©π∫µ•£°"));
            }
            
            LOG.info("------> call order-application.frm?method=fetchbydgdh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("ªÒ»°∂©µ• ß∞‹", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }

    /**
     *
     * @param sqdh
     * @param type 1-∂©µ•π‹¿Ì£¨2-∂©µ•…Û∫À
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(params="method=edit")
    public ModelAndView edit(@RequestParam(value = "sqdh") String sqdh,
                             @RequestParam(value = "type", defaultValue = "1") int type,
                             Model model, HttpServletRequest request) {
        LOG.info("------> display order-application.frm?method=edit(" + sqdh + "," + type + ")");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }

            OrderApplicationView orderApplicationView =
                    orderApplicationService.fetchBySQDH(sqdh);
            model.addAttribute("orderApplication", orderApplicationView);
            model.addAttribute("productCategories", productCategoryService.fetchAll());
            model.addAttribute("orderApplicationStatus", OrderApplicationStatus.values());
            model.addAttribute("type", type);
        } catch (Exception e) {
            LOG.error("display edit order application page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("edit");
    }

    @RequestMapping(params="method=edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(@RequestParam(value = "type", defaultValue = "1") int type,
                       HttpServletRequest request, OrderApplication bean) {
        LOG.info("------> call order-application.frm?method=edit(" + ToStringBuilder.reflectionToString(bean) + ")");
        try {
            gSystemService.doBeginCall(request, getClass().getName(), "");

            if (type == 2) {
                orderApplicationService.audit(bean.getSqdh(), bean.getZt(), bean.getShyj());
            } else {
                orderApplicationService.update(bean);
            }

            gSystemService.doEndCall(request, getClass().getName(), "",
                    MenuConstant.P_save);

            String result = GsonHelper.getGson().toJson(MapUtilities.buildMap("resultId", "00"));
            LOG.info("------> call order-application.frm?method=edit result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("∏¸–¬∂©π∫…Í«Î ß∞‹", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }

    @RequestMapping(params="method=delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "sqdh", required = true) String sqdh,
                         HttpServletRequest request) {
        LOG.info("------> call order-application.frm?method=delete(" + sqdh + ")");
        try {
            gSystemService.doBeginCall(request, getClass().getName(), "");

            orderApplicationService.delete(sqdh);

            gSystemService.doEndCall(request, getClass().getName(), "",
                    MenuConstant.P_save);

            String result = GsonHelper.getGson().toJson(MapUtilities.buildMap("resultId", "00"));
            LOG.info("------> call order-application.frm?method=delete result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("»°œ˚∂©π∫…Í«Î ß∞‹", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }

}
