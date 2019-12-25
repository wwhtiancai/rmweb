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
 * �������ݿⶨ��Job���������Job�ĵ�������,Job������������Ϣ�����ݿ����
 * ͬʱ��Job�������ݿ���ص�Job�Ĺ������
 * <value>1 0/2 * * * ?</value> 2min
 *
 *
 */
public class DatabaseTaskManageJob implements Job {
	private boolean isDebug;
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		Map jobDataMap = ctx.getJobDetail().getJobDataMap();
		ApplicationContext appContext=(ApplicationContext)jobDataMap.get("applicationContext");
		
		//���Ի�ȡ������Ϣ
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
//			System.out.println("ϵͳ�������������");
//			System.out.println("����ر�������Ϣ������޸�ϵͳ����TTMRMֵΪ0");
//			System.out.println("�趨���з�����TASKWEBIP��"+taskwebip);
//			System.out.println("���ط�����IP��"+ip);
//			System.out.println("���ؾ����ַFZJHDZ��"+FZJHDZ);
//		}
//		//ע��ϵͳ����
//		//20111226���޸� jianghailong ip�����ϲ���¼
//		if(taskwebip.equals(ip)){
//			List taskList = this.getRunTaskList(taskwebip,ip,appContext);		
//			if(taskList!=null){
//				Scheduler scheduler = ctx.getScheduler();			
//				this.regJob(taskList,ip,scheduler,appContext);			
//				
//				//��ȡ��������12Сʱ���ϵ�����ͨ���������򴫻ؼ��ϵͳ
//				String statusString = this.getErrorExecStatus(scheduler,taskList);
//				if(statusString.equals("")) statusString = "1";
//				//F1.setBj101(statusString);
//			}
//		}else{
//			if(isDebug){
//				System.out.println("��ǰ������IP��"+ip+"�������趨�������������IP��"+taskwebip+"��");
//			}
//		}
		
		if (servername != null && !servername.equals("")) {
			// ע��ϵͳ����
			// 20111226���޸� jianghailong ip�����ϲ���¼
			if (servername.toLowerCase().indexOf("tomcat") > -1) {
				if (taskwebip.equals(ip)) {
					List taskList = this.getRunTaskList(taskwebip, ip,
							appContext);
					if (taskList != null) {
						Scheduler scheduler = ctx.getScheduler();
						this.regJob(taskList, ip, scheduler, appContext);

						// ��ȡ��������12Сʱ���ϵ�����ͨ���������򴫻ؼ��ϵͳ
						String statusString = this.getErrorExecStatus(
								scheduler, taskList);
						if (statusString.equals(""))
							statusString = "1";
						// F1.setBj101(statusString);
					}
				} else {
					if (isDebug) {
						System.out.println("��ǰ������IP��" + ip + "�������趨�������������IP��"
								+ taskwebip + "��");
					}
				}
			} else {
				ip = servername + "@" + ip;
				if (isDebug) {
					System.out.println("servername+���ط�����IP��" + ip);
				}
				if (taskwebip.equals(ip)) {
					List taskList = this.getRunTaskList(taskwebip, ip,
							appContext);
					if (taskList != null) {
						Scheduler scheduler = ctx.getScheduler();
						this.regJob(taskList, ip, scheduler, appContext);

						// ��ȡ��������12Сʱ���ϵ�����ͨ���������򴫻ؼ��ϵͳ
						String statusString = this.getErrorExecStatus(
								scheduler, taskList);
						if (statusString.equals(""))
							statusString = "1";
						// F1.setBj101(statusString);
					}
				} else {
					if (isDebug) {
						System.out.println("��ǰ������IP��" + ip + "�������趨�������������IP��"
								+ taskwebip + "��");
					}
				}
			}
		} else {
			System.out.println("δ��ȡ��servername�����鼯Ⱥ��");
		}

		if(isDebug){
			System.out.println("ϵͳ������������н���");
		}

	}
	private String getTaskName(List taskList,String rwid){		
		String result="";
		if(rwid.equals("databaseTaskManageJob")){
			result = "ϵͳ����";
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
						qctx.setJobRunTime((spd/60)+"�֣�"+spd+"�룩");
						resultBuffer.append(qctx.getName()+"," + qctx.getFireTime() + "����ʱ��:"+qctx.getJobRunTime() + "\n");
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
		String zxxh=taskDaoJdbc.saveRwStratlog("00","9999","�������",currentMachinceIp);
		//�������У��λ
		SysResult sysResult = taskDaoJdbc.checkTaskValid();
		if(sysResult.getFlag()==0){
			taskDaoJdbc.saveRwEndlog(zxxh,"0",currentMachinceIp + ":" + sysResult.getDesc1(),"");
			return null;
		}
		List tasklist = taskDaoJdbc.getTask();	
		List runableTaskList=new ArrayList();
		if(taskwebip.equals(currentMachinceIp)){			
			//����1�������ڶ��IP������
			FrmTask task = null;
			for(int i=0;i<tasklist.size();i++){
				task = (FrmTask) tasklist.get(i);
				//ȡ��������ϵͳ����������������������IP��ַ�趨
				//if(task.getYxipdz().indexOf(currentMachinceIp)>-1){
					task.setCurrentRunningIp(currentMachinceIp);
					runableTaskList.add(task);
				//}
			}				
		}
		taskDaoJdbc.saveRwEndlog(zxxh,"1","������سɹ�","");
		return runableTaskList;
	}
	
	
	
	
	
	/*private List getRunTransTaskList(String taskwebip,String runip,ApplicationContext appContext){
		TaskDao taskDao=(TaskDao)appContext.getBean(TaskDao.class);
		//�������У��λ
		
		SysResult sysResult = taskDaoJdbc.checkTaskValid();
		if(sysResult.getFlag()==0){
			taskDaoJdbc.saveRwlog("00","9999","0",runip + ":" + sysResult.getDesc1(),"");
			return null;
		}
		
		List tasklist = taskDao.getTransTask();	
		List runableTaskList=new ArrayList();
		if(taskwebip.equals(runip)){			
			//����1�������ڶ��IP������
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
    
	//ע��
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
						     System.out.println("��ʼע��:"+task.getRwid()+task.getRwmc());							
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
										System.out.println("����ע����:�ɹ�,"+task.getRwid());
									}else{
										System.out.println("����ע����:"+r+","+task.getRwid());
									}
									
								}
								iJzrws ++;
							} catch (Exception e) {
								e.printStackTrace();
								errString += e.getMessage() + ",";
								//����ʱ�����쳣
								
							}
						}
					}else{
						if(isDebug){
						     System.out.println("�����Ѵ���:"+task.getRwid());							
						}
						//�������
					}
				}
			}
		} catch (Exception ex) {
			errString += ex.getMessage() + ",";
			ex.printStackTrace();
		}		
		if(errString.equals("")){
			taskDaoJdbc.saveRwEndlog(zxxh,"1",runip + ":���غ�̨����" + iJzrws + "��","");
		}else{
			taskDaoJdbc.saveRwEndlog(zxxh,"0",runip + ":���غ�̨����" + iJzrws + "��","" + errString);
		}	
	}
}
