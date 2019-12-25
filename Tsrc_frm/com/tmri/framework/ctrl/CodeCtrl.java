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

import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SmsContent;
import com.tmri.framework.bean.SmsSetup;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.service.CodeManager;
import com.tmri.framework.util.FrmTranslation;
import com.tmri.pub.util.XmlUtil;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Codetype;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
@Controller
@RequestMapping("/code.frm")
public class CodeCtrl extends FrmCtrl{
	@Autowired
	private CodeManager codeManager=null;
	@Autowired
	private GBasService gBasService;


	/**
	 * ��ת��ָ��ҳ�桪�����������ѯ������ά��
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=findForward")
	public ModelAndView findForward(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		String czlx=(String)request.getParameter("czlx");
		try{
			if(czlx.equals("wh")){
				this.sysService.doBeginCall(request,"00",Constants.MENU_DMWH,"","");
			}else{
				this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");
			}
			request.setAttribute("czlx",czlx);
			request.setAttribute("sysservice",this.sysService);
			request.setAttribute("gSysparaCodeService",this.gSysparaCodeService);
			
			request.setAttribute("gHtmlService",this.gHtmlService);
			
			return redirectMav("jsp/codeList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * ��ѯ�����б�����������ϵͳ����ѯ
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 */
	@RequestMapping(params = "method=queryList")
	public ModelAndView queryList(HttpServletRequest request,HttpServletResponse response,Codetype command){
		Codetype codetype=(Codetype)command;
		PageController controller=new PageController(request);
		controller.setPageSize(13);
		List queryList;
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		String czlx=(String)request.getParameter("czlx");
		try{
			if(czlx.equals("wh")){
				codetype.setLbsx("1");
				this.sysService.doBeginCall(request,"00",Constants.MENU_DMWH,"","");
			}else{
				this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");
			}
			queryList=this.codeManager.getCodetypesByPageSize(codetype,controller);
			request.setAttribute("queryList",queryList); // ���ز�ѯlist
			request.setAttribute("codetype",codetype); // ���ز�ѯ����
			request.setAttribute("controller",controller);
			request.setAttribute("czlx",czlx);
			// request.setAttribute("sysservice",this.sysService);
			
			request.setAttribute("gHtmlService",this.gHtmlService);
			
			request.setAttribute("gSysparaCodeService",this.gSysparaCodeService);
			return redirectMav("jsp/codeList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * ����ά�����������༭��ɾ��
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=editOne")
	public ModelAndView editOne(HttpServletRequest request,HttpServletResponse response){

		String dmlb=(String)request.getParameter("dmlb");
		String xtlb=(String)request.getParameter("xtlb");
		String cdbh=(String)request.getParameter("cdbh");
		List list=null;
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			String czlx="wh";
			this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");
			
			Codetype codetype=this.codeManager.getCodetype(xtlb,dmlb);
			if(codetype!=null){
				list=this.gSysparaCodeService.getCodes(codetype.getXtlb(),codetype.getDmlb());
			}
			request.setAttribute("codetype",codetype);
			request.setAttribute("codes",list);
			request.setAttribute("modal","edit");
			request.setAttribute("czlx",czlx);
			request.setAttribute("cdbh",cdbh);
			return redirectMav("jsp/codetypeEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}

	/**
	 * ����ά�����������½�����
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=newcode")
	public ModelAndView newcode(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");
			String dmlb=(String)request.getParameter("dmlb");
			String dmcd=(String)request.getParameter("dmcd");
			String xtlb=(String)request.getParameter("xtlb");

			request.setAttribute("modal","new");
			request.setAttribute("dmlb",dmlb);
			request.setAttribute("dmcd",dmcd);
			request.setAttribute("xtlb",xtlb);
			request.setAttribute("czlx",(String)request.getParameter("czlx"));
			return redirectMav("jsp/codeEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * ����ά�����������༭
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=editcode")
	public ModelAndView editcode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		String czlx=(String)request.getParameter("czlx");
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");
			String xtlb=(String)request.getParameter("xtlb");
			String dmlb=(String)request.getParameter("dmlb");
			String dmz=(String)request.getParameter("dmz");
			Code code=this.gSysparaCodeService.getCode(xtlb,dmlb,dmz);
			Codetype codetype=this.codeManager.getCodetype(xtlb,dmlb);

			request.setAttribute("modal","edit");
			request.setAttribute("code",code);
			request.setAttribute("czlx",czlx);
			if(codetype!=null){
				String d=codetype.getDmcd().toString();
				request.setAttribute("dmcd",d);
			}
			return redirectMav("jsp/codeEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * ����ά�����������������
	 * 
	 * @param request
	 * @param response
	 * @param command
	 */
	@RequestMapping(params = "method=savecode")
	public void savecode(HttpServletRequest request,HttpServletResponse response,Code command){

		Code code=(Code)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{

			this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");

			out=response.getWriter();
			String modal=(String)request.getParameter("modal");

			// �½�ʱ����Ƿ���ڸô���
			if(modal.equals("new")){
				Code tmpCode=this.gSysparaCodeService.getCode(code.getXtlb(),code.getDmlb(),code.getDmz());
				if(tmpCode!=null){
					out.println("<script language=javascript>parent.resultSave('2','[00R9532C1]:�Ѵ��ڴ���Ϊ"+code.getDmz()+"�Ĵ��룡');</script>");
					return;
				}
			}
			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="����ά��:"+code.getXtlb()+"-"+code.getDmlb()+"-"+code.getDmz();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_DMWH,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.codeManager.saveCode(code,log,modal);

			if(result.getFlag()==1){
				this.codeManager.freshCodebyDmlb(code);
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','[00R9530C2]:����ά���ɹ�����');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','[00R9532C3]:"+result.getDesc1()+"');</script>");
			}

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.cScriptInfoStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}

	/**
	 * ����ά����������ɾ������
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=removecode")
	public void removecode(HttpServletRequest request,HttpServletResponse response,Code command){
		Code code=(Code)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{

			this.sysService.doBeginCall(request,"00",Constants.MENU_DMCX,"","");

			out=response.getWriter();
			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="����ά��:"+code.getXtlb()+"-"+code.getDmlb()+"-"+code.getDmz();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_DMWH,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.codeManager.removeCode(code,log);
			if(result.getFlag()==1){
				this.codeManager.freshCodebyDmlb(code);
				out.println("<script language=javascript>parent.resultDel('"+result.getFlag()+"','[00R9530C5]:�ô���ɾ���ɹ���');</script>");
			}else{
				out.println("<script language=javascript>parent.resultDel('"+result.getFlag()+"','[00R9532C6]:"+result.getDesc1()+"��');</script>");
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.cScriptInfoStr(errmsg);
			out.println("<script language=javascript>parent.resultDel('0','"+s+"');</script>");
		}
	}

	/**
	 * ��ת����������ά��ҳ��
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=findForwardXzqh")
	public ModelAndView findForwardXzqh(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_XZQHWH,"","");
			Department department=userSession.getDepartment();
			if(!(department.getBmjb().equals("3")||department.getBmjb().equals("2"))){
				request.setAttribute("message","ֻ���ܶӻ�֧�Ӽ������ά����������");
				return redirectMav("authErr");
			}
			return redirectMav("jsp/xzqhList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * ��ȡ���������б�
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @SuppressWarnings("unchecked")
	 */
	@RequestMapping(params = "method=queryXzqhList")
	public ModelAndView queryXzqhList(HttpServletRequest request,HttpServletResponse response,Code command){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		Code code=(Code)command;
		PageController controller=new PageController(request);
		controller.setPageSize(13);
		List queryList;
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_XZQHWH,"","");
			Department department=userSession.getDepartment();
			String xzqh="";
			if(department.getBmjb().equals("2"))
				xzqh=department.getGlbm().substring(0,2);
			else if(department.getBmjb().equals("3"))
				xzqh=department.getGlbm().substring(0,4);
			else xzqh=department.getGlbm().substring(0,6);
			queryList=this.codeManager.getXzqhList(code.getDmz(),code.getDmsm1(),xzqh,controller);
			request.setAttribute("queryList",queryList); // ���ز�ѯlist
			request.setAttribute("code",code); // ���ز�ѯ����
			request.setAttribute("controller",controller);
			return redirectMav("jsp/xzqhList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	// ӳ�䵽ѡ����������
	@RequestMapping(params = "method=choosexzqh")
	public ModelAndView choosexzqh(HttpServletRequest request,HttpServletResponse response){
		String xzqh=(String)request.getParameter("xzqh");
		String cxfw=(String)request.getParameter("cxfw");
		String lb=(String)request.getParameter("lb");
		request.setAttribute("xzqh",xzqh);
		request.setAttribute("cxfw",cxfw);
		request.setAttribute("lb",lb);

		return redirectMav("jsp/choosexzqh");
	}

	// ��ȡ���������������б�
	// yangzm
	@RequestMapping(params = "method=queryXzqhTreeList")
	public void queryXzqhTreeList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// ���ý�����Ϣ���ַ���
		// HttpSession session=request.getSession();
		String xzqh=request.getParameter("xzqh");
		String cxfw=request.getParameter("cxfw");
		String lb=request.getParameter("lb");
		String checkbox=request.getParameter("check");

		// ���������Ϣ�ĸ�ʽ���ַ���
		response.setContentType("application/xml; charset=GB2312");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType(contentType);

		PrintWriter out=response.getWriter();

		//gSysparaCodeService.getCodeValue("00","0033"
		//0033XXX
		List objlist=null;
		if(lb.compareTo("2")==0||lb.compareTo("3")==0){
			objlist=gSysparaCodeService.getCodes("32","0006");
		}else{
			objlist=gBasService.getXzqhCodeList();
		}

		// ��ȡ���в˵�
		String result="";
		if(objlist!=null){
			String strDmz="",strDmsm1="";
			if(objlist.size()>0){
				boolean isfirst=true;
				String province=xzqh.substring(0,2);
				for(int i=0;i<objlist.size();i++){
					Code obj=(Code)objlist.get(i);
					strDmz=obj.getDmz();
					strDmsm1=obj.getDmsm1();
					String subProvince=strDmz.substring(0,2);
					if(strDmz.length()!=6) continue;
					if(lb.compareTo("2")==0&&subProvince.compareTo(province)!=0) continue;
					if(lb.compareTo("3")==0&&subProvince.compareTo(province)==0) continue;
					if(!strDmz.substring(4,6).equals("00")) continue;
					// forѭ��ȡͷ�ڵ�
					if(strDmz.substring(2,4).equals("00")&&strDmz.substring(4,6).equals("00")){
						if(!isfirst) result=result+XmlUtil.getClose();
						isfirst=false;
						if(checkbox!=null&&checkbox.equals("true"))
							result=result+XmlUtil.getCheckboxFoldXml(strDmz,strDmsm1,cxfw);
						else result=result+XmlUtil.getCheckboxFoldXml(strDmz,strDmsm1,cxfw);
					}else if(!strDmz.substring(2,4).equals("00")&&strDmz.substring(4,6).equals("00")){
						if(checkbox!=null&&checkbox.equals("true"))
							result=result+XmlUtil.getCheckboxMenuXml(strDmz,strDmsm1,cxfw);
						else result=result+XmlUtil.getCheckboxMenuXml(strDmz,strDmsm1,cxfw);
					}
					if(!isfirst) result=result+XmlUtil.getClose();
				}
			}
		}
		String strret="<?xml version='1.0' encoding='GBK'?>";
		strret=strret+"<tree id='0'>";
		if(result.equals("")){
			result=XmlUtil.getMenuXml("000000","δ�ҵ����ص���","");
		}
		strret=strret+result+"</tree>";

		out.println(strret);
		out.close();
	}
	@RequestMapping(params = "method=editXzqh")
	public ModelAndView editXzqh(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		String dmz=(String)request.getParameter("dmz");
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_XZQHWH,"","");
            // getFrmLocalxzqhCode-0050
            Code code = this.gBasService.getLocalxzqhCode(dmz);
			request.setAttribute("code",code);
			request.setAttribute("modal","edit");
			return redirectMav("jsp/xzqhEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * �½���������
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=newxzqh")
	public ModelAndView newxzqh(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_XZQHWH,"","");
			Department department=userSession.getDepartment();
			String xzqh="";
			String dmcd="0";
			if(department.getBmjb().equals("2")){
				xzqh=department.getGlbm().substring(0,2);
				dmcd="4";
			}else if(department.getBmjb().equals("3")){
				xzqh=department.getGlbm().substring(0,4);
				dmcd="2";
			}else xzqh=department.getGlbm().substring(0,6);

			request.setAttribute("modal","new");
			request.setAttribute("xzqh",xzqh);
			request.setAttribute("dmcd",dmcd);
			return redirectMav("jsp/xzqhEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}
	@RequestMapping(params = "method=savexzqh")
	public void savexzqh(HttpServletRequest request,HttpServletResponse response,Code command){

		Code code=(Code)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_XZQHWH,"","");
			out=response.getWriter();
			String modal=(String)request.getParameter("modal");

			// �½�ʱ����Ƿ���ڸô���
			if(modal.equals("new")){
				Code tmpCode=this.gSysparaCodeService.getCode(code.getXtlb(),code.getDmlb(),code.getDmz());
				if(tmpCode!=null){
					out.println("<script language=javascript>parent.resultSave('2','[00R9532C1]:�Ѵ�����������Ϊ"+code.getDmz()+"�Ĵ��룡');</script>");
					return;
				}
			}
			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="��������ά��:"+code.getXtlb()+"-"+code.getDmlb()+"-"+code.getDmz();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_XZQHWH,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.codeManager.saveCode(code,log,modal);

			if(result.getFlag()==1){
				this.codeManager.freshCodebyDmlb(code);
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','[00R9530C2]:��������ά���ɹ�����');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','[00R9532C3]:"+result.getDesc1()+"');</script>");
			}

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			if(errmsg.indexOf("ORA-01031")>0) errmsg="�޴�������Ȩ�ޣ�����ϵͳ����Ա��ϵ";
			e.printStackTrace();
			String s=StringUtil.cScriptInfoStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	@RequestMapping(params = "method=removexzqh")
	public void removexzqh(HttpServletRequest request,HttpServletResponse response,Code command){
		Code code=(Code)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{

			this.sysService.doBeginCall(request,"00",Constants.MENU_XZQHWH,"","");

			out=response.getWriter();
			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="��������ɾ��:"+code.getXtlb()+"-"+code.getDmlb()+"-"+code.getDmz();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_XZQHWH,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.codeManager.removeCode(code,log);
			if(result.getFlag()==1){
				this.codeManager.freshCodebyDmlb(code);
				out.println("<script language=javascript>parent.resultDel('"+result.getFlag()+"','[00R9530C5]:��������ɾ���ɹ���');</script>");
			}else{
				out.println("<script language=javascript>parent.resultDel('"+result.getFlag()+"','[00R9532C6]:"+result.getDesc1()+"��');</script>");
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.cScriptInfoStr(errmsg);
			out.println("<script language=javascript>parent.resultDel('0','"+s+"');</script>");
		}
	}

	/**
	 * �񾯹��������������á��������б����
	 */
	@RequestMapping(params = "method=informsetup")
	public ModelAndView informsetup(HttpServletRequest request,HttpServletResponse response,FrmInformsetup command){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_INFORMSETUP,"","");
			PageController controller=new PageController(request);
			//����Ԥ����
			if(command.getXxdm()==null){
				command.setXxdm("");
			}
			if(command.getGlbm()==null||command.getGlbm().equals("")){
				//if(userSession.getDepartment().getYwlb().substring(0,10).equals("1111111111")){
					command.setGlbm(userSession.getDepartment().getGlbm());
				//}else {
				//	command.setGlbm(userSession.getDepartment().getSjbm());
				//}
				command.setBaxjbm("1");				
			}
			command.setBmjb(((Department)this.gDepartmentService.getDepartment(command.getGlbm())).getBmjb());
			
			//��ȡ�񾯹��������������ô����б�
			String DJGLBM = this.gSysparaCodeService.getSysParaValue("00","DJGLBM");
			String DJ_BMJB = ((Department)this.gDepartmentService.getDepartment(DJGLBM)).getBmjb();
			String DQYH_BMJB = userSession.getDepartment().getBmjb(); 
			List inform_codes = this.codeManager.getInformCodes(DQYH_BMJB,DJ_BMJB);
			String inform_optionString = this.gHtmlService.transListToOptionHtml(inform_codes,command.getXxdm(),true,"3");
			
			//��ȡ�񾯹��������������������б�
			List inform_setups = this.codeManager.getInformSetups(command,controller);
			FrmTranslation.getInstance(sysService).transFrminformSetupList(inform_setups,"1");
			
			Code code= null;
			if(!command.getXxdm().equals("")){
				code = this.codeManager.getInformCode(command.getXxdm());
				Department department_dj = this.gDepartmentService.getDepartment(this.gSysparaCodeService.getSysParaValue(Constants.SYS_XTLB_FRAME,"DJGLBM"));
				if(code.getDmsm4().equals("A")){
					code.setDmsm4(this.gSysparaCodeService.transCode(Constants.SYS_XTLB_FRAME,"0065",department_dj.getBmjb()));
				}else{
					code.setDmsm4(this.gSysparaCodeService.transMultiCodeBySplit(Constants.SYS_XTLB_FRAME,"0065",code.getDmsm4(),","));
				}
			}
			request.setAttribute("cdmc",this.gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,Constants.MENU_INFORMSETUP));
			request.setAttribute("inform_setups",inform_setups);
			request.setAttribute("inform_optionString",inform_optionString);
			request.setAttribute("command",command);
			request.setAttribute("code",code);
			request.setAttribute("controller",controller);
			request.setAttribute("sysuser",userSession.getSysuser());
			return redirectMav("jsp/informsetup");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}
	
	/**
	 * �񾯹��������������á��������༭��������
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=informsetup_edit")
	public ModelAndView informsetup_edit(HttpServletRequest request,HttpServletResponse response,FrmInformsetup command){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_INFORMSETUP,"","");			
			//��ȡ�񾯹��������������������б�
			if(command.getGlbm()!=null&&!command.getGlbm().equals(""))
			{
				command.setGlbm(command.getGlbm().substring(0, 12));

			}
			FrmInformsetup frmInformsetup = this.codeManager.getInformSetup(command);
			Code code = this.codeManager.getInformCode(command.getXxdm());
			Department department = this.gDepartmentService.getDepartment(command.getGlbm());
			if(department!=null)
			{
				command.setBmmc(department.getBmmc());
			}
			Department department_dj = this.gDepartmentService.getDepartment(this.gSysparaCodeService.getSysParaValue(Constants.SYS_XTLB_FRAME,"DJGLBM"));
			if(code.getDmsm4().equals("A")){
				if(!department.getBmjb().equals(department_dj.getBmjb())){
					throw new Exception("TERR:��ǰ����������Ϣ�衾" + this.gSysparaCodeService.transCode(Constants.SYS_XTLB_FRAME,"0065",department_dj.getBmjb()) +"�������û�����");
				}
				code.setDmsm4(this.gSysparaCodeService.transCode(Constants.SYS_XTLB_FRAME,"0065",department_dj.getBmjb()));
			}else{
				if(code.getDmsm4().indexOf(department.getBmjb())<0){
					throw new Exception("TERR:��ǰ����������Ϣ�衾" + this.gSysparaCodeService.transMultiCodeBySplit(Constants.SYS_XTLB_FRAME,"0065",code.getDmsm4(),",") +"�������û�����");
				}
				code.setDmsm4(this.gSysparaCodeService.transMultiCodeBySplit(Constants.SYS_XTLB_FRAME,"0065",code.getDmsm4(),","));
			}
			
			if(frmInformsetup!=null){
				command = frmInformsetup;			
			}else{
				command.setTxzq(code.getDmsm2());
			}
			FrmTranslation.getInstance(sysService).transFrminformSetup(command,"1");
			List sysusers = this.codeManager.getInformSetupUsers(command);

			request.setAttribute("cdmc",gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,Constants.MENU_INFORMSETUP));
			request.setAttribute("sysusers",sysusers);
			request.setAttribute("command",command);
			request.setAttribute("code",code);
			request.setAttribute("xc_option",this.gHtmlService.transDmlbToOptionHtml(Constants.SYS_XTLB_FRAME,"9064","",false,"2",""));
			return redirectMav("jsp/informsetup_edit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			String errCode=sysService.getOraErrCode(errmsg);
			if(!(errCode.equals("20001")||errCode.equals("TERR"))){
				e.printStackTrace();
			}
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}
	/**
	 * �񾯹��������������á����������洦��
	 */
	@RequestMapping(params = "method=informsetup_save")
	public void informsetup_save(HttpServletRequest request,HttpServletResponse response,FrmInformsetup command){
		FrmInformsetup frmInformsetup=(FrmInformsetup)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{

			this.sysService.doBeginCall(request,"00",Constants.MENU_INFORMSETUP,"","");

			out=response.getWriter();
			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr= "�޸��񾯹���������������,��Ϣ���룺" + frmInformsetup.getXxdm() + ",�����ţ�" + frmInformsetup.getGlbm() + "������:" + frmInformsetup.getYhdh() + "״̬:" + frmInformsetup.getZt();
			if(cznr.length()>3900)	cznr = cznr.substring(0,3900);
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_INFORMSETUP,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			
			
			SysResult result=this.codeManager.savefrmInformsetup(frmInformsetup,log);
			if(result.getFlag()==1){
				if(frmInformsetup.getZt().equals("1")){
					out.println("<script language=javascript>parent.resultSubmit('"+result.getFlag()+"','[0091102C1]:����ɹ���');</script>");	
				}else{
					out.println("<script language=javascript>parent.resultSubmit('"+result.getFlag()+"','[0091102C2]:ɾ���ɹ���');</script>");
				}
			}else{
				out.println("<script language=javascript>parent.resultSubmit('"+result.getFlag()+"','[0091102C3]:"+result.getDesc1()+"��');</script>");
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.cScriptInfoStr(errmsg);
			out.println("<script language=javascript>parent.resultSubmit('0','"+s+"');</script>");
		}
	}
	/**
	 * ������Ϣ�����û����ñ༭���桪����������
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=smssetupsave")
	public void smssetupsave(HttpServletRequest request,HttpServletResponse response,SmsSetup command){
		SmsSetup smsSetup=(SmsSetup)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{

			this.sysService.doBeginCall(request,"00",Constants.MENU_SMSSETUP,"","");

			out=response.getWriter();
			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr= "�޸�ϵͳ��Ϣ����,��Ϣ���룺" + smsSetup.getXxdm() + ",������:" + smsSetup.getYhdh();
			if(cznr.length()>3900)	cznr = cznr.substring(0,3900);
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_SMSSETUP,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.codeManager.savesmssetup(smsSetup,log);
			if(result.getFlag()==1){
				out.println("<script language=javascript>parent.resultSubmit('"+result.getFlag()+"','[00R9772C1]:ϵͳ��Ϣ" + smsSetup.getXxdm() + "�������óɹ���');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSubmit('"+result.getFlag()+"','[00R9772C2]:"+result.getDesc1()+"��');</script>");
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.cScriptInfoStr(errmsg);
			out.println("<script language=javascript>parent.resultSubmit('0','"+s+"');</script>");
		}
	}	
	/**
	 * ϵͳ��Ϣ���á��������û���ѯ
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=queryyhxm")
	public void queryyhxm(HttpServletRequest request,HttpServletResponse response){		
		PrintWriter out=null;
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		response.setContentType(contentType);
		try{
			out=response.getWriter();
			response.setContentType("application/xml");
			out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			
			this.sysService.doBeginCall(request,"00",Constants.MENU_SMSSETUP,"","");
			String xtgly = request.getParameter("xtgly");
			String kgywyhlx = request.getParameter("kgywyhlx");
			String xm = request.getParameter("xm");


			String roaditemxmlString=this.codeManager.getUsersToXml(xtgly,kgywyhlx,xm);
			out.println(roaditemxmlString);
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
		}

	}
	/**
	 * �û���Ϣ���������б����
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=queryUserSmsList")
	public ModelAndView queryUserSmsList(HttpServletRequest request,HttpServletResponse response,SmsContent command){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			String yhdh = userSession.getYhdh();
		  command.setJsyhdh(yhdh);
			PageController controller=new PageController(request);
			List smsList = this.codeManager.getUserSmsList(command,controller);
			request.setAttribute("smslist",smsList);
			request.setAttribute("smscontent",command);
			request.setAttribute("sysService",this.sysService);
			return redirectMav("jsp/smslist");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}
	@RequestMapping(params = "method=displayUserSms")
	public ModelAndView displayUserSms(HttpServletRequest request,HttpServletResponse response,SmsContent command){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{			
			SmsContent smsContent = this.codeManager.saveReadOneUserSms(command);
			request.setAttribute("sysService",this.sysService);
			request.setAttribute("smsContent",smsContent);
			return redirectMav("jsp/smscontent");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}
}
