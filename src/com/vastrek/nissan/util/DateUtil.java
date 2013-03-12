/**
 * 
 */
package com.vastrek.nissan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Frank
 *
 */
public class DateUtil {

	public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
	
	public static String getCurrDateShort(Date currDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(currDate);
	}
	
	public static String getCurrDate(Date currDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(currDate);
	}
	
	public static long getCurrDateLong() {
		return DateUtil.getToday().getTime();
	}
	
	public static long getLongDate(Date date) {
		return date.getTime();
	}
	
	public static String getShortDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("beijing"));
		return format.format(new Date(date));
	}
	
	public static String getYesterdayStr(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("beijing"));
		return format.format(new Date(date));
	}
	
	public static void main(String[] args) {
		//System.out.println("strhour:"+DateUtil.getShortDate(17957610));
	}
	
	public static String getDate(String strTime) {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			strTime = df.format(new Date(strTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}
	public static String getCurrDateTime() {
		Date currDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(currDate);
	}
	
	public static Date StringToDate(String d) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = (Date) sdf.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 字符串转Long
	 * @param str
	 * @return
	 */
	public static long getLongByStr(String str) {
		Date date = StringToDate(str);
		return getLongDate(date);
	}

	/**
	 * long字符串转时间格式String
	 * @param long1
	 * @return
	 * @throws Exception
	 */
	public static String getByLongStr(String long1) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(long1);
		String d = format.format(time);
		return d;
	}
	
}
