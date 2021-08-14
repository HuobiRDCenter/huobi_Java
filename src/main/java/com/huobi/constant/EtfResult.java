package com.huobi.constant;

public abstract class EtfResult {

  public static String checkResult(int code) {
    switch (code) {
      case 200:
        return "";
      case 10404:
        return "Invalid ETF name";
      case 13403:
        return "Insufficient asset to create ETF";
      case 13404:
        return "Create and redemption disabled due to system setup";
      case 13405:
        return "Create and redemption disabled due to configuration issue";
      case 13406:
        return "Invalid API call";
      case 13410:
        return "API authentication fails";
      case 13500:
        return "System error";
      case 13601:
        return "Create and redemption disabled during rebalance";
      case 13603:
        return "Create and redemption disabled due to other reason";
      case 13604:
        return "Create suspended";
      case 13605:
        return "Redemption suspended";
      case 13606:
        return "Amount incorrect. For the cases when creation amount or redemption amount is not in the range of min/max amount";
    }
    return "";
  }

}