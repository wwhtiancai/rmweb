package com.tmri.share.frm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * �漰������صĴ�����
 * 
 * @author jianghailong
 * 
 */
public final class DateUtil {
	private static SimpleDateFormat DateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateUtil(){
		
	}
	
	public static String formatDateTime(long l) {
		return DateTimeFormat.format(l);
	}
	
	public static String getSysDate() {
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// ��ȡ���
		int month = ca.get(Calendar.MONTH) + 1;// ��ȡ�·�
		int day = ca.get(Calendar.DATE);// ��ȡ��
		String str1 = year + "-";
		if (month >= 10) {
			str1 = str1 + (month + "-");
		} else {
			str1 = str1 + "0" + (month + "-");
		}
		if (day >= 10) {
			str1 = str1 + (day + "");
		} else {
			str1 = str1 + "0" + (day + "");
		}
		return str1;

	}

	/**
	 * ���ݳ������ڼ�������
	 * 
	 * @param csrq
	 *            ��������
	 * @return
	 * @throws Exception
	 * @author ����
	 */
	public static int getAge(String sysdate, String csrq) throws Exception {
		Date birthDay = praseDate(csrq);
		Date now = praseDate(sysdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("���������쳣!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
				}
			} else {
				age--;
			}
		} else {
		}

