package com.tmri.framework.bean;

import java.io.Serializable;

public class SessionKey implements Serializable{
	private String randname;	
	private String randvalue;
	private String randkey;
	public String getRandkey(){
		return randkey;
	}
	public void setRandkey(String randkey){
		this.randkey=randkey;
	}
	public String getRandname(){
		return randname;
	}
	public void setRandname(String randname){
		this.randname=randname;
	}
	public String getRandvalue(){
		return randvalue;
	}
	public void setRandvalue(String randvalue){
		this.randvalue=randvalue;
	}	
}
