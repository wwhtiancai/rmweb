package com.tmri.rm.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.dao.RmStreamParaDao;
import com.tmri.rm.service.RmStreamParaService;
import com.tmri.rm.util.StreamMachineUtil;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.util.AES;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Service
public class RmStreamParaServiceImpl implements RmStreamParaService {
	@Autowired
	private RmStreamParaDao rmStreamParaDao;
	@Autowired
	private SLogDao sLogDao;
	
	public DbResult testConn(String url, String username, String password) throws Exception {
		DbResult result = new DbResult();

		if (!StringUtil.checkBN(url) || !StringUtil.checkBN(username) || !StringUtil.checkBN(password)) {
			result.setCode(-1L);
			result.setMsg("传入参数不能为空");
			return result;
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url.trim(), username, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from frm_sysuser");
			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count < 1) {
				result.setCode(-3L);
				result.setMsg("不是集成指挥平台数据数据库");
				return result;
			}

		} catch (Exception e) {
			result.setCode(-2L);
			if (null != e.getMessage() && e.getMessage().contains("ORA-00942")) {
				result.setMsg("不是集成指挥平台数据数据库");
			} else {
				result.setMsg(e.getMessage());
			}
			return result;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		result.setCode(1L);
		result.setMsg("success");
		return result;
	}

	public DbResult testConn(String url, String username, String password, String yhdh) throws Exception {
		DbResult result = new DbResult();

		if (!StringUtil.checkBN(url) || !StringUtil.checkBN(username) || !StringUtil.checkBN(password)) {
			result.setCode(-1L);
			result.setMsg("传入参数不能为空");
			return result;
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url.trim(), username, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from frm_sysuser where yhdh='" + yhdh + "'");
			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count < 1) {
				result.setCode(-3L);
				result.setMsg("不是集成指挥平台数据数据库");
				return result;
			}

		} catch (Exception e) {
			result.setCode(-2L);
			if (null != e.getMessage() && e.getMessage().contains("ORA-00942")) {
				result.setMsg("不是集成指挥平台数据数据库");
			} else {
				result.setMsg(e.getMessage());
			}
			return result;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		result.setCode(1L);
		result.setMsg("success");
		return result;
	}

	public DbResult testConn2(String url, String username, String password) throws Exception {
		DbResult result = new DbResult();

		if (!StringUtil.checkBN(url) || !StringUtil.checkBN(username) || !StringUtil.checkBN(password)) {
			result.setCode(-1L);
			result.setMsg("传入参数不能为空");
			return result;
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url.trim(), username, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from vio_jdczp_temp");
			if (!rs.next()) {
				result.setCode(-3L);
				result.setMsg("图片数据库必须初始化与基础数据库同样的数据表和存储过程");
				return result;
			}

		} catch (Exception e) {
			result.setCode(-2L);
			if (null != e.getMessage() && e.getMessage().contains("ORA-00942")) {
				result.setMsg("图片数据库必须初始化与基础数据库同样的数据表和存储过程");
			} else {
				result.setMsg(e.getMessage());
			}
			return result;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		result.setCode(1L);
		result.setMsg("success");
		return result;
	}

	public List<RmStreamConsumer> getConsumerList(String xtyxms) throws Exception {
		return this.rmStreamParaDao.getConsumerList(StreamMachineUtil.getYxjb(xtyxms));
	}

	public void updateJobid(List<RmStreamConsumer> resList, RmLog log, String yxjb) throws Exception {
		if (null != resList && !resList.isEmpty()) {
			for (RmStreamConsumer rmStreamConsumer : resList) {
				if (null != rmStreamConsumer && StringUtil.checkBN(rmStreamConsumer.getSxl()) && StringUtil.checkBN(rmStreamConsumer.getJobid())) {
					this.rmStreamParaDao.updateJobid(rmStreamConsumer.getSxl(), rmStreamConsumer.getJobid(), yxjb);
				}
			}
		}
		sLogDao.saveRmLog(log);
	}

	public String getJobServerUri() throws Exception {
		return this.rmStreamParaDao.getJobServerUri();
	}

	public void saveJdbc(String url, String username, String password, String url2, String username2, String password2) throws Exception {
		this.rmStreamParaDao.saveJdbc(AES.encrypt(url, StreamMachineUtil.key), AES.encrypt(username, StreamMachineUtil.key), AES.encrypt(url2,
				StreamMachineUtil.key), AES.encrypt(username2, StreamMachineUtil.key), AES.encrypt(password, StreamMachineUtil.getPwdKey()), AES.encrypt(
				password2, StreamMachineUtil.getPwdKey()));
	}

	public String getJdbcUrl() {
		String url = this.rmStreamParaDao.getJdbcUrl();

		try {
			url = AES.decrypt(url, StreamMachineUtil.key);
		} catch (Exception e) {
			url = "";
		}

		return url;
	}

	public String getJdbcUser() {
		String user = this.rmStreamParaDao.getJdbcUser();

		try {
			user = AES.decrypt(user, StreamMachineUtil.key);
		} catch (Exception e) {
			user = "";
		}

		return user;
	}

	public String getJdbcUrl2() {
		String url = this.rmStreamParaDao.getJdbcUrl2();

		try {
			url = AES.decrypt(url, StreamMachineUtil.key);
		} catch (Exception e) {
			url = "";
		}

		return url;
	}

	public String getJdbcUser2() {
		String user = this.rmStreamParaDao.getJdbcUser2();

		try {
			user = AES.decrypt(user, StreamMachineUtil.key);
		} catch (Exception e) {
			user = "";
		}

		return user;
	}

	public void updateRunUuid() throws Exception {
		this.rmStreamParaDao.updateRunUuid();
	}

	public String getFlushInterval() {
		return this.rmStreamParaDao.getFlushInterval();
	}

	public String getFlushSize() {
		return this.rmStreamParaDao.getFlushSize();
	}

	public String getHbaseDir() {
		return this.rmStreamParaDao.getHbaseDir();
	}

	public String getHbaseZookeeperAddr() {
		return this.rmStreamParaDao.getHbaseZookeeperAddr();
	}

	public String getKafkaZookeeperAddr() {
		return this.rmStreamParaDao.getKafkaZookeeperAddr();
	}
}
