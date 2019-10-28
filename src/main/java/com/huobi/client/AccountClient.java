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

  /**
   * Get User Account List
   * @return
   */
  List<Account> getAccounts();

  /**
   * Get User Account Balance
   * @param request
   * @return
   */
  AccountBalance getAccountBalance(AccountBalanceRequest request);

  /**
   * Transfer to sub-user
   * @param request
   * @return
   */
  long transferSubuser(TransferSubuserRequest request);

  /**
   * Get sub-user's account balance
   * @param subuserId
   * @return
   */
  List<AccountBalance> getSubuserAccountBalance(Long subuserId);

  /**
   * Get the aggregated balance of all sub-accounts of the current user.
   * @return
   */
  List<SubuserAggregateBalance> getSubuserAggregateBalance();

  /**
   * Use WebSocket connection subscribe Account assets change event
   * @param request
   * @param callback
   */
  void subAccounts(SubAccountChangeRequest request, ResponseCallback<AccountChangeEvent> callback);

  /**
   * Use WebSocket connection request Account Info
   * @param callback
   */
  void reqAccounts(ResponseCallback<AccountReq> callback);


  static AccountClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAccountService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
