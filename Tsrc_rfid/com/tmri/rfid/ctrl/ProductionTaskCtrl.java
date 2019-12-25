package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.ProductionTask;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.common.ProductionTaskStatus;
import com.tmri.rfid.ctrl.view.ProductionTaskView;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.service.ProductService;
import com.tmri.rfid.service.ProductionTaskService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.FuncUtil;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Joey on 2015/10/16.
 */
@Controller
@RequestMapping("/production-task.rfid")
public class ProductionTaskCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(ProductionTaskCtrl.class);

    @Resource
    private InventoryService inventoryService;

    @Resource
    private ProductionTaskService productionTaskService;

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductService productService;

    private ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/productionTask", mav);
    }

    @RequestMapping(params = "method=list")
    public ModelAndView list(@RequestParam(value = "rwh", required = false) String rwh,
                             @RequestParam(value = "jbr", required = false) String jbr,
                             @RequestParam(value = "jhrqks", required = false) String jhrqks,
                             @RequestParam(value = "jhrqjs", required = false) String jhrqjs,
                             @RequestParam(value = "zt", required = false) String zt,
                             @RequestParam(value = "cpdm", required = false) String cpdm,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                             Model model, HttpServletRequest request) {
        LOG.info(String.format("------> display /production-task.rfid?method=list(%s)", ""));
        try {
            Map<Object, Object> condition = new HashMap<Object, Object>();
            if (StringUtils.isNotEmpty(rwh)) {
                condition.put("rwh", rwh);
            }
            if (StringUtils.isNotEmpty(jbr)) {
                condition.put("jbr", jbr);
            }
            if (StringUtils.isNotEmpty(jhrqks)) {
                Calendar start = Calendar.getInstance();
                start.setTime(DateUtil.praseDate(jhrqks));
                start.set(Calendar.HOUR, 0);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                condition.put("jhrqks", start.getTime());
            }
            if (StringUtils.isNotEmpty(jhrqjs)) {
                Calendar end = Calendar.getInstance();
                end.setTime(DateUtil.praseDate(jhrqjs));
                end.set(Calendar.HOUR, 23);
                end.set(Calendar.MINUTE, 59);
                end.set(Calendar.SECOND, 59);
                condition.put("jhrqjs", end.getTime());
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", Integer.valueOf(zt));
            }
            if (StringUtils.isNotEmpty(cpdm)) {
                condition.put("cpdm", cpdm);
            }
            List<ProductionTaskView> tasks = productionTaskService.fetchByCondition(condition, page, pageSize);
            PageList<ProductionTaskView> pageList = (PageList<ProductionTaskView>)tasks;
            Paginator paginator = pageList.getPaginator();
            model.addAttribute("controller", getPageInfo(paginator, request));
            model.addAttribute("queryList", tasks);
            model.addAttribute("condition", condition);
            model.addAttribute("productionTaskStatus", ProductionTaskStatus.values());
            model.addAttribute("productionTaskType", gSysparaCodeService.getCodes("90", "0001"));
            model.addAttribute("products", productService.listAll());
        } catch (Exception e) {
            LOG.error("------> display /production-task.rfid?method=list fail", e);
        }
        return redirectMav("main");
    }

    @RequestMapping(params = "method=create")
    public ModelAndView create(Model model) {
        LOG.info("------> display /production-task.rfid?method=create");
        model.addAttribute("provinces", gSysparaCodeService.getCodes("00", "0032"));
        model.addAttribute("vestCapacity", InventoryService.VEST_CAPACITY);
        model.addAttribute("boxCapacity", InventoryService.BOX_CAPACITY);
        model.addAttribute("productionTaskStatus", ProductionTaskStatus.values());
        model.addAttribute("productionTaskType", gSysparaCodeService.getCodes("90", "0001"));
        model.addAttribute("productCategories", productCategoryService.fetchAll());
        return redirectMav("create");
    }

    @RequestMapping(params = "method=create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestParam("sf") String sf, @RequestParam("qskh") String qskh,
                         @RequestParam("sl") int sl, @RequestParam("rwdm") String rwdm,
                         @RequestParam(value = "bz", required = false) String bz,
                         @RequestParam(value = "cpdm", required = true) String cpdm,
                         HttpServletRequest request) {
        LOG.info(String.format("------> post /production-task.rfid?method=create(sf=%s, qskh=%s, sl=%s)", sf, qskh, sl));
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            List<String> occupiedKh = inventoryService.findOccupiedKh(sf, Integer.valueOf(qskh.substring(2)), sl);
            String result = "";
            if (occupiedKh != null && !occupiedKh.isEmpty()) {
                result = GsonHelper.getGson().toJson(
                        MapUtilities.buildMap("resultId", "01", "khs", occupiedKh));
            } else {
                ProductionTask productionTask = new ProductionTask();
                productionTask.setRwdm(rwdm);
                productionTask.setJhrq(new Date());
                productionTask.setJbr(userSession.getSysuser().getXm());
                productionTask.setBz(bz);
                productionTaskService.create(productionTask, sf, Integer.valueOf(qskh.substring(2)), sl, cpdm);
                result = GsonHelper.getGson().toJson(
                        MapUtilities.buildMap("resultId", "00"));
            }
            LOG.info("------> call inventory.frm?method=check-kh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("创建生产任务失败", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "99", "resultMsg", e.getMessage()));
        }
    }

    @RequestMapping(params = "method=edit")
    public ModelAndView edit(@RequestParam("rwh") String rwh,
                             Model model, HttpServletRequest request) {
        LOG.info(String.format("------> display /production-task.rfid?method=edit(rwh=%s)", rwh));
        try {
            ProductionTask productionTask = productionTaskService.fetchByRwh(rwh);
            PageBounds pageBounds = new PageBounds(1, 999999);
            List<Order> orders = new ArrayList<Order>();
            Order order = new Order("bzhh", Order.Direction.ASC, null);
            orders.add(order);
            pageBounds.setOrders(orders);
            List<Inventory> inventories = inventoryService.fetchByCondition(MapUtilities.buildMap("rwh", rwh), pageBounds);
            model.addAttribute("qskh", inventories.get(0).getQskh());
            model.addAttribute("zzkh", inventories.get(inventories.size() - 1).getZzkh());
            model.addAttribute("task", productionTask);
            model.addAttribute("inventories", inventories);
            model.addAttribute("productionTaskStatus", ProductionTaskStatus.values());
            model.addAttribute("inventoryStatus", InventoryStatus.values());
        } catch (Exception e) {
            LOG.error("------> display /production-task.rfid?method=edit fail", e);
        }
        return redirectMav("edit");
    }

}
