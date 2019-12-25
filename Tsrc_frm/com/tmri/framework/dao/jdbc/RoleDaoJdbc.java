package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.RoleMenu;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.RoleDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
@Repository
public class RoleDaoJdbc extends FrmDaoJdbc implements RoleDao {
	
	
	//zhoujn 20100522 获取角色名称
	public Role getRole(String jsdh) throws DataAccessException {
		Role role=new Role();
		/*
		if(jsdh.equals(Constants.SYS_SUPER_ROLE)){
			role.setJsdh(Constants.SYS_SUPER_ROLE);
			role.setJsmc(Constants.SYS_SUPER_ROLEMC);
		}else{
		*/
		HashMap map=new HashMap();
		String strSql = "select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm "
			+"from frm_role v1 "
			+"where v1.jsdh=:jsdh";	
		map.put("jsdh",jsdh);
		role = (Role)jdbcTemplate.queryForSingleObject(strSql,map, Role.class);
		//}
		return role;
	}	
	/*
	 *获取角色list 
	 * 
	 */
	//zhoujn 获取可授权角色
	public List getRoleList(Role role) throws DataAccessException {
		HashMap map=new HashMap();
		String tmpSql = " select w1.jsdh,w1.jsmc,w1.sjjsdh,w1.jssx,w1.bz,w1.yhdh"
			+" from ("
			//+" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,'' yhdh"
			+" select v1.jsdh,v1.jsmc,v1.sjjsdh,v1.jssx,v1.bz,v2.yhdh,v1.glbm"
			+" from  frm_user_grantrole v2,frm_role v1"
			+" where v2.yhdh=:yhdh"
			+" and   v2.jsdh=v1.jsdh";
			
		if(role.getJscj()!=null&&!role.getJscj().equals("")){
			if(!tmpSql.equals("")){
				if(role.getCzqx().equals("1")){
					tmpSql += getJscjsql("v1.jscj",role.getJscj());
				}else{
					tmpSql += " and v1.jscj like :jscj1";
					map.put("jscj1","%"+role.getJscj()+"%");
				}
			}
		}
		tmpSql+=" union "
			+" select v1.jsdh,v1.jsmc,v1.sjjsdh,v1.jssx,v1.bz,v2.yhdh,v1.glbm" 
			+" from frm_role v1,frm_user_grantrole v2 "
			+" where v1.sjjsdh=v2.jsdh"
			+" and   v2.yhdh=:yhdh"
			+" and   v1.glbm='"+role.getGlbm()+"'";

		if(role.getJscj()!=null&&!role.getJscj().equals("")){
			if(!tmpSql.equals("")){
				if(role.getCzqx().equals("1")){
					tmpSql += getJscjsql("v1.jscj",role.getJscj());
				}else{
					tmpSql += " and v1.jscj like :jscj2";
					map.put("jscj2","%"+role.getJscj()+"%");
				}
			}
		}
		tmpSql+=" )w1 where 1=1";
		
		if(role!=null){
			if(role.getJsmc()!=null&&!role.getJsmc().equals("")){
				tmpSql=" and w1.jsmc like :jsmc";
				map.put("jsmc","%"+role.getJsmc()+"%");
			}
			if(role.getJssx()!=null&&!role.getJssx().equals("")){
				if(!tmpSql.equals("")){
					tmpSql += " and w1.jssx=:jssx";
					map.put("jssx",role.getJssx());
				}
			}
			/*
			if(role.getXtlb()!=null&&!role.getXtlb().equals("")){
				if(tmpSql.equals("")){
					tmpSql = " where Frm_comm_pkg.instr1('" + role.getXtlb() + "',xtlb)=1 ";
				}else{
					tmpSql += " and Frm_comm_pkg.instr1('" + role.getXtlb() + "',xtlb)=1 ";	
				}				
			}*/

			/*
			if(role.getYhdh().equals(Constants.SYS_SUPER_USER)){
				tmpSql +=" and sjjsdh = '"+Constants.SYS_SUPER_ROLE+"'";
			}else{
			*/
			//}
			
		}		
		
		tmpSql += " order by w1.jsmc";
		map.put("yhdh",role.getYhdh());
		List list = jdbcTemplate.queryForList(tmpSql, map, Role.class);
		return list;
	}
	
