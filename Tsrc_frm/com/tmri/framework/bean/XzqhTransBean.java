package com.tmri.framework.bean;

public class XzqhTransBean implements Comparable<XzqhTransBean>{
	private String xzqh;
	private String name;

	public XzqhTransBean(String xzqh){
		this.xzqh = xzqh;
	}
	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(XzqhTransBean cmd) {		
		return xzqh.compareTo(cmd.xzqh);
	}

	public boolean equals(XzqhTransBean cmd) {
		return this.xzqh == null ? this.xzqh == cmd.xzqh : this.xzqh
				.equals(cmd.xzqh);
	}

}
