package com.tmri.framework.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.Fold;
import com.tmri.framework.bean.FrmUserprog;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.framework.dao.RoleprogDao;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;

@Repository
public class RoleprogDaoJdbc extends FrmDaoJdbc implements RoleprogDao {
	//FrmUserprog frmuserprog
	//String jb,String xtlb
	public List getFunctionList(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select * From frm_function v1 Where exists (Select * From frm_program  v2 "
				+"Where v1.xtlb=v2.xtlb and  v1.cdbh=v2.cdbh and v2.cxsx Like :cxsx ";
		map.put("cxsx","%"+frmuserprog.getCxsx()+"%" );

		if(!frmuserprog.getXtlb().equals("")){
			strSql += " and instr(:xtlb,v2.xtlb)>0";
			map.put("xtlb",frmuserprog.getXtlb() );
		}
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and Frm_Comm_Pkg.Getyxsjk(v2.yxsjk) Like :yxsjk ";
			map.put("yxsjk","%"+frmuserprog.getYxsjk()+"%" );
		}		
		strSql += ") Order By v1.cdbh,v1.sxh,v1.gnid";
		List list = jdbcTemplate.queryForList(strSql, map, Function.class);
		return list;
	};
	
	
	//20100223 zhoujn 获取角色代号的function 
	//String xtyxms,String jscj,String jsdh
	public List getFunctionListByJsdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		//修改，gnid只列上级角色权限中存在的 jianghl 2010-06-17
		String strSql = "Select distinct a.* From frm_function a,"
		+ "(Select v1.xtlb,v1.cdbh,v1.gnlb,v2.cxsx,v2.yxsjk,v2.cxlx From frm_rolemenu v1,frm_program v2 "
		+ " Where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v1.jsdh in ("+getJsdh(frmuserprog.getJsdh())+")) b"
		+ " where a.xtlb=b.xtlb and a.cdbh=b.cdbh and instr(b.gnlb,a.gnid)>0";
		
