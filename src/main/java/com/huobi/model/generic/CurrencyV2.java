package com.huobi.model.generic;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyV2 {
    private String cc;
    private String dn;
    private String fn;
    private Integer at;
    private Integer wp;
    private String ft;
    private String dma;
    private String wma;
    private String sp;
    private String w;
    private Boolean qc;
    private String state;
    private Boolean v;
    private Boolean whe;
    private Boolean cd;
    private Boolean de;
    private Boolean wed;
    private Boolean cawt;
    private Integer fc;
    private Integer sc;
    private String swd;
    private String wd;
    private String sdd;
    private String dd;
    private String svd;
    private String tags;
}
