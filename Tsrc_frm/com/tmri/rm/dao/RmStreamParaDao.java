package com.tmri.rm.dao;

import java.util.List;

import com.tmri.rm.bean.RmStreamConsumer;

public interface RmStreamParaDao {

	List<RmStreamConsumer> getConsumerList(String yxjb) throws Exception;

	void updateJobid(String sxl, String jobid, String yxjb) throws Exception;

	String getJobServerUri() throws Exception;

	void saveJdbc(String jdbcurl, String username, String jdbcurl2, String username2, String password, String password2) throws Exception;

	String getJdbcUrl();

	String getJdbcUser();
	
	String getJdbcUrl2();

	String getJdbcUser2();
	
	String getJdbcPwd();
	
	String getJdbcPwd2();

	void updateRunUuid() throws Exception;

	String getFlushInterval();

	String getFlushSize();

	String getHbaseDir();

	String getHbaseZookeeperAddr();

	String getKafkaZookeeperAddr();
}
