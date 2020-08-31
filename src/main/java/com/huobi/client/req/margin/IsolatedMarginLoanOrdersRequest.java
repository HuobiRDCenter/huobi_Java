package com.huobi.client.req.margin;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.LoanOrderStateEnum;
import com.huobi.constant.enums.QueryDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginLoanOrdersRequest {

  private String symbol;

  private Date startDate;

  private Date endDate;

  private List<LoanOrderStateEnum> states;

  private Long from;

  private QueryDirectionEnum direction;

  private Integer size;

  private Long subUid;

  public String getStatesString(){
    String stateString = null;
    if (this.getStates() != null && this.getStates().size() > 0) {
      StringBuffer statesBuffer = new StringBuffer();
      this.getStates().forEach(orderType -> {
        statesBuffer.append(orderType.getCode()).append(",");
      });
      stateString = statesBuffer.substring(0, statesBuffer.length() - 1);
    }
    return stateString;
  }

}
