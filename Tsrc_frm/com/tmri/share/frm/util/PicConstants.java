package com.tmri.share.frm.util;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class PicConstants {

	private static PicConstants applicationContext = null;

	private Map<HttpServletRequest, String> streams = new HashMap<HttpServletRequest, String>();

	private String rootpath; // 发布包根目录

	public static PicConstants getInstance() {
		if (applicationContext == null) {
			applicationContext = new PicConstants();
		}
		return applicationContext;
	}

	public String getRootpath() {
		return rootpath;
	}

	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}

	public String getRequestStream(HttpServletRequest request) {
		String path = null;
		synchronized (streams) {
			path = streams.get(request);
			streams.remove(request);
		}
		return path;
	}

	public void setRequestStream(HttpServletRequest request, ServletInputStream photoStream) throws IOException {
		if (photoStream == null)
			return;
		String path = FuncUtilpic.getRootPath() + "images/";

		String name = photoStream.toString();
		int lastIndex = name.lastIndexOf(".");
		if (lastIndex >= 0)
			name = name.substring(lastIndex + 1);
		path += name;
		InputStream is = photoStream;
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream(path));
			if (os != null) {
				int nRead = 0;
				byte[] b = new byte[1024];
				// 读取网络文件,写入指定的文件中
				while ((nRead = is.read(b, 0, 1024)) > 0) {
					os.write(b, 0, nRead);
				}
				os.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (streams.size() > 10000) {
			String msg = "PicConstants.streams outsize,clear!";
			System.out.println(msg);
			streams.clear();
		}
		synchronized (streams) {
			streams.put(request, path);
		}
	}

}
