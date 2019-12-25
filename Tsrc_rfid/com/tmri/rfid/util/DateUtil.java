package com.tmri.rfid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	/**
	 * ��ȡ���� 2015���һ����Ϊ0���ڶ�����Ϊ1����������
	 * 
	 * @return �������ַ���
	 */
	public static String getQuarter() {
		Calendar calendarInstance = Calendar.getInstance();
		int y = calendarInstance.get(Calendar.YEAR);// today.getYear();
		int m = calendarInstance.get(Calendar.MONTH);// today.getMonth();

		int quarter = (y - 2015) * 4 % 40 + m / 3;// �꼾 2015��Ϊ��һ����

		String binary = Integer.toBinaryString(quarter);

		String pattern = "0000000000";
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		binary = df.format(Integer.parseInt(binary));

		return binary;
	}

	public static boolean isValidDate(String str,SimpleDateFormat format) {
		boolean convertSuccess = true;
		// ָ�����ڸ�ʽΪ��λ��-��λ�·�-��λ���ڣ�ע��yyyy-MM-dd���ִ�Сд��
		try {
			// ����lenientΪfalse.
			// ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// ���throw java.text.ParseException����NullPointerException����˵����ʽ����
			convertSuccess = false;
		}
		return convertSuccess;
	}
}
