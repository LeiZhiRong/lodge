package com.shags.lodge.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class BeanUtil {

  private static final SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final SimpleDateFormat timeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static String addDayEnd(Date date, int add) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DAY_OF_MONTH, add);
    cal.set(11, 23);
    cal.set(12, 59);
    cal.set(13, 59);
    return dateToStr(cal.getTime(), null);
  }

  public static String addDayStart(Date date, int add) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DAY_OF_MONTH, add);
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    return dateToStr(cal.getTime(), null);
  }

  public static String addMonthEnd(Date date, int add) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(2, add);
    cal.set(5, cal.getActualMaximum(5));
    cal.set(11, 23);
    cal.set(12, 59);
    cal.set(13, 59);
    return dateToStr(cal.getTime(), null);
  }

  public static String addMonthStart(Date date, int add) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(2, add);
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    return dateToStr(cal.getTime(), null);
  }

  public static boolean dateFormatValidate(String date) {
    return date.matches("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");
  }

  public static Timestamp dateToTimestamp(Date date) {
    return new Timestamp(date.getTime());

  }

  public static int daysBetween(Date date1, Date date2) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date1);
    long time1 = cal.getTimeInMillis();
    cal.setTime(date2);
    long time2 = cal.getTimeInMillis();
    long between_days = (time2 - time1) / (1000 * 3600 * 24);
    return Integer.parseInt(String.valueOf(between_days));
  }


  public static String formatDate(Date date, String Str) {
    SimpleDateFormat format = new SimpleDateFormat(Str);
    try {
      return format.format(date);
    } catch (Exception e) {
      try {
        return daySdf.format(date);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    return "";
  }

  public static Date formatDate(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return format.parse(date);
    } catch (ParseException e) {
      try {
        return daySdf.parse(date);
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
    return new Date();
  }

  public static Date formatlongDate(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return format.parse(date);
    } catch (ParseException e) {
      try {
        return longSdf.parse(date);
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
    return new Date();
  }

  public static List<String> getAllDates(String start, String end) {
    List<String> result = new ArrayList<>();
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(formatDate(start));
    int day = tempStart.get(Calendar.DATE);
    tempStart.set(Calendar.DATE, day - 1);
    tempStart.add(Calendar.DAY_OF_YEAR, 1);
    Calendar tempEnd = Calendar.getInstance();
    tempEnd.setTime(formatDate(end));
    int endDay = tempEnd.get(Calendar.DATE);
    tempEnd.set(Calendar.DATE, endDay + 1);
    while (tempStart.before(tempEnd)) {
      result.add(dateToStr(tempStart.getTime(), "yyyy-MM-dd"));
      tempStart.add(Calendar.DAY_OF_YEAR, 1);
    }
    return result;

  }

  /**
   * 获取两个日期相隔的所有日期
   *
   * @param start
   * @param end
   * @return
   */
  public static List<String> getBetweenDates(Date start, Date end) {
    List<String> result = new ArrayList<>();
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(start);
    tempStart.add(Calendar.DAY_OF_YEAR, 1);
    Calendar tempEnd = Calendar.getInstance();
    tempEnd.setTime(end);
    while (tempStart.before(tempEnd)) {
      result.add(formatDate(tempStart.getTime()));
      tempStart.add(Calendar.DAY_OF_YEAR, 1);
    }
    return result;
  }

  /**
   * 两个日期相隔的所有日期
   *
   * @param start
   * @param end
   * @return
   */
  public static List<String> getBetweenDates(String start, String end) {
    List<String> result = new ArrayList<>();
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(formatDate(start));
    tempStart.add(Calendar.DAY_OF_YEAR, 1);
    Calendar tempEnd = Calendar.getInstance();
    tempEnd.setTime(formatDate(end));
    while (tempStart.before(tempEnd)) {
      result.add(dateToStr(tempStart.getTime(), "yyyy-MM-dd"));
      tempStart.add(Calendar.DAY_OF_YEAR, 1);
    }
    return result;
  }

  public static int getCountMonthBetween(String minDate, String maxDate) throws ParseException {
    ArrayList<String> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
    Calendar min = Calendar.getInstance();
    Calendar max = Calendar.getInstance();
    min.setTime(sdf.parse(minDate));
    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
    max.setTime(sdf.parse(maxDate));
    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
    while (min.before(max)) {
      result.add(sdf.format(min.getTime()));
      min.add(Calendar.MONTH, 1);
    }
    return result.size();
  }

  public static int[] getDateLength(String startDateStr, String endDateStr) {
    Calendar calS = Calendar.getInstance();
    Date startDate = formatDate(startDateStr);
    Date endDate = formatDate(endDateStr);
    calS.setTime(startDate);
    int startY = calS.get(Calendar.YEAR);
    int startM = calS.get(Calendar.MONTH);
    int startD = calS.get(Calendar.DATE);
    int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);
    calS.setTime(endDate);
    int endY = calS.get(Calendar.YEAR);
    int endM = calS.get(Calendar.MONTH);
    int endD = calS.get(Calendar.DATE) + 1;
    int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);
    int lday = endD - startD;
    if (lday < 0) {
      endM = endM - 1;
      lday = startDayOfMonth + lday;
    }
    if (lday == endDayOfMonth) {
      endM = endM + 1;
      lday = 0;
    }
    int mos = (endY - startY) * 12 + (endM - startM);
    int lyear = mos / 12;
    int lmonth = mos % 12;
    int nyear = 0;
    int nmonth = 0;
    int nday = 0;
    if (lyear > 0) {
      nyear = lyear;
    }
    if (lmonth > 0) {
      nmonth = lmonth;
    }
    if (lyear == 0) {
      if (lday > 0) {
        nday = lday - 1;
      }
    } else {
      if (lday > 0) {
        nday = lday;
      }
    }
    return new int[]{nyear, nmonth, nday};
  }

  public static int getDateQuarterIndex(String date) {
    Calendar c = Calendar.getInstance();
    c.setTime(formatDate(date));
    int currentMonth = c.get(2);
    try {
      if ((currentMonth >= 1) && (currentMonth <= 3))
        return 1;
      if ((currentMonth >= 4) && (currentMonth <= 6))
        return 2;
      if ((currentMonth >= 7) && (currentMonth <= 9))
        return 3;
      if ((currentMonth >= 10) && (currentMonth <= 12))
        return 4;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 1;
  }

  public static int getDateQuarterIndex(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int currentMonth = c.get(2);
    try {
      if ((currentMonth >= 1) && (currentMonth <= 3))
        return 1;
      if ((currentMonth >= 4) && (currentMonth <= 6))
        return 2;
      if ((currentMonth >= 7) && (currentMonth <= 9))
        return 3;
      if ((currentMonth >= 10) && (currentMonth <= 12))
        return 4;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 1;
  }

  public static String getDayOfLastMonthEnd(String date) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, -1);
    cal.setTime(formatDate(date));
    cal.set(5, cal.getActualMaximum(5));
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    return dateToStr(cal.getTime(), null);
  }



  public static int getDayOfMonth(Date date) {
    Date lastDayOfMonth = getLastDayOfMonth(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(lastDayOfMonth);
    return calendar.get(5);
  }

  /**
   * 获取月最后一天
   * @param date
   * @return
   */
  public static Date getLastDayOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(5, 1);
    cal.add(2, 1);
    cal.add(5, -1);
    return cal.getTime();
  }

  /**
   * 获取日期后多少天的日期
   * @param date
   * @param day
   * @return
   */
  public static Date getDayOfMonth(Date date, int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(5, day);
    return cal.getTime();
  }

  /**
   * 获取指定日期月份最后一天
   * @param date
   * @return
   */
  public static String getDayOfMonthEnd(String date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(formatDate(date));
    cal.set(5, cal.getActualMaximum(5));
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    return dateToStr(cal.getTime(), null);
  }

  /**
   * 获取指定日期月份第一天
   * @param date
   * @return
   */
  public static String getDayOfMonthStart(String date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(formatDate(date));
    cal.set(5, 1);
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    return dateToStr(cal.getTime(), null);
  }

  public static String dateToStr(Date date, String pattern) {
    if (isNullOrEmpty(pattern)) {
      pattern = "yyyy-MM-dd HH:mm:ss";
    }

    return date == null ? "" : new SimpleDateFormat(pattern).format(date);
  }

  public static Date getFirstDayOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(2, -1);
    cal.set(5, 1);
    return cal.getTime();
  }

  public static String getFriendlytime(Date d) {
    long deta = (new Date().getTime() - d.getTime()) / 1000;
    if (deta / (60 * 60 * 24 * 365) > 0)
      return deta / (60 * 60 * 24 * 365) + "年前";
    if (deta / (60 * 60 * 24 * 30) > 0)
      return deta / (60 * 60 * 24 * 30) + "个月前";
    if (deta / (60 * 60 * 24 * 7) > 0)
      return deta / (60 * 60 * 24 * 7) + "周前";
    if (deta / (60 * 60 * 24) > 0)
      return deta / (60 * 60 * 24) + "天前";
    if (deta / (60 * 60) > 0)
      return deta / (60 * 60) + "小时前";
    if (deta / (60) > 0)
      return deta / (60) + "分钟前";
    return "刚刚";
  }

  public static Integer getMonth(Timestamp date) {
    DateFormat sdf = new SimpleDateFormat("M");
    return Integer.parseInt(sdf.format(date));
  }

  /**
   * 返回两段日期之间所有月份
   *
   * @param minDate
   * @param maxDate
   * @return
   * @throws ParseException
   */
  public static String[] getMonthBetween(Timestamp minDate, Timestamp maxDate) throws ParseException {
    List<String> result = new ArrayList<String>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
    Calendar min = Calendar.getInstance();
    Calendar max = Calendar.getInstance();
    min.setTime(minDate);
    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
    max.setTime(maxDate);
    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
    while (min.before(max)) {
      result.add(sdf.format(min.getTime()));
      min.add(Calendar.MONTH, 1);
    }
    String[] array = result.toArray(new String[result.size()]);
    return array;
  }

  /**
   * 获取两段时间所包含的月份
   *
   * @param minDate
   * @param maxDate
   * @return
   * @throws ParseException
   */
  public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
    ArrayList<String> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
    Calendar min = Calendar.getInstance();
    Calendar max = Calendar.getInstance();
    min.setTime(sdf.parse(minDate));
    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
    max.setTime(sdf.parse(maxDate));
    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
    while (min.before(max)) {
      result.add(sdf.format(min.getTime()));
      min.add(Calendar.MONTH, 1);
    }
    return result;
  }

  public static int getMonthDay(String date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(formatDate(date));
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  @SuppressWarnings("deprecation")
  public static int getMonthOfDate(String date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(formatDate(date));
    return cal.getTime().getMonth() + 1;
  }

  /**
   * 获取下周第一天
   *
   * @param date
   * @return
   */
  public static String getNextWeekMonday(String date) {
    Calendar cal = Calendar.getInstance();
    Date d = formatDate(date);
    cal.setTime(d);
    int dw = cal.get(Calendar.DAY_OF_WEEK);
    cal.setTimeInMillis(cal.getTimeInMillis() + (9 - dw) * 24 * 60 * 60 * 1000);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(cal.getTime());
  }

  /**
   * 获取本周第一天
   *
   * @param date
   * @return
   */
  public static String getNowWeekMonday(String date) {
    Calendar cal = Calendar.getInstance();
    Date d = formatDate(date);
    cal.setTime(d);
    cal.add(Calendar.DAY_OF_MONTH, -1);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(cal.getTime());
  }

  public static int getNowYear() {
    Calendar date = Calendar.getInstance();
    String year = String.valueOf(date.get(Calendar.YEAR));
    return Integer.parseInt(year);
  }

  public static Date getQuarterEndTime(Date date, int q) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    Date now = null;
    try {
      switch (q) {
        case 1:
          c.set(2, 2);
          c.set(5, 31);
          break;
        case 2:
          c.set(2, 5);
          c.set(5, 30);
          break;
        case 3:
          c.set(2, 8);
          c.set(5, 30);
          break;
        case 4:
          c.set(2, 11);
          c.set(5, 31);
      }

      now = longSdf.parse(daySdf.format(c.getTime()) + " 23:59:59");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }

  public static Date getQuarterStartTime(Date date, int q) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    Date now = null;
    try {
      switch (q) {
        case 1:
          c.set(2, 0);
          break;
        case 2:
          c.set(2, 3);
          break;
        case 3:
          c.set(2, 6);
          break;
        case 4:
          c.set(2, 9);
      }

      c.set(5, 1);
      now = longSdf.parse(daySdf.format(c.getTime()) + " 00:00:00");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }

  public static String getTodayEnd(String date) {
    try {
      Calendar c = Calendar.getInstance();
      c.setTime(formatDate(date));
      return formatDate(longSdf.parse(daySdf.format(c.getTime()) + " 23:59:59"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static String getTodayStart(String date) {
    try {
      Calendar c = Calendar.getInstance();
      c.setTime(formatDate(date));
      return formatDate(longSdf.parse(daySdf.format(c.getTime()) + " 00:00:00"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static String formatDate(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return format.format(date);
    } catch (Exception e) {
      try {
        return daySdf.format(date);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    return "";
  }

  public static int getWeekDay(String date) {
    Calendar c = Calendar.getInstance();
    c.setTime(formatDate(date));
    return c.get(Calendar.DAY_OF_WEEK) - 1;
  }

  public static int getYearIndex(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(1);
  }

  public static String getYearMonth(Timestamp date) {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM");
    return sdf.format(date);
  }

  public static boolean isNotEmpty(String str) {
    return (str != null) && (!"".equals(str.trim()));
  }

  public static boolean isNull(Object value) {
    return value == null;
  }

  public static boolean isNull(Integer value) {
    return value == null;
  }

  public static boolean isNullOrEmpty(String str) {
    return str == null;
  }

  public static boolean isNullOrEmpty(Long value) {
    return value == null;
  }

  public static Date nextMonthFirstDate(String date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(formatDate(date));
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.add(Calendar.MONTH, 1);
    return cal.getTime();
  }

  public static String obj2Str(Object obj) {
    return obj == null ? "" : obj.toString();
  }

  public static Date parseDate(String dateStr, String pattern) throws ParseException {
    Date date;
    date = new SimpleDateFormat(pattern).parse(dateStr);
    return date;
  }

  public static String replaceChar(String data) {
    return data.replaceAll("[=,(,),']", "");
  }

  public static String showNullOrEmpty(String str) {
    return (str == null) || ("".equals(str.trim())) ? "" : str;
  }

  public static Timestamp strToTimestampDay(String strDate) {
    Date date = BeanUtil.formatDate(strDate);
    String str = BeanUtil.formatDate(date);
    return Timestamp.valueOf(str);
  }

  public static Timestamp strToTimestampTime(String strDate) {
    Date date = BeanUtil.formatTimeDate(strDate);
    return new Timestamp(date.getTime());
  }

  public static Date formatTimeDate(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return format.parse(date);
    } catch (ParseException e) {
      try {
        return timeSdf.parse(date);
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
    return new Date();
  }

  public static String timestampToStr(Timestamp date) {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  public static String timestampToStr(Timestamp date, String format) {
    DateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static String trim(String src) {
    return src == null ? null : src.trim();
  }

}