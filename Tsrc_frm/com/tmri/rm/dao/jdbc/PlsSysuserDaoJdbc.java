package com.tmri.rm.dao.jdbc;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.RoleMenu;
import com.tmri.rm.dao.PlsSysuserDao;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;

@Repository
public class PlsSysuserDaoJdbc extends FrmDaoJdbc implements PlsSysuserDao {

	public List<SysUser> getSysuserList(SysUser user, PageController controller) throws DataAccessException {
		HashMap<String, String> map = new HashMap<String, String>();
		List<SysUser> queryList  = null;
		String sql ="select v1.yhdh,v1.xm,v1.mm,v1.glbm,v1.sfzmhm,v1.ipks,"
			+"v1.ipjs,to_char(v1.zhyxq,'yyyy-mm-dd') zhyxq,"
			+"to_char(v1.mmyxq,'yyyy-mm-dd') mmyxq,v1.jyd,v1.spjb,v1.spglbm,"
			+"v1.sfmj,v1.rybh,v1.qxms,v1.zt,v1.xtgly,"
			+"to_char(v1.zjdlsj,'yyyy-mm-dd') zjdlsj,v1.bz,v1.kgywyhlx,v1.yhssyw,v1.mac,v1.gdip "
			+"from frm_sysuser v1 where 1=1 ";
		
		if (StringUtil.checkBN(user.getGlbm())) {
			sql += " and v1.glbm like '%"+user.getGlbm()+"%'";
		}		
		
		if(!user.getTjsdh().equals("")){
			sql = sql + " and exists ("
				+ " select w1.* " 
				+ " from frm_userrole w1" 
				+ " where w1.yhdh=v1.yhdh "
				+ " and   w1.jsdh='"+user.getTjsdh()+"'"
				+ ")";
		}

		if(user.getYhdh()!=null&&!user.getYhdh().equals("")){
			sql+=" and yhdh like :yhdh ";
			map.put("yhdh","%"+user.getYhdh()+"%");
		}
		if(user.getXm()!=null&&!user.getXm().equals("")){
			sql+=" and xm like :xm ";
			map.put("xm","%"+user.getXm()+"%");
		}
		if(user.getXtgly()!=null&&!user.getXtgly().equals("")){
			sql+=" and xtgly = :xtgly ";
			map.put("xtgly",user.getXtgly());
		}
		//增加用户所属业务判断
		/*
		if(user.getYhssyw().equals("")){
			//该用户可管辖的全部
			String kgywyhlx=user.getKgywyhlx();
			if(kgywyhlx.equals("")){
				kgywyhlx="1000000000";
			}
			if(kgywyhlx.charAt(0)!='1'){
				sql+=getKgywyhlx(kgywyhlx);
			}
		}else{
			String yhssyw=user.getYhssyw();
			String kgywyhlx=user.getKgywyhlx();
			if(kgywyhlx.equals("")){
				kgywyhlx="1000000000";
			}
			int index=Integer.valueOf(yhssyw).intValue();
			sql+=" and (v1.yhssyw is null or v1.yhssyw like '"+getZwf(index-1,"_")+"1"+getZwf(kgywyhlx.length()-index,"_")+"')";
			
		}
		
		//增加权限查询条件，是操作权限还是管理权限
		if(user.getQxms().equals("1")){
			//操作权限
			if(user.getSqms().equals("1")){
				if(!user.getTjsdh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.* " 
						+ " from frm_userrole w1"
						+ " where w1.yhdh=v1.yhdh "
						+ " and   w1.jsdh='"+user.getTjsdh()+"'"
						+ ")";
				}				
			}else if(user.getSqms().equals("2")){
				if(!user.getTgnid().equals("")){
					sql = sql + " and exists ("
						+ " select w1.xtlb,w1.cdbh,w1.gnlb" 
						+ " from frm_usermenu w1"
						+ " where w1.yhdh=v1.yhdh"
						+ " and   w1.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w1.xtlb||w1.cdbh = '"+user.getTxtlb()+"'"
						+ " union"
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_userrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w2.xtlb||w2.cdbh = '"+user.getTxtlb()+"'"
						+ ")";				
				}else if(!user.getTcdbh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.xtlb,w1.cdbh,w1.gnlb" 
						+ " from frm_usermenu w1"
						+ " where w1.yhdh=v1.yhdh"
						+ " and   w1.cdbh='"+user.getTcdbh()+"'"
						+ " and   w1.xtlb = '"+user.getTxtlb()+"'"
						+ " union"
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_userrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.cdbh='"+user.getTcdbh()+"'"
						+ " and   w2.xtlb = '"+user.getTxtlb()+"'"
						+ ")";
				}
			}
			
		}else{
			//管理权限
			if(user.getSqms().equals("1")){
				if(!user.getTjsdh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.* " 
						+ " from frm_user_grantrole w1"
						+ " where w1.yhdh=v1.yhdh "
						+ " and   w1.jsdh='"+user.getTjsdh()+"'"
						+ ")";
				}			
				
			}else if(user.getSqms().equals("2")){			
				if(!user.getTgnid().equals("")){
					sql = sql + " and exists ("
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_user_grantrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w2.xtlb||w2.cdbh = '"+user.getTxtlb()+"'"
						+ ")";				
				}else if(!user.getTcdbh().equals("")){
					sql = sql + " and exists ("
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_user_grantrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.cdbh='"+user.getTcdbh()+"'"
						+ " and   w2.xtlb = '"+user.getTxtlb()+"'"
						+ ")";
				}
			}
		}
		*/
		//增加操作权限是否授权查询
		if (StringUtil.checkBN(user.getCzqx())) {
			//已授权和未授权
			if(user.getCzqx().equals("1")){
				sql = sql + " and exists ("
					+ " select w1.yhdh" 
					+ " from frm_userrole w1 "
					+ " where w1.yhdh=v1.yhdh "
					+ " union "
					+ " select w2.yhdh" 
					+ " from frm_usermenu w2 "
					+ " where w2.yhdh=v1.yhdh "
					+ ")";
			}else if(user.getCzqx().equals("2")){
				sql = sql + " and not exists ("
					+ " select w1.yhdh" 
					+ " from frm_userrole w1 "
					+ " where w1.yhdh=v1.yhdh "
					+ " union "
					+ " select w2.yhdh" 
					+ " from frm_usermenu w2 "
					+ " where w2.yhdh=v1.yhdh "
					+ ")";
			}
		}
		
		// 排序
		if (StringUtil.checkBN(user.getOrder())) {
			sql += " order by v1." + user.getOrder()+" "+user.getProper();
		} else {
			sql += " order by v1.yhdh";
		}
		queryList = controller.getWarpedList(sql, map, SysUser.class, jdbcTemplate);
		return queryList;
//		StringBuffer sql = new StringBuffer();
//		return controller.getWarpedList(sql.toString(), SysUser.class, jdbcTemplate);
	}

