package com.huobi.model.generic;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketSymbol {
    private String symbol;
    private String bc;
    private String qc;
    private String state;
    private String sp;
    private String tags;
    private BigDecimal lr;
    private BigDecimal smlr;
    private Integer pp;
    private Integer ap;
    private Integer vp;
    private BigDecimal minoa;
    private BigDecimal maxoa;
    private BigDecimal minov;
    private BigDecimal lominoa;
    private BigDecimal lomaxoa;
    private BigDecimal lomaxba;
    private BigDecimal lomaxsa;
    private BigDecimal smminoa;
    private BigDecimal smmaxoa;
    private BigDecimal bmmaxov;
    private BigDecimal blmlt;
    private BigDecimal slmgt;
    private BigDecimal msormlt;
    private BigDecimal mbormlt;
    private String at;
    private String u;
    private BigDecimal mfr;
    private String ct;
    private String rt;
    private BigDecimal rthr;
    private BigDecimal in;
    private BigDecimal maxov;
    private BigDecimal flr;
    private String castate;

}
