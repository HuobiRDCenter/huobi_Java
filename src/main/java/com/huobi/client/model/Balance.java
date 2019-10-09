package com.huobi.client.model;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.model.enums.BalanceType;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * The balance of specified account.
 */
public class Balance {

  private String currency;
  private BalanceType type;
  private BigDecimal balance;

  /**
   * Get the currency of this balance.
   *
   * @return The currency.
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Get the balance type, trade or frozen.
   *
   * @return The balance type, see {@link BalanceType}
   */
  public BalanceType getType() {
    return type;
  }

  /**
   * Get the balance in the main currency unit.
   *
   * @return The balance.
   */
  public BigDecimal getBalance() {
    return balance;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setType(BalanceType type) {
    this.type = type;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public static RestApiJsonParser<List<Balance>> getListParser() {
    return (jsonWrapper -> {
      List<Balance> balances = new LinkedList<>();
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      JsonWrapperArray listArray = data.getJsonArray("list");
      listArray.forEach((item) -> {
        balances.add(parse(item));
      });
      return balances;
    });
  }

  public static RestApiJsonParser<List<Balance>> getAggregatedBalanceParser(){
    return (jsonWrapper -> {
      List<Balance> balances = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        balances.add(parse(item));
      });
      return balances;
    });
  }

  public static Balance parse(JsonWrapper item) {
    BalanceType balanceType = null;
    String type = item.getStringOrDefault("type", null);
    if (type != null) {
      balanceType = BalanceType.lookup(type);
    }

    Balance balance = new Balance();
    balance.setBalance(item.getBigDecimal("balance"));
    balance.setCurrency(item.getString("currency"));
    balance.setType(balanceType);
    return balance;
  }
}
