package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.ElectronicCertificate;
import com.tmri.rfid.common.ElectronicCertificateSubjectType;
import com.tmri.rfid.service.ElectronicCertificateService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.ctrl.BaseCtrl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/11/13.
 */
@Controller
@RequestMapping("e-cert.rfid")
public class ElectronicCertificateCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(ElectronicCertificateCtrl.class);

    @Resource
    private ElectronicCertificateService electronicCertificateService;

    public ModelAndView redirectMav(String mav) {
        return redirectMav("electronicCertificate", mav);
    }

    @RequestMapping(params = "method=list")
    public ModelAndView list(@RequestParam(value = "xh", required = false) String xh,
                             @RequestParam(value = "ssztlx", required = false) String ssztlx,
                             @RequestParam(value = "ssztbh", required = false) String ssztbh,
                             @RequestParam(value = "zt", required = false) String zt,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                             Model model){
        LOG.info(String.format("------> display e-cert.rfid?method=list(xh = %s, ssztxh = %s, ssztbh = %s, zt = %s, page = %s, pageSize = %s",
                xh, ssztlx, ssztbh, zt, page, pageSize));
        try {
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(xh)) {
                condition.put("xh", Integer.valueOf(xh));
            }
            if (StringUtils.isNotEmpty(ssztlx)) {
                condition.put("ssztlx", Integer.valueOf(ssztlx));
            }
            if (StringUtils.isNotEmpty(ssztbh)) {
                condition.put("ssztbh", ssztbh);
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", zt);
            }
            List<ElectronicCertificate> certificates = electronicCertificateService.fetchByCondition(condition, new PageBounds(page, pageSize));
            model.addAttribute("certificates", certificates);
        } catch (Exception e) {
            LOG.error("------> display e-cert.rfid?method=list error", e);
        }
        return redirectMav("main");
    }

    @RequestMapping(params = "method=create")
    @ResponseBody
    public String create(@RequestParam(value = "ssztlx", required = true) String ssztlx,
                         @RequestParam(value = "ssztbh", required = true) String ssztbh,
                         @RequestParam(value = "zsnr", required = true) String zsnr,
                         @RequestParam(value = "zsbh", required = false, defaultValue = "1") int zsbh) {
        LOG.info(String.format("------> call e-cert.rfid?method=create (ssztlx = %s, ssztbh = %s, zsnr = %s, zsbh = %s",
                ssztlx, ssztbh, zsnr, zsbh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            ElectronicCertificate electronicCertificate = electronicCertificateService.create(
                    ElectronicCertificateSubjectType.valueOf(ssztlx), ssztbh, zsnr, zsbh);
            resultMap.put("resultId", "00");
            resultMap.put("xh", electronicCertificate.getXh());
        } catch (Exception e) {
            LOG.error("------> display e-cert.rfid?method=list error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        return GsonHelper.getGson().toJson(resultMap);
    }

}