	public SysResult saveRole(Role role,List rolemenuList){
		// 插入菜单表中的记录
		String callString = "{call Frm_Sys_Pkg.saveFRM_ROLE(?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();				
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();				
				result.setRetcode(cstmt.getString(1));
				result.setRetval(cstmt.getString(2));
				result.setRetdesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult result = (SysResult)jdbcTemplate.execute(callString, callBack);
	
		//更新角色
		if(result.getRetcode().equals("1")){
			role.setJsdh(result.getRetval());
			callString = "{call Frm_Sys_Pkg.saveFRM_ROLEMENU(?,?,?,?)}";
			callBack = new CallableStatementCallbackImpl() {
				public Object doInCallableStatement(CallableStatement cstmt)
						throws SQLException, DataAccessException {
					RoleMenu frmRolemenu = (RoleMenu) getParameterObject();		
					if(frmRolemenu.getJsdh()==null) cstmt.setNull(1,Types.VARCHAR);else cstmt.setString(1,frmRolemenu.getJsdh());
					if(frmRolemenu.getXtlb()==null) cstmt.setNull(2,Types.VARCHAR);else cstmt.setString(2,frmRolemenu.getXtlb());
					if(frmRolemenu.getCdbh()==null) cstmt.setNull(3,Types.VARCHAR);else cstmt.setString(3,frmRolemenu.getCdbh());
					if(frmRolemenu.getGnlb()==null) cstmt.setNull(4,Types.VARCHAR);else cstmt.setString(4,frmRolemenu.getGnlb());
					cstmt.execute();				
					return null;
				}
			};			
			RoleMenu roleMenu = null;
			for(int i=0;i<rolemenuList.size();i++){
				roleMenu = (RoleMenu)rolemenuList.get(i);
				roleMenu.setJsdh(role.getJsdh());
				callBack.setParameterObject(roleMenu);
				jdbcTemplate.execute(callString, callBack);				
			}
		}
		
		//级联删除下级角色菜单
		callString = "{call Frm_Sys_Pkg.Savefrm_Rolecascade(?,?,?)}";
		callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();				
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();				
				result.setRetcode(cstmt.getString(1));
				result.setRetval(cstmt.getString(2));
				result.setRetdesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult temp = (SysResult)jdbcTemplate.execute(callString, callBack);		
		return result;		
	}
	
	public SysResult saveUsermenuCancel(String glbm,List rolemenuList) throws Exception{
		// 插入菜单表中的记录
		String callString = "{call Frm_Sys_pkg.Savefrm_Usermenucancel(?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();				
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();				
				result.setRetcode(cstmt.getString(1));
				result.setRetval(cstmt.getString(2));
				result.setRetdesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult result = (SysResult)jdbcTemplate.execute(callString, callBack);
	
		//更新角色
		if(result.getRetcode().equals("1")){
			callString = "{call Frm_Sys_pkg.Updatefrm_Usermenu(?,?,?,?)}";
			callBack = new CallableStatementCallbackImpl() {
				public Object doInCallableStatement(CallableStatement cstmt)
						throws SQLException, DataAccessException {
					RoleMenu frmRolemenu = (RoleMenu) getParameterObject();		
					if(frmRolemenu.getXtlb()==null) cstmt.setNull(1,Types.VARCHAR);else cstmt.setString(1,frmRolemenu.getXtlb());
					if(frmRolemenu.getCdbh()==null) cstmt.setNull(2,Types.VARCHAR);else cstmt.setString(2,frmRolemenu.getCdbh());
					if(frmRolemenu.getGnlb()==null) cstmt.setNull(3,Types.VARCHAR);else cstmt.setString(3,frmRolemenu.getGnlb());
					if(frmRolemenu.getJsdh()==null) cstmt.setNull(4,Types.VARCHAR);else cstmt.setString(4,frmRolemenu.getJsdh());
					cstmt.execute();				
					return null;
				}
			};			
			RoleMenu roleMenu = null;
			for(int i=0;i<rolemenuList.size();i++){
				roleMenu = (RoleMenu)rolemenuList.get(i);
				//使用替代字段,代替glbm
				roleMenu.setJsdh(glbm);
				callBack.setParameterObject(roleMenu);
				jdbcTemplate.execute(callString, callBack);				
			}
		}
		return result;		
	}	

	public SysResult removeRole(){
		String callString = "{call Frm_Sys_Pkg.Deletefrm_Role(?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();				
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();				
				result.setRetcode(cstmt.getString(1));
				result.setRetval(cstmt.getString(2));
				result.setRetdesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult result = (SysResult)jdbcTemplate.execute(callString, callBack);
		return result;		
	}
	
	// 将字符串分割为字符数组
	private String[] splitString(String str, String split) {
		String[] result;
		String tmpStr;
		tmpStr = str.trim();
		if (tmpStr.equals("")) {
			result = null;
		} else {
			result = tmpStr.split(split);
		}
		return result;
	}

