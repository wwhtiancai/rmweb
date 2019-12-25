package com.tmri.framework.bean;

public class FrmTaskRowlog {
	private String zxxh;// VARCHAR2(16) N 执行序号
	private String zjxx;// VARCHAR2(200) N 主键信息
	private String jgbj;// CHAR(1) N 结果标记 0-失败 1-成功
	private String fhxx;// VARCHAR2(4000) Y 返回信息
	private String bz;// VARCHAR2(128) Y 备注
	
	public FrmTaskRowlog(){
		
	}
	
	public FrmTaskRowlog(String zxxh, String zjxx, String jgbj, String fhxx, String bz){
		this.zxxh = zxxh;
		this.zjxx = zjxx;
		this.jgbj = jgbj;
		this.fhxx = fhxx;
		this.bz = bz;
	}
	
	public String getZxxh() {
		return zxxh;
	}

	public void setZxxh(String zxxh) {
		this.zxxh = zxxh;
	}

	public String getZjxx() {
		return zjxx;
	}

	public void setZjxx(String zjxx) {
		this.zjxx = zjxx;
	}

	public String getJgbj() {
		return jgbj;
	}

	public void setJgbj(String jgbj) {
		this.jgbj = jgbj;
	}

	public String getFhxx() {
		return fhxx;
	}

	public void setFhxx(String fhxx) {
		this.fhxx = fhxx;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public String toString() {
		return "" + zxxh + "|" + zjxx + "|" + jgbj + "|" + fhxx + "|" + bz;
	}
}
