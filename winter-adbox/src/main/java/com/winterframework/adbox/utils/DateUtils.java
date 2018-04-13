package com.winterframework.adbox.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/** 
* @ClassName: DateUtils 
* @Description: 日期处理工具类
* @author david 
* @date 2013-8-20 下午3:11:57 
*  
*/
public class DateUtils {

	/**
	 * ISO8601 formatter for date without time zone. The format used is <tt>yyyy-MM-dd</tt>.
	 */
	public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

	/**
	 * ISO8601 date format: yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	public static final String DATE_FORMAT_PATTERN_NO_SEPARATOR = "yyyyMMdd";

	/**
	 * ISO8601 date-time format: yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * for bet view format: yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_JSVIEW_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";
	public static final String DATETIME_SENDTIME_FORMAT_PATTERN = "yyyyMMddHHmmssSSS";
	/**
	 * 日期时间格式：yyyy-MM-dd HH:mm，不显示秒
	 */
	public static final String DATETIME_WITHOUT_SECOND_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";
	
	
	/**
	 * 日期时间格式：yyyy-MM-dd HH
	 */
	public static final String DATETIME_WITHOUT_MINUTES_FORMAT_PATTERN = "yyyy-MM-dd HH";

	/**
	 * 获得当前时间
	 */
	public static Date currentDate() {
		return new Date();
	}
	public static Date getDate(Long time) {
		return new Date(time);
	}
	public static Long getCurTime() {
		return System.currentTimeMillis();
	}
	public static Date parse(String str) {
		return parse(str, DATE_FORMAT_PATTERN);
	}

