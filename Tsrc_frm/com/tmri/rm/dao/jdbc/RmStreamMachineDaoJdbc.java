package com.tmri.rm.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.bean.RmStreamMachine;
import com.tmri.rm.bean.RmStreamTopic;
import com.tmri.rm.dao.RmStreamMachineDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Repository
public class RmStreamMachineDaoJdbc extends FrmDaoJdbc implements RmStreamMachineDao {

	public int deleteMachines(RmStreamMachine bean) throws SQLException {
		StringBuffer sql = new StringBuffer(64);
		List<Object> params = new ArrayList<Object>();
		sql.append(" Delete From rm_stream_machine where 1=1 ");
		if (bean != null) {
			if (StringUtil.checkBN(bean.getJqlx())) {
				sql.append(" and jqlx=? ");
				params.add(bean.getJqlx());
			}
			if (StringUtil.checkBN(bean.getSfgljd())) {
				sql.append(" and sfgljd=? ");
				params.add(bean.getSfgljd());
			}
		}
		return jdbcTemplate.update(sql.toString(), params.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<RmStreamMachine> queryList(RmStreamMachine bean) throws SQLException {
		StringBuffer sql = new StringBuffer(64);
		List<Object> params = new ArrayList<Object>();
		sql.append(" Select jqlx,ip,dkh,sfgljd,gxsj,bz ");
		sql.append(" From rm_stream_machine where 1=1 ");
		if (bean != null) {
			if (StringUtil.checkBN(bean.getJqlx())) {
				sql.append(" and jqlx=? ");
				params.add(bean.getJqlx());
			}
			if (StringUtil.checkBN(bean.getSfgljd())) {
				sql.append(" and sfgljd=? ");
				params.add(bean.getSfgljd());
			}
		}
		sql.append(" Order by gxsj");
		return jdbcTemplate.queryForList(sql.toString(), params.toArray(), RmStreamMachine.class);
	}

	public DbResult insertBatch(final List<RmStreamMachine> list) throws SQLException {
		StringBuffer sql = new StringBuffer(128);
		sql.append(" Insert Into rm_stream_machine ");
		sql.append(" (jqlx,ip,dkh,sfgljd,gxsj,bz) ");
		sql.append(" Values (?,?,?,?,sysdate,'') ");

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int j = 0;
				ps.setString(++j, list.get(i).getJqlx());
				ps.setString(++j, list.get(i).getIp());
				ps.setString(++j, list.get(i).getDkh());
				ps.setString(++j, list.get(i).getSfgljd());
			}

			public int getBatchSize() {
				return list.size();
			}
		});

