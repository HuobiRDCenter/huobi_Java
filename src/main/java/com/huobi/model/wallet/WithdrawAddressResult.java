package com.huobi.model.wallet;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawAddressResult {

  private Long nextId;

  private List<WithdrawAddress> withdrawAddressList;

}
