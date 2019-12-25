package com.tmri.pub.util;

import java.util.Comparator;

import com.tmri.framework.bean.Role;

public class ComparatorRole  implements Comparator<Role>{
	public int compare(Role code1, Role code2) {
		//比较代码值
		int flag = code1.getBz().compareTo(code2.getBz());
		if (flag == 0) {
			return code2.getBz().compareTo(code1.getBz());
		} else {
			return flag;
		}
	}

}
