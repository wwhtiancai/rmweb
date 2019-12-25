package com.tmri.framework.ctrl;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.tmri.framework.bean.FrmNoworkday;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.service.DepartmentManager;
import com.tmri.framework.service.SysParaManager;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SafeWebMachineInfo;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.service.GSysDateService;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
@Controller
@RequestMapping("/syspara.frm")
public class SysParaCtrl extends FrmCtrl{

    public static final Logger LOG = LoggerFactory.getLogger(SysParaCtrl.class);

	@Autowired
	private SysParaManager sysparaManager;
	@Autowired
	private DepartmentManager departmentManager;

	private String MSG_NOROLEGLBM="无权编辑该管理部门参数信息!";
	
	private String[] allhpzlString={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};
	@Autowired
	private GDepartmentService gDepartmentService;
	@Autowired
	private GSysparaCodeService gSysparaCodeService;
	@Autowired
	private GSysDateService gSysDateService;
	
    private final String XTLB = "00";

    private final String CDBH_PARA = "K021";

    private final String CDBH_DEPT = "K029";

	/**
	 * 系统参数维护————>系统参数查询界面
	 */
	@RequestMapping(params = "method=querySysParaList")
	public ModelAndView querySysParaList(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			List queryList;

			this.sysService.doBeginCall(request,"00",Constants.MENU_XTCS,"","");

			queryList=this.gSysparaCodeService.getSysparasShow(userSession.getDepartment().getBmjb());
            // 程序名称
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH_PARA));
			request.setAttribute("querylist",queryList); // 返回查询list
			return redirectMav("jsp/sysparaList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	private String getCszxgHtmlString(SysPara sysPara,int flag,String glbm) throws Exception{
		String cszxghtml="";
		String cszmc="mrz";
		if(flag==2) cszmc="csz";
		if(sysPara.getSfxs().equals("3")){
			// 文本框
			cszxghtml="<input type=\"text\" name=\""+cszmc+"\" class=\"box\" value=\""+sysPara.getMrz()+"\" size=\"95\" maxlength=\"2048\" readOnly>";
		}else{
			if(sysPara.getXsxs().equals("1")){
				// 文本框
				cszxghtml="<input type=\"text\" name=\""+cszmc+"\" class=\"box\" value=\""+sysPara.getMrz()+"\" size=\"95\" maxlength=\"2048\">";
			}else if(sysPara.getXsxs().equals("2")){
				// 下拉框 依据代码
				cszxghtml="<select name=\""+cszmc+"\" class=\"text_12\" size=\"1\" style=\"width:250\">";
				int iPos=sysPara.getDmlb().indexOf(",");
				String xtlb=sysPara.getDmlb().substring(0,iPos);
				String dmlb=sysPara.getDmlb().substring(iPos+1);
				cszxghtml+=this.gHtmlService.transDmlbToOptionHtml(xtlb,dmlb,"",true,"3","");
				cszxghtml+="</select>";
			}else if(sysPara.getXsxs().equals("3")){
				// 下拉框 无代码
				cszxghtml="<select name=\""+cszmc+"\" class=\"text_12\" size=\"1\" style=\"width:250\">";
				cszxghtml+=sysPara.getDmlb();
				cszxghtml+="</select>";
			}else if(sysPara.getXsxs().equals("4")){
				// 是否radio
				cszxghtml="<select name=\""+cszmc+"\" class=\"text_12\" size=\"1\" style=\"width:250\">";
				cszxghtml+="<option value=\"1\" selected>是</option>";
				cszxghtml+="<option value=\"2\">否</option>";
				cszxghtml+="</select>";
			}else if(sysPara.getXsxs().equals("5")){
				// 多选checkbox 依据代码
				int iPos=sysPara.getDmlb().indexOf(",");
				String xtlb=sysPara.getDmlb().substring(0,iPos);
				String dmlb=sysPara.getDmlb().substring(iPos+1);
				cszxghtml=this.gHtmlService.transDmlbToCheckBoxHtml(xtlb,dmlb,"ck_"+cszmc,"","3");
				cszxghtml+="<input type='hidden' name='"+cszmc+"' value=''/>";
			}else if(sysPara.getXsxs().equals("6")){
				// 多选checkbox 无需依据代码
				cszxghtml=sysPara.getDmlb();
				cszxghtml+="<input type='hidden' name='"+cszmc+"' value=''/>";
			}else{
				// 特殊处理
				if(sysPara.getXtlb().equals("00")&&sysPara.getGjz().equals("WGFWFWQ")){
					// 外挂接口访问服务器特殊处理
					List list=null;
					cszxghtml="<table border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"list_table\" id=\"id_csztable\">";
					cszxghtml+="<Tr><td width=\"10%\" class=\"list_body_left\">请选择</td><td width=\"10%\" class=\"list_body_left\">IP地址</td><td class=\"list_body_left\">只允许外挂系统访问</td></tr>";
					SafeWebMachineInfo safeWebMachineInfo=null;
					for(int i=0;i<list.size();i++){
						safeWebMachineInfo=(SafeWebMachineInfo)list.get(i);
						cszxghtml+="<tr>";
						cszxghtml+="<td class=\"list_body_left\">";
						if(sysPara.getMrz().indexOf(safeWebMachineInfo.getIpdz())>=0){
							cszxghtml+="<input type=\"checkbox\" name=\"ipdz\" value= \""+safeWebMachineInfo.getIpdz()+"\" checked/>";
							cszxghtml+="</td>";
							cszxghtml+="<td class=\"list_body_left\">"+safeWebMachineInfo.getIpdz()+"</td>";
							cszxghtml+="<td class=\"list_body_left\">";
							if(sysPara.getMrz().indexOf(safeWebMachineInfo.getIpdz()+":1")>=0){
								cszxghtml+="<input type=\"checkbox\" name=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\" id=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\" checked/><label for=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\">是</label>";
							}else{
								cszxghtml+="<input type=\"checkbox\" name=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\" id=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\" /><label for=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\">是</label>";
							}
							cszxghtml+="</td>";
							cszxghtml+="</tr>";
						}else{
							cszxghtml+="<input type=\"checkbox\" name=\"ipdz\" value= \""+safeWebMachineInfo.getIpdz()+"\"/>";
							cszxghtml+="</td>";
							cszxghtml+="<td class=\"list_body_left\">"+safeWebMachineInfo.getIpdz()+"</td>";
							cszxghtml+="<td class=\"list_body_left\"><input type=\"checkbox\" name=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\" id=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\" /><label for=\"LIMIT_"+safeWebMachineInfo.getIpdz()+"\">是</label></td>";
							cszxghtml+="</tr>";
						}
					}
					cszxghtml+="</table><input type=\"hidden\" name=\"mrz\" value=\""+sysPara.getMrz()+"\"/>";
				}else if(sysPara.getXtlb().equals("00")&&sysPara.getGjz().equals("SSLPORT")){
					cszxghtml= "<textarea name=\"mrz\" style=\"width:98%\" rows=\"10\">" + sysPara.getMrz() + "</textarea>";
				}else{	
					cszxghtml="<input type=\"text\" name=\"csz\" class=\"box\" value=\""+sysPara.getMrz()+"\" size=\"95\" maxlength=\"256\">";
				}
			}
		}

		return cszxghtml;
	}

	/**
	 * 系统参数维护————>打开编辑页面
	 */
	@RequestMapping(params = "method=editsyspara")
	public ModelAndView editsyspara(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			String gjz=(String)request.getParameter("gjz");
			String xtlb=(String)request.getParameter("xtlb");

			this.sysService.doBeginCall(request,"00",Constants.MENU_XTCS,"","");

			SysPara syspara=this.gSysparaCodeService.getSyspara(xtlb,"2",gjz);

			request.setAttribute("syspara",syspara);
			request.setAttribute("modal","edit");
			sysService.genWebKey(request);
			request.setAttribute("cszxghtml",this.getCszxgHtmlString(syspara,1,""));
            // 程序名称
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH_PARA));
			return redirectMav("jsp/sysparaEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}

	/**
	 * 系统参数维护————>保存系统参数
	 * @param request
	 * @param response
	 * @param command
	 */
	@RequestMapping(params = "method=saveSyspara", method = RequestMethod.POST)
    @ResponseBody
	public String saveSyspara(HttpServletRequest request,HttpServletResponse response,SysPara command){
	    LOG.info(String.format("------> calling SysParaCtrl.saveSysPara gjz = %s, mrz = %s", command.getGjz(), command.getMrz()));
        Map<String, Object> resultMap = new HashMap<String, Object>();
		SysPara syspara=(SysPara)command;

		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_XTCS,"","");

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}

			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="修改系统参数,关键值:"+syspara.getGjz()+"参数值:"+syspara.getMrz();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_XTCS,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.sysparaManager.saveSyspara(syspara,log);

			if(result.getFlag()==1){
			    resultMap.put("resultId", "00");
                resultMap.put("resultMsg", "[00K0210C1]:该系统参数保存成功！");
			}else{
				resultMap.put("resultId", result.getFlag());
                resultMap.put("resultMsg", result.getDesc1());
			}
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
            LOG.error("保存系统参数异常", errmsg);
			String s=StringUtil.transScriptStr(errmsg);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", s);
		}
		String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("保存系统参数结果:" + result);
        return result;
	}

	/**
	 * 部门参数管理页面————>主页面
	 */
	@RequestMapping(params = "method=forwardDepPara")
	public ModelAndView forwardDepPara(HttpServletRequest request,HttpServletResponse response){
		
		HttpSession session=request.getSession();
		if(this.gSystemService.checkUserRight(session,"00",Constants.MENU_BMCS,"")==false){
			request.setAttribute("message",Constants.SAFE_CTRL_MSG_101);
			return redirectMav("authErr");
		}
		return redirectMav("jsp/departmentparaMain");
	}


	private String getSqhpzl(String sqhpzl,String ywlx){
		String resultString="";
		int iPos1=sqhpzl.indexOf("["+ywlx+"]");
		int iPos2=sqhpzl.indexOf("[/"+ywlx+"]");
		if(iPos1>=0&&iPos2>=0){
			String sqzjcxString=sqhpzl.substring(iPos1+ywlx.length()+2,iPos2);
			String checkedString="";
			for(int i=0;i<allhpzlString.length;i++){
				if(sqzjcxString.indexOf(allhpzlString[i])>=0)
					checkedString="checked";
				else checkedString="";
				resultString+="<input type=checkbox name='zjcx_"+ywlx+"' id='zjcx_"+ywlx+"_"+allhpzlString[i]+"' value='"+allhpzlString[i]+"' "+checkedString+"/><label for='zjcx_"+ywlx+"_"+allhpzlString[i]+"'>"+allhpzlString[i]+"</label>";
			}
		}else{
			String checkedString="";
			for(int i=0;i<allhpzlString.length;i++){
				resultString+="<input type=checkbox name='zjcx_"+ywlx+"' id='zjcx_"+ywlx+"_"+allhpzlString[i]+"' value='"+allhpzlString[i]+"' "+checkedString+"/><label for='zjcx_"+ywlx+"_"+allhpzlString[i]+"'>"+allhpzlString[i]+"</label>";
			}
		}
		return resultString;
	}


	/**
	 * 非工作时间维护————>主界面
	 */
	@RequestMapping(params = "method=frd_fgzsjwh")
	public ModelAndView frd_fgzsjwh(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_FGZRWH,"","");
			sysService.genWebKey(request);

			String gnlbString=this.sysService.getUserCxdhGnstr(session,"00",Constants.MENU_FGZRWH);
			if(gnlbString.equals("")){
				throw new Exception("TERR02:无权访问该功能模块！");
			}
			String sysdatetime=gSysDateService.getSysDate(0,1);
			request.setAttribute("yyyy",sysdatetime.substring(0,4));
			request.setAttribute("mm",new Integer(sysdatetime.substring(5,7)).intValue()+"");
			request.setAttribute("gnid",gnlbString);
			return redirectMav("jsp/noworkday");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}

	/**
	 * 非工作时间维护————>更新统计年月,刷新主界面
	 */
	public void getNoworkday(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out=null;
		response.setContentType(contentType);
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,"00",Constants.MENU_FGZRWH,"","");

			String ny=request.getParameter("yymm");
			String ywlb=request.getParameter("ywlb");
			if(ywlb==null){
				ywlb="01";
			}
			String qsrq="";
			String jsrq="";
			if(ny==null){
				ny=gSysDateService.getSysDate(0,1);
				ny=ny.substring(0,4)+"-"+ny.substring(5,7)+"-";
			}else{
				ny=ny.substring(0,4)+"-"+ny.substring(4,6)+"-";
			}
			qsrq=ny+"01";
			jsrq=ny+"31";
			List list=this.sysparaManager.getFgzrwhList(qsrq,jsrq,ywlb);
			FrmNoworkday noworkday=null;
			String initScript="";
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					noworkday=(FrmNoworkday)list.get(i);
					String days=noworkday.getSdate().substring(8,10);
					if(days.substring(0,1).equals("0")) days=days.substring(1,2);
					initScript+="obj1=parent.document.getElementById('"+days+"');\n";
					initScript+="if(obj1!=null){obj1.parentElement.parentElement.bgColor='red';}\n";
				}
			}
			out.println("<script language=javascript>"+initScript+"</script>");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.transScriptStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	@RequestMapping(params = "method=changeNoworkday")
	public void changeNoworkday(HttpServletRequest request,HttpServletResponse response,FrmNoworkday command){
		FrmNoworkday noworkday=(FrmNoworkday)command;
		PrintWriter out=null;
		response.setContentType(contentType);
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,"00",Constants.MENU_FGZRWH,"","");

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			;

			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="修改非工作日表,日期:"+noworkday.getSdate()+"标记:"+noworkday.getBj()+"业务:"+noworkday.getYwlb();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_FGZRWH,"",cznr,FuncUtil.getRemoteIpdz(request),"");

			SysResult result=this.sysparaManager.saveNoworkday(noworkday,log);

			String logonErrMsg="";
			if(result.getFlag()==1){
				out.println("<script language=javascript>");
				String day=noworkday.getSdate().substring(8,10);
				if(day.substring(0,1).equals("0")) day=day.substring(1,2);
				out.println("obj1=parent.document.getElementById('"+day+"');");
				if(noworkday.getBj().equals("2")){
					out.println("if(obj1!=null){obj1.parentElement.parentElement.bgColor='#D3D1CC';}");
					logonErrMsg="[00R9550C1]:【"+day+"】号成功切换成工作日";
				}else{
					out.println("if(obj1!=null){obj1.parentElement.parentElement.bgColor='red';}");
					logonErrMsg="[00R9550C2]:【"+day+"】号成功切换成休息日";
				}
				out.println("parent.resultSave('"+result.getFlag()+"','"+logonErrMsg+"')</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc1()+"');</script>");
			}

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.transScriptStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}

	public void setInitNoworkday(HttpServletRequest request,HttpServletResponse response,FrmNoworkday command){
		FrmNoworkday noworkday=(FrmNoworkday)command;
		PrintWriter out=null;
		response.setContentType(contentType);
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		String year=request.getParameter("year");
		noworkday.setSdate(year);
		try{
			out=response.getWriter();
			this.sysService.doBeginCall(request,"00",Constants.MENU_FGZRWH,"","");

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			;

			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="修改非工作日表,重置所有休息日，业务:"+noworkday.getYwlb();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_FGZRWH,"",cznr,FuncUtil.getRemoteIpdz(request),"");

			SysResult result=this.sysparaManager.setInitNoworkday(noworkday,log);

			String logonErrMsg="";
			if(result.getFlag()==1){
				out.println("<script language=javascript>");
				out.println("parent.initCal()</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc1()+"');</script>");
			}

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.transScriptStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}

	/**
	 * 部门参数修改级别设置——>查询页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=queryDepParaJb")
	public ModelAndView queryDepParaJb(HttpServletRequest request,HttpServletResponse response,SysPara command){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			this.sysService.doBeginCall(request,"00",Constants.MENU_DEPPARASETUP,"","");
			if(command.getGjz()==null){
				command.setXtlb("");
				command.setXgjb("");
				command.setGjz("");
				command.setCsmc("");
			}
			List queryList = this.gSysparaCodeService.getDepSyspara(command);
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH_DEPT));
			request.setAttribute("queryList",queryList);
			request.setAttribute("sysservice",this.sysService);
			request.setAttribute("syspara",command);
			return redirectMav("jsp/sysparajbList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}
	/**
	 * 部门参数修改级别设置————>打开编辑页面
	 */
	@RequestMapping(params = "method=editDepParaJb")
	public ModelAndView editDepParaJb(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=(UserSession)gSystemService.getSessionUserInfo(session);
		try{
			String gjz=(String)request.getParameter("gjz");
			String xtlb=(String)request.getParameter("xtlb");

			this.sysService.doBeginCall(request,"00",Constants.MENU_DEPPARASETUP,"","");

			SysPara syspara=this.gSysparaCodeService.getSyspara(xtlb,"1",gjz);
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH_DEPT));
			request.setAttribute("syspara",syspara);
			sysService.genWebKey(request);
			return redirectMav("jsp/sysparajbEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}
	/**
	 * 部门参数修改级别设置————>保存
	 * @param request
	 * @param response
	 * @param command
	 */
	@RequestMapping(params = "method=saveDepParaJb")
	public void saveDepParaJb(HttpServletRequest request,HttpServletResponse response,SysPara command){

		SysPara syspara=(SysPara)command;

		PrintWriter out=null;
		response.setContentType(contentType);
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			out=response.getWriter();

			this.sysService.doBeginCall(request,"00",Constants.MENU_DEPPARASETUP,"","");

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}
			;

			SysUser sysUser=userSession.getSysuser();

			String sysdatetime=gSysDateService.getSysDate(0,3);
			String cznr="修改部门参数级别,关键值:"+syspara.getGjz()+"修改级别:"+syspara.getXgjb();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_DEPPARASETUP,"",cznr,FuncUtil.getRemoteIpdz(request),"");
			SysResult result=this.sysparaManager.saveDepSysparaJb(syspara,log);

			if(result.getFlag()==1){
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','[0091090C1]:该部门参数修改级别设置成功！');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc1()+"');</script>");
			}

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.transScriptStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}
	
	
	
	/**
	 * 部门参数管理————>部门列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=queryGlbmList")
	public ModelAndView queryGlbmList(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{

			this.sysService.doBeginCall(request,"00",Constants.MENU_BMCS,"","");
			request.setAttribute("departmenttree",this.departmentManager.getDepartmentTreeStr(userSession.getDepartment()));
			return redirectMav("jsp/departmentList");

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}

	/**
	 * 部门参数管理————>部门参数列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=queryDepParaList")
	public ModelAndView queryDepParaList(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=gSystemService.getSessionUserInfo(session);
		try{
			String glbm=request.getParameter("glbm");
			this.sysService.doBeginCall(request,"00",Constants.MENU_BMCS,"","");

			if(!StringUtil.checkBN(glbm)){
				glbm=userSession.getDepartment().getGlbm();
				request.setAttribute("department",userSession.getDepartment());
			}else{
				request.setAttribute("department",this.gDepartmentService.getDepartment(glbm));
			}
			String gnlbString=this.sysService.getUserCxdhGnstr(session,"00",Constants.MENU_BMCS);
			String xtyxms=this.gSysparaCodeService.getSysParaValue(Constants.SYS_XTLB_FRAME,"XTYXMS");
			String bmjb=userSession.getDepartment().getBmjb();
			if(xtyxms.equals("1")&&bmjb.equals("3")){
				bmjb="2";
			}
			List queryList=this.sysparaManager.getDepparasShow(bmjb,glbm,gnlbString);
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH_DEPT));
			request.setAttribute("querylist",queryList); // 返回查询list
			return redirectMav("jsp/departmentparaList");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}

	}
	
	/**
	 * 部门参数维护————>打开编辑页面
	 */
	@RequestMapping(params = "method=editdeppara")
	public ModelAndView editdeppara(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UserSession userSession=this.gSystemService.getSessionUserInfo(session);
		try{
			String glbm=(String)request.getParameter("glbm");
			String gjz=(String)request.getParameter("gjz");
			String xtlb=(String)request.getParameter("xtlb");

			this.sysService.doBeginCall(request,"00",Constants.MENU_BMCS,"","");

			SysPara syspara=this.gSysparaCodeService.getSyspara(xtlb,"1",gjz);
			SysPara syspara1=new SysPara();
			syspara1.setBz(syspara.getBz());
			syspara1.setCslx(syspara.getCslx());
			syspara1.setCsmc(syspara.getCsmc());
			syspara1.setCssm(syspara.getCssm());
			syspara1.setCssx(syspara.getCssx());
			syspara1.setDmlb(syspara.getDmlb());
			syspara1.setFzmc(syspara.getFzmc());
			syspara1.setGjz(syspara.getGjz());
			syspara1.setJyw(syspara.getJyw());
			syspara1.setMrz(syspara.getMrz());
			syspara1.setSfxs(syspara.getSfxs());
			syspara1.setSjgf(syspara.getSjgf());
			syspara1.setSjlx(syspara.getSjlx());
			syspara1.setXgjb(syspara.getXgjb());
			syspara1.setXssx(syspara.getXssx());
			syspara1.setXsxs(syspara.getXsxs());
			syspara1.setXtlb(syspara.getXtlb());
			String csz=this.gSysparaCodeService.getDeptParaValue(glbm,xtlb,gjz);
			if(csz==null){
				syspara1.setMrz("");
			}else{
				syspara1.setMrz(csz);
			}

			String cszxghtml=this.getCszxgHtmlString(syspara1,2,glbm);

			Department dep=this.gDepartmentService.getDepartment(glbm);
			request.setAttribute("syspara",syspara1);
			request.setAttribute("department",dep);
			request.setAttribute("cszxghtml",cszxghtml);
			sysService.genWebKey(request);
			return redirectMav("jsp/departmentparaEdit");
		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			request.setAttribute("message",errmsg);
			return redirectMav("error");
		}
	}	
	
	
	
	/**
	 * 部门参数维护————>保存部门参数
	 * @param request
	 * @param response
	 * @param command
	 */
	@RequestMapping(params = "method=saveDeppara")
	public void saveDeppara(HttpServletRequest request,HttpServletResponse response,SysparaValue command){

		SysparaValue sysparavalue=(SysparaValue)command;

		PrintWriter out=null;
		response.setContentType(contentType);
		UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
		try{
			out=response.getWriter();
			command.setCsbj("0");

			this.sysService.doBeginCall(request,"00",Constants.MENU_BMCS,"","");

			if(sysService.checkWebkey(request)==0){
				throw new Exception(Constants.SAFE_CTRL_MSG_102);
			}

			SysUser sysUser=userSession.getSysuser();
			String sysdatetime=this.gSysDateService.getSysDate(0,3);
			String cznr="修改部门参数,部门代码:"+sysparavalue.getGlbm()+"关键值:"+sysparavalue.getGjz()+"参数值:"+sysparavalue.getCsz();
			Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_BMCS,"",cznr,sysService.getRemoteIpdz(request),"");

			SysResult result=this.sysparaManager.saveDeppara(sysparavalue,log);

			if(result.getFlag()==1){
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','[0091020C1]:该部门参数保存成功！');</script>");
			}else{
				out.println("<script language=javascript>parent.resultSave('"+result.getFlag()+"','"+result.getDesc1()+"');</script>");
			}

		}catch(Exception e){
			String errmsg=StringUtil.getExpMsg(e);
			e.printStackTrace();
			String s=StringUtil.transScriptStr(errmsg);
			out.println("<script language=javascript>parent.resultSave('0','"+s+"');</script>");
		}
	}	
}
