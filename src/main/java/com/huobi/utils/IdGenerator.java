package com.huobi.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
  private static AtomicInteger COUNTER = new AtomicInteger();

  private static Set<Long> TIME_SET = new HashSet<>();

  public static Long getNextId(){

    Long time = System.currentTimeMillis();

    if (!TIME_SET.contains(time)) {
      COUNTER.set(0);
      TIME_SET.clear();
      TIME_SET.add(time);
    }

    Long id = (time * 1000) + COUNTER.addAndGet(1);
    return id;
  }
}
