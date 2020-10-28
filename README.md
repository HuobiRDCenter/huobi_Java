[![Build Status](https://travis-ci.com/HuobiRDCenter/huobi_Java.svg?branch=master)](https://travis-ci.com/HuobiRDCenter/huobi_Java)

# Huobi Java SDK v2

This is Huobi Java SDK v2, you can import to your project and use this SDK to query all market data, trading and manage your account. The SDK supports RESTful API invoking, and subscribing the market, account and order update from the WebSocket connection.

If you already use SDK v1, it is strongly suggested migrate to v2 as we refactor the implementation to make it simpler and easy to maintain. We will stop the maintenance of v1 in the near future. Please refer to the instruction on how to migrate v1 to v2 in section [Migrate from v1](#Migrate-from-v1)

## Table of Contents

- [Quick start](#Quick-start)
- [Usage](#Usage)
  - [Folder structure](#Folder-structure)
  - [Run examples](#Run-examples)
  - [Client](#client)
  - [Migrate from v1](#Migrate-from-v1)
- [Request example](#Request-example)
  - [Reference data](#Reference-data)
  - [Market data](#Market-data)
  - [Account](#account)
  - [Wallet](#wallet)
  - [Trading](#trading)
  - [Margin Loan](#margin-loan)
- [Subscription example](#Subscription-example)
  - [Subscribe trade update](#Subscribe-trade-update)
  - [Subscribe candlestick update](#subscribe-candlestick-update)
  - [Subscribe order update](#Subscribe-order-update)
  - [Subscribe account change](#subscribe-account-change)

## Quick start

*The SDK is compiled by Java8*, you can import the source code in java IDE (idea or eclipse)

The example code are in folder */java/src/test/java/com/huobi/examples* that you can run directly

If you want to create your own application, you can follow below steps:

* Create the client instance.
* Call the interfaces provided by client.

```java
// Create GenericClient instance and get the timestamp
GenericClient genericService = GenericClient.create(HuobiOptions.builder().build());

Long serverTime = genericService.getTimestamp();
System.out.println("server time:" + serverTime);

// Create MarketClient instance and get btcusdt latest 1-min candlestick
MarketClient marketClient = MarketClient.create(new HuobiOptions());

List<Candlestick> list = marketClient.getCandlestick(CandlestickRequest.builder()
    .symbol("btcusdt")
    .interval(CandlestickIntervalEnum.MIN1)
    .size(10)
    .build());

list.forEach(candlestick -> {
  System.out.println(candlestick.toString());
});
```

## Usage

### Folder Structure

This is the folder and package structure of SDK source code and the description

- **src/main/java/com/huobi**: The core of the SDK
  - **client**: The client that are responsible to access data, this is the external interface layer
  - **constant**: The enum and constant definition
  - **exception**: The exception definition
  - **model**: The data model for response
  - **service**: The internal implementation for each **client**
  - **utils**: The utilities that include signature, websocket management etc
- **src/test/java/com/huobi**: The test of the SDK
  - **examples**: The examples how to use **client** instance to access API and read response
  - **service**: The unit test for **service** package
  - **test**: The additional test such as performance test
  - **utils**: The unit test for **utils** package

### Run Examples


This SDK provides examples that under **src/test/java/com/huobi/example** folder, if you want to run the examples to access private data, you need below additional steps:

1. Create an **API Key** first from Huobi official website
2. Assign your API access key and secret key to "**Constant.java**" as below:

```java
public static final String API_KEY = "hrf5gdfghe-e74bebd8-2f4a33bc-e7963"
public static final String SECRET_KEY = "fecbaab2-35befe7e-2ea695e8-67e56"
```

If you don't need to access private data, you can ignore the API key.

Regarding the difference between public data and private data you can find details in [Client](https://github.com/HuobiRDCenter/huobi_Python#Client) section below.

### Client

In this SDK, the client is the class to access the Huobi API. In order to isolate the private data with public data, and isolated different kind of data, the client category is designated to match the API category.

All the client is listed in below table. Each client is very small and simple, it is only responsible to operate its related data, you can pick up multiple clients to create your own application based on your business.

| Data Category   | Client               | Privacy | API Protocol       |
| --------------- | -------------------- | ------- | ------------------ |
| Generic         | GenericClient        | Public  | Rest               |
| Market          | MarketClient         | Public  | Rest, WebSocket    |
| Account         | AccountClient        | Private | Rest, WebSocket v2 |
| Wallet          | WalletClient         | Private | Rest               |
| Sub user        | SubUserClient        | Private | Rest               |
| Trade           | TradeClient          | Private | Rest, WebSocket v2 |
| Algo            | AlgoClient           | Private | Rest               |
| Isolated margin | IsolatedMarginClient | Private | Rest               |
| Cross margin    | CrossMarginClient    | Private | Rest               |
| ETF             | ETFClient            | Private | Rest               |

#### Public and Private

There are two types of privacy that is correspondent with privacy of API:

**Public client**: It invokes public API to get public data (Generic data and Market data), therefore you can create a new instance without applying an API Key.

```java
// Create a GenericClient instance
GenericClient genericService = GenericClient.create(new HuobiOptions());

// Create a MarketClient instance
MarketClient marketClient = MarketClient.create(new HuobiOptions());
```

**Private client**: It invokes private API to access private data, you need to follow the API document to apply an API Key first, and pass the API Key to the init function

```java
// Create an AccountClient instance with APIKey
AccountClient accountService = AccountClient.create(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

// Create a TradeClient instance with API Key
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());
```

The API key is used for authentication. If the authentication cannot pass, the invoking of private interface will fail.

#### Rest and WebSocket

There are two protocols of API, Rest and WebSocket

**Rest**: It invokes Rest API and get once-off response, it has two basic types of method: GET and POST

**WebSocket**: It establishes WebSocket connection with server and data will be pushed from server actively. There are two types of method for WebSocket client:

- Request method: The method name starts with "req-", it will receive the once-off data after sending the request.
- Subscription: The method name starts with "sub-", it will receive update after sending the subscription.

### Migrate from v1

#### Why v2

The major difference between v1 and v2 is that the client category.

In SDK v1, the client is categorized as two protocol, request client and subscription client. For example, for Rest API, you can operate everything in request client. It is simple to choose which client you use, however, when you have a client instance, you will have dozens of method, and it is not easy to choose the proper method.

The thing is different in SDK v2, the client class is categorized as seven data categories, so that the responsibility for each client is clear. For example, if you only need to access market data, you can use MarketClient without applying API Key, and all the market data can be retrieved from MarketClient. If you want to operate your order, then you know you should use TradeClient and all the order related methods are there. Since the category is exactly same as the API document, so it is easy to find the relationship between API and SDK. In SDK v2, each client is smaller and simpler, which means it is easier to maintain and less bugs.

#### How to migrate

You don't need to change your business logic, what you need is to find the v1 request client and subscription client, and replace with the proper v2 client. The additional cost is that you need to have additional initialization for each v2 client.

## Request example

### Reference data

#### Exchange timestamp

```java
GenericClient genericService = GenericClient.create(new HuobiOptions());
Long serverTime = genericService.getTimestamp();
```

#### Symbol and currencies

```java
GenericClient genericService = GenericClient.create(new HuobiOptions());
List<Symbol> symbolList = genericService.getSymbols();
List<String> currencyList = genericService.getCurrencys();
```

### Market data

#### Candlestick

```java
MarketClient marketClient = MarketClient.create(new HuobiOptions());
List<Candlestick> list = marketClient.getCandlestick(CandlestickRequest.builder()
    .symbol(symbol)
    .interval(CandlestickIntervalEnum.MIN15)
    .size(10)
    .build());
```

#### Depth

```java
MarketClient marketClient = MarketClient.create(new HuobiOptions());
MarketDepth marketDepth = marketClient.getMarketDepth(MarketDepthRequest.builder()
    .symbol(symbol)
    .depth(DepthSizeEnum.SIZE_5)
    .step(DepthStepEnum.STEP0)
    .build());
```

#### Latest trade

```java
MarketClient marketClient = MarketClient.create(new HuobiOptions());
List<MarketTrade> marketTradeList = marketClient.getMarketTrade(MarketTradeRequest.builder().symbol(symbol).build());
```

#### Historical

```java
MarketClient marketClient = MarketClient.create(new HuobiOptions());
List<MarketTrade> marketHistoryTradeList = marketClient.getMarketHistoryTrade(MarketHistoryTradeRequest.builder().symbol(symbol).build());
```

### Account

*Authentication is required.*

#### Get account balance

```java
AccountClient accountService = AccountClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
AccountBalance accountBalance = accountService.getAccountBalance(AccountBalanceRequest.builder()
    .accountId(accountId)
    .build());
```

### Wallet

*Authentication is required.*

#### Withdraw

```java
HuobiWalletService walletService = new HuobiWalletService(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
long withdrawId = walletService.createWithdraw(CreateWithdrawRequest.builder()
    .address(withdrawAddress)
    .addrTag(withdrawAddressTag)
    .currency("eos")
    .amount(new BigDecimal("1"))
    .fee(new BigDecimal("0.1"))
    .build());
```

#### Cancel withdraw

```java
HuobiWalletService walletService = new HuobiWalletService(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
long res = walletService.cancelWithdraw(withdrawId);
```

#### Withdraw and deposit history

```java
List<DepositWithdraw> depositWithdrawList = walletService.getDepositWithdraw(DepositWithdrawRequest.builder()
    .type(DepositWithdrawTypeEnum.WITHDRAW)
    .build());
```

### Trading

*Authentication is required.*

#### Create order

```java
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
CreateOrderRequest buyLimitRequest = CreateOrderRequest.spotBuyLimit(spotAccountId, clientOrderId, symbol, bidPrice, new BigDecimal("2"));
Long buyLimitId = tradeService.createOrder(buyLimitRequest);
```

#### Cancel order

```java
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
int cancelResult = tradeService.cancelOrder(clientOrderId);
```

#### Cancel open orders

```java
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
BatchCancelOpenOrdersResult result = tradeService.batchCancelOpenOrders(BatchCancelOpenOrdersRequest.builder()
    .accountId(spotAccountId)
    .symbol(symbol)
    .build());
```

#### Get order info

```java
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
Order getOrder = tradeService.getOrder(51210074624L);
```

#### Historical orders

```java
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
List<Order> historyOrderList = tradeService.getOrdersHistory(OrderHistoryRequest.builder()
    .symbol(symbol)
    .startTime(1565107200000L)
    .direction(QueryDirectionEnum.PREV)
    .build());
```

### Margin Loan

*Authentication is required.*

These are examples for cross margin

#### Apply loan

```java
CrossMarginClient marginService = CrossMarginClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
Long applyId = marginService.applyLoan(CrossMarginApplyLoanRequest.builder()
    .currency("usdt")
    .amount(new BigDecimal("100"))
    .build());
```

#### Repay loan

```java
CrossMarginClient marginService = CrossMarginClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
marginService.repayLoan(CrossMarginRepayLoanRequest.builder()
    .orderId(applyId)
    .amount(loanAmount)
    .build());
```

#### Loan history

```java
CrossMarginClient marginService = CrossMarginClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
List<Balance> balanceList = crossMarginAccount1.getBalanceList()
```

## Subscription example

### Subscribe trade update

```java
MarketClient marketClient = MarketClient.create(new HuobiOptions());
marketClient.subMarketTrade(SubMarketTradeRequest.builder().symbol(symbol).build(), (marketTradeEvent) -> {
    System.out.println("ch:" + marketTradeEvent.getCh());
    System.out.println("ts:" + marketTradeEvent.getTs());

    marketTradeEvent.getList().forEach(marketTrade -> {
      System.out.println(marketTrade.toString());
    });
});
```

###Subscribe candlestick update

```java
MarketClient marketClient = MarketClient.create(new HuobiOptions());
marketClient.subCandlestick(SubCandlestickRequest.builder()
    .symbol(symbol)
    .interval(CandlestickIntervalEnum.MIN15)
    .build(), (candlestick) -> {

  System.out.println(candlestick.toString());
});
```

### Subscribe order update

*Authentication is required.*

```java
TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
    .apiKey(Constants.API_KEY)
    .secretKey(Constants.SECRET_KEY)
    .build());
tradeService.subOrderUpdateV2(SubOrderUpdateV2Request.builder().symbols("*").build(), orderUpdateV2Event -> {
    System.out.println(orderUpdateV2Event.toString());
});
```

### Subscribe account change

*Authentication is required.*

```java
AccountBalance accountBalance = accountService.getAccountBalance(AccountBalanceRequest.builder()
    .accountId(accountId)
    .build());
accountService.subAccountsUpdate(SubAccountUpdateRequest.builder()
    .accountUpdateMode(AccountUpdateModeEnum.ACCOUNT_CHANGE).build(), event -> {
  System.out.println(event.toString());
});
```