		if(!frmuserprog.getCxsx().equals("")){
			strSql+=" and (";
			String[] cxsx = frmuserprog.getCxsx().split("A");
			boolean flag=false;
			for(int i=0;i<cxsx.length;i++){
				if(flag){
					strSql+= " or b.cxsx Like '%"+cxsx[i]+"%'";
				}else{
					strSql+= " b.cxsx Like '%"+cxsx[i]+"%'";
				}
				flag=true;
			}
			strSql+=")";
		}
		//20110809
		if(frmuserprog.getJssx().equals("2")){
			//操作角色
			strSql+=" and (b.cxlx='1' or b.cxlx='2' or b.cxlx='3' or b.cxlx='5' or b.cxlx = '6')";
		}else if(frmuserprog.getJssx().equals("3")){
			//atm角色
			strSql+=" and b.cxlx='4'";
		}

		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(b.yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}
		strSql += "  Order By a.cdbh,a.sxh,a.gnid";
		List list = jdbcTemplate.queryForList(strSql, map,Function.class);
		return list;
	}
	
	//获取用户代号
	//String xtyxms,String jscj,String yhdh
	public List getFunctionListByYhdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql="";
		/*
		if(frmuserprog.getYhdh().equals(Constants.SYS_SUPER_USER)){
			strSql = "Select * From frm_function Order By cdbh,sxh,gnid";
		}else{
		*/	
		strSql = "Select * From frm_function w Where exists ("
				+ " Select v1.cdbh From frm_rolemenu v1,frm_program v2,frm_user_grantrole v3,frm_role v4 "
				+ " Where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v4.jsdh=v3.jsdh and v1.jsdh=v4.jsdh and v3.yhdh= :yhdh";
		map.put("yhdh", frmuserprog.getYhdh() );

		if(!frmuserprog.getYxsjk().equals("")){
			strSql += " and   v2.cxsx Like :cxsx ";
			map.put("cxsx", "%"+frmuserprog.getCxsx()+"%");
		}
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(v2.yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}		
		strSql += " and   v1.cdbh=w.cdbh";
		strSql += " and   instr(v1.gnlb,w.gnid)>0";
		
		strSql += " ) Order By cdbh,sxh,gnid";
		//}
		List list = jdbcTemplate.queryForList(strSql, map,Function.class);
		return list;
	}	
	
	//自由角色，功能id
	public List getFunctionListByYhdh(String yhdh) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql="";
		strSql=" select * from frm_function  v1"
			+ " where exists("
			+ " select * from frm_usermenu v2"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh"
			+ " and   instr(v2.gnlb,v1.gnid)>=0"
			+ " and   v2.yhdh=:yhdh";
		map.put("yhdh", yhdh);
		strSql += " ) Order By cdbh,sxh,gnid";
		List list = jdbcTemplate.queryForList(strSql, map,Function.class);
		return list;
	}	
	
	
	//FrmUserprog frmuserprog
	//String jb
	public List getFoldList(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select * From frm_fold Where mldh In ("
			+"Select mldh From frm_program Where cxsx Like :cxsx ";
		map.put("cxsx","%"+frmuserprog.getCxsx()+"%");
		
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}		
		strSql += " ) Order By sxh";
		List list = jdbcTemplate.queryForList(strSql,map, Fold.class);
		return list;
	};
	
	//20100223 zhoujn 获取角色代号的fold 
	//String xtyxms,String jscj,String jsdh
	public List getFoldListByJsdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select distinct * From frm_fold Where mldh In ("
			+ " select v2.mldh from frm_rolemenu v1,frm_program v2"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v1.jsdh in ("+getJsdh(frmuserprog.getJsdh())+")";
		if(!frmuserprog.getCxsx().equals("")){
			strSql+="and (";
			String[] cxsx = frmuserprog.getCxsx().split("A");
			boolean flag=false;
			for(int i=0;i<cxsx.length;i++){
				if(flag){
					strSql+= " or v2.cxsx Like '%"+cxsx[i]+"%'";
				}else{
					strSql+= " v2.cxsx Like '%"+cxsx[i]+"%'";
				}
				flag=true;
			}
			strSql+=")";
		}
		
		//20110809
		if(frmuserprog.getJssx().equals("2")){
			//操作角色
			strSql+=" and (v2.cxlx='1' or v2.cxlx='2' or v2.cxlx='3' or v2.cxlx='5' or v2.cxlx = '6')";
		}else if(frmuserprog.getJssx().equals("3")){
			//atm角色
			strSql+=" and v2.cxlx='4'";
		}
			
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(v2.yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}		
		strSql+= " ) Order By sxh";
		List list = jdbcTemplate.queryForList(strSql,map, Fold.class);
		return list;
	}
	
	//String xtyxms,String jscj,String yhdh
	//根据用户编号，用户甄别超级用户
	public List getFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "";

		strSql = "Select * From frm_fold Where mldh In ("
			+ " select v2.mldh from frm_rolemenu v1,frm_program v2,frm_user_grantrole v3,frm_role v4"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v4.jsdh=v3.jsdh and v1.jsdh=v4.jsdh and v3.yhdh= :yhdh";
		map.put("yhdh", frmuserprog.getYhdh());
		
		if(!frmuserprog.getCxsx().equals("")){
			strSql+= " and   v2.cxsx Like :cxsx ";
			map.put("cxsx","%"+frmuserprog.getCxsx()+"%");
		}
		
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(v2.yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}		
		strSql+= " ) Order By sxh";
		//}	
		List list = jdbcTemplate.queryForList(strSql,map, Fold.class);
		return list;
	}	
	
	//获取用户操作权限
	public List getUserFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "";

		strSql = "Select * From frm_fold Where mldh In ("
			+ " select v2.mldh from frm_rolemenu v1,frm_program v2,frm_userrole v3,frm_role v4"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v4.jsdh=v3.jsdh and v1.jsdh=v4.jsdh and v3.yhdh= :yhdh";
		map.put("yhdh", frmuserprog.getYhdh());
		strSql+= " ) Order By sxh";
		List list = jdbcTemplate.queryForList(strSql,map, Fold.class);
		return list;
	}	
	
	
	
	
	
	//根据自由权限获取用户的fold
	public List getFoldListByYhdh(String yhdh) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "";

		strSql = "Select * From frm_fold Where mldh In ("
			+ " select v2.mldh from frm_usermenu v1,frm_program v2"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v1.yhdh= :yhdh";
		map.put("yhdh", yhdh );
		strSql+= " ) Order By sxh";
		List list = jdbcTemplate.queryForList(strSql,map, Fold.class);
		return list;
	}	
	

	//FrmUserprog frmuserprog
	//String jb,String xtlb
	public List getProgramList(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select * From frm_program Where cxsx Like :cxsx";
		map.put("cxsx","%"+frmuserprog.getCxsx()+"%");
		//zhoujn 20100520增加 xtlb 为空判断
		if(!frmuserprog.getXtlb().equals("")){
			 strSql += " and instr('" + frmuserprog.getXtlb() + "',xtlb)>0";
		}
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}		
		strSql += " Order By mldh,sxh";
		List list = jdbcTemplate.queryForList(strSql,map, Program.class);
		return list;
	};
	//20100223 zhoujn 获取角色代号的program 
	//String xtyxms,String jscj,String jsdh
	public List getProgramListByJsdh2(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select distinct * From frm_program Where (xtlb,cdbh) In ("
			+ " select v1.xtlb,v1.cdbh from frm_rolemenu v1,frm_program v2"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and v1.jsdh in ("+getJsdh(frmuserprog.getJsdh())+")";
		
		if(!frmuserprog.getCxsx().equals("")){
			strSql+="and (";
			String[] cxsx = frmuserprog.getCxsx().split("A");
			boolean flag=false;
			for(int i=0;i<cxsx.length;i++){
				if(flag){
					strSql+= " or v2.cxsx Like '%"+cxsx[i]+"%'";
				}else{
					strSql+= " v2.cxsx Like '%"+cxsx[i]+"%'";
				}
				flag=true;
			}
			strSql+=")";
		}	
		if(frmuserprog.getJssx().equals("2")){
			//操作角色
			strSql+=" and (v2.cxlx='1' or v2.cxlx='2' or v2.cxlx='3' or v2.cxlx='5' or v2.cxlx = '6')";
		}else if(frmuserprog.getJssx().equals("3")){
			//atm角色
			strSql+=" and v2.cxlx='4'";
		}

		
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(v2.yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}
		strSql+= " ) Order By mldh,sxh";
		List list = jdbcTemplate.queryForList(strSql, map,Program.class);
		return list;
	}
	//String xtyxms,String jscj,String yhdh
	//将取上级角色改为自己角色
	public List getProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql="";
		/*
		if(frmuserprog.getYhdh().equals(Constants.SYS_SUPER_USER)){
			strSql = "Select * From frm_program Order By mldh,sxh";
		}else{
		*/
		strSql = "Select * From frm_program Where (xtlb,cdbh) In ("
			+ " select v1.xtlb,v1.cdbh from frm_rolemenu v1,frm_program v2,frm_user_grantrole v3,frm_role v4"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and  v4.jsdh=v3.jsdh and v1.jsdh=v4.jsdh and v3.yhdh= :yhdh";
		map.put("yhdh",frmuserprog.getYhdh());
		
		if(!frmuserprog.getCxsx().equals("")){
			strSql+= " and   v2.cxsx Like :cxsx ";
			map.put("cxsx","%"+frmuserprog.getCxsx()+"%");
		}
		if(!frmuserprog.getYxsjk().equals("")){
			strSql+= " and  Frm_Comm_Pkg.Getyxsjk(v2.yxsjk) Like :yxsjk ";
			map.put("yxsjk", "%"+frmuserprog.getYxsjk()+"%" );
		}
		strSql += " ) Order By mldh,sxh";
		//}	
		List list = jdbcTemplate.queryForList(strSql, map,Program.class);
		return list;
	}
	
	//获取用户操作权限
	public List getUserProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql="";
		/*
		if(frmuserprog.getYhdh().equals(Constants.SYS_SUPER_USER)){
			strSql = "Select * From frm_program Order By mldh,sxh";
		}else{
		*/
		strSql = "Select * From frm_program Where (xtlb,cdbh) In ("
			+ " select v1.xtlb,v1.cdbh from frm_rolemenu v1,frm_program v2,frm_userrole v3,frm_role v4"
			+ " where v1.xtlb=v2.xtlb and v1.cdbh=v2.cdbh and  v4.jsdh=v3.jsdh and v1.jsdh=v4.jsdh and v3.yhdh= :yhdh";
		map.put("yhdh",frmuserprog.getYhdh());
		
		strSql += " ) Order By mldh,sxh";
		List list = jdbcTemplate.queryForList(strSql, map,Program.class);
		return list;
	}

	//自由权限
	public List getProgramListByYhdh(String yhdh) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql="";
		strSql = "Select * From frm_program Where (xtlb,cdbh) In ("
			+ " select v1.xtlb,v1.cdbh from frm_usermenu v1 where v1.yhdh= :yhdh";
		map.put("yhdh",yhdh);
		strSql += " ) Order By mldh,sxh";
		//}	
		List list = jdbcTemplate.queryForList(strSql, map,Program.class);
		return list;
	}
	
	//处理角色代号，#分隔
	private String getJsdh(String jsdh){
		 String result="";
		 String[] jsdharr=jsdh.split("#");
		 for(int i=0;i<jsdharr.length;i++){
			 result+="'"+jsdharr[i]+"',";
		 }
		 result=result.substring(0,result.length()-1);
		 return result;
	}
	
	public List getProgramListByJsdh(String jsdh) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select xtlb,cdbh,gnlb From frm_rolemenu Where jsdh=:jsdh";
		map.put("jsdh",jsdh);
		List list = jdbcTemplate.queryForList(strSql, map,UserMenu.class);
		return list;
	}
	
	public List getUserMenu(String yhdh) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select xtlb,cdbh,gnlb From frm_usermenu Where yhdh=:yhdh";
		map.put("yhdh",yhdh);
		List list = jdbcTemplate.queryForList(strSql,map, UserMenu.class);
		return list;
	}
	
	
	public List getUserDeskmenu(String yhdh)throws DataAccessException {
		HashMap map=new HashMap();
		String strSql = "Select xtlb,cdbh From frm_userdesk Where yhdh=:yhdh";
		map.put("yhdh",yhdh);
		List list = jdbcTemplate.queryForList(strSql,map, UserMenu.class);
		return list;
	}


	//zhoujn 20100524 获取维护用户重叠角色代号
	public List getRoleList(String yhdh,String whyh) throws DataAccessException {
		HashMap map=new HashMap();
		String strSql="";
		/*
		if(yhdh.equals(Constants.SYS_SUPER_USER)){
			strSql=" select * from frm_userrole v"
				+ " where v.yhdh=:whyh"
				+ " and  jsdh in ( "
			    + " select  jsdh "
				+ " from frm_role" 
				+ " where sjjsdh='"+Constants.SYS_SUPER_ROLE+"'"
				+ " )";
			map.put("whyh",whyh);
				
		}else{
		*/
		strSql=" select * from frm_userrole v"
			+ " where v.yhdh=:whyh"
			+ " and  sjjsdh in ( "
		    + " select  jsdh "
			+ " from frm_user_grantrole" 
			+ " where yhdh=:yhdh"
			+ " )";
		map.put("whyh",whyh);
		map.put("yhdh",yhdh);
		//}
		List list = jdbcTemplate.queryForList(strSql,map,UserRole.class);
		return list;
	}	
	
	
	public String getXtlbmc(String xtlb){
		String result="";
		try{
			String strSql="select * from frm_code "
				+" where xtlb='00' and dmlb='0001'"
				+" and dmz=:dmz";
			HashMap map=new HashMap();
			map.put("dmz",xtlb);
			Code code=(Code)jdbcTemplate.queryForSingleObject(strSql, map, Code.class);
			result=code.getDmsm1();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
		
	}
	
	//获取当前目录名称以及上级目录名称
	public Fold getFold(String mldh){
		Fold fold=null;
		try{
			String strSql="select * from frm_fold where mldh=:mldh";
			HashMap map=new HashMap();
			map.put("mldh",mldh);
			fold=(Fold)jdbcTemplate.queryForSingleObject(strSql, map, Fold.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return fold;
	}
	
	public String getMlmc(String mldh){
		String result="";
		Fold fold=getFold(mldh);
		if(fold!=null){
			result=fold.getMlmc();
		}	
		return result;
	}
	
	//根据foldlist构造一级目录,获取所有的目录结构
	public List getAllfoldlist(List foldlist) {
		String mldhs="";
		for(int i=0;i<foldlist.size();i++){
			Fold fold=(Fold)foldlist.get(i);
			mldhs=mldhs+"'"+fold.getMldh()+"',";
		}
		if(!mldhs.equals("")){
			mldhs=mldhs.substring(0,mldhs.length()-1);
		}
		
		List result=new ArrayList();
		if(!mldhs.equals("")){
		try{
			String sql = " select distinct v2.*,level"
				+ " from ("
				+ " select v1.*  from frm_fold v1"
				+ " start with v1.mldh in ("+mldhs+")"
				+ " connect by prior v1.sjmldh=v1.mldh"
				+ " )v2"
				+ " start with v2.sjmldh='0000'"
				+ " connect by prior v2.mldh=v2.sjmldh"
				+ " order by level,sxh";
			result = jdbcTemplate.queryForList(sql, Fold.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		}
		return result;
	}
	
	
	public List getChildfoldlist(List foldlist,String sjmldh) {
		String mldhs="";
		for(int i=0;i<foldlist.size();i++){
			Fold fold=(Fold)foldlist.get(i);
			mldhs=mldhs+"'"+fold.getMldh()+"',";
		}
		if(!mldhs.equals("")){
			mldhs=mldhs.substring(0,mldhs.length()-1);
		}
		
		List result=new ArrayList();
		try{
			String sql = " select distinct v2.*,level"
				+ " from ("
				+ " select v1.*  from frm_fold v1"
				+ " start with v1.mldh in ("+mldhs+")"
				+ " connect by prior v1.sjmldh=v1.mldh"
				+ " )v2"
				+ " start with v2.sjmldh='"+sjmldh+"'"
				+ " connect by prior v2.mldh=v2.sjmldh"
				+ " order by level,sxh";
			result = jdbcTemplate.queryForList(sql, Fold.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}	
}
