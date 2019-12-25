package com.tmri.tfc.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;
import com.tmri.tfc.bean.TfcSpecial;
import com.tmri.tfc.dao.SpecialVehDao;

/**
@author Yangzm
@data: 2014-06-04  time: ÏÂÎç04:46:52

 */
@Repository
public class SpecialVehDaoJdbc extends FrmDaoJdbc implements SpecialVehDao {

	public List<TfcSpecial> getSpecialVehList(TfcSpecial command,PageController controller) throws Exception {
		String sql = " where sjzt= 1";
		if(StringUtil.checkBN(command.getHpzl())){
			sql=sql+" and hpzl ='"+command.getHpzl()+"'";
		}
		if(StringUtil.checkBN(command.getHphm())){
			sql=sql+" and hphm = '"+command.getHphm()+"'";
		}
		
		if(StringUtil.checkBN(command.getBzsm())){
			sql=sql+" and bzsm like '%"+command.getBzsm()+"%'";
		}
		
		if (StringUtil.checkBN(command.getGlbm())) {
			if ("1".equals(command.getBmjb())) {
				sql += " and glbm like '01%'";
			} else if ("2".equals(command.getBmjb())) {
				sql += " and glbm like '"
							+ command.getGlbm().substring(0, 2) + "%'";
			} else if ("3".equals(command.getBmjb())) {
				sql += " and glbm like '"
							+ command.getGlbm().substring(0, 4) + "%'";
			} else {
				sql += " and glbm like '" + command.getGlbm() + "%'";
			}
		}
		
		sql=" select * from tfc_special "+sql+" order by hphm desc";
		return controller.getWarpedList(sql,TfcSpecial.class,jdbcTemplate);
	}
	
	public TfcSpecial getSpecial(String xh) throws Exception {
		String sql = "select * from TFC_SPECIAL where rownum<=1 and xh ='"+xh+"'";
		
		List<TfcSpecial> list = this.jdbcTemplate.queryForList(sql,
				TfcSpecial.class);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	
	public DbResult setSpecial(TfcSpecial bean) throws Exception {
		String callString="{call Tfc_Setdata_Pkg.setspecial_veh(?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				TfcSpecial bean=(TfcSpecial)getParameterObject();
				DbResult result = new DbResult();
				if(bean.getXh()==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,bean.getXh());
				if(bean.getHpzl()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getHpzl());
				if(bean.getHphm()==null) cstmt.setNull(3, Types.NULL); else cstmt.setString(3,bean.getHphm());
				if(bean.getBzsm()==null) cstmt.setNull(4, Types.NULL); else cstmt.setString(4,bean.getBzsm());
				if(bean.getSjjb()==null) cstmt.setNull(5, Types.NULL); else cstmt.setString(5,bean.getSjjb());
				if(bean.getCjr()==null) cstmt.setNull(6, Types.NULL); else cstmt.setString(6,bean.getCjr());
				if(bean.getGlbm()==null) cstmt.setNull(7, Types.NULL); else cstmt.setString(7,bean.getGlbm());
				cstmt.registerOutParameter(8,Types.NUMERIC);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(8));
				result.setMsg(cstmt.getString(9));
				return result;
			}
		};
		callBack.setParameterObject(bean);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	public DbResult saveSpecialVeh(String hpxx) throws Exception {
		String callString = "{call TFC_PASS_PKG.saveSpecial_Veh(?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String hpxx=(String)getParameterObject();
				DbResult result = new DbResult();
				if(hpxx==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,hpxx);
				cstmt.registerOutParameter(2, Types.NUMERIC);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(2));
				result.setMsg(cstmt.getString(3));
				return result;
			}
		};
		callBack.setParameterObject(hpxx);
		DbResult result = (DbResult) jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	
	public DbResult delSpecial() throws Exception {
		/*DbResult result=new DbResult();
		try {
			this.jdbcTemplate.execute("delete from v ");
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg(e.getMessage());
		}
		return  result; 
		*/
		return this.save("TFC_PASS_PKG.delSpecial_Veh");
	}

}
