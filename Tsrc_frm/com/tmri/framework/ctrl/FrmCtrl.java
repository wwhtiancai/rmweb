package com.tmri.framework.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tmri.pub.service.SysService;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.service.GHtmlService;
import com.tmri.share.frm.service.GSysDateService;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.service.GSystemService;
import com.tmri.share.frm.util.Constants;

/**
 * 控制层基类，提供框架服务功能
 * @author jianghailong
 *
 */
@Controller
public class FrmCtrl extends MultiActionController {
	protected String xtlb=Constants.SYS_XTLB_FRAME;
	
	protected String contentType = "text/html; charset=GBK";
	@Autowired
	protected SysService sysService;
	@Autowired
	protected GSysparaCodeService gSysparaCodeService;
	@Autowired
	protected GDepartmentService gDepartmentService;
	@Autowired
	protected GSystemService gSystemService;
	@Autowired
	protected GHtmlService gHtmlService;
	@Autowired
	protected GSysDateService gSysDateService;
    @Autowired
    protected GBasService gBasService;
	
	public String getParaValue(HttpServletRequest request,String _para)
	{
		String returnStr=(String)request.getParameter(_para);
		if(returnStr==null||returnStr.equals("")||returnStr.toLowerCase().equals("null"))
		{
			returnStr=(String)request.getAttribute(_para);
		}
		return returnStr;
	}
	
	protected final String jspPath = "jsp_core/framework/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
	
	public ModelAndView redirectMav(ModelAndView mav) {
		mav.setViewName(this.jspPath + mav.getViewName());
		return mav;
	}
}
