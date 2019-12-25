package com.tmri.share.frm.bean;

public class ProcedureArgs {
	
	private String argumentname;
	private Long position;
	private int datatype;

	/**
	 * @return 参数名称
	 */
	public String getArgumentname() {
		return argumentname;
	}

	/**
	 * 设置参数名称
	 * @param argumentname 参数名称
	 */
	public void setArgumentname(String argumentname) {
		this.argumentname = argumentname;
	}

	/**
	 * @return 得到参数位置
	 */
	public Long getPosition() {
		return position;
	}

	/**
	 * @param position 参数位置
	 */
	public void setPosition(Long position) {
		this.position = position;
	}

	/**
	 * @return 获取参数类型，值参考java.sql.Types
	 */
	public int getDatatype() {
		return datatype;
	}

	/**
	 * 设置参数类型
	 * @param datatype
	 */
	public void setDatatype(int datatype) {
		this.datatype = datatype;
	}
}
