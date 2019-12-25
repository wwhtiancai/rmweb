package com.tmri.rfid.endpoints;

import com.tmri.rfid.bean.Device;
import com.tmri.rfid.service.DeviceService;
import com.tmri.rfid.util.GsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2016/1/8.
 */
@Controller
@RequestMapping("/be/device.rfid")
public class DeviceEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(DeviceEndPoint.class);

    @Resource
    private DeviceService deviceService;

    @RequestMapping(params = "method=fetch-by-mac")
    @ResponseBody
    public String fetchByMac(@RequestParam(value = "mac", required = true) String mac) {
        LOG.info("------>calling /be/device.rfid?method=fetch-by-mac mac=" +mac);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Device device = deviceService.fetchByMac(mac);
            if (device != null) {
                resultMap.put("resultId", "00");
                resultMap.put("device", device);
            } else {
                resultMap.put("resultId", "01");
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/device.rfid?method=fetch-by-mac fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/device.rfid?method=fetch-by-mac result = " + result);
        return result;
    }
}
