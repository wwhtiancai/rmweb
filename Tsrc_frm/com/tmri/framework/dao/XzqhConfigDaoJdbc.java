package com.tmri.framework.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

@Repository
public class XzqhConfigDaoJdbc extends FrmDaoJdbc implements XzqhConfigDao {

	@SuppressWarnings("unchecked")
	public List<FrmXzqhLocal> getFrmXzqhLocal(FrmXzqhLocal bean,
			PageController controller) throws Exception {
		/**
		 * select * from( Select n.Xzqh, Nvl(m.Qhmc, n.Qhmc) Qhmc, Nvl(m.Qhsm,
		 * n.Qhsm) Qhsm, m.Sfxnqh, m.Sjxzqh, Nvl(m.Gxsj, n.Gxsj) Gxsj, Nvl(m.Bz,
		 * n.Bz) Bz, nvl(m.whbj, n.whbj) whbj From (Select Xzqh, Qhmc, Qhsm,
		 * Sfxnqh, Sjxzqh, Gxsj, Bz, '1' whbj From Frm_Xzqh_Local) m, (Select
		 * Xzqh, Qhmc, Qhsm, Gxsj, Bz, '0' whbj From Frm_Xzqh_Zhpt) n Where
		 * m.Xzqh(+) = n.Xzqh) where 1=1;
		 * */
		StringBuilder sql = new StringBuilder();
		sql.append("select * from( ");
		sql.append(" Select n.Xzqh, Nvl(m.Qhmc, n.Qhmc) Qhmc, Nvl(m.Qhsm, n.Qhsm) Qhsm, Nvl(m.Sfxnqh, n.Sfxnqh) Sfxnqh, m.Sjxzqh, Nvl(m.Gxsj, n.Gxsj) Gxsj, ");
		sql.append(" Nvl(m.Bz, n.Bz) Bz, nvl(m.whbj, n.whbj) whbj ");
		sql.append(" From (Select Xzqh, Qhmc, Qhsm, Sfxnqh, Sjxzqh, Gxsj, Bz, '1' whbj From Frm_Xzqh_Local) m, ");
		sql.append(" (Select Xzqh, Qhmc, Qhsm, '0' Sfxnqh, Gxsj, Bz, '0' whbj From Frm_Xzqh_Zhpt) n ");
		sql.append(" Where m.Xzqh(+) = n.Xzqh) where 1=1");
		if (StringUtils.isNotBlank(bean.getXzqhHead())) {
			sql.append(" and Xzqh like '").append(bean.getXzqhHead())
					.append("%'");
		}
		if (StringUtils.isNotBlank(bean.getWhbj())) {
			sql.append(" and instr('").append(bean.getWhbj())
					.append("', Whbj) > 0 ");
		}
		if (StringUtils.isNotBlank(bean.getSfxnqh())) {
			sql.append(" and instr('").append(bean.getSfxnqh())
					.append("', Sfxnqh) > 0 ");
		}
		sql.append(" order by xzqh");
		if (controller == null) {
			return this.jdbcTemplate.queryForList(sql.toString(),
					FrmXzqhLocal.class);
		}
		return controller.getWarpedList(sql.toString(), FrmXzqhLocal.class,
				jdbcTemplate);

	}

	@SuppressWarnings("unchecked")
	public List<BasAllxzqh> getBasAllXzqhList(FrmXzqhLocal bean) throws Exception {
		/**
		 * select t.xzqh, t.qhmc from bas_all_xzqh t where t.jlzt='1' and t.sfxnqh='0';
		 * */
		StringBuilder sql = new StringBuilder();
		sql.append("select trim(t.xzqh) xzqh, t.qhmc from bas_all_xzqh t where t.jlzt='1' and t.sfxnqh='0' and t.xzqh not like '%00'");
		if (StringUtils.isNotBlank(bean.getSfxnqh())){
			sql.append(" and t.sfxnqh = '").append(bean.getSfxnqh()).append("'");
		}
		if (StringUtils.isNotBlank(bean.getXzqhHead())){
			sql.append(" and t.xzqh like '").append(bean.getXzqhHead()).append("%'");
		}
		if (StringUtils.isNotBlank(bean.getXzqh())){
			sql.append(" and t.xzqh != '").append(bean.getXzqh()).append("'");
		}
		sql.append(" order by t.xzqh");		
		return jdbcTemplate.queryForList(sql.toString(), BasAllxzqh.class);
	}

	public DbResult saveXzqhList(final List<FrmXzqhLocal> list) throws Exception {
		DbResult result = new DbResult();
		result.setCode(0);
		if (null != list && !list.isEmpty()) {
			String deleteSql = "delete from frm_xzqh_local where xzqh = ?";
			String sql = "insert into frm_xzqh_local (XZQH, QHMC, QHSM, SFXNQH, SJXZQH, GXSJ, CSBJ, BJCSBJ) values (?, ?, ?, ?, ?, sysdate, '0', '0')";
			// 先将要插入的数据清除
			for(FrmXzqhLocal xzqhLocal :  list){
				jdbcTemplate.update(deleteSql, new Object[]{xzqhLocal.getXzqh()});
				jdbcTemplate.update(sql, new Object[]{xzqhLocal.getXzqh(), xzqhLocal.getQhmc(), xzqhLocal.getQhsm(), xzqhLocal.getSfxnqh(), xzqhLocal.getSjxzqh()});
			}			
			result.setCode(1);
			result.setMsg("保存成功！");
		}
		return result;
	}

}
