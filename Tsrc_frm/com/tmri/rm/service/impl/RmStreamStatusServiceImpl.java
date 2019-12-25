package com.tmri.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.bean.RmStreamPartitionStatusBean;
import com.tmri.rm.bean.RmStreamStatusBean;

import com.tmri.rm.dao.RmStreamStatusDao;

import com.tmri.rm.dao.RmStreamParaDao;
import com.tmri.rm.service.RmStreamParaService;

import com.tmri.rm.service.RmStreamStatusService;
import com.tmri.rm.util.StreamMachineUtil;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.util.AES;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

@Service
public class RmStreamStatusServiceImpl implements RmStreamStatusService {

	@Autowired
	private RmStreamStatusDao rmStreamStatusDao;

	@Autowired
	private GSysparaCodeDao gSysparaCodeDao;
	@Autowired
	private RmStreamParaDao rmStreamParaDao;
	@Autowired
	private RmStreamParaService rmStreamParaService;

	public List<RmStreamStatusBean> getStreamStatus(String tpkey, String gpkey) throws Exception {
		if (!StringUtil.checkBN(tpkey) || !StringUtil.checkBN(gpkey)) {
			return null;
		}

		SysPara sysPara = gSysparaCodeDao.getSysPara("00", "2", "STREAMMANAGERURI");
		if (null == sysPara) {
			throw new Exception("未找到流式计算管理节点IP端口配置信息:STREAMMANAGERURI");
		}
		return StreamMachineUtil.getRunStatus(sysPara.getMrz(), tpkey, gpkey);
	}

	public List<RmStreamPartitionStatusBean> getPartitionStatus(String ip, String tpkey, String gpkey) throws Exception {
		if (!StringUtil.checkBN(ip) || !StringUtil.checkBN(tpkey) || !StringUtil.checkBN(gpkey)) {
			return null;
		}

		SysPara sysPara = gSysparaCodeDao.getSysPara("00", "2", "STREAMMANAGERURI");
		if (null == sysPara) {
			throw new Exception("未找到流式计算管理节点IP端口配置信息:STREAMMANAGERURI");
		}
		return StreamMachineUtil.getPartitionStatus(sysPara.getMrz(), ip, tpkey, gpkey);
	}

	public void runJob(String sxl, String tpkey, String gpkey, String rwsl) throws Exception {
		if (!StringUtil.checkBN(sxl)) {
			return;
		}

		String jobId = null;

		if (StreamMachineUtil.isMapReduce(sxl)) {
			SysPara sysPara = gSysparaCodeDao.getSysPara("00", "2", "STREAMMANAGERURI");
			if (null == sysPara) {
				throw new Exception("未找到流式计算管理节点IP端口配置信息:STREAMMANAGERURI");
			}

			String kafkaZookeeperAddr = rmStreamParaService.getKafkaZookeeperAddr();
			String hbaseZookeeperAddr = rmStreamParaService.getHbaseZookeeperAddr();
			String hbaseBaseDir = rmStreamParaService.getHbaseDir();
			String flushSize = rmStreamParaService.getFlushSize();
			String flushInterval = rmStreamParaService.getFlushInterval();
			jobId = StreamMachineUtil.runMapReduceJob(sysPara.getMrz(), sxl, tpkey, kafkaZookeeperAddr, gpkey, rwsl, hbaseZookeeperAddr, hbaseBaseDir,
					flushSize, flushInterval);
		} else {
			String url = AES.decrypt(rmStreamParaDao.getJdbcUrl(), StreamMachineUtil.key);
			String user = AES.decrypt(rmStreamParaDao.getJdbcUser(), StreamMachineUtil.key);
			String pwd = AES.decrypt(rmStreamParaDao.getJdbcPwd(), StreamMachineUtil.getPwdKey());
			String url2 = AES.decrypt(rmStreamParaDao.getJdbcUrl2(), StreamMachineUtil.key);
			String user2 = AES.decrypt(rmStreamParaDao.getJdbcUser2(), StreamMachineUtil.key);
			String pwd2 = AES.decrypt(rmStreamParaDao.getJdbcPwd2(), StreamMachineUtil.getPwdKey());
			DbResult result = rmStreamParaService.testConn(url, user, pwd);
			if (result.getCode() != 1L) {
				throw new Exception(result.getMsg());
			}
			result = rmStreamParaService.testConn2(url2, user2, pwd2);
			if (result.getCode() != 1L) {
				throw new Exception(result.getMsg());
			}

			String jobServerUri = this.rmStreamParaService.getJobServerUri();
			if (!StringUtil.checkBN(jobServerUri)) {
				throw new Exception("未找到流式计算管理节点信息，请先同步");
			}
			jobId = StreamMachineUtil.runJob(jobServerUri, url, user, pwd, sxl, url2, user2, pwd2);
		}

		if (StringUtil.checkBN(jobId)) {
			rmStreamParaDao.updateJobid(sxl, jobId, StreamMachineUtil.getYxjb(gSysparaCodeDao.getSysPara("00", "2", "XTYXMS").getMrz()));
		}
	}

