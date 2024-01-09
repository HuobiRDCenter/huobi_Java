package com.huobi.model.generic;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Currency {
    private String name;
    private String dn;
    private Long vat;
    private Long det;
    private Long wet;
    private Integer wp;
    private String ct;
    private String cp;
    private String[] ss;
    private Integer oe;
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
    private Boolean we;
    private Boolean cawt;
    private Boolean cao;
    private Integer fc;
    private Integer sc;
    private String swd;
    private String wd;
    private String sdd;
    private String dd;
    private String svd;
    private String tags;
    private String fn;
    private String bc;
    private Boolean iqc;
}
