package com.tmri.share.ora.bean.lob;

public class Blob extends Lob {

	public Blob(Object value) {
		super(value);
	}

	public byte[] toByte() {
		Object object = getValue();
		if (object == null) {
			return null;
		}
		
		if (object instanceof byte[]) {
			return (byte[]) object;
		} else {
			return object.toString().getBytes();
		}
	}
}
