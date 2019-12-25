package com.tmri.framework.ctrl;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tmri.rfid.util.GsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.framework.bean.FrmUserprog;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.service.RoleManager;
import com.tmri.framework.service.RoleprogManager;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Controller
@RequestMapping("/role.frm")
public class RoleCtrl extends FrmCtrl {
	
	@Autowired
	private RoleManager roleManager;	
	@Autowired
	private RoleprogManager roleprogManager;
	
	
	/**
	 * 取消某部门自由权限
	 */
	@RequestMapping(params = "method=usermenuCancel")
	public ModelAndView usermenuCancel(HttpServletRequest request,
			HttpServletResponse response) {
		String mav = "jsp/usermenuCancel";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_ZYQX_QX,"","");
			request.setAttribute("department", dept);
			request.setAttribute("sysService", this.sysService);
			sysService.genWebKey(request);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_ZYQX_QX,"","",1000);
		} catch (Exception ex) {		
        	ex.printStackTrace();
			request.setAttribute("message", StringUtil.getExpMsg(ex));
			mav="error";			
		}		
		return redirectMav(mav);
	}	
	
	@RequestMapping(params = "method=saveUsermenuCancel")
	public void saveUsermenuCancel(HttpServletRequest request,
			HttpServletResponse response, Role command) throws Exception {
		Role role = (Role) command;
		response.setContentType(contentType);
		PrintWriter out = response.getWriter();
		SysResult returninfo = new SysResult();
		
		HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        Department dept = userSession.getDepartment();
        SysUser sysUser = userSession.getSysuser();
        String ip=FuncUtil.getRemoteIpdz(request);   	
		try {
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_ZYQX_QX,"","");
			if (sysService.checkWebkey(request) == 0) {
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			//管理部门
			String glbm =(String) request.getParameter("glbm");
			String bhxj =(String) request.getParameter("bhxj");
			//
			
			String cznr = "取消自由权限功能:" + role.getCxdh();
			if(bhxj!=null&&bhxj.equals("1")){
				Department depttemp=this.gDepartmentService.getDepartment(glbm);
				if(depttemp.getBmjb().equals("2")){
					glbm=" like '"+glbm.substring(0,2)+"%'";
				}else if(depttemp.getBmjb().equals("3")){
					if(!glbm.substring(2,4).equals("90")){
						glbm=" like '"+glbm.substring(0,4)+"%'";
					}else{
						glbm=" like '"+glbm.substring(0,6)+"%'";
					}
				}else if(depttemp.getBmjb().equals("4")){	
					glbm=" like '"+glbm.substring(0,8)+"%'";
				}else{
					glbm=" = '"+glbm + "'";
				}
				cznr=cznr+",glbm:"+glbm+"包含下级";
        	}else{
        		glbm=" in ('"+glbm+"')";
        		cznr=cznr+",glbm:"+glbm;
        	}


			if(cznr.length() > 3800) cznr = cznr.substring(0,3800);
			Log log = new Log(sysUser.getGlbm(), sysUser.getYhdh(),
					"", Constants.MENU_ZYQX_QX, "", cznr, ip, "");
			returninfo = this.roleManager.saveUsermenuCancel(glbm, role, log);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_ZYQX_QX,"","",1000);
        } catch (Exception ex) {
        	String errmsg = StringUtil.getExpMsg(ex);
        	ex.printStackTrace();
            returninfo.setRetcode("99");
            returninfo.setRetdesc(errmsg);
        }
        DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(Long.valueOf(returninfo.getRetcode()));
		dbreturninfo.setMsg1(returninfo.getRetdesc());
		dbreturninfo.setMsg(returninfo.getRetval());
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}
	
	
	/**
	 * 转入角色管理主界面   
	 * 
	 * @param request
	 * @param responsebn  
	 */
	@RequestMapping(params = "method=rolemain")
	public ModelAndView rolemain(HttpServletRequest request,
			HttpServletResponse response, Role command) {
		String mav="jsp/roleMain";
		HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        Department dept = userSession.getDepartment();
        SysUser sysUser = userSession.getSysuser();
        String ip=FuncUtil.getRemoteIpdz(request);   
        
		try{
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_JSGL,"","");
			// zhoujn 20100520
			// 只有管理员可以进入该页面
			String xtgly = userSession.getSysuser().getXtgly();
			if (xtgly.equals("2")) {
				throw new Exception("该用户非系统管理员，" + Constants.SAFE_CTRL_MSG_101);
			}
			
			//获取用户的可维护角色级别
			List list=this.gSysparaCodeService.getCodes(this.xtlb, "0003");
			List jbslist=getUserJb(list,userSession.getDepartment().getBmjb());
			request.setAttribute("jbslist", jbslist);	
			
			//返回值
			request.setAttribute("cdbh", Constants.MENU_JSGL);
			// request.setAttribute("sysService",this.sysService);
			request.setAttribute("gHtmlService",this.gHtmlService);
			request.setAttribute("department",dept);
			
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_JSGL,"","",1000);
		}catch(Exception ex){
        	ex.printStackTrace();
			request.setAttribute("message", StringUtil.getExpMsg(ex));
			mav="error";			
		}
		return redirectMav(mav);
	}

	
	
	/**
	 * 查询系统角色列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=roleresult")
	public ModelAndView roleresult(HttpServletRequest request,
			HttpServletResponse response, Role command) {

		Role role = (Role) command;
		String mav="jsp/roleResult";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);		
		try {
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_JSGL,"","");
			//分页
			PageController controller = new PageController(request);
			controller.setPageSize(10);
			role.setYhdh(userSession.getSysuser().getYhdh());
			//改为取用户管理部门
			role.setGlbm(userSession.getSysuser().getGlbm());
			List queryList = this.roleManager.getRoleListByPagesize(role,
					controller);
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", controller);
			
			//返回值
			request.setAttribute("cdbh", Constants.MENU_JSGL);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("gSysparaCodeService",this.gSysparaCodeService);
			request.setAttribute("gDepartmentService",this.gDepartmentService);
			request.setAttribute("roleManager",this.roleManager);
			
			request.setAttribute("gSystemService", this.gSystemService);
			
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_JSGL,"","",1000);
			mav="jsp/roleResult";
		} catch (Exception ex) {	
        	ex.printStackTrace();
			request.setAttribute("message", StringUtil.getExpMsg(ex));
			mav="error";
		}
		return redirectMav(mav);
	}	

	/**
	 * 角色————>新建
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=newAdd")
	public ModelAndView newAdd(HttpServletRequest request, HttpServletResponse response) {
		String mav="jsp/roleEdit";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);		
		try {
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_JSGL,"","");
			request.setAttribute("modal", "new");
			sysService.genWebKey(request);
			//获取用户的可维护角色级别
			List list=this.gSysparaCodeService.getCodes(this.xtlb, "0003");
			List jbslist=getUserJb(list,userSession.getDepartment().getBmjb());
			request.setAttribute("jbslist", jbslist);	
			
			
			Role role = new Role();
			//新增，增加选择自定义角色（普通功能）和ATM角色
			String jssx=(String)request.getParameter("jssx");
			role.setJssx(jssx);
			//role.setJssxmc("自定义角色");			
			role.setGlbm(sysUser.getGlbm());
			// 增加缺省值判断
			String yhdh = userSession.getSysuser().getYhdh();
			/*
			if (yhdh.equalsIgnoreCase(Constants.SYS_SUPER_USER)) {
				// 超级用户
				List roleList = new ArrayList();
				Role temp = new Role();
				temp.setJsdh(Constants.SYS_SUPER_ROLE);
				temp.setJsmc(Constants.SYS_SUPER_ROLEMC);
				roleList.add(temp);
				request.setAttribute("roleList", role2code(roleList));
				
				role.setSjjsdh(Constants.SYS_SUPER_ROLE);
				//获取第一个角色级别
				Code codetemp=(Code)jbslist.get(0);
				role.setJscj(codetemp.getDmz());
				request.setAttribute("frmRole", role);

			} else {
			*/
			// 其他系统管理员，待补充
			// 判断是否有可授权列表
			List roleList = this.roleManager.getUsergrantroleList(yhdh);
			if (roleList == null || roleList.size() == 0) {
				throw new Exception("无可用授权角色列表!");
			}
			request.setAttribute("roleList", role2code(roleList));
			
			//初始化数据
			//取第一个角色
			Role temp=(Role)roleList.get(0);
			role.setSjjsdh(temp.getJsdh());
			role.setJscj(userSession.getDepartment().getBmjb());
			request.setAttribute("frmRole", role);				
			//}
			String jslx="2";
			request.setAttribute("jslx", jslx);
			request.setAttribute("sysService", this.sysService);
			request.setAttribute("gHtmlService", this.gHtmlService);
			
			request.setAttribute("roleManager",this.roleManager);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_JSGL,"","",1000);
		} catch (Exception ex) {
        	ex.printStackTrace();
			request.setAttribute("message", StringUtil.getExpMsg(ex));
			mav="error";			
		}

		return redirectMav(mav);
	}

	/**
	 * 角色————>打开编辑
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=editOne")
	public ModelAndView editOne(HttpServletRequest request,
			HttpServletResponse response) {
		String mav = "jsp/roleEdit";
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department dept=userSession.getDepartment();
		SysUser sysUser=userSession.getSysuser();
		String ip=FuncUtil.getRemoteIpdz(request);		
		try{
			String jsdh = (String) request.getParameter("jsdh");
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_JSGL,"","");
			//获取用户的可维护角色级别
			List list=this.gSysparaCodeService.getCodes(this.xtlb, "0003");
			List jbslist=getUserJb(list,userSession.getDepartment().getBmjb());
			request.setAttribute("jbslist", jbslist);
			
			Role role = this.roleManager.getRole(jsdh);
			//重置用户
			request.setAttribute("frmRole", role);

			// 增加缺省值判断
			String yhdh = userSession.getSysuser().getYhdh();		
			/*
			if (yhdh.equalsIgnoreCase(Constants.SYS_SUPER_USER)) {
				// 超级用户
				List roleList = new ArrayList();
				Role temp = new Role();
				temp.setJsdh(Constants.SYS_SUPER_ROLE);
				temp.setJsmc(Constants.SYS_SUPER_ROLEMC);
				roleList.add(temp);
				request.setAttribute("roleList", role2code(roleList));
				
			} else {
			*/
			// 其他系统管理员，待补充
			// 判断是否有可授权列表
			List roleList = this.roleManager.getUsergrantroleList(yhdh);
			if (roleList == null || roleList.size() == 0) {
				throw new Exception("无可用授权角色列表!");
			}
			request.setAttribute("roleList", role2code(roleList));
			//}		
			
			sysService.genWebKey(request);
	
			// zhoujn 20100520
			// 如果JSSX（角色属性）为1（预定义），提供只读界面；
			String jslx=this.roleManager.getJslx(role.getJsdh(),yhdh);
			if (jslx.equals("2")) {
				request.setAttribute("modal", "edit");
				mav = "jsp/roleEdit";
			} else {
				//增加判断该角色，是子拥有角色，还是自定义角色，还是继承自定义角色
				request.setAttribute("modal", "view");
				mav = "jsp/roleView";
			}
			request.setAttribute("jslx", jslx);
			request.setAttribute("sysService", this.sysService);
			request.setAttribute("roleManager",this.roleManager);
			request.setAttribute("gHtmlService",this.gHtmlService);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_JSGL,"","",1000);
		} catch (Exception ex) {				
        	ex.printStackTrace();
			request.setAttribute("message", StringUtil.getExpMsg(ex));
			mav="error";			
		}		
		return redirectMav(mav);
	}

	/**
	 * 角色处理————>保存
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=saveRole", method = RequestMethod.POST)
    @ResponseBody
	public String saveRole(HttpServletRequest request,
                         HttpServletResponse response, Role command) throws Exception {
	    Map<String, String> resultMap = new HashMap<String, String>();
		Role role = (Role) command;
		SysResult returninfo = new SysResult();
		
		HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        Department dept = userSession.getDepartment();
        SysUser sysUser = userSession.getSysuser();
        String ip=FuncUtil.getRemoteIpdz(request);   	
		try {

			String modal = (String) request.getParameter("modal");
			String cznr = null;
			//20111101,角色变更新增原先角色情况
			if (modal.equals("new")) {
				cznr = "新增角色,角色名称:" + role.getJsmc();
				cznr += ";角色功能:" + role.getCxdh();
			} else {
				//获取当前角色权限信息
				//List  rolementlist=this.roleprogManager.getProgramListByJsdh(role.getJsdh());
				cznr = "修改角色,角色代号:" + role.getJsdh();
				cznr += ";角色功能:" + role.getCxdh();
			}
			
			if(cznr.length() > 3800) cznr = cznr.substring(0,3800);
			Log log = new Log(sysUser.getGlbm(), sysUser.getYhdh(),
					"", Constants.MENU_JSGL, "", cznr, ip, "");
			role.setJscj(StringUtil.replaceStr(role.getJscj(),"A", ""));
			role.setGlbm(sysUser.getGlbm());
			returninfo = this.roleManager.saveRole(role, log);
        } catch (Exception ex) {
        	String errmsg = StringUtil.getExpMsg(ex);
        	ex.printStackTrace();
            returninfo.setRetcode("99");
            returninfo.setRetdesc(errmsg);
        }

        resultMap.put("resultId", returninfo.getRetcode());
		resultMap.put("resultMsg", returninfo.getRetval());
        resultMap.put("resultMsg1", returninfo.getRetdesc());
        String result = GsonHelper.getGson().toJson(resultMap);
        return result;
	}
	
	public List getRolemenuList(String cxdh){
		List rolemenuList=new ArrayList();
		String[] arrCdbh=StringUtil.splitString(cxdh,'#');
		UserMenu userMenu=null;
		String xtlbs="";
		if(arrCdbh!=null){
			for(int i=0;i<arrCdbh.length;i++){
				if(arrCdbh[i]!=null&&arrCdbh[i]!=""){
					String[] menu=arrCdbh[i].split("-");
					userMenu=new UserMenu();
					userMenu.setXtlb(menu[0]);
					userMenu.setCdbh(menu[1]);
					if(menu.length>2)
						userMenu.setGnlb(menu[2]);
					else{
						userMenu.setGnlb("");
					}
					if(xtlbs.indexOf(userMenu.getXtlb())<0){
						if(xtlbs.equals("")){
							xtlbs+=userMenu.getXtlb();
						}else{
							xtlbs+=","+userMenu.getXtlb();
						}
					}
					rolemenuList.add(userMenu);
				}
			}
		}
		return rolemenuList;
	}
	
	
	public String getAddrolemenu(List  rolemenulist_new,List  rolemenulist_old){
		String resultadd="",resultsub="";
		for(int i=0;i<rolemenulist_new.size();i++){
			UserMenu usermenu=(UserMenu)rolemenulist_new.get(i);

			
		}
		
		return resultadd;
	}
	
	public String checkRolemenuList(UserMenu usermenu,List  rolemenulist_old){
		String result="";
		for(int j=0;j<rolemenulist_old.size();j++){
			UserMenu usermenutemp=(UserMenu)rolemenulist_old.get(j);
			if(usermenu.getXtlb().equals(usermenutemp.getXtlb())
					&&usermenu.getCdbh().equals(usermenutemp.getCdbh())){
				//gnlb不等于,0310,0313,0316,0318,0322,0350
				if(!usermenu.getGnlb().equals(usermenutemp.getGnlb())){
					
				}
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * 角色处理————>删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=removeRole")
	public void removeRole(HttpServletRequest request,
			HttpServletResponse response, Role command) throws Exception {
		Role role = (Role) command;
		response.setContentType(contentType);
		PrintWriter out = response.getWriter();
		SysResult returninfo = new SysResult();
		
		HttpSession session = request.getSession();
        UserSession userSession = gSystemService.getSessionUserInfo(session);
        Department dept = userSession.getDepartment();
        SysUser sysUser = userSession.getSysuser();
        String ip=FuncUtil.getRemoteIpdz(request);   		
		try {
			this.sysService.doBeginCall(request,this.xtlb,Constants.MENU_JSGL,"","");

			if (sysService.checkWebkey(request) == 0) {
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}

			String cznr = "删除角色,角色代号:" + role.getJsdh();
			Log log = new Log(sysUser.getGlbm(), sysUser.getXm(),
					"", Constants.MENU_JSGL, "", cznr, ip, "");
			returninfo = this.roleManager.removeRole(role, log);
			this.sysService.doEndCall(request,this.xtlb,Constants.MENU_JSGL,"","",1000);
        } catch (Exception ex) {
        	String errmsg = StringUtil.getExpMsg(ex);
        	ex.printStackTrace();
            returninfo.setRetcode("99");
            returninfo.setRetdesc(errmsg);
        }
		DbResult dbreturninfo=new DbResult();
		dbreturninfo.setCode(Long.valueOf(returninfo.getRetcode()));
		dbreturninfo.setMsg1(returninfo.getRetdesc());
		dbreturninfo.setMsg(returninfo.getRetval());
		
        out.println(FuncUtil.getReturnInfo(dbreturninfo));
	}
	
	//获取角色菜单列表有可能是多角色
	@RequestMapping(params = "method=rolemenuresult")
	public void rolemenuresult(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		List foldList=new ArrayList();
		Hashtable foldHt=new Hashtable();
		List roleMenuList=new ArrayList();			
		// 获取用户签名
		// 运行模式
		String xtyxms=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
		FrmUserprog frmuserprog=new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);
		

		String jsdh=(String)request.getParameter("jsdh");
		String jscj=(String)request.getParameter("jscj");
		jsdh=jsdh.replaceAll("A", "#");
		if(!jsdh.equals("")){
			// 设置查询条件
			frmuserprog.setJsdh(jsdh);
			frmuserprog.setCxsx(jscj);

			// 获取目录
			foldList=this.roleprogManager.getFoldListByJsdh(frmuserprog);
			// 获取所有program
			List programList=this.roleprogManager.getProgramListByJsdh2(frmuserprog);
			// 获取所有function
			List functionList=this.roleprogManager.getFunctionListByJsdh(frmuserprog);
			//foldList=this.roleprogManager.unionFoldList(foldList,programList,functionList);
			foldHt = this.roleprogManager.getUnionFoldList(foldList,
					programList, functionList);
		}
		// 补充信息
		//String rolemenu=getoutcdbh(foldList,roleMenuList,"1");
		String rolemenu=this.roleprogManager.getoutcdbh(foldHt,roleMenuList,"1",5);
		out.println(rolemenu);
		out.close();		
	}		
	
	
	/**
	 * 根据属性层级获取程序
	 * 
	 */
	//获取该角色的菜单，增加checkbox
	@RequestMapping(params = "method=queryProgramListByJscj")
	public void queryProgramListByJscj(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		//运行模式
		String xtyxms=this.gSysparaCodeService.getSysParaValue("00", "XTYXMS");
		FrmUserprog frmuserprog =new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);		
		
		String jsdh = (String) request.getParameter("jsdh");
		String sjjsdh = (String) request.getParameter("sjjsdh");
		String jscj = (String) request.getParameter("jscj");
		//新增角色属性判断20110809
		String jssx = (String) request.getParameter("jssx");
 		List roleMenuList = this.roleprogManager.getProgramListByJsdh(jsdh);
		// 获取管理部门
		HttpSession session = request.getSession();
		UserSession userSession = gSystemService.getSessionUserInfo(session);
		Department depObj = userSession.getDepartment();

		
		List foldList =new ArrayList();
		String yhdh = userSession.getSysuser().getYhdh();
		/*
		if (yhdh.equalsIgnoreCase(Constants.SYS_SUPER_USER)) {
			// 超级用户
			//查询条件
			frmuserprog.setCxsx(jscj);
			frmuserprog.setXtlb(Constants.SYS_ALL_XTLB);
			foldList = this.roleprogManager.getFoldList(frmuserprog);
			List programList = this.roleprogManager.getProgramList(frmuserprog);
			List functionList = this.roleprogManager.getFunctionList(frmuserprog);
			foldList = this.roleprogManager.unionFoldList(foldList,
					programList, functionList);
		} else {
		*/
		// 其他系统管理员，待补充
		// 判断是否有可授权列表
		//获取目录
		//设置查询条件
		frmuserprog.setCxsx(jscj);
		frmuserprog.setJsdh(sjjsdh);
		//20110809
		frmuserprog.setJssx(jssx);
		foldList = this.roleprogManager.getFoldListByJsdh(frmuserprog);
		//获取所有program
		List programList = this.roleprogManager.getProgramListByJsdh2(frmuserprog);
		//获取所有function
		List functionList = this.roleprogManager.getFunctionListByJsdh(frmuserprog);
		Hashtable foldHt = this.roleprogManager.getUnionFoldList(foldList,
				programList, functionList);
		//}
		// 补充信息
		String result="";
		try{
			result=this.roleprogManager.getoutcdbh(foldHt,roleMenuList,"2",5);
		}catch(Exception ex){
			ex.printStackTrace();
		}
