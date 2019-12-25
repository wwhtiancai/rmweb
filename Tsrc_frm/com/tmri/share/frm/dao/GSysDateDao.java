package com.tmri.share.frm.dao;

public interface GSysDateDao {

	// ��ȡϵͳ����
	public String getDBDate(String val);
	
	/*
	 * ��ȡϵͳ��ǰ���ڼӼ�һ��ʱ��ε�����
	 */
	public String getSysDate(String val);
	
	/*
	 * ��ȡϵͳ��ǰ���ڣ�Timestamp��
	 */
	public String getSysDateTimestamp();
	
	/*
	 * ��ȡϵͳ��ǰ���ڼӼ�һ��ʱ��ε�����
	 */
	public String getSysDateByFormat(String val, String format);

	// ��ȡϵͳʱ��
	public String getDBDateTimeToMinute(String value);

	// ��ȡϵͳ����
	public String getDBDateTime(String val);

	public String getMonthSysdate(String date, String v);

	public String getMonthSysdate_1(String date, String v);

	public String getDaySysdate(String date, String v);

	public int compareDay(String date);

	public int minusDateByMonths(String date1, String date2);

	public int minusDateByDays(String date1, String date2);

	public int minusDateByHour(String date1, String date2);

	public int compareMonth(String date,int month);
	
	//������С���������
	public double minusDateByMonths_2(String date1,String date2);

}
