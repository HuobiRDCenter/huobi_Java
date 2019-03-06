package com.huobi.client.model;

import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.BalanceType;
import java.math.BigDecimal;

/**
 * The account change information received by subscription of account.
 */
public class AccountChange {

  private String currency = "";

  private AccountType accountType;

  private BigDecimal balance = new BigDecimal(0.0);

  private BalanceType balanceType;

  /**
   * Get the currency of the change.
   *
   * @return The currency type, like "btc".
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Get the account of the change.
   *
   * @return The account type like "SPOT", "OTC".
   */
  public AccountType getAccountType() {
    return accountType;
  }

  /**
   * Get the balance after the change. If the {@link BalanceMode} in {@link
   * com.huobi.client.SubscriptionClient#subscribeAccountEvent(BalanceMode, SubscriptionListener)}
   * is AVAILABLE, the balance refers to available balance. If the {@link BalanceMode} is TOTAL, the
   * balance refers to total balance for trade sub account (available+frozen)
   *
   * @return The balance value.
   */
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * Get the balance type.
   *
   * @return The balance type, like trade, loan, interest, see {@link BalanceType}
   */
  public BalanceType getBalanceType() {
    return balanceType;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public void setBalanceType(BalanceType balanceType) {
    this.balanceType = balanceType;
  }
}
