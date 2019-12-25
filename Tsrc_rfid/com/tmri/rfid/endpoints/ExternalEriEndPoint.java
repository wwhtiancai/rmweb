package com.tmri.rfid.endpoints;

import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleInfo;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.RemoteVehicleService;
import com.tmri.rfid.service.VehicleLogService;
import com.tmri.rfid.service.VehicleService;
import com.tmri.rfid.util.GsonHelper;
import org.apache.commons.lang.StringUtils;
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
 * Created by Joey on 2016-06-06.
 */
@Controller
@RequestMapping("/ex/eri.rfid")
public class ExternalEriEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(ExternalEriEndPoint.class);

    @Resource
    private VehicleService vehicleService;

    @Resource
    private VehicleLogService vehicleLogService;

    @Resource
    private EriService eriService;

    @RequestMapping(params = "method=fetch-by-hphm")
    @ResponseBody
    public String fetchEriByHphm(@RequestParam(value = "fzjg", required = true) String fzjg,
                                 @RequestParam(value = "hphm", required = true) String hphm,
                                 @RequestParam(value = "hpzl", required = true) String hpzl) {
        LOG.info(String.format("------> calling /ex/eri.rfid?method=fetch-by-hphm fzjg = %s, hphm = %s, hpzl = %s", fzjg, hphm, hpzl));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isEmpty(fzjg) || StringUtils.isEmpty(hphm) || StringUtils.isEmpty(hpzl)) {
                resultMap.put("resultId", "11");
                resultMap.put("resultMsg", "传入参数有误");
            } else {
                Vehicle vehicle = vehicleService.synchronize(null, fzjg, hphm, hpzl,null);
                if (vehicle != null && StringUtils.isNotEmpty(vehicle.getXh())) {
                    Eri eri = eriService.fetchByJdcxh(vehicle.getXh());
                    if (eri != null) {
                        VehicleInfo old = vehicleLogService.fetchBoundByTid(eri.getTid());
                        if (!old.getHphm().equalsIgnoreCase(vehicle.getHphm())
                                || !old.getHpzl().equalsIgnoreCase(vehicle.getHpzl())
                                || !old.getFzjg().equalsIgnoreCase(vehicle.getFzjg())) {
                            resultMap.put("resultId", "03");
                            resultMap.put("resultMsg", "号牌号码发生变更");
                        } else {
                            resultMap.put("resultId", "00");
                            resultMap.put("kh", eri.getKh());
                            resultMap.put("tid", eri.getTid());
                        }
                    } else {
                        resultMap.put("resultId", "01");
                        resultMap.put("resultMsg", "未制卡");
                    }
                } else {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "车辆信息有误");
                }
            }
        } catch (Exception e) {
            LOG.error("------> calling /ex/eri.rfid?method=fetch-by-hphm fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", "异常");
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /ex/eri.rfid?method=fetch-by-hphm result = " + result);
        return result;
    }

}
