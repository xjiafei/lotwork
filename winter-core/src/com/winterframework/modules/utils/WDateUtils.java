package com.winterframework.modules.utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WDateUtils {
	private static final String DATEFORMAT="yyyy-MM-dd";
	private static final String DATETIMEFORMAT="yyyy-MM-dd HH:mm:ss";

    private WDateUtils() {
    };

    private static final Logger log = LoggerFactory.getLogger(WDateUtils.class);

    /**
     * 将字符串日期转换为Date
     * 
     * @param s
     * @return
     */
    public static Date convertToDate(String s) {
        DateFormat df;
        if (s == null) {
            return null;
        }
        if (s.length()==10) {
        	try {
                df = new SimpleDateFormat(DATEFORMAT);
                return df.parse(s);
            } catch (Exception e) {
                log.error("error to format date",e);
                return null;
            }
           
        } else {
        	 try {
                 df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 return df.parse(s);
             } catch (Exception e) {
            	 log.error("error to format date",e);
                 return null;
             }
            
        }
    }

    /**
     * 将Date转换为String
     * 
     * @param d
     * @return
     */
    public static String formatDate(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        try {
            return sdf.format(d);
        } catch (Exception e) {
        	 log.error("error to format date",e);
            return null;
        }
    }

    /**
     * 将Date转换为String
     * 
     * @param d
     * @return
     */
    public static String formatTime(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format(d);
        } catch (Exception e) {
        	 log.error("error to format date",e);
            return null;
        }
    }

    /**
     * 将Date按locale转换为String
     * 
     * @param d
     * @return
     */
    public static String formatLocaleDate(Date d, Locale locale) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT, locale);
        try {
            return sdf.format(d);
        } catch (Exception e) {
        	 log.error("error to format date",e);
            return null;
        }
    }

    /**
     * 将Date按locale转换为String
     * 
     * @param d
     * @return
     */
    public static String formatLocaleDateTime(Date d, Locale locale) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        try {
            return sdf.format(d);
        } catch (Exception e) {
        	 log.error("error to format date",e);
            return null;
        }
    }

    /**
     * 将Date转换为String
     * 
     * @param d
     * @return
     */
    public static String formatDateTime(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(d);
        } catch (Exception e) {
        	 log.error("error to format date",e);
            return null;
        }
    }

    /**
     * 得到每月多少天
     * 
     * @param year
     * @param month
     * @return 返回 -1表示异常
     */
    public static int getDaysByMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }

        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;

        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
        return -1;
    }

    public static String dayOfWeekByDayNum(int x) {
        String str = "周日";
        if (x == 7) {
            str = "周六";
        } else if (x == 6) {
            str = "周五";
        } else if (x == 5) {
            str = "周四";
        } else if (x == 4) {
            str = "周三";
        } else if (x == 3) {
            str = "周二";
        } else if (x == 2) {
            str = "周一";
        }
        return str;
    }

    /**
     * 据年、月、日，获得当天为周几
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getWeekByDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);

        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到现在是这个周的第几天
     * 
     * @return
     */
    public static int getWeekByDate() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static List<String> monthDiff(Date date1, Date date2) throws Exception {
        List<String> monthList = new ArrayList<String>();

        if (WDateUtils.dateDiff(date1, date2) < 0) {
            return monthList;
        }

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        while (WDateUtils.dateDiff(calendar1.getTime(), calendar2.getTime()) >= 0) {
            monthList.add(WDateUtils.formatDate(calendar1.getTime()));
            calendar1.set(Calendar.DAY_OF_MONTH, 1);
            calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) + 1);
        }
        if (monthList.size() > 0) {
            monthList.remove(monthList.size() - 1);
            monthList.add(WDateUtils.formatDate(date2));
        }

        return monthList;
    }

    /**
     * 计算两个日期之间相差多少天
     * 
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static int dateDiff(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        long increaseDate = (calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / 1000 / 60 / 60 / 24;
        if (((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) % (1000 * 60 * 60 * 24)) > 0) {
            increaseDate = increaseDate + 1;
        }
        return (int) increaseDate;
    }

    /**
     * 取得两天之间的天数
     * 
     * @param start
     * @param end
     * @return
     */
    public static int daysBetween(Date start, Date end) {
        // return date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000);
        String formatDate = formatDate(start);
        Date convertStartDate = convertToDate(formatDate);
        formatDate = formatDate(end);
        Date convertEndDate = convertToDate(formatDate);
        return (int) (convertEndDate.getTime() / 86400000 - convertStartDate.getTime() / 86400000); // 用立即数，减少乘法计算的开销
    }

    /**
     * 取得两天之间的日期数组，包含开始日期与结束日期
     * 
     * @param startDateStr 开始日期
     * @param endDateStr 结束日期
     * @return Date[] 日期数组
     */
    public static Date[] getBetweenTwoDayArray(String startDateStr, String endDateStr) {
        Date startDate = formatDateYyyyMmDd(startDateStr);
        int dateNum = Integer.parseInt(getDaysBetweenTwoDates(startDateStr, endDateStr)) + 1;
        Date[] dataArray = new Date[dateNum];
        for (int i = 0; i < dateNum; i++) {
            dataArray[i] = startDate;
            startDate = DateUtils.addDays(startDate, 1);
        }
        return dataArray;
    }

    /**
     * 把日期字符串格式化为日期类型
     * 
     * @param datetext
     * @return
     */
    public static Date formatDateYyyyMmDd(String datetext) {
        try {
            SimpleDateFormat df;
            if (datetext == null || "".equals(datetext.trim())) {
                return null;
            }
            datetext = datetext.replaceAll("/", "-");
            df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(datetext);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 两个日期之间相隔天数的共通 author:jerry.ji date:08-03-06
     * 
     * @param from 開始時間 @param to 終了時間 @return 天数
     */
    public static String getDaysBetweenTwoDates(String dateFrom, String dateEnd) {
        Date dtFrom = null;
        Date dtEnd = null;
        dtFrom = string2Date(dateFrom, "yyyy-MM-dd");
        dtEnd = string2Date(dateEnd, "yyyy-MM-dd");
        long begin = dtFrom.getTime();
        long end = dtEnd.getTime();
        long inter = end - begin;
        if (inter < 0) {
            inter = inter * (-1);
        }
        long dateMillSec = 24 * 60 * 60 * 1000;

        long dateCnt = inter / dateMillSec;

        long remainder = inter % dateMillSec;

        if (remainder != 0) {
            dateCnt++;
        }
        return String.valueOf(dateCnt);
    }

    /**
     * 把日期字符串格式化为日期类型
     * 
     * @param datetext 日期字符串
     * @param format 日期格式，如果不传则使用"yyyy-MM-dd HH:mm:ss"格式
     * @return
     */
    public static Date string2Date(String datetext, String format) {
        try {
            SimpleDateFormat df;
            if (datetext == null || "".equals(datetext.trim())) {
                return new Date();
            }
            if (format != null) {
                df = new SimpleDateFormat(format);
            } else {
                datetext = datetext.replaceAll("/", "-");
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }

            Date date = df.parse(datetext);

            return date;

        }

        catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String formatDate(Date date, String format) {
        try {
            if (format != null && !"".equals(format) && date != null) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                return formatter.format(date);
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static String addDays(String basicDate, int days) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(java.sql.Date.valueOf(basicDate));
            cal.add(GregorianCalendar.DATE, days);
            return formatDate(cal.getTime(), "yyyy-MM-dd");
        } catch (Exception e) {
            return "";
        }
    }

    public static String[] getBetweenTwoDayMonthArray(String startDateStr, String endDateStr) {
        Date[] dateArray = getBetweenTwoDayArray(startDateStr, endDateStr);

        String monthFirstDay = formatDateYyyyMmDd(dateArray[0]).substring(2, 7);
        List<String> dayList = new ArrayList();
        dayList.add(monthFirstDay);
        for (int i = 0; i < dateArray.length; i++) {
            if (!monthFirstDay.equals(formatDateYyyyMmDd(dateArray[i]).substring(2, 7))) {

                monthFirstDay = formatDateYyyyMmDd(dateArray[i]).substring(2, 7);
                dayList.add(monthFirstDay);
            }
        }

        String[] strArray = new String[dayList.size()];
        for (int i = 0; i < strArray.length; i++) {
            strArray[i] = dayList.get(i).substring(0, 2) + dayList.get(i).substring(3, 5);
        }

        return strArray;
    }

    public static String formatDateYyyyMmDd(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(date);

        } catch (Exception e) {
            return null;
        }
    }

    public static long getDaysBetweenTwoDatesMath(String dateFrom, String dateEnd) {
        Date dtFrom = null;
        Date dtEnd = null;
        dtFrom = string2Date(dateFrom, "yyyy-MM-dd");
        dtEnd = string2Date(dateEnd, "yyyy-MM-dd");
        long begin = dtFrom.getTime();
        long end = dtEnd.getTime();
        long inter = end - begin;
        long dateMillSec = 24 * 60 * 60 * 1000;
        long dateCnt = inter / dateMillSec;
        long remainder = inter % dateMillSec;
        if (remainder != 0) {
            dateCnt++;
        }
        return dateCnt;
    }

    public static Date parseToDate(String s) throws Exception {
        DateFormat df;
        if (s == null) {
            return null;
        }
        if (s.contains(":")) {
            try {
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.parse(s);
            } catch (Exception e) {
            	 log.error("error to format date",e);
                throw e;
            }
        } else {
            try {
                df = new SimpleDateFormat("yyyy-MM-dd");
                return df.parse(s);
            } catch (Exception e) {
            	 log.error("error to format date",e);
                throw e;
            }
        }
    }

    public static Date parseToDate2(String s) throws Exception {
        DateFormat df;
        if (s == null) {
            return null;
        }
        try {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(s);
        } catch (Exception e) {
        	 log.error("error to format date",e);
            throw e;
        }

    }

    /**
     * 得到yyyyMM的年月
     * 
     * @param date
     * @return
     */
    public static String getDateyyyyMMdd(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }
    
    /**
     * 时间字符串转Date
     * @param dateStr 时间字符串
     * @param format 时间格式
     * @return
     * @throws Exception
     */
    public static Date parseToDate(String dateStr,String format){
        DateFormat df;
        if (dateStr == null) {
            return null;
        }
        try {
            df = new SimpleDateFormat(format);
            return df.parse(dateStr);
        } catch (Exception e) {
            log.error("[parseToDate error] e="+e);
            return null;
        }
        
    }

    /**
     * 从指定日期移动一定的天数
     * 
     * @param date
     * @param day
     * @return
     */
    public static Date moveDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }
    
    public static void main(String[] args) {

        try {
            System.out.println(getDaysBetweenTwoDatesMath("2009-10-09", "2009-10-13"));
            System.out.println(dateDiff(parseToDate("2009-10-09"), parseToDate("2009-10-11")));;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
