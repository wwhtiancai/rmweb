package com.tmri.rfid.util;

public class BinaryUtil {
	
	public static String coverBefore(String result ,int num){
		while(result.length() < num){
			result = "0" + result;
		}
		
		return result;
	} 
}
