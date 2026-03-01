package com.huobi.client.req.market;

import lombok.*;

import com.huobi.constant.enums.DepthLevels;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubMbpRefreshUpdateRequest {

  private String symbols;

  private DepthLevels levels;

}
