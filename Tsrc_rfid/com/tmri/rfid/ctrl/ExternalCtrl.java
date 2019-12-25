package com.tmri.rfid.ctrl;

import com.tmri.rfid.service.EriService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Joey on 2015/12/7.
 */
@Controller
@RequestMapping("/external")
public class ExternalCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(ExternalCtrl.class);

    @Resource
    private EriService eriService;

    @RequestMapping(value = "/homepage/{id}")
    public ModelAndView homepage(@PathVariable("id") String id, Model model) {
        LOG.info("------> calling /rfid/" + id);
        try {
            if (StringUtils.isEmpty(id) || id.length() < 12) {
                model.addAttribute("error", "ÎÞÐ§Á´½Ó");
            } else {
                String kh = id.substring(0, 12);
                String sign = id.substring(12);
                if (eriService.verifyIdentity(kh, sign)) {
                    model.addAttribute("userInfo", kh);
                }
            }
        } catch (Exception e) {
            LOG.error("------> calling /rfid/" + id + " fail", e);
        }
        return new ModelAndView("jsp_core/rfid/homepage");
    }

}
