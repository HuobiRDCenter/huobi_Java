package com.huobi.client.examples;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.QueryDirection;
import com.huobi.client.model.request.OrdersRequest;

public class SubscribeOrderUpdate {

  public static void main(String[] args) {

    String symbol = "htusdt";
    SubscriptionClient client = SubscriptionClient.create(Constants.API_KEY, Constants.SECRET_KEY);
    /**
     * subscribe order updateEvent ,old interface
     */
    client.subscribeOrderUpdateEvent(symbol,(event ->{
      System.out.println(event.getData().toString());
    }));

    /**
     * subscribe order updateEvent ,new interface (recommend)
     */
    client.subscribeOrderUpdateNewEvent(symbol,(event ->{
      System.out.println(event.getData().toString());
    }));

    /**
     * request order list event
     */
    List<OrderState> stateList = new ArrayList<>();
    stateList.add(OrderState.CANCELED);
    stateList.add(OrderState.FILLED);

    List<OrderType> typeList = new ArrayList<>();
    typeList.add(OrderType.BUY_LIMIT);
    typeList.add(OrderType.SELL_LIMIT);
    typeList.add(OrderType.BUY_MARKET);
    typeList.add(OrderType.SELL_MARKET);

    Date today = new Date();
    Date startDate = DateUtils.addDays(today, -2);

    long startOrderId = 48909764277L;

    OrdersRequest ordersRequest = new OrdersRequest(symbol, stateList, typeList, startDate, today, startOrderId, 20, QueryDirection.PREV);

    client.requestOrderListEvent(ordersRequest, orderListEvent -> {
      System.out.println("=================Request Order List======================");
      orderListEvent.getOrderList().forEach(order -> {
        System.out.println("Request Orders:" + order.toString());
      });
    });

    System.out.println("------------------------------------------");

    /**
     * request order detail event
     */
    client.requestOrderDetailEvent(startOrderId,orderListEvent -> {
      System.out.println("=================Request Order Detail======================");
      orderListEvent.getOrderList().forEach(order -> {
        System.out.println("Request Order:" + order.toString());
      });
    });

  }

}
