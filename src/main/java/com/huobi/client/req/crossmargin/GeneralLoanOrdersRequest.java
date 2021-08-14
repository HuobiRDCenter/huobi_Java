package com.huobi.client.req.crossmargin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.QuerySortEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeneralLoanOrdersRequest {

    private String repayId;

    private String accountId;

    private String currency;

    private long startTime;

    private long endTime;

    private QuerySortEnum sort;

    private int limit;

    private long fromId;

}
