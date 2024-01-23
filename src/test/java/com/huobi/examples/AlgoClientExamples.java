package com.huobi.examples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huobi.client.req.algo.*;
import com.huobi.model.algo.*;
import org.apache.commons.lang.RandomStringUtils;

import com.huobi.Constants;
import com.huobi.client.AlgoClient;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.algo.AlgoOrderSideEnum;
import com.huobi.constant.enums.algo.AlgoOrderStatusEnum;
import com.huobi.constant.enums.algo.AlgoOrderTimeInForceEnum;
import com.huobi.constant.enums.algo.AlgoOrderTypeEnum;

public class AlgoClientExamples {

  public static void main(String[] args) {

    String symbol = "htusdt";
    Long accountId = 59373420L;
    AlgoClient algoClient = AlgoClient.create(HuobiOptions.builder().apiKey(Constants.API_KEY).secretKey(Constants.SECRET_KEY).build());

    CreateAlgoOrderResult createAlgoOrderResult = algoClient.createAlgoOrder(CreateAlgoOrderRequest.builder()
        .clientOrderId(getRandomClientOrderId())
        .accountId(accountId)
        .symbol(symbol)
        .orderPrice(new BigDecimal("4"))
        .orderSize(new BigDecimal("1.5"))
        .orderSide(AlgoOrderSideEnum.BUY)
        .orderType(AlgoOrderTypeEnum.LIMIT)
        .timeInForce(AlgoOrderTimeInForceEnum.GTC)
        .stopPrice(new BigDecimal("4.1"))
        .build());

    System.out.println("create order result:" + createAlgoOrderResult);

    AlgoOrder algoOrder = algoClient.getAlgoOrdersSpecific(createAlgoOrderResult.getClientOrderId());
    System.out.println(algoOrder);

    GetOpenAlgoOrdersResult getOpenAlgoOrdersResult = algoClient.getOpenAlgoOrders(GetOpenAlgoOrdersRequest.builder().build());
    System.out.println("get open algo orders next:" + getOpenAlgoOrdersResult.getNextId());
    getOpenAlgoOrdersResult.getList().forEach(order -> {
      System.out.println(order);
    });

    List<String> cancelList = new ArrayList<String>();
    cancelList.add(createAlgoOrderResult.getClientOrderId());
    CancelAlgoOrderResult cancelAlgoOrderResult = algoClient.cancelAlgoOrder(CancelAlgoOrderRequest.builder().clientOrderIds(cancelList).build());
    System.out.println(cancelAlgoOrderResult);

    GetHistoryAlgoOrdersResult getHistoryAlgoOrdersResult = algoClient.getHistoryAlgoOrders(GetHistoryAlgoOrdersRequest.builder()
        .symbol(symbol)
        .orderStatus(AlgoOrderStatusEnum.CANCELED)
        .build());
    System.out.println("get history algo orders next:" + getHistoryAlgoOrdersResult.getNextId());
    getHistoryAlgoOrdersResult.getList().forEach(order -> {
      System.out.println(order);
    });

    CancelAlgoAllOrderRequest cancelAlgoAllOrderRequest = CancelAlgoAllOrderRequest.builder().timeout(10).build();
    CancelAlgoAllOrderResult cancelAlgoAllOrderResult = algoClient.cancelAlgoAllOrder(cancelAlgoAllOrderRequest);
    System.out.println(cancelAlgoAllOrderResult);

  }

  public static String getRandomClientOrderId() {
    return "d_" + System.nanoTime() + "_" + RandomStringUtils.randomAlphanumeric(4);
  }

}
