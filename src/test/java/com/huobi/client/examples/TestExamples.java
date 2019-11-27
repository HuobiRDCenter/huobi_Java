package com.huobi.client.examples;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.request.AccountHistoryRequest;
import com.huobi.client.model.request.CandlestickRequest;
import com.huobi.client.model.request.CrossMarginLoanOrderRequest;
import com.huobi.client.model.request.LoanOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import com.huobi.client.model.request.OrdersRequest;

public class TestExamples {

  public static void main(String[] args) {
    String symbol = "htusdt,btcusdt";

    SubscriptionClient subscriptionClient = SubscriptionClient.create(Constants.API_KEY, Constants.SECRET_KEY);

    subscriptionClient.requestAccountListEvent(event->{

    });


  }

}
