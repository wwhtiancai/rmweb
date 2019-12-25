package com.tmri.share.frm.dao;

import com.tmri.share.frm.bean.RmLog;

public interface SLogDao {
	public void setRmLog(RmLog log) throws Exception;
	public void saveRmLog() throws Exception;
	public void saveRmLog(RmLog log) throws Exception;
	public Object saveRmLogForQuery(Object obj,RmLog log) throws Exception;
}
