package com.tmri.framework.service;

import java.io.Serializable;

import com.tmri.framework.bean.SysResult;

public interface CommManager{
	public SysResult doTrans(Serializable ectobject,String url);
}
