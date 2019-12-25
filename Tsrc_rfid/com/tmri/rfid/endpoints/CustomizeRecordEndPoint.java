package com.tmri.rfid.endpoints;

import com.tmri.rfid.service.EriCustomizeRecordService;
import com.tmri.rfid.util.GsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Joey on 2017/6/1.
 */
@Controller
@RequestMapping("/be/customize-record.rfid")
public class CustomizeRecordEndPoint {

    public static final Logger LOG = LoggerFactory.getLogger(CustomizeRecordEndPoint.class);

    @Resource
    private EriCustomizeRecordService eriCustomizeRecordService;

    @RequestMapping(params = "method=cancel", method= RequestMethod.POST)
    @ResponseBody
    public String cancel(@RequestParam(value = "id", required = true) Long id) {
        String uuid = UUID.randomUUID().toString();
        LOG.debug("------>(?) calling /be/customize-record.rfid?method=cancel&id=?", uuid, id);
        Map resultMap = new HashMap();
        try {
            if (eriCustomizeRecordService.cancel(id)) {
                resultMap.put("resultId", "00");
            } else {
                resultMap.put("resultId", "01");
            }
        } catch (Exception e) {
            LOG.error("------>(?) error:", uuid, e);
            resultMap.put("resultId", "99");
            resultMap.put("exception", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.debug("------>(?) result = ?", uuid, result);
        return result;
    }

}
