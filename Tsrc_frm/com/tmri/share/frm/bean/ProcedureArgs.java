package com.tmri.share.frm.bean;

public class ProcedureArgs {
	
	private String argumentname;
	private Long position;
	private int datatype;

	/**
	 * @return ��������
	 */
	public String getArgumentname() {
		return argumentname;
	}

	/**
	 * ���ò�������
	 * @param argumentname ��������
	 */
	public void setArgumentname(String argumentname) {
		this.argumentname = argumentname;
	}

	/**
	 * @return �õ�����λ��
	 */
	public Long getPosition() {
		return position;
	}

	/**
	 * @param position ����λ��
	 */
	public void setPosition(Long position) {
		this.position = position;
	}

	/**
	 * @return ��ȡ�������ͣ�ֵ�ο�java.sql.Types
	 */
	public int getDatatype() {
		return datatype;
	}

	/**
	 * ���ò�������
	 * @param datatype
	 */
	public void setDatatype(int datatype) {
		this.datatype = datatype;
	}
}
