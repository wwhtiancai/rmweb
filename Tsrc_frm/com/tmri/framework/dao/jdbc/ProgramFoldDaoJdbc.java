package com.tmri.framework.dao.jdbc;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.Fold;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.dao.ProgramFoldDao;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.FrmJdbcTemplate;
/**
 * 程序 目录 存储 frm_fold frm_program 实现
 * 
 * @author long
 * 
 */
@Repository
public class ProgramFoldDaoJdbc extends FrmDaoJdbc implements ProgramFoldDao{
//	private String MEM_FRM_FUNCTION="FrmFunction";
	public void setJdbcTemplate(FrmJdbcTemplate jdbcTemplate){
		this.jdbcTemplate=jdbcTemplate;
	}


	public String getGnmc(String xtlb,String cdbh,String gnid){
		if(gnid==null||gnid.toLowerCase().equals("null")||gnid.equals("")){
			return "";
		}
		Function function=getOneFunction(xtlb,cdbh,gnid);
		String gnmc = "";
		if(function!=null)
		{
			gnmc = function.getMc();
		}
		if(gnmc==null||gnmc.toLowerCase().equals("null"))
		{
			gnmc="";
		}
		return gnmc;
	}
	public String getGnmc(String xtlb,String gnid){
		String gnmc = "";
		gnmc = getGnmc(xtlb,"",gnid);
		return gnmc;
	}		
	public List getFoldList2(SysUser sysUser,HashMap paras){
		String sql = null;
		String XTYXMS = (String)paras.get("XTYXMS");
		String DEBUG = (String)paras.get("DEBUG");
		if(sysUser.getQxms()!=null&&sysUser.getQxms().equals("1"))
		{
			sql=" select distinct v4.* from frm_program v1,frm_userrole v2,frm_rolemenu v3,frm_fold v4 where ";
			sql += " v2.yhdh=? ";
			if(sysUser.getXtgly()!=null&&sysUser.getXtgly().equals("1")){
				sql += " and v1.glysy='1'";
			}
			if(DEBUG==null||DEBUG.equals("0")){
				if(XTYXMS.equals("1")||XTYXMS.equals("2")){
					sql = sql + " and (v1.yxsjk='1' or v1.yxsjk='0')";
				}else{
					sql = sql + " and (v1.yxsjk='2' or v1.yxsjk='0')";
				}
			}
			sql += " and v1.cxlx in ('1','5') and v2.jsdh=v3.jsdh and v3.cdbh=v1.cdbh and v3.xtlb=v1.xtlb and v1.mldh=v4.mldh  order by v4.sxh";			
		}else{
			sql=" select distinct v3.* from frm_program v1,frm_usermenu v2,frm_fold v3 where ";

			sql += " v2.yhdh=? ";
			if(sysUser.getXtgly()!=null&&sysUser.getXtgly().equals("1")){
				sql += " and v1.glysy='1'";
			}
			if(XTYXMS.equals("1")||XTYXMS.equals("2")){
				sql = sql + " and (v1.yxsjk='1' or v1.yxsjk='0')";
			}else{
				sql = sql + " and (v1.yxsjk='2' or v1.yxsjk='0')";
			}
			sql += " and v1.cxlx in ('1','5') and v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v1.mldh=v3.mldh order by v3.sxh";		
		}				
		Object[] paraObjects=new Object[]{sysUser.getYhdh()};
		List foldList=jdbcTemplate.queryForList(sql,paraObjects,Fold.class);
		return foldList;
	}
	public List getTopFoldList(){
		String sql="select * from frm_fold where sjmldh='0000' order by sxh";
		List foldList=jdbcTemplate.queryForList(sql,Fold.class);
		return foldList;
	}

	public Fold getFold(String mldh){
		String sql="select * from frm_fold where mldh='"+mldh+"'";
		Fold fold=null;
		fold=(Fold)jdbcTemplate.queryForBean(sql,Fold.class);
		return fold;
	}
	
