package com.huobi.client.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  TransferFuturesDirection {

  FUTURES_TO_PRO("futures-to-pro"),
  PRO_TO_FUTURES("pro-to-futures")
  ;

  private String direction;

  public static TransferFuturesDirection find(String direction){
    for (TransferFuturesDirection d : TransferFuturesDirection.values()) {
      if (d.getDirection().equals(direction)) {
        return d;
      }
    }
    return null;
  }
}
