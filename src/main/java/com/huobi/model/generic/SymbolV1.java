package com.huobi.model.generic;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SymbolV1 {
    private String symbol;
    private String sn;
    private String bc;
    private String qc;
    private String state;
    private Boolean ve;
    private Boolean we;
    private Boolean dl;
    private Boolean cd;
    private Boolean te;
    private Boolean ce;
    private Long tet;
    private Long toa;
    private Long tca;
    private Long voa;
    private Long vca;
    private String sp;
    private String tm;
    private Integer w;
    private BigDecimal ttp;
    private BigDecimal tap;
    private BigDecimal tpp;
    private BigDecimal fp;
    private String tags;
    private String d;
    private String bcdn;
    private String qcdn;
    private String elr;
    private String castate;
    private Long ca1oa;
    private Long ca1ca;
    private Long ca2oa;
    private Long ca2ca;

}
