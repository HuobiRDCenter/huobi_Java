package com.huobi.client;

import java.util.List;

import com.huobi.client.req.account.*;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.client.req.trade.SubTradeClearingRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.account.*;
import com.huobi.model.subuser.SubUserState;
import com.huobi.model.trade.OrderUpdateV2Event;
import com.huobi.model.trade.TradeClearingEvent;
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

  AccountValuationResult accountValuation(AccountValuationRequest request);

  AccountTransferV2Result accountTransferV2(AccountTransferV2Request request);

  void subAccountsUpdate(SubAccountUpdateRequest request, ResponseCallback<AccountUpdateEvent> callback);

  void subOrderUpdateV2(SubOrderUpdateV2Request request, ResponseCallback<OrderUpdateV2Event> callback);

  void subTradeClearing(SubTradeClearingRequest request, ResponseCallback<TradeClearingEvent> callback);


  static AccountClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAccountService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
