package com.tmri.rm.bean;

/**
 * 流式计算机器配置信息
 * 
 * @author shiyl 2014-11-20
 */
public class RmStreamMachine {
	private String jqlx;
	private String ip;
	private String hostname;
	private String dkh;
	private String sfgljd;
	private String gxsj;
	private String bz;
	private String jqlxmc;
	private String sfgljdmc;

	// -----------------
	private String jqlxs;
	private String jqlxdkhs;
	private String jqlxsmc;
	private String kafkadk;
	private String streamdk;
	private String zookeeperdk;

	public RmStreamMachine() {

	}

	public RmStreamMachine(String ip, String port, String jqlx, String sfgljd, String hostname) {
		this.ip = ip;
		this.dkh = port;
		this.jqlx = jqlx;
		this.sfgljd = sfgljd;
		this.hostname = hostname;
	}

	public String getJqlx() {
		return jqlx;
	}

	public void setJqlx(String jqlx) {
		this.jqlx = jqlx;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDkh() {
		return dkh;
	}

	public void setDkh(String dkh) {
		this.dkh = dkh;
	}

	public String getSfgljd() {
		return sfgljd;
	}

	public void setSfgljd(String sfgljd) {
		this.sfgljd = sfgljd;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getJqlxmc() {
		return jqlxmc;
	}

	public void setJqlxmc(String jqlxmc) {
		this.jqlxmc = jqlxmc;
	}

	public String getSfgljdmc() {
		return sfgljdmc;
	}

	public void setSfgljdmc(String sfgljdmc) {
		this.sfgljdmc = sfgljdmc;
	}

	public String getJqlxs() {
		return jqlxs;
	}

	public void setJqlxs(String jqlxs) {
		this.jqlxs = jqlxs;
	}

	public String getJqlxdkhs() {
		return jqlxdkhs;
	}

	public void setJqlxdkhs(String jqlxdkhs) {
		this.jqlxdkhs = jqlxdkhs;
	}

	public String getJqlxsmc() {
		return jqlxsmc;
	}

	public void setJqlxsmc(String jqlxsmc) {
		this.jqlxsmc = jqlxsmc;
	}

	public String getKafkadk() {
		return kafkadk;
	}

	public void setKafkadk(String kafkadk) {
		this.kafkadk = kafkadk;
	}

	public String getStreamdk() {
		return streamdk;
	}

	public void setStreamdk(String streamdk) {
		this.streamdk = streamdk;
	}

	public String getZookeeperdk() {
		return zookeeperdk;
	}

	public void setZookeeperdk(String zookeeperdk) {
		this.zookeeperdk = zookeeperdk;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public String toString() {
		return "RmStreamMachine [bz=" + bz + ", dkh=" + dkh + ", hostname=" + hostname + ", ip=" + ip + ", jqlx=" + jqlx + ", sfgljd=" + sfgljd + "]";
	}
}
