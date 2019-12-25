package com.tmri.share.frm.dao;

public interface GSysDateDao {

	// 获取系统日期
	public String getDBDate(String val);
	
	/*
	 * 获取系统当前日期加减一个时间段的日期
	 */
	public String getSysDate(String val);
	
	/*
	 * 获取系统当前日期（Timestamp）
	 */
	public String getSysDateTimestamp();
	
	/*
	 * 获取系统当前日期加减一个时间段的日期
	 */
	public String getSysDateByFormat(String val, String format);

	// 获取系统时间
	public String getDBDateTimeToMinute(String value);

	// 获取系统日期
	public String getDBDateTime(String val);

	public String getMonthSysdate(String date, String v);

	public String getMonthSysdate_1(String date, String v);

	public String getDaySysdate(String date, String v);

	public int compareDay(String date);

	public int minusDateByMonths(String date1, String date2);

	public int minusDateByDays(String date1, String date2);

	public int minusDateByHour(String date1, String date2);

	public int compareMonth(String date,int month);
	
	//不过滤小数点后数字
	public double minusDateByMonths_2(String date1,String date2);

}
