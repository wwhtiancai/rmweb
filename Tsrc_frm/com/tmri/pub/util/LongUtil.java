package com.tmri.pub.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LongUtil {
	// 处理统计
	public static String getCompare(String value1, String value2) {
		String result;
		if (value2.equals("0")) {
			result = "0";
		} else {
			long lvalue1 = Long.valueOf(value1).longValue();
			long lvalue2 = Long.valueOf(value2).longValue();
			double dvalue = (double) (lvalue1 - lvalue2) / lvalue2;
			result = String.valueOf(dvalue);
		}
		return result;
	}

	public static Long transNull(Long num) {
		Long result = num;
		if (num == null) {
			result = new Long(0);
		}
		return result;
	}

	private static String FormatColName(String colName) {
		return colName.substring(0, 1).toUpperCase()
				+ colName.substring(1).toLowerCase();
	}

	public static Object add2Obj_Long(Class type, Object obj1, Object obj2) {
		if (obj1 == null)
			return obj2;
		if (obj2 == null)
			return obj1;
		try {
			Field[] fs = type.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				String colName = fs[i].getName();
				if (fs[i].getType() == Long.class) {
					colName = FormatColName(colName);
					Long val1 = null, val2 = null;
					try {
						Method meth = type.getMethod("get" + colName);
						val1 = (Long) meth.invoke(obj1);
						val2 = (Long) meth.invoke(obj2);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Long colValue = val1 + val2;
					Object values[] = { colValue };
					try {
						Method meth = type.getMethod("set" + colName,
								Long.class);
						meth.invoke(obj1, values);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj1;
	}

	public static Object add2Obj_Long_name(Class type, Object obj1, Object obj2) {
		if (obj1 == null)
			return obj2;
		if (obj2 == null)
			return obj1;
		try {
			Field[] fs = type.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				String colName = fs[i].getName();
				// if (fs[i].getType() == Long.class) {
				if (colName.substring(0, 3).equals("num")) {
					colName = FormatColName(colName);
					Long val1 = null, val2 = null;
					try {
						Method meth = type.getMethod("get" + colName);
						String strobj1 = (String) meth.invoke(obj1);
						if (strobj1.equals("")) {
							strobj1 = "0";
						}

						String strobj2 = (String) meth.invoke(obj2);
						if (strobj2.equals("")) {
							strobj2 = "0";
						}
						val1 = Long.valueOf(strobj1).longValue();
						val2 = Long.valueOf(strobj2).longValue();

						Long colValue = val1 + val2;
						String strColvalue = String.valueOf(colValue);
						Object values[] = { strColvalue };
						try {
							Method meth2 = type.getMethod("set" + colName,
									String.class);
							meth2.invoke(obj1, values);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj1;
	}
}
