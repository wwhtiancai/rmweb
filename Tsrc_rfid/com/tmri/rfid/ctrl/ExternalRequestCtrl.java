package com.tmri.rfid.ctrl;

import com.tmri.share.frm.ctrl.BaseCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Joey on 2016-04-21.
 */
@Controller
@RequestMapping("external-request.frm")
public class ExternalRequestCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(ExternalRequestCtrl.class);

    
}
