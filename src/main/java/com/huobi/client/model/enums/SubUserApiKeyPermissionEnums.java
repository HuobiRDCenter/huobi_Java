package com.huobi.client.model.enums;

public enum  SubUserApiKeyPermissionEnums {

  READ_ONLY("readOnly"),
  TRADE("trade"),

  ;
  private final String permission;

  SubUserApiKeyPermissionEnums(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }

  public static SubUserApiKeyPermissionEnums find(String permission) {
    for (SubUserApiKeyPermissionEnums permissionEnums : SubUserApiKeyPermissionEnums.values()) {
      if (permissionEnums.getPermission().equals(permission)) {
        return permissionEnums;
      }
    }
    return null;
  }
}
