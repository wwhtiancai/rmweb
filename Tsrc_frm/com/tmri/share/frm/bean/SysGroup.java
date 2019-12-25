package com.tmri.share.frm.bean;

import java.io.Serializable;
import java.util.List;

public class SysGroup implements Serializable{
	private String gjz;
	
	private String mc;

	private List list;

	public String getMc(){
		return mc;
	}

	public void setMc(String mc){
		this.mc=mc;
	}

	public List getList(){
		return list;
	}

	public void setList(List list){
		this.list=list;
	}

	public String getGjz(){
		return gjz;
	}

	public void setGjz(String gjz){
		this.gjz=gjz;
	}
}
