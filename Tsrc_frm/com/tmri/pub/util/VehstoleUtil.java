package com.tmri.pub.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.digester.Digester;

import com.tmri.pub.bean.veh.VehStole;
import com.tmri.pub.bean.veh.VehStoleItem;


public class VehstoleUtil {
	public static String getVehstolenCondition(String clsbdh, String fdjh)
			throws Exception {
		String result = "";
		// 预处理
		if (fdjh == null) {
			fdjh = "";
		} else {
			fdjh = fdjh.trim();
			if (fdjh.equals("") && fdjh.indexOf("无") >= 0) {
				fdjh = "";
			}
		}

		if (clsbdh == null) {
			clsbdh = "";
		} else {
			clsbdh = clsbdh.trim();
			if (clsbdh.equals("") && clsbdh.indexOf("无") >= 0) {
				clsbdh = "";
			}
		}
		// 拼出条件
		if (!clsbdh.equals("")) {
			result += "clsbdh=" + URLEncoder.encode(clsbdh, "GBK");
			if (!fdjh.equals("")) {
				result += "&fdjh=" + URLEncoder.encode(fdjh, "GBK");
			}
		} else {
			if (!fdjh.equals("")) {
				result += "fdjh=" + URLEncoder.encode(fdjh, "GBK");
			}
		}
		return result;
	}

	// 根据url获取返回信息
	public static String getStolenXml(String strUrl) throws Exception {
		String sCurrentLine = "";
		String sTotalString = "";
		try {
			// 连接
			URL currenturl = new URL(strUrl);
			URLConnection urlConn = currenturl.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) urlConn;
			httpconn.setConnectTimeout(10000);  
			httpconn.setReadTimeout(300000);  
			InputStreamReader in = new InputStreamReader(httpconn
					.getInputStream(), "GBK");
			BufferedReader buf = new BufferedReader(in);
			while ((sCurrentLine = buf.readLine()) != null) {
				sTotalString += sCurrentLine;
			}
		} catch (Exception ex) {
			// 抛出异常
			ex.printStackTrace();
			throw ex;
		}
		return sTotalString;
	}
	
	// 为了获取盗抢车信息
	public static VehStole parseVehstole(String queryresult){
		VehStole re=new VehStole();
		;
		try{
			Digester digester=new Digester();
			digester.setValidating(false);
			digester.addObjectCreate("root",VehStole.class);
			digester.addBeanPropertySetter("root/retCode","retCode");
			digester.addBeanPropertySetter("root/rowCount","rowCount");
			digester.addBeanPropertySetter("root/error","error");

			digester.addObjectCreate("root/vehicles/vehicle",VehStoleItem.class);
			digester.addBeanPropertySetter("root/vehicles/vehicle/hpzl","hpzl");
			digester.addBeanPropertySetter("root/vehicles/vehicle/hphm","hphm");
			digester.addBeanPropertySetter("root/vehicles/vehicle/clpp1","clpp1");
			digester.addBeanPropertySetter("root/vehicles/vehicle/clxh","clxh");
			digester.addBeanPropertySetter("root/vehicles/vehicle/clsbdh","clsbdh");
			digester.addBeanPropertySetter("root/vehicles/vehicle/fdjh","fdjh");
			digester.addBeanPropertySetter("root/vehicles/vehicle/csys","csys");
			digester.addBeanPropertySetter("root/vehicles/vehicle/syr","syr");
			digester.addBeanPropertySetter("root/vehicles/vehicle/ccdjrq","ccdjrq");
			digester.addBeanPropertySetter("root/vehicles/vehicle/zt","zt");
			digester.addSetNext("root/vehicles/vehicle","addVehStoleItem");
			byte[] src=queryresult.getBytes();
			ByteArrayInputStream stream=new ByteArrayInputStream(src);
			re=(VehStole)digester.parse(stream);
		}catch(Exception ex){
			ex.printStackTrace();
			re.setRetCode("-1");
			re.setError("XML文档格式不正确："+ex.getMessage());
		}
		return re;
	}
		
}
