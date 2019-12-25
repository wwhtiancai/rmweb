package com.tmri.share.frm.service;

public interface GSysDateService {

	public String getSysDate(int days, int type);

	public String getSysDate(int days, String format);

	public int compareDay(String date);
	
	public String addMonths(int months, String date);

	public String addMonths_1(int months, String date);

	public String addDays(int days, String date);

	public int minusWithSysdate(String date);

	public int minusDateByMonths(String date1, String date2);

	public int minusDateByDays(String date1, String date2);

	public int minusDateByHour(String date1, String date2);
	
}
