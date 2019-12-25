package com.tmri.pub.bean;

import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class CommQuery extends HashMap implements Serializable {
	
	public CommQuery(){
		
	}
	public CommQuery(String yhdh,String ipdz){
		this.put("yhdh", yhdh);
		this.put("ipdz", ipdz);
	}
	
	public CommQuery(String yhdh,String ipdz,HttpSession session,String yzm){
		this.put("yhdh", yhdh);
		this.put("ipdz", ipdz);
		this.put("session", session);
		this.put("yzm", yzm);
	}

	//返回最大行数
	private int maxrownum=100;
	//查询条件
	private HashMap map;
	//查询条件sql
	private String sql;
	public int getMaxrownum() {
		return maxrownum;
	}
	public void setMaxrownum(int maxrownum) {
		this.maxrownum = maxrownum;
	}
	public HashMap getMap() {
		return map;
	}
	public void setMap(HashMap map) {
		this.map = map;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public void putVal(String key,Object val){
		if(val!=null){
			this.put(key, val);
		}
	}
	
//	public void putVal(String key,HttpSession session){
//		if(session!=null){
//			this.put(key, session);
//		}
//	}	

}
