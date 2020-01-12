package com.huobi.client.model.enums;

public enum SubuserManagementAction {

  LOCK("lock"),
  UNLOCK("unlock"),

  ;

  private final String action;


  SubuserManagementAction(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }

  public static SubuserManagementAction find(String action) {
    for (SubuserManagementAction act : SubuserManagementAction.values()) {
      if (act.getAction().equals(action)) {
        return act;
      }
    }
    return null;
  }
}
