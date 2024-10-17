package com.huobi.model.trade;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderUpdateV2 {

  private String eventType;

  private String symbol;

  private Long orderId;

  private String clientOrderId;

  private String type;

  private String orderStatus;

  private BigDecimal remainAmt;

  private String orderSource;

  private String execAmt;

  /******* creation field ******/

  private BigDecimal orderPrice;

  private BigDecimal orderSize;


  private Long orderCreateTime;

  /******* trade field ******/

  private Long tradeId;

  private BigDecimal tradePrice;

  private BigDecimal tradeVolume;

  private Boolean aggressor;

  private Long tradeTime;


  /******* cancellation field ******/

  private Long lastActTime;

  private Long errCode;

  private String errMessage;

  private Long accountId;

}
