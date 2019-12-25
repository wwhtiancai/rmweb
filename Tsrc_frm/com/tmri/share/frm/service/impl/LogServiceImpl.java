package com.tmri.share.frm.service.impl;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.util.WebUtils;

import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.GSysDateDao;
import com.tmri.share.frm.dao.GSystemDao;
import com.tmri.share.frm.service.LogService;

@Service
public class LogServiceImpl implements LogService{

	@Autowired
	private GSystemDao gSystemDao;
	@Autowired
	private GSysDateDao gSysDateDao;
	
	public void saveErrLog(HttpServletRequest request,String className,String gnid,String message){
		if(message!=null&&message.length()>0){
			if(request.getParameter("method")==null){
				System.out.println("TERR02:功能菜单不存在，连接已失效，请重新登陆系统！");
				return;
			}
			String[] xc=gSystemDao.getMenuId(className,request.getParameter("method"));
			if(xc==null){
				System.out.println("TERR03:功能菜单不存在，连接已失效，请重新登陆系统！");
				return;
			}
			if(gnid==null||gnid.equals("")||gnid.toLowerCase().equals("null"))
				gnid="0000";
		}
	}
		
	public RmLog getRmLog(HttpServletRequest request,String className,String gnid,String message,HashMap keyMap) throws Exception{
		if(request.getParameter("method")==null)
			throw new Exception("TERR02:功能菜单不存在，连接已失效，请重新登陆系统！");
		String[] xc=gSystemDao.getMenuId(className,request.getParameter("method"));
		if(xc==null)
			throw new Exception("TERR03:功能菜单不存在，连接已失效，请重新登陆系统！");
		RmLog log=new RmLog();
		UserSession userSession=(UserSession)WebUtils.getSessionAttribute(request,"userSession");
		log.setGlbm(userSession.getDepartment().getGlbm());
		log.setYhdh(userSession.getSysuser().getYhdh());
		log.setCzry(userSession.getSysuser().getXm());
		log.setCzsj(gSysDateDao.getDBDateTime("0"));
		log.setCdbh(xc[1]);
				
		//xuxd 保存主键信息 
		if(keyMap!=null){
			String zjxx = (String)keyMap.get("zjxx");
			if(zjxx!=null&&!zjxx.equals("")){
				log.setZjxx(zjxx);
			}
			String kkbh = (String)keyMap.get("kkbh");
			if(kkbh!=null&&!kkbh.equals("")){
				log.setKkbh(kkbh);
			}
			String bkxh = (String)keyMap.get("bkxh");
			if(bkxh!=null&&!bkxh.equals("")){
				log.setBkxh(bkxh);
			}
			String yjxh = (String)keyMap.get("yjxh");
			if(yjxh!=null&&!yjxh.equals("")){
				log.setYjxh(yjxh);
			}
		}
		if(gnid==null||gnid.equals("")||gnid.toLowerCase().equals("null"))
			log.setGnid("0000");
		else
			log.setGnid(gnid);
		if(message==null||message.length()<1){
			String t="";
			Enumeration param=request.getParameterNames();
			while(param.hasMoreElements()){
				String pname=param.nextElement().toString();
				if(!pname.equalsIgnoreCase("page")&&!pname.equalsIgnoreCase("method")&&!pname.equalsIgnoreCase("toPage")&&!pname.equalsIgnoreCase("formAction")&&!pname.equalsIgnoreCase("md5")&&!pname.equalsIgnoreCase("totalRowsAmount")&&!pname.equalsIgnoreCase("totalPages")){
					if(request.getParameter(pname)!=null&&request.getParameter(pname).length()>0)
						t+=pname+":"+request.getParameter(pname)+";";
				}
			}
			log.setCznr(t);
		}else{
			log.setCznr(message);
		}
		log.setIp(userSession.getSysuser().getIp());
		return log;
	}
}
