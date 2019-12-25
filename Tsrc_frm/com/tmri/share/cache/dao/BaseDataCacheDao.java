package com.tmri.share.cache.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.share.frm.bean.SysPara;

public interface BaseDataCacheDao {

	public List<SysPara> getAllSysParas() throws SQLException;
}
