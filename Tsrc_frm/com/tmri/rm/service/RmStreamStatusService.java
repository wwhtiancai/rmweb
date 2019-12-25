package com.tmri.rm.service;

import java.util.List;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.bean.RmStreamPartitionStatusBean;
import com.tmri.rm.bean.RmStreamStatusBean;
import com.tmri.share.frm.util.PageController;

public interface RmStreamStatusService {
	enum RmStreamStaus {
		/**
		 * 运行中
		 */
		RUNNING,
		
		/**
		 * 存在错误
		 */
		ERROR,

		/**
		 * 未运行
		 */
		FINSHED;
	}

	List<RmStreamStatusBean> getStreamStatus(String tpkey, String gpkey) throws Exception;

	List<RmStreamPartitionStatusBean> getPartitionStatus(String ip, String tpkey, String gpkey) throws Exception;

	RmStreamStaus getStatus(String jobId, String sxl) throws Exception;

	void runJob(String sxl, String tpkey, String gpkey, String rwsl) throws Exception;

	void stopJob(String jobId, String sxl) throws Exception;

	List<RmStreamConsumer> getStreamConsumers(String yxjb, PageController controller) throws Exception;
}
