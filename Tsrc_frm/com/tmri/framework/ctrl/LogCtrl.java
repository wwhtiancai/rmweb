package com.tmri.framework.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmri.framework.bean.Log;
import com.tmri.framework.service.LogManager;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
@Controller
@RequestMapping("/log.frm")
public class LogCtrl extends FrmCtrl {
    private String cdbh=Constants.MENU_RZCX;
    private String gnid="";
    
    @Autowired
    private LogManager logManager;
    
    @Autowired
    private GDepartmentService gDepartmentService;

    @RequestMapping(params = "method=userlogquery")
    public ModelAndView userlogquery(HttpServletRequest request,
            HttpServletResponse response, Log log) throws Exception {
        String mav="jsp/userlogquery";
        try {
            HttpSession session = request.getSession();
            Department depObj = gSystemService.getSessionUserInfo(session)
                .getDepartment();
            //获取
            request.setAttribute("cdbh", log.getCdbh());
            request.setAttribute("sysService",this.sysService);
            request.setAttribute("gSystemService",this.gSystemService);
            request.setAttribute("gDepartmentService",this.gDepartmentService);
            mav="jsp/userlogquery";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", StringUtil.getExpMsg(ex));
            mav="error";
        }
        return redirectMav(mav);
    }
    
    @RequestMapping(params = "method=logquery")
    public ModelAndView logquery(HttpServletRequest request,
            HttpServletResponse response, Object command) throws Exception {
        String mav="jsp/logquery";
        try {
            HttpSession session = request.getSession();
            Department depObj = gSystemService.getSessionUserInfo(session)
                .getDepartment();
            //确定可管理业务种类
            List gnidList = this.sysService.getUserCxdhGnlb(session, "00", this.cdbh);
            request.setAttribute("gnidList", _getGnidList(gnidList));
            
            //获取
            request.setAttribute("cdbh", this.cdbh);
            request.setAttribute("sysService",this.sysService);
            mav="jsp/logquery";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", StringUtil.getExpMsg(ex));
            mav="error";
        }
        return redirectMav(mav);
    }

    
    //20111102人员信息维护，管理部门变更
    @RequestMapping(params = "method=logresulttrack")
    public ModelAndView logresulttrack(HttpServletRequest request,
            HttpServletResponse response, Log obj) throws Exception {
        String mav="jsp/logresultbastrack";
        try {
            List querylist = this.logManager.getBaslogtrackList(obj);
            
            request.setAttribute("queryobj", obj);
            request.setAttribute("querylist", querylist);
            request.setAttribute("sysService", this.sysService);
            request.setAttribute("logManager", logManager);
            request.setAttribute("cdbh", this.cdbh);
            mav="jsp/logresultbastrack";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", StringUtil.getExpMsg(ex));
            mav="error";
        }
        return redirectMav(mav);
    } 
    
    
    //察看用户操作日志
    @RequestMapping(params = "method=logfrmresulttrack")
    public ModelAndView logfrmresulttrack(HttpServletRequest request,
            HttpServletResponse response, Log obj) throws Exception {
        String mav="jsp/logresultfrmtrack";
        try {
        	if (!StringUtil.checkBN(obj.getGlbm())) {
        		 HttpSession session = request.getSession();
                 Department depObj = gSystemService.getSessionUserInfo(session)
                         .getDepartment();
				obj.setGlbm(depObj.getGlbm());
			}
        	String bmjb=this.gDepartmentService.getDepartment(obj.getGlbm()).getBmjb();
        	String glbmlike=FuncUtil.getGlbmlike(obj.getGlbm(), bmjb, obj.getBhxj());
        	obj.setGlbm(glbmlike);
        	
            //分页
            PageController controller = new PageController(request);
            if(obj.getIsxls().equals("7")){
            	//获取系统参数，最大 excel最大行数
            	int MAXEXCEL=Integer.valueOf(gSysparaCodeService.getSysParaValue("00", "MAXEXCEL")).intValue();
            	controller.setPageSize(MAXEXCEL);
        		mav="jsp/logresultfrmtrackXls";
        	}
            List querylist = this.logManager.getFrmlogtrackList(obj,controller);
            request.setAttribute("queryobj", obj);
            request.setAttribute("querylist", querylist);
            request.setAttribute("sysService", this.sysService);
            request.setAttribute("gDepartmentService",this.gDepartmentService);
            request.setAttribute("logManager", logManager);
            request.setAttribute("cdbh", this.cdbh);
            
            request.setAttribute("gSystemService",this.gSystemService);
            
            request.setAttribute("gSysdateService",this.gSysDateService);
            
            request.setAttribute("controller", controller);
            
            if(obj.getIsxls().equals("6")){
            	mav="jsp/logresultfrmtrack";
            }else if(obj.getIsxls().equals("7")){
            	mav="jsp/logresultfrmtrackXls";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", StringUtil.getExpMsg(ex));
            mav="error";
        }
        return redirectMav(mav);
    }
    
    @RequestMapping(params = "method=logresult")
    public ModelAndView logresult(HttpServletRequest request,
            HttpServletResponse response, Log obj) throws Exception {
        String mav="jsp/logresult";
        try {
        	if (!StringUtil.checkBN(obj.getGlbm())) {
       		 HttpSession session = request.getSession();
                Department depObj = gSystemService.getSessionUserInfo(session)
                        .getDepartment();
				obj.setGlbm(depObj.getGlbm());
			}
        	
        	String bmjb=this.gDepartmentService.getDepartment(obj.getGlbm()).getBmjb();
        	String glbmlike=FuncUtil.getGlbmlike(obj.getGlbm(), bmjb, obj.getBhxj());
        	obj.setGlbm(glbmlike);
        	
        	
            //分页
            PageController controller = new PageController(request);
            if(obj.getIsxls().equals("1")){
            	//获取系统参数，最大 excel最大行数
            	int MAXEXCEL=Integer.valueOf(gSysparaCodeService.getSysParaValue("00", "MAXEXCEL")).intValue();
            	controller.setPageSize(MAXEXCEL);
        		mav="jsp/logresultloginfailXls";
        	}
            List querylist = this.logManager.getLogList(obj, controller);
            
            request.setAttribute("queryobj", obj);
            request.setAttribute("querylist", querylist);
            request.setAttribute("sysService", this.sysService);
            request.setAttribute("logManager", logManager);
            request.setAttribute("cdbh", this.cdbh);
            request.setAttribute("controller", controller);
            String rzlx=obj.getRzlx();
            if(rzlx.equals("1005")){
            	//登录失败日志
            	if(obj.getIsxls().equals("0")){
            		mav="jsp/logresultloginfail";
            	}else if(obj.getIsxls().equals("1")){
            		mav="jsp/logresultloginfailXls";
            	}
            }else if(rzlx.equals("1003")){
            	//异常报错日志
            	if(obj.getIsxls().equals("0")){
            		mav="jsp/logresulterr";
            	}else if(obj.getIsxls().equals("1")){
            		mav="jsp/logresulterrXls";
            	}
            }else if(rzlx.equals("0001")){
            	//车业务日志         	
            	if(obj.getIsxls().equals("0")){
            		mav="jsp/logresultveh";
            	}else if(obj.getIsxls().equals("1")){
            		mav="jsp/logresultvehXls";
            	}
            	
            }else if(rzlx.equals("0002")){
            	//车业务日志         	
            	if(obj.getIsxls().equals("0")){
            		mav="jsp/logresultdrv";
            	}else if(obj.getIsxls().equals("1")){
            		mav="jsp/logresultdrvXls";
            	}
            	            	
            }else if(rzlx.equals("1001")){
            	//登录日志      	
            	if(obj.getIsxls().equals("0")){
            		mav="jsp/logresultlogin";
            	}else if(obj.getIsxls().equals("1")){
            		mav="jsp/logresultloginXls";
            	}
            }else if(rzlx.equals("1002")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresult";     
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultXls";     
            	}
            }else if(rzlx.equals("0008")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
            		mav="jsp/logresultqueryerr";      
            	}else if(obj.getIsxls().equals("1")){
            		mav="jsp/logresultqueryerrXls";  
            	}            	
            }else if(rzlx.equals("1004")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresultws";    
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultwsXls";    
            	}            	
            }else if(rzlx.equals("0007")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresultbas";    
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultbasXls";    
            	}            	
            }else if(rzlx.equals("0003")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresultvio";                	
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultvioXls";                	
            	}            	
            }else if(rzlx.equals("0004")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresultacd";    
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultacdXls";    
            	}            	
            }else if(rzlx.equals("0005")){
            	//操作日志      	
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresultwxp";                	
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultwxpXls";                	
            	}            	
            }else{
            	//操作日志
            	if(obj.getIsxls().equals("0")){
                	mav="jsp/logresult";
            	}else if(obj.getIsxls().equals("1")){
                	mav="jsp/logresultXls";
            	}            	
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", StringUtil.getExpMsg(ex));
            mav="error";
        }
        return redirectMav(mav);
    }    
    
    @RequestMapping(params = "method=logedit")
    public ModelAndView logedit(HttpServletRequest request,
            HttpServletResponse response, Log obj) throws Exception {
        String mav="jsp/logedit";
        try {
            HttpSession session = request.getSession();
            Department depObj = gSystemService.getSessionUserInfo(session)
                    .getDepartment();
            
            Log log =new Log();
            
            request.setAttribute("log", log);
            request.setAttribute("sysService", sysService);
            request.setAttribute("logManager", logManager);
            request.setAttribute("cdbh", this.cdbh);
            

            mav="jsp/logedit";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", StringUtil.getExpMsg(ex));
            mav="error";
        }
        return redirectMav(mav);
    }    
 	
    
	private List _getGnidList(List gnidList){
		List result=new ArrayList();
		if(gnidList!=null){
			for(int i=0;i<gnidList.size();i++){
				Function function=(Function)gnidList.get(i);
				Code code=new Code();
				code.setDmz(function.getGnid());
				code.setDmsm1(function.getMc());
				result.add(code);
			}
		}
		return result;
	}
}
