package com.huobi.client.req.crossmargin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeneralRepayLoanRequest {

    private String accountId;

    private String currency;

    private String amount;

    private String transactId;

}
