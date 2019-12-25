package com.tmri.rm.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.share.frm.ctrl.BaseCtrl;

@Controller
public class RmCtrl extends BaseCtrl {
	protected final String jspPath = "jsp_core/tfc/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}

}
