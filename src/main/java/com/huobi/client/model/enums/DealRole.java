package com.huobi.client.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DealRole {

  TAKER("taker"),
  MAKER("maker")
  ;

  private final String role;

  public static DealRole find(String role) {
    for (DealRole fr : DealRole.values()) {
      if (fr.getRole().equals(role)) {
        return fr;
      }
    }
    return null;
  }
}
