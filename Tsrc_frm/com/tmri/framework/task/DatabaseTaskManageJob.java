package com.tmri.framework.task;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.tmri.framework.bean.FrmTask;
import com.tmri.framework.bean.QJobExecutionContext;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.TaskDao;
import com.tmri.framework.dao.jdbc.TaskDaoJdbc;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.NetUtil;

/**
 * 加载数据库定义Job到运行这个Job的调度器上,Job的运行配置信息从数据库加载
 * 同时该Job管理数据库加载的Job的工作情况
 * <value>1 0/2 * * * ?</value> 2min
 *
 *
 */
public class DatabaseTaskManageJob implements Job {
	private boolean isDebug;
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		Map jobDataMap = ctx.getJobDetail().getJobDataMap();
		ApplicationContext appContext=(ApplicationContext)jobDataMap.get("applicationContext");
		
		//测试获取容器信息
		WebApplicationContext webCtx = null;
        ServletContext srvCtx = null;
        if (appContext instanceof WebApplicationContext){
             webCtx = (WebApplicationContext) appContext;
             srvCtx = webCtx.getServletContext();
             String serverKey="com.ibm.websphere.servlet.application.host";
             String serverKeyValue="";
            // System.out.println("(serverKey,serverKeyValue):("+serverKey+","+srvCtx.getAttribute(serverKey)+")");
        }

		
	
		GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)appContext.getBean(GSysparaCodeService.class);		
		InetAddress localhost;
		String ip = "";
		String FZJHDZ="";
		try{
			//localhost=InetAddress.getLocalHost();
			
			FZJHDZ=gSysparaCodeService.getSysParaValue("00", "FZJHDZ");
			ip = NetUtil.getLocalFiltedIP(FZJHDZ);
		}catch(Exception e){
			e.printStackTrace();
		}
		String taskwebip = gSysparaCodeService.getSysParaValue("00","TASKWEBIP");
		String debugMode=gSysparaCodeService.getSysParaValue("31", "TTMRM");
		if(debugMode!=null && debugMode.equals("1")){
		   isDebug=true;	
		}
		String servername=Constants.SYS_SERVERNAME;
		
