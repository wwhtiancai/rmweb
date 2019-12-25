package com.tmri.share.frm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;

public interface GSystemService {
	
	public UserSession getSessionUserInfo(HttpSession session);
	
	public UserSession getSessionUserInfo(HttpServletRequest request);

	public boolean checkUserRight(HttpSession session, String xtlb,String cxdh, String gndh);
	
	public void doBeginCall(HttpServletRequest request,String className,String gnid) throws Exception;
	
	public void doEndCall(HttpServletRequest request,String className,String gnid,String czlx) throws Exception;
	
	public String getCdmc(String xtlb, String cdbh);
	
	public void setCheck(HttpServletRequest request,String id,String data) throws Exception;
	
	public void setCheck(HttpServletRequest request,String id,List data,String item) throws Exception;
	
	public boolean getCheck(HttpServletRequest request,String id,String value) throws Exception;
	
	public void getCheck(HttpServletRequest request) throws Exception;
	
	public List<SysUser> getUsers(String glbm) throws Exception;
	
	public SysUser getUser(String yhdh) throws Exception;
	
	public SysUser getPoliceman(String yhdh) throws Exception;
	
	public List<SysUser> getPolicemen(String glbm) throws Exception;
	
	public String getDistrictByDepartment(String glbm) throws Exception;
		
	public String getDistrictByDepartmentInScope(Department department) throws Exception;
	
	public String getProgramName(String xtlb, String cdbh) throws Exception;
	
	public String getFunctionName(String xtlb, String cdbh, String gnid) throws Exception;
	
	public String getUrl(String xtlb, String cdbh) throws Exception;
	
	public List getUserCxdhGnlb(HttpSession session, String xtlb, String cxdh);

	public List getUserCxdhGnlb_filter(HttpSession session, String xtlb,String cxdh, String filterGnid);
		
	
}