	public SysUser getSysuser(String yhdh) throws Exception {
		String sql = "select * from frm_sysuser where yhdh=?";
		return (SysUser)jdbcTemplate.queryForBean(sql, new Object[]{yhdh}, SysUser.class);
	}

	public SysUser getPlsSysuser(String yhdh) throws Exception {
		String sql = "select * from pls_sysuser where yhdh=?";
		return (SysUser)jdbcTemplate.queryForBean(sql, new Object[]{yhdh}, SysUser.class);
	}

	public List<Role> getRoleList() throws Exception {
		String sql = " select * from  frm_role where jsdh in ('9001','9002','9003','9004','9005','9006')";
		return this.jdbcTemplate.queryForList(sql, Role.class);
	}

	public List<Role> getRole(String jsdh) throws Exception {
		String sql = "select * from frm_role ";
		if(StringUtil.checkBN(jsdh)){
			sql += "where jsdh='" + jsdh + "'";
		}
		return jdbcTemplate.queryForList(sql, Role.class);
	}

	public List<Role> getUserRole(SysUser user) throws Exception {
		String sql = "select * from FRM_ROLE where jsdh in (select jsdh from FRM_USERROLE where yhdh=?)";
		return jdbcTemplate.queryForList(sql, new Object[]{user.getYhdh()}, Role.class);
	}

