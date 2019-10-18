package com.huobi.client.req.trade;

import java.math.BigDecimal;

public class CreateOrderRequest {

  private Long accountId;

  private String symbol;

  private String type;

  private BigDecimal price;

  private BigDecimal amount;

  private String source;

  private String clientOrderId;

  private BigDecimal stopPrice;

  private String operator;

}
