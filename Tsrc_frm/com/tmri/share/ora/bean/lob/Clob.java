package com.tmri.share.ora.bean.lob;

public class Clob extends Lob {

	public Clob(Object value) {
		super(value);
	}

	public String toString() {
        if(getValue()== null)
        {
            return null;
        }
		return getValue().toString();
	}

}
