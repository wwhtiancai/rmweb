package com.tmri.share.frm.util;

public class DebugLog {
	
	private static boolean isDebug = true;
	public static void setDebug(boolean debug){
		isDebug = debug;
	}
	
	public static boolean isDebug() {
		return isDebug;
	}
	
	public static void debug(String msg){
		if(isDebug){
			System.out.println(msg);
		}
	}
	
}
