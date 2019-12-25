package com.tmri.rm.dao.jdbc;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.dao.RmStreamStatusDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
@Repository
public class RmStreamStatusDaoJdbc extends FrmDaoJdbc implements
		RmStreamStatusDao {

	@SuppressWarnings("unchecked")
	public List<RmStreamConsumer> getStreamConsumers(String yxjb, PageController controller)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from RM_STREAM_CONSUMER where yxjb='").append(yxjb).append("' and jyw=Frm_Sys_Pkg.Encrypt(gpkey||tpkey||rwgz||rwsl||yxjb,gpkey)  order by gpkey");
		return controller.getWarpedList(sql.toString(), RmStreamConsumer.class,
		        jdbcTemplate);
	}
}
