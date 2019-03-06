package com.huobi.client.examples;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;
import java.math.BigDecimal;

public class PlaceOrder {
  public static void main(String[] args) {
    SyncRequestClient syncRequestClient = SyncRequestClient.create(
        "xxxxxx", "xxxxxx");
    NewOrderRequest newOrderRequest = new NewOrderRequest(
        "htbtc",
        AccountType.SPOT,
        OrderType.SELL_LIMIT,
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(1.0));

    // Place a new limit order.
    long orderId = syncRequestClient.createOrder(newOrderRequest);
    System.out.println("---- The new order created ----");

    // Get the order detail for order created above.
    Order orderInfo = syncRequestClient.getOrder("htbtc", orderId);
    System.out.println("Id: " + orderInfo.getOrderId());
    System.out.println("Type: " + orderInfo.getType());
    System.out.println("Status: " + orderInfo.getState());
    System.out.println("Amount: " + orderInfo.getAmount());
    System.out.println("Price: " + orderInfo.getPrice());

    // Cancel above order.
    syncRequestClient.cancelOrder("htbtc", orderId);

    // Confirm the order status after cancel.
    Order canceledOrderInfo = syncRequestClient.getOrder("htbtc", orderId);
    System.out.println("---- The order detail after cancel ----");
    System.out.println("Id: " + orderInfo.getOrderId());
    System.out.println("Status :" + canceledOrderInfo.getState());
  }
}
