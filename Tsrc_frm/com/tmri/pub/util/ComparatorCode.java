package com.tmri.pub.util;

import java.util.Comparator;

import com.tmri.share.frm.bean.Code;

public class ComparatorCode implements Comparator<Code>{
	public int compare(Code code1, Code code2) {
		//比较代码值
		int flag = code1.getDmz().compareTo(code2.getDmz());
		if (flag == 0) {
			return code2.getDmz().compareTo(code1.getDmz());
		} else {
			return flag;
		}
	}

}
