package com.huobi.client.req.account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import com.huobi.constant.enums.QuerySortEnum;
import com.huobi.constant.enums.AccountHistoryTransactTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryRequest {

  private Long accountId;

  private String currency;

  private List<AccountHistoryTransactTypeEnum> types;

  private Long startTime;

  private Long endTime;

  private QuerySortEnum sort;

  private Integer size;

  public String getTypesString(){
    String typeString = null;
    if (this.getTypes() != null && this.getTypes().size() > 0) {
      typeString = StringUtils.join(types,",");
    }
    return typeString;
  }

}
