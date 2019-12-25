package com.tmri.share.cache.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tmri.share.frm.util.StringUtil;

public final class RmWebCache {
	private static final Map<String, String> fzjgUrlMap = new ConcurrentHashMap<String, String>();
	
	private RmWebCache(){
	}
	
	public static String getUrl(String fzjg){
		if (StringUtil.checkBN(fzjg)) {
			return fzjgUrlMap.get(fzjg);
		}
		return null;
	}
	
	public static void addUrl(String fzjg, String url){
		if (StringUtil.checkBN(fzjg) && StringUtil.checkBN(url)) {
			fzjgUrlMap.put(fzjg, url);
		}
	}
	
	public static void cleanUrls(){
		fzjgUrlMap.clear();
	}
}
