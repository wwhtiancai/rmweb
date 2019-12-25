package com.tmri.framework.ctrl;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.framework.service.DepartmentManager;
import com.tmri.framework.service.ProgramFoldManager;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Controller
@RequestMapping("/department.frm")
public class DepartmentCtrl extends FrmCtrl{
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private ProgramFoldManager programFoldManager;

	/**
	 * 跳转到指定页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=findForward")
	public ModelAndView findForward(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        try {
            //验证授权访问
            this.sysService.doBeginCall(request,"00",Constants.MENU_BMGL,"","");   
            request.setAttribute("sysService",this.sysService);
            return redirectMav("jsp/departmentMain");
        } catch (Exception e) {        	
            //记录程序错误日志
        	String errmsg = StringUtil.getExpMsg(e);		
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
        }
	}
	/**
	 * 跳转到指定页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=openkeyWorkConfig")
	public ModelAndView openkeyWorkConfig(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        try {
            //验证授权访问
            this.sysService.doBeginCall(request,"00","9508","","");
            java.util.Map m=this.departmentManager.getCheckboxSate();
            request.setAttribute("queryresult", m);
            return redirectMav("jsp/openkeyWorkConfig");
        } catch (Exception e) {        	
            //记录程序错误日志
        	String errmsg = StringUtil.getExpMsg(e);		
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
        }
	}

	@RequestMapping(params = "method=savekeyWorkConfig")
	public void savekeyWorkConfig(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		List list=new java.util.ArrayList();
		for(int i=1;i<6;i++)
		{
			java.util.Map temp=new java.util.HashMap();
			String cdbh=request.getParameter("cdbh"+i);
			String xtlb=request.getParameter("xtlb"+i);
			String pki=request.getParameter("pki"+i);
			String ip=request.getParameter("ip"+i);
			if(pki!=null&&!pki.equals(""))
			{
				pki="1";
			}else{
				pki="";
			}
			if(ip!=null&&!ip.equals(""))
			{
				ip="2";
			}else{
				ip="";
			}
			temp.put("cdbh", cdbh);
			temp.put("xtlb", xtlb);
			temp.put("fwfs", pki+ip);
			list.add(temp);
			
		}
		DbResult returninfo = new DbResult();
		try {
			out = response.getWriter();
			returninfo = this.departmentManager.savekeyWorkConfig(list);
		} catch (Exception ex) {
			ex.printStackTrace();
			returninfo.setCode(99);
			returninfo.setMsg1(StringUtil.transExp2Script(ex));
		}

		out.println(FuncUtil.getReturnInfo(returninfo));
	}
	
	@RequestMapping(params = "method=findForwardcode")
	public ModelAndView findForwardcode(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        try {
            //验证授权访问
            this.sysService.doBeginCall(request,"00",Constants.MENU_BMGL,"","");   
            request.setAttribute("sysService",this.sysService);
            return redirectMav("jsp/departmentcodeMain");
        } catch (Exception e) {
            //记录程序错误日志
        	String errmsg = StringUtil.getExpMsg(e);			
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
        }
	}
	
	/**
	 * 跳转到公告维护页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=forwardbroadcast")
	public ModelAndView forwardbroadcast(HttpServletRequest request,HttpServletResponse response){
		return redirectMav("jsp/broadcastList");
	}
	
	/**
	 * 新建代码
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=newbroadcast")
	public ModelAndView newbroadcast(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		try{
			if(this.gSystemService.checkUserRight(session,"00",Constants.MENU_XXGG,"")==false){
				request.setAttribute("message",Constants.SAFE_CTRL_MSG_101);
				return redirectMav("authErr");
			}
			UserSession userSession=gSystemService.getSessionUserInfo(session);
			request.setAttribute("modal","new");
			// request.setAttribute("sysservice",sysService);
			
			request.setAttribute("gSysdateService",this.gSysDateService);
			
			sysService.genWebKey(request);
			//request.setAttribute("glbmlist",this.sysService.getXjDepartments(userSession.getDepartment().getGlbm(),true));
			request.setAttribute("glbmlist",this.gDepartmentService.getXjDepartments(userSession.getDepartment().getGlbm(),true));
		}catch(Exception e){
			e.printStackTrace();
		}
		return redirectMav("jsp/broadcastEdit");
	}

	public String list2str(List list){
		String str="";
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Department dep=(Department)list.get(i);
				str=str+dep.getGlbm()+dep.getBmmc()+"\n";
			}
		}
		return str;
	}
	
	private String ScriptToText(String str){
		String r=null;
		if(str==null){
			r="";
		}else{
			r=str;
			r=r.replaceAll("<br>","[br]");
			r=r.replaceAll("<","〈");
			r=r.replaceAll(">","〉");
			r=r.replaceAll("\r\n","<br>");
			r=r.replaceAll("\r","<br>");
			r=r.replaceAll("\n","<br>");
			r=r.replaceAll("[br]","<br>");
		}
		return r;
	}
}
