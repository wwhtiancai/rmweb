package com.tmri.rfid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	/**
	 * 获取季度 2015年第一季度为0，第二季度为1，依次类推
	 * 
	 * @return 二进制字符串
	 */
	public static String getQuarter() {
		Calendar calendarInstance = Calendar.getInstance();
		int y = calendarInstance.get(Calendar.YEAR);// today.getYear();
		int m = calendarInstance.get(Calendar.MONTH);// today.getMonth();

		int quarter = (y - 2015) * 4 % 40 + m / 3;// 年季 2015年为第一季度

		String binary = Integer.toBinaryString(quarter);

		String pattern = "0000000000";
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		binary = df.format(Integer.parseInt(binary));

		return binary;
	}

	public static boolean isValidDate(String str,SimpleDateFormat format) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年-两位月份-两位日期，注意yyyy-MM-dd区分大小写；
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
}
