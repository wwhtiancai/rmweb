package com.tmri.share.ora.bean;


import org.springframework.jdbc.core.CallableStatementCallback;

public  abstract class CallableStatementCallbackImpl<T> implements CallableStatementCallback<T>{
    private Object parameterObject;
	public Object getParameterObject() {
		return parameterObject;
	}
	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}
}