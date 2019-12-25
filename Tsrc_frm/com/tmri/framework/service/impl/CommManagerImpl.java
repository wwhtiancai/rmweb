package com.tmri.framework.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Service;


import com.tmri.framework.bean.SysResult;
import com.tmri.framework.service.CommManager;
import com.tmri.framework.util.WrapHelper;

@Service

public class CommManagerImpl implements CommManager{

	public SysResult doTrans(Serializable ectobject,String urlString){
		SysResult sysResult = null;
	  try{
	    //·¢ËÍÊý¾Ý
	    URLConnection con = null;
	    URL url = null;
	    InputStream in = null;
	    OutputStream outs = null;
	    url = new URL(urlString);
		con = url.openConnection();
	    con.setDoOutput(true);
	    con.setDoInput(true);
	    con.setUseCaches(false);
        con.setConnectTimeout(10000);  
        con.setReadTimeout(300000);  
        outs = con.getOutputStream();    
	    outs.write(WrapHelper.objectEZByte(ectobject));
	    in = con.getInputStream();
	    sysResult = (SysResult)WrapHelper.byteEZObject(WrapHelper.getBytes(in));
		}catch(Exception e){
			sysResult = new SysResult();
			sysResult.setFlag(0);
			sysResult.setDesc(e.getMessage());
		}   
		return sysResult;
	}

}
