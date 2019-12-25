package com.tmri.rm.bean;
/**
 *
 * <p>Title: RMS</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2013-07-03 16:10:53</p>
 *
 */
public class RmLastVersion {
	private String gjz;	//关键字
	private String version;	//最新版本号
	//[特定属性]<!--本区域内开发用户可加入特定属性，特定属性可以被工具软件自动化生成时保留。-->
	
	//[/特定属性]。

	public String getGjz() {
		return gjz;
	}
	public void setGjz(String gjz) {
		this.gjz = gjz;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}