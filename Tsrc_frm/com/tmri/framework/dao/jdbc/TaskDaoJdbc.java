package com.tmri.framework.dao.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.Checkresult;
import com.tmri.framework.bean.FrmTask;
import com.tmri.framework.bean.FrmTaskLog;
import com.tmri.framework.bean.FrmTaskRowlog;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.TaskDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
@Repository
public class TaskDaoJdbc extends FrmDaoJdbc implements TaskDao{

	/**
	 * 判断任务是否正在运行
	 * 
	 * @return true,在运行;false 不在运行
	 * @throws Exception
	 * @判断两个小时无任务运行日志信息
	 */
	public boolean checktaskrun() throws Exception {
		boolean result = true;
		try {
			String sql = "select to_char(zxsj,'YYYY-MM-DD HH24:MI') zxsj from FRM_TASK_log order by zxsj desc";
			List list = jdbcTemplate.queryForList(sql, FrmTaskLog.class);
			if (list.size() > 0) {
				FrmTaskLog tasklog = (FrmTaskLog) list.get(0);
				sql = "Select sysdate - to_date('" + tasklog.getZxsj()
						+ "','YYYY-MM-DD HH24:MI') From dual";
				String d = (String) this.jdbcTemplate.queryForObject(sql,
						String.class);
				if (d.length() > 4) {
					d = d.substring(0, 4);
				}
				if (Double.parseDouble(d) > 0.083) {
					result = false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

	public List getTask() {
		List list = null;
		try {
			String sql = "select * from FRM_TASK order by xtlb,rwid";
			list = jdbcTemplate.queryForList(sql, FrmTask.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public SysResult checkTaskValid() {
		String callString = "{call FRM_SYS_PKG.checkTaskContentValid(?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();
				cstmt.registerOutParameter(1, Types.NUMERIC);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(1)));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result = (SysResult) jdbcTemplate.execute(callString,
				callBack);
		return result;
	}

	public FrmTask getOneTask(String xtlb, String rwid) {
		List list = null;
		try {
			String sql = "select * from FRM_TASK where xtlb=? and rwid=?";
			Object[] paras = new Object[]{xtlb, rwid};
			list = jdbcTemplate.queryForList(sql, paras, FrmTask.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (FrmTask) list.get(0);
	}

	public int editTask(FrmTask task) {
		int r = 0;
		try {
			String sql = "update  FRM_TASK set rwzt='" + task.getRwzt()
					+ "' where xtlb=? and rwid=?";
			Object[] paras = new Object[]{task.getXtlb(), task.getRwid()};
			r = this.jdbcTemplate.update(sql, paras);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	public int editTaskIp(FrmTask task) {
		String sql = null;
		Object[] paras = null;
		if (task.getYxipdz().equals("")) {
			sql = "update  FRM_TASK set yxipdz=null where xtlb=? and rwid=?";
			paras = new Object[]{task.getXtlb(), task.getRwid()};
		} else {
			sql = "update  FRM_TASK set yxipdz=? where xtlb=? and rwid=?";
			paras = new Object[]{task.getYxipdz(), task.getXtlb(),
					task.getRwid()};
		}
		int r = this.jdbcTemplate.update(sql, paras);
		return r;
	}

	/**
	 * 
	 * @param scheduler
	 * @param task
	 * @param appContext
	 * @throws Exception
	 */
	public String regJob(Scheduler scheduler, FrmTask task,
			ApplicationContext appContext) throws Exception {
		boolean isExist = false;
		JobDetail existJobDetail = scheduler.getJobDetail(task.getRwid(), scheduler.DEFAULT_GROUP);
		if (existJobDetail != null) {
			if (existJobDetail.getName()
					.equals(task.getRwid())) {// exist
				isExist = true;
			}
		}
		if (!isExist) {
			JobDetail jobDetail = this.getJobDetail(scheduler,task.getRwid(), task.getImplc());
			jobDetail.getJobDataMap().put("applicationContext", appContext);
			jobDetail.getJobDataMap().put("taskcontent", task);
			CronTrigger trigger = new CronTrigger(task.getRwid(), scheduler.DEFAULT_GROUP, task.getRwid(), scheduler.DEFAULT_GROUP, task.getZxsj());
			scheduler.addJob(jobDetail, true);
			scheduler.scheduleJob(trigger);
			System.out.println("finish reg task:"+task.getRwid());
		}
		// check result
		existJobDetail = scheduler.getJobDetail(
				task.getRwid(), scheduler.DEFAULT_GROUP);
		if (existJobDetail == null) {
			return "已执行启动操作，但无法获取到任务状态";
		} else {
			return "1";
		}

	}

	/**
	 * 任务不存在true ,如果继续存在返回false
	 * 
	 * @param scheduler
	 * @param task
	 * @param appContext
	 * @return
	 * @throws Exception
	 */
	public boolean rmvJob(Scheduler scheduler, FrmTask task,
			ApplicationContext appContext) throws Exception {
		boolean isExist = false;
		boolean rmvresult = false;
		JobDetail existJobDetail = scheduler.getJobDetail(task.getRwid(),
				scheduler.DEFAULT_GROUP);
		if (existJobDetail != null) {
			if (existJobDetail.getName().equals(task.getRwid())) {// exist
				isExist = true;
			}
		}
		if (isExist) {
			scheduler.deleteJob(task.getRwid(), scheduler.DEFAULT_GROUP);
		}
		// check result
		existJobDetail = scheduler.getJobDetail(task.getRwid(),
				scheduler.DEFAULT_GROUP);
		if (existJobDetail == null) {
			return true;
		} else {
			return false;
		}
	}

	public JobDetail getJobDetail(Scheduler sched, String jobname,String className) {
		JobDetail job = null;
		try {
			Class jobClass = Class.forName(className);
			job = new JobDetail(jobname, sched.DEFAULT_GROUP, jobClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return job;
	}

	// 获取机动车更新日志信息
	public List getTaskLogsByPagesize(FrmTaskLog log, PageController controller)
			throws SQLException {
		String tmpSql = "";
		if (log.getRwid() != null && !log.getRwid().equals("")) {
			tmpSql += " and a.rwid='" + log.getRwid() + "'";
		}
		if (log.getXtlb() != null && !log.getXtlb().equals("")) {
			tmpSql += " and a.xtlb='" + log.getXtlb() + "'";
		}
		if (log.getJgbj() != null && !log.getJgbj().equals("")) {
			tmpSql += " and a.jgbj='" + log.getJgbj() + "'";
		}
		if (log.getZxxh() != null && !log.getZxxh().equals("")) {
			tmpSql += " and a.zxxh='" + log.getZxxh() + "'";
		}
		tmpSql = "select a.ZXXH,a.RWID,to_char(a.ZXSJ,'yyyy-mm-dd hh24:mi:ss') zxsj,a.JGBJ,a.FHXX, nvl(b.rwmc,'系统任务') rwmc "
				+ "from FRM_TASK_LOG a, FRM_TASK b Where a.xtlb=b.xtlb(+) and a.rwid=b.rwid(+) "
				+ tmpSql + " Order By a.ZXSJ Desc";
		return controller.getWarpedList(tmpSql, FrmTaskLog.class, jdbcTemplate);
	}


	public void delWebIp() {
		String callString = "{call Frm_Sys_Pkg.Delwebinfo}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				cstmt.execute();
				return null;
			}
		};
		jdbcTemplate.execute(callString, callBack);
	}

	public void Writewebinfo(String xtlb, String azdm, String sfdl,
			String fzjgip) throws Exception {
		class Para {
			public String p1;

			public String p2;

			public String p3;

			public String p4;
		}
		String callString = "{call Frm_Sys_Pkg.Writewebinfo(?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				Para np = (Para) this.getParameterObject();
				cstmt.setString(1, np.p1);
				cstmt.setString(2, np.p2);
				cstmt.setString(3, np.p3);
				cstmt.setString(4, np.p4);
				cstmt.execute();
				return null;
			}
		};
		Para para = new Para();
		para.p1 = xtlb;
		para.p2 = azdm;
		para.p3 = sfdl;
		para.p4 = fzjgip;
		callBack.setParameterObject(para);
		jdbcTemplate.execute(callString, callBack);
	}

	/**
	 * 解析XML文档并将数据写入webinfo表中
	 * 
	 * @param xml
	 * @throws Exception
	 */
	public void loadFromXml(String xml) throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new ByteArrayInputStream(xml
				.getBytes("GB2312")));
		Element rootElm = doc.getRootElement();
		Element elmhead = rootElm.element("head");
		String code = elmhead.elementText("code");

		if (code.equals("1")) {
			delWebIp();
			Element elmlist = rootElm.element("recordlist");
			List list = elmlist.elements();
			for (int i = 0; i < list.size(); i++) {
				Element elm = (Element) list.get(i);
				String xtlb = elm.elementText("nr1");
				String azdm = elm.elementText("nr2");
				String sfdl = elm.elementText("nr3");
				String fzjgip = elm.elementText("nr4");
				Writewebinfo(xtlb, azdm, sfdl, fzjgip);
			}
		}
	}
	public SysResult saveTempinfo(long commanded, String sql) {
		class Para {
			public long p1;

			public String p2;
		}
		String callString = "{call FRM_SYS_PKG.saveNTIIS_DRV_TEMPINFO(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();
				Para para = (Para) this.getParameterObject();
				cstmt.setLong(1, para.p1);
				cstmt.setString(2, para.p2);
				cstmt.registerOutParameter(3, Types.NUMERIC);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(3)));
				result.setDesc(cstmt.getString(4));
				result.setDesc1(cstmt.getString(5));
				return result;
			}
		};
		Para para = new Para();
		para.p1 = commanded;
		para.p2 = sql;
		callBack.setParameterObject(para);
		SysResult result = (SysResult) jdbcTemplate.execute(callString,callBack);
		return result;
	}
	

	public int saveRwEndlog(String zxxh, String JGBJ, String fhxx, String bz) {
		String callString = "{call Frm_SYS_Pkg.save_RwEndlog(?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				FrmTaskLog taskLog = (FrmTaskLog) this.getParameterObject();
				cstmt.setString(1, taskLog.getZxxh());
				cstmt.setString(2, taskLog.getJgbj());
				cstmt.setString(3, taskLog.getFhxx());
				cstmt.setString(4, taskLog.getBz());
				cstmt.execute();
				return null;
			}
		};
		FrmTaskLog taskLog = new FrmTaskLog();
		taskLog.setZxxh(zxxh);
		taskLog.setJgbj(JGBJ);
		taskLog.setFhxx(fhxx);
		taskLog.setBz(bz);
		callBack.setParameterObject(taskLog);
		jdbcTemplate.execute(callString, callBack);
		return 1;		
	}

	public String saveRwStratlog(String xtlb, String rwid, String fhxx,
			String ipdz) {
		String callString = "{call Frm_SYS_Pkg.Saverwstratlog(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				FrmTaskLog taskLog = (FrmTaskLog) this.getParameterObject();
				cstmt.registerOutParameter(1, Types.VARCHAR);
				cstmt.setString(2, taskLog.getXtlb());
				cstmt.setString(3, taskLog.getRwid());
				cstmt.setString(4, taskLog.getFhxx());
				cstmt.setString(5, taskLog.getIpdz());
				cstmt.setString(6, taskLog.getKssj());
				cstmt.execute();
				taskLog.setZxxh(cstmt.getString(1));
				return taskLog;
			}
		};
		FrmTaskLog taskLog = new FrmTaskLog();
		taskLog.setXtlb(xtlb);
		taskLog.setRwid(rwid);
		taskLog.setFhxx(fhxx);
		taskLog.setIpdz(ipdz);
		long time=System.currentTimeMillis();
		Date d=new Date(time);
		taskLog.setKssj(DateUtil.formatDate(d,"yyyyMMddHHmmss"));
		callBack.setParameterObject(taskLog);
		FrmTaskLog result=(FrmTaskLog)jdbcTemplate.execute(callString, callBack);
		String zxxh=result.getZxxh();
		return zxxh;
	}

	// 获取机动车更新日志信息
	public String getTaskRunInfo(String Rwid) throws SQLException {
		if (Rwid == null) {
			return null;
		}
		String tmpSql = "select substr(client_info,6) from v$session where client_info like ?";
		Object[] paraObjects = new Object[] { Rwid + "%" };
		String result =(String) jdbcTemplate.queryForObject(tmpSql, paraObjects,
				String.class);
		return result;
	}
	public List getTimeOutTask(String overTime){
		String tmpSql = "select distinct b.rwmc result from frm_task_log a,frm_task b where a.rwid=b.rwid and a.zxsj is null and a.kssj>sysdate-2 and a.kssj<=sysdate - 1 and a.kssj<sysdate-(b.zdzxsj + " + overTime + ")/24";
		List list = jdbcTemplate.queryForList(tmpSql,Checkresult.class);
		return list;		
	}
	public List getFailureTask(){
		String tmpSql = "select distinct b.rwmc result from frm_task_log a,frm_task b where a.rwid=b.rwid and a.zxsj>sysdate-1 and a.zxsj<=sysdate and a.jgbj=0";
		List list = jdbcTemplate.queryForList(tmpSql,Checkresult.class);
		return list;		
	}
	public int getRunTaskTimes(String rwid){
		String tmpSql = "select count(*) result from frm_task_log a where a.zxsj>sysdate-1 and a.zxsj<=sysdate and rwid=:rwid";
		HashMap map = new HashMap();
		map.put("rwid",rwid);
		List list = jdbcTemplate.queryForList(tmpSql,map,Checkresult.class);
		Checkresult checkresult = (Checkresult)list.get(0);
		int r = new Integer(checkresult.getResult()).intValue();
		return r;
	}


	@SuppressWarnings("unchecked")
	public List<FrmTaskRowlog> getErrorList(FrmTaskRowlog bean,
			PageController controller) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from FRM_TASK_ROWLOG where 1=1 ");
		if (bean != null){
			if (StringUtils.isNotBlank(bean.getZxxh())){
				sql.append(" and zxxh='").append(bean.getZxxh()).append("'");
			}
		}
		sql.append(" order by zxxh, zjxx");
		return controller.getWarpedList(sql.toString(), FrmTaskRowlog.class, jdbcTemplate);
	}
}


