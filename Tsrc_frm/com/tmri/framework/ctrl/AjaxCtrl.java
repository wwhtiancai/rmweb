package com.tmri.framework.ctrl;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.share.frm.bean.Code;
@Controller
@RequestMapping("/ajax.frm")
public class AjaxCtrl extends FrmCtrl{
	private static final String CONTENT_TYPE = "text/xml; charset=GBK";
	/**
	 * 跳转到指定页面(行政区划查询)
	 * 
	 * @param request
	 * @param response
	 */
	private String getBodyStr() throws Exception
	{
	    String bodystr = "leftMargin=0 style='overflow-x:hidden ' topMargin=0 onKeyDown='if (event.keyCode==8){if(event.srcElement.type==\"text\"){}else{event.keyCode=0;event.returnValue=false;}}else{if (event.srcElement.name!=\"Submit\"){if (event.keyCode==13) event.keyCode=9;}}'";
	    return bodystr;
	}	
	
	@RequestMapping(params = "method=showPccMain")
	public ModelAndView showPccMain(HttpServletRequest request,
			HttpServletResponse response, Object command) throws Exception {
		response.setContentType(CONTENT_TYPE);
		try {
			request.setAttribute("bodystr", getBodyStr());
			return redirectMav("jsp/sl_pcc_select");
		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("errMsg", ex.toString());
			return redirectMav("error/errorMsg");
		}
	}
	// 获取行政区划列表信息
	@RequestMapping(params = "method=queryPccList")
	public void queryPccList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(CONTENT_TYPE);

		String para1 = (String) request.getParameter("para1");
		List queryList = this.gSysparaCodeService.getCodes("00", "33");
		PrintWriter out = null;
		try {
			para1 = URLDecoder.decode(para1, "utf-8");
			out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			StringBuffer buf = new StringBuffer();
			buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
			buf.append("\n<items>");
			if (queryList!=null&&queryList.size() > 0) {
				Iterator iterator = queryList.iterator();
				while (iterator.hasNext()) {
					Code _code = (Code) iterator.next();
					boolean isRead=false;
					if(_code.getDmsm1().indexOf(para1) > -1)
					{
						isRead=true;
					}
					if(!isRead&&para1.length()>=4&&_code.getDmz().indexOf(para1)==0)
					{
						isRead=true;
					}
					if (isRead) {
						buf.append("\n<item>");
						String prov = this.gSysparaCodeService.getCode("00", "33",
								_code.getDmz().substring(0, 2) + "0000")
								.getDmsm1();
						String city= this.gSysparaCodeService.getCode("00", "33",
								_code.getDmz().substring(0, 4) + "00")
								.getDmsm1();
						if(city==null||city.equals("")||city.toLowerCase().equals("null"))
						{
							city="-";
						}
						else if(city.indexOf("（*）")>-1)
						{
							city=city.trim().substring(0, city.indexOf("（*）"));
						}
						String sfdm=_code.getDmz().substring(0, 2);
						//直辖市特殊处理，北京、天津、上海、重庆不显示"市辖区"
						if(sfdm.equals("11")||sfdm.equals("12")||sfdm.equals("31")|sfdm.equals("50"))
						{
							city=prov;
						}
						String dmsm1=_code.getDmsm1();
						if(dmsm1==null||dmsm1.equals("")||dmsm1.toLowerCase().equals("null"))
						{
							dmsm1="-";
						}
						else if(dmsm1.indexOf("（*）")>-1)
						{
							dmsm1=dmsm1.trim().substring(0, dmsm1.indexOf("（*）"));
						}				
						if(!(city.equals(dmsm1)))
						{
							dmsm1=city+dmsm1;
						}
						buf.append("<prov>" + prov + "</prov>");
						buf.append("<dmz>" + _code.getDmz() + "</dmz>");
						buf.append("<dmsm1>" + dmsm1 + "</dmsm1>");
						buf.append("\n</item>");
					}
				}
			}
			buf.append("\n</items>");
			out.println(buf);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