	public List<Role> getUserGrantRole(Role role) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from frm_role where jsdh in (select jsdh from frm_user_grantrole where yhdh=?");
		sql.append(" union select jsdh from frm_role where sjjsdh in(select jsdh from frm_user_grantrole where yhdh=?) and glbm=?) ");
		if(StringUtil.checkBN(role.getJscj())){
			sql.append(this.getJscjSql(role.getJscj()));
		}
		return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{role.getYhdh(), role.getYhdh(), role.getGlbm()}, Role.class);
	}
	
	private String getJscjSql(String jscj) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql_1 = new StringBuffer();
		int iJscj = Integer.parseInt(jscj);
		for(int i = iJscj; i <= 5; i++){
			if(i != iJscj){
				sql_1.append(" or ");
			}
			sql_1.append("jscj like '%").append(i).append("%'");
		}
		if(sql.length() > 0){
			sql.append(" and (").append(sql_1.substring(4)).append(")");
		}
		return sql.toString();
	}
	
	public int deleteUserRole(String yhdh) throws Exception {
		String sql = "delete from FRM_USERROLE where yhdh=?";
		return jdbcTemplate.update(sql, yhdh);
	}

	public int deleteUserGrantRole(String yhdh) throws Exception {
		String sql = "delete from FRM_USER_GRANTROLE where yhdh=?";
		return jdbcTemplate.update(sql, yhdh);
	}

	public List<RoleMenu> getRoleMenus(String jsdh) throws Exception {
		String sql = "select * from frm_rolemenu where jsdh in (select jsdh from frm_role where jsdh=?)";
		return jdbcTemplate.queryForList(sql, new Object[]{ jsdh }, RoleMenu.class);
	}
	
	public List<Function> getRoleMenuFunction(RoleMenu r) throws Exception {
		String sql = "select * from frm_function where xtlb='" + r.getXtlb() + "' and gnlb in ('" + r.getGnlb().replaceAll(",", "','") + "')";
		return this.jdbcTemplate.queryForList(sql, Function.class);
	}

	public List<SysUser> getPlsSysuserList(SysUser user, PageController controller) throws DataAccessException {
		String sql ="select v1.yhdh,v1.xm,v1.mm,v1.glbm,v1.sfzmhm,v1.ipks,"
			+"v1.ipjs,to_char(v1.zhyxq,'yyyy-mm-dd') zhyxq,"
			+"to_char(v1.mmyxq,'yyyy-mm-dd') mmyxq,"
			+"v1.sfmj,v1.rybh,v1.qxms,v1.zt,v1.xtgly,"
			+"to_char(v1.zjdlsj,'yyyy-mm-dd') zjdlsj,v1.bz,v1.mac,v1.gdip "
			+"from pls_sysuser v1 where 1=1 ";
		
		if (StringUtil.checkBN(user.getGlbm())) {
			sql += " and v1.glbm like '%"+user.getGlbm()+"%'";
		}		

		if(user.getYhdh()!=null&&!user.getYhdh().equals("")){
			sql+=" and yhdh like '%"+user.getYhdh()+"%'";
			// map.put("yhdh","%"+user.getYhdh()+"%");
		}
		if(user.getXm()!=null&&!user.getXm().equals("")){
			sql+=" and xm like '%"+user.getXm()+"%'";
			// map.put("xm","%"+user.getXm()+"%");
		}
				
		if(!user.getTjsdh().equals("")){
			sql = sql + " and exists ("
				+ " select w1.* " 
				+ " from frm_userrole w1" 
				+ " where w1.yhdh=v1.yhdh "
				+ " and   w1.jsdh='"+user.getTjsdh()+"'"
				+ ")";
		}	
		
		
		//增加权限查询条件，是操作权限还是管理权限
		/*if(user.getQxms().equals("1")){
			//操作权限
			if(user.getSqms().equals("1")){
				if(!user.getTjsdh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.* " 
						+ " from frm_userrole w1" 
						+ " where w1.yhdh=v1.yhdh "
						+ " and   w1.jsdh='"+user.getTjsdh()+"'"
						+ ")";
				}				
			}else if(user.getSqms().equals("2")){
				if(!user.getTgnid().equals("")){
					sql = sql + " and exists ("
						+ " select w1.xtlb,w1.cdbh,w1.gnlb" 
						+ " from frm_usermenu w1"
						+ " where w1.yhdh=v1.yhdh"
						+ " and   w1.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w1.xtlb||w1.cdbh = '"+user.getTxtlb()+"'"
						+ " union"
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_userrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w2.xtlb||w2.cdbh = '"+user.getTxtlb()+"'"
						+ ")";				
				}else if(!user.getTcdbh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.xtlb,w1.cdbh,w1.gnlb" 
						+ " from frm_usermenu w1"
						+ " where w1.yhdh=v1.yhdh"
						+ " and   w1.cdbh='"+user.getTcdbh()+"'"
						+ " and   w1.xtlb = '"+user.getTxtlb()+"'"
						+ " union"
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_userrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.cdbh='"+user.getTcdbh()+"'"
						+ " and   w2.xtlb = '"+user.getTxtlb()+"'"
						+ ")";
				}
			}
			
		}else{
			//管理权限
			if(user.getSqms().equals("1")){
				if(!user.getTjsdh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.* " 
						+ " from frm_user_grantrole w1"
						+ " where w1.yhdh=v1.yhdh "
						+ " and   w1.jsdh='"+user.getTjsdh()+"'"
						+ ")";
				}			
				
			}else if(user.getSqms().equals("2")){			
				if(!user.getTgnid().equals("")){
					sql = sql + " and exists ("
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_user_grantrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w2.xtlb||w2.cdbh = '"+user.getTxtlb()+"'"
						+ ")";				
				}else if(!user.getTcdbh().equals("")){
					sql = sql + " and exists ("
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_user_grantrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.cdbh='"+user.getTcdbh()+"'"
						+ " and   w2.xtlb = '"+user.getTxtlb()+"'"
						+ ")";
				}
			}
		}*/
		
		//增加操作权限是否授权查询
		if (StringUtil.checkBN(user.getCzqx())) {
			//已授权和未授权
			if(user.getCzqx().equals("1")){
				sql = sql + " and exists ("
					+ " select w1.yhdh" 
					+ " from frm_userrole w1 "
					+ " where w1.yhdh=v1.yhdh "
					+ " union "
					+ " select w2.yhdh" 
					+ " from frm_usermenu w2 "
					+ " where w2.yhdh=v1.yhdh "
					+ ")";
			}else if(user.getCzqx().equals("2")){
				sql = sql + " and not exists ("
					+ " select w1.yhdh" 
					+ " from frm_userrole w1 "
					+ " where w1.yhdh=v1.yhdh "
					+ " union "
					+ " select w2.yhdh" 
					+ " from frm_usermenu w2 "
					+ " where w2.yhdh=v1.yhdh "
					+ ")";
			}
		}
		
		// 排序
		if (StringUtil.checkBN(user.getOrder())) {
			sql += " order by v1." + user.getOrder()+" "+user.getProper();
		} else {
			sql += " order by v1.yhdh";
		}
		
		System.out.println(" **** "+sql);
		
		List<SysUser> queryList  = controller.getWarpedList(sql, SysUser.class, jdbcTemplate);
		return queryList;
	}
	
	
	/////测试事物控制
	public void  throwsEx() throws Exception{
		throw new Exception ("1111");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Department> getPoliceDepartmentList(String glbmHead) throws Exception{
		String sql = "select t.* from pls_department t where jlzt ='1' and sjbm like '"+glbmHead+"%'";
		return this.jdbcTemplate.queryForList(sql, Department.class);
	}
	
	public void saveuser() throws Exception {
		int userid = new Random().nextInt(500);
		String inserQuery = "insert into users (username, password, enabled , id) values (?, ?, ?, ?) ";
		Object[] params = new Object[]{"1", "1","1",userid};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.CHAR,Types.INTEGER};
		int number =  jdbcTemplate.update(inserQuery,params,types);

	}
}
