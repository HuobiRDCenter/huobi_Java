package com.huobi.constant;

import com.huobi.constant.enums.ExchangeEnum;

public abstract class Options {

  public abstract String getOptionsApiKey();

  public abstract String getOptionsSecretKey();

  public abstract ExchangeEnum getExchange();

  public abstract String getOptionRestHost();

  public abstract String getOptionWebSocketHost();

}
