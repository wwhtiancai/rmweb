package com.tmri.framework.task;

/**
 * 任务执行消息定义
 * 
 * @author wu
 * 
 */
public class JobMessage {
	private String time;
	private String message;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
