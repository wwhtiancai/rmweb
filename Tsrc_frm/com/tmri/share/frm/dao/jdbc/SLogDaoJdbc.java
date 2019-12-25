package com.tmri.share.frm.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;

@Repository
public class SLogDaoJdbc implements SLogDao {
	@Resource
	private FrmJdbcTemplate jdbcTemplate;
	
	@Autowired
	public LobHandler lobHandler;
	
	/**
	 * 设置日志类
	 * @param log
	 * @throws Exception
	 */
	public void setRmLog(RmLog log) throws Exception {
		String callString = "{call Rm_Pub_Pkg.Setrm_Log(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				RmLog obj = (RmLog) getParameterObject();
				int maxLen = 2000;
				if (null != obj && StringUtil.checkBN(obj.getCznr())) {
					try {
						if (obj.getCznr().getBytes(StringUtil.CHAR_SET).length >= maxLen) {
							obj.setCznr(StringUtil.substring(obj.getCznr(), maxLen - 1));
						}
					} catch (Exception e) {
					}
				}
				
				FuncUtil.bean2cstmt(obj, "glbm", cstmt, 1);
				FuncUtil.bean2cstmt(obj, "yhdh", cstmt, 2);
				FuncUtil.bean2cstmt(obj, "czry", cstmt, 3);
				FuncUtil.bean2cstmt(obj, "czsj", cstmt, 4);
				FuncUtil.bean2cstmt(obj, "cdbh", cstmt, 5);
				FuncUtil.bean2cstmt(obj, "gnid", cstmt, 6);
				FuncUtil.bean2cstmt(obj, "cznr", cstmt, 7);
				FuncUtil.bean2cstmt(obj, "zjxx", cstmt, 8);
				FuncUtil.bean2cstmt(obj, "kkbh", cstmt, 9);
				FuncUtil.bean2cstmt(obj, "bkxh", cstmt, 10);
				FuncUtil.bean2cstmt(obj, "yjxh", cstmt, 11);
				FuncUtil.bean2cstmt(obj, "hpzl", cstmt, 12);
				FuncUtil.bean2cstmt(obj, "hphm", cstmt, 13);
				FuncUtil.bean2cstmt(obj, "ip", cstmt, 14);
				FuncUtil.bean2cstmt(obj, "bz", cstmt, 15);
				cstmt.execute();
				return null;
			}
		};
		callBack.setParameterObject(log);
		jdbcTemplate.execute(callString,callBack);
	}
	
	public void saveRmLog() throws Exception {		
		String callString = "{call Rm_Pub_Pkg.Saverm_Log()}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
			throws SQLException, DataAccessException {
				cstmt.execute();				
				return null;
			}
		};
		jdbcTemplate.execute(callString, callBack);
	}
	
	public void saveRmLog(RmLog log) throws Exception{
		this.setRmLog(log);
		this.saveRmLog();
	}
	
	public Object saveRmLogForQuery(Object obj,RmLog log) throws Exception{
        if (log == null) {
            return obj;
        }
        if (obj instanceof List) {
			List tmpList = (List) obj;
			log.setCznr("查询列表:" + log.getCznr() + ".得到" + tmpList.size()
					+ "条记录.");
		}else{
			log.setCznr("查询详细:" + log.getCznr() + ".得到1条记录.");
		}
		this.setRmLog(log);
		this.saveRmLog();
		return obj;
	}
}
