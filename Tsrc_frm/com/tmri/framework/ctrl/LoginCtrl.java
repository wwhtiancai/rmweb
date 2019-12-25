package com.tmri.framework.ctrl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.PassWord;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.service.DepartmentManager;
import com.tmri.framework.service.DesktopManager;
import com.tmri.framework.service.ProgramFoldManager;
import com.tmri.framework.service.SysuserManager;
import com.tmri.framework.util.CheckCode;
import com.tmri.pub.util.WrapUtil;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.ConstantsDebug;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Controller
@RequestMapping("/login.frm")
public class LoginCtrl extends FrmCtrl{

	private final static Logger LOG = LoggerFactory.getLogger(LoginCtrl.class);

	@Autowired
	private SysuserManager sysuserManager;
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private ProgramFoldManager programFoldManager;
	@Autowired
	private DesktopManager desktopManager;

	private String debug="1";
	
	
	//ӳ�䵽��ҳ��
	@RequestMapping(params = "method=login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,SysUser command) throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("login");
		return redirectMav(mav);
	}
	
	/**
	 * �û���֤
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=check")
	public ModelAndView check(HttpServletRequest request,HttpServletResponse response,SysUser command) throws Exception{
		String strRemoteAddr=FuncUtil.getRemoteIpdz(request); // ��ȡip��ַ
		ModelAndView mav=new ModelAndView();
		Hashtable rightsobj=null; // ��½�û��ĳ���˵�
		String jspnameString=request.getParameter("jspname");
		String PKISYMS = this.gSysparaCodeService.getSysParaValue("00","PKISYMS");
		try{
			if(Constants.SYS_WGFWFWQ_BJ.equals("1")){
				throw new Exception("��ǰ������ֻ�������ϵͳʹ�ã�������������ҵ��");
			}

			if(!Constants.SYS_OTHER_STATE.equals("1")){
				//throw new Exception(Constants.SYS_OTHER_STATE);
			}			
			//1-��ͨ��¼ 2-PKI 3-���֤��¼ 4-ָ�Ƶ�¼ 5-PKI��CA����֤
			
			//�ų����ں���Χ�ڵĵ�¼ģʽ
			if("1#2#3#4#".indexOf(command.getDlms() + "#")<0){
				throw new Exception("�û���¼ģʽ�Ƿ���");
			}
			if(PKISYMS.equals("2")&&!request.isSecure()){
				throw new Exception("����ʹ������֤���¼��");
			}
			// �û���PKI��ʽ��¼
			if(request.isSecure()){
				String certAttribute="javax.servlet.request.X509Certificate";
				X509Certificate[] certificate=(X509Certificate[])request.getAttribute(certAttribute);
				String s1=certificate[0].getSubjectDN().getName();
				s1=s1.substring(3);
				int iPos=s1.indexOf(" ");
				command.setSfzmhm(s1.substring(0,iPos));
				s1=s1.substring(iPos+1);
				iPos=s1.indexOf(",");
				s1=s1.substring(0,iPos);
				command.setSfzmhm(s1);
				command.setYhdh("");
				command.setMm("");
			}
			if(command.getDlms().equals("4")){
			}
			HttpSession session=request.getSession();

			session.setAttribute("themestyle","1");
			UserSession userSession=new UserSession();
			//20111118 jianghailong ��¼��¼ģʽ�����ݸõ�¼ģʽ�ж��˵�ʹ�����
			//����usersession��¼ģʽ
			userSession.setDlms(command.getDlms());
			SysUser sysuser=new SysUser();
			Department department=new Department();
			if(command.getDlms().equals("1")){
				if(request.getParameter("yhdh")==null||request.getParameter("yhdh").length()<1
						||request.getParameter("yhdh").indexOf("'")!=-1){
					throw new Exception("�������û�����");
				}
				if(request.getParameter("mm")==null||request.getParameter("mm").length()<1
						||request.getParameter("mm").indexOf("'")!=-1){
					throw new Exception("���������룡");
				}
				
				if(command.getDlms().equals("1")||command.getDlms().equals("3")){
					String codeString=(String)session.getAttribute("rand_code");
					if(codeString==null){
						throw new Exception("��������֤�룡");
					}
					codeString=WrapUtil.Decrypt(codeString);
					if(!codeString.toUpperCase().equals(command.getYzm().toUpperCase())){
						throw new Exception("��֤�벻��ȷ��");
					}
					session.setAttribute("rand_code","");
				}else{
					session.setAttribute("rand_code","");
				}
				
				int p=this.sysuserManager.getTrafficPolice(request.getParameter("yhdh"));
				if(p<=0){
					throw new Exception("�û�������");
				}
			}else if(command.getDlms().equals("2")){
				int p=this.sysuserManager.getTrafficPoliceSfzmhm(command.getSfzmhm());
				if(p<=0){
					throw new Exception("�û�������");
				}
			}	

			sysuserManager.validateSysuser(command,userSession,strRemoteAddr);
			sysuser=this.sysuserManager.getSysuser(command.getYhdh());
			if(sysuser==null){
				throw new Exception(Constants.SYS_NO_RIGHT);
			}
			sysuser.setIp(strRemoteAddr);// ���õ�¼��Ա��ip��ַ
			sysuser.setDlms(command.getDlms());
			department=this.departmentManager.getDepartment(sysuser.getGlbm());
			if(department==null) throw new Exception(Constants.SYS_NO_HIGHXZQH);
			department.setJzlx("17");
			String fzjgString=gSysparaCodeService.getSysParaValue("00","BDFZJG");
			if(fzjgString.indexOf(department.getFzjg())<0){
				throw new Exception(Constants.SYS_BMFZJG_ERR);
			}
			
			String XTYXMS = this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
			HashMap paras = new HashMap();
			paras.put("XTYXMS",XTYXMS);
			//2012-4-25 DEBUGģʽ���в˵�
			paras.put("DEBUG",debug);
			HashMap map=this.programFoldManager.getFoldMenuStr(sysuser,paras);
			List ProgramList=(List)map.get("prolist");
			List desktopList=(List)map.get("desktoplist");
			Program program=null;
			program=new Program();
			program.setCdbh("0000");
			program.setCxmc("�ҵ�����");
			desktopList.add(program);
			for(int i=0;i<ProgramList.size();i++){
				if(rightsobj==null){
					rightsobj=new Hashtable();
				}
				program=(Program)ProgramList.get(i);
				rightsobj.put(program.getXtlb()+"-"+program.getCdbh(),ProgramList.get(i));
			}
			// ��ȡ�û��Զ�������˵�����Ϣ
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
			// ��ȡ�㲥��Ϣ
			userSession.setSysuser(sysuser);
			userSession.setDepartment(department);
			userSession.setFwzbh(this.sysuserManager.getFwzbh(command.getYhdh()));
			Desktop mydesktop=this.desktopManager.getMyDesktop(command.getYhdh(),"7");
			String themestyle="1";
			if(mydesktop!=null) themestyle=mydesktop.getMksx1();
			if(themestyle==null||themestyle.length()<1) themestyle="1";
			session.setAttribute("themestyle",themestyle);
			Cookie cookie=new Cookie("themestyles",themestyle);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
			session.removeAttribute("userSession");
			session.setAttribute("userSession",userSession);
			session.setAttribute("acdsession",null);
			session.setAttribute("menuhtml",map.get("menu"));
			session.setAttribute("foldhtml",map.get("fold"));
			session.setAttribute("counthtml",map.get("count"));
			session.setAttribute("totalhtml",map.get("total"));
			session.setAttribute("foldlist1",map.get("foldlist1"));
			session.setAttribute("topfoldhtml",map.get("topfold"));
			session.setAttribute("topfoldcount",map.get("topfoldcount"));
			session.setAttribute("rightsobj",rightsobj);
			session.setAttribute("userdesklist",userdeskList);
			session.setAttribute("userdeskFold",foldList);
			session.setAttribute("desktoplist",desktopList);
			session.setAttribute("rand_code_state","");
			// ���õ�ǰ��¼�û���sessionid
			userSession.setSessionid(WrapUtil.getRandomNormalString(16));
			if(!request.isSecure()&&command.getMm().equals("888888")){
				request.setAttribute("message","��ǰ�û��״ε�¼ϵͳ�����޸ĳ�ʼ���룡");
				request.setAttribute("sysuser",sysService.getSysUser(userSession.getSysuser().getYhdh()));
				return redirectMav("jsp/passwordEdit");
			}else{
				mav.setViewName("frmmain");
			}

		}catch(Exception ex){
			LOG.error("��¼�쳣", ex);
			if(ex==null||ex.getMessage()==null){
				mav.setViewName("../../../"+jspnameString);
				mav.addObject("message","java.lang.NullPointerException");
			}else if(ex.getMessage().equals(Constants.SYS_CHANGE_PASSWORD)||ex.getMessage().equals(Constants.SYS_PASSWORD_INVALID)||ex.getMessage().equals(Constants.SYS_FIRST_LOGIN)||ex.getMessage().equals(Constants.SYS_GLY_PASS)||ex.getMessage().equals(Constants.SYS_PTYH_PASS)){
				request.setAttribute("sysuser",sysService.getSysUser(command.getYhdh()));
				mav.setViewName("../../../WEB-INF/jsp_core/framework/jsp/passwordEdit");
				mav.addObject("message",ex.getMessage());
			}else if(ex.getMessage().equals(Constants.SYS_PTYH_GDDZTS)){
				request.setAttribute("sysuser",sysService.getSysUser(command.getYhdh()));
				mav.setViewName("../../../WEB-INF/jsp_core/framework/jsp/gdipdzEdit");
				String reqipString = FuncUtil.getRemoteIpdz(request);
				mav.addObject("reqip",reqipString);
				mav.addObject("message",ex.getMessage());
			}else{
				mav.setViewName("../../../"+jspnameString);
				mav.addObject("message",ex.getMessage());
			}
		}
		return redirectMav(mav);
	}
	/**
	 * CA�û���֤
	 * 20111118 jianghailong ����CA��¼ģʽ�����ڰ�ȫ���ص�ģʽ
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=keycheck")
	public ModelAndView keycheck(HttpServletRequest request,HttpServletResponse response,SysUser command) throws Exception{
		String strRemoteAddr=request.getHeader("clientip"); //��ȡip��ַ
		String dnnameString = request.getHeader("dnname");
		ModelAndView mav=new ModelAndView();
		Hashtable rightsobj=null; // ��½�û��ĳ���˵�
		String jspnameString="index_pki";
		command.setDlms("2");
		try{
			if(Constants.SYS_WGFWFWQ_BJ.equals("1")){
				throw new Exception("��ǰ������ֻ�������ϵͳʹ�ã�������������ҵ��");
			}
			String bj="1";
			if(bj.equals("81")){
				throw new Exception("��װ��δ����");
			}else if(bj.equals("82")){
				throw new Exception("Ӧ�÷�����δ�Ǽ�");
			}else if(bj.equals("83")){
				throw new Exception("��Կ���Ϸ�");
			}else if(bj.equals("84")){
				throw new Exception("��Կ���ڣ�����ϵϵͳ����Ա");
			}else if(bj.equals("85")){
				throw new Exception("Ӧ�÷�������������ϵͳ����Ա����");
			}else if(bj.equals("92")){
				throw new Exception("Ӧ��ϵͳ�汾�Ѿ����ڣ�����ʹ��");
			}else if(bj.equals("93")){
				throw new Exception("�������ݿ��쳣������ϵϵͳ����Ա");
			}else if(bj.equals("94")){
				throw new Exception("Ӧ��ϵͳ�����쳣����������Դ���ӣ���������Ӧ�ó���");
			}else if(bj.equals("95")){
				throw new Exception("Ӧ��ϵͳ����������ϵ��������ͨ��ѧ������");
			}else if(bj.equals("96")){
				throw new Exception("Ӧ��ϵͳ��ȫģ��δ���أ���������Դ���ӣ���������Ӧ�ó���");
			}
			if(!Constants.SYS_OTHER_STATE.equals("1")){
				throw new Exception(Constants.SYS_OTHER_STATE);
			}
			// �û���PKI��ʽ��¼			
			String s1=dnnameString;
			s1=s1.substring(3);
			int iPos=s1.indexOf(" ");
			command.setSfzmhm(s1.substring(0,iPos));
			s1=s1.substring(iPos+1);
			iPos=s1.indexOf(",");
			s1=s1.substring(0,iPos);
			command.setSfzmhm(s1);
			command.setYhdh("");
			command.setMm("");
			HttpSession session=request.getSession();
			UserSession userSession=new UserSession();
			userSession.setDlms(command.getDlms());
			sysuserManager.validateSysuser(command,userSession,strRemoteAddr);
			SysUser sysuser=this.sysuserManager.getSysuser(command.getYhdh());
			if(sysuser==null){
				throw new Exception(Constants.SYS_NO_RIGHT);
			}
			sysuser.setIp(strRemoteAddr);// ���õ�¼��Ա��ip��ַ
			sysuser.setDlms(command.getDlms());
			Department department=this.departmentManager.getDepartment(sysuser.getGlbm());
			if(department==null) throw new Exception(Constants.SYS_NO_HIGHXZQH);
			String fzjgString=gSysparaCodeService.getSysParaValue("00","BDFZJG");
			if(fzjgString.indexOf(department.getFzjg())<0){
				throw new Exception(Constants.SYS_BMFZJG_ERR);
			}
			//2012-02-24 ����ϵͳ����ģʽ��Ϊ��ѯ�˵�����
			String XTYXMS = this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
			HashMap paras = new HashMap();
			paras.put("XTYXMS",XTYXMS);
			HashMap map=this.programFoldManager.getFoldMenuStr(sysuser,paras);
			List ProgramList=(List)map.get("prolist");
			List desktopList=(List)map.get("desktoplist");
			Program program=null;
			program=new Program();
			program.setCdbh("0000");
			program.setCxmc("�ҵ�����");
			desktopList.add(program);
			for(int i=0;i<ProgramList.size();i++){
				if(rightsobj==null){
					rightsobj=new Hashtable();
				}
				program=(Program)ProgramList.get(i);
				rightsobj.put(program.getXtlb()+"-"+program.getCdbh(),ProgramList.get(i));
			}
			// ��ȡ�û��Զ�������˵�����Ϣ
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
			
			// ��ȡ�㲥��Ϣ
			userSession.setSysuser(sysuser);
			userSession.setDepartment(department);
			userSession.setFwzbh(this.sysuserManager.getFwzbh(command.getYhdh()));
			Desktop mydesktop=this.desktopManager.getMyDesktop(command.getYhdh(),"7");
			String themestyle="1";
			if(mydesktop!=null) themestyle=mydesktop.getMksx1();
			if(themestyle==null||themestyle.length()<1) themestyle="1";
			session.setAttribute("themestyle",themestyle);
			Cookie cookie=new Cookie("themestyles",themestyle);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
			session.removeAttribute("userSession");
			session.setAttribute("userSession",userSession);
			session.setAttribute("acdsession",null);
			session.setAttribute("menuhtml",map.get("menu"));
			session.setAttribute("foldhtml",map.get("fold"));
			session.setAttribute("counthtml",map.get("count"));
			session.setAttribute("totalhtml",map.get("total"));
			session.setAttribute("foldlist1",map.get("foldlist1"));
			session.setAttribute("topfoldhtml",map.get("topfold"));
			session.setAttribute("topfoldcount",map.get("topfoldcount"));
			session.setAttribute("rightsobj",rightsobj);
			session.setAttribute("userdesklist",userdeskList);
			session.setAttribute("userdeskFold",foldList);
			session.setAttribute("desktoplist",desktopList);
			session.setAttribute("rand_code_state","");
			// ���õ�ǰ��¼�û���sessionid
			userSession.setSessionid(WrapUtil.getRandomNormalString(16));
			mav.setViewName("frmmain");
			session.setAttribute("stralert","");
		}catch(Exception ex){
			ex.printStackTrace();
			if(ex.getMessage().equals(Constants.SYS_PTYH_GDDZTS)){
				request.setAttribute("sysuser",sysService.getSysUser(command.getYhdh()));
				mav.setViewName("../../../WEB-INF/jsp_core/framework/jsp/gdipdzEdit");
				mav.addObject("reqip",strRemoteAddr);
				mav.addObject("message",ex.getMessage());
			}else{
				mav.setViewName("../../../"+jspnameString);
				mav.addObject("message","dnname" + dnnameString + "clientip" + strRemoteAddr +  ex.getMessage());
			}
		}
		return redirectMav(mav);
	}
	@RequestMapping(params = "method=logout_program")
	public ModelAndView logout_program(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		try{
			mav.setViewName("authErr");
			request.setAttribute("message","�ó���˵���¼���Ƿ��޸ģ�");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav.setViewName("authErr");
		}
		return redirectMav(mav);
	}
	@RequestMapping(params = "method=logout_pki")
	public ModelAndView logout_pki(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		try{
			mav.setViewName("authErr");
			request.setAttribute("message","�ù��ܱ���ʹ������֤���¼����ʣ�");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav.setViewName("authErr");
		}
		return redirectMav(mav);
	}
	@RequestMapping(params = "method=logout_ipdz")
	public ModelAndView logout_ipdz(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		try{
			mav.setViewName("authErr");
			request.setAttribute("message","�ù��ܱ���ʹ�õ�ǰ�û��̶�IP��ַ�ͻ��˷��ʣ�");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav.setViewName("authErr");
		}
		return redirectMav(mav);
	}	
	@RequestMapping(params = "method=logout_setgdip")
	public ModelAndView logout_setgdip(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			UserSession userSession=gSystemService.getSessionUserInfo(request.getSession());
			request.setAttribute("message","��ǰҵ�����ʹ�ù̶�IP��ַ���ʣ������ù̶�IP��ַ��");
			request.setAttribute("sysuser",sysService.getSysUser(userSession.getSysuser().getYhdh()));
			String reqipString = FuncUtil.getRemoteIpdz(request);
			request.setAttribute("reqip",reqipString);
			return redirectMav("jsp/gdipdzEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			return redirectMav("authErr");
		}
	}	
	@RequestMapping(params = "method=logout")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		try{
			mav.setViewName("authErr");
			request.setAttribute("message","���ʳ�ʱ����Ȩ�ޣ������µ�½��");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav.setViewName("authErr");
		}
		return redirectMav(mav);
	}
	@RequestMapping(params = "method=logout_wgfwfwq")
	public ModelAndView logout_wgfwfwq(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		try{
			mav.setViewName("authErr");
			request.setAttribute("message","��ǰ������ֻ�������ϵͳʹ�ã�������������ҵ��");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("message",ex.getMessage());
			mav.setViewName("authErr");
		}
		return redirectMav(mav);
	}
	/**
	 * �ж�Ȩ�޲�ʵ����ת
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=sendRedirect")
	public void sendRedirect(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			String cdbh=(String)request.getParameter("cdbh");
			String xtlb=(String)request.getParameter("xtlb");
			String PKISYMS = this.gSysparaCodeService.getSysParaValue("00","PKISYMS");
			Hashtable rightsobj=(Hashtable)request.getSession().getAttribute("rightsobj");
			HttpSession session=request.getSession();
			session.setAttribute("systems",null);
			UserSession userSession=gSystemService.getSessionUserInfo(session);
			if(rightsobj!=null){
				Program p=(Program)rightsobj.get(xtlb+"-"+cdbh);
				if(p==null){
					response.sendRedirect("login.frm?method=logout");
				}else{
					if(p.getFlag().equals("2")){
						response.sendRedirect("login.frm?method=logout_program");
						return;
					}
					if(p.getFwfs().indexOf("1")>=0){
						//20120221 jianghailong PKI��¼�ж�����Ϊ����dlms���� 
						if(debug.equals("0")&&!userSession.getSysuser().getDlms().equals("2")){
							String sysdateString = gSysDateService.getSysDate(0,1);
							if(sysdateString.compareTo("2012-06-01")>=0){
								response.sendRedirect("login.frm?method=logout_pki");
								return;
							}
						}
					}
					if(p.getFwfs().indexOf("2")>=0){
						//20120223 jianghailong ָ��IP��ַ���ʹؼ�ҵ����
						if(userSession.getSysuser().getGdip().equals("") && Constants.JJ.equals(userSession.getDepartment().getJzlx())){
							response.sendRedirect("login.frm?method=logout_setgdip");
							return;
						}
						if(debug.equals("0")&&
								((!userSession.getSysuser().getGdip().equals(userSession.getDlip())))
								&&(!userSession.getSysuser().getGdip1().equals(userSession.getDlip())
								&&(!userSession.getSysuser().getGdip2().equals(userSession.getDlip())))){
							//ȡ�����޸� 20120422 jianghailong ϵͳ����Ա���ܴ����� 
//							if(!((userSession.getSysuser().getXtgly()!=null)&&userSession.getSysuser().getXtgly().equals("1"))){
								response.sendRedirect("login.frm?method=logout_ipdz");
								return;
//							}
						}
					}
					response.sendRedirect(p.getYmdz()+"&cdbh="+cdbh+"&xtlb="+xtlb);					
				}
			}else{
				response.sendRedirect("login.frm?method=logout");
			}
		}catch(Exception ex){
			String errmsg=StringUtil.getExpMsg(ex);
			ex.printStackTrace();
			request.setAttribute("message",errmsg);
			response.sendRedirect("login.frm?method=logout");
		}
	}

	/**
	 * ע��
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=userquit")
	public void userquit(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		try{
			out=response.getWriter();
			HttpSession session=request.getSession();
			session.removeAttribute("userSession");
			session.removeAttribute("menuhtml");
			session.removeAttribute("foldhtml");
			session.removeAttribute("counthtml");
			session.removeAttribute("totalhtml");
			session.removeAttribute("rightsobj");
			session.removeAttribute("allrightobj");
			session.removeAttribute("userdesklist");
			session.removeAttribute("userdeskFold");
			// out.println("<script language=javascript>parent.reload();</script>");
			out.println("<script language=javascript>parent.reload();</script>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * �������롪������>����
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=savepassword")
	public void savepassword(HttpServletRequest request,HttpServletResponse response,PassWord command){
		PassWord passWord=(PassWord)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		try{
			out=response.getWriter();
			SysUser sysuser=this.sysService.getSysUser(command.getYhdh());
			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="�޸�����";
			Log log=new Log(sysuser.getGlbm(),sysuser.getYhdh(),sysdatetime,Constants.MENU_YHGL,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.sysuserManager.savapassword(passWord,log);
			if(result.getFlag()==1){
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','�����޸ĳɹ���');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc()+"');</script>");
			}
		}catch(Exception e){
			String s=StringUtil.cScriptInfoStr(e.getMessage());
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	@RequestMapping(params = "method=savePlspassword")
	public void savePlspassword(HttpServletRequest request,HttpServletResponse response,PassWord command){
		PassWord passWord=(PassWord)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		try{
			out=response.getWriter();
			//SysUser sysuser = this.sysService.getSysUser(command.getYhdh());
			SysUser sysuser = this.sysuserManager.getPoliceman(command.getYhdh());
			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="�޸�����";
			Log log=new Log(sysuser.getGlbm(),sysuser.getYhdh(),sysdatetime,Constants.MENU_YHGL,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.sysuserManager.savaPlspassword(passWord,log);
			if(result.getFlag()==1){
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','�����޸ĳɹ���');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc()+"');</script>");
			}
		}catch(Exception e){
			String s=StringUtil.cScriptInfoStr(e.getMessage());
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	/**
	 * ����̶�IP��������>����
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=savegdip")
	public void savegdip(HttpServletRequest request,HttpServletResponse response,PassWord command){
		PassWord passWord=(PassWord)command;
		PrintWriter out=null;
		response.setContentType("text/html; charset=GBK");
		try{
			out=response.getWriter();
			SysUser sysuser=this.sysService.getSysUser(command.getYhdh());
			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="�޸Ĺ̶�IP��ַ";
			Log log=new Log(sysuser.getGlbm(),sysuser.getYhdh(),sysdatetime,Constants.MENU_YHGL,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.sysuserManager.savagdip(passWord,log);
			if(result.getFlag()==1){
				UserSession userSession = this.gSystemService.getSessionUserInfo(request);
				if(userSession!=null){
					userSession.getSysuser().setGdip(command.getGdip());
				}
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','�û��̶�IP��ַ�޸ĳɹ���');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc()+"');</script>");
			}
		}catch(Exception e){
			String s=StringUtil.cScriptInfoStr(e.getMessage());
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	@RequestMapping(params = "method=getVadidateImage")
	public synchronized void getVadidateImage(HttpServletRequest request,HttpServletResponse response) throws NumberFormatException,Exception{
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires",0);
		response.setContentType("image/JPEG;charset=GBK");
		HttpSession session=request.getSession(true);
		String width=request.getParameter("width");
		String height=request.getParameter("height");
		String model = request.getParameter("model");
		String len = request.getParameter("len");
		if(width==null||width.equals("")){
			width="80";
			height="30";
		}
		if(model==null||model.equals("")){
			model = "2";
		}
		if(len==null||len.equals("")){
			len = "0";
		}
		/*
		// ���ͼ��ҳ��
		DbReturnInfo returnInfo=this.creatImage(new Integer(width).intValue(),new Integer(height).intValue());		
		ImageIO.write((BufferedImage)returnInfo.getObj(),"JPEG",response.getOutputStream());
		*/
		CheckCode checkCode = new CheckCode(new Integer(model).intValue(),new Integer(width).intValue(),new Integer(height).intValue(),new Integer(len).intValue());
		BufferedImage image = checkCode.generateCheckCodeImage();
		ImageIO.write(image,"JPEG",response.getOutputStream());
		// ����֤�����SESSION
		session.setAttribute("rand_code",WrapUtil.Encrypt(checkCode.getCode()));
	}

	private char[] generateCheckCode(){
		// ������֤����ַ���
		String chars="0123456789ABCDEFGHJKLNPQRSTUVXYZabcdefghijklmnpqrstuvwxyz";
		char[] rands=new char[4];
		for(int i=0;i<4;i++){
			int rand=(int)(Math.random()*32);
			rands[i]=chars.charAt(rand);
		}
		return rands;
	}

	private int[] getRandOnePic(int WIDTH,int HEIGHT,char rands){
		Font[] fonts;
		int[] angles={9,8,7,0,-9,-8,-7};
		int X_POS,Y_POS;
		BufferedImage Image1=new BufferedImage(WIDTH/4,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics2D g1=(Graphics2D)Image1.getGraphics();
		drawBackground(g1,WIDTH/4,HEIGHT);
		Random random=new Random();
		g1.setColor(Color.BLACK);
		int height=(int)(Math.random()*2);
		Color[] randColors={new Color(0,0,0),new Color(0,0,255)};
		if(WIDTH==150){
			X_POS=10;
			Y_POS=HEIGHT-10+height;
			fonts=new Font[]{new Font("ITALIC",Font.BOLD,32),new Font("����",Font.BOLD,32),new Font("����",Font.BOLD,32)};
		}else{
			X_POS=3;
			Y_POS=15+height;
			fonts=new Font[]{new Font("ITALIC",Font.BOLD,18),new Font("����",Font.BOLD,18),new Font("����",Font.BOLD,18)};
		}
		double role=angles[random.nextInt(5)];
		AffineTransform at=new AffineTransform();
		if(role==0){
			at.setToRotation(0,WIDTH/8,HEIGHT/2);
		}else{
			at.setToRotation(Math.PI/role,WIDTH/8,HEIGHT/2);
		}
		g1.setTransform(at);
		g1.setFont(fonts[random.nextInt(1)]);
		g1.setColor(randColors[random.nextInt(1)]);
		g1.drawString(""+rands,X_POS,Y_POS);
		int[] ImageArrays=new int[(WIDTH/4)*HEIGHT];
		ImageArrays=Image1.getRGB(0,0,WIDTH/4,HEIGHT,ImageArrays,0,WIDTH/4);
		return ImageArrays;
	}

	private void drawRands(Graphics2D g,char[] rands,int WIDTH,int HEIGHT,BufferedImage bufferedImage){
		g.setColor(Color.BLACK);
		int[] ImageArrays=getRandOnePic(WIDTH,HEIGHT,rands[0]);
		bufferedImage.setRGB(0,0,WIDTH/4,HEIGHT,ImageArrays,0,WIDTH/4);
		ImageArrays=getRandOnePic(WIDTH,HEIGHT,rands[1]);
		bufferedImage.setRGB(WIDTH/4,0,WIDTH/4,HEIGHT,ImageArrays,0,WIDTH/4);
		ImageArrays=getRandOnePic(WIDTH,HEIGHT,rands[2]);
		bufferedImage.setRGB(WIDTH/2,0,WIDTH/4,HEIGHT,ImageArrays,0,WIDTH/4);
		ImageArrays=getRandOnePic(WIDTH,HEIGHT,rands[3]);
		bufferedImage.setRGB(3*WIDTH/4,0,WIDTH/4,HEIGHT,ImageArrays,0,WIDTH/4);
	}

	private Color getRandColor(int fc,int bc){
		// ������Χ��������ɫ
		Random random=new Random();
		if(fc>255){
			fc=255;
		}
		if(bc>255){
			bc=255;
		}
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}

	private void drawBackground(Graphics g,int WIDTH,int HEIGHT){
		// ������
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0,0,WIDTH,HEIGHT);
		// �������120�����ŵ�
		for(int i=0;i<10;i++){
			int x=(int)(Math.random()*WIDTH);
			int y=(int)(Math.random()*HEIGHT);
			int red=(int)(Math.random()*255);
			int green=(int)(Math.random()*255);
			int blue=(int)(Math.random()*255);
			g.setColor(new Color(red,green,blue));
			g.drawOval(x,y,1,0);
		}
		Random random=new Random();
		for(int i=0;i<10;i++){
			int x=random.nextInt(WIDTH);
			int y=random.nextInt(HEIGHT);
			int xl=random.nextInt(WIDTH);
			int yl=random.nextInt(HEIGHT);
			g.setColor(getRandColor(0,255));
			g.drawLine(x,y,x+xl,y+yl);
		}
	}

	private DbResult creatImage(int WIDTH,int HEIGHT) throws Exception{
		DbResult returnInfo=new DbResult();
		// ���ڴ��д���ͼ��
		BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		// ��ȡͼ��������
		Graphics2D g=(Graphics2D)image.getGraphics();
		// �����������֤��
		char[] rands=generateCheckCode();
		String msg=new String(rands);
		returnInfo.setMsg(WrapUtil.Encrypt(msg));
		// ����ͼ��
		drawRands(g,rands,WIDTH,HEIGHT,image);
		// ����ͼ��Ļ��ƹ���,���ͼ��
		g.dispose();
		returnInfo.setObj(image);
		return returnInfo;
	}

	@RequestMapping(params = "method=getVadidateImageJs")
	public ModelAndView getVadidateImageJs(HttpServletRequest request,HttpServletResponse response){
		try{
			return redirectMav("validimagepage");
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("errMsg",ex.toString());
			return redirectMav("error/errorMsg");
		}
	}
	

	
}
