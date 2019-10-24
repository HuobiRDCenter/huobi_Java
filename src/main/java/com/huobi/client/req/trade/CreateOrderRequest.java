package com.huobi.client.req.trade;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.StopOrderOperatorEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderRequest {

  private AccountTypeEnum accountType;

  private String symbol;

  private OrderTypeEnum type;

  private BigDecimal price;

  private BigDecimal amount;

  private String source;

  private String clientOrderId;

  private BigDecimal stopPrice;

  private StopOrderOperatorEnum operator;

  //------------------------------- Spot ---------------------------------------//

  public static CreateOrderRequest spotBuyLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return spotBuyLimit(null, symbol, price, amount);
  }

  public static CreateOrderRequest spotBuyLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SPOT)
        .type(OrderTypeEnum.BUY_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest spotSellLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return spotSellLimit(null, symbol, price, amount);
  }

  public static CreateOrderRequest spotSellLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SPOT)
        .type(OrderTypeEnum.SELL_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest spotBuyMarket(String symbol, BigDecimal amount) {
    return spotBuyMarket(null, symbol, amount);
  }

  public static CreateOrderRequest spotBuyMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SPOT)
        .type(OrderTypeEnum.BUY_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest spotSellMarket(String symbol, BigDecimal amount) {
    return spotSellMarket(null, symbol, amount);
  }

  public static CreateOrderRequest spotSellMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SPOT)
        .type(OrderTypeEnum.SELL_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .build();
  }

  //--------------------------------- Margin -----------------------------------------//

  public static CreateOrderRequest marginBuyLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return marginBuyLimit(null, symbol, price, amount);
  }

  public static CreateOrderRequest marginBuyLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.MARGIN)
        .type(OrderTypeEnum.BUY_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest marginSellLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return marginSellLimit(null, symbol, price, amount);
  }

  public static CreateOrderRequest marginSellLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.MARGIN)
        .type(OrderTypeEnum.SELL_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest marginBuyMarket(String symbol, BigDecimal amount) {
    return marginBuyMarket(null, symbol, amount);
  }

  public static CreateOrderRequest marginBuyMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.MARGIN)
        .type(OrderTypeEnum.BUY_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest marginSellMarket(String symbol, BigDecimal amount) {
    return marginSellMarket(null, symbol, amount);
  }

  public static CreateOrderRequest marginSellMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.MARGIN)
        .type(OrderTypeEnum.SELL_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .build();
  }

  //-------------------------------Super Margin---------------------------------------//

  public static CreateOrderRequest superMarginBuyLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return superMarginBuyLimit(null, symbol, price, amount);
  }

  public static CreateOrderRequest superMarginBuyLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SUPER_MARGIN)
        .type(OrderTypeEnum.BUY_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest superMarginSellLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return superMarginSellLimit(null, symbol, price, amount);
  }

  public static CreateOrderRequest superMarginSellLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SUPER_MARGIN)
        .type(OrderTypeEnum.SELL_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest superMarginBuyMarket(String symbol, BigDecimal amount) {
    return superMarginBuyMarket(null, symbol, amount);
  }

  public static CreateOrderRequest superMarginBuyMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SUPER_MARGIN)
        .type(OrderTypeEnum.BUY_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .build();
  }

  public static CreateOrderRequest superMarginSellMarket(String symbol, BigDecimal amount) {
    return superMarginSellMarket(null, symbol, amount);
  }

  public static CreateOrderRequest superMarginSellMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return CreateOrderRequest.builder()
        .accountType(AccountTypeEnum.SUPER_MARGIN)
        .type(OrderTypeEnum.SELL_MARKET)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .amount(amount)
        .build();
  }


  //-------------------------------Stop Loss---------------------------------------//
  public static CreateOrderRequest buyStopLoss(AccountTypeEnum accountType, String symbol,
      BigDecimal price, BigDecimal amount, BigDecimal stopPrice, StopOrderOperatorEnum operator) {
    return buyStopLoss(null, accountType, symbol, price, amount, stopPrice, operator);
  }

  public static CreateOrderRequest buyStopLoss(String clientOrderId, AccountTypeEnum accountType, String symbol,
      BigDecimal price, BigDecimal amount, BigDecimal stopPrice, StopOrderOperatorEnum operator) {
    return CreateOrderRequest.builder()
        .accountType(accountType)
        .type(OrderTypeEnum.BUY_STOP_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .stopPrice(stopPrice)
        .operator(operator)
        .build();
  }


  public static CreateOrderRequest sellStopLoss(AccountTypeEnum accountType, String symbol,
      BigDecimal price, BigDecimal amount, BigDecimal stopPrice, StopOrderOperatorEnum operator) {
    return sellStopLoss(null, accountType, symbol, price, amount, stopPrice, operator);
  }

  public static CreateOrderRequest sellStopLoss(String clientOrderId, AccountTypeEnum accountType, String symbol,
      BigDecimal price, BigDecimal amount, BigDecimal stopPrice, StopOrderOperatorEnum operator) {
    return CreateOrderRequest.builder()
        .accountType(accountType)
        .type(OrderTypeEnum.SELL_STOP_LIMIT)
        .clientOrderId(clientOrderId)
        .symbol(symbol)
        .price(price)
        .amount(amount)
        .stopPrice(stopPrice)
        .operator(operator)
        .build();
  }
}
