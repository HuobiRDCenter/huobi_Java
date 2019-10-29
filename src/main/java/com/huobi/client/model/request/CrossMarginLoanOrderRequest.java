package com.huobi.client.model.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.model.enums.LoanOrderStates;
import com.huobi.client.model.enums.QueryDirection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginLoanOrderRequest {

  /**
   * Search starts date, in format yyyy-mm-dd
   */
  private Date startDate;

  /**
   * Search ends date, in format yyyy-mm-dd
   */
  private Date endDate;

  /**
   * Currency
   */
  private String currency;

  /**
   * Order status	: created,accrual,cleared,invalid
   */
  private LoanOrderStates state;

  /**
   * Search order id to begin with
   */
  private Long from;

  /**
   * Search direction when 'from' is used	 : next, prev
   */
  private QueryDirection direction;

  /**
   * The number of orders to return
   */
  private Integer size;

}
