package com.tmri.rfid.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.ElectronicCertificate;
import com.tmri.rfid.service.ElectronicCertificateService;
import com.tmri.rfid.service.EriReaderWriterService;
import com.tmri.rfid.service.SecurityModelService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Joey on 2015/12/21.
 */
@Controller
@RequestMapping("/be/security-model.rfid")
public class SecurityModelEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(SecurityModelEndPoint.class);

    @Resource
    private SecurityModelService securityModelService;
	@Resource
	private ElectronicCertificateService electronicCertificateService;
    @Resource
    private EriReaderWriterService eriReaderWriterService;

    @RequestMapping(params = "method=init", method= RequestMethod.POST)
    @ResponseBody
    public String initModel(@RequestParam(value = "xh", required = true) String xh,
                            @RequestParam(value = "lx", required = false) String lx,
                            @RequestParam(value = "qulx", required = false) String qulx,
                            @RequestParam(value = "xp1gy", required = false) String xp1gy,
                            @RequestParam(value = "xp2gy", required = false) String xp2gy,
                            @RequestParam(value = "cagysyh", required = false) String cagysyh,
                            @RequestParam(value = "cagy", required = false) String cagy,
                            @RequestParam(value = "xp1mybb", required = false) String xp1mybb,
                            @RequestParam(value = "xp1yhcxbb", required = false) String xp1yhcxbb,
                            @RequestParam(value = "xp2yhcxbb", required = false) String xp2yhcxbb,
                            @RequestParam(value = "stm32gjbb", required = false) String stm32gjbb,
                            @RequestParam(value = "ccrq", required = false) String ccrq,
                            @RequestParam(value = "dlbbb", required = false) String dlbbb,
                            @RequestParam(value = "cshrq", required = false) String cshrq,
                            @RequestParam(value = "czr", required = false) String czr) {
        LOG.info(String.format("------> calling /security-model.frm?method=init-model, xh=%s, lx = %s, qulx = %s, xp1gy = %s, xp2gy = %s," +
                        "cagysyh = %s, cagy = %s, xp1mybb = %s, xp1yhcxbb = %s, xp2yhcxbb = %s, stm32gjbb = %s, ccrq = %s, dlbbb = %s, cshrq = %s, czr = %s",
                xh, lx, qulx, xp1gy, xp2gy, cagysyh, cagy, xp1mybb, xp1yhcxbb, xp2yhcxbb, stm32gjbb, ccrq, dlbbb, cshrq, czr));
        try {
            return securityModelService.initModel(xh, lx, qulx, xp1gy, xp2gy, cagysyh, cagy, xp1mybb, xp1yhcxbb,
                    xp2yhcxbb, stm32gjbb, DateUtil.praseDate(ccrq, "yyyy-MM-dd"), dlbbb, cshrq, czr);
        } catch (Exception e) {
            LOG.error("初始化失败", e);
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "99", "resultMsg", e.getMessage()));
        }
    }


    @RequestMapping(params = "method=query-cret-model")
    @ResponseBody
    public String queryCretModel(@RequestParam(value = "ssztbh", required = true) String ssztbh,
    		@RequestParam(value = "zsbh", required = true) String zsbh,
    		@RequestParam(value = "zt", required = false,defaultValue = "1") String zt) {
        try {
        	 LOG.info(String.format("------> calling /security-model.frm?method=query-cret-model, xh=%s,zt",
        			 ssztbh));
        	 Map<String, Object> condition = new HashMap<String, Object>();
             if (StringUtils.isNotEmpty(ssztbh)) {
                 condition.put("ssztbh", ssztbh);
                 condition.put("zt", zt);
             }
             if (StringUtils.isNotEmpty(zsbh)) {
                 condition.put("zsbh", zsbh);
             }
        	 List<ElectronicCertificate> certificates = electronicCertificateService.fetchByCondition(condition);
        	 if(!certificates.isEmpty()){
        		 return GsonHelper.getGson().toJson(
                         MapUtilities.buildMap("resultId", "00", "cert", certificates.get(0))); 
        	 }else{
        		 return GsonHelper.getGson().toJson(
                         MapUtilities.buildMap("resultId", "01", "resultMsg", "没有查到相关安全模块数据！")); 
        	 }
        } catch (Exception e) {
            LOG.error("查询失败", e);
            return GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "99", "resultMsg", e.getMessage())); 
        }
    }
}
