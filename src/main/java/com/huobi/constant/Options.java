package com.huobi.constant;

import com.huobi.constant.enums.ExchangeEnum;

public interface Options {

  public String getApiKey();

  public String getSecretKey();

  public ExchangeEnum getExchange();

  public String getRestHost();

  public String getWebSocketHost();

}
