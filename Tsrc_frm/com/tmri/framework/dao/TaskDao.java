package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;

import com.tmri.framework.bean.FrmTask;
import com.tmri.framework.bean.FrmTaskLog;
import com.tmri.framework.bean.FrmTaskRowlog;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.util.PageController;

public interface TaskDao{
	public List getTask();

	public FrmTask getOneTask(String xtlb,String rwid);

	public int editTask(FrmTask task);

	public String regJob(Scheduler scheduler,FrmTask task,ApplicationContext appContext) throws Exception;

	public boolean rmvJob(Scheduler scheduler,FrmTask task,ApplicationContext appContext) throws Exception;

	public int editTaskIp(FrmTask task);

	public List getTaskLogsByPagesize(FrmTaskLog log,PageController controller) throws SQLException;

	public SysResult checkTaskValid();

	/**
	 * ����ʼִ��ʱд����־
	 * @param xtlb
	 * @param rwid
	 * @param fhxx
	 * @param bz "JOBMAIN"
	 * @param zxxh
	 * @return ִ�����
	 */
	public String saveRwStratlog(String xtlb,String rwid,String fhxx,String ipdz);

	/**
	 * �������ʱ����ǰ��д���ִ����־��д������ִ�еĽ���ʱ��
	 * @param zxxh
	 * @param flag
	 */
	public int saveRwEndlog(String zxxh,String flag,String fhxx,String bz);

	public String getTaskRunInfo(String Rwid) throws SQLException;
	
	public List getTimeOutTask(String overTime);
	
	public List getFailureTask();
	
	public int getRunTaskTimes(String rwid);

	public List<FrmTaskRowlog> getErrorList(FrmTaskRowlog bean,
			PageController controller);
}
