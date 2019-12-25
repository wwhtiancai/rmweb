package com.tmri.framework.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.cache.service.WebCacheService;
import com.tmri.framework.bean.SysInitObj;
import com.tmri.framework.service.SysParaManager;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;
@Controller
@RequestMapping("/sysInit.frm")
public class SysInitCtrl extends FrmCtrl{
	@Autowired
	private SysParaManager sysparaManager;
	@Autowired
	private WebCacheService rmWebCacheService;
	@Autowired
	private GSysparaCodeService gSysparaCodeService;
	
//	public void setgSysparaCodeService(GSysparaCodeService gSysparaCodeService) {
//		this.gSysparaCodeService = gSysparaCodeService;
//	}
//
//	public void setRmWebCacheService(RmWebCacheService rmWebCacheService) {
//		this.rmWebCacheService = rmWebCacheService;
//	}
//
//	public void setSysparaManager(SysParaManager sysparaManager){
//		this.sysparaManager=sysparaManager;
//	}

	/**
	 * 系统初始化————>转初始化页面
	 */
	@RequestMapping(params = "method=findForward")
	public ModelAndView findForward(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		try{			
			String xtzt = gSysparaCodeService.getSyspara("00","2","XTZT").getMrz();
			if(xtzt.equals("1")){
				request.setAttribute("sysservice",sysService);
				request.setAttribute("gHtmlService",this.gHtmlService);
				mav.setViewName("sysinit");
			}else{
				Constants.SYS_XTZT = xtzt;
				mav.setViewName("../../index");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav.setViewName("authErr");
		}
		return redirectMav(mav);
	}
	
	/**
	 * 系统初始化————>执行初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=doSysInit")
	public void doSysInit(HttpServletRequest request,HttpServletResponse response,SysInitObj command){
		SysInitObj sysInitObj=(SysInitObj)command;
		PrintWriter out=null;
		response.setContentType(contentType);
		try{
			out=response.getWriter();
			DbResult result =this.sysparaManager.saveSysInit(sysInitObj);
			this.rmWebCacheService.initDepartments();
			this.rmWebCacheService.initSysParas();
			this.rmWebCacheService.initSysParaValue();
			if(result.getCode()==1){
				out.println("<script language=javascript>parent.resultSave('"+result.getCode()+"','[00G0010C1]:系统初始化成功');</script>");
			}else{
				String s=StringUtil.transScriptStr(result.getMsg());
				out.println("<script language=javascript>parent.resultSave('"+result.getCode()+"','[00G0012C2]:" + s + "');</script>");
			}
		}catch(Exception e){
			String errmsg = StringUtil.getExpMsg(e);
			String s=StringUtil.transScriptStr(errmsg);
			e.printStackTrace();
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
}
