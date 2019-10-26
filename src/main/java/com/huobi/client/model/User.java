package com.huobi.client.model;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.enums.AccountType;

import java.util.List;

/**
 * The user's account information, consisting of account and balance etc.
 */
public class User {


  private List<Account> accounts;

  /**
   * Get all accounts for each user.
   *
   * @return The accounts list, see {@link Account}
   */
  public List<Account> getAccounts() {
    return accounts;
  }

  /**
   * Get account by account type.
   *
   * @param accountType The specified account type, see {@link AccountType}
   * @return The account, see {@link Account}
   */
  public Account getAccount(AccountType accountType) {
    for (Account account : accounts) {
      if (account.getType() == accountType) {
        return account;
      }
    }
    return null;
  }

  public Account getAccount(AccountType accountType, String symbol) {
    if (AccountType.MARGIN != accountType) {
      return getAccount(accountType);
    }

    if (symbol == null || symbol.trim().length() <= 0) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR, "Margin Account Type need subtype.");
    }

    for (Account account : accounts) {
      if (account.getType() == accountType && symbol.equals(account.getSubtype())) {
        return account;
      }
    }
    return null;
  }

  /**
   * Get account by account id.
   *
   * @param accountId The specified account id.
   * @return The account, see {@link Account}
   */
  public Account getAccount(long accountId) {
    for (Account account : accounts) {
      if (account.getId() == accountId) {
        return account;
      }
    }
    return null;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }


}
