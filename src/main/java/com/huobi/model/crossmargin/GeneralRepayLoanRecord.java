package com.huobi.model.crossmargin;


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
public class GeneralRepayLoanRecord {

    private String repayId;

    private long repayTime;

    private String accountId;

    private String currency;

    private String repaidAmount;

    private Transact transactIds;

    private long nextId;

}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Transact {
    private long transactId;

    private String repaidPrincipal;

    private String repaidInterest;

    private String paidHt;

    private String paidPoint;

}
