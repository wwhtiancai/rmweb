package com.tmri.share.ora;

/**
 * <p>Title: JdbcTemplate的重载</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007 公安部交通管理科学研究所</p>
 * <p>Company: 公安部交通管理科学研究所</p>
 * @version 1.0
 * @修改历史
 */
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.tmri.share.frm.bean.Function;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;

public class FrmJdbcTemplate extends JdbcTemplate {

	// 获取bean
	public Object queryForBean(String sql, Class elementType) {
		try {
			return queryForObject(sql, new FrmRowMapper(elementType));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public Object queryForBean(String sql, Object[] params, Class elementType) {
		try {
			return queryForObject(sql, params, new FrmRowMapper(elementType));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public Object queryForSingleObject(String sql, Class elementType) {
		try {
			return queryForObject(sql, elementType);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public Object queryForSingleObject(String sql, Object[] params,
			Class elementType) {
		try {
			return queryForObject(sql, params, new FrmRowMapper(elementType));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public List queryForList(String sql, Class elementType)
			throws DataAccessException {
		return query(sql, new FrmRowMapper(elementType));
	}

	public List queryForList(String sql, Object[] params, Class elementType)
			throws DataAccessException {
		return query(sql, params, new FrmRowMapper(elementType));
	}

	// 名称站位符 2010-05-20 wangjf
	public Object queryForSingleObject(String sql, Map map, Class elementType)
			throws DataAccessException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
					this.getDataSource());
			return namedParameterJdbcTemplate.queryForObject(sql, map,
					new FrmRowMapper(elementType));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	// 名称站位符 2010-05-20 wangjf
	public List queryForList(String sql, Map map, Class elementType)
			throws DataAccessException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
					this.getDataSource());
			return namedParameterJdbcTemplate.query(sql, map, new FrmRowMapper(
					elementType));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public int queryForInt(String sql, Map map) throws DataAccessException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
					this.getDataSource());
			return namedParameterJdbcTemplate.queryForInt(sql, map);
		} catch (EmptyResultDataAccessException ex) {
			return 0;
		}
	}

	public String getTalkid(String xtlb, String cdbh, String gnid) {
		String callString = "{call Admin_check_pkg.A201(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				Function function = (Function) this.getParameterObject();
				cstmt.setString(1, function.getXtlb());
				cstmt.setString(2, function.getCdbh());
				cstmt.setString(3, function.getGnid());
				cstmt.registerOutParameter(4, Types.NUMERIC);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.execute();
				String result = "";
				if (cstmt.getLong(4) == 1) {
					result = cstmt.getString(5);
				}
				return result;
			}
		};
		Function function = new Function();
		function.setXtlb(xtlb);
		function.setCdbh(cdbh);
		function.setGnid(gnid);
		callBack.setParameterObject(function);
		String result = (String) this.execute(callString, callBack);
		return result;
	}

	public List<Map<String, Object>> query(String sql, Map<String, String> hm,
			RowMapper map) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				this.getDataSource());
		return namedParameterJdbcTemplate.query(sql, hm, map);
	}
	
	public DbResult getTrans(String pro) throws Exception {
		String callString = "{call " + pro + "(?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				DbResult result = new DbResult();
				cstmt.registerOutParameter(1, Types.NUMERIC);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(1));
				result.setMsg(cstmt.getString(2));
				return result;
			}
		};
		DbResult result = (DbResult) this.execute(callString, callBack);
		return result;
	}



}
