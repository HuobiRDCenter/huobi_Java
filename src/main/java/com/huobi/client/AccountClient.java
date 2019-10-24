package com.huobi.client;

import java.util.List;

import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.SubAccountChangeRequest;
import com.huobi.client.req.account.TransferSubuserRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountChangeEvent;
import com.huobi.model.account.AccountReq;
import com.huobi.model.account.SubuserAggregateBalance;
import com.huobi.service.huobi.HuobiAccountService;
import com.huobi.utils.ResponseCallback;

public interface AccountClient {

  List<Account> getAccounts();

  AccountBalance getAccountBalance(AccountBalanceRequest request);

  long transferSubuser(TransferSubuserRequest request);

  List<AccountBalance> getSubuserAccountBalance(Long subuserId);

  List<SubuserAggregateBalance> getSubuserAggregateBalance();

  void subAccounts(SubAccountChangeRequest request, ResponseCallback<AccountChangeEvent> callback);

  void reqAccounts(ResponseCallback<AccountReq> callback);


  static AccountClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAccountService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
