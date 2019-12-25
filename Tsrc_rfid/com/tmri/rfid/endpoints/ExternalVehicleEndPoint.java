package com.tmri.rfid.endpoints;

import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.RemoteVehicleService;
import com.tmri.rfid.service.VehicleService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
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
 * Created by Joey on 2017/3/16.
 */
@Controller
@RequestMapping("/ex/vehicle.rfid")
public class ExternalVehicleEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(ExternalVehicleEndPoint.class);

    @Resource
    private VehicleService vehicleService;

    @Resource
    private EriService eriService;

    @RequestMapping(params = "method=fetch-by-hphm")
    @ResponseBody
    public String fetchEriByHphm(@RequestParam(value = "hphm") String hphm,
                                 @RequestParam(value = "hpzl") String hpzl) {
        LOG.info(String.format("------> calling /ex/vehicle.rfid?method=fetch-by-hphm hphm = %s, hpzl = %s", hphm, hpzl));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Vehicle vehicle = vehicleService.fetchByHphm(hpzl, hphm);
            if (vehicle == null) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "未找到对应车辆");
            } else {
                resultMap.put("resultId", "00");
                resultMap.put("vehicle", vehicle);
            }
        } catch (Exception e) {
            LOG.error("------> calling /ex/vehicle.rfid?method=fetch-by-hphm fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", "异常");
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /ex/vehicle.rfid?method=fetch-by-hphm result = " + result);
        return result;
    }
}