	//定义私有函数取下属角色
	//type 1有union 2没有union
	public String getChildRole(Role frmRole){
		HashMap map=new HashMap();
		String sqlbody="";
		String sql=" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm" 
			+" from frm_role v1,frm_user_grantrole v2 "
			+" where v1.sjjsdh=v2.jsdh"
			+" and   v2.yhdh=:yhdh";
		map.put("yhdh",frmRole.getYhdh());
		//管理部门
		if(frmRole.getGlbm()!=null&&!frmRole.getGlbm().equals("")){
			sql+=" and v1.glbm = :glbm";
			map.put("glbm",frmRole.getGlbm());
		}	
		
		List list=jdbcTemplate.queryForList(sql,map,Role.class);
		for(int i=0;i<list.size();i++){
			Role role=(Role)list.get(i);
			if(i!=0){
				sqlbody+=" union";
			}
			sqlbody+=" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm"
				+" from ("
				+" select  * from frm_role w1"
				+" start with w1.jsdh='"+role.getJsdh()+"'"
				+" connect by prior w1.jsdh=w1.sjjsdh" 
				+" )v1"
				+" where v1.jsdh!='"+role.getJsdh()+"'";
		}		
		return sqlbody;
	}
	
	public List getRoleListByPagesize(Role frmRole, PageController controller) throws Exception {
		HashMap map=new HashMap();

		String sqlbody="";
		if(frmRole.getJslx().equals("")){
			sqlbody =" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm"
				+" from  frm_user_grantrole v2,frm_role v1"
				+" where v2.yhdh=:yhdh"
				+" and   v2.jsdh=v1.jsdh"
				+" union "
				+" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm" 
				+" from frm_role v1,frm_user_grantrole v2 "
				+" where v1.sjjsdh=v2.jsdh"
				+" and   v2.yhdh=:yhdh"
				+" and   v1.glbm=:glbm";
			//管理部门
			if(frmRole.getGlbm()!=null&&!frmRole.getGlbm().equals("")){
				sqlbody+=" and v1.glbm = :glbm";
				map.put("glbm",frmRole.getGlbm());
			}			
			if(!getChildRole(frmRole).equals("")){
				sqlbody+=" union " + getChildRole(frmRole);
			}			
			
		}else if(frmRole.getJslx().equals("1")){
			sqlbody=" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm"
				+" from  frm_user_grantrole v2,frm_role v1"
				+" where v2.yhdh=:yhdh"
				+" and   v2.jsdh=v1.jsdh";			
		}else if(frmRole.getJslx().equals("2")){
			sqlbody=" select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm" 
				+" from frm_role v1,frm_user_grantrole v2 "
				+" where v1.sjjsdh=v2.jsdh"
				+" and   v2.yhdh=:yhdh";
			//管理部门
			if(frmRole.getGlbm()!=null&&!frmRole.getGlbm().equals("")){
				sqlbody+=" and v1.glbm = :glbm";
				map.put("glbm",frmRole.getGlbm());
			}			
		}else if(frmRole.getJslx().equals("3")){
			sqlbody=getChildRole(frmRole);	
			if(sqlbody.equals("")){
				return null;
			}
		}	
			
		String tmpSql = " select w1.jsdh,w1.jsmc,w1.jscj,w1.sjjsdh,w1.jssx,w1.bz,w1.glbm"
			+" from (" + sqlbody +" )w1 where 1=1 ";
		
		if(frmRole!=null){
			if(frmRole.getJsmc()!=null&&!frmRole.getJsmc().equals("")){
				tmpSql+=" and w1.jsmc like :jsmc";
				map.put("jsmc","%"+frmRole.getJsmc()+"%");
			}
			if(frmRole.getJscj()!=null&&!frmRole.getJscj().equals("")){
				tmpSql+=" and w1.jscj like :jscj";
				map.put("jscj","%"+frmRole.getJscj()+"%");
			}	
			
			if(frmRole.getBmmc()!=null&&!frmRole.getBmmc().equals("")){
				tmpSql+=" and exists(select * from frm_department w2 where w1.glbm=w2.glbm and w2.bmmc like :bmmc)";
				map.put("bmmc","%"+frmRole.getBmmc()+"%");
			}
			
			if(!frmRole.getTcdbh().equals("")){
				tmpSql = tmpSql + " and exists ("
					+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
					+ " from frm_rolemenu w2"
					+ " where w1.jsdh=w2.jsdh"
					+ " and   w2.cdbh='"+frmRole.getTcdbh()+"'"
					+ " and   w2.xtlb='"+frmRole.getTxtlb()+"'"
					+ ")";
			}else if(!frmRole.getTgnid().equals("")){
				tmpSql = tmpSql + " and exists ("
					+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
					+ " from frm_rolemenu w2"
					+ " where w1.jsdh=w2.jsdh"
					+ " and   w2.gnlb like '%"+frmRole.getTgnid()+"%'"
					+ " and   w2.xtlb||w2.cdbh = '"+frmRole.getTxtlb()+"'"
					+ ")";
			}			
			
			/*
			if(frmRole.getYhdh().equals(Constants.SYS_SUPER_USER)){
				tmpSql+=" and sjjsdh = '"+Constants.SYS_SUPER_ROLE+"'";
			}else{
			*/
			//非system的系统管理员，frm_user_grantrole
			//}			
		}
		tmpSql += " order by w1.jsdh,w1.jscj";
		map.put("yhdh",frmRole.getYhdh());
		System.out.println(tmpSql);
		//System.out.println("角色查询结果："+tmpSql+";用户代号："+frmRole.getYhdh()+";管理部门:"+frmRole.getGlbm());
		return controller.getWarpedList(tmpSql,map,Role.class,jdbcTemplate);
	}
	
