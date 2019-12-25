package com.tmri.rfid.ctrl;

import com.jcabi.aspects.Loggable;
import com.tmri.share.frm.ctrl.BaseCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Joey on 2016-05-03.
 */
@Controller
@RequestMapping("/job.frm")
public class JobCtrl extends BaseCtrl {

    protected final String jspPath = "jsp_core/rfid/job/";

    private ModelAndView redirectMav(String mav) {
        return new ModelAndView(this.jspPath + mav);
    }

    @RequestMapping("method = list")
    @Loggable(skipResult = true)
    public ModelAndView list() {
        
        return redirectMav("main");
    }

}
