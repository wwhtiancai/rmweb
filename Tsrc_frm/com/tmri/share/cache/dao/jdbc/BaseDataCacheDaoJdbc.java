package com.tmri.share.cache.dao.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmri.share.cache.dao.BaseDataCacheDao;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;

@Repository
public class BaseDataCacheDaoJdbc extends FrmDaoJdbc implements BaseDataCacheDao {

	public List<SysPara> getAllSysParas() throws SQLException {
		String strSql = "select XTLB,CSLX,GJZ,CSMC,CSSM,MRZ,XGJB,SFXS,XSSX,DMLB,SJGF,CSSX,BZ,'1' JYW,XSXS from frm_syspara order by xtlb,cslx,gjz";
		return jdbcTemplate.queryForList(strSql, SysPara.class);
	}
}
