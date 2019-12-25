package com.tmri.rm.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.tmri.rm.bean.RmArgs;
import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.dao.RmStreamParaDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;

@Repository
public class RmStreamParaDaoJdbc extends FrmDaoJdbc implements RmStreamParaDao {

	@SuppressWarnings("unchecked")
	public List<RmStreamConsumer> getConsumerList(String yxjb) throws Exception {
		String sql = "select * from rm_stream_consumer where yxjb = '" + yxjb + "'";
		return jdbcTemplate.queryForList(sql, RmStreamConsumer.class);
	}

	public void updateJobid(String sxl, String jobid, String yxjb) throws Exception {
		String sql = "update rm_stream_consumer set jobid='" + jobid + "' where sxl='" + sxl + "' and yxjb = '" + yxjb + "'";
		jdbcTemplate.execute(sql);
	}

	public String getJobServerUri() throws Exception {
		try {
			return jdbcTemplate
					.queryForObject("select ip || ':' || dkh from rm_stream_machine where jqlx = '2' and sfgljd = '1' and rownum = 1 ", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public void saveJdbc(String jdbcurl, String username, String jdbcurl2, String username2, String password, String password2) throws Exception {
		String sql = "delete from rm_args where gjz in ('RM_STREAM_DB_CONN_URL', 'RM_STREAM_DB_CONN_UNAME','RM_STREAM_DB_CONN_URL_PHOTO', 'RM_STREAM_DB_CONN_UNAME_PHOTO', 'RM_STREAM_DB_CONN_PWD', 'RM_STREAM_DB_CONN_PWD_PHOTO')";
		jdbcTemplate.execute(sql);

		sql = "insert into rm_args(gjz, csz) values(?,?)";
		final List<RmArgs> argList = new ArrayList<RmArgs>();
		argList.add(new RmArgs("RM_STREAM_DB_CONN_URL", jdbcurl));
		argList.add(new RmArgs("RM_STREAM_DB_CONN_UNAME", username));
		argList.add(new RmArgs("RM_STREAM_DB_CONN_PWD", password));
		argList.add(new RmArgs("RM_STREAM_DB_CONN_URL_PHOTO", jdbcurl2));
		argList.add(new RmArgs("RM_STREAM_DB_CONN_UNAME_PHOTO", username2));
		argList.add(new RmArgs("RM_STREAM_DB_CONN_PWD_PHOTO", password2));

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, argList.get(i).getGjz());
				ps.setString(2, argList.get(i).getCsz());
			}

			public int getBatchSize() {
				return argList.size();
			}
		});
	}

	public String getJdbcUrl() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_DB_CONN_URL'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getJdbcUser() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_DB_CONN_UNAME'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getJdbcUrl2() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_DB_CONN_URL_PHOTO'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getJdbcUser2() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_DB_CONN_UNAME_PHOTO'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getJdbcPwd() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_DB_CONN_PWD'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getJdbcPwd2() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_DB_CONN_PWD_PHOTO'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public void updateRunUuid() throws Exception {
		String sql = "delete from rm_args where gjz = 'RM_STREAM_WATCH_UUID'";
		this.jdbcTemplate.execute(sql);
		sql = "insert into rm_args(gjz,csz) values('RM_STREAM_WATCH_UUID','" + UUID.randomUUID() + "')";
		this.jdbcTemplate.execute(sql);
	}

	public String getFlushInterval() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz='RM_STREAM_MAP_REDUCE_FLUSH_INTERVAL'", String.class);
		} catch (Exception e) {
			return "1000";
		}
	}

	public String getFlushSize() {
		try {
			return jdbcTemplate.queryForObject("select csz from rm_args where gjz = 'RM_STREAM_MAP_REDUCE_FLUSH_SIZE'", String.class);
		} catch (Exception e) {
			return "100";
		}
	}

	public String getHbaseDir() {
		try {
			return jdbcTemplate.queryForObject("select bz from rm_stream_machine where jqlx = '4' and bz is not null and rownum = 1", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getHbaseZookeeperAddr() {
		try {
			return jdbcTemplate.queryForObject("select wm_concat(ip || ':' || dkh) from rm_stream_machine where jqlx = '4'", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getKafkaZookeeperAddr() {
		try {
			return jdbcTemplate.queryForObject("select wm_concat(ip || ':' || dkh) from rm_stream_machine where jqlx = '3'", String.class);
		} catch (Exception e) {
			return null;
		}
	}
}