	//获取角色类型
	//获取当用户的自拥有角色，属于则是1否则是3
	public String getJslx(String jsdh,String yhdh) throws Exception {
		String result="1";
		HashMap map=new HashMap();
		
		String sql=" select count(*)" 
			+" from frm_role v1,frm_user_grantrole v2 "
			+" where v1.sjjsdh=v2.jsdh"
			+" and   v2.yhdh=:yhdh"
			+" and   v1.jsdh=:jsdh";
		
		map.put("yhdh",yhdh);
		map.put("jsdh",jsdh);
		int irownum=jdbcTemplate.queryForInt(sql, map);
		if(irownum>0){
			result="2";
		}else{
			//在判断是否是自身权限
			sql=" select count(*)"
				+" from  frm_user_grantrole v2,frm_role v1"
				+" where v2.yhdh=:yhdh"
				+" and   v2.jsdh=v1.jsdh"
				+" and   v2.jsdh=:jsdh";
			map.put("yhdh",yhdh);
			map.put("jsdh",jsdh);
			irownum=jdbcTemplate.queryForInt(sql, map);
			if(irownum>0){
				result="1";
			}else{
				result="3";
			}
		}
		return result;
	}
	
	//获取可授权权限列表
	//zhoujn 20100520
	public List getUsergrantroleList(String yhdh) throws Exception {
		HashMap map=new HashMap();
		String sql=" select v2.jsdh,v2.jsmc from  frm_user_grantrole v1,frm_role v2 where v1.jsdh=v2.jsdh";
		if(yhdh!=null&&!yhdh.equals("")){
			sql+=" and v1.yhdh=:yhdh";
			map.put("yhdh",yhdh);
		}
		sql+=" order by v2.jsdh";
		List list = jdbcTemplate.queryForList(sql,map,Role.class);
		return list;
	}
	
	
	//根据角色代号获取所有角色
	public List getRoleList(String jsdh) throws Exception {
		Role role=new Role();
		HashMap map=new HashMap();
		String strSql = "select v1.jsdh,v1.jsmc,v1.jscj,v1.sjjsdh,v1.jssx,v1.bz,v1.glbm "
			+"from frm_role v1 "
			+"where 1=1";	
		if(jsdh!=null&&!jsdh.equals("")){
			strSql+=" and v1.jsdh in("+jsdh+")";
		}		
		List result = jdbcTemplate.queryForList(strSql, Role.class);
		return result;
	}		
	
	
	//获取其子角色
	public List getChildRoleList(String jsdh){
		List result=new ArrayList();
		try{
			String strSql=" select  * from frm_role w1"
				+ " start with w1.jsdh='"+jsdh+"'"
				+ " connect by prior w1.jsdh=w1.sjjsdh";
			
			result = jdbcTemplate.queryForList(strSql, Role.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	//获取某级别以下角色
	//如角色层级为3,则需要获取到3,4,5三级
	public String getJscjsql(String name,String jscj){
		String result="";
		int ijscj=new Integer(jscj).intValue();
		for(int i=ijscj;i<=5;i++){
			if(i!=ijscj){
				result = result + " or ";
			}
			result=result+ name +" like '%" + i + "%' ";  
		}
		if(!result.equals("")){
			result=" and ( " + result + " )";
		}
		return result;
	}
	

	
}
