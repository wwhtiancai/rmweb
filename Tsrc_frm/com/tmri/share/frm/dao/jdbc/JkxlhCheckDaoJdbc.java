package com.tmri.share.frm.dao.jdbc;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmri.share.frm.dao.JkxlhCheckDao;

@Repository
public class JkxlhCheckDaoJdbc extends FrmDaoJdbc implements JkxlhCheckDao {

	public Map<String, Object>  queryControlByJklxh(String jkxlh) {
		String sql = "SELECT * FROM RM_EWS_CONTROL WHERE JKXLH=?";
		return jdbcTemplate.queryForMap(sql, jkxlh);
	}
	
	public Map<String, Object>  queryJkxlhCreator(Map<String, Object> para) {
		//bean.getXtlb() + "$" + bean.getAzdm() + "#" + bean.getDyrjkfdw() + "@" + bean.getDyfzjg()
		String keys = para.get("XTLB") + "$" + para.get("AZDM") + "#" + para.get("DYRJKFDW") + "@" + para.get("DYFZJG");
		String jkxlhSql = "SELECT EWS_PKG.CreateEwsJkxlh(?) JKXLH FROM DUAL";
		Map<String, Object> jkxlh = this.jdbcTemplate.queryForMap(jkxlhSql, keys);
		return jkxlh;
	}
	
	public Map<String, Object> queryControlJyw(Map<String, Object> appformBean) {
		String jywSql = "SELECT EWS_PKG.Enmycode2(KSIP||JSIP||TO_CHAR(JKQSRQ,'YYYYMMDD')||TO_CHAR(JKJZRQ,'YYYYMMDD'), KFWJK) JYW FROM RM_EWS_CONTROL WHERE XTLB=? AND AZDM=? AND DYRJKFDW=? AND DYFZJG=?";
		Map<String, Object> jyw = this.jdbcTemplate.queryForMap(jywSql, appformBean.get("XTLB"), appformBean.get("AZDM"), appformBean.get("DYRJKFDW"), appformBean.get("DYFZJG"));
		return jyw;
	}
	
	public Map<String, Object> queryHf(Map<String, Object> appformBean, String jkid) {
		String sql = "SELECT * FROM RM_EWS_HF WHERE XTLB=? AND AZDM=? AND DYRJKFDW=? AND DYFZJG=? AND JKID=?";
		Map<String, Object> obj = this.jdbcTemplate.queryForMap(sql, appformBean.get("XTLB"), appformBean.get("AZDM"), appformBean.get("DYRJKFDW"), appformBean.get("DYFZJG"), jkid);
		return obj;
	}
	
	public Map<String, Object> getContent(String jkid) {
		String sql = "SELECT * FROM RM_EWS_CONTENT WHERE JKID=? AND JYW=EWS_PKG.ENMYCODE(JKID||JKZT||RZBJ||ZGPFWCSXZ, FZL||SXL||FHL||JDMC)";
		return jdbcTemplate.queryForMap(sql, jkid);
	}
}
