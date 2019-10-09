package com.huobi.client.model.event;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.Account;
import com.huobi.client.model.Balance;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;

/**
 * The account list event information received by request of account list.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountListEvent {

  private Long timestamp;

  private List<Account> accountList;


  public static RestApiJsonParser<AccountListEvent> getParser(){
    return (jsonWrapper) -> {
      long ts = TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts"));
      JsonWrapperArray array = jsonWrapper.getJsonArray("data");
      List<Account> accountList = new ArrayList<>();
      array.forEach(accountItem -> {
        Account account = Account.parse(accountItem);
        List<Balance> balanceList = new ArrayList<>();
        JsonWrapperArray balanceArray = accountItem.getJsonArray("list");
        balanceArray.forEach(balanceItem -> {
          balanceList.add(Balance.parse(balanceItem));
        });
        account.setBalances(balanceList);
        accountList.add(account);
      });

      return AccountListEvent.builder()
          .timestamp(ts)
          .accountList(accountList)
          .build();
    };
  }

}
