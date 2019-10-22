package com.huobi.service.huobi;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.WalletClient;
import com.huobi.client.req.wallet.CreateWithdrawRequest;
import com.huobi.client.req.wallet.DepositAddressRequest;
import com.huobi.client.req.wallet.WithdrawQuotaRequest;
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.model.wallet.DepositAddress;
import com.huobi.model.wallet.WithdrawQuota;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.DepositAddressParser;
import com.huobi.service.huobi.parser.WithdrawQuotaParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;

public class HuobiWalletService implements WalletClient {


  public static final String GET_DEPOSIT_ADDRESS_PATH = "/v2/account/deposit/address";
  public static final String GET_WITHDRAW_QUOTA_PATH = "/v2/account/withdraw/quota";
  public static final String CREATE_WITHDRAW_PATH = "/v1/dw/withdraw/api/create";
  public static final String CANCEL_WITHDRAW_PATH = "/v1/dw/withdraw-virtual/{withdraw-id}/cancel";

  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiWalletService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }

  @Override
  public List<DepositAddress> getDepositAddress(DepositAddressRequest request) {

    // 验证参数
    InputChecker.checker()
        .shouldNotNull(request.getCurrency(),"currency");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency",request.getCurrency());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_DEPOSIT_ADDRESS_PATH,builder);
    JSONArray array = jsonObject.getJSONArray("data");
    return new DepositAddressParser().parseArray(array);
  }

  @Override
  public WithdrawQuota getWithdrawQuota(WithdrawQuotaRequest request) {
    // 验证参数
    InputChecker.checker()
        .shouldNotNull(request.getCurrency(),"currency");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency",request.getCurrency());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_WITHDRAW_QUOTA_PATH,builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new WithdrawQuotaParser().parse(data);
  }

  @Override
  public Long createWithdraw(CreateWithdrawRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getAddress(),"address")
        .shouldNotNull(request.getAmount(),"amount")
        .shouldNotNull(request.getCurrency(),"currency")
        .shouldNotNull(request.getFee(),"fee");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("address", request.getAddress())
        .putToPost("amount", request.getAmount())
        .putToPost("currency", request.getCurrency())
        .putToPost("fee", request.getFee())
        .putToPost("addr-tag", request.getAddrTag())
        .putToPost("chain",request.getChain());

    JSONObject jsonObject = restConnection.executePostWithSignature(CREATE_WITHDRAW_PATH,builder);
    return jsonObject.getLong("data");
  }

  @Override
  public Long cancelWithdraw(Long withdrawId) {

    InputChecker.checker()
        .shouldNotNull(withdrawId,"withdraw-id");

    String path = CANCEL_WITHDRAW_PATH.replace("{withdraw-id}",withdrawId+"");

    JSONObject jsonObject = restConnection.executePostWithSignature(path,UrlParamsBuilder.build());
    return jsonObject.getLong("data");
  }

  @Override
  public void getDepositWithdraw() {

  }


  public static void main(String[] args) {
    HuobiWalletService walletService = new HuobiWalletService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    List<DepositAddress> addressList = walletService.getDepositAddress(DepositAddressRequest.builder().currency("usdt").build());
    addressList.forEach(depositAddress -> {
      System.out.println(depositAddress.toString());
    });

//    WithdrawQuota withdrawQuota = walletService.getWithdrawQuota(WithdrawQuotaRequest.builder().currency("usdt").build());
//    System.out.println("currency:"+withdrawQuota.getCurrency());
//    withdrawQuota.getChains().forEach(withdrawChainQuota -> {
//      System.out.println(withdrawChainQuota.toString());
//    });

//
//    long withdrawId = walletService.createWithdraw(CreateWithdrawRequest.builder()
//        .address("huobideposit")
//        .addrTag("4101432")
//        .currency("eos")
//        .amount(new BigDecimal("1"))
//        .fee(new BigDecimal("0.1"))
//        .build());
//
//    System.out.println("-----------create withdraw : "+withdrawId+"------------------");

//    long res = walletService.cancelWithdraw(1638216801L);
//    System.out.println("-----------cancel withdraw : "+res+"------------------");



  }
}
