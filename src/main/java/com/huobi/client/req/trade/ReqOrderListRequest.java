package com.huobi.client.req.trade;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.OrderStateEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.QueryDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqOrderListRequest {

  private Long accountId;

  private String symbol;

  private List<OrderStateEnum> states;

  private List<OrderTypeEnum> types;

  private Date startDate;

  private Date endDate;

  private Long from;

  private QueryDirectionEnum direction;

  private Integer size;


  public String getTypesString(){
    String typeString = null;
    if (this.getTypes() != null && this.getTypes().size() > 0) {
      StringBuffer typeBuffer = new StringBuffer();
      this.getTypes().forEach(orderType -> {
        typeBuffer.append(orderType.getCode()).append(",");
      });

      typeString = typeBuffer.substring(0, typeBuffer.length() - 1);
    }
    return typeString;
  }

  public String getStatesString(){
    String stateString = null;
    if (this.getStates() != null && this.getStates().size() > 0) {
      StringBuffer statesBuffer = new StringBuffer();
      this.getStates().forEach(orderState -> {
        statesBuffer.append(orderState.getCode()).append(",");
      });
      stateString = statesBuffer.substring(0, statesBuffer.length() - 1);
    }
    return stateString;
  }

}
