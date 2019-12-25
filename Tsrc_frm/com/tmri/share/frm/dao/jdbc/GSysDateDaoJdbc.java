package com.tmri.share.frm.dao.jdbc;

import org.springframework.stereotype.Repository;

import com.tmri.share.frm.dao.GSysDateDao;

@Repository
public class GSysDateDaoJdbc extends FrmDaoJdbc implements GSysDateDao {

	// 获取系统日期
	public String getDBDate(String val) {
		String strSysdate = "";
		String sql = "select to_char(sysdate + " + val
				+ ",'yyyy-mm-dd') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	/*
	 * 获取系统当前日期加减一个时间段的日期
	 */
	public String getSysDate(String val) {
		return this.getDBDate(val);
	}

	/*
	 * 获取系统当前日期（Timestamp）
	 */
	public String getSysDateTimestamp() {
		String strSysdate = "";
		String sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||to_char(current_timestamp(4),'xff') from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	/*
	 * 获取系统当前日期加减一个时间段的日期
	 */
	public String getSysDateByFormat(String val, String format) {
		String strSysdate = "";
		String sql = null;
		if (val.substring(0, 1).equals("-")) {
			sql = "select to_char(sysdate" + val + ",'" + format
					+ "') dd from dual";
		} else {
			sql = "select to_char(sysdate+" + val + ",'" + format
					+ "') dd from dual";
		}
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统时间
	public String getDBDateTimeToMinute(String value) {
		String strSysdate = "";
		String sql = "select to_char(sysdate+" + value
				+ ",'yyyy-mm-dd hh24:mi') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统日期
	public String getDBDateTime(String val) {
		String strSysdate = "";
		String sql = "select to_char(sysdate+" + val
				+ ",'yyyy-mm-dd hh24:mi:ss') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public String getMonthSysdate(String date, String v) {
		String strSysdate = "";
		String sql = "select to_char(add_months(to_date('" + date
				+ "','YYYY-MM-DD')," + v + "),'YYYY-MM-DD') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public String getMonthSysdate_1(String date, String v) {
		String strSysdate = "";
		String sql = "select to_char(add_months(to_date('" + date
				+ "','YYYY-MM-DD')," + v + ")-1,'YYYY-MM-DD') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public String getDaySysdate(String date, String v) {
		String strSysdate = "";
		String sql = "select to_char(to_date('" + date + "','YYYY-MM-DD')+" + v
				+ ",'YYYY-MM-DD') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public int compareDay(String date) {
		Integer strSysdate;
		String sql = "select trunc(sysdate)-to_date('" + date
				+ "','YYYY-MM-DD') dd from dual";
		strSysdate = (Integer) this.jdbcTemplate.queryForObject(sql,
				Integer.class);
		return strSysdate.intValue();
	}

	public int minusDateByMonths(String date1, String date2) {
		Integer strSysdate;
		String sql = "select trunc(months_between(to_date('" + date1
				+ "','yyyy-mm-dd'),to_date('" + date2
				+ "','yyyy-mm-dd'))) dd from dual";
		strSysdate = (Integer) this.jdbcTemplate.queryForObject(sql,
				Integer.class);
		return strSysdate.intValue();
	}

	public int minusDateByDays(String date1, String date2) {
		Integer strSysdate;
		String sql = "select trunc(to_date('" + date1
				+ "','yyyy-mm-dd')-to_date('" + date2
				+ "','yyyy-mm-dd')) dd from dual";
		strSysdate = (Integer) this.jdbcTemplate.queryForObject(sql,
				Integer.class);
		return strSysdate.intValue();
	}

	public int minusDateByHour(String date1, String date2) {
		Integer strSysdate;
		String sql = "select trunc((to_date('" + date1
				+ "','yyyy-mm-dd hh24:mi:ss')-to_date('" + date2
				+ "','yyyy-mm-dd hh24:mi:ss'))*24) dd from dual";
		strSysdate = (Integer) this.jdbcTemplate.queryForObject(sql,
				Integer.class);
		return strSysdate.intValue();
	}

	public int compareMonth(String date,int month)
	{
		Integer strSysdate;
		String sql = "select trunc(sysdate)-add_months(to_date('" + date
				+ "','YYYY-MM-DD'),"+month+") dd from dual";
		strSysdate = (Integer) this.jdbcTemplate.queryForObject(sql,
				Integer.class);
		return strSysdate.intValue();		
	}
	
	
	//不过滤小数点后数字
	public double minusDateByMonths_2(String date1,String date2) {
		Double strSysdate;
		if (date1 != null && date1.length() > 10){
			date1 = date1.substring(0, 10);
		}
		if (date2 != null && date2.length() > 10){
			date2 = date2.substring(0, 10);
		}
		String sql = "select months_between(to_date('" + date1 + "','yyyy-mm-dd'),to_date('" + date2 + "','yyyy-mm-dd')) dd from dual";
		strSysdate = (Double) this.jdbcTemplate.queryForObject(sql,
				Double.class);
		return strSysdate.doubleValue();
	}

}
