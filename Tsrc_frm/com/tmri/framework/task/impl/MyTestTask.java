package com.tmri.framework.task.impl;

import java.text.SimpleDateFormat;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tmri.framework.task.GeneralJob;

public class MyTestTask extends GeneralJob {
	private static int times = 0;
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(sdf.format(System.currentTimeMillis()) + " : " + times++);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
