package com.tmri.framework.ctrl;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.service.DesktopManager;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.service.GSystemService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;
@Controller
@RequestMapping("/main.frm")
public class MainCtrl extends FrmCtrl{
	private final String scope="(最近一个月)";
	private String start="2013-10-20";
	private String end="2013-11-20";
	@Autowired
	private DesktopManager desktopManager=null;
	@Autowired
	private GSysparaCodeService gSysparaCodeService;
	@Autowired
	private GSystemService gSystemService;

	@RequestMapping(params="method=top")
	public ModelAndView top(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("sysService",this.gSystemService);
		request.setAttribute("gSysparaCodeService",this.gSysparaCodeService);
		request.setAttribute("SFT",this.gSysparaCodeService.getSysParaValue("00","SFT"));
		return redirectMav("top");
	}

	@RequestMapping(params="method=left")
	public ModelAndView left(HttpServletRequest request,HttpServletResponse response){
		return redirectMav("left");
	}

	@RequestMapping(params="method=rightmain")
	public ModelAndView rightmain(HttpServletRequest request,HttpServletResponse response){
		String xtyxms=this.gSysparaCodeService.getParaValue("00","XTYXMS");
		request.setAttribute("xtyxms",xtyxms);
		return redirectMav("rightmain");
	}

	@RequestMapping(params="method=righttop")
	public ModelAndView righttop(HttpServletRequest request,HttpServletResponse response){
		try{
			String xtyxms=this.gSysparaCodeService.getParaValue("00","XTYXMS");
			request.setAttribute("xtyxms",xtyxms);
			String control=request.getParameter("control");
			if(control==null)
				control="0";
			request.setAttribute("control",control);
			HttpSession session=request.getSession();
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			String qxxx="";
			if(this.gSystemService.checkUserRight(request.getSession(),Constants.SYS_XTLB_FRAME,Constants.MENU_YWGG,"")){
				qxxx="A001";
				String gnlbString=sysService.getUserCxdhGnstr(request.getSession(),Constants.SYS_XTLB_FRAME,Constants.MENU_YWGG);
			}
			List desktoplist=(List)session.getAttribute("desktoplist");
			Desktop mytemp=this.desktopManager.getMyDesktop(sysUser.getYhdh(),"0");
			if(mytemp!=null&&mytemp.getMksx1().equals("0000")){
				String urlsign="",urls="";
				List temp=this.desktopManager.getMyDesktops(sysUser.getYhdh());
				for(Iterator iter=temp.iterator();iter.hasNext();){
					Desktop d=(Desktop)iter.next();
					if(d.getMkbh().equals("0")&&!d.getMksx1().equals("")){
						Program program=null;
						if(desktoplist!=null){
							for(Iterator its=desktoplist.iterator();its.hasNext();){
								program=(Program)its.next();
								if(program.getCdbh().equals(d.getMksx1())){
									urlsign=program.getCdbh();
									urls=program.getYmdz();
									break;
								}
							}
						}
						if(urls.length()>1&&!control.equals("1")){
							request.setAttribute("urlsign",urlsign);
							request.setAttribute("urls",urls);
						}
					}
				}
				if(urls.length()<1||control.equals("1")){
					request.setAttribute("desktoplist",desktoplist);
					request.setAttribute("first","0");
					LinkedList mylist=new LinkedList();
					for(Iterator it=temp.iterator();it.hasNext();){
						Desktop d=(Desktop)it.next();
						d.setBz(String.valueOf(Integer.parseInt(d.getMkwz())%2));
						if(d.getHtml().indexOf("[c:out value=\"${desktoplist}\"/]")!=-1){
							String html="";
							if(desktoplist!=null){
								for(Iterator its=desktoplist.iterator();its.hasNext();){
									Program programs=(Program)its.next();
									html+="<option value=\""+programs.getCdbh()+"\">"+programs.getCxmc()+"</option>";
								}
							}
							d.setHtml(d.getHtml().replace("[c:out value=\"${desktoplist}\"/]",html));
						}
						mylist.add(d);
					}
					request.setAttribute("mylist",mylist);
					request.setAttribute("nows",this.gSysDateService.getSysDate(0,"yyyy-mm-dd hh24:mi:ss"));
				}
			}else if((mytemp==null||mytemp.getMksx1()==null||mytemp.getMksx1().length()<1)||control.equals("1")){
				String urlsign="",urls="";
				List temp=this.desktopManager.getMyDesktops(sysUser.getYhdh());
				if(temp==null||temp.size()<1){
					Program program=null;
					if(desktoplist!=null){
						for(Iterator it=desktoplist.iterator();it.hasNext();){
							program=(Program)it.next();
							urlsign=program.getCdbh();
							urls=program.getYmdz();
							break;
						}
					}
					if(urls!=null&&urls.length()>1&&!control.equals("1")){
						request.setAttribute("urlsign",urlsign);
						request.setAttribute("urls",urls);
					}else{
						request.setAttribute("desktoplist",desktoplist);
						request.setAttribute("first","1");
					}
				}else{
					for(Iterator iter=temp.iterator();iter.hasNext();){
						Desktop d=(Desktop)iter.next();
						if(d.getMkbh().equals("0")&&!d.getMksx1().equals("")){
							Program program=null;
							if(desktoplist!=null){
								for(Iterator its=desktoplist.iterator();its.hasNext();){
									program=(Program)its.next();
									if(program.getCdbh().equals(d.getMksx1())){
										urlsign=program.getCdbh();
										urls=program.getYmdz();
										break;
									}
								}
							}
							if(urls.length()>1&&!control.equals("1")){
								request.setAttribute("urlsign",urlsign);
								request.setAttribute("urls",urls);
							}
						}
					}
					if(urls.length()<1||control.equals("1")){
						request.setAttribute("desktoplist",desktoplist);
						request.setAttribute("first","0");
						LinkedList mylist=new LinkedList();
						for(Iterator it=temp.iterator();it.hasNext();){
							Desktop d=(Desktop)it.next();
							d.setBz(String.valueOf(Integer.parseInt(d.getMkwz())%2));
							if(d.getHtml().indexOf("[c:out value=\"${desktoplist}\"/]")!=-1){
								String html="";
								if(desktoplist!=null){
									for(Iterator its=desktoplist.iterator();its.hasNext();){
										Program programs=(Program)its.next();
										html+="<option value=\""+programs.getCdbh()+"\">"+programs.getCxmc()+"</option>";
									}
								}
								d.setHtml(d.getHtml().replace("[c:out value=\"${desktoplist}\"/]",html));
							}
							mylist.add(d);
						}
						request.setAttribute("mylist",mylist);
						request.setAttribute("nows",this.gSysDateService.getSysDate(0,"yyyy-mm-dd hh24:mi:ss"));
					}
				}
			}else if(mytemp!=null&&mytemp.getMksx1()!=null&&mytemp.getMksx1().length()>0){
				String urlsign="",urls="";
				Program program=null;
				if(desktoplist!=null){
					for(Iterator it=desktoplist.iterator();it.hasNext();){
						program=(Program)it.next();
						if(program.getCdbh().equals(mytemp.getMksx1())){
							request.setAttribute("urlsign",program.getCdbh());
							request.setAttribute("urls",program.getYmdz());
							break;
						}
					}
				}
			}else{
				request.setAttribute("desktoplist",desktoplist);
				request.setAttribute("first","1");
			}
			request.setAttribute("dtsz",null!=desktoplist&&!desktoplist.isEmpty()?desktoplist.size():0);
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("errMsg",errmsg);
			return redirectMav("error");
		}
		return redirectMav("righttop");
	}

