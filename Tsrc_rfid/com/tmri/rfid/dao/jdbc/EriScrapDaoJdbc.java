package com.tmri.rfid.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.rfid.dao.EriScrapDao;
import com.tmri.share.frm.dao.CommDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;
/*
 *wuweihong
 *2016-1-7
 */
@Repository
public class EriScrapDaoJdbc  extends FrmDaoJdbc implements EriScrapDao{
	@Autowired
	private CommDao commDao;
	@Override
	public String getSeqId() throws Exception{
		String callString = "{? = call  FRM_COMM_PKG.GETSEQLENVAL(?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				try {
					Map map = new HashMap();
					map.put("SEQ", "SEQ_RFID_SCRAP_APPLICATION");
					DbResult result = new DbResult();
					result = commDao.operaPackage("FRM_FUNC_PKG.GETSEQVALUE", map);
					cstmt.setString(2, result.getObj().toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("查询流水号序列出错");
				}
				cstmt.setInt(3, 3);
				cstmt.registerOutParameter(1, Types.VARCHAR);
				cstmt.execute();
				return cstmt.getString(1);
			}
		};
		String result =  String.valueOf(jdbcTemplate.execute(callString, callBack));
		if(result == null){
			throw new RuntimeException("数据库无返回结果");
		}
		return result;
	}
}
