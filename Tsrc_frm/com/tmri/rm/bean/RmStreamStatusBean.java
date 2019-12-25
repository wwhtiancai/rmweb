package com.tmri.rm.bean;

import java.util.List;

public class RmStreamStatusBean {
	private String ip; // ip��ַ
	private int rwsl; // ��������
	private long cll; // ������
	private long jyl; // ��ѹ��
	
	private List<RmStreamPartitionStatusBean> partitionStatusList;

	public RmStreamStatusBean() {

	}

	public RmStreamStatusBean(String ip, int rwsl, long cll, long jyl) {
		this.ip = ip;
		this.rwsl = rwsl;
		this.cll = cll;
		this.jyl = jyl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getRwsl() {
		return rwsl;
	}

	public void setRwsl(int rwsl) {
		this.rwsl = rwsl;
	}

	public long getCll() {
		return cll;
	}

	public void setCll(long cll) {
		this.cll = cll;
	}

	public long getJyl() {
		return jyl;
	}

	public void setJyl(long jyl) {
		this.jyl = jyl;
	}

	public List<RmStreamPartitionStatusBean> getPartitionStatusList() {
		return partitionStatusList;
	}

	public void setPartitionStatusList(
			List<RmStreamPartitionStatusBean> partitionStatusList) {
		this.partitionStatusList = partitionStatusList;
	}

	public String toString() {
		return "RmStreamStatusBean [cll=" + cll + ", ip=" + ip + ", jyl=" + jyl + ", rwsl=" + rwsl + "]";
	}
}
