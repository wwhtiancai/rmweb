package com.tmri.rfid.util;

import com.alibaba.fastjson.JSONObject;
import com.tmri.rfid.bean.DataExch;
import com.tmri.rfid.bean.MyDataEx;
import com.tmri.rfid.common.ClassTableName;

public class SJNRUtil {

	public static int SJNRSIZE = 1000;//每个数据内容能存4000byte，若为中文，一个字符占4个，长度为1000
	public static int BHSIZE = 25;//14位编号+10位顺序号+1位存储类型（1为有子记录，2为没有子记录, 3为子记录）
	
	/**
	 * 将json数据转为数据交换格式
	 * @param sj
	 * @param sjlx
	 * @return
	 */
	public static DataExch parseSJNR(String sj ,String sjlx ,String clfs){
		
		int sjLeng = sj.length();
		DataExch dataExch = new DataExch();
		dataExch.setSjlx(clfs+sjlx);
		
		if(sjLeng > SJNRSIZE*9){
			dataExch.setSjnr10(sj.substring(SJNRSIZE*9));
			sj = sj.substring(0,SJNRSIZE*9);
		}
		if(sjLeng > SJNRSIZE*8){
			dataExch.setSjnr9(sj.substring(SJNRSIZE*8));
			sj = sj.substring(0,SJNRSIZE*8);
		}
		if(sjLeng > SJNRSIZE*7){
			dataExch.setSjnr8(sj.substring(SJNRSIZE*7));
			sj = sj.substring(0,SJNRSIZE*7);
		}
		if(sjLeng > SJNRSIZE*6){
			dataExch.setSjnr7(sj.substring(SJNRSIZE*6));
			sj = sj.substring(0,SJNRSIZE*6);
		}
		if(sjLeng > SJNRSIZE*5){
			dataExch.setSjnr6(sj.substring(SJNRSIZE*5));
			sj = sj.substring(0,SJNRSIZE*5);
		}
		if(sjLeng > SJNRSIZE*4){
			dataExch.setSjnr5(sj.substring(SJNRSIZE*4));
			sj = sj.substring(0,SJNRSIZE*4);
		}
		if(sjLeng > SJNRSIZE*3){
			dataExch.setSjnr4(sj.substring(SJNRSIZE*3));
			sj = sj.substring(0,SJNRSIZE*3);
		}
		if(sjLeng > SJNRSIZE*2){
			dataExch.setSjnr3(sj.substring(SJNRSIZE*2));
			sj = sj.substring(0,SJNRSIZE*2);
		}
		if(sjLeng > SJNRSIZE*1){
			dataExch.setSjnr2(sj.substring(SJNRSIZE));
			sj = sj.substring(0,SJNRSIZE);
		}
		dataExch.setSjnr1(sj);
		return dataExch;
	}
	
	public static String getSjByDataExch(DataExch dataExch){
		String jsonStr = "";
		String sjnr1 = dataExch.getSjnr1();
		String sjnr2 = dataExch.getSjnr2();
		String sjnr3 = dataExch.getSjnr3();
		String sjnr4 = dataExch.getSjnr4();
		String sjnr5 = dataExch.getSjnr5();
		String sjnr6 = dataExch.getSjnr6();
		String sjnr7 = dataExch.getSjnr7();
		String sjnr8 = dataExch.getSjnr8();
		String sjnr9 = dataExch.getSjnr9();
		String sjnr10 = dataExch.getSjnr10();
		
		if(sjnr1 != null){
			jsonStr += sjnr1;
		}
		if(sjnr2 != null){
			jsonStr += sjnr2;
		}
		if(sjnr3 != null){
			jsonStr += sjnr3;
		}
		if(sjnr4 != null){
			jsonStr += sjnr4;
		}
		if(sjnr5 != null){
			jsonStr += sjnr5;
		}
		if(sjnr6 != null){
			jsonStr += sjnr6;
		}
		if(sjnr7 != null){
			jsonStr += sjnr7;
		}
		if(sjnr8 != null){
			jsonStr += sjnr8;
		}
		if(sjnr9 != null){
			jsonStr += sjnr9;
		}
		if(sjnr10 != null){
			jsonStr += sjnr10;
		}
		
		return jsonStr;
	}
	
	public static MyDataEx parseDataEx(DataExch dataExch){
		String jsonStr = getSjByDataExch(dataExch);
		MyDataEx myDataEx = new MyDataEx();
		myDataEx.setSj(jsonStr);
		myDataEx.setBh(dataExch.getBh());
		myDataEx.setClfs(dataExch.getSjlx().substring(BHSIZE, BHSIZE+1));
		myDataEx.setSjlx(dataExch.getSjlx().substring(BHSIZE+1));
		
		return myDataEx;
	}
	
	/**
	 * 将数据交换表的格式转换为业务需要的格式
	 * @param dataExch
	 * @return
	 */
	public static MyDataEx parseDataExWithTableInfo(DataExch dataExch){
		String jsonStr = getSjByDataExch(dataExch);

		JSONObject jsonObj=JSONObject.parseObject(jsonStr);  
		String newClassname = (String) jsonObj.get("className");
		String sj = (String) jsonObj.get("sj");
		
		MyDataEx myDataEx = new MyDataEx();
		myDataEx.setSj(sj);
		String keyName = ClassTableName.getByClassName(newClassname).getkeyName();
		myDataEx.setKeyName(keyName);
		myDataEx.setBh(dataExch.getBh());
		myDataEx.setClfs(dataExch.getSjlx().substring(BHSIZE, BHSIZE+1));
		myDataEx.setSjlx(dataExch.getSjlx().substring(BHSIZE+1));
		
		return myDataEx;
	}
	
	public static void main(String[] args){
		String str = "";
		for(int i = 0 ; i< 320; i++){
			str+= "1234567890";
		}
		
		DataExch dataExch = parseSJNR(str, "asas", "0");
		System.out.println(dataExch.getSjnr1()+"==="+dataExch.getSjnr3());
		
	}
}
