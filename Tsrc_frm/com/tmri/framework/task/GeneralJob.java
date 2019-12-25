package com.tmri.framework.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;

import com.tmri.share.frm.util.DateUtil;

public abstract class GeneralJob  implements StatefulJob{
	
	/**
	 * 设置任务消息
	 * @param jobContext
	 * @param message
	 * @throws SchedulerException
	 */
	public void putMessage(JobExecutionContext jobExecutionContext,String message) throws SchedulerException{
		JobMessage jm=new JobMessage();
		long current=System.currentTimeMillis();
		Date cd=new Date(current);
		jm.setTime(DateUtil.formatDate(cd, "yyyy-MM-dd HH:mm:ss"));
		jm.setMessage(message);
		jobExecutionContext.getScheduler().getContext().put(jobExecutionContext.getTrigger().getName(), jm);
	}
	/**
	 * 获取任务消息
	 * @param sch
	 * @param jobContext
	 * @return
	 * @throws SchedulerException
	 */
	public JobMessage getJobMessage(Scheduler scheduler,JobExecutionContext jobExecutionContext) throws SchedulerException{
		String key=jobExecutionContext.getTrigger().getJobName();
		Object o=scheduler.getContext().get(key);
		if(o==null){
			return null;
		}else{
			return (JobMessage)o;
		}
	}
   
}