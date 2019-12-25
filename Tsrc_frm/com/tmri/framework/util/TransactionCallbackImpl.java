package com.tmri.framework.util;

import org.springframework.transaction.support.TransactionCallback;

public abstract class TransactionCallbackImpl implements TransactionCallback{
	private Object parameterObject;
	private Object uclass;
	public Object getParameterObject() {
		return parameterObject;
	}
	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}
	public Object getUclass() {
		return uclass;
	}
	public void setUclass(Object uclass) {
		this.uclass = uclass;
	}
	
}
