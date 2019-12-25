package com.tmri.rfid.ctrl;

import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.service.StatisticsService;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2016/12/21.
 */
@Controller
@RequestMapping("/statistics.frm")
public class StatisticsCtrl  extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(StatisticsCtrl.class);

    @Resource
    private StatisticsService statisticsService;

    private ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/statistics", mav);
    }

    @RequestMapping(params = "method=count-customization")
    public ModelAndView countCustomization(@RequestParam(value = "qsrq", required = false) String qsrq,
                                           @RequestParam(value = "zzrq", required = false) String zzrq,
                                           @RequestParam(value = "yhdh", required = false) String yhdh,
                                           Model model) {
        LOG.info(String.format("------> calling /statistics.frm?method=count-customization qsrq = %s, zzrq = %s, yhdh = %s", qsrq, zzrq, yhdh));
        model.addAttribute("result", statisticsService.countCustomizationByYhdh(qsrq, zzrq, yhdh));
        model.addAttribute("qsrq", qsrq);
        model.addAttribute("zzrq", zzrq);
        model.addAttribute("yhdh", yhdh);
        return redirectMav("customization");
    }


    @RequestMapping(params = "method=countEri")
    public ModelAndView countEri(@RequestParam(value = "fzjg", required = false) String fzjg, Model model) {
        LOG.info(String.format("------> calling /statistics.frm?method=countEri fzjg = %s", fzjg));
        if (StringUtil.checkBN(fzjg)) {
            try {
                fzjg = URLDecoder.decode(fzjg, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("=============:" + fzjg);
        String bdfzjg = gSysparaCodeService.getSysParaValue("00", "BDFZJG");
        String[] fzjgs = bdfzjg.split(",");
        List<String> fzjgList = new ArrayList<String>();
        for (int i = 0; i < fzjgs.length; i++) {
            fzjgList.add(fzjgs[i]);
        }
        model.addAttribute("fzjgList", fzjgList);
        model.addAttribute("fzjg", fzjg);
        if (StringUtil.checkBN(fzjg)) {
            model.addAttribute("result", statisticsService.countEri(fzjg));
        } else {
            model.addAttribute("result", "");
        }
        return redirectMav("countEri");
    }

}
