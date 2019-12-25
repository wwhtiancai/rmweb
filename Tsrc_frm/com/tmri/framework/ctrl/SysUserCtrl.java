package com.tmri.framework.ctrl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tmri.framework.bean.Fold;
import com.tmri.framework.bean.FrmUserprog;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.SysUserFinger;
import com.tmri.framework.bean.SysUserSeal;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.framework.service.CommManager;
import com.tmri.framework.service.DepartmentManager;
import com.tmri.framework.service.ProgramFoldManager;
import com.tmri.framework.service.RoleManager;
import com.tmri.framework.service.RoleprogManager;
import com.tmri.framework.service.SysuserManager;
import com.tmri.framework.util.ToolsManager;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Controller
@RequestMapping("/sysuser.frm")
public class SysUserCtrl extends FrmCtrl{
	
	@Autowired
	private SysuserManager sysuserManager;
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private ToolsManager toolsManager;
	@Autowired
	private ProgramFoldManager programFoldManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private RoleprogManager roleprogManager;
	@Autowired
	private CommManager commManager;

	private String cxdh=Constants.MENU_YHGL;

	private String MSG_NOROLEGLBM="无权编辑该管理部门用户信息!";

	// 签名图片上传
	@RequestMapping(params = "method=uploadfile")
	public void uploadfile(HttpServletRequest request,HttpServletResponse response,SysUserSeal command) throws Exception{
		PrintWriter out=response.getWriter();
		SysUserSeal userseal=(SysUserSeal)command;
		SysResult returninfo=new SysResult();
		try{
			//将
			byte[] qmtp=userseal.getQmtp().getBytes();
			returninfo=this.sysuserManager.saveQmtp(userseal,qmtp);
		}catch(Exception ex){
			returninfo.setFlag(99);
			returninfo.setDesc(ex.getMessage());
		}
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}

