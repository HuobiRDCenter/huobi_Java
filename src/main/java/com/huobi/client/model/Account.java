package com.huobi.client.model;


import java.util.LinkedList;
import java.util.List;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;

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

  public static RestApiJsonParser<List<Account>> getListParser() {
    return  (jsonWrapper -> {
      List<Account> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        res.add(parse(item));
      });
      return res;
    });
  }

  public static Account parse(JsonWrapper item){
    Account account = new Account();
    account.setId(item.getLong("id"));
    account.setType(AccountType.lookup(item.getString("type")));
    account.setState(AccountState.lookup(item.getString("state")));
    return account;
  }
}
