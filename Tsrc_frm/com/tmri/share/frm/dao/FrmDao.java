package com.tmri.share.frm.dao;

import com.tmri.share.ora.bean.DbResult;

public interface FrmDao {
	public DbResult save(String procedure) throws Exception;
	public DbResult setOracleData(String pkgName, String procName, Object bean) throws Exception;
}
