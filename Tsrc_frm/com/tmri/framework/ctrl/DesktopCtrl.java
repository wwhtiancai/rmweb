package com.tmri.framework.ctrl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.bean.DesktopLinks;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.service.DesktopManager;
import com.tmri.framework.service.ProgramFoldManager;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;
@Controller
@RequestMapping("/desktop.frm")
public class DesktopCtrl extends FrmCtrl{
	@Autowired
	private DesktopManager desktopManager=null;
	@Autowired
	private ProgramFoldManager programFoldManager;
	@Autowired
	private GDepartmentService gDepartmentService;

    private static final String basextlb = "61,60,60,65,64,67,67,67,67,67,67,67,67,67,67,67";

    private static final String basecdbh = "A011,C051,C052,A056,C054,M020,M021,M022,M023,M028,M024,M026,M025,M025,M025,M025";

    private static final String bkclxtlb = "63,63,63";

    private static final String bkclcdbh = "B011,B016,B015";

    private static final String bkxxxtlb = "63,63";

    private static final String bkxxcdbh = "B017,B018";

    private static final String blockshxtlb = "68,68";

    private static final String blockshcdbh = "N012,N015";

    private static final String blockqsxtlb = "68,68";

    private static final String blockqscdbh = "N013,N016";

    private static final String blockcxxtlb = "68,68";

    private static final String blockcxcdbh = "N061,N062";

    private static final String incidentxtlb = "68,68";

    private static final String incidentcdbh = "N064,N064";

