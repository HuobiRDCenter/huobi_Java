package com.huobi.service.huobi.utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class GoogleAuthHelper {
  /**
   * 防止生成重复安全码
   * @param list      已生成的安全码
   * @return
   */
  public static String getRepetitionKeyStr(List<String> list) {
    AtomicInteger integer = new AtomicInteger(0);
    String code = "";
    while (integer.get() < 1) {
      code = getKeyStr();
      if((null == list || list.size() == 0) || !list.contains(code)) {
        integer.incrementAndGet();//原子自增
      }
    }
    return code;
  }

  /**
   * 获得为用户随机生成的安全码
   * @return
   */
  public static String getKeyStr() {
    GoogleAuthenticator gAuth  = new GoogleAuthenticator();
    final GoogleAuthenticatorKey key = gAuth .createCredentials();
    String keyStr = key.getKey();
//        System.out.println(keyStr);
    return keyStr;
  }

  /**
   * 判断输入的验证码是否符合
   * @param key        安全码
   * @param password   验证码,根据时间来生成的验证码
   * @return
   */
  public static boolean isPattern(String key,int password) {
    GoogleAuthenticator gAuth = new GoogleAuthenticator();
    boolean isPattern = gAuth.authorize(key,password);
//        System.out.println(isPattern);
    return isPattern;
  }


  /**
   * 获得TOTF算法生成的验证码,根据时间产生
   * @param secretKey   安全码
   * @return
   */
  public static int getVercodeTime(String secretKey) {
    GoogleAuthenticator gAuth = new GoogleAuthenticator();
    int code = gAuth.getTotpPassword(secretKey);
    return code;
  }

  public static void main(String[] args) {


  }
}
