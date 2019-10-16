package com.huobi.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.ExchangeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HuobiOptions extends Options {

  @Builder.Default
  private String restHost = "https://api.huobi.vn";

  @Builder.Default
  private String websocketHost = "wss://api.huobi.vn";

  private String apiKey;

  private String secretKey;

  @Override
  public String getOptionsApiKey() {
    return this.apiKey;
  }

  @Override
  public String getOptionsSecretKey() {
    return this.secretKey;
  }

  @Override
  public ExchangeEnum getExchange() {
    return ExchangeEnum.HUOBI;
  }

  @Override
  public String getOptionRestHost() {
    return this.restHost;
  }

  @Override
  public String getOptionWebSocketHost() {
    return this.websocketHost;
  }
}
