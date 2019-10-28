package com.huobi.service.huobi.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataUtils {

  public static String getDataKey(JSONObject jsonObject) {
    String key = "data";
    Object dataObj = jsonObject.get(key);
    if (dataObj == null) {
      key = "tick";
      dataObj = jsonObject.get("tick");
    }

    if (dataObj == null) {
      return null;
    }
    return key;
  }

  public static boolean isJSONArray(Object object) {
    if (object instanceof JSONArray) {
      return true;
    }
    return false;
  }

  public static boolean isJSONObject(Object object) {
    if (object instanceof JSONObject) {
      return true;
    }
    return false;
  }


  public static void timeWait(Long millis) {
    try {
      System.out.println("time wait " + millis + " ms");
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }
}