		DbResult dbResult = new DbResult();
		dbResult.setCode(1);
		dbResult.setMsg("success");
		return dbResult;
	}

	@SuppressWarnings("unchecked")
	public List<RmStreamTopic> queryTopicList(RmStreamTopic bean) throws SQLException {
		StringBuffer sql = new StringBuffer(64);
		List<Object> params = new ArrayList<Object>();
		sql.append(" Select tpkey,sjmc,ptgz,ptsl,yxjb,gxsj,bz ");
		sql.append(" ,(Case when jyw=Frm_Sys_Pkg.Encrypt(tpkey||ptgz||ptsl||yxjb,tpkey)");
		sql.append(" then '1' else '0' End) as jzjyw ");
		sql.append(" From rm_stream_topic where 1=1 ");
		if (bean != null) {
			if (StringUtil.checkBN(bean.getPtgz())) {
				sql.append(" and ptgz=? ");
				params.add(bean.getPtgz());
			}
			if (StringUtil.checkBN(bean.getYxjb())) {
				sql.append(" and yxjb like ? ");
				params.add("%" + bean.getYxjb() + "%");
			}
		}
		return jdbcTemplate.queryForList(sql.toString(), params.toArray(), RmStreamTopic.class);
	}

    public RmStreamTopic getTopicInfo(String tpkey) throws SQLException {
        StringBuffer sql = new StringBuffer(64);
        sql.append(" Select tpkey,sjmc,ptgz,ptsl,yxjb,gxsj,bz ");
        sql.append(" From rm_stream_topic where tpkey=? ");
        return (RmStreamTopic) jdbcTemplate.queryForBean(sql.toString(), new Object[] { tpkey },
                RmStreamTopic.class);
    }

	public DbResult updateTopicBatch(final List<RmStreamTopic> list) throws SQLException {
		StringBuffer sql = new StringBuffer(128);
		sql.append(" Update rm_stream_topic set ptsl=?,jyw= ");
		sql.append(" Frm_Sys_Pkg.Encrypt(tpkey||ptgz||?||yxjb,tpkey) ");
		sql.append(" where tpkey=? ");

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int j = 0;
				ps.setString(++j, list.get(i).getPtsl());
				ps.setString(++j, list.get(i).getPtsl());
				ps.setString(++j, list.get(i).getTpkey());
			}

			public int getBatchSize() {
				return list.size();
			}
		});

		DbResult dbResult = new DbResult();
		dbResult.setCode(1);
		dbResult.setMsg("success");
		return dbResult;
	}

	@SuppressWarnings("unchecked")
	public List<RmStreamConsumer> queryConsumerList(RmStreamConsumer bean) throws SQLException {
		StringBuffer sql = new StringBuffer(64);
		List<Object> params = new ArrayList<Object>();
		sql.append(" Select gpkey,gpmc,tpkey,sxl,rwgz,rwsl,yxjb,gxsj ");
		sql.append(" ,(Case when jyw=Frm_Sys_Pkg.Encrypt(gpkey||tpkey||rwgz||rwsl||yxjb,gpkey)");
		sql.append(" then '1' else '0' End) as jzjyw ");
		sql.append(" From rm_stream_consumer where 1=1 ");
		if (bean != null) {
			if (StringUtil.checkBN(bean.getTpkey())) {
				sql.append(" and tpkey=? ");
				params.add(bean.getTpkey());
			}
			if (StringUtil.checkBN(bean.getYxjb())) {
				sql.append(" and yxjb=? ");
				params.add(bean.getYxjb());
			}
		}
		return jdbcTemplate.queryForList(sql.toString(), params.toArray(), RmStreamConsumer.class);
	}

	public DbResult updateConsumerBatch(final List<RmStreamConsumer> list) throws SQLException {
		StringBuffer sql = new StringBuffer(128);
		sql.append(" Update rm_stream_consumer set rwsl=?, jyw=");
		sql.append(" Frm_Sys_Pkg.Encrypt(gpkey||tpkey||rwgz||?||yxjb,gpkey) ");
		sql.append(" where gpkey=? ");

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int j = 0;
				ps.setString(++j, list.get(i).getRwsl());
				ps.setString(++j, list.get(i).getRwsl());
				ps.setString(++j, list.get(i).getGpkey());
			}

			public int getBatchSize() {
				return list.size();
			}
		});

		DbResult dbResult = new DbResult();
		dbResult.setCode(1);
		dbResult.setMsg("success");
		return dbResult;
	}

	@SuppressWarnings("unchecked")
	public List<RmStreamMachine> queryMachineList() throws Exception {
		String sql = "select ip, wm_concat(jqlx) jqlxs, wm_concat(jqlx || '#' || dkh) jqlxdkhs, max(sfgljd) sfgljd, max(hostname) hostname from ((select * from rm_stream_machine order by ip,jqlx,sfgljd)) group by ip order by ip";
		return jdbcTemplate.queryForList(sql, RmStreamMachine.class);
	}

	public DbResult saveStreamMachines(final List<RmStreamMachine> machineList) throws Exception {
		String sql = "delete from rm_stream_machine";
		this.jdbcTemplate.execute(sql);

		sql = "insert into rm_stream_machine(jqlx, ip, dkh, sfgljd, gxsj, bz,hostname) values(?,?,?,?,sysdate,?,?)";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, machineList.get(i).getJqlx());
				ps.setString(2, machineList.get(i).getIp());
				ps.setString(3, machineList.get(i).getDkh());
				ps.setString(4, machineList.get(i).getSfgljd());
				ps.setString(5, machineList.get(i).getBz());
				ps.setString(6, machineList.get(i).getHostname());
			}

			public int getBatchSize() {
				return machineList.size();
			}
		});

		DbResult result = new DbResult();
		result.setCode(1L);
		result.setMsg("success");
		return result;
	}

	public void deleteMachineSjs() throws Exception {
		String sql = "delete from RM_STREAM_MACHINE_SJ";
		jdbcTemplate.execute(sql);
	}

	public void saveMachineSj(RmStreamMachine streamMachine) throws Exception {
		String tmp = "null";
		if (StringUtil.checkBN(streamMachine.getDkh())) {
			tmp = "'"+streamMachine.getDkh()+"'";
		}
		String sql = "insert into RM_STREAM_MACHINE_SJ(jqlx,ip, dkh, sfgljd, gxsj) values ('"+streamMachine.getJqlx()
		+"','"+streamMachine.getIp()+"',"+tmp+",'"+streamMachine.getSfgljd()+"',sysdate)";
		jdbcTemplate.execute(sql);
	}
}
