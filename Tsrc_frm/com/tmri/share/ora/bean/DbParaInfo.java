package com.tmri.share.ora.bean;

import java.sql.CallableStatement;
import java.util.Map;

import org.springframework.jdbc.support.lob.DefaultLobHandler;

public class DbParaInfo {
	private int check;
	private String callkey;
	private String procname;
	private CallableStatement cstmt;
	private Map<String,Object> hashmap;
	private Object object;
	
	public String getProcname() {
		return procname;
	}

	public void setProcname(String procname) {
		this.procname = procname;
	}

	public CallableStatement getCstmt() {
		return cstmt;
	}

	public void setCstmt(CallableStatement cstmt) {
		this.cstmt = cstmt;
	}

	public Map<String,Object> getHashmap() {
		return hashmap;
	}

	public void setHashmap(Map<String,Object> hashmap) {
		this.hashmap = hashmap;
	}	
	
	
	private DefaultLobHandler lobHandler;
	
	public DefaultLobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(DefaultLobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public String getCallkey() {
		return callkey;
	}

	public void setCallkey(String callkey) {
		this.callkey = callkey;
	}
}
