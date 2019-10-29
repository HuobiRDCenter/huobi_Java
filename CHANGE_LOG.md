# Huobi Java SDK Change Log



This is Huobi Java SDK, This is a lightweight java library, you can import to your java project and use this SDK to query all market data, trading and manage your account.



The SDK supports both synchronous RESTful API invoking, and subscribe the market data from the Websocket connection.







## Table of Contents

- [Huobi Global API Java SDK version 1.0.3](#Huobi-Global-API-Java-SDK-version-1.0.3)

- [Huobi Global API Java SDK version 1.0.2](#Huobi-Global-API-Java-SDK-version-1.0.2)

- [Huobi Global API Java SDK version 1.0.1](#Huobi-Global-API-Java-SDK-version-1.0.1)


## Huobi Global API Java SDK version 1.0.3

[***version 1.0.3***](https://github.com/HuobiRDCenter/huobi_Java/releases)

***2019-10-28***

修改的点：

- 类中增加字段

  - ```
    Account 
    CancelOpenOrderRequest 
    NewOrderRequest
    OpenOrderRequest
    中加入subtype字段
    ```

- 枚举：

  - lookup方法中找不到对应的枚举时不再抛异常，而是返回`sdk_not_support`，在各个枚举中加入`sdk_not_support`

  - LoanOrderStates 缺少`failed`字段

  - OrderState增加以下缺失字段

    ```
    pre_submmitted;
    submitting;
    failed;
    place_timeout;
    canceling;  
    ```

  - AccountType增加以下缺失字段
    ```
  minepool;
    etf;
    agency;
    super_margin;
    ```
  
- 方法重载

  - RequestClient类重载以下方法，增加subtype字段

  ```
  getAccountBalance 
  ```

  - User类重载以下方法，增加subtype字段

  ```
  getAccount
  ```

- account解析时增加subtype字段


## Huobi Global API Java SDK version 1.0.2

[***version 1.0.2***](https://github.com/HuobiRDCenter/huobi_Java/releases)

***2019-09-26***

1.  **Added support for the following Websocket request interface：**

> Market Websocket：

```
"req": "market.$symbol.kline.$period"
"req": "market.type"
"req": "market.$symbol.trade.detail"
"req": "market.$symbol.detail"
```

> Asset Websocket：

```
"topic": "accounts.list"
"topic": "orders.list"
"topic": "orders.detail"
```

2. **Added support following Websocket subscription topic:**

```
market.$symbol.bbo
```

 

## Huobi Global API Java SDK version 1.0.1

[***version 1.0.1***](https://github.com/HuobiRDCenter/huobi_Java/releases)

 ***2019-09-19***

1. **Supported following REST endpoints:**

```
 GET /v1/order/orders/getClientOrder
 POST /v1/order/orders/submitCancelClientOrder
 GET /v1/fee/fee-rate/get
 GET /v1/common/symbols
 POST /v1/futures/transfer
 GET /v1/order/history
 GET /market/trade
```

2. **Supported following Websocket subscription topic:**

 ```
 orders.$symbol.update
 ```

3. **Enhanced features to support request fields “from”, “direct”, and “size”, for following REST endpoints:**

```
 GET /v1/order/orders
 GET /v1/order/matchresults
 GET /v1/order/openOrders
```

4. **Enhanced features to support stop limit order type for following endpoint:**

```
POST /v1/order/orders/place
```

5. **Enhanced features to support response fields “role”, “filled-points”, and “fee-deduct-currency”, for following REST endpoints:**

 ```
 GET /v1/order/orders/{order-id}/matchresults
 GET /v1/order/matchresults
 ```
