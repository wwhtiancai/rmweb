package com.tmri.share.ora.bean;

import org.springframework.jdbc.core.PreparedStatementCallback;

public abstract class PreparedStatementCallbackImpl implements PreparedStatementCallback{
    private Object parameterObject;
	public Object getParameterObject() {
		return parameterObject;
	}
	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}
}
