package com.xincaidong.calendardemo;

public class DateEntity {
  /** 时间戳 */
  public long million; // 时间戳
  /** 周几 */
  public String weekName;
  /** 一周中第几天，非中式 */
  public int weekNum;
  /** 日期 */
  public String date;
  /** 是否今天 */
  public boolean isToday;
  /** 天 */
  public String day;

  @Override
  public String toString() {
    return "DateEntity{"
        + "million="
        + million
        + ", weekName='"
        + weekName
        + '\''
        + ", weekNum="
        + weekNum
        + ", date='"
        + date
        + '\''
        + ", isToday="
        + isToday
        + ", day='"
        + day
        + '\''
        + '}';
  }
}
