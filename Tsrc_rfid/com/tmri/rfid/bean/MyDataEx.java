package com.tmri.rfid.bean;

/**
 * 
 * @author stone
 * @date 2016-1-28 下午12:04:47
 */
public class MyDataEx {

    private String bh;
    private String sj;//数据
    private String sjlx;//数据类型
    private Integer clbj;//处理标记 0-初始（由专网到数据交换平台） 1-已上传至专网数据交换平台 2-业务已处理  5-初始（由公安网数据交换平台至公安网） 6-已更新至公安网 7-业务已处理
	private String clfs;//处理方式 增删改
	private String keyName;//主键名称
    
    public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public Integer getClbj() {
		return clbj;
	}
	public void setClbj(Integer clbj) {
		this.clbj = clbj;
	}
	public String getClfs() {
		return clfs;
	}
	public void setClfs(String clfs) {
		this.clfs = clfs;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
    
}
