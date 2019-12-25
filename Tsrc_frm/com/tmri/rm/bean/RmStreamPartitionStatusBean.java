package com.tmri.rm.bean;

public class RmStreamPartitionStatusBean {
	private String bh; // 编号
	private long jyl; // 积压量
	private long cll; // 处理量
	
	public RmStreamPartitionStatusBean(String bh, long cll, long jyl){
		this.bh = bh;
		this.jyl = jyl;
		this.cll = cll;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public long getJyl() {
		return jyl;
	}

	public void setJyl(long jyl) {
		this.jyl = jyl;
	}

	public long getCll() {
		return cll;
	}

	public void setCll(long cll) {
		this.cll = cll;
	}

	public String toString() {
		return "RmStreamPartitionStatusBean [bh=" + bh + ", cll=" + cll + ", jyl=" + jyl + "]";
	}
}