	@RequestMapping(params="method=right")
	public ModelAndView right(HttpServletRequest request,HttpServletResponse response){
		try{
			String control=request.getParameter("control");
			if(control==null)
				control="0";
			request.setAttribute("control",control);
			HttpSession session=request.getSession();
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			String qxxx="";
			if(this.gSystemService.checkUserRight(request.getSession(),Constants.SYS_XTLB_FRAME,Constants.MENU_YWGG,"")){
				qxxx="A001";
				String gnlbString=sysService.getUserCxdhGnstr(request.getSession(),Constants.SYS_XTLB_FRAME,Constants.MENU_YWGG);
			}
			List desktoplist=(List)session.getAttribute("desktoplist");
			Desktop mytemp=this.desktopManager.getMyDesktop(sysUser.getYhdh(),"0");
			if(mytemp!=null&&mytemp.getMksx1().equals("0000")){
				String urlsign="",urls="";
				List temp=this.desktopManager.getMyDesktops(sysUser.getYhdh());
				for(Iterator iter=temp.iterator();iter.hasNext();){
					Desktop d=(Desktop)iter.next();
					if(d.getMkbh().equals("0")&&!d.getMksx1().equals("")){
						Program program=null;
						if(desktoplist!=null){
							for(Iterator its=desktoplist.iterator();its.hasNext();){
								program=(Program)its.next();
								if(program.getCdbh().equals(d.getMksx1())){
									urlsign=program.getCdbh();
									urls=program.getYmdz();
									break;
								}
							}
						}
						if(urls.length()>1&&!control.equals("1")){
							request.setAttribute("urlsign",urlsign);
							request.setAttribute("urls",urls);
						}
					}
				}
				if(urls.length()<1||control.equals("1")){
					request.setAttribute("desktoplist",desktoplist);
					request.setAttribute("first","0");
					LinkedList mylist=new LinkedList();
					for(Iterator it=temp.iterator();it.hasNext();){
						Desktop d=(Desktop)it.next();
						d.setBz(String.valueOf(Integer.parseInt(d.getMkwz())%2));
						if(d.getHtml().indexOf("[c:out value=\"${desktoplist}\"/]")!=-1){
							String html="";
							if(desktoplist!=null){
								for(Iterator its=desktoplist.iterator();its.hasNext();){
									Program programs=(Program)its.next();
									html+="<option value=\""+programs.getCdbh()+"\">"+programs.getCxmc()+"</option>";
								}
							}
							d.setHtml(d.getHtml().replace("[c:out value=\"${desktoplist}\"/]",html));
						}
						mylist.add(d);
					}
					request.setAttribute("mylist",mylist);
					request.setAttribute("nows",this.gSysDateService.getSysDate(0,"yyyy-mm-dd hh24:mi:ss"));
				}
			}else if((mytemp==null||mytemp.getMksx1()==null||mytemp.getMksx1().length()<1)||control.equals("1")){
				String urlsign="",urls="";
				List temp=this.desktopManager.getMyDesktops(sysUser.getYhdh());
				if(temp==null||temp.size()<1){
					Program program=null;
					if(desktoplist!=null){
						for(Iterator it=desktoplist.iterator();it.hasNext();){
							program=(Program)it.next();
							urlsign=program.getCdbh();
							urls=program.getYmdz();
							break;
						}
					}
					if(urls!=null&&urls.length()>1&&!control.equals("1")){
						request.setAttribute("urlsign",urlsign);
						request.setAttribute("urls",urls);
					}else{
						request.setAttribute("desktoplist",desktoplist);
						request.setAttribute("first","1");
					}
				}else{
					for(Iterator iter=temp.iterator();iter.hasNext();){
						Desktop d=(Desktop)iter.next();
						if(d.getMkbh().equals("0")&&!d.getMksx1().equals("")){
							Program program=null;
							if(desktoplist!=null){
								for(Iterator its=desktoplist.iterator();its.hasNext();){
									program=(Program)its.next();
									if(program.getCdbh().equals(d.getMksx1())){
										urlsign=program.getCdbh();
										urls=program.getYmdz();
										break;
									}
								}
							}
							if(urls.length()>1&&!control.equals("1")){
								request.setAttribute("urlsign",urlsign);
								request.setAttribute("urls",urls);
							}
						}
					}
					if(urls.length()<1||control.equals("1")){
						request.setAttribute("desktoplist",desktoplist);
						request.setAttribute("first","0");
						LinkedList mylist=new LinkedList();
						for(Iterator it=temp.iterator();it.hasNext();){
							Desktop d=(Desktop)it.next();
							d.setBz(String.valueOf(Integer.parseInt(d.getMkwz())%2));
							if(d.getHtml().indexOf("[c:out value=\"${desktoplist}\"/]")!=-1){
								String html="";
								if(desktoplist!=null){
									for(Iterator its=desktoplist.iterator();its.hasNext();){
										Program programs=(Program)its.next();
										html+="<option value=\""+programs.getCdbh()+"\">"+programs.getCxmc()+"</option>";
									}
								}
								d.setHtml(d.getHtml().replace("[c:out value=\"${desktoplist}\"/]",html));
							}
							mylist.add(d);
						}
						request.setAttribute("mylist",mylist);
						request.setAttribute("nows",this.gSysDateService.getSysDate(0,"yyyy-mm-dd hh24:mi:ss"));
					}
				}
			}else if(mytemp!=null&&mytemp.getMksx1()!=null&&mytemp.getMksx1().length()>0){
				String urlsign="",urls="";
				Program program=null;
				if(desktoplist!=null){
					for(Iterator it=desktoplist.iterator();it.hasNext();){
						program=(Program)it.next();
						if(program.getCdbh().equals(mytemp.getMksx1())){
							request.setAttribute("urlsign",program.getCdbh());
							request.setAttribute("urls",program.getYmdz());
							break;
						}
					}
				}
			}else{
				request.setAttribute("desktoplist",desktoplist);
				request.setAttribute("first","1");
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("errMsg",errmsg);
			return redirectMav("error");
		}
		request.setAttribute("sysservice",this.sysService);
		return redirectMav("right");
	}
	@RequestMapping(params="method=middle")
	public ModelAndView middle(HttpServletRequest request,HttpServletResponse response){
		return redirectMav("middle");
	}
	@RequestMapping(params="method=desktop")
	public ModelAndView desktop(HttpServletRequest request,HttpServletResponse response){
		try{
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			List mylist=this.desktopManager.getMyDesktops(sysUser.getYhdh());
			Desktop mydesktop=this.desktopManager.getMyDesktop(sysUser.getYhdh(),"0");
			List querylist=this.desktopManager.getDesktops();
			request.setAttribute("querylist",querylist);
			request.setAttribute("querylistsize",querylist.size());
			if(mydesktop!=null&&mydesktop.getMksx1()!=null&&mydesktop.getMksx1().length()>1){
				request.setAttribute("first","0");
				HttpSession session=request.getSession();
				List desktoplist=(List)session.getAttribute("desktoplist");
				request.setAttribute("desktoplist",desktoplist);
				String mylists="";
				for(Iterator it=mylist.iterator();it.hasNext();){
					Desktop d=(Desktop)it.next();
					mylists+=d.getMkbh()+",";
				}
				if(mylists.length()>1){
					mylists.substring(0,mylists.length()-1);
				}
				request.setAttribute("indexs",mydesktop.getMksx1());
				request.setAttribute("mylists",mylists);
			}else if(mylist==null||mylist.size()<1){
				request.setAttribute("first","1");
			}else{
				request.setAttribute("first","0");
				HttpSession session=request.getSession();
				List desktoplist=(List)session.getAttribute("desktoplist");
				request.setAttribute("desktoplist",desktoplist);
				String mylists="";
				String indexs="";
				for(Iterator it=mylist.iterator();it.hasNext();){
					Desktop d=(Desktop)it.next();
					if(d.getMkbh().equals("0")){
						indexs=d.getMksx1();
					}
					mylists+=d.getMkbh()+",";
				}
				if(mylists.length()>1){
					mylists.substring(0,mylists.length()-1);
				}
				request.setAttribute("indexs",indexs);
				request.setAttribute("mylists",mylists);
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("errMsg",errmsg);
			return redirectMav("error");
		}
		request.setAttribute("sysservice",this.sysService);
		return redirectMav("desktop");
	}
	@RequestMapping(params="method=desktopsave")
	public void desktopsave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		int r=0;
		try{
			String indexs=request.getParameter("indexs");
			if(indexs==null||indexs.length()<1)
				indexs="";
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			String content=request.getParameter("content");
			if(content==null||content.length()<1){
				r=0;
			}else{
				r=this.desktopManager.saveDesktopUserData(content,sysUser.getYhdh(),indexs);
			}
		}catch(Exception e){
			e.printStackTrace();
			r=-1;
		}
		out.println(r);
		out.close();
	}
	@RequestMapping(params="method=about")
    public ModelAndView about(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List downloadlist = this.desktopManager.getMyDesktopDownloads(null);
        request.setAttribute("downloadlist", downloadlist);
		return redirectMav("about");
	}

	@RequestMapping(params="method=gate")
	public ModelAndView gate(HttpServletRequest request,HttpServletResponse response){
		return redirectMav("gate");
	}
	@RequestMapping(params="method=count")
	public ModelAndView count(HttpServletRequest request,HttpServletResponse response){
		return redirectMav("count");
	}

	private String fixZero(int i){
		return ("00"+i).replaceAll(".*(\\d{2})$","$1");
	}
	
}