//		if(isDebug){
//			System.out.println("系统任务管理器启动");
//			System.out.println("如需关闭任务信息输出，修改系统参数TTMRM值为0");
//			System.out.println("设定运行服务器TASKWEBIP："+taskwebip);
//			System.out.println("本地服务器IP："+ip);
//			System.out.println("负载均衡地址FZJHDZ："+FZJHDZ);
//		}
//		//注册系统任务
//		//20111226日修改 jianghailong ip不符合不记录
//		if(taskwebip.equals(ip)){
//			List taskList = this.getRunTaskList(taskwebip,ip,appContext);		
//			if(taskList!=null){
//				Scheduler scheduler = ctx.getScheduler();			
//				this.regJob(taskList,ip,scheduler,appContext);			
//				
//				//获取持续运行12小时以上的任务，通过心跳程序传回监管系统
//				String statusString = this.getErrorExecStatus(scheduler,taskList);
//				if(statusString.equals("")) statusString = "1";
//				//F1.setBj101(statusString);
//			}
//		}else{
//			if(isDebug){
//				System.out.println("当前服务器IP（"+ip+"）不是设定运行任务服务器IP（"+taskwebip+"）");
//			}
//		}
		
		if (servername != null && !servername.equals("")) {
			// 注册系统任务
			// 20111226日修改 jianghailong ip不符合不记录
			if (servername.toLowerCase().indexOf("tomcat") > -1) {
				if (taskwebip.equals(ip)) {
					List taskList = this.getRunTaskList(taskwebip, ip,
							appContext);
					if (taskList != null) {
						Scheduler scheduler = ctx.getScheduler();
						this.regJob(taskList, ip, scheduler, appContext);

						// 获取持续运行12小时以上的任务，通过心跳程序传回监管系统
						String statusString = this.getErrorExecStatus(
								scheduler, taskList);
						if (statusString.equals(""))
							statusString = "1";
						// F1.setBj101(statusString);
					}
				} else {
					if (isDebug) {
						System.out.println("当前服务器IP（" + ip + "）不是设定运行任务服务器IP（"
								+ taskwebip + "）");
					}
				}
			} else {
				ip = servername + "@" + ip;
				if (isDebug) {
					System.out.println("servername+本地服务器IP：" + ip);
				}
				if (taskwebip.equals(ip)) {
					List taskList = this.getRunTaskList(taskwebip, ip,
							appContext);
					if (taskList != null) {
						Scheduler scheduler = ctx.getScheduler();
						this.regJob(taskList, ip, scheduler, appContext);

						// 获取持续运行12小时以上的任务，通过心跳程序传回监管系统
						String statusString = this.getErrorExecStatus(
								scheduler, taskList);
						if (statusString.equals(""))
							statusString = "1";
						// F1.setBj101(statusString);
					}
				} else {
					if (isDebug) {
						System.out.println("当前服务器IP（" + ip + "）不是设定运行任务服务器IP（"
								+ taskwebip + "）");
					}
				}
			}
		} else {
			System.out.println("未获取到servername，请检查集群！");
		}

		if(isDebug){
			System.out.println("系统任务管理器运行结束");
		}

	}
	private String getTaskName(List taskList,String rwid){		
		String result="";
		if(rwid.equals("databaseTaskManageJob")){
			result = "系统任务";
		}else{
			for(int i=0;i<taskList.size();i++){
				FrmTask task=(FrmTask)taskList.get(i);
				if(task.getRwid().equals(rwid)){
					result=task.getRwmc();
					break;
				}
			}			
		}
		return result;
	}
	private String getErrorExecStatus(Scheduler sch,List qtaskList){
		StringBuffer resultBuffer = new StringBuffer();
		try{
			List taskList=sch.getCurrentlyExecutingJobs();
			QJobExecutionContext qctx=new QJobExecutionContext();
			for(int i=0;i<taskList.size();i++){
				JobExecutionContext ctx=(JobExecutionContext)taskList.get(i);
				Date s=ctx.getFireTime();
				if(s!=null){
					long st=s.getTime();
					long ed=System.currentTimeMillis();
					long spd=(ed-st)/1000;
					if(spd>12*60*60){
						qctx.setName(getTaskName(qtaskList,ctx.getJobDetail().getName()));
						qctx.setFireTime(DateUtil.formatDate(ctx.getFireTime()));
						qctx.setJobRunTime((spd/60)+"分（"+spd+"秒）");
						resultBuffer.append(qctx.getName()+"," + qctx.getFireTime() + "持续时间:"+qctx.getJobRunTime() + "\n");
					}					
				}				
			}
		}catch(SchedulerException e){
			e.printStackTrace();
		}
		return resultBuffer.toString();

	}
	private List getRunTaskList(String taskwebip,String currentMachinceIp,ApplicationContext appContext){
		TaskDao taskDaoJdbc=(TaskDao)appContext.getBean(TaskDao.class);
		String zxxh=taskDaoJdbc.saveRwStratlog("00","9999","任务加载",currentMachinceIp);
		//检查任务校验位
		SysResult sysResult = taskDaoJdbc.checkTaskValid();
		if(sysResult.getFlag()==0){
			taskDaoJdbc.saveRwEndlog(zxxh,"0",currentMachinceIp + ":" + sysResult.getDesc1(),"");
			return null;
		}
		List tasklist = taskDaoJdbc.getTask();	
		List runableTaskList=new ArrayList();
		if(taskwebip.equals(currentMachinceIp)){			
			//考虑1个任务在多个IP上运行
			FrmTask task = null;
			for(int i=0;i<tasklist.size();i++){
				task = (FrmTask) tasklist.get(i);
				//取消传输用系统任务启动遗留下来的运行IP地址设定
				//if(task.getYxipdz().indexOf(currentMachinceIp)>-1){
					task.setCurrentRunningIp(currentMachinceIp);
					runableTaskList.add(task);
				//}
			}				
		}
		taskDaoJdbc.saveRwEndlog(zxxh,"1","任务加载成功","");
		return runableTaskList;
	}
	
	
	
	
	
	/*private List getRunTransTaskList(String taskwebip,String runip,ApplicationContext appContext){
		TaskDao taskDao=(TaskDao)appContext.getBean(TaskDao.class);
		//检查任务校验位
		
		SysResult sysResult = taskDaoJdbc.checkTaskValid();
		if(sysResult.getFlag()==0){
			taskDaoJdbc.saveRwlog("00","9999","0",runip + ":" + sysResult.getDesc1(),"");
			return null;
		}
		
		List tasklist = taskDao.getTransTask();	
		List runableTaskList=new ArrayList();
		if(taskwebip.equals(runip)){			
			//考虑1个任务在多个IP上运行
			FrmTask task = null;
			for(int i=0;i<tasklist.size();i++){
				task = (FrmTask) tasklist.get(i);
				if(task.getYxipdz().indexOf(runip)>-1){
					runableTaskList.add(task);
				}
			}				
		}
		return runableTaskList;
 	}*/
    
	//注册
	private void regJob(List taskList,String runip,Scheduler scheduler,ApplicationContext appContext){
		TaskDaoJdbc taskDaoJdbc=(TaskDaoJdbc)appContext.getBean(TaskDao.class);
		String zxxh=taskDaoJdbc.saveRwStratlog("00","9999","",runip);
		String errString = "";
		int iJzrws = 0;
		try {
			if (taskList != null) {
				for (int i = 0; i < taskList.size(); i++) {
					FrmTask task = (FrmTask) taskList.get(i);
					if(task.getRwzt().equals("1")){
						if(isDebug){
						     System.out.println("开始注册:"+task.getRwid()+task.getRwmc());							
						}
						boolean isExist=false;
						JobDetail existJobDetail =  scheduler.getJobDetail(task.getRwid(), Scheduler.DEFAULT_GROUP);
						if(existJobDetail!=null){
							if(existJobDetail.getName().equals(task.getRwid())){//exist
								isExist=true;
							}
						}
						if(!isExist){
							try {
								/*
								if(task.getTdlx()!=null && !task.getTdlx().equals("")){
									Map setupMap=taskDaoJdbc.getTransSetup(runip, task.getRwid());
									if(setupMap!=null){
										task.setRunningIp(runip);
										task.setRunningJqmc((String)setupMap.get("jqmc"));
										task.setRunningTdbh((String)setupMap.get("tdbh"));
									}
								}
								*/
								String r=taskDaoJdbc.regJob(scheduler,task, appContext);
								if(isDebug){
									if(r.equals("1")){
										System.out.println("任务注册结果:成功,"+task.getRwid());
									}else{
										System.out.println("任务注册结果:"+r+","+task.getRwid());
									}
									
								}
								iJzrws ++;
							} catch (Exception e) {
								e.printStackTrace();
								errString += e.getMessage() + ",";
								//启动时报出异常
								
							}
						}
					}else{
						if(isDebug){
						     System.out.println("任务已存在:"+task.getRwid());							
						}
						//不予理会
					}
				}
			}
		} catch (Exception ex) {
			errString += ex.getMessage() + ",";
			ex.printStackTrace();
		}		
		if(errString.equals("")){
			taskDaoJdbc.saveRwEndlog(zxxh,"1",runip + ":加载后台任务" + iJzrws + "个","");
		}else{
			taskDaoJdbc.saveRwEndlog(zxxh,"0",runip + ":加载后台任务" + iJzrws + "个","" + errString);
		}	
	}
}