	public void stopJob(String jobId, String sxl) throws Exception {
		if (!StringUtil.checkBN(jobId) || !StringUtil.checkBN(sxl)) {
			return;
		}

		if (StreamMachineUtil.isMapReduce(sxl)) {
			SysPara sysPara = gSysparaCodeDao.getSysPara("00", "2", "STREAMMANAGERURI");
			if (null == sysPara) {
				throw new Exception("未找到流式计算管理节点IP端口配置信息:STREAMMANAGERURI");
			}
			StreamMachineUtil.stopMapReduceJob(jobId, sysPara.getMrz());
		} else {
			String jobServerUri = this.rmStreamParaService.getJobServerUri();
			if (!StringUtil.checkBN(jobServerUri)) {
				throw new Exception("未找到流式计算管理节点信息，请先同步");
			}
			StreamMachineUtil.stopJob(jobId, jobServerUri);
		}

		rmStreamParaDao.updateJobid(sxl, "", StreamMachineUtil.getYxjb(gSysparaCodeDao.getSysPara("00", "2", "XTYXMS").getMrz()));
	}

	public List<RmStreamConsumer> getStreamConsumers(String yxjb, PageController controller) throws Exception {
		List<RmStreamConsumer> list = this.rmStreamStatusDao.getStreamConsumers(yxjb, controller);
		if (list != null) {
			for (RmStreamConsumer bean : list) {
				this.transfer(bean);
			}
		}
		return list;
	}

	private void transfer(RmStreamConsumer bean) throws Exception {
		if (bean == null) {
			return;
		}
		// 获取状态列表
		List<RmStreamStatusBean> statusList = this.getStreamStatus(bean.getTpkey(), bean.getGpkey());

		if (statusList == null) {
			statusList = new ArrayList<RmStreamStatusBean>();
		}
		if (statusList.isEmpty()) {
			RmStreamStatusBean statBean = new RmStreamStatusBean();
			statBean.setCll(0);
			statBean.setJyl(0);
			statBean.setIp(null);
			statBean.setRwsl(0);
			statusList.add(statBean);
		}

		bean.setStatusList(statusList);
		long rwslzs = 0;
		long cllzs = 0;
		long jylzs = 0;
		for (RmStreamStatusBean stat : statusList) {
			rwslzs += stat.getRwsl();
			cllzs += stat.getCll();
			jylzs += stat.getJyl();
			String ip = stat.getIp();
			if (ip == null) {
				stat.setIp("-");
				continue;
			}
			List<RmStreamPartitionStatusBean> partitionStatusList = this.getPartitionStatus(ip, bean.getTpkey(), bean.getGpkey());
			stat.setPartitionStatusList(partitionStatusList);
		}
		bean.setRwslzs(rwslzs);
		bean.setCllzs(cllzs);
		bean.setJylzs(jylzs);
		// 获取运行状态
		RmStreamStaus status = this.getStatus(bean.getJobid(), bean.getSxl());
		if (status == RmStreamStaus.FINSHED) {
			bean.setYxzt(0);
		} else if (status == RmStreamStaus.RUNNING) {
			bean.setYxzt(1);
		}else if (status == RmStreamStaus.ERROR) {
			bean.setYxzt(2);
		}else {
			bean.setYxzt(0);
		}
	}

	public RmStreamStaus getStatus(String jobId, String sxl) throws Exception {
		if (!StringUtil.checkBN(jobId)) {
			return RmStreamStaus.FINSHED;
		}

		if (StreamMachineUtil.isMapReduce(sxl)) {
			SysPara sysPara = gSysparaCodeDao.getSysPara("00", "2", "STREAMMANAGERURI");
			if (null == sysPara) {
				throw new Exception("未找到流式计算管理节点IP端口配置信息:STREAMMANAGERURI");
			}

			return StreamMachineUtil.getMapReduceJobStatus(jobId, sysPara.getMrz());
		} else {
			String jobServerUri = this.rmStreamParaService.getJobServerUri();
			if (!StringUtil.checkBN(jobServerUri)) {
				throw new Exception("未找到流式计算管理节点信息，请先同步");
			}
			return StreamMachineUtil.getJobStatus(jobId, jobServerUri);
		}
	}
}
