package com.huobi.client;

import java.util.List;

import com.huobi.client.req.AccountBalanceRequest;
import com.huobi.client.req.SubAccountChangeRequest;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountChangeEvent;
import com.huobi.utils.ResponseCallback;

public interface AccountClient {

  List<Account> getAccounts();

  AccountBalance getAccountBalance(AccountBalanceRequest request);

  void transferSubuser();

  void getSubuserAccountBalance();

  void getSubuserAggregateBalance();

  void subAccounts(SubAccountChangeRequest request, ResponseCallback<AccountChangeEvent> callback);


}
