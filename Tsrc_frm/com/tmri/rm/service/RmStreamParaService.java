package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.ora.bean.DbResult;

public interface RmStreamParaService {
	DbResult testConn(String url, String username, String password) throws Exception;
	
	DbResult testConn(String url, String username, String password, String yhdh) throws Exception;
	
	DbResult testConn2(String url, String username, String password) throws Exception;

	List<RmStreamConsumer> getConsumerList(String xtyxms) throws Exception;

	void updateJobid(List<RmStreamConsumer> resList, RmLog rmLog, String yxjb) throws Exception;

	String getJobServerUri() throws Exception;

	void saveJdbc(String url, String username, String password, String url2, String username2, String password2) throws Exception;

	String getJdbcUrl();

	String getJdbcUser();
	
	String getJdbcUrl2();

	String getJdbcUser2();

	void updateRunUuid() throws Exception;

	String getKafkaZookeeperAddr();

	String getHbaseZookeeperAddr();

	String getHbaseDir();

	String getFlushSize();

	String getFlushInterval();
}
