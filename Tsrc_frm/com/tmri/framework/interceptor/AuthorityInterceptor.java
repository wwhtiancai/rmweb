package com.tmri.framework.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.PicConstants;

public class AuthorityInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request,
	HttpServletResponse response, Object handler) throws Exception {
		if(Constants.SYS_WGFWFWQ_BJ.equals("1")&&handler.getClass().getName().indexOf("LoginCtrl")<0){
			//强制外挂系统访问
			sendRedirect_wgfwfwq(request,response);
			return false;
		}		
		
		if (handler.getClass().getName().indexOf("WriteDirectPicCtrl")>=0){
			PicConstants.getInstance().setRequestStream(request, request.getInputStream());	
			return true;
		}
		
		if (handler.getClass().getName().indexOf("RecgStatusCtrl")>=0||
			handler.getClass().getName().indexOf("ReadRecgPicCtrl")>=0||
			handler.getClass().getName().indexOf("ReadVehicleParkPicCtl")>=0||
			handler.getClass().getName().indexOf("VehQryCtrl")>=0||
			handler.getClass().getName().indexOf("AlarmSendCtrl")>=0||
			handler.getClass().getName().indexOf("RealCtrl")>=0||
			handler.getClass().getName().indexOf("VGatCtrl")>=0||
			handler.getClass().getName().indexOf("SysInfoCtrl")>=0||
			handler.getClass().getName().indexOf("VideoCtrl")>=0||
			handler.getClass().getName().indexOf("LoginCtrl")>=0||
			handler.getClass().getName().indexOf("FoldCtrl")>=0 || 
			handler.getClass().getName().indexOf("ByteCtrl")>=0 ||
			handler.getClass().getName().indexOf("FrPrintClientCtrl")>=0||
			handler.getClass().getName().indexOf("FreshCtrl")>=0 ||
			handler.getClass().getName().indexOf("DrvCtksCtrl")>=0||
			handler.getClass().getName().indexOf("SysInitCtrl")>=0||
			handler.getClass().getName().indexOf("ZjtpCtrl")>=0||
			handler.getClass().getName().indexOf("VehfakepicCtrl")>=0||			
			handler.getClass().getName().indexOf("UnitpicCtrl")>=0||
			handler.getClass().getName().indexOf("PubedupicCtrl")>=0||
			handler.getClass().getName().indexOf("AssistCtrl")>=0||
			handler.getClass().getName().indexOf("ResponsCtrl")>=0||
			handler.getClass().getName().indexOf("PhotoCtrl")>=0||
			handler.getClass().getName().indexOf("YwclCtrl")>=0||
			handler.getClass().getName().indexOf("ApprizePdfExport")>=0||
			handler.getClass().getName().indexOf("AcdExtResCtrl")>=0||
			handler.getClass().getName().indexOf("TranspcorppicCtrl")>=0||	
			handler.getClass().getName().indexOf("CommonTipCtrl")>=0||
			handler.getClass().getName().indexOf("PGisCtrl")>=0||
			handler.getClass().getName().indexOf("TfaceQueryCtrl")>=0||
			handler.getClass().getName().indexOf("TfaceUpdateCtrl")>=0||
			handler.getClass().getName().indexOf("TfaceCloudCtrl")>=0||			
			handler.getClass().getName().indexOf("ClientLoginCtrl")>=0||
			handler.getClass().getName().indexOf("ReadPassPicCtrl")>=0||
			handler.getClass().getName().indexOf("ReadTzPicCtrl")>=0||
			handler.getClass().getName().indexOf("ReadAlarmPicCtrl")>=0||
			handler.getClass().getName().indexOf("CliReadTzPicCtrl")>=0||
			handler.getClass().getName().indexOf("CliReadPassPicCtrl")>=0||	
			//stone
			handler.getClass().getName().indexOf("InitCardCtrl")>=0||	
			//MapData DCMap MapConfServlet
			handler.getClass().getName().indexOf("MapConfServlet")>=0||
			handler.getClass().getName().indexOf("MapData")>=0||
			handler.getClass().getName().indexOf("DCMap")>=0 ||
            handler.getClass().getName().indexOf("EriCtrl")>=0||
            handler.getClass().getName().indexOf("ProductionTaskCtrl")>=0 ||
            handler.getClass().getName().indexOf("SecurityModelCtrl") >=0 ||
            handler.getClass().getName().indexOf("ElectronicCertificateCtrl") >= 0||
            handler.getClass().getName().indexOf("CustomizeTaskCtrl") >= 0 ||
                handler.getClass().getName().indexOf("ElectronicCertificateCtrl") >= 0 ||
                handler.getClass().getName().indexOf("DeviceCtrl")>=0 ||
                handler.getClass().getName().indexOf("EndPoint")>=0 ||
                handler.getClass().getName().indexOf("ExternalCtrl") >= 0 ||
                handler.getClass().getName().indexOf("VehicleCtrl") >= 0 ||
                handler.getClass().getName().indexOf("RoleCtrl") >= 0

			){
			return true;
		}
		
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		if (userSession == null){
			sendRedirect(request,response);
			return false;
		}
	    return true; // always continue
	}
	
	public void postHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
	
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
	}
	
	private void sendRedirect(HttpServletRequest request,HttpServletResponse response){
		try {
			response.sendRedirect("login.frm?method=logout");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendRedirect_wgfwfwq(HttpServletRequest request,HttpServletResponse response){
		try {
			response.sendRedirect("login.frm?method=logout_wgfwfwq");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
