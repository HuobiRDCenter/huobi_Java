package com.huobi.client;

import java.util.List;

import com.huobi.client.req.margin.IsolatedMarginAccountRequest;
import com.huobi.client.req.margin.IsolatedMarginApplyLoanRequest;
import com.huobi.client.req.margin.IsolatedMarginLoanOrdersRequest;
import com.huobi.client.req.margin.IsolatedMarginRepayLoanRequest;
import com.huobi.client.req.margin.IsolatedMarginTransferRequest;
import com.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;

public interface IsolatedMarginClient {

  Long transfer(IsolatedMarginTransferRequest request);

  Long applyLoan(IsolatedMarginApplyLoanRequest request);

  Long repayLoan(IsolatedMarginRepayLoanRequest request);

  List<IsolatedMarginLoadOrder> getLoanOrders(IsolatedMarginLoanOrdersRequest request);

  List<IsolatedMarginAccount> getLoanBalance(IsolatedMarginAccountRequest request);

}