	// 签名图片删除
	@RequestMapping(params = "method=delfile")
	public void delfile(HttpServletRequest request,HttpServletResponse response,SysUserSeal obj) throws Exception{
		PrintWriter out=response.getWriter();
		SysResult returninfo=new SysResult();
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			returninfo=this.sysuserManager.delQmtp(obj);
		}catch(Exception ex){
			String errmsg = StringUtil.getExpMsg(ex);
			returninfo.setFlag(99);
			returninfo.setDesc(errmsg);
		}
		
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}

	// 跳转到上传图片页面
	@RequestMapping(params = "method=uploadphoto")
	public ModelAndView uploadphoto(HttpServletRequest request,HttpServletResponse response){
		// 获取用户签名
		String yhdh=(String)request.getParameter("yhdh");
		SysUserSeal sysuserseal=new SysUserSeal();
		sysuserseal.setYhdh(yhdh);
		response.setContentType(contentType);
		request.setAttribute("sysuserseal",sysuserseal);
		String zdsx=String.valueOf(this.sysuserManager.getQmtpNum(yhdh));
		request.setAttribute("zdsx",zdsx);
		request.setAttribute("sysuserManager",sysuserManager);
		return redirectMav("jsp/usersealupload");
	}

	// 显示图片
	@RequestMapping(params = "method=outputphoto")
	public synchronized void outputphoto(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String yhdh=(String)request.getParameter("yhdh");
		try{
			Blob fileData=this.sysuserManager.getQmtp(yhdh);
			response.reset();
			response.setContentType("image/*;charset=GBK");
			response.setHeader("Content-Disposition","x");
			OutputStream sos=response.getOutputStream();
			InputStream pi=null;
			int blobsize=0;
			if(fileData!=null){
				try{
					pi=fileData.getBinaryStream();
					blobsize=(int)fileData.length();
				}catch(SQLException ex){
					ex.printStackTrace();
				}
				byte[] blobbytes=new byte[blobsize];
				int bytesRead=0;
				while((bytesRead=pi.read(blobbytes))!=-1){
					sos.write(blobbytes,0,bytesRead);
				}
				pi.close();
				sos.flush();
			}else{
				RequestDispatcher dis;
				dis=request.getRequestDispatcher("theme/blue/nophoto.jpg");
				dis.forward(request,response);
			}

		}catch(Exception ex){
			RequestDispatcher dis;
			dis=request.getRequestDispatcher("theme/blue/nophoto.jpg");
			dis.forward(request,response);
		}
	}

	/**
	 * 用户管理页面————>主页面
	 */
	@RequestMapping(params = "method=usermain")
	public ModelAndView usermain(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userMain";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
            //获取当前用户功能id
			List gnidList = this.sysService.getUserCxdhGnlb(session, "00", Constants.MENU_YHGL);
			if(gnidList==null||gnidList.size()==0){
//				throw new Exception("TERR02:无权访问该功能模块！");
			}			
			
			String CASSWITCH=gSysparaCodeService.getSysParaValue("00","CASSWITCH");
			request.setAttribute("CASSWITCH",CASSWITCH);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("cdbh",Constants.MENU_YHGL);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";			
		}
		return redirectMav(mav);
	}

	@RequestMapping(params = "method=usermaintrack")
	public ModelAndView usermaintrack(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userMain";
		HttpSession session=request.getSession();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL_GJ,"","");       	
            //获取当前用户功能id
			List gnidList = this.sysService.getUserCxdhGnlb(session, "00", Constants.MENU_YHGL_GJ);
			if(gnidList==null||gnidList.size()==0){
//				throw new Exception("TERR02:无权访问该功能模块！");
			}			
			
			String CASSWITCH=gSysparaCodeService.getSysParaValue("00","CASSWITCH");
			request.setAttribute("CASSWITCH",CASSWITCH);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("cdbh",Constants.MENU_YHGL_GJ);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL_GJ,"","",1000);
		}catch(Exception ex){	
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";			
		}
		return redirectMav(mav);
	}	
	/**
	 * 用户管理页面————>部门列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=queryGlbmList")
	public ModelAndView queryGlbmList(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/departmentList";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
			request.setAttribute("departmenttree",this.departmentManager.getDepartmentTreeStr(userSession.getDepartment()));
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){			
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";			
		}
		return redirectMav(mav);
	}
	

	//按业务类别显示部门树形结构
	//zhoujn 20100909
	@RequestMapping(params = "method=queryGlbmYwlbList")
	public ModelAndView queryGlbmYwlbList(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/departmentList";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		try{
			String sjbmmc=this.gDepartmentService.getDepartmentName(dept.getSjbm());
			dept.setSjbmmc(sjbmmc);
			String treestr = this.departmentManager.getDepartmentTreeStr(userSession.getDepartment());
			request.setAttribute("departmenttree",treestr);
			request.setAttribute("f_glbm", dept.getGlbm());
		}catch(Exception ex){		
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";			
		}
		return redirectMav(mav);
	}	
	
	// zhoujn 20090531
	@RequestMapping(params = "method=userQuery")
	public ModelAndView userQuery(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userQuery";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");
			// 确定可管理业务种类
			List gnidList=this.sysService.getUserCxdhGnlb(session,this.xtlb,Constants.MENU_YHGL);
			request.setAttribute("gnidList",gnidList);

			// 获取
			String glbm=request.getParameter("glbm");
			Department dep = this.gDepartmentService.getDepartment(glbm);
			request.setAttribute("department",dep);
			request.setAttribute("cdbh",Constants.MENU_YHGL);
			request.setAttribute("sysService",this.sysService);
			//获取可选择用户类别list
			List yhssywlist=this.gSysparaCodeService.getCodes(Constants.SYS_XTLB_FRAME, "0020");
			//根据用户类别获取可选择类别
			yhssywlist=getYhssywlist(yhssywlist,sysUser.getKgywyhlx());
			request.setAttribute("yhssywlist",yhssywlist);
			
			mav="jsp/userQuery";
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){	
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}
	
	//轨迹查询
	@RequestMapping(params = "method=userQuerytrack")
	public ModelAndView userQuerytrack(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userQuery";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL_GJ,"","");
			// 确定可管理业务种类
			List gnidList=this.sysService.getUserCxdhGnlb(session,this.xtlb,Constants.MENU_YHGL_GJ);
			request.setAttribute("gnidList",gnidList);

			// 获取
			String glbm=request.getParameter("glbm");
			Department dep = this.gDepartmentService.getDepartment(glbm);
			request.setAttribute("department",dep);
			request.setAttribute("cdbh",Constants.MENU_YHGL_GJ);
			request.setAttribute("sysService",this.sysService);
			//获取可选择用户类别list
			List yhssywlist=this.gSysparaCodeService.getCodes(Constants.SYS_XTLB_FRAME, "0020");
			//根据用户类别获取可选择类别
			yhssywlist=getYhssywlist(yhssywlist,sysUser.getKgywyhlx());
			request.setAttribute("yhssywlist",yhssywlist);
			
			mav="jsp/userQuery";
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL_GJ,"","",1000);
		}catch(Exception ex){	
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}	
	
	
	//根据管辖类别过滤
	private List getYhssywlist(List yhssywlist,String kgywyhlx){
		List resultlist=new ArrayList();
		if(kgywyhlx.equals("")){
			kgywyhlx="1000000000";
		}
		
		if(yhssywlist!=null){
			if(kgywyhlx.charAt(0)=='1'){
				//科技
				resultlist=yhssywlist;
			}else{
				for(int i=0;i<yhssywlist.size();i++){
					if(kgywyhlx.charAt(i)=='1'){
						Code code=(Code)yhssywlist.get(i);
						resultlist.add(code);
					}
				}
			}
		}
		return resultlist;
	}

	@RequestMapping(params = "method=userResult")
	public ModelAndView userResult(HttpServletRequest request,HttpServletResponse response,SysUser obj) throws Exception{
		String mav="jsp/userResult";
		HttpSession session=request.getSession();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"",""); 		
        	//是否查看下属管理部门
        	String bmjb=this.gDepartmentService.getDepartment(obj.getGlbm()).getBmjb();
        	String glbmlike=FuncUtil.getGlbmlike(obj.getGlbm(), bmjb, obj.getBhxj());
        	obj.setGlbm(glbmlike);	
        	
			// 分页
			PageController controller=new PageController(request);
			List querylist=this.sysuserManager.getSysusersByPagesize(obj,controller);

			request.setAttribute("queryobj",obj);
			request.setAttribute("querylist",querylist);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("sysuserManager",sysuserManager);
			request.setAttribute("cdbh",Constants.MENU_YHGL);
			request.setAttribute("controller",controller);
			mav="jsp/userResult";
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}

	@RequestMapping(params = "method=userResulttrack")
	public ModelAndView userResulttrack(HttpServletRequest request,HttpServletResponse response,SysUser obj) throws Exception{
		String mav="jsp/userResult";
		HttpSession session=request.getSession();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL_GJ,"","");       	
			
        	//是否查看下属管理部门
        	String bmjb=this.gDepartmentService.getDepartment(obj.getGlbm()).getBmjb();
        	String glbmlike=FuncUtil.getGlbmlike(obj.getGlbm(), bmjb, obj.getBhxj());
        	obj.setGlbm(glbmlike);	
        	
			// 分页
			PageController controller=new PageController(request);
			List querylist=this.sysuserManager.getSysusersByPagesize(obj,controller);

			request.setAttribute("queryobj",obj);
			request.setAttribute("querylist",querylist);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("sysuserManager",sysuserManager);
			request.setAttribute("cdbh",Constants.MENU_YHGL);
			request.setAttribute("controller",controller);
			mav="jsp/userResult";
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL_GJ,"","",1000);
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}	
	
	/**
	 * 用户管理————>编辑或删除
	 * @param request
	 * @param response
	 */
	//增加查看信息
	@RequestMapping(params = "method=editOne")
	public ModelAndView editOne(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userEdit";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
			String yhdh=(String)request.getParameter("yhdh");
			String glbm=(String)request.getParameter("glbm");	
			Department dep=gDepartmentService.getDepartment(glbm);
			request.setAttribute("department",dep);

			SysUser sysuser=this.sysuserManager.getSysuser(yhdh);
			this.sysuserManager.setSysuserRoles(sysuser);
			//需设置可授权权限
			this.sysuserManager.setSysuserGrantRoles(sysuser);
			
			//判断当前用户是否可维护用户信息
			//根据当前用户管理的业务类型与管理原类型比较，有相同就可维护
			String kgywyhlx=sysUser.getKgywyhlx();
			Department userdept=this.gDepartmentService.getDepartment(glbm);
			//String yhlx=userdept.getKclyw();
			//比较是否可以维护用户信息
			String glqx="1";
	
			request.setAttribute("glqx",glqx);
			
			//判断用户是否可以维护用户权限grantrolelist
			String sqqx="0";
			if(sysUser.getXtgly().equals("1")){
				sqqx="1";
			}
			request.setAttribute("sqqx",sqqx);
			//增加权限模式20101215
			String xgxqms="0";
			//增加用户授权模式　20110113
			String yhsqms=this.gSysparaCodeService.getSysParaValue("00", "YHSQMS");
			if(yhsqms.equals("1")){
				xgxqms="1";
				sysuser.setQxms(xgxqms);
			}			
			request.setAttribute("xgxqms",xgxqms);
			
			//增加管理部门最小用户级别判断
			String xtglyzxjb="1",xtglyzxjbmsg="创建系统管理员的最小级别:";
			int XTGLYZXJB=Integer.valueOf(this.gSysparaCodeService.getSysParaValue("00", "XTGLYZXJB")).intValue();
			int BMJB=Integer.valueOf(dep.getBmjb()).intValue();
			
			//当前用户级别大于系统规定的最大用户级别
			if(BMJB>XTGLYZXJB){
				sysuser.setXtgly("2");
				xtglyzxjb="2";
			}
			xtglyzxjbmsg=xtglyzxjbmsg+ this.gSysparaCodeService.getCode("00", "0003", ""+XTGLYZXJB).getDmsm1();
			request.setAttribute("xtglyzxjb",xtglyzxjb);
			request.setAttribute("xtglyzxjbmsg",xtglyzxjbmsg);
			
			request.setAttribute("sysuser",sysuser);
			request.setAttribute("modal","edit");
			request.setAttribute("sysservice",sysService);
			request.setAttribute("sysuserManager",sysuserManager);
			sysService.genWebKey(request);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){	
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}
	
	
	//察看人员信息
	@RequestMapping(params = "method=userView")
	public ModelAndView userView(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userView";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	

			String yhdh=(String)request.getParameter("yhdh");
			String glbm=(String)request.getParameter("glbm");

			
			Department dep=gDepartmentService.getDepartment(glbm);
			request.setAttribute("department",dep);

			SysUser sysuser=this.sysuserManager.getSysuser(yhdh);
			this.sysuserManager.setSysuserRoles(sysuser);
			//需设置可授权权限
			this.sysuserManager.setSysuserGrantRoles(sysuser);
			
			//判断当前用户是否可维护用户信息
			//根据当前用户管理的业务类型与管理原类型比较，有相同就可维护
			String kgywyhlx=sysUser.getKgywyhlx();
			Department userdept=this.gDepartmentService.getDepartment(glbm);
			//比较是否可以维护用户信息
			String glqx="1";			
			request.setAttribute("glqx",glqx);
			
			//判断用户是否可以维护用户权限grantrolelist
			String sqqx="0";
			if(sysUser.getXtgly().equals("1")){
				sqqx="1";
			}
			request.setAttribute("sqqx",sqqx);
			
			
			//增加权限模式20101215
			String xgxqms="0";
			//增加用户授权模式　20110113
			String yhsqms=this.gSysparaCodeService.getSysParaValue("00", "YHSQMS");
			//角色模式
			//20110929,修改如果改为自由模式，则可调整
			if(yhsqms.equals("1")){
				xgxqms="1";
				sysuser.setQxms(xgxqms);
			}			
			request.setAttribute("xgxqms",xgxqms);
			
			//增加管理部门最小用户级别判断
			String xtglyzxjb="1",xtglyzxjbmsg="创建系统管理员的最小级别:";
			int XTGLYZXJB=Integer.valueOf(this.gSysparaCodeService.getSysParaValue("00", "XTGLYZXJB")).intValue();
			int BMJB=Integer.valueOf(dep.getBmjb()).intValue();
			
			//当前用户级别大于系统规定的最大用户级别
			if(BMJB>XTGLYZXJB){
				sysuser.setXtgly("2");
				xtglyzxjb="2";
			}
			xtglyzxjbmsg=xtglyzxjbmsg+ this.gSysparaCodeService.getCode("00", "0003", ""+XTGLYZXJB).getDmsm1();
			request.setAttribute("xtglyzxjb",xtglyzxjb);
			request.setAttribute("xtglyzxjbmsg",xtglyzxjbmsg);
			
			request.setAttribute("sysuser",sysuser);
			request.setAttribute("modal","edit");
			request.setAttribute("sysservice",sysService);
			request.setAttribute("sysuserManager",sysuserManager);
			sysService.genWebKey(request);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){		
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}	
	
	
	//将角色改为html
	@RequestMapping(params = "method=fillrolehtml")
	public void fillrolehtml(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 设置接收信息的字符集
		HttpSession session=request.getSession();
		String jsdh=request.getParameter("jsdh");
		String ckname=request.getParameter("ckname");
		// 设置输出信息的格式及字符集
		response.setContentType("application/xml; charset=GB2312");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType(contentType);

		PrintWriter out=response.getWriter();
		String result="";
		try{
			//jsdh处理 1101A1102转换成'1101',''
			jsdh=StringUtil.replaceStr(jsdh, "A", ",");
			List rolelist=this.roleManager.getRoleList(jsdh);
			result=this.sysuserManager.getRolelistHtml(ckname, 4, null);
		}catch(Exception ex){
			result=ex.getMessage();
		}
	    
		out.println(result);
		out.close();
	}		
	/**
	 * 用户角色授权窗口
	 * 要列出管理员角色和当前用户角色（包括不属于该管理员的）
	 * 能够分辨出新增角色和删除角色
	 * 新增，编辑窗口，列出当前用户可授权限，当前权限
	 * 分为两个功能， modal 1是可授予权限维护 2用户拥有权限维护
	 * 上级的系统管理员，是否能取消同级管理创建角色取消呢？
	 */
	@RequestMapping(params = "method=userRole")
	public ModelAndView userRole(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/userRole";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
			String yhdh=(String)request.getParameter("yhdh");
			String glbm=(String)request.getParameter("glbm");

			SysUser sysuser=this.sysuserManager.getSysuser(yhdh);
			Department dep=gDepartmentService.getDepartment(glbm);
			request.setAttribute("department",dep);
			
			String modal=(String)request.getParameter("modal");
			//合并管理员角色和用户角色,全部改为disabled
			//将管理员权限置为enabled
			if(modal.equals("1")){
				//管理员授予用户可授予权限
				Role queryrole=new Role();
				queryrole.setYhdh(userSession.getSysuser().getYhdh());
				queryrole.setGlbm(dept.getGlbm());
				List xtglyrolelist=this.roleManager.getRoleList(queryrole);
				request.setAttribute("xtglyrolelist",xtglyrolelist);
				//获取当前用户可授予权限
				List userrolelist=this.sysuserManager.getUserGrantRoleList(sysuser.getYhdh());
				Hashtable allrolelist=this.sysuserManager.combinRoleList(xtglyrolelist, userrolelist);
				request.setAttribute("allrolelist",allrolelist);
				//需设置可授权权限
				this.sysuserManager.setSysuserGrantRoles(sysuser);
			}else if(modal.equals("2")){
				//管理员授予用户拥有权限
				Role role=new Role();
				role.setJscj(dep.getBmjb());
				role.setYhdh(userSession.getSysuser().getYhdh());
				role.setGlbm(dept.getGlbm());
				List xtglyrolelist=this.roleManager.getRoleList(role);
				request.setAttribute("xtglyrolelist",xtglyrolelist);
				//获取当前权限
				List userrolelist=this.sysuserManager.getUserRoleList(sysuser.getYhdh());
				Hashtable allrolelist=this.sysuserManager.combinRoleList(xtglyrolelist, userrolelist);
				request.setAttribute("allrolelist",allrolelist);
				//用户拥有权限和可授权权限
				this.sysuserManager.setSysuserRoles(sysuser);
			}

			request.setAttribute("sysuser",sysuser);
			request.setAttribute("modal","edit");
			request.setAttribute("sysservice",sysService);
			request.setAttribute("sysuserManager",sysuserManager);
			
			mav="jsp/userRole";				
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}
	
	/**
	 * 用户管理————>保存
	 * @param request
	 * @param response
	 */
	//保存用户基本信息
	@RequestMapping(params = "method=saveUser")
	public void saveUser(HttpServletRequest request,HttpServletResponse response,SysUser command){
		SysUser edituser=(SysUser)command;
		PrintWriter out=null;
		response.setContentType(contentType);

		SysResult returninfo=new SysResult();
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}

			String modal=(String)request.getParameter("modal");
			String cznr=null;
			if(modal.equals("new")){
				cznr="新增用户,用户代号:"+edituser.getYhdh();
			}else{
				cznr="修改用户,用户代号:"+edituser.getYhdh();
			}
			String sysdatetime=gSysDateService.getSysDate(0,3);
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,cxdh,"",cznr,ip,"");
			returninfo=this.sysuserManager.saveSysuser(edituser,log,modal);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
			String errmsg = StringUtil.getExpMsg(ex);
			ex.printStackTrace();
			returninfo.setFlag(99);
			returninfo.setDesc(errmsg);
		}
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}

	//保存用户权限信息
	@RequestMapping(params = "method=saveUserrole")
	public void saveUserrole(HttpServletRequest request,HttpServletResponse response,SysUser command){
		SysUser edituser=(SysUser)command;
		PrintWriter out=null;
		response.setContentType(contentType);

		SysResult returninfo=new SysResult();
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			/*
			if(!this.sysService.checkDominionGlbm(session,edituser.getGlbm())){
				throw new Exception(MSG_NOROLEGLBM);
			}*/

			UserRole userRole=new UserRole();
			userRole.setYhdh(edituser.getYhdh());
			userRole.setJsdh(request.getParameter("roles"));
			
			UserRole userGrantrole=new UserRole();
			userGrantrole.setYhdh(edituser.getYhdh());
			userGrantrole.setJsdh(request.getParameter("grantroles"));
			
			
			UserMenu userMenu=new UserMenu();
			userMenu.setYhdh(edituser.getYhdh());
			userMenu.setCdbh(request.getParameter("cxdh"));

			String modal=(String)request.getParameter("modal");
			String cznr=null;
			if(modal.equals("new")){
				cznr="新增用户,用户代号:"+edituser.getYhdh()+";";
			}else{
				cznr="修改用户,用户代号:"+edituser.getYhdh()+";";
			}
			if(edituser.getQxms().equals("1")){
				cznr+="用户角色:"+userRole.getJsdh()+";";
			}else{
				cznr+="用户权限:"+userMenu.getCdbh()+";";
			}
			
			if(edituser.getXtgly().equals("1")){
				cznr+="用户管理权限:"+userGrantrole.getJsdh()+";";
			}
			
			String sysdatetime=gSysDateService.getSysDate(0,3);
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,cxdh,"",cznr,ip,"");
			returninfo=this.sysuserManager.saveSysuserrole(edituser,userRole,userMenu,userGrantrole,log,modal);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
			String errmsg = StringUtil.getExpMsg(ex);
			ex.printStackTrace();
			returninfo.setFlag(99);
			returninfo.setDesc(errmsg);
		}
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}	
	/**
	 * 用户管理————>删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=removeUser")
	public void removeUser(HttpServletRequest request,HttpServletResponse response,SysUser command){
		SysUser edituser=(SysUser)command;
		PrintWriter out=null;
		response.setContentType(contentType);
		SysResult returninfo=new SysResult();
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");  

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			/*
			if(!this.sysService.checkDominionGlbm(session,edituser.getGlbm())){
				throw new Exception(MSG_NOROLEGLBM);
			}*/

			String cznr="删除用户,用户代号为"+edituser.getYhdh();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),"",cxdh,"",cznr,ip,"");
			returninfo=this.sysuserManager.removeSysuser(edituser,log);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
			String errmsg = StringUtil.getExpMsg(ex);
			ex.printStackTrace();
			returninfo.setFlag(99);
			returninfo.setDesc(errmsg);
		}
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}

	/**
	 * 用户管理————>重置密码
	 * @param request
	 * @param response
	 */
	// zhoujn 20100601
	@RequestMapping(params = "method=saveSetuppassword")
	public void saveSetuppassword(HttpServletRequest request,HttpServletResponse response,SysUser command){
		SysUser edituser=(SysUser)command;
		PrintWriter out=null;
		response.setContentType(contentType);

		SysResult returninfo=new SysResult();
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");  
			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}

			String cznr="重置用户密码,用户代号为"+edituser.getYhdh();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),"",cxdh,"",cznr,ip,"");
			returninfo=this.sysuserManager.saveSetuppassword(edituser,log);
		}catch(Exception ex){
			String errmsg = StringUtil.getExpMsg(ex);
			ex.printStackTrace();
			returninfo.setFlag(99);
			returninfo.setDesc(errmsg);
		}
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}

	/**
	 * 用户管理————>用户解锁
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=unlock")
	public void unlock(HttpServletRequest request,HttpServletResponse response,SysUser command){
		SysUser edituser=(SysUser)command;
		PrintWriter out=null;
		response.setContentType(contentType);

		SysResult returninfo=new SysResult();
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");  

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			String cznr="";
			if(edituser.getZt().equals("1")){
				cznr="用户停用,用户代号为"+edituser.getYhdh();
			}else if(edituser.getZt().equals("2")){
				cznr="用户解锁,用户代号为"+edituser.getYhdh();
			}else if(edituser.getZt().equals("3")){
				cznr="用户启用,用户代号为"+edituser.getYhdh();
			}
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),"",cxdh,"",cznr,ip,"");
			returninfo=this.sysuserManager.saveUnlockUser(edituser,log);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
			String errmsg = StringUtil.getExpMsg(ex);
			ex.printStackTrace();
			returninfo.setFlag(99);
			returninfo.setDesc(errmsg);
		}
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(returninfo.getFlag());
		dbreturninfo.setMsg1(returninfo.getDesc());
		dbreturninfo.setMsg(returninfo.getDesc1());
		
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}

	@RequestMapping(params = "method=getLoginFail")
	public ModelAndView getLoginFail(HttpServletRequest request,HttpServletResponse response,SysUser command){
		String mav="jsp/userLoginfail";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);	
		try{
			SysUser edituser=(SysUser)command;
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");  
			List loginfaiList=this.sysuserManager.getLoginFail(edituser.getYhdh());
			request.setAttribute("loginfaiList",loginfaiList);
			request.setAttribute("sysuser",edituser);
			sysService.genWebKey(request);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){			
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}

	/**
	 * 修改用户密码————>打开界面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=editpassword")
	public ModelAndView editpassword(HttpServletRequest request,HttpServletResponse response){
		UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
		request.setAttribute("sysuser",sysService.getSysUser(userSession.getSysuser().getYhdh()));
		return redirectMav("jsp/passwordEdit");
	}
	/**
	 * 修改用户指纹信息————>打开界面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=editfinger")
	public ModelAndView editfinger(HttpServletRequest request,HttpServletResponse response){
		UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
		try{
			SysUserFinger sysUserFinger=sysuserManager.getSysUserFinger(userSession.getSysuser().getYhdh());
			request.setAttribute("yhdh",userSession.getSysuser().getYhdh());
			request.setAttribute("sysuserfinger",sysUserFinger);
			request.setAttribute("sysservice",this.sysService);
			return redirectMav("jsp/fingerEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			return redirectMav("error");
		}
	}	
	/**
	 * 指纹信息————>保存
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=saveFinger")
	public void saveFinger(HttpServletRequest request,HttpServletResponse response,SysUserFinger sysuserfinger){

		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		SysUser sysuser = userSession.getSysuser();
		try{
			out=response.getWriter();
			if(!userSession.getYhdh().equals(sysuserfinger.getYhdh())){
				out.println("<script language=javascript>parent.resultSave(0,'不能修改不是当前登录用户的指纹信息！');</script>");
			}
			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="修改指纹";
			Log log=new Log(sysuser.getGlbm(),sysuser.getYhdh(),sysdatetime,Constants.MENU_YHGL,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.sysuserManager.savaFinger(sysuserfinger,log);
			if(result.getFlag()==1){
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','指纹信息修改成功！');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc()+"');</script>");
			}
		}catch(Exception e){
			String s=StringUtil.cScriptInfoStr(e.getMessage());
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	/**
	 * 指纹信息————>校验
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=checkFinger")
	public void checkFinger(HttpServletRequest request,HttpServletResponse response){

		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		try{
			out=response.getWriter();
			String yhdh = request.getParameter("yhdh");
			String zwtz = request.getParameter("zwtz");
			Base64 base = new Base64();
			SysUserFinger sysUserFinger = sysuserManager.getSysUserFinger(yhdh);
		}catch(Exception e){
			String s=StringUtil.cScriptInfoStr(e.getMessage());
			out.println("error:,'"+s+"');</script>");
		}
	}	
	/**
	 * 桌面设置
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=setupdesk")
	public ModelAndView setupdesk(HttpServletRequest request,HttpServletResponse response){
		try{
			HttpSession session=request.getSession();
			UserSession userSession=(UserSession)session.getAttribute("userSession");
			
			//2012-02-24 增加系统运行模式作为查询菜单条件
			String XTYXMS = this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
			HashMap paras = new HashMap();
			paras.put("XTYXMS",XTYXMS);
			
			List ProgramList=this.programFoldManager.getProgramList(userSession.getSysuser(),paras);
			List foldList=this.programFoldManager.getFoldList(userSession.getSysuser().getYhdh());
			List userdeskList=(List)session.getAttribute("userdesklist");
			request.setAttribute("foldList",foldList);
			request.setAttribute("programList",ProgramList);
			String userDeskMenus="";
			if(userdeskList!=null){
				Iterator iterator=userdeskList.iterator();
				while(iterator.hasNext()){
					UserDesk userDesk=(UserDesk)iterator.next();
					userDeskMenus=userDeskMenus+userDesk.getMldh()+"-"+userDesk.getXtlb()+userDesk.getCdbh()+"#";
				}
			}
			request.setAttribute("userDeskMenus",userDeskMenus);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return redirectMav("setupdesk");
	}

	/**
	 * 保存桌面设置
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=Savedesk")
	public void Savedesk(HttpServletRequest request,HttpServletResponse response,UserDesk command){
		UserDesk userDesk=(UserDesk)command;
		int iResult=0;
		PrintWriter out=null;
		try{
			HttpSession session=request.getSession();
			UserSession userSession=(UserSession)session.getAttribute("userSession");
			response.setContentType("text/html; charset=GBK");
			SysUser sysuser=userSession.getSysuser();
			userDesk.setYhdh(sysuser.getYhdh());
			userDesk.setXtlb("02");
			Hashtable rightsobj=(Hashtable)session.getAttribute("rightsobj");
			out=response.getWriter();
			iResult=this.sysuserManager.Savedesk(userDesk);
			if(iResult==1){
				session.removeAttribute("userdesklist");
				session.removeAttribute("userdeskFold");
				// 获取用户自定义菜单的信息
				List userdeskList=this.programFoldManager.getUserDeskList(sysuser.getYhdh(),rightsobj);
				Vector foldList=new Vector();
				String mldh="";
				if(userdeskList!=null){
					for(int i=0;i<userdeskList.size();i++){
						UserDesk userdesk=(UserDesk)userdeskList.get(i);
						if(!mldh.equals(userdesk.getMldh())){
							foldList.add(userdesk);
						}
						mldh=userdesk.getMldh();
					}
				}
				session.setAttribute("userdesklist",userdeskList);
				session.setAttribute("userdeskFold",foldList);
				out.println("<script language=javascript>parent.resultSave('"+iResult+"','桌面设置成功！');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+iResult+"','桌面设置时发生错误！');</script>");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	//获取民警和协警信息
	@RequestMapping(params = "method=userinfoquery")
	public ModelAndView userinfoquery(HttpServletRequest request,
			HttpServletResponse response,SysUser obj) throws Exception{
		request.setCharacterEncoding("utf-8");
		String mav="jsp/choosepolice";
		String xm=URLDecoder.decode(obj.getXm(), "UTF-8");
		obj.setXm(xm);
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			request.setAttribute("queryobj",obj);
			request.setAttribute("cdbh",Constants.MENU_YHGL);
			request.setAttribute("sysService",this.sysService);	
			request.setAttribute("sysService",this.gHtmlService);	
			request.setAttribute("sysService",this.gSystemService);
			request.setAttribute("sysService",this.gDepartmentService);
			request.setAttribute("sysService",this.gSysparaCodeService);
			List querylist=this.sysuserManager.getUserinfoList(obj);
			request.setAttribute("querylist", querylist);
			if(obj.getSfmj().equals("1")){
				mav="jsp/choosepolice";
			}else{
				mav="jsp/chooseemployee";
			}
		}catch(Exception ex){
            request.setAttribute("message", ex.getMessage());
            mav="error";
            ex.printStackTrace();
		}
		return redirectMav(mav);
	}		

	
	//获取角色菜单列表有可能是多角色
	@RequestMapping(params = "method=rolemenuresult")
	public ModelAndView rolemenuresult(HttpServletRequest request,
			HttpServletResponse response){
		String mav="jsp/rolemenuresult";
		try{
			List foldList=new ArrayList();
			Hashtable foldHt=new Hashtable();
			List roleMenuList=new ArrayList();			
			// 获取用户签名
			// 运行模式
			String xtyxms=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
			FrmUserprog frmuserprog=new FrmUserprog();
			frmuserprog.setYxsjk(xtyxms);
			
			String glbm=(String)request.getParameter("glbm");
			if("".equals(glbm)){
				glbm=this.gSysparaCodeService.getSysParaValue("00","DJGLBM");
			}
			Department dept=gDepartmentService.getDepartment(glbm);
			String jsdh=(String)request.getParameter("jsdh");
			jsdh=jsdh.replaceAll("A", "#");
			if(!jsdh.equals("")){
				// 设置查询条件
				frmuserprog.setJsdh(jsdh);
				frmuserprog.setCxsx(dept.getBmjb());
	
				// 获取目录
				foldList=this.roleprogManager.getFoldListByJsdh(frmuserprog);
				// 获取所有program
				List programList=this.roleprogManager.getProgramListByJsdh2(frmuserprog);
				// 获取所有function
				List functionList=this.roleprogManager.getFunctionListByJsdh(frmuserprog);
				foldHt=this.roleprogManager.getUnionFoldList(foldList,programList,functionList);
			}
			// 补充信息
			String rolemenu=this.roleprogManager.getoutcdbh(foldHt,roleMenuList,"1",4);
			request.setAttribute("rolemenu",rolemenu);
		}catch(Exception ex){
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}	
	
	//
	// zhoujn 20100524 加载模式
	@RequestMapping(params = "method=queryProgramListByJsdh")
	public void queryProgramListByJsdh(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();

		Hashtable foldHt=new Hashtable();
		List roleMenuList=new ArrayList();

		// 运行模式
		String xtyxms=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
		FrmUserprog frmuserprog=new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);
		// 获取管理部门
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department depObj=userSession.getDepartment();

		String yhdh=userSession.getSysuser().getYhdh();

		String type=(String)request.getParameter("type");
		if(type.equals("1")){
			String sjjsdh=(String)request.getParameter("sjjsdh");
			String jscj=(String)request.getParameter("jscj");
			// 设置查询条件
			frmuserprog.setJsdh(sjjsdh);
			frmuserprog.setCxsx("");
			// 获取目录
			List foldList=this.roleprogManager.getFoldListByJsdh(frmuserprog);
			// 获取所有program
			List programList=this.roleprogManager.getProgramListByJsdh2(frmuserprog);
			// 获取所有function
			List functionList=this.roleprogManager.getFunctionListByJsdh(frmuserprog);
			foldList=this.roleprogManager.unionFoldList(foldList,programList,functionList);
		}else if(type.equals("2")){
			// 获取维护用户对应菜单
			String whyh=(String)request.getParameter("yhdh");
			String jscj=(String)request.getParameter("jscj");

			roleMenuList=this.roleprogManager.getUserMenuListByYhdh(whyh);
			List userfoldList=this.roleprogManager.getFoldListByYhdh(whyh);
			List userprogramList=this.roleprogManager.getProgramListByYhdh(whyh);
			List userfunctionList=this.roleprogManager.getFunctionListByYhdh(whyh);
			
			// 设置查询条件
			frmuserprog.setYhdh(yhdh);
			frmuserprog.setCxsx(jscj);

			List foldList=this.roleprogManager.getFoldListByYhdh(frmuserprog);
			List programList=this.roleprogManager.getProgramListByYhdh(frmuserprog);
			List functionList=this.roleprogManager.getFunctionListByYhdh(frmuserprog);
			
			//合并
			foldList=comFoldList(foldList,userfoldList);
			programList=comProgramList(programList,userprogramList);
			functionList=comFunctionList(functionList,userfunctionList);
			
			foldHt=this.roleprogManager.getUnionFoldList(foldList,programList,functionList);

		}

		// 补充信息
		String result=this.roleprogManager.getoutcdbh(foldHt,roleMenuList,type,4);
		out.println(result);
		out.close();
		
		//构造系统管理员和用户全菜单
		//将全菜单分为三类，
		//1用户本身菜单，不在系统管理员之列
		//2用户本身菜单、在系统管理员之列
		//3系统管理员菜单（不在上面之列）

	}
	
	
	//用于桌面快捷方式设置
	@RequestMapping(params = "method=queryDeskProgramList")
	public void queryDeskProgramList(HttpServletRequest request,HttpServletResponse response) 
		throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();

		Hashtable foldHt=new Hashtable();
		//List roleMenuList=new ArrayList();

		// 获取管理部门
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department depObj=userSession.getDepartment();
		String yhdh=userSession.getSysuser().getYhdh();

		List userdeskList = this.roleprogManager.getUserDeskmenu(yhdh);
		//获取当前权限模式
		String qxms=userSession.getSysuser().getQxms();
		List foldList = new ArrayList();
		List programList = new ArrayList();
		List functionList = new ArrayList();
		if(qxms.equals("1")){
			// 运行模式
			String xtyxms=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
			FrmUserprog frmuserprog=new FrmUserprog();
			frmuserprog.setYxsjk(xtyxms);
			frmuserprog.setYhdh(yhdh);
			frmuserprog.setCxsx("");
			// 获取目录
			foldList=this.roleprogManager.getUserFoldListByYhdh(frmuserprog);
			// 获取所有program
			programList=this.roleprogManager.getUserProgramListByYhdh(frmuserprog);
		}else if(qxms.equals("2")){
			//自由模式
			foldList=this.roleprogManager.getFoldListByYhdh(yhdh);
			programList=this.roleprogManager.getProgramListByYhdh(yhdh);
		}
		foldHt=this.roleprogManager.getUnionFoldList(foldList,programList);
		// 补充信息
		String result=this.roleprogManager.getDeskoutcdbh(foldHt,userdeskList,"2",4);
		out.println(result);
		out.close();
	}	
	
	
	private List comFoldList(List foldlist,List userfoldlist){
		for(int i=0; i<userfoldlist.size();i++){
			Fold temp=(Fold)userfoldlist.get(i);
			if(!checkFold(foldlist,temp)){
				foldlist.add(temp);
			}
		}
		return foldlist;
	}
	
	private boolean checkFold(List foldlist,Fold fold){
		boolean result=false;
		for(int i=0;i<foldlist.size();i++){
			Fold temp=(Fold)foldlist.get(i);
			if(temp.getMldh().equals(fold.getMldh())){
				result=true;
				break;
			}
		}
		return result;
	}
	
	
	//合并program
	private List comProgramList(List programlist,List userprogramlist){
		for(int i=0; i<userprogramlist.size();i++){
			Program temp=(Program)userprogramlist.get(i);
			if(!checkProgram(programlist,temp)){
				temp.setBz("AAA");
				programlist.add(temp);
			}
		}
		return programlist;
	}
	
	private boolean checkProgram(List programlist,Program program){
		boolean result=false;
		for(int i=0;i<programlist.size();i++){
			Program temp=(Program)programlist.get(i);
			if(temp.getCdbh().equals(program.getCdbh())){
				result=true;
				break;
			}
		}
		return result;
	}
	
	
	private List comFunctionList(List functionlist,List userfunctionlist){
		for(int i=0; i<userfunctionlist.size();i++){
			Function temp=(Function)userfunctionlist.get(i);
			if(!checkFunction(functionlist,temp)){
				temp.setBz("AAA");
				functionlist.add(temp);
			}
		}
		return functionlist;
	}
	
	private boolean checkFunction(List functionlist,Function function){
		boolean result=false;
		for(int i=0;i<functionlist.size();i++){
			Function temp=(Function)functionlist.get(i);
			if(temp.getCdbh().equals(function.getCdbh())&&temp.getGnid().equals(function.getGnid())){
				result=true;
				break;
			}
		}
		return result;
	}	
	/**
	 * 角色代号获取菜单
	 * 
	 */
	// zhoujn 20100524 加载模式
	/*
	public void queryProgramList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();

		// 运行模式
		String xtyxms=this.sysService.getSysParaValue("00","XTYXMS");
		FrmUserprog frmuserprog=new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);

		// 获取管理部门
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department depObj=userSession.getDepartment();

		List foldList=new ArrayList();
		String yhdh=userSession.getSysuser().getYhdh();

		// 维护用户
		String whyh=(String)request.getParameter("yhdh");
		List roleMenuList=new ArrayList();
		// 定义权限模式
		String qxms="";
		// 新增为空，1角色，2自由
		if(whyh.equals("")){
			// 获取部门信息
			String glbm=(String)request.getParameter("glbm");
			Department whyhdept=sysService.getDepartment(glbm);
			// 缺省处理,自由模式
			// 设置查询条件
			frmuserprog.setYhdh(yhdh);
			frmuserprog.setCxsx(whyhdept.getBmjb());

			foldList=this.roleprogManager.getFoldListByYhdh(frmuserprog);
			List programList=this.roleprogManager.getProgramListByYhdh(frmuserprog);
			List functionList=this.roleprogManager.getFunctionListByYhdh(frmuserprog);
			foldList=this.roleprogManager.unionFoldList(foldList,programList,functionList);
			qxms="2";
		}else{
			// 获取用户信息
			SysUser sysuser=(SysUser)this.sysuserManager.getSysuser(whyh);
			// 获取部门信息
			Department whyhdept=sysService.getDepartment(sysuser.getGlbm());
			qxms=sysuser.getQxms();
			if(qxms.equals("1")){
				// 获取该用户对应角色，用#分割，获取维护人员的角色，
				// 显示重叠角色菜单列表，该人员已授予角色，用#分隔

				// 获取用户对应角色
				List rolelist=(List)this.roleprogManager.getRoleList(yhdh,whyh);

				// 获取上级角色代号，#分割
				String sjjsdh=getSjjsdh(rolelist);
				if(!sjjsdh.equals("")){
					// 设置查询条件
					frmuserprog.setJsdh(sjjsdh);
					frmuserprog.setCxsx("");

					// 获取目录
					foldList=this.roleprogManager.getFoldListByJsdh(frmuserprog);
					// 获取所有program
					List programList=this.roleprogManager.getProgramListByJsdh2(frmuserprog);
					// 获取所有function
					List functionList=this.roleprogManager.getFunctionListByJsdh(frmuserprog);
					foldList=this.roleprogManager.unionFoldList(foldList,programList,functionList);
				}

			}else if(qxms.equals("2")){
				// 获取该用户对应角色权限菜单，与维护人员菜单对应
				// 区分系统管理员
				// 按用户选择可授权菜单列表
				// 设置查询条件
				frmuserprog.setYhdh(yhdh);
				frmuserprog.setCxsx(whyhdept.getBmjb());

				foldList=this.roleprogManager.getFoldListByYhdh(frmuserprog);
				List programList=this.roleprogManager.getProgramListByYhdh(frmuserprog);
				List functionList=this.roleprogManager.getFunctionListByYhdh(frmuserprog);
				foldList=this.roleprogManager.unionFoldList(foldList,programList,functionList);

				// 获取维护用户对应菜单
				roleMenuList=this.roleprogManager.getUserMenuListByYhdh(whyh);
			}
		}

		// 补充信息
		String result=getoutcdbh(foldList,roleMenuList,qxms);
		out.println(result);
		out.close();
	}
	*/

	// 修改根据权限模式修改显示

	private String getSjjsdh(List rolelist){
		String result="";
		for(int i=0;i<rolelist.size();i++){
			UserRole userrole=(UserRole)rolelist.get(i);
			result+=userrole.getJsdh()+"#";
		}
		if(result.length()>0){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}


	public void setDepartmentManager(DepartmentManager departmentManager){
		this.departmentManager=departmentManager;
	}

	public void setRoleManager(RoleManager roleManager){
		this.roleManager=roleManager;
	}

	public CommManager getCommManager(){
		return commManager;
	}

	public void setCommManager(CommManager commManager){
		this.commManager=commManager;
	}

	// 获取该用户对应用户级别的审批权限
	private List getSpjblist(List spjblist,String bmjb){
		List result=new ArrayList();
		for(int i=0;i<spjblist.size();i++){
			Code code=(Code)spjblist.get(i);
			if(code.getDmsm4().equals(bmjb)){
				result.add(code);
			}
		}
		return result;
	}

	// zhoujn 20100526 动态获取用户列表
	@RequestMapping(params = "method=queryUserList")
	public void queryUserList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/xml; charset=GBK");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();

		String glbm=(String)request.getParameter("glbm");
		SysUser user=new SysUser();
		user.setGlbm(glbm);
		List objlist=this.sysuserManager.getSysusers(user);
		String result="";
		result+="<?xml version=\"1.0\" encoding=\"GB2312\"?>";
		result+="<response>";
		if(objlist!=null){
			Department obj=null;
			for(int i=0;i<objlist.size();i++){
				SysUser sysuser=(SysUser)objlist.get(i);
				result+="<unit>";
				result+="<dm>"+sysuser.getYhdh()+"</dm>";
				result+="<mc>"+sysuser.getXm()+"</mc>";
				result+="</unit>";
			}
		}

		result+="</response>";

		out.println(result);
		out.close();
	}
	
	//用户维护时候，动态加载操作权限
	//20110809
	@RequestMapping(params = "method=queryUserczqx")
	public void queryUserczqx(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=GBK");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();

		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);	
		String result="";
		try{
			String yhdh=(String)request.getParameter("yhdh");
			String glbm=(String)request.getParameter("glbm");
			String sfmj=(String)request.getParameter("sfmj");
			
			Department dep=gDepartmentService.getDepartment(glbm);
			
			Role queryrole=new Role();
			queryrole.setJscj(dep.getBmjb());
			queryrole.setYhdh(userSession.getSysuser().getYhdh());
			queryrole.setGlbm(dept.getGlbm());
			if(sfmj.equals("4")){
				queryrole.setJssx("3");
			}
			List grantrolelist=this.roleManager.getRoleList(queryrole);
			//20111221 改为管理权限不加角色层级限制
			queryrole.setCzqx("1");
			List grantrolelistall=this.roleManager.getRoleList(queryrole);
			

			//获取当前权限
			List userrolelist=this.sysuserManager.getUserRoleList(yhdh);
			Hashtable roleht=this.sysuserManager.combinRoleList(grantrolelist, userrolelist);
			
			//获取当前用户可授予权限
			List usergrantrolelist=this.sysuserManager.getUserGrantRoleList(yhdh);
			Hashtable grantroleht=this.sysuserManager.combinRoleList(grantrolelistall, usergrantrolelist);
			
			//处理只有顶级部门可授予0001权限
			String XTYXMS=this.gSysparaCodeService.getSysParaValue("00", "XTYXMS");
			String topbmjb=getTopbmjb(XTYXMS);
			if(!dep.getBmjb().equals(topbmjb)){
				roleht=filterToproleht(roleht);
				grantroleht=filterToproleht(grantroleht);
			}
			//request.setAttribute("roleht",roleht);
			//request.setAttribute("grantroleht",grantroleht);	
			result=result+sysuserManager.getRolelistHtml("yhz",5,roleht);
			result=result+"AAAAA"+sysuserManager.getRolelistHtml("yyyhz",5,grantroleht);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		out.println(result);
		out.close();
	}	
	
	///20111102获取该用户当前权限信息
	@RequestMapping(params = "method=queryUserczqxtrack")
	public void queryUserczqxtrack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=GBK");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();

		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);	
		String result="";
		try{
			String yhdh=(String)request.getParameter("yhdh");
			//获取当前权限
			List userrolelist=this.sysuserManager.getUserRoleList(yhdh);
			Hashtable roleht=new Hashtable();
			roleht.put("rolelist5", userrolelist);
			
			//获取当前用户可授予权限
			List usergrantrolelist=this.sysuserManager.getUserGrantRoleList(yhdh);
			Hashtable grantroleht=new Hashtable();
			grantroleht.put("rolelist5", usergrantrolelist);
			
			result=result+sysuserManager.getRolelistHtml("yhz",5,roleht);
			result=result+"AAAAA"+sysuserManager.getRolelistHtml("yyyhz",5,grantroleht);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		out.println(result);
		out.close();
	}
	
	
	//转移到查询窗口
	@RequestMapping(params = "method=choosecdbhquery")
	public ModelAndView choosecdbhquery(HttpServletRequest request,HttpServletResponse response){
		String mav="jsp/choosecdbhquery";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);		
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");
			request.setAttribute("department",dept);
			request.setAttribute("cdbh",Constants.MENU_YHGL);
			request.setAttribute("sysService",this.sysService);
			String sqms=(String)request.getParameter("sqms");
			request.setAttribute("sqms",sqms);
			mav="jsp/choosecdbhquery";
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){		
        	ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}
	
	@RequestMapping(params = "method=choosecdbhresult")
	public ModelAndView choosecdbhresult(HttpServletRequest request,HttpServletResponse response,
			Program program) throws Exception{
		String mav="jsp/choosecdbhresult";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_YHGL,"","");       	
			// 分页
			String sqms=(String)request.getParameter("sqms");
			List querylist=null;
			if(sqms.equals("1")){
				String jsmc=(String)request.getParameter("jsmc");
				String jslx=(String)request.getParameter("jslx");
				//分页
				PageController controller = new PageController(request);
				controller.setPageSize(2000);
				Role role=new Role();
				role.setYhdh(userSession.getSysuser().getYhdh());
				role.setGlbm(userSession.getSysuser().getGlbm());
				role.setJsmc(jsmc);
				role.setJslx(jslx);
				querylist = this.roleManager.getRoleListByPagesize(role,controller);
				mav="jsp/choosejsdhresult";
			}else if(sqms.equals("2")){
				querylist=this.programFoldManager.getProgramList(program.getCxmc());
				mav="jsp/choosecdbhresult";
			}
			request.setAttribute("querylist",querylist);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("roleManager",this.roleManager);
			request.setAttribute("sysuserManager",sysuserManager);
			request.setAttribute("gDepartmentService", this.gDepartmentService);
			request.setAttribute("gSysparaCodeService",this.gSysparaCodeService);
			request.setAttribute("cdbh",Constants.MENU_YHGL);

			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_YHGL,"","",1000);
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav="error";
		}
		return redirectMav(mav);
	}	
	
	
	//根据系统运行模式，获取顶级部门级别
	private String getTopbmjb(String XTYXMS){
		String result="";
		if(XTYXMS.equals("1")){
			result="3";
		}else if(XTYXMS.equals("2")){
			result="2";
		}else if(XTYXMS.equals("3")){
			result="2";
		}else if(XTYXMS.equals("4")){	
			result="1";
		}
		return result;
	}
	
	//获取操作和管理权限的0001
	private Hashtable filterToproleht(Hashtable roleht){
		Hashtable result = new Hashtable();
		List rolelist1=(List)roleht.get("rolelist1");
		List rolelist2=(List)roleht.get("rolelist2");
		List rolelist3=(List)roleht.get("rolelist3");
		List rolelist4=(List)roleht.get("rolelist4");
		
		result.put("rolelist1", filterToplist(rolelist1));
		result.put("rolelist2", filterToplist(rolelist2));
		result.put("rolelist3", filterToplist(rolelist3));
		result.put("rolelist4", filterToplist(rolelist4));		
		
		return result;
	}
	
	//过滤0001角色
	private List filterToplist(List rolelist){
		List result=new ArrayList();
		for(int i=0;i<rolelist.size();i++){
			Role role =(Role)rolelist.get(i);
			if(!role.getJsdh().equals("0001")){
				result.add(role);
			}
		}	
		return result;
	}
	

}