		return age;
	}

	/**
	 * ��YYYY-mm-dd���ַ���ת��Ϊ���ڶ���
	 * 
	 * @param dateString
	 *            YYYY-mm-dd���ַ���
	 * @return ���ڶ���,��ʽ����ȷ����NULL
	 */
	public static java.sql.Date praseDate(String dateString) {
		return praseDate(dateString, "yyyy-MM-dd");
	}

	public static java.sql.Date praseDate(String dateString, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date d = null;
		if (dateString == null || dateString.equals("")
				|| dateString.toLowerCase().equals("null")) {
			return null;
		}
		try {
			d = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new java.sql.Date(d.getTime());
	}

	/**
	 * ��ʽ��dbȡ��������ʱ��Ϊyyyy-MM-dd
	 * 
	 * @param strDateTime
	 *            String
	 * @return String
	 */
	public static String formatDate(String strDateTime) {
		if ("--".equals(strDateTime)) {
			return strDateTime;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (strDateTime != null && !strDateTime.equals("")) {
			try {
				strDateTime = format.format(format.parse(strDateTime));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			strDateTime = "";
		}
		return strDateTime;
	}

	/**
	 * ��ʽ��dbȡ��������ʱ��Ϊyyyy-MM-dd
	 * 
	 * @param strDateTime
	 *            String
	 * @return String
	 */
	public static String formatDateMinute(String strDateTime) {
		if ("--".equals(strDateTime)) {
			return strDateTime;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (strDateTime != null && !strDateTime.equals("")) {
			try {
				strDateTime = format.format(format.parse(strDateTime));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			strDateTime = "";
		}
		return strDateTime;
	}

	/**
	 * ���ڼ���
	 * 
	 * @param strDateTiem
	 * @param i
	 * @return
	 */
	public String addYears(String strDateTiem, int i) {
		String result = "", temp = "";
		try {
			strDateTiem = formatDate(strDateTiem);
			temp = strDateTiem.substring(0, 4);
			temp = Integer.parseInt(temp) + i + "";
			result = temp + strDateTiem.substring(4);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * ��ʱ���ַ���ת��Ϊʱ�����
	 * 
	 * @param dateString
	 *            yyyy-MM-dd HH:mm:ss��yyyy-MM-dd HH:mm��YYYY-mm-dd��ʽ���ַ���
	 * @return ʱ����󣬸�ʽ����ȷ����NULL
	 */
	public static java.sql.Timestamp praseTimestamp(String dateString) {
		String format = "yyyy-MM-dd HH:mm:ss";
		if (dateString.trim().length() >= 17) {
			format = "yyyy-MM-dd HH:mm:ss";
		} else if (dateString.trim().length() >= 14) {
			format = "yyyy-MM-dd HH:mm";
		} else if (dateString.trim().length() >= 10) {
			format = "yyyy-MM-dd";
		} else {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new java.sql.Timestamp(d.getTime());
	}

	/**
	 * �����ڶ���ת��Ϊyyy-mm-dd���ַ���
	 * 
	 * @param curTime
	 *            ���ڶ���
	 * @return �����ַ���
	 */
	public static String formatDate(Date curTime) {
		if (curTime == null || curTime.equals("")) {
			return " ";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return formatter.format(curTime);
		}
	}

	/**
	 * �����ڶ���ת��Ϊָ�����ڸ�ʽ�ĵ��ַ���
	 * 
	 * @param curTime
	 *            ���ڶ���
	 * @return �����ַ���
	 */
	public static String formatDate(Date curTime, String format) {
		if (curTime == null) {
			return " ";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(curTime);
		}
	}

	/**
	 * ʹ��Ĭ�Ͻ�����ʽ�����������ڵ��ִ�������yyyy-MM-dd�Ĺ̶���ʽ
	 * 
	 * @param curTimeStr
	 *            �����ַ��������� yyyy-MM-dd ��Ϣ���� 2009-12-29 00:00:00
	 * @param format
	 *            ��Ҫ���صĸ�ʽ��ʽ��һ��Ϊyyyy-MM-dd
	 * @return
	 */
	public static String formatDate(String curTimeStr, String format) {
		if ("--".equals(curTimeStr)) {
			return curTimeStr;
		}
		if (curTimeStr == null) {
			return " ";
		} else if (curTimeStr.equals("")) {
			return " ";
		} else {
			Date curTime = praseDate(curTimeStr, "yyyy-MM-dd");
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(curTime);
		}
	}

	/**
	 * ʹ��ָ��������ʽ�������������ִ���������Ϊָ���ĸ�ʽ
	 * 
	 * @param curTimeStr
	 *            �����ַ��������� yyyy-MM-dd ��Ϣ���� 2009-12-29 00:00:00
	 * @param praseFormat
	 *            ָ��������ʽ��һ��Ϊyyyy-MM-dd
	 * @param format
	 *            ��Ҫ���صĸ�ʽ��ʽ��һ��Ϊyyyy-MM-dd��yyyy'��'MM'��'dd'�գ�yyyy'��'MM'��'dd'��'HH'ʱ'mm'��'��
	 * @return
	 */
	public static String formatDate(String curTimeStr, String praseFormat,
			String format) {
		if ("--".equals(curTimeStr)) {
			return curTimeStr;
		}
		if (curTimeStr == null) {
			return " ";
		} else {
			Date curTime = praseDate(curTimeStr, praseFormat);
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(curTime);
		}
	}

	/**
	 * ʹ��Ĭ�Ͻ�����ʽ�����������ڵ��ִ�������yyyy-MM-dd hh24:mi:ss.0�Ĺ̶���ʽ
	 * 
	 * @param curTimeStr
	 *            �����ַ��������� yyyy-MM-dd yyyy-MM-dd hh:mm:ss��Ϣ���� 2009-12-29
	 *            00:00:00
	 * @return
	 */
	public static String formatDateTime(String curTimeStr) {
		if ("--".equals(curTimeStr)) {
			return curTimeStr;
		}
		String strTmp = curTimeStr;
		try {
			if (curTimeStr != null && !curTimeStr.equals("")
					&& !curTimeStr.equals(" ")) {
				if (curTimeStr.length() == 10) {
					strTmp = formatDate(curTimeStr, "yyyy-MM-dd", "yyyy-MM-dd");
				} else {
					if (curTimeStr.length() == 16) {
						strTmp = formatDate(curTimeStr, "yyyy-MM-dd HH:mm",
								"yyyy-MM-dd HH:mm");
					} else {
						strTmp = formatDate(curTimeStr, "yyyy-MM-dd HH:mm:ss",
								"yyyy-MM-dd HH:mm:ss");
					}
				}
				if (strTmp != null && strTmp.length() == 19
						&& strTmp.endsWith("00:00:00")) {
					strTmp = strTmp.substring(0, 10);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTmp;
	}

	/**
	 * ��Ӣ�ĸ�ʽ�����ַ���ת��Ϊ���������ַ���
	 * 
	 * @param timestr
	 *            Ӣ�ĸ�ʽ�ַ��� yyyy-MM-dd HH:mm��YYYY-mm-dd��ʽ���ַ���
	 * @return ���ĸ�ʽ�����ַ���
	 */
	public static String formatDateZh(String timestr) {
		String format_mm = "yyyy'��'MM'��'dd'��'HH'ʱ'mm'��'";
		String format_dd = "yyyy'��'MM'��'dd'��'";
		String result = timestr;
		try {
			Date curTime = null;
			String format = format_dd;
			if (timestr.length() > 10) {
				try {
					curTime = praseTimestamp(timestr);
				} catch (Exception e1) {
					return timestr;
				}
				format = format_mm;
			} else {
				curTime = praseDate(timestr);
			}
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			result = formatter.format(curTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��Ӣ�ĸ�ʽ�����ַ���ת��Ϊ���������ַ���
	 * 
	 * @param timestr
	 *            Ӣ�ĸ�ʽ�ַ��� yyyy-MM-dd HH:mm��YYYY-mm-dd��ʽ���ַ���
	 * @return ���ĸ�ʽ�����ַ���
	 * @author xuxiaodong ��Ҫ���ڷ��������ӡ���޳������0
	 */
	public static String formatDateZh_print(String timestr) {
		String format_mm = "yyyy'��'M'��'d'��'H'ʱ'm'��'";
		String format_dd = "yyyy'��'M'��'d'��'";
		String result = timestr;
		try {
			Date curTime = null;
			String format = format_dd;
			if (timestr.length() > 10) {
				curTime = praseTimestamp(timestr);
				format = format_mm;
			} else {
				curTime = praseDate(timestr);
			}
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			result = formatter.format(curTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��Ӣ�ĸ�ʽ�����ַ���ת��Ϊ���������ַ���
	 * 
	 * @param timestr
	 *            Ӣ�ĸ�ʽ�ַ���YYYY-mm-dd��ʽ���ַ���
	 * @return ���ĸ�ʽ�����ַ���
	 * @author xuxiaodong ��Ҫ���ڷ��������ӡ���޳������0
	 */
	public static String formatDateZh1_print(String timestr) {
		String format_dd = "yyyy'��'M'��'d'��'";
		String result = timestr;
		try {
			Date curTime = null;
			String format = format_dd;
			curTime = praseDate(timestr);
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			result = formatter.format(curTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String formatDateTimeStr(java.util.Calendar calendar) {
		String result = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
				.format(calendar.getTime());
		return result;
	}

	public static void main(String a[]) {
		/*
		 * String d=""; d="2008-09-03"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 23:12"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 23:12:23"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 11:12:23"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 "; System.out.println(d+" ->
		 * "+formatDateZh(d)); d=null; System.out.println(d+" ->
		 * "+formatDateZh(d)); d=""; System.out.println(d+" ->
		 * "+formatDateZh(d)); d=" "; System.out.println(d+" ->
		 * "+formatDateZh(d)); d=" "; System.out.println(d+" ->
		 * "+formatDateZh(d));
		 */
		/*
		 * System.out.println(praseTimestamp("2009-3-10 19:09"));
		 * System.out.println(praseTimestamp("2008-12-11 12:30:30"));
		 * System.out.println(praseTimestamp("2008-12-11 12:30:30.0"));
		 * System.out.println(praseTimestamp("2008-12-11"));
		 * 
		 * System.out.println(formatDate("2009-12-29 19:09","yyyy-MM-dd"));
		 * System.out.println(formatDate("2009-12-29 00:00:00","yyyy-MM-dd"));
		 * System.out.println(formatDate("2009-12-29","yyyy-MM-dd"));
		 * System.out.println(formatDate("2009-12-29
		 * 00:00:00","yyyy-MM-dd","yyyy'��'MM'��'dd'��'"));
		 * 
		 * Timestamp ts=praseTimestamp(""); System.out.println(ts);
		 * if(ts==null){ System.out.println("ts is null"); }
		 * ts=praseTimestamp("2008"); System.out.println(ts); if(ts==null){
		 * System.out.println("ts is null"); } ts=praseTimestamp("xxxx");
		 * System.out.println(ts); if(ts==null){ System.out.println("ts is
		 * null"); }
		 */
		String sfzmhm = "���֤����";
		System.out.println(sfzmhm.indexOf("���֤����"));
		// System.out.println("19"+sfzmhm.substring(6,8)+"-"+sfzmhm.substring(8,10)+"-"+sfzmhm.substring(10,12));
		// System.out.println(sfzmhm.substring(14,15));
		System.out.println(getSysDate());
		
		System.out.println(timeDiff("2014-03-15 01:00:00", "2014-03-16 01:00:00"));
	}

	/**
	 * �Ƚ��������ڵ�������
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 * @author chiva.s
	 */
	public static long compareDate(String s1, String s2) {
		long DAY = 24L * 60L * 60L * 1000L;
		Date d1 = praseDate(s1);
		Date d2 = praseDate(s2);
		return (d2.getTime() - d1.getTime()) / DAY;
	}

	/**
	 * �ض����ڼ��ض�����
	 * 
	 * @param nDate
	 *            String ���� 'yyyy-MM-dd' ���ַ���
	 * @param nNumberOfDay
	 *            int ����
	 * @return String
	 */
	public static String addDay(String nDate, int nNumberOfDay) {
		String a[] = nDate.split("-");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(a[0]),
				Integer.parseInt(a[1]) - 1, Integer.parseInt(a[2]));
		gc.add(Calendar.DATE, nNumberOfDay);
		return formatter.format(gc.getTime());
	}

	/**
	 * �ض����ڼ��ض�����
	 * 
	 * @param nDate
	 *            String ���� 'yyyy-MM-dd' ���ַ���
	 * @param nNumberOfDay
	 *            int ����
	 * @return String
	 */
	public static String addMonth(String nDate, int nNumberOfDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(nDate));
			cd.add(Calendar.MONTH, nNumberOfDay);// ����n����

			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String addMonth_t(String nDate, int nNumberOfMonth) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(nDate));
			cd.add(Calendar.MONTH, nNumberOfMonth);// ����n����

			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static String getSystime() {
		Date dt = new Date();// �������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// ������ʾ��ʽ
		String nowTime = "";
		nowTime = df.format(dt);// ��DateFormat��format()������dt�л�ȡ����yyyy/MM/dd
								// HH:mm:ss��ʽ��ʾ
		return nowTime;
	}
	
	public static boolean checkSystime(String dateString) {
		boolean flag = false;
		if(new Date().getTime() - praseDate(dateString).getTime() > 0) {
			flag = true;
		}
		return flag;
	}

	public static String numtochinese(String str) throws Exception {
		String[] chinese = { "0", "һ", "��", "��", "��", "��", "��", "��", "��", "��" };
		String strResult = "";
		// ����������ת������.
		int slen = str.length();
		strResult = "";
		for (int i = 0; i < slen; i++) {
			strResult = strResult
					+ chinese[Integer.parseInt(str.charAt(i) + "")];
		}
		return strResult;
	}

	public static String numToCharacter(String str) throws Exception {
		String date = "";
		String mmChar = "";
		String ddChar = "";
		if (str == null || str.length() < 10) {
			return date;
		} else {
			String mm = str.substring(5, 7);
			String dd = str.substring(8, 10);
			if (mm.substring(0, 1).equals("0")) {
				mm = mm.substring(1, 2);
			}
			if (mm.length() == 2) {
				if (mm.substring(0, 1).equals("1")) {
					if (mm.substring(1, 2).equals("0")) {
						mmChar = "ʮ";
					} else {
						mmChar = "ʮ" + numtochinese(mm.substring(1, 2));
					}
				} else {
					if (mm.substring(1, 2).equals("0"))
						mmChar = numtochinese(mm.substring(0, 1)) + "ʮ";
					else
						mmChar = numtochinese(mm.substring(0, 1)) + "ʮ"
								+ numtochinese(mm.substring(1, 2));
				}
			} else {
				mmChar = numtochinese(mm.substring(0, 1));
			}
			if (dd.substring(0, 1).equals("0")) {
				dd = dd.substring(1, 2);
			}
			if (dd.length() == 2) {
				if (dd.substring(0, 1).equals("1")) {
					if (dd.substring(1, 2).equals("0")) {
						ddChar = "ʮ";
					} else {
						ddChar = "ʮ" + numtochinese(dd.substring(1, 2));
					}
				} else {
					if (dd.substring(1, 2).equals("0"))
						ddChar = numtochinese(dd.substring(0, 1)) + "ʮ";
					else
						ddChar = numtochinese(dd.substring(0, 1)) + "ʮ"
								+ numtochinese(dd.substring(1, 2));
				}

			} else {
				ddChar = numtochinese(dd.substring(0, 1));
			}
			return numtochinese(str.substring(0, 4)) + "��" + mmChar + "��"
					+ ddChar + "��";

		}
	}

	// ���ݴ�����������·���������Ӧ������
	public static String numToCharacterPar(String str) throws Exception {
		String mmChar = "";
		if (str.length() == 4) {
			return numtochinese(str);
		} else if (str.length() == 2) {
			if (str.substring(0, 1).equals("0")) {
				str = str.substring(1, 2);
			}
			if (str.length() == 2) {
				if (str.substring(0, 1).equals("1")) {
					if (str.substring(1, 2).equals("0")) {
						mmChar = "ʮ";
					} else {
						mmChar = "ʮ" + numtochinese(str.substring(1, 2));
					}
				} else {
					if (str.substring(1, 2).equals("0"))
						mmChar = numtochinese(str.substring(0, 1)) + "ʮ";
					else
						mmChar = numtochinese(str.substring(0, 1)) + "ʮ"
								+ numtochinese(str.substring(1, 2));
				}
			} else {
				mmChar = numtochinese(str.substring(0, 1));
			}
			return mmChar;
		} else if (str.length() == 10) {
			return numToCharacter(str);
		} else
			return "";
	}

	// ����ʱ�䷵�ش������յĸ�ʽ
	public static String getNyr(String date) throws Exception {
		if (date.length() == 10) { // ����2005-02-01
			return date.substring(0, 4) + "��" + date.substring(5, 7) + "��"
					+ date.substring(8, 10) + "��";
		} else if (date.length() == 16) { // ����2005-02-01 01:01
			return date.substring(0, 4) + "��" + date.substring(5, 7) + "��"
					+ date.substring(8, 10) + "��" + date.substring(11, 13)
					+ "ʱ" + date.substring(14, 16) + "��";
		} else if (date.length() == 18) { // 18λ���֤��
			return date.substring(6, 10) + "��" + date.substring(10, 12) + "��"
					+ date.substring(12, 14) + "��";
		} else if (date.length() == 15) { // 15λ���֤��
			return "19" + date.substring(6, 8) + "��" + date.substring(8, 10)
					+ "��" + date.substring(10, 12) + "��";
		} else {
			return "";
		}
	}

	// ����ʱ���ַ�������Ҫ�ĸ�ʽ
	public static String tranDateStr(String date, String type) {
		// typeΪ1���ؾ�ȷ���գ�2���룬
		if (date == null) {
			date = "";
		}
		if (type.equals("1")) {
			if (date.length() > 10) {
				return date.substring(0, 10);
			} else {
				return date;
			}

		} else if (type.equals("2")) {
			if (date.length() > 19) {
				return date.substring(0, 19);
			} else {
				return date;
			}
		} else {
			return "";
		}

	}
	
	public static String getToday() {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		return fmtCalendar(calendar);
	}

	public static String getNextNDay(String n) {
		if (!StringUtil.checkBN(n)) {
			return getToday();
		}

		int days = 0;
		try {
			days = Integer.parseInt(n);
		} catch (Exception e) {
		}

		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ days);
		return fmtCalendar(calendar);
	}

	private static String fmt(int s) {
		return ("00" + s).replaceAll(".*(\\d{2})$", "$1");
	}

	private static String fmtCalendar(Calendar calendar) {
		if (null == calendar) {
			return "";
		}

		return calendar.get(Calendar.YEAR) + "-"
				+ fmt(calendar.get(Calendar.MONTH) + 1) + "-"
				+ fmt(calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	
	public static String getWeek(Date date){
        String[] weeks = {"7","1","2","3","4","5","6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }
	
	public static String getWeekValue(int week){
		String result="";
		switch (week) {
			case 1:
				result="������";
				break;
			case 2:
				result="����һ";
				break;	
			case 3:
				result="���ڶ�";
				break;
			case 4:
				result="������";
				break;
			case 5:
				result="������";
				break;
			case 6:
				result="������";
				break;
			case 7:
				result="������";
				break;
		}
		return result;
	}
	

	
	public static long timeDiff(String time1, String time2) {
		Date date1, date2;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = sDateFormat.parse(time1);
			date2 = sDateFormat.parse(time2);
		} catch (Exception e) {
			e.printStackTrace();
			return Integer.MAX_VALUE;
		}
		return Math.abs((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24)) + 1;
	}
	
	public static int calDayByYearAndMonth(String year, String month) {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
		Calendar rightNow = Calendar.getInstance();
		try {
			rightNow.setTime(simpleDate.parse(year + "/" + month));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);// �������»�ȡ�·�����
	}
	
	//�ж��Ƿ�����Ч�����ڸ�ʽ
	public static boolean isValidDate(String date,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try{
			dateFormat.parse(date);             
			return true;
		}catch(Exception ex){
            // ex.printStackTrace();
			return false;
		}
	}

    // �Ƚ�����ʱ���ʽ�����ڴ�С
    // shiyl 141210
    public static int compareDate(String DATE1, String DATE2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("date1��date2ǰ");
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("date1��date2��");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
    }
    
    /**
     * ��ȡ�������һ��
     * */
    public static Date getLastDateOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return cal.getTime();  
    }  
    
    /**
     * ��ȡ���µ�һ��
     * */
    public static Date getFirstDateOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
       return cal.getTime();  
    } 
    
    /**
     * ��ȡ���ڼ�
     * */
    public static int getDayForWeek(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	 int dayForWeek = 0;
		  if(cal.get(Calendar.DAY_OF_WEEK) == 1){
			  dayForWeek = 7;
		  }else{
		   dayForWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		  }
		  return dayForWeek;
    }
    
    /**
     * �ж������Ƿ�Ϊ��ĩ
     * */
    public static boolean isWeekend(Date date) {
    	int dayForWeek = getDayForWeek(date);
    	if (6 == dayForWeek || 7 == dayForWeek){
    		return true;
    	}
    	return false;
    }
    
    /**
     * �����ַ�����ȡ����
     * @throws ParseException 
     * */
    public static Date getDate(String strDate, String format) throws ParseException{
    	SimpleDateFormat formatDate = new SimpleDateFormat(format);
		return  formatDate.parse(strDate);
    }
    
    public static Date getDate(String strDate) throws ParseException{
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		return  formatDate.parse(strDate);
    }
    
    
    public static String getDateString(Date date, String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	return sdf.format(date);
    }
    
    public static String getDateString(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(date);
    }
    
    public static int getLastDayForMonth(int year, int month){
    	Date date = getLastDateOfMonth(year, month);
    	Calendar cal =  Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DATE);
    }

    public static List<Date> getDateListForMonth(int year, int month){
    	List<Date> dateList = new ArrayList<Date>();
    	Date date = getFirstDateOfMonth(year, month);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int lastDay = getLastDayForMonth(year, month);
    	for (int i =0; i<lastDay; i++){
    		dateList.add(cal.getTime());
    		cal.set(Calendar.DATE, cal.get(Calendar.DATE) +1);
    	}
    	return dateList;
    }
    
    public static List<Date> getDateList(Date ksDate, long ts){
    	List<Date> dateList = new ArrayList<Date>();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(ksDate);
    	for (int i =0; i<ts; i++){
    		dateList.add(cal.getTime());
    		cal.set(Calendar.DATE, cal.get(Calendar.DATE) +1);
    	}
    	return dateList;
    }
    
    public static List<String> getYearList() {
		List<String> yearList = new ArrayList<String>();
		for (int i = -10; i < 1; i++) {
			yearList.add("" + (Calendar.getInstance().get(Calendar.YEAR) + i));
		}
		return yearList;
	}

    public static String getYear() {
		return "" + Calendar.getInstance().get(Calendar.YEAR);
	}
	
	// ��ȡ��ǰ�·ݣ�������ɵ���0��ʼ������Ҫ+1
    public static String getMonth() {
		int m = (Calendar.getInstance().get(Calendar.MONTH)+1);
		if (m < 10){
			return "0" + m;
		}
		return "" + m;
	}
    
    public static String getPrevMonth() {
		int m = (Calendar.getInstance().get(Calendar.MONTH));
		if (m < 10){
			return "0" + m;
		}
		return "" + m;
	}
    
    public static String getNextMonth() {
		int m = (Calendar.getInstance().get(Calendar.MONTH)+2);
		if (m < 10){
			return "0" + m;
		}
		return "" + m;
	}
	
    public static List<String> getMonthList(){
		List<String> monthList = new ArrayList<String>();
		for(int i =0 ;i<12; i++){
			if (i < 9) {
				monthList.add("0" + (i + 1));
			}
			else{
				monthList.add("" + (i + 1));
			}
		}
		return monthList;
	}
    
    public static Date getPrevMonth(int year, int month){
    	Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-2); 
        return cal.getTime();
    }
}