	public static Date parse(String str, String pattern) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		DateFormat parser = new SimpleDateFormat(pattern);
		try {
			return parser.parse(str);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Can't parse " + str + " using " + pattern);
		}
	}

	/**
	 * 根据时间变量返回时间字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		FastDateFormat df = FastDateFormat.getInstance(pattern);
		return df.format(date);
	}
	public static String format(Long time, String pattern) {
		if (time == null) {
			return null;
		}
		FastDateFormat df = FastDateFormat.getInstance(pattern);
		return df.format(new Date(time));
	}
	/**
	 * return date format is <code>yyyy-MM-dd</code>
	 */
	public static String format(Date date) {
		return date == null ? null : DATE_FORMAT.format(date);
	}

	public static Date getEndDateTimeOfCurrentYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getStartDateTimeOfCurrentYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate());
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return parse(format(cal.getTime()));

	}

	public static Date getStartDateTimeOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return parse(format(cal.getTime()));
	}

	public static Date getEndDateTimeOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getStartTimeOfCurrentDate() {
		return getStartTimeOfDate(currentDate());
	}

	public static Date getEndTimeOfCurrentDate() {
		return getEndTimeOfDate(currentDate());
	}

	public static Date getStartTimeOfCurrentMonth() {
		return getStartDateTimeOfMonth(DateUtils.currentDate());
	}

	public static Date getEndTimeOfCurrentMonth() {
		return getEndDateTimeOfMonth(DateUtils.currentDate());
	}

	public static Date getStartTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return parse(format(cal.getTime()));
	}

	public static Date getEndTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getSpecialEndTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
	}

	public static Date getStartDateTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return parse(format(cal.getTime()));
	}

	public static Date getStartDateTimeOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return parse(format(cal.getTime()));
	}

	public static Date getEndDateTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date addHours(Date date, int hours) {
		return add(date, Calendar.HOUR_OF_DAY, hours);
	}

	public static Date addMinutes(Date date, int minutes) {
		return add(date, Calendar.MINUTE, minutes);
	}

	public static Date addSeconds(Date date, int seconds) {
		return add(date, Calendar.SECOND, seconds);
	}

	public static Date addDays(Date date, int days) {
		return add(date, Calendar.DATE, days);
	}

	public static Date addMonths(Date date, int months) {
		return add(date, Calendar.MONTH, months);
	}

	public static Date addYears(Date date, int years) {
		return add(date, Calendar.YEAR, years);
	}

	private static Date add(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static long calcDateBetween(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}

		return ((end.getTime() - start.getTime()) / 86400001) + 1;
	}
	
	public static long calcHoursBetween(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}

		return ((end.getTime() - start.getTime()) / (60000*60));
	}
	
	
	public static Double calcHoursDoubleBetween(Date start, Date end) {
		if (start == null || end == null) {
			return 0d;
		}
		Double time = ((end.getTime() - start.getTime()) / (60000.0*60.0));
		return Double.valueOf(new java.text.DecimalFormat("#.0").format(time));
	}
	
	public static Double calcHoursDouble(Date start, Date end) {
		if (start == null || end == null) {
			return 0d;
		}
		//(new BigDecimal(end.getTime()).subtract(new BigDecimal(start.getTime()))).divide(new BigDecimal(60000.0*60.0), 7, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double time = (new BigDecimal(end.getTime()).subtract(new BigDecimal(start.getTime()))).divide(new BigDecimal(60000.0*60.0), 7, BigDecimal.ROUND_HALF_UP).doubleValue();//((end.getTime() - start.getTime()) / (60000.0*60.0));
		return time;
	}
	
	public static long calcMinutesBetween(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}

		return ((end.getTime() - start.getTime()) / 60000);
	}
	public static long calcSecondsBetween(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}
		return ((end.getTime() - start.getTime()) / 1000);
	}

	public static long calcSecondsBetween(Long start, Long end) {
		return (end - start) / 1000;
	}
	/**
	 * 获得日期是否为星期天。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isSunday(Date date) {
		return getDate(date) == Calendar.SUNDAY;
	}

	/**
	 * 获得日期是否为星期一。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isMonday(Date date) {
		return getDate(date) == Calendar.MONDAY;
	}

	/**
	 * 获得日期是否为星期二。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isTuesday(Date date) {
		return getDate(date) == Calendar.TUESDAY;
	}

	/**
	 * 获得日期是否为星期三。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isWednesday(Date date) {
		return getDate(date) == Calendar.WEDNESDAY;
	}

	/**
	 * 获得日期是否为星期四。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isThursday(Date date) {
		return getDate(date) == Calendar.THURSDAY;
	}

	/**
	 * 获得日期是否为星期五。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isFriday(Date date) {
		return getDate(date) == Calendar.FRIDAY;
	}

	/**
	 * 获得日期是否为星期六。
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isSaturday(Date date) {
		return getDate(date) == Calendar.SATURDAY;
	}

	public static int getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得相对于今天的昨天的时间。
	 * 
	 * @return 昨天此时。
	 */
	public static Date getYesterday() {
		return addDays(currentDate(), -1);
	}

	/**
	 * 获得月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获得天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHours(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinutes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	public static int getSeconds(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	
	public static int getMILLISECOND(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MILLISECOND);
	}
	
	public static long getTimeDiff(Date beginDate, Date endDate) {
		return (endDate.getTime() - beginDate.getTime()) / 1000;
	}
	
	public static int getDaysOfMonth(int year, int month) {
	Calendar   cal   =   Calendar.getInstance();   
	  cal.set(Calendar.YEAR,year);   
	  cal.set(Calendar.MONTH,month);//7月   
	  return  cal.getActualMaximum(Calendar.DATE);
	}
	
	public static long convertDate2Long(Date date) {
		if (date == null) {
			return 0l;
		}
		return date.getTime();
	}

	public static Date convertLong2Date(Long unixTimestamp) {
		if (unixTimestamp != null) {
			return new java.util.Date(unixTimestamp);
		}
		return null;
	}

	/**
	 * 判断时间是否在指定的时间范围内。
	 * 
	 * @param low
	 * @param high
	 * @return
	 */
	public static boolean between(Date low, Date high) {
		return currentDate().after(low) && currentDate().before(high);
	}
	
	public static int getDayValue(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return 7;
		} else {
			return i - 1;
		}
	}
	public static boolean isBefore(Date date1,Date date2) {
		return date1.getTime()<date2.getTime();
	}
	public static Date average(Date date1,Date date2) {
		return new Date((date1.getTime()+date2.getTime())/2);
	}
	
	public static int getDaysOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
    public static int getCurrentRemainingSeconds(){
		//*** 计算过期时间  一整天 获取   时：分：秒   
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY); // 时
		int minutes = calendar.get(Calendar.MINUTE);    // 分
		int seconds = calendar.get(Calendar.SECOND);    // 秒
        return 24*60*60 - hours*60*60- minutes*60 - seconds;
    }

	public static void main(String[] args) {
		//System.out.println(getMILLISECOND(new Date()));
		
		System.out.println(format(new Date(),DATETIME_SENDTIME_FORMAT_PATTERN));
		
	}

}