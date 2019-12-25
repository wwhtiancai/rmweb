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
 * 涉及日期相关的处理函数
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
		int year = ca.get(Calendar.YEAR);// 获取年份
		int month = ca.get(Calendar.MONTH) + 1;// 获取月份
		int day = ca.get(Calendar.DATE);// 获取日
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
	 * 根据出生日期计算年龄
	 * 
	 * @param csrq
	 *            出生日期
	 * @return
	 * @throws Exception
	 * @author 武红斌
	 */
	public static int getAge(String sysdate, String csrq) throws Exception {
		Date birthDay = praseDate(csrq);
		Date now = praseDate(sysdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("出生日期异常!");
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
	 * 将YYYY-mm-dd的字符串转化为日期对象
	 * 
	 * @param dateString
	 *            YYYY-mm-dd的字符串
	 * @return 日期对象,格式不正确返回NULL
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
	 * 格式化db取出的日期时间为yyyy-MM-dd
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
	 * 格式化db取出的日期时间为yyyy-MM-dd
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
	 * 日期加年
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
	 * 将时间字符串转化为时间对象
	 * 
	 * @param dateString
	 *            yyyy-MM-dd HH:mm:ss或yyyy-MM-dd HH:mm或YYYY-mm-dd格式的字符串
	 * @return 时间对象，格式不正确返回NULL
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
	 * 将日期对象转化为yyy-mm-dd的字符串
	 * 
	 * @param curTime
	 *            日期对象
	 * @return 日期字符串
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
	 * 将日期对象转化为指定日期格式的的字符串
	 * 
	 * @param curTime
	 *            日期对象
	 * @return 日期字符串
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
	 * 使用默认解析格式解析传入日期的字串，返回yyyy-MM-dd的固定格式
	 * 
	 * @param curTimeStr
	 *            日期字符串，含有 yyyy-MM-dd 信息，如 2009-12-29 00:00:00
	 * @param format
	 *            需要返回的格式形式，一般为yyyy-MM-dd
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
	 * 使用指定解析格式解析传入日期字串，并返回为指定的格式
	 * 
	 * @param curTimeStr
	 *            日期字符串，含有 yyyy-MM-dd 信息，如 2009-12-29 00:00:00
	 * @param praseFormat
	 *            指定解析格式，一般为yyyy-MM-dd
	 * @param format
	 *            需要返回的格式形式，一般为yyyy-MM-dd，yyyy'年'MM'月'dd'日，yyyy'年'MM'月'dd'日'HH'时'mm'分'等
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
	 * 使用默认解析格式解析传入日期的字串，返回yyyy-MM-dd hh24:mi:ss.0的固定格式
	 * 
	 * @param curTimeStr
	 *            日期字符串，含有 yyyy-MM-dd yyyy-MM-dd hh:mm:ss信息，如 2009-12-29
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
	 * 将英文格式日期字符串转换为日期中文字符串
	 * 
	 * @param timestr
	 *            英文格式字符串 yyyy-MM-dd HH:mm或YYYY-mm-dd格式的字符串
	 * @return 中文格式日期字符串
	 */
	public static String formatDateZh(String timestr) {
		String format_mm = "yyyy'年'MM'月'dd'日'HH'时'mm'分'";
		String format_dd = "yyyy'年'MM'月'dd'日'";
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
	 * 将英文格式日期字符串转换为日期中文字符串
	 * 
	 * @param timestr
	 *            英文格式字符串 yyyy-MM-dd HH:mm或YYYY-mm-dd格式的字符串
	 * @return 中文格式日期字符串
	 * @author xuxiaodong 主要用于法律文书打印，剔除多余的0
	 */
	public static String formatDateZh_print(String timestr) {
		String format_mm = "yyyy'年'M'月'd'日'H'时'm'分'";
		String format_dd = "yyyy'年'M'月'd'日'";
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
	 * 将英文格式日期字符串转换为日期中文字符串
	 * 
	 * @param timestr
	 *            英文格式字符串YYYY-mm-dd格式的字符串
	 * @return 中文格式日期字符串
	 * @author xuxiaodong 主要用于法律文书打印，剔除多余的0
	 */
	public static String formatDateZh1_print(String timestr) {
		String format_dd = "yyyy'年'M'月'd'日'";
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
		 * 00:00:00","yyyy-MM-dd","yyyy'年'MM'月'dd'日'"));
		 * 
		 * Timestamp ts=praseTimestamp(""); System.out.println(ts);
		 * if(ts==null){ System.out.println("ts is null"); }
		 * ts=praseTimestamp("2008"); System.out.println(ts); if(ts==null){
		 * System.out.println("ts is null"); } ts=praseTimestamp("xxxx");
		 * System.out.println(ts); if(ts==null){ System.out.println("ts is
		 * null"); }
		 */
		String sfzmhm = "身份证号码";
		System.out.println(sfzmhm.indexOf("身份证号码"));
		// System.out.println("19"+sfzmhm.substring(6,8)+"-"+sfzmhm.substring(8,10)+"-"+sfzmhm.substring(10,12));
		// System.out.println(sfzmhm.substring(14,15));
		System.out.println(getSysDate());
		
		System.out.println(timeDiff("2014-03-15 01:00:00", "2014-03-16 01:00:00"));
	}

	/**
	 * 比较两个日期的天数差
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
	 * 特定日期加特定天数
	 * 
	 * @param nDate
	 *            String 形如 'yyyy-MM-dd' 的字符串
	 * @param nNumberOfDay
	 *            int 天数
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
	 * 特定日期加特定月数
	 * 
	 * @param nDate
	 *            String 形如 'yyyy-MM-dd' 的字符串
	 * @param nNumberOfDay
	 *            int 月数
	 * @return String
	 */
	public static String addMonth(String nDate, int nNumberOfDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(nDate));
			cd.add(Calendar.MONTH, nNumberOfDay);// 增加n个月

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
			cd.add(Calendar.MONTH, nNumberOfMonth);// 增加n个月

			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static String getSystime() {
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置显示格式
		String nowTime = "";
		nowTime = df.format(dt);// 用DateFormat的format()方法在dt中获取并以yyyy/MM/dd
								// HH:mm:ss格式显示
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
		String[] chinese = { "0", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String strResult = "";
		// 将单个数字转成中文.
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
						mmChar = "十";
					} else {
						mmChar = "十" + numtochinese(mm.substring(1, 2));
					}
				} else {
					if (mm.substring(1, 2).equals("0"))
						mmChar = numtochinese(mm.substring(0, 1)) + "十";
					else
						mmChar = numtochinese(mm.substring(0, 1)) + "十"
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
						ddChar = "十";
					} else {
						ddChar = "十" + numtochinese(dd.substring(1, 2));
					}
				} else {
					if (dd.substring(1, 2).equals("0"))
						ddChar = numtochinese(dd.substring(0, 1)) + "十";
					else
						ddChar = numtochinese(dd.substring(0, 1)) + "十"
								+ numtochinese(dd.substring(1, 2));
				}

			} else {
				ddChar = numtochinese(dd.substring(0, 1));
			}
			return numtochinese(str.substring(0, 4)) + "年" + mmChar + "月"
					+ ddChar + "日";

		}
	}

	// 根据传进来的年份月份日生成相应的中文
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
						mmChar = "十";
					} else {
						mmChar = "十" + numtochinese(str.substring(1, 2));
					}
				} else {
					if (str.substring(1, 2).equals("0"))
						mmChar = numtochinese(str.substring(0, 1)) + "十";
					else
						mmChar = numtochinese(str.substring(0, 1)) + "十"
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

	// 根据时间返回带年月日的格式
	public static String getNyr(String date) throws Exception {
		if (date.length() == 10) { // 类似2005-02-01
			return date.substring(0, 4) + "年" + date.substring(5, 7) + "月"
					+ date.substring(8, 10) + "日";
		} else if (date.length() == 16) { // 类似2005-02-01 01:01
			return date.substring(0, 4) + "年" + date.substring(5, 7) + "月"
					+ date.substring(8, 10) + "日" + date.substring(11, 13)
					+ "时" + date.substring(14, 16) + "分";
		} else if (date.length() == 18) { // 18位身份证号
			return date.substring(6, 10) + "年" + date.substring(10, 12) + "月"
					+ date.substring(12, 14) + "日";
		} else if (date.length() == 15) { // 15位身份证号
			return "19" + date.substring(6, 8) + "年" + date.substring(8, 10)
					+ "月" + date.substring(10, 12) + "日";
		} else {
			return "";
		}
	}

	// 根据时间字符返回需要的格式
	public static String tranDateStr(String date, String type) {
		// type为1返回精确到日，2到秒，
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
				result="星期日";
				break;
			case 2:
				result="星期一";
				break;	
			case 3:
				result="星期二";
				break;
			case 4:
				result="星期三";
				break;
			case 5:
				result="星期四";
				break;
			case 6:
				result="星期五";
				break;
			case 7:
				result="星期六";
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
		return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);// 根据年月获取月份天数
	}
	
	//判断是否是有效的日期格式
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

    // 比较两个时间格式的日期大小
    // shiyl 141210
    public static int compareDate(String DATE1, String DATE2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("date1在date2前");
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("date1在date2后");
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
     * 获取当月最后一天
     * */
    public static Date getLastDateOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return cal.getTime();  
    }  
    
    /**
     * 获取当月第一天
     * */
    public static Date getFirstDateOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
       return cal.getTime();  
    } 
    
    /**
     * 获取星期几
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
     * 判断日期是否为周末
     * */
    public static boolean isWeekend(Date date) {
    	int dayForWeek = getDayForWeek(date);
    	if (6 == dayForWeek || 7 == dayForWeek){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 根据字符串获取日期
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
	
	// 获取当前月份，这个生成的以0开始，所以要+1
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
