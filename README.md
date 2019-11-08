# Huobi Java SDK (beta version)

This is Huobi Java SDK, This is a lightweight Java library, you can import to your Java project and use this SDK to query all market data, trading and manage your account.

The SDK supports both synchronous and asynchronous RESTful API invoking, and subscribe the market data from the Websocket connection.



## Huobi Java SDK (change log)
[Java SDK Change Log](https://github.com/HuobiRDCenter/huobi_Java/blob/master/CHANGE_LOG.md)


## Huobi Java SDK Download

- [Huobi Global API Java SDK version 1.0.6](https://github.com/HuobiRDCenter/huobi_Java/releases/tag/1.0.6)

- [Huobi Global API Java SDK version 1.0.5](https://github.com/HuobiRDCenter/huobi_Java/releases/tag/1.0.5)

- [Huobi Global API Java SDK version 1.0.4](https://github.com/HuobiRDCenter/huobi_Java/releases/tag/1.0.4)

- [Huobi Global API Java SDK version 1.0.3](https://github.com/HuobiRDCenter/huobi_Java/releases/tag/1.0.3)

- [Huobi Global API Java SDK version 1.0.2](https://github.com/HuobiRDCenter/huobi_Java/releases/tag/1.0.2)

- [Huobi Global API Java SDK version 1.0.1](https://github.com/HuobiRDCenter/huobi_Java/releases/tag/1.0.1)


## Table of Contents

- [Beginning](#Beginning)
  - [Installation](#Installation)
  - [Quick Start](#Quick-Start)
  - [Request vs. Subscription](#request-vs.-subscription)
  - [Clients](#Clients)
  - [Create client](#create-client)
  - [Custom host](#custom-host)
- [Usage](#Usage)
  - [Synchronous](#Synchronous)
  - [Asynchronous](#Asynchronous)
  - [Subscription](#Subscription)
  - [Errir handling](error-handling)

- [Request example](#Request-example)

  - [Reference data](#Reference-data)
    - [Exchange timestamp](#Exchange-timestamp)
    - [Symbol and currencies](#symbol-and-currencies)
  - [Market data](#Market-data)
    - [Candlestick/KLine](#Candlestick/KLine)
    - [Depth](#Depth)
    - [Latest trade](#latest-trade)
    - [Best bid/ask](#best-bid/ask)
    - [Historical](#historical)
    - [24H Statistics](#24h-statistics)
  - [Account](#account)
  - [Wallet](#wallet)
    - [Withdraw](@Withdraw)
    - [Cancel withdraw](@cancel-withdraw)
    - [Withdraw and deposit history](#withdraw-and-deposit-history)
  - [Trading](#trading)
    - [Create order](#create-order)
    - [Cancel order](#cancel-order)
    - [Cancel open orders](#cancel-open-orders)
    - [Get order info](#get-order-info)
    - [Historical orders](#historical_orders)
  - [Margin Loan](#margin-loan)
    - [Apply loan](#apply-loan)
    - [Reply loan](#reply-loan)
    - [Loan history](#loan-history)
- [Subscription example](#Subscription-example)
  - [Subscribe trade update](#Subscribe-trade-update)
  - [Subscribe candlestick/KLine update](#Subscribe-candlestick/KLine-update)
  - [Subscribe order update](#subscribe-order-update)
  - [Subscribe account change](#subscribe-account-change)
  - [Unsubscribe](#unsubscribe)

  

## Beginning

### Installation

*The SDK is compiled by Java8*

For Beta version, please import the source code in java IDE (idea or eclipse)

The example code is in huobi-api-sdk/java/src/test/java/com/huobi/client/examples.

### Quick Start

In your Java project, you can follow below steps:

* Create the client instance.
* Call the interfaces provided by client.

```java
SyncRequestClient syncClient = SyncRequestClient.create();

// Get the timestamp from Huobi server and print on console
long timestamp = syncClient.getExchangeTimestamp();
System.out.println(timestamp);

// Get the latest btcusdt‘s candlestick data and print the highest price on console
List<Candlestick> candlestickList =
    syncClient.getLatestCandlestick("btcusdt", CandlestickInterval.DAY1, 20);
for (Candlestick item : candlestickList) {
    System.out.println(item.getHigh());
}
```

Please NOTE:

All timestamp which is got from SDK is the Unix timestamp based on UTC.



### Request vs. Subscription

Huobi API supports 2 types of invoking.

1. Request method: You can use request method to trade, withdraw and loan. You can also use it to get the market related data from Huobi server.
2. Subscription method: You can subscribe the market updated data and account change from Huobi server. For example, if you subscribed the price depth update, you will receive the price depth message when the price depth updates on Huobi server.

We recommend developers to use request method to trade, withdraw and loan, to use subscription method to access the market related data.



### Clients

There are 2 clients for request method, ```SyncRequestClient``` and ```AsyncRequestClient```. One client for subscription method,  ```SubscriptionClient```.

* **SyncRequestClient**: It is a synchronous request, it will invoke the Huobi API via synchronous method, all invoking will be blocked until receiving the response from server.

* **AsyncRequestClient**: It is an asynchronous request, it will invoke the Huobi API via asynchronous method, all invoking will return immediately, instead of waiting the server's response. So you must implement the ```onResponse()``` method in```RequestCallback``` interface. As long as receiving the response of the server, callback method you defined will be called. You can use the lambda expression to simplify the implementation, see [Asynchronous usage](#Asynchronous) for detail. 

* **SubscriptionClient**: It is the subscription, it is used for subscribing any market data update and account change.  It is asynchronous, so you must implement ```onUpdate()``` method in  ```SubscriptionListener``` interface. The server will push any update for the client. if client receive the update, the ```onUpdate()``` method will be called. You can use the lambda expression to simplify the implementation, see [Subscription usage](#Subscription) for detail. 

  

### Create client

You can assign the API key and Secret key when you create the client. See below:

```java
SyncRequestClient syncClient = SyncRequestClient.create(
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx");
```

```java
AsyncRequestClient asyncClient = AsyncRequestClient.create(
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx");
```

```java
SubscriptionClient subscriptionClient = SubscriptionClient.create(
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx");
```

The API key and Secret key are used for authentication.

Some APIs related with account, trading, deposit and withdraw etc require the authentication. We can name them after private interface.

The APIs only return the market data that don't need the authentication. We can name them after public interface.

If you want to invoke both public interface and private interface. You must apply API Key and Secret Key from Huobi and put them into the client you created.

If the authentication cannot pass, the invoking of private interface will fail.

If you want to invoke public interface only. You can create the client as follow:

```java
SyncRequestClient syncClient = SyncRequestClient.create();
```

```java
AsyncRequestClient asyncClient = AsyncRequestClient.create();
```

```java
SubscriptionClient subscriptionClient = SubscriptionClient.create();
```



### Custom host

To support huobi cloud, you can specify the custom host.

1. Set your custom host to ```RequestOptions``` or ```SubscriptionOptions```.
2. Set the options to client when creating the client instance.

See below example

```java
// Set custom host for request
RequestOptions options = new RequestOptions();
options.setUrl("https://www.xxx.yyy/");

SyncRequestClient syncClient = SyncRequestClient.create(
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  options);
AsyncRequestClient asyncClient = AsyncRequestClient.create(
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  options);

// Set custom host for subscription
SubscriptionOptions options = new SubscriptionOptions();
options.setUri("wss://www.xxx.yyy");

SubscriptionClient subscriptionClient = SubscriptionClient.create(
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  "xxxxxxxx-xxxxxxxx-xxxxxxxx-xxxxx",
  options);
```

If you do not set yout custom host, below default host will be used:

For request: https://api.huobi.pro

For subscription: wss://api.huobi.pro



## Usage

### Synchronous

To invoke the interface by synchronous, you can create the ```SyncRequestClient``` by calling ```SyncRequestClient.create()```, and call the API directly.

```java
SyncRequestClient syncClient = SyncRequestClient.create();
// Get the best bid and ask for btcusdt, print the best ask price and amount on console.
BestQuote bestQuote = client.getBestQuote("btcusdt");
System.out.println(bestQuote.getAskPrice());
System.out.println(bestQuote.getAskAmount());
```



### Asynchronous

To invoke the interface by asynchronous, you can create the ```AsyncRequestClient``` by calling ```AsyncRequestClient.create()```, and call the API which  implement the callback by yourself. You will get a resultset after the asynchronous invoking completed, call ```succeeded()``` to check whether the invoking succeeded or not, then call ```getData()``` to get the server's response data.

```java
AsyncRequestClient asyncClient = AsyncRequestClient.create();
// Get the price depth for btcusdt, print bid price and ask price in first level.
asyncClient.getPriceDepth("btcusdt", 5, (priceDepthResult) -> {
    if (priceDepthResult.succeeded() {
      System.out.println(priceDepthResult.getData().getBids().get(0).getPrice());
      System.out.println(priceDepthResult.getData().getAsks().get(0).getPrice());
    }
});
```



### Subscription

To receive the subscribed data, you can create the ```SubscriptionClient``` by calling ```SubscriptionClient.create()```. When subscribing the event, you should define your listener. See below example:

```java
SubscriptionClient subscriptionClient = HuobiClientFactory.createSubscriptionClient();
// Subscribe the trade update for btcusdt.
subscriptionClient.subscribeTradeEvent("btcusdt", (tradeEvent) -> {
  System.out.println(event.getSymbol());
  for (Trade trade : tradeEvent.getTradeData()) {
    System.out.println(trade.getPrice());
  }
});
```

The subscription method supports multi-symbol string. Each symbol should be separated by a comma.

```java
subscriptionClient.subscribeTradeEvent("btcusdt,ethusdt", (tradeEvent) -> {
  ......
});
```



### Error handling

#### For synchronous

In error case, such as you set the invalid symbol to ```getBestQuote()```. The ```HuobiApiException``` will be thrown. See below example:

```java
try {
  SyncRequestClient syncClient = service.createSyncRequestClient();
  // Set the invaild symbol
  BestQuote bestQuote = client.getBestQuote("abcdefg");
  System.out.println(bestQuote.getAskPrice());
  System.out.println(bestQuote.getAskAmount());
} catch (HuobiApiException e) {
  System.err.println(e.getErrorCode());
  System.err.println(e.getMessage());
}
```

#### For asynchronous

If the invoking failed, you can get the ```HuobiApiException``` in the resultset. See below example:

```java
asyncClient.getPriceDepth("btcusdt", 5, (priceDepthResult) -> {
  if (priceDepthResult.succeeded() {
    System.out.println(priceDepthResult.getData().getBids().get(0).getPrice());
    System.out.println(priceDepthResult.getData().getAsks().get(0).getPrice());
  } else {
    System.err.println(e.getErrorCode());
    System.err.println(e.getMessage());
  }  
});
```

#### For Subscription

If you want to check the error, you should implement your ```SubscriptionErrorHandler```. See below example:

```java
subscriptionClient.subscribeTradeEvent("btcusdt",
    (tradeEvent) -> {
      System.out.println(event.getSymbol());
      for (Trade trade : tradeEvent.getTradeData()) {
        System.out.println(trade.getPrice());
      }
    },
    (e) -> {
      System.err.println(e.getErrorCode());
      System.err.println(e.getMessage());
    }
});
```

Any error made during subscription will be output to a log file, If you do not define your ```SubscriptionErrorHandler```, the error will be output to log file only.



## Request example

### Reference data

#### Exchange timestamp

```java
//Synchronous
long timestamp = syncClient.getExchangeTimestamp();
System.out.println(timestamp);
```

```java
//Asynchronous
asyncClient.getExchangeTimestamp((timestampResult) -> {
  if (timestampResult.succeeded()) {
    System.out.println(timestampResult.getData())
  }  
});
```

#### Symbol and currencies

```java
//Synchronous
ExchangeInfo info = syncClient.getExchangeInfo();
for (String currency : info.getCurrencies()) {
  System.out.println(currency);
}
```

```java
//Asynchronous
asyncClient.getExchangeInfo((result) -> {
  if (result.succeeded()) {
    for (String currency : result.getData().getCurrencies()) {
      System.out.println(currency);
    }   
  }
});
```

### Market data

#### Candlestick/KLine

```java
//Synchronous
List<Candlestick> candlestickList = syncClient.getLatestCandlestick(
  "btcusdt", CandlestickInterval.DAY1, 20);
for (Candlestick candlestick : candlestickList) {
  System.out.println(candlestick.getHigh());
}
```

```java
//Asynchronous
asyncClient.getCandlestick("btcusdt", CandlestickInterval.DAY1, 20, (candlestickResult) -> 
{
  if (candlestickResult.succeeded()) {
    for (Candlestick candlestick : candlestickResult.getData()) {
      System.out.println(candlestick.getHigh());
    }
  }
});
```

#### Depth

```java
//Synchronous
PriceDepth depth = syncClient.getPriceDepth("btcusdt", 5);
for (DepthEntry entry : depth.getBids()) {
    System.out.println(entry.getPrice());
}
```

```java
//Asynchronous
asyncClient.getPriceDepth("btcusdt", 5, (depthResult) -> {
    for (DepthEntry entry : depthResult.getData().getBids()) {
        System.out.println(entry.getPrice());
    }
}
```

#### Latest trade

```java
//Synchronous
Trade trade = syncClient.getLastTrade("btcusdt");
System.out.println(trade.getPrice());
```

```java
//Asynchronous
asyncClient.getLastTrade("btcusdt", (tradeResult) -> {
  if (tradeResult.succeeded()) {
    System.out.println(tradeResult.getData().getPrice());
  }
});
```

#### Best bid/ask

```java
//Synchronous
BestQuote bestQuote = syncClient.getBestQuote("btcusdt");
System.out.println(bestQuote.getBidPrice());
System.out.println(bestQuote.getAskPrice());
```

```java
//Asynchronous
asyncClient.getBestQuote("btcusdt", (bestQuoteResult) -> {
  if (bestQuoteResult.succeeded()) {
    System.out.println(bestQuoteResult.getData().getBidPrice());
    System.out.println(bestQuoteResult.getData().getAskPrice());
  }
});
```

#### Historical

```java
//Synchronous
List<Trade> tradeList = syncClient.getHistoricalTrade("btcusdt", 5);
System.out.println(tradeList.get(0).getPrice());
```

```java
//Asynchronous
asyncClient.getHistoricalTrade("btcusdt", 5, (tradeResult) -> {
      if (tradeResult.succeeded()) {
        for (Trade trade : tradeResult.getData()) {
          System.out.println(trade.getPrice());
        }
      }
});
```

#### 24H statistics

```java
//Synchronous
TradeStatistics statistics = syncClient.get24HTradeStatistics("btcusdt");
System.out.println(statistics.getOpen());
```

```java
//Asynchronous
asyncClient.get24HTradeStatistics("btcusdt", (statisticsResult) -> {
  if (statisticsResult.succeeded()) {
    System.out.println(statisticsResult.getData().getHigh());
  }
});
```

### Account

*Authentication is required.*

```java
//Synchronous
Account balance = syncClient.getAccountBalance(AccountType.SPOT);
System.out.println(balance.getBalance("btc").get(0).getBalance());
```

```java
//Asynchronous
asyncClient.getAccountBalance(AccountType.SPOT, (accountResult) -> {
  if (accountResult.succeeded()) {
    System.out.println(accountResult.getData().getBalance("btc").get(0).getBalance());
  }
});
```

### Wallet

#### Withdraw

*Authentication is required.*

```java
//Synchronous
WithdrawRequest request = new WithdrawRequest("xxxxxxx", new BigDecimal(0.1), "btc");
long id = syncClient.withdraw(request);
System.out.println(id);
```

```java
//Asynchronous
WithdrawRequest request = new WithdrawRequest("xxxxxxx", new BigDecimal(0.1), "btc");
asyncClient.withdraw(request, (withdrawResult) -> {
  if (withdrawResult.succeeded())
    System.out.println(withdrawResult.getData());
});
```

#### Cancel withdraw

*Authentication is required.*

```java
//Synchronous
syncClient.cancelWithdraw("btc", id);
```

```java
//Asynchronous
asyncClient.cancelWithdraw("btc", id, (withdrawResult) -> {
  if (withdrawResult.succeeded()) {}
});
```

#### Withdraw and deposit history

*Authentication is required.*

```java
//Synchronous
List<Withdraw> withdrawList = syncClient.getWithdrawHistory("btc", id, 10);
System.out.println(withdrawList.get(0).getAmount());
List<Deposit> depositList = syncClient.getDepositHistory("btc", id, 10);
System.out.println(depositList.get(0).getAmount());
```

```java
//Asynchronous
asyncClient.getWithdrawHistory("btc", id, 10, (withdrawResult) -> {
  if (withdrawResult.succeeded()) {
    System.out.println(withdrawResult.getData().get(0).getAmount());
  }
});
asyncClient.getDepositHistory("btc", id, 10, (depositResult) -> {
  if (depositResult.succeeded()) {
    System.out.println(depositResult.getData().get(0).getAmount());
  }
});
```

### Trading

#### Create order

*Authentication is required.*

```java
//Synchronous
NewOrderRequest request = new NewOrderRequest(
    "btcusdt", AccountType.SPOT, OrderType.SELL_MARKET, new BigDecimal(0.1), null);
long id = syncClient.createOrder(request);
System.out.println(id);
```

```java
//Asynchronous
NewOrderRequest request = new NewOrderRequest(
    "btcusdt", AccountType.SPOT, OrderType.SELL_MARKET, new BigDecimal(0.1), null);
asyncClient.createOrder(request, (orderResult) -> {
  if (orderResult.succeeded()) {
    System.out.println(orderResult.getData());
  }
});
```

#### Cancel order

*Authentication is required.*

```java
//Synchronous
syncClient.cancelOrder("btcusdt", id);
```

```java
//Asynchronous
asyncClient.cancelOrder("btcusdt", id, (cancelResult) -> {
  if (cancelResult.succeeded()) {}
});
```

#### Cancel open orders

*Authentication is required.*

```java
//Synchronous
CancelOpenOrderRequest request = new CancelOpenOrderRequest("btcusdt", AccountType.SPOT);
BatchCancelResult result = syncClient.cancelOpenOrders(request);
System.out.println(result.getSuccessCount());
```

```java
//Asynchronous
CancelOpenOrderRequest request = new CancelOpenOrderRequest("btcusdt", AccountType.SPOT);
asyncClient.cancelOpenOrders("btcusdt", (cancelResult) -> {
  if (cancelResult.succeeded()) {
    System.out.println(cancelResult.getData().getSuccessCount());
  }
});
```

#### Get order info

*Authentication is required.*

```java
//Synchronous
Order order = syncClient.getOrder(id);
System.out.println(order.getPrice());
```

```java
//Asynchronous
asyncClient.getOrder("btcusdt", id, (orderResult) -> {
  if (orderResult.succeeded()) {
    System.out.println(orderResult.getData().getPrice());
  }
});
```

#### Historical orders

*Authentication is required.*

```java
//Synchronous
HistoricalOrdersRequest request =
    new HistoricalOrdersRequest("btcusdt", OrderState.SUBMITTED);
List<Order> orderList = syncClient.getHistoricalOrders(request);
System.out.println(orderList.get(0).getPrice());
```

```java
//Asynchronous
HistoricalOrdersRequest request =
    new HistoricalOrdersRequest("btcusdt", OrderState.SUBMITTED);
asyncClient.getHistoricalOrders(request, (orderResult) ->{
  if (orderResult.succeeded()) {
    System.out.println(orderResult.getData().get(0).getPrice());
  }
});
```

### Margin Loan

#### Apply loan

*Authentication is required.*

```java
//Synchronous
long id = syncClient.applyLoan("btcusdt", "btc", new BigDecimal(10.0));
System.out.println("id");
```

```java
//Asynchronous
asyncClient.applyLoan("btcusdt", "btc", new BigDecimal(10.0), (loanResult) -> {
  if (loanResult.succeeded()) {
    System.out.println(loanResult.getData());
  }
});
```

#### Repay loan

*Authentication is required.*

```java
//Synchronous
long id = syncClient.repayLoan(id, new BigDecimal(10.0));
System.out.println("id");
```

```java
//Asynchronous
asyncClient.applyLoan("btcusdt", "btc", new BigDecimal(10.0), (loanResult) -> {
  if (loanResult.succeeded()) {
    System.out.println(loanResult.getData());
  }
});
```

#### Loan history

*Authentication is required.*

```java
//Synchronous
LoanOrderRequest request = new LoanOrderRequest("btcusdt");
List<Loan> loanList = syncClient.getLoanHistory(request);
System.out.println(loanList.get(0).getLoanAmount());
```

```java
//Asynchronous
LoanOrderRequest request = new LoanOrderRequest("btcusdt");
asyncClient.getLoanHistory("btcusdt", (loanResult) -> {
  if (loanResult.succeeded()) { 
    System.out.println(loanResult.getData().get(0).getLoanAmount());
  }
});
```



## Subscription example

### Subscribe trade update

```java
// Subscribe the trade update for btcusdt.
subscriptionClient.subscribeTradeEvent("btcusdt", (tradeEvent) -> {
    System.out.println(event.getSymbol());
    for (Trade trade : tradeEvent.getTradeData()) {
      System.out.println(trade.getPrice());
    }
});
```

###Subscribe candlestick/KLine update

```java
// Subscribe candlestick update for btcusdt.
subscriptionClient.subscribeCandlestickEvent("btcusdt", (candlestickEvent) -> {
    System.out.println(candlestickEvent.getSymbol());
});
```

### Subscribe order update

*Authentication is required.*

```java
// Subscribe order update
subscriptionClient.subscribeOrderUpdateEvent("btcusdt", (orderEvent) -> {
    System.out.println(orderEvent.getData().getPrice());
});
```

### Subscribe account change

*Authentication is required.*

```java
// Subscribe account change.
subscriptionClient.subscribeAccountEvent(BalanceMode.AVAILABLE, (accountEvent) -> {
    for (AccountChange change : accountEvent.getData()) {
        System.out.println(change.getAccountType());
        System.out.println("Balance: " + change.getBalance());
    }
});
```

### Unsubscribe

You can cancel all subscription by calling ```unsubscribeAll()```.

```java
subscriptionClient.unsubscribeAll();
```



