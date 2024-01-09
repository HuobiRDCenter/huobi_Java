package com.huobi.model.account;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountLedgerResult {

  private Long nextId;

  private List<AccountLedger> ledgerList;

}
