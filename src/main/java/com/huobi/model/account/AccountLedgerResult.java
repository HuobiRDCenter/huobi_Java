package com.huobi.model.account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLedgerResult {

  private Long nextId;

  private List<AccountLedger> ledgerList;

}
