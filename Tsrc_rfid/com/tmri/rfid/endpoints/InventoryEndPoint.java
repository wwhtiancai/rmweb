package com.tmri.rfid.endpoints;

import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.util.GsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/12/4.
 */
@Controller
@RequestMapping("/be/inventory.rfid")
public class InventoryEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(InventoryEndPoint.class);

    @Resource
    private InventoryService inventoryService;


    @RequestMapping(params = "method=query-available-kh")
    @ResponseBody
    public String queryAvailableKh(@RequestParam("sf") String sf) {
        LOG.info(String.format("------> call /inventory.frm?method=query-available-kh(sf=%s)", sf));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map> availableKh = inventoryService.findAvailableKh(sf);
            resultMap.put("resultId", "00");
            resultMap.put("availableKh", availableKh);
        } catch (Exception e) {
            LOG.error("²éÕÒ¿ÉÓÃ¿¨ºÅÊ§°Ü", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call /inventory.frm?method=query-available-kh result = " + result);
        return result;
    }

    @RequestMapping(params = "method=query-kc")
    @ResponseBody
    public String queryKc() {
        LOG.info(String.format("------> call /inventory.frm?method=query-kc"));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            int kc = inventoryService.queryKc(UserState.getUser().getGlbm());
            resultMap.put("resultId", "00");
            resultMap.put("kc", kc);
        } catch (Exception e) {
            LOG.error("²éÑ¯¿â´æ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call /inventory.frm?method=query-kc result = " + result);
        return result;
    }

}
