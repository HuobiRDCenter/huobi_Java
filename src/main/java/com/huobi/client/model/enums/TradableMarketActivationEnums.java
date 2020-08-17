package com.huobi.client.model.enums;

public enum  TradableMarketActivationEnums {

  ACTIVATED("activated"),
  DEACTIVATED("deactivated"),
  ;

  private final String activation;

  TradableMarketActivationEnums(String activation) {
    this.activation = activation;
  }

  public String getActivation() {
    return activation;
  }

  public static TradableMarketActivationEnums find(String activation) {
    for (TradableMarketActivationEnums activationEnums : TradableMarketActivationEnums.values()) {
      if (activationEnums.getActivation().equals(activation)) {
        return activationEnums;
      }
    }
    return null;
  }
}
