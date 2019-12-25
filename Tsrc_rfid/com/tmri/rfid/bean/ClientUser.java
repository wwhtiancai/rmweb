package com.tmri.rfid.bean;

import com.tmri.share.frm.bean.SysUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author stone
 * @date 2015/12/4 
 */
public class ClientUser {
	
	private String token; //用户此次登录的信息唯一标识
	private SysUser sysUser;
	private Date lastViewTime;
	private List<String> menuList;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Date getLastViewTime() {
		return lastViewTime;
	}
	public void setLastViewTime(Date lastViewTime) {
		this.lastViewTime = lastViewTime;
	}

	public List<String> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}

	public void addMenu(String menuUrl) {
		if (menuList == null) menuList = new ArrayList<String>();
		menuList.add(menuUrl);
	}
}
