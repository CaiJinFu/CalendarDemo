package com.xincaidong.calendardemo;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DateEntity {
  /** 时间戳 */
  private long million;
  /** 周几 */
  private String weekName;
  /** 一周中第几天，非中式 */
  private int weekNum;
  /** 日期 */
  private String date;
  /** 是否今天 */
  private boolean isToday;
  /** 天 */
  private String day;
  /** 是否选中开始或者结束的日期 */
  private boolean isSelect;
  private boolean isSelect2;

  private int selectStatus = NORMAL;

  /** 选择日期开始 */
  public static final int START = 0;
  /** 选择日期结束 */
  public static final int END = 1;
  /** 选择日期开始到结束的范围内的日期 */
  public static final int RANGE = 2;
  /** 不在选中范围内日期 */
  public static final int NORMAL = 3;

  @IntDef({START, END, RANGE, NORMAL})
  @Retention(RetentionPolicy.SOURCE)
  public @interface SelectStatus {}

  public long getMillion() {
    return million;
  }

  public void setMillion(long million) {
    this.million = million;
  }

  public String getWeekName() {
    return weekName;
  }

  public void setWeekName(String weekName) {
    this.weekName = weekName;
  }

  public int getWeekNum() {
    return weekNum;
  }

  public void setWeekNum(int weekNum) {
    this.weekNum = weekNum;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public boolean isToday() {
    return isToday;
  }

  public void setToday(boolean today) {
    isToday = today;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public boolean isSelect() {
    return isSelect;
  }

  public void setSelect(boolean select) {
    isSelect = select;
  }

  public int getSelectStatus() {
    return selectStatus;
  }

  public void setSelectStatus(@SelectStatus int selectStatus) {
    this.selectStatus = selectStatus;
  }
}
