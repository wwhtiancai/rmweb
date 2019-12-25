package com.tmri.share.frm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class FuncUtilpic {

	public static String getRootPath() {
		String fullpath = FuncUtilpic.class.getResource("").getPath()
				.toString();
		try {
			fullpath = URLDecoder.decode(fullpath, "UTF-8");
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		fullpath = fullpath.replace("file:\\","");
		fullpath = fullpath.replace("file:/","");
		
		int index = fullpath.indexOf("WEB-INF");
		if (index>=0)
			fullpath = fullpath.substring(0,index);
		//linux下路径检查是否“/”开始
		if (fullpath.indexOf(":/")<0 && !fullpath.startsWith("/")){
			fullpath = "/" + fullpath;
		}
		
		System.out.println(fullpath);
		return fullpath;
	}
}
