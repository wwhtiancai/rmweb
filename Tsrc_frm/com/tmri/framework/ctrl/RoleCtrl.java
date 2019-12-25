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
	 * ȡ��ĳ��������Ȩ��
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
			//������
			String glbm =(String) request.getParameter("glbm");
			String bhxj =(String) request.getParameter("bhxj");
			//
			
			String cznr = "ȡ������Ȩ�޹���:" + role.getCxdh();
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
				cznr=cznr+",glbm:"+glbm+"�����¼�";
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
	 * ת���ɫ����������   
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
			// ֻ�й���Ա���Խ����ҳ��
			String xtgly = userSession.getSysuser().getXtgly();
			if (xtgly.equals("2")) {
				throw new Exception("���û���ϵͳ����Ա��" + Constants.SAFE_CTRL_MSG_101);
			}
			
			//��ȡ�û��Ŀ�ά����ɫ����
			List list=this.gSysparaCodeService.getCodes(this.xtlb, "0003");
			List jbslist=getUserJb(list,userSession.getDepartment().getBmjb());
			request.setAttribute("jbslist", jbslist);	
			
			//����ֵ
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
	 * ��ѯϵͳ��ɫ�б�
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
			//��ҳ
			PageController controller = new PageController(request);
			controller.setPageSize(10);
			role.setYhdh(userSession.getSysuser().getYhdh());
			//��Ϊȡ�û�������
			role.setGlbm(userSession.getSysuser().getGlbm());
			List queryList = this.roleManager.getRoleListByPagesize(role,
					controller);
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", controller);
			
			//����ֵ
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
	 * ��ɫ��������>�½�
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
			//��ȡ�û��Ŀ�ά����ɫ����
			List list=this.gSysparaCodeService.getCodes(this.xtlb, "0003");
			List jbslist=getUserJb(list,userSession.getDepartment().getBmjb());
			request.setAttribute("jbslist", jbslist);	
			
			
			Role role = new Role();
			//����������ѡ���Զ����ɫ����ͨ���ܣ���ATM��ɫ
			String jssx=(String)request.getParameter("jssx");
			role.setJssx(jssx);
			//role.setJssxmc("�Զ����ɫ");			
			role.setGlbm(sysUser.getGlbm());
			// ����ȱʡֵ�ж�
			String yhdh = userSession.getSysuser().getYhdh();
			/*
			if (yhdh.equalsIgnoreCase(Constants.SYS_SUPER_USER)) {
				// �����û�
				List roleList = new ArrayList();
				Role temp = new Role();
				temp.setJsdh(Constants.SYS_SUPER_ROLE);
				temp.setJsmc(Constants.SYS_SUPER_ROLEMC);
				roleList.add(temp);
				request.setAttribute("roleList", role2code(roleList));
				
				role.setSjjsdh(Constants.SYS_SUPER_ROLE);
				//��ȡ��һ����ɫ����
				Code codetemp=(Code)jbslist.get(0);
				role.setJscj(codetemp.getDmz());
				request.setAttribute("frmRole", role);

			} else {
			*/
			// ����ϵͳ����Ա��������
			// �ж��Ƿ��п���Ȩ�б�
			List roleList = this.roleManager.getUsergrantroleList(yhdh);
			if (roleList == null || roleList.size() == 0) {
				throw new Exception("�޿�����Ȩ��ɫ�б�!");
			}
			request.setAttribute("roleList", role2code(roleList));
			
			//��ʼ������
			//ȡ��һ����ɫ
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
	 * ��ɫ��������>�򿪱༭
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
			//��ȡ�û��Ŀ�ά����ɫ����
			List list=this.gSysparaCodeService.getCodes(this.xtlb, "0003");
			List jbslist=getUserJb(list,userSession.getDepartment().getBmjb());
			request.setAttribute("jbslist", jbslist);
			
			Role role = this.roleManager.getRole(jsdh);
			//�����û�
			request.setAttribute("frmRole", role);

			// ����ȱʡֵ�ж�
			String yhdh = userSession.getSysuser().getYhdh();		
			/*
			if (yhdh.equalsIgnoreCase(Constants.SYS_SUPER_USER)) {
				// �����û�
				List roleList = new ArrayList();
				Role temp = new Role();
				temp.setJsdh(Constants.SYS_SUPER_ROLE);
				temp.setJsmc(Constants.SYS_SUPER_ROLEMC);
				roleList.add(temp);
				request.setAttribute("roleList", role2code(roleList));
				
			} else {
			*/
			// ����ϵͳ����Ա��������
			// �ж��Ƿ��п���Ȩ�б�
			List roleList = this.roleManager.getUsergrantroleList(yhdh);
			if (roleList == null || roleList.size() == 0) {
				throw new Exception("�޿�����Ȩ��ɫ�б�!");
			}
			request.setAttribute("roleList", role2code(roleList));
			//}		
			
			sysService.genWebKey(request);
	
			// zhoujn 20100520
			// ���JSSX����ɫ���ԣ�Ϊ1��Ԥ���壩���ṩֻ�����棻
			String jslx=this.roleManager.getJslx(role.getJsdh(),yhdh);
			if (jslx.equals("2")) {
				request.setAttribute("modal", "edit");
				mav = "jsp/roleEdit";
			} else {
				//�����жϸý�ɫ������ӵ�н�ɫ�������Զ����ɫ�����Ǽ̳��Զ����ɫ
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
	 * ��ɫ����������>����
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
			//20111101,��ɫ�������ԭ�Ƚ�ɫ���
			if (modal.equals("new")) {
				cznr = "������ɫ,��ɫ����:" + role.getJsmc();
				cznr += ";��ɫ����:" + role.getCxdh();
			} else {
				//��ȡ��ǰ��ɫȨ����Ϣ
				//List  rolementlist=this.roleprogManager.getProgramListByJsdh(role.getJsdh());
				cznr = "�޸Ľ�ɫ,��ɫ����:" + role.getJsdh();
				cznr += ";��ɫ����:" + role.getCxdh();
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
				//gnlb������,0310,0313,0316,0318,0322,0350
				if(!usermenu.getGnlb().equals(usermenutemp.getGnlb())){
					
				}
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * ��ɫ����������>ɾ��
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

			String cznr = "ɾ����ɫ,��ɫ����:" + role.getJsdh();
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
	
	//��ȡ��ɫ�˵��б��п����Ƕ��ɫ
	@RequestMapping(params = "method=rolemenuresult")
	public void rolemenuresult(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		List foldList=new ArrayList();
		Hashtable foldHt=new Hashtable();
		List roleMenuList=new ArrayList();			
		// ��ȡ�û�ǩ��
		// ����ģʽ
		String xtyxms=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
		FrmUserprog frmuserprog=new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);
		

		String jsdh=(String)request.getParameter("jsdh");
		String jscj=(String)request.getParameter("jscj");
		jsdh=jsdh.replaceAll("A", "#");
		if(!jsdh.equals("")){
			// ���ò�ѯ����
			frmuserprog.setJsdh(jsdh);
			frmuserprog.setCxsx(jscj);

			// ��ȡĿ¼
			foldList=this.roleprogManager.getFoldListByJsdh(frmuserprog);
			// ��ȡ����program
			List programList=this.roleprogManager.getProgramListByJsdh2(frmuserprog);
			// ��ȡ����function
			List functionList=this.roleprogManager.getFunctionListByJsdh(frmuserprog);
			//foldList=this.roleprogManager.unionFoldList(foldList,programList,functionList);
			foldHt = this.roleprogManager.getUnionFoldList(foldList,
					programList, functionList);
		}
		// ������Ϣ
		//String rolemenu=getoutcdbh(foldList,roleMenuList,"1");
		String rolemenu=this.roleprogManager.getoutcdbh(foldHt,roleMenuList,"1",5);
		out.println(rolemenu);
		out.close();		
	}		
	
	
	/**
	 * �������Բ㼶��ȡ����
	 * 
	 */
	//��ȡ�ý�ɫ�Ĳ˵�������checkbox
	@RequestMapping(params = "method=queryProgramListByJscj")
	public void queryProgramListByJscj(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		//����ģʽ
		String xtyxms=this.gSysparaCodeService.getSysParaValue("00", "XTYXMS");
		FrmUserprog frmuserprog =new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);		
		
		String jsdh = (String) request.getParameter("jsdh");
		String sjjsdh = (String) request.getParameter("sjjsdh");
		String jscj = (String) request.getParameter("jscj");
		//������ɫ�����ж�20110809
		String jssx = (String) request.getParameter("jssx");
 		List roleMenuList = this.roleprogManager.getProgramListByJsdh(jsdh);
		// ��ȡ������
		HttpSession session = request.getSession();
		UserSession userSession = gSystemService.getSessionUserInfo(session);
		Department depObj = userSession.getDepartment();

		
		List foldList =new ArrayList();
		String yhdh = userSession.getSysuser().getYhdh();
		/*
		if (yhdh.equalsIgnoreCase(Constants.SYS_SUPER_USER)) {
			// �����û�
			//��ѯ����
			frmuserprog.setCxsx(jscj);
			frmuserprog.setXtlb(Constants.SYS_ALL_XTLB);
			foldList = this.roleprogManager.getFoldList(frmuserprog);
			List programList = this.roleprogManager.getProgramList(frmuserprog);
			List functionList = this.roleprogManager.getFunctionList(frmuserprog);
			foldList = this.roleprogManager.unionFoldList(foldList,
					programList, functionList);
		} else {
		*/
		// ����ϵͳ����Ա��������
		// �ж��Ƿ��п���Ȩ�б�
		//��ȡĿ¼
		//���ò�ѯ����
		frmuserprog.setCxsx(jscj);
		frmuserprog.setJsdh(sjjsdh);
		//20110809
		frmuserprog.setJssx(jssx);
		foldList = this.roleprogManager.getFoldListByJsdh(frmuserprog);
		//��ȡ����program
		List programList = this.roleprogManager.getProgramListByJsdh2(frmuserprog);
		//��ȡ����function
		List functionList = this.roleprogManager.getFunctionListByJsdh(frmuserprog);
		Hashtable foldHt = this.roleprogManager.getUnionFoldList(foldList,
				programList, functionList);
		//}
		// ������Ϣ
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

		// ����ģʽ
		String xtyxms=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
		FrmUserprog frmuserprog=new FrmUserprog();
		frmuserprog.setYxsjk(xtyxms);
		// ��ȡ������
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		Department depObj=userSession.getDepartment();

		String yhdh=userSession.getSysuser().getYhdh();
		// ���ò�ѯ����
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