//		System.out.println(result);
		out.println(result);
		out.close();
	}
	
	@RequestMapping(params = "method=queryProgramListByYhdh")
	public void queryProgramListByYhdh(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		// 设置查询条件
		frmuserprog.setYhdh(yhdh);
		//frmuserprog.setCxsx(jscj);

		List foldList=this.roleprogManager.getFoldListByYhdh(frmuserprog);
		List programList=this.roleprogManager.getProgramListByYhdh(frmuserprog);
		List functionList=this.roleprogManager.getFunctionListByYhdh(frmuserprog);
		foldHt=this.roleprogManager.getUnionFoldList(foldList,programList,functionList);

		String result=this.roleprogManager.getoutcdbh(foldHt,roleMenuList,"2",5);
		out.println(result);
		out.close();
	}	

	//
	private List role2code(List roleList){
		List result=new ArrayList();
		for(int i=0;i<roleList.size();i++){
			Role role=(Role)roleList.get(i);
			Code code=new Code();
			code.setDmz(role.getJsdh());
			code.setDmsm1(role.getJsmc());
			result.add(code);
		}
		return result;
	}

	//
	private List getUserJb(List list,String bmjb){
		List result=new ArrayList();
		int ibmjb=Integer.valueOf(bmjb).intValue();
		for(int i=0;i<list.size();i++){
			Code code=(Code)list.get(i);
			int idmz=Integer.valueOf(code.getDmz()).intValue();
			if(idmz>=ibmjb){
				result.add(code);
			}
		}
		return result;
	}

}
