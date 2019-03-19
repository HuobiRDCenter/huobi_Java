package com.huobi.client.impl;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsInfoMap {

  private static final Map<String, User> userMap = new HashMap<>();

  static void updateUserInfo(String key, RestApiRequestImpl requestImpl) {
    List<Account> accounts = RestApiInvoker.callSync(requestImpl.getAccounts());
    User user = new User();
    user.setAccounts(accounts);
    AccountsInfoMap.userMap.put(key, user);
  }

  static User getUser(String key) {
    if ("".equals(key) || key == null) {
      throw new HuobiApiException(
          HuobiApiException.KEY_MISSING, "[User] Key is empty or null");
    }
    if (!userMap.containsKey(key)) {
      throw new HuobiApiException(
          HuobiApiException.RUNTIME_ERROR, "[User] Cannot found user by key: " + key);
    }
    return AccountsInfoMap.userMap.get(key);
  }

  static Account getAccount(String apiKey, long accountId) {
    User user = getUser(apiKey);
    Account account = user.getAccount(accountId);
    if (account == null) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[User] Cannot find the account, key: " + apiKey + ", account id: " + accountId);
    }
    return account;
  }
}
