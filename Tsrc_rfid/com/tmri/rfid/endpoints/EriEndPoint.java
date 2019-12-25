package com.tmri.rfid.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.EriStatus;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.service.GSysparaCodeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/12/30.
 */
@Controller
@RequestMapping("/be/eri.rfid")
public class EriEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(EriEndPoint.class);

    @Resource
    private EriService eriService;

    @Resource
    private InventoryService inventoryService;

    @Resource
    private LogService logService;

    @Resource
    private GSysparaCodeService gSysparaCodeService;

    @Resource
    private VehicleLogService vehicleLogService;

    @Resource
    private VehicleService vehicleService;

    @Resource
    private CustomizeTaskService customizeTaskService;

    @RequestMapping(params = "method=factory-init-card-result")
    @ResponseBody
    public String factoryInitCardResult(@RequestParam(value = "tid", required = true) String tid,
                                        @RequestParam(value = "result", required = true) int result,
                                        @RequestParam(value = "bzhh", required = false) String bzhh,
                                        @RequestParam(value = "sbyy", required = false) String sbyy) {
        LOG.info(String.format("------> calling /be/eri.rfid?method=factory-init-card-result, tid = %s, result = %s, sbyy = %s", tid, result, sbyy));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            eriService.saveFactoryInitializeResult(tid, result, bzhh, sbyy);
            resultMap.put("resultId", "00");
        } catch (Exception e) {
            LOG.error("update init card result fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultGson = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=factory-init-card-result result = " + resultGson);
        return resultGson;
    }

    /**
     * 初始化电子标识接口
     * @param tid 电子标识TID
     * @param kh 电子标识卡号，通过生产任务预生成
     * @param sbxh 设备序号
     * @param gwh 工位号
     * @param czr 操作人
     * @param rwh 生产任务号
     * @param test 是否生成测试卡
     * @return
     */
    @RequestMapping(params = "method=factory-init-card", method= RequestMethod.POST)
    @ResponseBody
    public String factoryInitCard(@RequestParam(value = "tid", required = true) String tid,
                                  @RequestParam(value = "kh", required = true) String kh,
                                  @RequestParam(value = "bzhh", required = true) String bzhh,
                                  @RequestParam(value = "sbxh", required = true) String sbxh,
                                  @RequestParam(value = "gwh", required = false) String gwh,
                                  @RequestParam(value = "czr", required = true) String czr,
                                  @RequestParam(value = "rwh", required = true) String rwh,
                                  @RequestParam(value = "cert", required = true) String cert,
                                  @RequestParam(value = "test", required = false) String test) {
        LOG.info(String.format("------> calling /be/eri.rfid?method=factory-init-card, tid=%s, kh=%s, bzhh=%s, sbxh=%s, gwh=%s, czr=%s, rwh=%s, test=%s",
                tid, kh, bzhh, sbxh, gwh, czr, rwh, test));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriInitializeContent eriInitializeContent;
            if (StringUtils.isNotEmpty(test) && Boolean.valueOf(test)) {
                eriInitializeContent =
                        eriService.factoryInitialize(tid, kh, bzhh, rwh, sbxh, gwh, czr, cert, Boolean.valueOf(test));
            } else {
                eriInitializeContent =
                        eriService.factoryInitialize(tid, kh, bzhh, rwh, sbxh, gwh, czr, cert);
            }
            resultMap.put("resultId", "00");
            resultMap.put("content", eriInitializeContent);
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=factory-init-card fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String result = gson.toJson(resultMap).replaceAll("\"", "'");
        LOG.info("------> calling /be/eri.rfid?method=factory-init-card result = " + result);
        return result;
    }

    @RequestMapping(params = "method=fetch-eri-by-bzhh")
    @ResponseBody
    public String fetchEriByBzhh(@RequestParam(value = "bzhh", required = true) String bzhh,
                                 HttpServletRequest request) {
        LOG.info(String.format("------> call /be/eri.rfid?method=fetch-eri-by-bzhh(bzhh=%s", bzhh));
        try {
            Inventory inventory = inventoryService.fetchByBzhh(bzhh);
            List<Eri> eriList = eriService.fetchByRegion(inventory.getQskh(), inventory.getZzkh());
            String result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "eriList", eriList));
            LOG.info("------> call /be/eri.rfid?method=fetch-eri-by-bzhh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("根据包装盒号查询所属电子标识异常", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }

    @RequestMapping(params = "method=generate-url-by-kh")
    @ResponseBody
    public String generateUrlByKh(@RequestParam(value = "kh", required = true) String kh) {
        LOG.info(String.format("------> call /be/eri.rfid?method=generate-url-by-kh kh = %s", kh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String qrcode = eriService.generateUrlByKh(kh);
            resultMap.put("resultId", "00");
            resultMap.put("url", qrcode);
        } catch (Exception e) {
            LOG.error("生成二维码失败", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", "生成二维码失败");
            resultMap.put("exception", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call /be/eri.rfid?method=generate-url-by-kh result = " + result);
        return result;
    }

    @RequestMapping(params = "method=customize")
    @ResponseBody
    public String customize(@RequestParam(value = "tid", required = true) String tid,
                            @RequestParam(value = "lsh", required = false) String lsh,
                            @RequestParam(value = "xh", required = false) Long xh,
                            @RequestParam(value = "cert", required = true) String cert) {
        LOG.info(String.format("------> calling /eri.frm?method=customize, tid = %s, lsh = %s, cert = %s", tid, lsh, cert));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriCustomizeContent eriCustomizeContent = null;
            if (xh != null) {
                eriCustomizeContent = eriService.customize(tid, xh, cert);
            } else if (StringUtils.isNotEmpty(lsh)) {
                CustomizeTask task = customizeTaskService.fetchAvailableByLsh(lsh);
                eriCustomizeContent = eriService.customize(tid, task.getXh(), cert);
            }
            resultMap.put("resultId", "00");
            resultMap.put("info", eriCustomizeContent);
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=customize result = " + result);
        return result;
    }

    @RequestMapping(params = "method=customize-result")
    @ResponseBody
    public String customizeResult(@RequestParam(value = "xh", required = false) Long xh,
                                  @RequestParam(value = "result") int result,
                                  @RequestParam(value = "sbyy", required = false) String sbyy,
                                  @RequestParam(value = "tid", required = false) String tid) throws Exception {
        LOG.info(String.format("------> calling /eri.frm?method=customize-result, xh = %s, result = %s, sbyy = %s", xh, result, sbyy));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            eriService.saveCustomizeResult(xh, result, sbyy, tid);
            resultMap.put("resultId", "00");
            resultMap.put("resultMsg", "更新成功");
        } catch (Exception e) {
            LOG.error("save customize result fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=customize-result result = " + resultStr);
        return resultStr;
    }

    @RequestMapping(params = "method=modify-password-result")
    @ResponseBody
    public String modifyPasswordResult(@RequestParam(value = "tid") String tid,
                                       @RequestParam(value = "result") int result,
                                       @RequestParam(value = "sbyy", required = false) String sbyy) {
        LOG.info(String.format("------> calling /be/eri.rfid?method=modify-password-result tid = %s, result = %s, sbyy = %s",
                tid, result, sbyy));
        Map resultMap = new HashMap();
        try {
            eriService.modifyPasswordResult(tid, result, sbyy);
            resultMap.put("resultId", "00");
            resultMap.put("resultMsg", "更新成功");
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=modify-password-result fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=modify-password-result result = " + resultStr);
        return resultStr;
    }

    @RequestMapping(params = "method=fetch-cert")
    @ResponseBody
    public String fetchCert() {
        LOG.info("------> calling /be/eri.rfid?method=fetch-cert");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SysPara sysPara = gSysparaCodeService.getSyspara("00", "2", "DSJZS");
            if (sysPara == null) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "未安装证书");
            } else {
                resultMap.put("resultId", "00");
                resultMap.put("cert", sysPara.getMrz());
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=fetch-cert error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=fetch-cert result = " + result);
        return result;
    }

    @RequestMapping(params = "method=parse-data")
    public String parseUser0Info(@RequestParam(value = "data", required = true) String data) {
        LOG.info("------> calling /be/eri.rfid?method=parse-data");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            User0Data user0Data = eriService.parseUser0Data(data);
            resultMap.put("resultId", "00");
            resultMap.put("data", user0Data);
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=parse-data failed", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=parse-data result = " + result);
        return result;
    }


    @RequestMapping(params = "method=unbind-result")
    @ResponseBody
    public String unbindResult(@RequestParam(value = "tid", required = true) String tid,
                                  @RequestParam(value = "result") int result,//1成功 0 失败
                                  @RequestParam(value = "sbyy", required = false) String sbyy) throws Exception {
        LOG.info(String.format("------> calling /eri.frm?method=unbind-result, tid = %s, result = %s, sbyy = %s", tid, result, sbyy));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //eriService.saveCustomizeResult(xh, result, sbyy, tid);
        	eriService.unbindEri(tid);
        	
            resultMap.put("resultId", "00");
            resultMap.put("resultMsg", "更新成功");
        } catch (Exception e) {
            LOG.error("save unbind result fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=customize-result result = " + resultStr);
        return resultStr;
    }

    
}
