package com.tmri.rfid.endpoints;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.CustomizeTaskStatus;
import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.ctrl.view.CustomizeTaskView;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2017/6/28.
 */
@RequestMapping("/be/batch-task.rfid")
@Controller
public class BatchTaskEndPoint {

    public static final Logger LOG = LoggerFactory.getLogger(BatchTaskEndPoint.class);

    @Resource
    private BatchTaskService batchTaskService;

    @Resource
    private EriService eriService;

    @Resource
    private VehicleLogService vehicleLogService;

    @Resource
    private CustomizeTaskService customizeTaskService;

    @Resource
    private VehicleService vehicleService;

    @RequestMapping(params = "method=list")
    @ResponseBody
    public String list(@RequestParam(value = "page") int page,
                       @RequestParam(value = "pageSize") int pageSize) {
        LOG.info("------> calling /be/batch-task.rfid?method=list");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<BatchTask> taskList = batchTaskService.fetchByCondition(
                    MapUtilities.buildMap("zt", 0, "czr", UserState.getUser().getYhdh()), page, pageSize);
            PageList<BatchTask> pageList = (PageList<BatchTask>) taskList;
            Paginator paginator = pageList.getPaginator();
            resultMap.put("resultId", "00");
            resultMap.put("list", taskList);
            resultMap.put("page", paginator.getPage());
            resultMap.put("totalPages", paginator.getTotalPages());
            resultMap.put("totalCount", paginator.getTotalCount());
        } catch (Exception e) {
            LOG.error("------> calling /be/batch-task.rfid?method=list error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/batch-task.rfid?method=list result = " + result);
        return result;
    }

    @RequestMapping(params = "method=delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "xh") Long xh) {
        LOG.info("------> calling /be/batch-task.rfid?method=delete");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (batchTaskService.updateByXh(xh, 2)) {
                resultMap.put("resultId", "00");
            } else {
                resultMap.put("resultId", "01");
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/batch-task.rfid?method=delete error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/batch-task.rfid?method=delete result = " + result);
        return result;
    }

    @RequestMapping(params = "method=update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestParam(value = "xh") Long xh) {
        LOG.info("------> calling /be/batch-task.rfid?method=update");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (batchTaskService.updateByXh(xh, 1)) {
                resultMap.put("resultId", "00");
            } else {
                resultMap.put("resultId", "01");
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/batch-task.rfid?method=update error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/batch-task.rfid?method=update result = " + result);
        return result;
    }

    @RequestMapping(params = "method=fetch-task")
    @ResponseBody
    public String fetchTask(@RequestParam(value = "xh") Long xh) {
        LOG.info("------> calling /be/batch-task.rfid?method=fetch-task");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            BatchTask batchTask = batchTaskService.fetchByXh(xh);
            Eri eri = eriService.fetchByVehicle(batchTask.getHphm().substring(0, 2), batchTask.getHphm().substring(1), batchTask.getHpzl());
            resultMap.put("resultId", "00");
            int type;
            if (eri != null) {
                type = CustomizeTaskType.CHANGE.getType();
                resultMap.put("eri", eri);
            } else {
                type = CustomizeTaskType.NEW.getType();
            }
            Vehicle vehicle = vehicleService.synchronize(null, batchTask.getHphm().substring(0, 2), batchTask.getHphm().substring(1), batchTask.getHpzl(),batchTask.getCllx());

            CustomizeTask customizeTask = customizeTaskService.fetch(vehicle, type, null);
            resultMap.put("task", customizeTask);
            resultMap.put("vehicle", vehicleService.translate(vehicle));
        } catch (Exception e) {
            LOG.error("------> calling /be/batch-task.rfid?method=fetch-task error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/batch-task.rfid?method=fetch-task result = " + result);
        return result;
    }

}
