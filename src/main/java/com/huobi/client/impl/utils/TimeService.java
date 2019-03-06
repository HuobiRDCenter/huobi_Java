package com.huobi.client.impl.utils;

public abstract class TimeService {

  public static long convertCSTInSecondToUTC(long timeInSecond) {
    if (timeInSecond > 946656000) {
      // bigger than 2000-01-01 00:00:00
      return (timeInSecond - 8 * 60 * 60) * 1000;
    }
    return 0;
  }

  public static long convertCSTInMillisecondToUTC(long timeInMs) {
    if (timeInMs > 946656000000L) {
      // bigger than 2000-01-01 00:00:00
      return timeInMs - 8 * 60 * 60 * 1000;
    }
    return 0;
  }

  public static long getCurrentTimeStamp() {
    return System.currentTimeMillis();
  }
}
