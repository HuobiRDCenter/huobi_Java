package com.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountUpdateEvent {

  private String action;

  private String topic;

  private AccountUpdate accountUpdate;

}
