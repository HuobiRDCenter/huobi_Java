package com.huobi.client;

import java.util.List;

import com.huobi.client.req.wallet.CreateWithdrawRequest;
import com.huobi.client.req.wallet.DepositAddressRequest;
import com.huobi.client.req.wallet.WithdrawQuotaRequest;
import com.huobi.model.wallet.DepositAddress;
import com.huobi.model.wallet.WithdrawQuota;

public interface WalletClient {

  List<DepositAddress> getDepositAddress(DepositAddressRequest request);

  WithdrawQuota getWithdrawQuota(WithdrawQuotaRequest request);

  Long createWithdraw(CreateWithdrawRequest request);

  Long cancelWithdraw(Long withdrawId);

  void getDepositWithdraw();

}