	@RequestMapping(params = "method=test")
	public void test(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		out.println("hello,world<br>你好，程序");
		out.close();
	}
	@RequestMapping(params = "method=favorite")
	public void favorite(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		String html="";
		try{
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			Hashtable rightsobj=null;
			String XTYXMS=this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
			HashMap paras=new HashMap();
			paras.put("XTYXMS",XTYXMS);
			HashMap map=this.programFoldManager.getFoldMenuStr(sysUser,paras);
			List ProgramList=(List)map.get("prolist");
			Program program=null;
			for(int i=0;i<ProgramList.size();i++){
				if(rightsobj==null){
					rightsobj=new Hashtable();
				}
				program=(Program)ProgramList.get(i);
				rightsobj.put(program.getXtlb()+"-"+program.getCdbh(),ProgramList.get(i));
			}
			List userdeskList=this.programFoldManager.getUserDeskList(sysUser.getYhdh(),rightsobj);
			Desktop mydesktop=this.desktopManager.getMyDesktop(sysUser.getYhdh(),"1");
			if(userdeskList==null||userdeskList.size()<1){
				html="<div class=\"s1\" style=\"height:32px;\"></div>";
				html+="<div style=\"fonts-size:12px;color:black;text-align:center;\">您尚未拥有自己的快捷菜单<span class=\"s4\"><br><br></span>立即开始：<input type=\"button\" value=\"定制我的快捷菜单\" onClick=\"dofavoritesetting()\"></div>";
			}else{
				int column=Integer.parseInt(mydesktop.getMksx1());
				int row=Integer.parseInt(mydesktop.getMksx2());
				int c=0;
				String tdhtml="",tablehtml="";
				int total=userdeskList.size();
				if(total>column*row){
					for(int i=0;i<column*row;i++){
						c++;
						UserDesk userdesk=(UserDesk)userdeskList.get(i);
						tdhtml+="<td align=\"center\" valign=\"top\" bgcolor=\"#f1f1f1\" height=\"75\"><a href=\"javascript:void(0)\" style=\"font-size:12px;color:black;\" onClick=\"openWindow('login.frm?method=sendRedirect&cdbh="+userdesk.getCdbh()+"&xtlb="+userdesk.getXtlb()
								+"')\"><img src=\"theme/style/"+userdesk.getTbmc()+"\" alt=\"\" width=\"36\" height=\"36\" border=\"0\"><span class=\"s4\"><br><br></span>"+userdesk.getCxmc()+"</a></td>";
						if(c%column==0){
							tablehtml+="<tr>"+tdhtml+"</tr>";
							tdhtml="";
						}
					}
					if(tdhtml.length()>1){
						if(c%column!=0){
							for(int j=column;j>c%column;j--){
								tdhtml+="<td bgcolor=\"#f1f1f1\" valign=\"top\"  height=\"75\">&nbsp;</td>";
							}
						}
						tablehtml=tablehtml+"<tr>"+tdhtml+"</tr>";
					}
					if(tablehtml.length()>1){
						if(mydesktop.getMksx1().equals("4")){
							tablehtml="<col width=\"25%\"><col width=\"25%\"><col width=\"25%\"><col width=\"25%\">"+tablehtml;
						}else if(mydesktop.getMksx1().equals("5")){
							tablehtml="<col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\">"+tablehtml;
						}
						tablehtml="<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" align=\"center\" bgcolor=\"white\">"+tablehtml+"</table>";
					}
					html=tablehtml+"<div id=\"favoriteunfurlcontrol\" style=\"text-align:right;padding-top:3px;height:20\"><a href=\"javascript:void(0)\" style=\"font-size:12px;color:blue;\" onClick=\"favoriteunfurl()\">还有<span style=\"color:red;\">"
							+String.valueOf(total-column*row)+"个</span>快捷菜单，点击展开</div>";
					c=0;
					tdhtml="";
					tablehtml="";
					if(total>column*4){
						total=column*4;
					}
					for(int i=column*row;i<total;i++){
						c++;
						UserDesk userdesk=(UserDesk)userdeskList.get(i);
						tdhtml+="<td align=\"center\" valign=\"top\" bgcolor=\"#f1f1f1\" height=\"75\"><a href=\"javascript:void(0)\" style=\"font-size:12px;color:black;\" onClick=\"openWindow('login.frm?method=sendRedirect&cdbh="+userdesk.getCdbh()+"&xtlb="+userdesk.getXtlb()
								+"')\"><img src=\"theme/style/"+userdesk.getTbmc()+"\" alt=\"\" width=\"36\" height=\"36\" border=\"0\"><span class=\"s4\"><br><br></span>"+userdesk.getCxmc()+"</a></td>";
						if(c%column==0){
							tablehtml+="<tr>"+tdhtml+"</tr>";
							tdhtml="";
						}
					}
					if(tdhtml.length()>1){
						if(c%column!=0){
							for(int j=column;j>c%column;j--){
								tdhtml+="<td bgcolor=\"#f1f1f1\" valign=\"top\"  height=\"75\"></td>";
							}
						}
						tablehtml=tablehtml+"<tr>"+tdhtml+"</tr>";
					}
					if(tablehtml.length()>1){
						if(mydesktop.getMksx1().equals("4")){
							tablehtml="<col width=\"25%\"><col width=\"25%\"><col width=\"25%\"><col width=\"25%\">"+tablehtml;
						}else if(mydesktop.getMksx1().equals("5")){
							tablehtml="<col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\">"+tablehtml;
						}
						tablehtml="<table id=\"favoriteunfurl\" style=\"display:none;\" width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" align=\"center\" bgcolor=\"white\">"+tablehtml
								+"</table><div id=\"close_tt\" style=\"text-align:right;padding-top:3px;height:20;display:none;\"><a href=\"javascript:void(0)\" style=\"font-size:12px;color:blue;\" onClick=\"close_tt();\">点击收起</a></div>";
					}
					html=html+tablehtml;
				}else{
					for(Iterator it=userdeskList.iterator();it.hasNext();){
						c++;
						UserDesk userdesk=(UserDesk)it.next();
						tdhtml+="<td align=\"center\" valign=\"top\" bgcolor=\"#f1f1f1\" height=\"80\"><a href=\"javascript:void(0)\" style=\"font-size:12px;color:black;\" onClick=\"openWindow('login.frm?method=sendRedirect&cdbh="+userdesk.getCdbh()+"&xtlb="+userdesk.getXtlb()
								+"')\"><img src=\"theme/style/"+userdesk.getTbmc()+"\" alt=\"\" width=\"36\" height=\"36\" border=\"0\"><span class=\"s4\"><br><br></span>"+userdesk.getCxmc()+"</a></td>";
						if(c%column==0){
							tablehtml+="<tr>"+tdhtml+"</tr>";
							tdhtml="";
						}
					}
					if(tdhtml.length()>1){
						if(c%column!=0){
							for(int j=column;j>c%column;j--){
								tdhtml+="<td bgcolor=\"#f1f1f1\" valign=\"top\" height=\"75\"></td>";
							}
						}
						tablehtml=tablehtml+"<tr>"+tdhtml+"</tr>";
					}
					if(tablehtml.length()>1){
						if(mydesktop.getMksx1().equals("4")){
							tablehtml="<col width=\"25%\"><col width=\"25%\"><col width=\"25%\"><col width=\"25%\">"+tablehtml;
						}else if(mydesktop.getMksx1().equals("5")){
							tablehtml="<col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\"><col width=\"20%\">"+tablehtml;
						}
						tablehtml="<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" align=\"center\" bgcolor=\"white\">"+tablehtml+"</table>";
					}
					html=tablehtml;
				}
			}
		}catch(Exception e){
			html="error:"+e.getLocalizedMessage();
			e.printStackTrace();
		}
		out.println(html);
		out.close();
	}
	@RequestMapping(params = "method=favoritesave")
	public void favoritesave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		int r=0;
		try{
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			int mksx1=Integer.parseInt(request.getParameter("mksx1"));
			int mksx2=Integer.parseInt(request.getParameter("mksx2"));
			if(mksx1<4||mksx1>5)
				mksx1=5;
			if(mksx2<2||mksx2>4)
				mksx2=2;
			Desktop d=new Desktop();
			d.setYhdh(sysUser.getYhdh());
			d.setMkbh("1");
			d.setMksx1(String.valueOf(mksx1));
			d.setMksx2(String.valueOf(mksx2));
			r=this.desktopManager.saveDesktopUserAttribute(d);
		}catch(Exception e){
			e.printStackTrace();
			r=-1;
		}
		out.println(r);
		out.close();
	}
	
	
	@RequestMapping(params = "method=external")
	public void external(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		String html="";
		try{
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			Desktop mydesktop=this.desktopManager.getMyDesktop(sysUser.getYhdh(),"4");
            List list = this.desktopManager.getMyDesktopLinkss(null);
			if(list==null||list.size()<1){
				html="<div class=\"s1\" style=\"height:66px;\"></div>";
				html+="<div style=\"fonts-size:12px;color:black;text-align:center;\">您还没有设置链接信息，点击开始：<input type=\"button\" value=\"设置\" onClick=\"dosetting('4')\"></div>";
			}else{
				int c=0;
				String tdhtml="",tablehtml="";
				for(Iterator it=list.iterator();it.hasNext();){
					c++;
					DesktopLinks dl=(DesktopLinks)it.next();
                    int xh = Integer.valueOf(dl.getXh()) % 9;
                    tdhtml += "<td height=\"38\"><a href=\""
                            + dl.getLjdz()
                            + "\" style=\"font-size:14px;color:black;\" target=\"_blank\"><img src=\"theme/page/iconquery"
                            + String.valueOf(xh)
                            + ".gif\" alt=\"\" hspace=\"5\" border=\"0\" align=\"absmiddle\">"
                            + dl.getLjmc() + "</a></td>";
					if(c%2==0){
						tablehtml+="<tr>"+tdhtml+"</tr>";
						tdhtml="";
					}
				}
				if(tdhtml.length()>1){
					if(c%2!=0){
						for(int j=2;j>c%2;j--){
							tdhtml+="<td></td>";
						}
					}
					tablehtml=tablehtml+"<tr>"+tdhtml+"</tr>";
				}
				if(tablehtml.length()>1){
					tablehtml="<col align=\"left\" width=\"50%\"><col align=\"left\" width=\"50%\">"+tablehtml;
					tablehtml="<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"+tablehtml+"</table>";
				}
				html=tablehtml;
			}
		}catch(Exception e){
			html="error:"+e.getLocalizedMessage();
		}
		out.println(html);
		out.close();
	}

    /**
     * 业务链接设置
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=setupexternal")
    public ModelAndView setupexternal(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute("userSession");

            List linklist = this.desktopManager.getMyDesktopLinkss(null);
            request.setAttribute("linklist", linklist);
            request.setAttribute("linksize", linklist.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return redirectMav("setupexternal");
    }

    @RequestMapping(params = "method=saveExternal")
    public void saveExternal(HttpServletRequest request, HttpServletResponse response,
            DesktopLinks link) throws Exception {
        int iResult = 0;
        PrintWriter out = null;
        try {
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute("userSession");
            response.setContentType("text/html; charset=GBK");
            SysUser sysuser = userSession.getSysuser();
            out = response.getWriter();
            if (!StringUtil.checkBN(link.getMcs())) {
                return;
            }
            String[] mcs = link.getMcs().split("!@#");
            String[] dzs = link.getDzs().split("!@#");
            List<DesktopLinks> list = new ArrayList<DesktopLinks>();
            DesktopLinks bean = null;
            for (int i = 0; i < mcs.length; i++) {
                bean = new DesktopLinks();
                if (!StringUtil.checkBN(mcs[i])) {
                    continue;
                }
                bean.setCjjg(sysuser.getGlbm());
                bean.setLjmc(mcs[i]);
                bean.setLjdz(dzs[i]);
                list.add(bean);
            }
            DbResult result = this.desktopManager.saveMyDesktopLinks(list);
            // if (result.getCode() == 1) {
            // out.println("<script language=javascript>parent.resultSave('1',"
            // + "'链接添加成功！');</script>");
            // } else {
            // out.println("<script language=javascript>parent.resultSave('0',"
            // + "'链接添加时发生错误！');</script>");
            // }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(params = "method=delExternal")
    public void delExternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int iResult = 0;
        PrintWriter out = null;
        try {
            response.setContentType("text/html; charset=GBK");
            out = response.getWriter();
            String xh = request.getParameter("xh");
            DbResult result = this.desktopManager.delDesktop(xh);
            // if (!StringUtil.checkBN(xh)) {
            // out.println("<script language=javascript>parent.resultSave('0',"
            // + "'删除发生错误！');</script>");
            // } else {
            // if (result.getCode() == 1) {
            // out.println("<script language=javascript>parent.resultSave('1',"
            // + "'链接删除成功！');</script>");
            // } else {
            // out.println("<script language=javascript>parent.resultSave('0',"
            // + "'删除发生错误！');</script>");
            // }
            // }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	@RequestMapping(params = "method=externalsave")
	public void externalsave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		int r=0;
		try{
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			String mksx1=request.getParameter("mksx1");
			if(mksx1==null||mksx1.length()<1)
				mksx1="";
			Desktop d=new Desktop();
			d.setYhdh(sysUser.getYhdh());
			d.setMkbh("4");
            d.setMksx1(String.valueOf(mksx1) + "!@#");
			r=this.desktopManager.saveDesktopUserAttribute(d);
		}catch(Exception e){
			e.printStackTrace();
			r=-1;
		}
		out.println(r);
		out.close();
	}

	@RequestMapping(params = "method=position")
	public void position(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		int r=0;
		try{
			SysUser sysUser=gSystemService.getSessionUserInfo(request.getSession()).getSysuser();
			String xy=request.getParameter("xy");
			if(xy==null||xy.length()<1){
				r=0;
			}else{
				r=this.desktopManager.saveDesktopUserPosition(sysUser.getYhdh(),xy);
			}
		}catch(Exception e){
			e.printStackTrace();
			r=-1;
		}
		out.println(r);
		out.close();
	}

	private String getwordshow(String str,int len){
		if(len<str.length()){
			return str.substring(0,len)+"..";
		}else{
			return str;
		}
	}
	private String cleanline(String str){
		if(str==null){
			return "";
		}else{
			String r=str;
			r=r.replaceAll("\r\n","<br>");
			r=r.replaceAll("\r","<br>");
			r=r.replaceAll("\n","<br>");
			return r;
		}
	}
}
