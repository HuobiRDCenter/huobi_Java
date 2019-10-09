package com.huobi.client.model;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.model.enums.AccountType;


import java.util.LinkedList;
import java.util.List;

/**
 *  sub-account completed info
 */
public class CompleteSubAccountInfo {
  private long id;
  private AccountType type;
  private List<Balance> balances;

  /**
   * get sub-id
   *
   * @return sud-id
   */
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  /**
   * get sub type
   *
   * @return sub type
   */
  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  /**
   * get balance list
   *
   * @return The balance list.
   */
  public List<Balance> getBalanceList() {
    return balances;
  }

  public void setBalances(List<Balance> balances) {
    this.balances = balances;
  }


  public static RestApiJsonParser<List<CompleteSubAccountInfo>> getListParser(){
    return (jsonWrapper -> {
      List<CompleteSubAccountInfo> completeSubAccountInfos = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        CompleteSubAccountInfo completeSubAccountInfo = new CompleteSubAccountInfo();
        completeSubAccountInfo.setId(item.getLong("id"));
        completeSubAccountInfo.setType(AccountType.lookup(item.getString("type")));
        JsonWrapperArray list = item.getJsonArray("list");
        List<Balance> balances = new LinkedList<>();
        list.forEach((val) -> {
          balances.add(Balance.parse(val));
        });
        completeSubAccountInfo.setBalances(balances);
        completeSubAccountInfos.add(completeSubAccountInfo);
      });
      return completeSubAccountInfos;
    });
  }

}
