package com.huobi.model.wallet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawAddressResult {

  private Long nextId;

  private List<WithdrawAddress> withdrawAddressList;

}
