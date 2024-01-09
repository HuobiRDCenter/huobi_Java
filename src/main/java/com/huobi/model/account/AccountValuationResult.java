package com.huobi.model.account;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountValuationResult {
    private String totalBalance;
    private String todayProfit;
    private String todayProfitRate;
    List<ProfitAccountBalance> profitAccountBalanceList;
    Updated updated;
}
