package com.tmri.rfid.endpoints;

import com.tmri.rfid.bean.EriReaderWriterActivation;
import com.tmri.rfid.service.EriReaderWriterService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.ctrl.BaseCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/encryption.frm")
public class EncryptionEndPoint extends BaseCtrl {
    private final static Logger LOG = LoggerFactory.getLogger(EncryptionEndPoint.class);


    @Resource
    private EriReaderWriterService eriReaderWriterService;
	
	
	@RequestMapping(params = "method=activation")
    @ResponseBody
	public String activation(@RequestParam(value = "buffer", required = true) String buffer,
                             @RequestParam(value = "caPubkeyIndex", required = true) String caPubkeyIndex) {
		LOG.info(String.format("------> calling /encryption.frm?method=activation, buffer = %s", buffer));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            EriReaderWriterActivation eriReaderWriterActivation = eriReaderWriterService.activate(buffer, caPubkeyIndex);
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
		
		LOG.info("------> calling /encryption.frm?method=activation result = " + result);
		
		return result;
	}
}