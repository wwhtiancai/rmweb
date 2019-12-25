package com.tmri.share.ora.bean.lob;

public abstract class Lob {

	private Object value;

	public Object getValue() {
		return value;
	}

	public Lob(Object value) {
		super();
		this.value = value;
	}

}
