package com.tmri.share.frm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.share.frm.dao.GSysDateDao;
import com.tmri.share.frm.service.GSysDateService;

@Service

public class GSysDateServiceImpl implements GSysDateService{

	@Autowired
	private GSysDateDao gSysDateDao;
	
	public String getSysDate(int days, int type) {
		String dayString = new Integer(days).toString();
		if (type == 1) {
			return this.gSysDateDao.getSysDate(dayString);
		} else if (type == 2) {
			return this.gSysDateDao.getDBDateTimeToMinute(dayString);
		} else if (type == 3) {
			return this.gSysDateDao.getDBDateTime(dayString);
		} else {
			return this.gSysDateDao.getSysDateTimestamp();
		}
	}

	public String getSysDate(int days, String format) {
		String dayString = gSysDateDao.getSysDateByFormat(days + "", format);
		return dayString;
	}

	public int compareDay(String date) {
		return this.gSysDateDao.compareDay(date);
	}

	public String addMonths(int months, String date) {
		String monthsString = new Integer(months).toString();
		return this.gSysDateDao.getMonthSysdate(date, monthsString);
	}

	public String addMonths_1(int months, String date) {
		String monthsString = new Integer(months).toString();
		return this.gSysDateDao.getMonthSysdate_1(date, monthsString);
	}

	public String addDays(int days, String date) {
		String daysString = new Integer(days).toString();
		return this.gSysDateDao.getDaySysdate(date, daysString);
	}

	public int minusWithSysdate(String date) {
		return this.gSysDateDao.compareDay(date);
	}

	public int minusDateByMonths(String date1, String date2) {
		return this.gSysDateDao.minusDateByMonths(date1, date2);
	}

	public int minusDateByDays(String date1, String date2) {
		return this.gSysDateDao.minusDateByDays(date1, date2);
	}

	public int minusDateByHour(String date1, String date2) {
		return this.gSysDateDao.minusDateByHour(date1, date2);
	}
}
