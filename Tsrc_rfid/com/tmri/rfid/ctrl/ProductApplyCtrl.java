package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.ProductApply;
import com.tmri.rfid.bean.ProductApplyDetail;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductApplyDetailService;
import com.tmri.rfid.service.ProductApplyService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.JsonView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/9.
 */
@Controller
@RequestMapping("/product-apply.rfid")
public class ProductApplyCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(ProductApplyCtrl.class);

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductApplyService productApplyService;

    @Resource
    private ProductApplyDetailService productApplyDetailService;

    @Resource
    private InventoryService inventoryService;

    public ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/productApply", mav);
    }

    @RequestMapping(params = "method=list")
    public ModelAndView list(@RequestParam(value = "lydh", required = false) String lydh,
                             @RequestParam(value = "lybm", required = false) String lybm,
                             @RequestParam(value = "lyr", required = false) String lyr,
                             @RequestParam(value = "lyrqks", required = false) String lyrqks,
                             @RequestParam(value = "lyrqjs", required = false) String lyrqjs,
                             @RequestParam(value = "glbm", required = false) String glbm,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "30") int pageSize,
                             Model model, HttpServletRequest request) {
        LOG.info(String.format("------> display /product-apply.rfid?method=list(lydh=%s,lybm=%s,lyr=%s,lyrqks=%s,lyrqjs=%s",
                lydh, lybm, lyr, lyrqks, lyrqjs));
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(lydh)) {
                condition.put("lydh", lydh);
            }
            if (StringUtils.isNotEmpty(lybm)) {
                condition.put("lybm", lybm);
            }
            if (StringUtils.isNotEmpty(lyr)) {
                condition.put("lyr", lyr);
            }
            if (StringUtils.isNotEmpty(lyrqks)) {
                condition.put("lyrqks", DateUtil.praseDate(lyrqks));
            }
            if (StringUtils.isNotEmpty(lyrqjs)) {
                condition.put("lyrqjs", DateUtil.praseDate(lyrqjs));
            }
            if (StringUtils.isNotEmpty(glbm)) {
                condition.put("glbm", glbm);
            }
            List<ProductApply> productApplyEntries = productApplyService.list(condition, page, pageSize);
            PageList<ProductApply> pageList = (PageList<ProductApply>) productApplyEntries;
            Paginator paginator = pageList.getPaginator();


            model.addAttribute("queryList", pageList);
            model.addAttribute("controller", getPageInfo(paginator, request));
            Department dept = userSession.getDepartment();
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("productCategories", productCategoryService.fetchAll());
            model.addAttribute("condition", condition);
            List<Department> list = this.gDepartmentService.getAllXjDepartments(
                    dept.getGlbm(), true);
            model.addAttribute("xjbm", list);
        } catch (Exception e) {
            LOG.error("------> display /product-apply.rfid?method=list fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("main");
    }

    @RequestMapping(params = "method=create")
    public ModelAndView create(Model model, HttpServletRequest request) {
        LOG.info("------> display /product-apply.rfid?method=create");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Department dept = userSession.getDepartment();
            List<Department> list = this.gDepartmentService.getAllXjDepartments(
                    dept.getGlbm(), false);
            model.addAttribute("xjbm", list);
            model.addAttribute("department", dept);
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("productCategories", productCategoryService.fetchAll());
        } catch (Exception e) {
            LOG.error("display create product apply page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("create");
    }

    @RequestMapping(params = "method=create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam(value = "bzhs") String[] bzhs,
                               ProductApply productApply, HttpServletRequest request) {
        LOG.info(String.format("------> post /product-apply.rfid?method=create(bean=%s,bzhs=%s)",
                ToStringBuilder.reflectionToString(productApply), bzhs));
        ModelAndView view = new ModelAndView(JsonView.instance);
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            Department dept = userSession.getDepartment();
            productApply.setGlbm(dept.getGlbm());
            productApply.setJbr(UserState.getUser().getYhdh());
            productApplyService.create(productApply, bzhs);
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("00", "创建成功"));
        } catch (Exception e) {
            LOG.error("------> post /product-apply.rfid?method=create exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("99", e.getMessage()));
        }
        return view;
    }

    @RequestMapping(params = "method=edit")
    public ModelAndView edit(@RequestParam(value = "lydh") String lydh, Model model,
                             HttpServletRequest request) {
        LOG.info("------> display /product-apply.rfid?method=edit");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            model.addAttribute("productApply", productApplyService.fetchByLYDH(lydh));
            List<ProductApplyDetail> productApplyDetails =
                    productApplyDetailService.fetchByCondition(MapUtilities.buildMap("lydh", lydh));
            Map<String, List<Inventory>> detailMap = new HashMap<String, List<Inventory>>();
            for (ProductApplyDetail detail : productApplyDetails) {
                if (detail.getBzh().startsWith(InventoryService.BOX_NUMBER_PREFIX)) {
                    detailMap.put(detail.getBzh(), inventoryService.fetchByCondition(
                            MapUtilities.buildMap("bzhh", detail.getBzh())));
                } else if (detail.getBzh().startsWith(InventoryService.VEST_NUMBER_PREFIX)) {
                    detailMap.put(detail.getBzh(), inventoryService.fetchByCondition(
                            MapUtilities.buildMap("bzxh", detail.getBzh())));
                }
            }
            
            if(productApplyDetails.size() > 0){
            	String firstBzh = productApplyDetails.get(0).getBzh();
            	if(detailMap.get(firstBzh).size() > 0){
                	Inventory firstDetail = detailMap.get(firstBzh).get(0);
                    String cplbmc = firstDetail.getCplbmc();
                    String cpmc = firstDetail.getCpmc();
                    model.addAttribute("cpObj", MapUtilities.buildMap("cplbmc",cplbmc,"cpmc",cpmc));
            	}
            }
            
            model.addAttribute("productApplyDetails", productApplyDetails);
            model.addAttribute("detailMap", detailMap);
        } catch (Exception e) {
            LOG.error("display edit order application page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("edit");
    }

}
