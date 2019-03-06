package com.huobi.client.model;


import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import java.util.LinkedList;
import java.util.List;

/**
 * The account information for spot account, margin account etc.
 */
public class Account {


  private long id;


  private AccountType type;


  private AccountState state;


  private List<Balance> balances;

  /**
   * Get The balance list of the account.
   *
   * @return The balance list, see {@link Balance}
   */
  public List<Balance> getBalances() {
    return balances;
  }

  /**
   * Get the unique account id.
   *
   * @return The id.
   */
  public long getId() {
    return id;
  }

  /**
   * Get the type of this account, possible value: spot, margin, otc, point.
   *
   * @return The account type, see {@link AccountType}
   */
  public AccountType getType() {
    return type;
  }

  /**
   * Get the account state, possible value: working, lock.
   *
   * @return The status, see {@link AccountState}
   */
  public AccountState getState() {
    return state;
  }

  /**
   * Get The balance list of the specified currency.
   *
   * @param currency The currency you want to check.
   * @return The balance, see {@link Balance}
   */
  public List<Balance> getBalance(String currency) {
    List<Balance> result = new LinkedList<>();
    for (Balance balance : balances) {
      if (currency.equals(balance.getCurrency())) {
        result.add(balance);
      }
    }
    return result;
  }

  public void setBalances(List<Balance> balances) {
    this.balances = balances;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public void setState(AccountState state) {
    this.state = state;
  }
}
