package com.huobi.model.subuser;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSubUserDepositResult {

  private List<SubUserDeposit> list;

  private Long nextId;

}
