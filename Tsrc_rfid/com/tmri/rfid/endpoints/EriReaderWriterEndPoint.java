package com.tmri.rfid.endpoints;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.tmri.rfid.bean.EriReaderWriter;
import com.tmri.rfid.bean.EriReaderWriterActivation;
import com.tmri.rfid.common.EriReaderWriterRegisterStatus;
import com.tmri.rfid.service.EriEquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmri.rfid.service.EriReaderWriterService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.ctrl.BaseCtrl;

/*
 *	zhangxd
 *	2016-02-29
 *  机动车电子标识读写器相关接口
 */
@Controller
@RequestMapping("/be/reader-writer.rfid")
public class EriReaderWriterEndPoint extends BaseCtrl {
	private final static Logger LOG = LoggerFactory.getLogger(EriReaderWriterEndPoint.class);

    @Resource
    private EriReaderWriterService eriReaderWriterService;

    @Resource
    private EriEquipmentService eriEquipmentService;

    /**
     * 机动车电子标识读写器激活接口，通过传入十六进制字符串解析读写器序列号、安全模块序列号
     * @param buffer
     * @param caPubkeyIndex
     * @return
     */
	@RequestMapping(params = "method=activation")
    @ResponseBody
	public String activation(@RequestParam(value = "buffer", required = true) String buffer,
                             @RequestParam(value = "caPubkeyIndex", required = true) String caPubkeyIndex) {
		LOG.info(String.format("------> calling /be/reader-writer.rfid?method=activation, buffer = %s,caPubkeyIndex= %s", buffer,caPubkeyIndex));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            EriReaderWriterActivation eriReaderWriterActivation = eriReaderWriterService.activate(buffer,caPubkeyIndex);
            resultMap.put("resultId", "00");
            resultMap.put("resultMsg", eriReaderWriterActivation.getActivateResponse());
            resultMap.put("dxqxh", eriReaderWriterActivation.getDxqxh());
            resultMap.put("ydxqxh", eriReaderWriterActivation.getYdxqxh());
            resultMap.put("aqmkxh", eriReaderWriterActivation.getAqmkxh());
        } catch (Exception e) {
            LOG.error("activation failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }

		String result = GsonHelper.getGson().toJson(resultMap);
		
		LOG.info("------> calling /be/reader-writer.rfid?method=activation result = " + result);
		return result;
	}

    @RequestMapping(params = "method=activation-result")
    @ResponseBody
    public String activationResult(@RequestParam(value = "dxqxh", required = true) String dxqxh,
                                   @RequestParam(value = "zt", required = true) int zt,
                                   @RequestParam(value = "sqzt", required = true) int sqzt,
                                   @RequestParam(value = "sbyy", required = false) String sbyy) {
        LOG.info(String.format("------> calling /be/reader-writer.rfid?method=activation-result, dxqxh = %s, zt = %s, sqzt = %s, sbyy = %s",
                dxqxh, zt, sqzt, sbyy));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            eriReaderWriterService.activateResult(dxqxh, zt, sqzt, sbyy);
            resultMap.put("resultId", "00");
        } catch (Exception e) {
            LOG.error("更新读写器激活结果失败", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }

        String result = GsonHelper.getGson().toJson(resultMap);

        LOG.info("------> calling /be/reader-writer.rfid?method=activation-result result = " + result);
        return result;
    }

    @RequestMapping(params = "method=check-register-status")
    @ResponseBody
    public String checkRegisterStatus(@RequestParam(value = "aqmkxh", required = true) String aqmkxh) {
        LOG.info(String.format("------> calling /be/reader-writer.rfid?method=check-register-status, aqmkxh = %s", aqmkxh));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriReaderWriterRegisterStatus registerStatus = eriEquipmentService.checkRegisterStatus(aqmkxh);
            if (registerStatus == null) {
                registerStatus = EriReaderWriterRegisterStatus.UNREGISTERED;
            }
            resultMap.put("resultId", "00");
            resultMap.put("statusCode", registerStatus.getStatus());
            resultMap.put("statusDesc", registerStatus.getDesc());
        } catch (Exception e) {
            LOG.error("更新读写器激活结果失败", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }

        String result = GsonHelper.getGson().toJson(resultMap);

        LOG.info(String.format("------> calling /be/reader-writer.rfid?method=check-register-status, result = %s", result));
        return result;
    }
}
