package com.xincaidong.calendardemo;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {
  public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  public static int selectPosition = -1;

  public static int getSelectPosition() {
    return selectPosition;
  }

  private static final String TAG = "DataUtils";

  /**
   * 获取当前日期一周的日期
   *
   * @param date
   * @return
   */
  public static ArrayList<DateEntity> getWeek(String date) {
    Log.d(TAG, "getWeek: " + date);
    ArrayList<DateEntity> result = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    try {
      cal.setTime(dateFormat.parse(date));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // 获取本周一的日期
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    for (int i = 0; i < 7; i++) {
      DateEntity entity = new DateEntity();
      entity.setDate(
          getValue(cal.get(cal.YEAR))
              + "-"
              + getValue(cal.get(cal.MONTH) + 1)
              + "-"
              + getValue(cal.get(cal.DATE)));
      entity.setMillion(cal.getTimeInMillis());
      entity.setDay(getValue(cal.get(cal.DATE)));
      entity.setWeekNum(cal.get(Calendar.DAY_OF_WEEK));
      entity.setWeekName(getWeekName(entity.getWeekNum()));
      entity.setToday(isToday(entity.getDate()));
      cal.add(Calendar.DATE, 1);
      result.add(entity);
    }

    return result;
  }
  /**
   * 获取当前日期一月的日期
   *
   * @param date
   * @return
   */
  public static ArrayList<DateEntity> getMonth(String date) {
    Log.d(TAG, "getMonth: " + date);
    ArrayList<DateEntity> result = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    try {
      cal.setTime(new SimpleDateFormat("yyyy-MM").parse(date));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    Log.d(TAG, "当前月份最大天数: " + max);
    for (int i = 1; i <= max; i++) {
      DateEntity entity = new DateEntity();
      entity.setDate(
          getValue(cal.get(cal.YEAR))
              + "-"
              + getValue(cal.get(cal.MONTH) + 1)
              + "-"
              + getValue(cal.get(cal.DATE)));
      entity.setMillion(cal.getTimeInMillis());
      entity.setWeekNum(cal.get(Calendar.DAY_OF_WEEK));
      entity.setDay(getValue(cal.get(cal.DATE)));
      entity.setWeekName(getWeekName(entity.getWeekNum()));
      entity.setToday(isToday(entity.getDate()));

      cal.add(Calendar.DATE, 1);
      result.add(entity);
      Log.d(TAG, "添加一天: " + entity);
    }
    // 为了用空的值填补第一个之前的日期
    // 先获取在本周内是周几
    int weekNum = result.get(0).getWeekNum() - 1;
    Log.d(TAG, "先获取在本周内是周几: " + weekNum);
    for (int j = 0; j < weekNum; j++) {
      DateEntity entity = new DateEntity();
      result.add(0, entity);
    }
    for (int i = 0; i < result.size(); i++) {
      if (date.equals(result.get(i).getDate())) {
        selectPosition = i;
      }
    }
    return result;
  }

  /**
   * 根据美式周末到周一 返回
   *
   * <p>日：1 一：2 二：3 三：4 四：5 五：6 六：7
   *
   * @param weekNum
   * @return
   */
  public static String getWeekName(int weekNum) {
    String name = "";
    switch (weekNum) {
      case Calendar.SUNDAY:
        name = "星期日";
        break;
      case Calendar.MONDAY:
        name = "星期一";
        break;
      case Calendar.TUESDAY:
        name = "星期二";
        break;
      case Calendar.WEDNESDAY:
        name = "星期三";
        break;
      case Calendar.THURSDAY:
        name = "星期四";
        break;
      case Calendar.FRIDAY:
        name = "星期五";
        break;
      case Calendar.SATURDAY:
        name = "星期六";
        break;
      default:
        break;
    }
    return name;
  }
  /**
   * 是否是今天
   *
   * @param sdate
   * @return
   */
  public static boolean isToday(String sdate) {
    boolean b = false;
    Date time = null;
    try {
      time = dateFormat.parse(sdate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Date today = new Date();
    if (time != null) {
      String nowDate = dateFormater.get().format(today);
      String timeDate = dateFormater.get().format(time);
      if (nowDate.equals(timeDate)) {
        b = true;
      }
    }
    return b;
  }
  /**
   * 个位数补0操作
   *
   * @param num
   * @return
   */
  public static String getValue(int num) {
    return String.valueOf(num > 9 ? num : ("0" + num));
  }

  private static final ThreadLocal<SimpleDateFormat> dateFormater =
      new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
          return new SimpleDateFormat("yyyy-MM-dd");
        }
      };

  /** 获取系统当前日期 */
  public static String getCurrDate(String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    Date curDate = new Date(System.currentTimeMillis()); // 获取当前时间
    String str = formatter.format(curDate);
    return str;
  }

  /** 格式化日期 */
  public static String formatDate(String date, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat formatter2 = new SimpleDateFormat(format);
    Date curDate = null; // 获取当前时间
    try {
      curDate = formatter.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String str = formatter2.format(curDate);
    return str;
  }

  /**
   * 切换周的时候用 获取前/后 几天的一个日期
   *
   * @param currentData
   * @param dayNum
   * @return
   */
  public static String getSomeDays(String currentData, int dayNum) {
    Calendar c = Calendar.getInstance();
    // 过去七天
    try {
      c.setTime(DataUtils.dateFormat.parse(currentData));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    c.add(Calendar.DATE, dayNum);
    Date d = c.getTime();
    String day = DataUtils.dateFormat.format(d);
    return day;
  }

  /**
   * 比较两个日期哪个更靠后一些
   *
   * @param time1 时间
   * @param time2 时间
   * @return true：time2在time1的后面，false：time2在time1的前面
   */
  public static boolean after(long time1, long time2) {
    Calendar c = Calendar.getInstance();
    Calendar d = Calendar.getInstance();
    c.setTimeInMillis(time1);
    d.setTimeInMillis(time2);
    Log.i("MainActivityFilter", "d在c后面：" + d.after(c));
    return d.after(c);
  }

  /**
   * 判断两个日期哪个更靠前
   *
   * @param time1 时间
   * @param time2 时间
   * @return true：time1在time2的前面，false：time1在time2的后面
   */
  public static boolean before(long time1, long time2) {
    Calendar c = Calendar.getInstance();
    Calendar d = Calendar.getInstance();
    c.setTimeInMillis(time1);
    d.setTimeInMillis(time2);
    Log.i("MainActivityFilter", "c在d前面：" + c.before(d));
    return c.before(d);
  }

  /**
   * 获取前/后 几个月的一个日期 切换月的时候用
   *
   * @param currentData
   * @param monthNum
   * @return
   */
  public static String getSomeMonthDay(String currentData, int monthNum) {
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(new SimpleDateFormat("yyyy-MM").parse(currentData));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    c.set(Calendar.MONTH, c.get(Calendar.MONTH) + monthNum);
    Date day = c.getTime();
    return new SimpleDateFormat("yyyy-MM-dd").format(day);
  }
}
