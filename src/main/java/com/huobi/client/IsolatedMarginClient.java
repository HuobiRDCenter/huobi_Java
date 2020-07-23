package com.huobi.client;

import java.util.List;

import com.huobi.client.req.margin.IsolatedMarginAccountRequest;
import com.huobi.client.req.margin.IsolatedMarginApplyLoanRequest;
import com.huobi.client.req.margin.IsolatedMarginLoanInfoRequest;
import com.huobi.client.req.margin.IsolatedMarginLoanOrdersRequest;
import com.huobi.client.req.margin.IsolatedMarginRepayLoanRequest;
import com.huobi.client.req.margin.IsolatedMarginTransferRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;
import com.huobi.model.isolatedmargin.IsolatedMarginSymbolInfo;
import com.huobi.service.huobi.HuobiIsolatedMarginService;

public interface IsolatedMarginClient {

  Long transfer(IsolatedMarginTransferRequest request);

  Long applyLoan(IsolatedMarginApplyLoanRequest request);

  Long repayLoan(IsolatedMarginRepayLoanRequest request);

  List<IsolatedMarginLoadOrder> getLoanOrders(IsolatedMarginLoanOrdersRequest request);

  List<IsolatedMarginAccount> getLoanBalance(IsolatedMarginAccountRequest request);

  List<IsolatedMarginSymbolInfo> getLoanInfo(IsolatedMarginLoanInfoRequest request);

  static IsolatedMarginClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiIsolatedMarginService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