	public List getFoldList(String yhdh){
		String sql=" select v3.* from frm_program v3,(select v2.xtlb,v2.cdbh from frm_userrole v1,frm_rolemenu v2 where v1.jsdh=v2.jsdh	and v1.yhdh=:yhdh union select v1.xtlb,v1.cdbh from frm_usermenu v1 where v1.yhdh=:yhdh) v4 where v3.xtlb=v4.xtlb and v3.cdbh=v4.cdbh order by sxh";
		sql="select n.mldh,n.mlmc,n.sjmldh,n.sxh,n.bz from ("+sql+") m,(select * from frm_fold) n where m.mldh=n.mldh group by n.sxh,n.mldh,n.mlmc,n.sjmldh,n.bz order by mldh";
		HashMap map = new HashMap();
		map.put("yhdh",yhdh);
		List foldList=jdbcTemplate.queryForList(sql,map,Fold.class);
		return foldList;
	}

	public List getProgramList(SysUser sysuser,HashMap paras){
		String xtlbs=(String)SystemCache.getInstance().getValue(Constants.SYS_SYSTEM_XTLB);
		String sqlxtlb=StringUtil.getXtlbsql(xtlbs, "t");
		
		String sql="";
		String XTYXMS = (String)paras.get("XTYXMS");
		String DEBUG = (String)paras.get("DEBUG");
		String RESTFUL = (String)paras.get("RESTFUL");
		if(sysuser.getQxms()!=null&&sysuser.getQxms().equals("1"))
		{
			sql=" select v3.*,v4.gnlb gnid,'1' flag from frm_program v3,(select distinct v2.xtlb,v2.cdbh,v2.gnlb from frm_userrole v1,frm_rolemenu v2 where v1.jsdh=v2.jsdh	and v1.yhdh=:yhdh ) v4 where v3.cdbh=v4.cdbh and v3.xtlb=v4.xtlb "
				+ (Boolean.valueOf(RESTFUL) ? "and v3.cxlx = '6' " : "and v3.cxlx<> '4' ");
			if(sysuser.getXtgly()!=null&&sysuser.getXtgly().equals("1")){
				sql = sql + " and glysy='1'";
			}
			if(DEBUG==null||DEBUG.equals("0")){
				if(XTYXMS.equals("1")||XTYXMS.equals("2")){
					sql = sql + " and (v3.yxsjk='1' or v3.yxsjk='0')";
				}else{
					sql = sql + " and (v3.yxsjk='2' or v3.yxsjk='0')";
				}
			}
			sql = sql + " order by sxh";
			sql="select m.* from ("+sql+") m,(select mldh,sxh from frm_fold) n where m.mldh=n.mldh  order by m.mldh, m.Sxh";
			sql="Select t.* From (" + sql +") t ,frm_fold x Where t.mldh=x.mldh Order By x.sxh,t.mldh,t.mldh, t.Sxh";
		}else{
			sql=" select v3.*,v4.gnlb gnid,'1' flag from frm_program v3,(select v1.xtlb,v1.cdbh,v1.gnlb from frm_usermenu v1 where v1.yhdh=:yhdh) v4 where v3.cdbh=v4.cdbh and v3.xtlb=v4.xtlb";
			if(sysuser.getXtgly()!=null&&sysuser.getXtgly().equals("1")){
				sql = sql + " and glysy='1'";
			}			
			if(DEBUG==null||DEBUG.equals("0")){
				if(XTYXMS.equals("1")||XTYXMS.equals("2")){
					sql = sql + " and (v3.yxsjk='1' or v3.yxsjk='0')";
				}else{
					sql = sql + " and (v3.yxsjk='2' or v3.yxsjk='0')";
				}
			}
			sql = sql + " order by sxh";
			sql="select m.* from ("+sql+") m,(select mldh,sxh from frm_fold) n where m.mldh=n.mldh  order by m.mldh, m.Sxh";
			sql="Select t.* From (" + sql +") t ,frm_fold x  Where t.mldh=x.mldh "+sqlxtlb+" Order By x.sxh,t.mldh,t.mldh, t.Sxh";
		}
		
		
		HashMap map = new HashMap();
		map.put("yhdh",sysuser.getYhdh());
		List programList=jdbcTemplate.queryForList(sql,map,Program.class);
		return programList;
	}
	  /**
	   * 获取用户自定义桌面信息
	   * 
	   * @param yhdh
	   * @return
	   * @author xiaodong_xu
	   */
	public List getUserDeskList(String yhdh,Hashtable rightsobj){
		String xtlbs=(String)SystemCache.getInstance().getValue(Constants.SYS_SYSTEM_XTLB);
		String sqlxtlb=StringUtil.getXtlbsql(xtlbs, "b");
		
		List deskList = null;
		String sql=" Select a.yhdh,a.cdbh,b.cxmc,b.mldh,b.ymdz,b.tbmc,c.mlmc,a.xtlb From frm_userdesk a ,frm_program b,frm_fold c "
		+" Where a.yhdh='" + yhdh + "' And a.xtlb=b.xtlb and a.cdbh=b.cdbh and b.mldh=c.mldh "+sqlxtlb
		+" Order By c.sxh,b.sxh";
		List userDeskList=jdbcTemplate.queryForList(sql,UserDesk.class);
		if (rightsobj != null) {
	          if (userDeskList != null) {
	            for (int i = 0; i < userDeskList.size(); i++) {
	              UserDesk userdesk = (UserDesk) userDeskList.get(i);
	              if ((Program) rightsobj.get(userdesk.getXtlb() + "-" +userdesk.getCdbh()) != null) {
	                if (deskList == null) {
	                  deskList = new Vector();
	                }
	                deskList.add(userdesk);
	              }
	            }
	          } else {
	            deskList = null;
	          }
	        } else {
	          deskList = null;
	        }
		return deskList;
	}
	public Function getOneFunction(String xtlb,String cdbh,String gnid){
		Function function=null;
		String key="function:"+xtlb + "-" + gnid;
		Hashtable tab=(Hashtable)SystemCache.getInstance().getValue(Constants.MEM_FRM_FUNCTION);
		function = (Function)tab.get(key);
		if(function==null){
			String strSql="select * from frm_function where xtlb=:xtlb and gnid=:gnid";
			HashMap map = new HashMap();
			map.put("xtlb",xtlb);
			map.put("gnid",gnid);
			function=(Function)jdbcTemplate.queryForSingleObject(strSql,map,Function.class);
			if(function!=null)
				tab.put(key,function);
		}
		return function;
	}
	
	
	//zhoujn 20110311 根据功能名称获取列表
	public List getProgramList(String gnmc) throws Exception {
		String strsql=" select v1.xtlb,Frm_Comm_Pkg.Getdmsm1('00','0001',v1.xtlb) xtmc,"
			+ " v1.mldh,Frm_Comm_Pkg.Getmlmc(v1.mldh) mlmc,v1.cdbh,v1.cxmc,nvl(v2.gnid,'') gnid,nvl(v2.mc,'') gnmc"
			+ " from frm_program v1,frm_function v2"
			+ " where v1.cdbh=v2.cdbh(+)"
			+ " and   v1.xtlb=v2.xtlb(+)"
			+ " and   (v1.cxmc like '%"+gnmc+"%' or nvl(v2.mc,'') like '%"+gnmc+"%')";
		strsql=strsql+" order by  v1.xtlb,v1.mldh,v1.cdbh";
		List programList=jdbcTemplate.queryForList(strsql,Program.class);
		return programList;
	}
	public Program getOneProgram(String xtlb,String cdbh){
		String 	sql=" select v3.*,'1' flag from frm_program v3 where xtlb=:xtlb and cdbh=:cdbh";
		HashMap map = new HashMap();
		map.put("xtlb",xtlb);
		map.put("cdbh",cdbh);
		return (Program)jdbcTemplate.queryForSingleObject(sql,map,Program.class);
	}
}
