package com.huobi.client;

import java.util.List;

import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.SubAccountChangeRequest;
import com.huobi.client.req.account.TransferSubuserRequest;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountChangeEvent;
import com.huobi.model.account.AccountReq;
import com.huobi.model.account.SubuserAggregateBalance;
import com.huobi.utils.ResponseCallback;

public interface AccountClient {

  List<Account> getAccounts();

  AccountBalance getAccountBalance(AccountBalanceRequest request);

  long transferSubuser(TransferSubuserRequest request);

  List<AccountBalance> getSubuserAccountBalance(Long subuserId);

  List<SubuserAggregateBalance> getSubuserAggregateBalance();

  void subAccounts(SubAccountChangeRequest request, ResponseCallback<AccountChangeEvent> callback);

  void reqAccounts(ResponseCallback<AccountReq> callback);


}
