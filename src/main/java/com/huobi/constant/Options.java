package com.huobi.constant;

import com.huobi.constant.enums.ExchangeEnum;

import java.net.Proxy;

public interface Options {

  String getApiKey();

  String getSecretKey();

  ExchangeEnum getExchange();

  String getRestHost();

  String getWebSocketHost();

  boolean isWebSocketAutoConnect();

  Proxy getProxy();

}
