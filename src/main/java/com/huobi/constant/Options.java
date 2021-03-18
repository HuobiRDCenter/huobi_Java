package com.huobi.constant;

import com.huobi.constant.enums.ExchangeEnum;

public interface Options {

  String getApiKey();

  String getSecretKey();

  ExchangeEnum getExchange();

  String getRestHost();

  String getWebSocketHost();

  boolean isWebSocketAutoConnect();

  default String getOriginRestHost() {
    return "https://api.huobi.pro";
  }

}
