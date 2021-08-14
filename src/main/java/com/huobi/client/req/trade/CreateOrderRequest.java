package com.huobi.client.req.trade;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.OrderSourceEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.StopOrderOperatorEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderRequest {


  private Long accountId;

  private String symbol;

  private OrderTypeEnum type;

  private BigDecimal price;

  private BigDecimal amount;

  private String source;

  private String clientOrderId;

  private BigDecimal stopPrice;

  private StopOrderOperatorEnum operator;

  private OrderSourceEnum orderSource;

  //------------------------------- Spot ---------------------------------------//

  public static CreateOrderRequest spotBuyLimit(Long spotAccountId, String symbol, BigDecimal price, BigDecimal amount) {
    return spotBuyLimit(spotAccountId,null, symbol, price, amount);
  }

  public static CreateOrderRequest spotBuyLimit(Long spotAccountId,String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(spotAccountId)
        .type(OrderTypeEnum.BUY_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .orderSource(OrderSourceEnum.API)
        .build();
  }

  public static CreateOrderRequest spotSellLimit(Long spotAccountId,String symbol, BigDecimal price, BigDecimal amount) {
    return spotSellLimit(spotAccountId,null, symbol, price, amount);
  }

  public static CreateOrderRequest spotSellLimit(Long spotAccountId,String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(spotAccountId)
        .type(OrderTypeEnum.SELL_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .orderSource(OrderSourceEnum.API)
        .build();
  }

  public static CreateOrderRequest spotBuyMarket(Long spotAccountId,String symbol, BigDecimal amount) {
    return spotBuyMarket(spotAccountId,null, symbol, amount);
  }

  public static CreateOrderRequest spotBuyMarket(Long spotAccountId,String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(spotAccountId)
        .type(OrderTypeEnum.BUY_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .orderSource(OrderSourceEnum.API)
        .build();
  }

  public static CreateOrderRequest spotSellMarket(Long spotAccountId,String symbol, BigDecimal amount) {
    return spotSellMarket(spotAccountId,null, symbol, amount);
  }

  public static CreateOrderRequest spotSellMarket(Long spotAccountId,String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(spotAccountId)
        .type(OrderTypeEnum.SELL_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .orderSource(OrderSourceEnum.API)
        .build();
  }

  //--------------------------------- Margin -----------------------------------------//

  public static CreateOrderRequest marginBuyLimit(Long marginAccountId,String symbol, BigDecimal price, BigDecimal amount) {
    return marginBuyLimit(marginAccountId,null, symbol, price, amount);
  }

  public static CreateOrderRequest marginBuyLimit(Long marginAccountId,String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(marginAccountId)
        .type(OrderTypeEnum.BUY_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .orderSource(OrderSourceEnum.MARGIN_API)
        .build();
  }

  public static CreateOrderRequest marginSellLimit(Long marginAccountId,String symbol, BigDecimal price, BigDecimal amount) {
    return marginSellLimit(marginAccountId,null, symbol, price, amount);
  }

  public static CreateOrderRequest marginSellLimit(Long marginAccountId,String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(marginAccountId)
        .type(OrderTypeEnum.SELL_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .orderSource(OrderSourceEnum.MARGIN_API)
        .build();
  }

  public static CreateOrderRequest marginBuyMarket(Long marginAccountId,String symbol, BigDecimal amount) {
    return marginBuyMarket(marginAccountId,null, symbol, amount);
  }

  public static CreateOrderRequest marginBuyMarket(Long marginAccountId,String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(marginAccountId)
        .type(OrderTypeEnum.BUY_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .orderSource(OrderSourceEnum.MARGIN_API)
        .build();
  }

  public static CreateOrderRequest marginSellMarket(Long marginAccountId,String symbol, BigDecimal amount) {
    return marginSellMarket(marginAccountId,null, symbol, amount);
  }

  public static CreateOrderRequest marginSellMarket(Long marginAccountId,String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(marginAccountId)
        .type(OrderTypeEnum.SELL_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .orderSource(OrderSourceEnum.MARGIN_API)
        .build();
  }

  //-------------------------------Super Margin---------------------------------------//

  public static CreateOrderRequest superMarginBuyLimit(Long superMarginAccountId,String symbol, BigDecimal price, BigDecimal amount) {
    return superMarginBuyLimit(superMarginAccountId,null, symbol, price, amount);
  }

  public static CreateOrderRequest superMarginBuyLimit(Long superMarginAccountId,String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(superMarginAccountId)
        .type(OrderTypeEnum.BUY_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .orderSource(OrderSourceEnum.SUPER_MARGIN_API)
        .build();
  }

  public static CreateOrderRequest superMarginSellLimit(Long superMarginAccountId,String symbol, BigDecimal price, BigDecimal amount) {
    return superMarginSellLimit(superMarginAccountId,null, symbol, price, amount);
  }

  public static CreateOrderRequest superMarginSellLimit(Long superMarginAccountId,String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(superMarginAccountId)
        .type(OrderTypeEnum.SELL_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .orderSource(OrderSourceEnum.SUPER_MARGIN_API)
        .build();
  }

  public static CreateOrderRequest superMarginBuyMarket(Long superMarginAccountId,String symbol, BigDecimal amount) {
    return superMarginBuyMarket(superMarginAccountId,null, symbol, amount);
  }

  public static CreateOrderRequest superMarginBuyMarket(Long superMarginAccountId,String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(superMarginAccountId)
        .type(OrderTypeEnum.BUY_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .orderSource(OrderSourceEnum.SUPER_MARGIN_API)
        .build();
  }

  public static CreateOrderRequest superMarginSellMarket(Long superMarginAccountId,String symbol, BigDecimal amount) {
    return superMarginSellMarket(superMarginAccountId,null, symbol, amount);
  }

  public static CreateOrderRequest superMarginSellMarket(Long superMarginAccountId,String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountId(superMarginAccountId)
        .type(OrderTypeEnum.SELL_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .orderSource(OrderSourceEnum.SUPER_MARGIN_API)
        .build();
  }


  //-------------------------------Stop Loss---------------------------------------//
  public static CreateOrderRequest buyStopLoss(Long accountId, String symbol, BigDecimal price, BigDecimal amount,
      BigDecimal stopPrice, StopOrderOperatorEnum operator, OrderSourceEnum orderSource) {
    return buyStopLoss(null, accountId, symbol, price, amount, stopPrice, operator,orderSource);
  }

  public static CreateOrderRequest buyStopLoss(String clientOrderId, Long accountId, String symbol,
      BigDecimal price, BigDecimal amount, BigDecimal stopPrice, StopOrderOperatorEnum operator, OrderSourceEnum orderSource) {
    return CreateOrderRequest.builder()
        .accountId(accountId)
        .type(OrderTypeEnum.BUY_STOP_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .stopPrice(stopPrice)
        .operator(operator)
        .orderSource(orderSource)
        .build();
  }


  public static CreateOrderRequest sellStopLoss(Long accountId, String symbol, BigDecimal price, BigDecimal amount,
      BigDecimal stopPrice, StopOrderOperatorEnum operator, OrderSourceEnum orderSource) {
    return sellStopLoss(null, accountId, symbol, price, amount, stopPrice, operator,orderSource);
  }

  public static CreateOrderRequest sellStopLoss(String clientOrderId, Long accountId, String symbol,
      BigDecimal price, BigDecimal amount, BigDecimal stopPrice, StopOrderOperatorEnum operator, OrderSourceEnum orderSource) {
    return CreateOrderRequest.builder()
        .accountId(accountId)
        .type(OrderTypeEnum.SELL_STOP_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .stopPrice(stopPrice)
        .operator(operator)
        .orderSource(orderSource)
        .build();
  }
}
