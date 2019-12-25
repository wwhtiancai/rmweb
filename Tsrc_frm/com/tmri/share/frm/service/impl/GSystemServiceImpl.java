package com.tmri.share.frm.service.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.GSystemDao;
import com.tmri.share.frm.service.GSystemService;
import com.tmri.share.frm.util.Constants;


@Service
public class GSystemServiceImpl implements GSystemService {
	@Autowired
	private GSystemDao gSystemDao;

	
	public UserSession getSessionUserInfo(HttpSession session) {
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		return userSession;
	}

	// chiva
	public UserSession getSessionUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		return userSession;
	}


	public boolean checkUserRight(HttpSession session, String xtlb,String cxdh, String gndh) {
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				if (gndh == null || gndh.equals("")
						|| gndh.toLowerCase().equals("null")) {
					return true;
				} else {
					String tmpgndh = ((Program) rightsobj
							.get(xtlb + "-" + cxdh)).getGnid();
					if (tmpgndh.indexOf(gndh) >= 0) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public synchronized void doBeginCall(HttpServletRequest request,String className,String gnid) throws Exception{
		HttpSession session=request.getSession();
		if(session==null)
			throw new Exception("TERR01:系统登陆超时，连接已失效，请重新登陆系统！");
		if(session.getAttribute("rand_code_state").equals("1"))
			throw new Exception("TERR11:系统登陆超时，连接已失效，请重新登陆系统！");
		if(request.getParameter("method")==null)
			throw new Exception("TERR02:功能菜单不存在，连接已失效，请重新登陆系统！");
		String[] xc=gSystemDao.getMenuId(className,request.getParameter("method"));
		if(xc==null)
			throw new Exception("TERR03:功能菜单不存在，连接已失效，请重新登陆系统！");
		if(checkUserRight(session,xc[0],xc[1],gnid)==false){
			throw new Exception("TERR04:无权访问该功能模块！");
		}
		session.setAttribute("_BeginCall",String.valueOf(System.currentTimeMillis()));
	}
	
	public synchronized void doEndCall(HttpServletRequest request,String className,String gnid,String czlx) throws Exception{
		if(request.getParameter("method")==null)
			throw new Exception("TERR02:功能菜单不存在，连接已失效，请重新登陆系统！");
		String[] xc=gSystemDao.getMenuId(className,request.getParameter("method"));
		if(xc==null)
			throw new Exception("TERR03:功能菜单不存在，连接已失效，请重新登陆系统！");
		if(czlx==null||czlx.length()<1)
			czlx=Constants.P_request;
	}

	public String getCdmc(String xtlb, String cdbh){
		return this.gSystemDao.getCdmc(xtlb, cdbh);
	}
	
	
	public void setCheck(HttpServletRequest request,String id,String data) throws Exception{
		HttpSession session=request.getSession();
		if(session!=null){
			HashMap<String,String> map=null;
			if(session.getAttribute("systems")==null){
				map=new HashMap<String,String>();
			}else{
				map=(HashMap<String,String>)session.getAttribute("systems");
			}
			map.put(id+":"+data.trim(),null);
			session.setAttribute("systems",map);
		}
	}
	
	public void setCheck(HttpServletRequest request,String id,List data,String item) throws Exception{
		HttpSession session=request.getSession();
		if(session!=null&&data!=null){
			HashMap<String,String> map=null;
			if(session.getAttribute("systems")==null){
				map=new HashMap<String,String>();
			}else{
				map=(HashMap<String,String>)session.getAttribute("systems");
			}
			if(item.indexOf("+")!=-1){
				String[] ids=item.split("+");
				Method m=null;
				String t="";
				for(Object obj:data){
					t="";
					for(String ss:ids){
						m=obj.getClass().getMethod("get"+ss.substring(0,1).toUpperCase()+ss.substring(1),null);
						t+=String.valueOf(m.invoke(obj,null));
					}
					map.put(id+":"+t.trim(),null);
				}
			}else{
				for(Object obj:data){
					Method m=obj.getClass().getMethod("get"+item.substring(0,1).toUpperCase()+item.substring(1),null);
					map.put(id+":"+String.valueOf(m.invoke(obj,null)).trim(),null);
				}					
			}
			session.setAttribute("systems",map);
		}
	}
	
	public boolean getCheck(HttpServletRequest request,String id,String value) throws Exception{
		HttpSession session=request.getSession();
		if(session==null||session.getAttribute("systems")==null){
			throw new Exception("请求逻辑校验失败，没有会话信息！");
		} else {
			HashMap<String,String> map=(HashMap<String,String>)session.getAttribute("systems");
			if(map.containsKey(id+":"+value.trim())){
				return true;
			} else {
				throw new Exception("请求逻辑校验失败，操作超出授权范围！");
			}
		}
	}
	
	public void getCheck(HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession();
		if(session==null||session.getAttribute("systems")==null) {
			System.out.println("It hasn't any hashmap.");
		} else {
			HashMap<String,String> map=(HashMap<String,String>)session.getAttribute("systems");
			Set<String> key=map.keySet();
            for (Iterator it=key.iterator();it.hasNext();) {
                System.out.println((String)it.next());
            }
		}
	}

	public List<SysUser> getUsers(String glbm) throws Exception{
		return this.gSystemDao.getUsers(glbm);
	}
	public SysUser getUser(String yhdh) throws Exception{
		return this.gSystemDao.getUser(yhdh);
	}
	public List<SysUser> getPolicemen(String glbm) throws Exception{
		return this.gSystemDao.getPolicemen(glbm);
	}
	public SysUser getPoliceman(String yhdh) throws Exception{
		return this.gSystemDao.getPoliceman(yhdh);
	}
	public String getDistrictByDepartment(String glbm) throws Exception {
		return this.gSystemDao.getDistrictByDepartment(glbm);
	}
	public String getDistrictByDepartmentInScope(Department department) throws Exception{
		return this.gSystemDao.getDistrictByDepartmentInScope(department);
	}
	
	public String getProgramName(String xtlb, String cdbh) throws Exception {
		return this.gSystemDao.getProgramName(xtlb, cdbh);
	}	
	
	public String getFunctionName(String xtlb, String cdbh, String gnid) throws Exception{
		return this.gSystemDao.getFunctionName(xtlb, cdbh, gnid);
	}
	
	public String getUrl(String xtlb, String cdbh) throws Exception {
		return this.gSystemDao.getUrl(xtlb, cdbh);
	}
	
	public List getUserCxdhGnlb(HttpSession session, String xtlb, String cxdh) {
		String gnlbString = "";
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				gnlbString = ((Program) rightsobj.get(xtlb + "-" + cxdh))
						.getGnid();
			}
		}
		if (!(gnlbString == null || gnlbString.equals("") || gnlbString
				.toLowerCase().equals("null"))) {
			String[] gnlbStrings = gnlbString.split(",");
			Arrays.sort(gnlbStrings);
			List list = new Vector();
			Function function = null;
			for (int i = 0; i < gnlbStrings.length; i++) {
				function = gSystemDao.getOneFunction(xtlb, cxdh,
						gnlbStrings[i]);
				if (function != null)
					list.add(function);
			}
			return list;
		} else {
			return null;
		}
	}

	public List getUserCxdhGnlb_filter(HttpSession session, String xtlb,
			String cxdh, String filterGnid) {
		String gnlbString = "";
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				gnlbString = ((Program) rightsobj.get(xtlb + "-" + cxdh))
						.getGnid();
			}
		}
		if (!(gnlbString == null || gnlbString.equals("") || gnlbString
				.toLowerCase().equals("null"))) {
			String[] gnlbStrings = gnlbString.split(",");
			List list = new Vector();
			Function function = null;
			for (int i = 0; i < gnlbStrings.length; i++) {
				function = gSystemDao.getOneFunction(xtlb, cxdh,
						gnlbStrings[i]);
				if (function != null) {
					if (filterGnid != null && !filterGnid.equals("")) {
						if (filterGnid.indexOf(function.getGnid()) < 0) {
							list.add(function);
						}
					} else {
						list.add(function);
					}
				}
			}
			return list;
		} else {
			return null;
		}
	}
	
}
