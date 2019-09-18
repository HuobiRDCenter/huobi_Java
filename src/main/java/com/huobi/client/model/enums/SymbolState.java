package com.huobi.client.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  SymbolState {

  /**
   * ONLINE, OFFLINE, SUSPEND.
   */
  ONLINE("online"),
  OFFLINE("offline"),
  SUSPEND("suspend")
  ;
  private final String state;

  public static SymbolState find(String state) {
    for (SymbolState st : SymbolState.values()) {
      if (st.getState().equals(state)) {
        return st;
      }
    }
    return null;
  }

}
