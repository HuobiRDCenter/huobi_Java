package com.huobi.client;

import java.util.List;

import com.huobi.client.req.crossmargin.CrossMarginApplyLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginLoanOrdersRequest;
import com.huobi.client.req.crossmargin.CrossMarginRepayLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginTransferRequest;
import com.huobi.model.crossmargin.CrossMarginAccount;
import com.huobi.model.crossmargin.CrossMarginLoadOrder;

public interface CrossMarginClient {

  Long transfer(CrossMarginTransferRequest request);

  Long applyLoan(CrossMarginApplyLoanRequest request);

  void repayLoan(CrossMarginRepayLoanRequest request);

  List<CrossMarginLoadOrder> getLoanOrders(CrossMarginLoanOrdersRequest request);

  CrossMarginAccount getLoanBalance();
}
