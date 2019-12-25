package com.tmri.rfid.endpoints;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.ProductionTask;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.common.ProductionTaskStatus;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductionTaskService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.service.LogService;
import com.tmri.share.frm.util.FuncUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/12/25.
 */
@Controller
@RequestMapping("/be/production-task.rfid")
public class ProductionTaskEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(ProductionTaskEndPoint.class);

    @Resource
    private ProductionTaskService productionTaskService;

    @Resource
    private InventoryService inventoryService;

    @Resource
    private LogService logService;

    @RequestMapping(params = "method=start")
    @ResponseBody
    public String startTask(@RequestParam(value = "rwh", required = true) String rwh,
                            HttpServletRequest request) {
        LOG.info("------> calling /be/production-task.rfid?method=start rwh = " + rwh);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            ProductionTask productionTask = productionTaskService.fetchByRwh(rwh);
            if (ProductionTaskStatus.PRODUCED.getStatus() == productionTask.getZt()) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "任务已完成");
            } else {
                if (ProductionTaskStatus.PLANNED.getStatus() == productionTask.getZt()) {
                    productionTaskService.startTask(rwh);
                }
                PageBounds pageBounds = new PageBounds();
                Order order = new Order("qskh", Order.Direction.ASC, null);
                List<Order> orders = new ArrayList<Order>(1);
                orders.add(order);
                pageBounds.setOrders(orders);
                List<Inventory> subTasks = inventoryService.fetchByCondition(
                        MapUtilities.buildMap("rwh", rwh), pageBounds);
                String lastFinishedBzhh = inventoryService.findLastFinished(rwh);
                resultMap.put("resultId", "00");
                resultMap.put("tasks", subTasks);
                resultMap.put("bzhh", lastFinishedBzhh);
                resultMap.put("cpdm", subTasks.get(0).getCpdm());
            }
        } catch (Exception e) {
            LOG.error("执行任务失败", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/production-task.rfid?method=start result=" + result);
        return result;
    }

    @RequestMapping(params = "method=finish", method = RequestMethod.POST)
    @ResponseBody
    public String finishTask(@RequestParam(value = "rwh", required = true) String rwh,
                             HttpServletRequest request) {
        LOG.info(String.format("------> call /be/production-task.rfid?method=finish(rwh=%s", rwh));
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            productionTaskService.finishTask(rwh);
            resultMap.put("resultId", "00");
        } catch (Exception e) {
            LOG.error("任务未完成", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call /be/production-task.rfid?method=finish result = " + result);
        return result;
    }

    @RequestMapping(params = "method=finish-sub-task", method = RequestMethod.POST)
    @ResponseBody
    public String finishSubTask(@RequestParam(value = "bzhh", required = true) String bzhh,
                                HttpServletRequest request) {
        LOG.info(String.format("------> call /be/production-task.rfid?method=finish-sub-task(bzhh=%s", bzhh));
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            productionTaskService.finishSubTask(bzhh);
            resultMap.put("resultId", "00");
        } catch (Exception e) {
            LOG.error("子任务未完成", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call /be/production-task.rfid?method=finish-sub-task result = " + result);
        return result;
    }
}
