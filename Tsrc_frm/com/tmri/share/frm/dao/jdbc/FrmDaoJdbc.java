package com.tmri.share.frm.dao.jdbc;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.tmri.share.frm.bean.ProcedureArgs;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;

@Repository
public class FrmDaoJdbc implements FrmDao {
	@Resource
	protected FrmJdbcTemplate jdbcTemplate;
	
	@Autowired
	protected LobHandler lobHandler;

	public DbResult save(String procedure) throws Exception {
		String callString = "{call " + procedure + "(?,?)}";
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
		DbResult result = (DbResult) jdbcTemplate.execute(callString, callBack);
		if(result == null){
			throw new RuntimeException("数据库无返回结果");
		}else if(result.getCode() < 1) {
			throw new RuntimeException(result.getMsg());
		}
		return result;
	}

	public DbResult setOracleData(String pkgName, String procName, Object bean)
			throws Exception {
		String sql = "SELECT SUBSTR(ARGUMENT_NAME, 4, 50) ARGUMENTNAME, POSITION, DATA_TYPE DATATYPE FROM USER_ARGUMENTS WHERE PACKAGE_NAME=? AND OBJECT_NAME=? AND IN_OUT='IN' ORDER BY POSITION";
		List<ProcedureArgs> argsList = jdbcTemplate.queryForList(sql,
				new Object[] { pkgName.toUpperCase(), procName.toUpperCase() },
				ProcedureArgs.class);
		if (argsList == null || argsList.isEmpty()) {
			throw new Exception("设置Oracle对象存储过程名不正确");
		}

		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public DbResult doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				Object[] paras = (Object[]) getParameterObject();
				Object bean = paras[0];
				List<ProcedureArgs> argsList = (List<ProcedureArgs>) paras[1];

				DbResult result = new DbResult();
				Method[] mths = bean.getClass().getDeclaredMethods();
				HashMap<String, Method> mthMap = new HashMap<String, Method>();
				for (Method m : mths) {
					String mthName = m.getName();
					if (mthName.startsWith("get")) {
						mthMap.put(mthName.substring(3).toUpperCase(), m);
					}
				}

				int size = argsList.size();
				for (int idx = 0; idx < size; idx++) {
					ProcedureArgs pa = argsList.get(idx);
					try {
						Method mth = mthMap.get(pa.getArgumentname());
						if (null == mth) {
							throw new RuntimeException("在"
									+ bean.getClass().getName() + "类中，需要参数"
									+ pa.getArgumentname() + "的set方法。");
						}
						Object val = mth.invoke(bean);
						if (null == val) {
							cstmt.setNull(pa.getPosition().intValue(),
									Types.NULL);
						} else if (val instanceof byte[]) {
							LobCreator lobCreator = lobHandler.getLobCreator();
							lobCreator.setBlobAsBytes(cstmt, pa.getPosition()
									.intValue(), (byte[]) val);
						} else {
							cstmt.setObject(pa.getPosition().intValue(), val);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					}
				}

				cstmt.registerOutParameter(size + 1, Types.NUMERIC);
				cstmt.registerOutParameter(size + 2, Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(size + 1));
				result.setMsg(cstmt.getString(size + 2));
				return result;
			}
		};
		callBack.setParameterObject(new Object[] { bean, argsList });
		String callString = "{call " + pkgName + "." + procName + "("
				+ Constants.PROC_PARA.substring(0, argsList.size() * 2 + 3)
				+ ")}";
			
		DbResult result = (DbResult) jdbcTemplate.execute(callString, callBack);
		if (null == result) {
			throw new RuntimeException("数据接口无返回结果");
		} else if (result.getCode() < 1) {
			throw new RuntimeException("数据操作失败, SQLCODE:" + result.getCode()
					+ ", SQLERRMSG:" + result.getMsg());
		}
		return result;
	}
}
