package com.huobi.client;

import java.util.List;

import com.huobi.client.req.account.AccountAssetValuationRequest;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.AccountFuturesTransferRequest;
import com.huobi.client.req.account.AccountHistoryRequest;
import com.huobi.client.req.account.AccountLedgerRequest;
import com.huobi.client.req.account.AccountTransferRequest;
import com.huobi.client.req.account.PointRequest;
import com.huobi.client.req.account.PointTransferRequest;
import com.huobi.client.req.account.SubAccountUpdateRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountAssetValuationResult;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountFuturesTransferResult;
import com.huobi.model.account.AccountHistory;
import com.huobi.model.account.AccountLedgerResult;
import com.huobi.model.account.AccountTransferResult;
import com.huobi.model.account.AccountUpdateEvent;
import com.huobi.model.account.Point;
import com.huobi.model.account.PointTransferResult;
import com.huobi.model.subuser.SubUserState;
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

  List<AccountHistory> getAccountHistory(AccountHistoryRequest request);

  AccountLedgerResult getAccountLedger(AccountLedgerRequest request);

  AccountTransferResult accountTransfer(AccountTransferRequest request);

  AccountFuturesTransferResult accountFuturesTransfer(AccountFuturesTransferRequest request);

  Point getPoint(PointRequest request);

  PointTransferResult pointTransfer(PointTransferRequest request);

  AccountAssetValuationResult accountAssetValuation(AccountAssetValuationRequest request);

  void subAccountsUpdate(SubAccountUpdateRequest request, ResponseCallback<AccountUpdateEvent> callback);

  static AccountClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAccountService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
