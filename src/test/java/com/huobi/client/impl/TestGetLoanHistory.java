package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.Account;
import com.huobi.client.model.Loan;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.LoanOrderStates;
import com.huobi.client.model.enums.QueryDirection;
import com.huobi.client.model.request.LoanOrderRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})
public class TestGetLoanHistory {

  private RestApiRequestImpl impl = null;
  Account testAccount1 = new Account();
  Account testAccount2 = new Account();
  User testUser = new User();

  private String data = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": [{\n"
      + "\t\t\t\"loan-balance\": \"0.100000000000000000\",\n"
      + "\t\t\t\"interest-balance\": \"0.000200000000000000\",\n"
      + "\t\t\t\"interest-rate\": \"0.002000000000000000\",\n"
      + "\t\t\t\"loan-amount\": \"0.110000000000000000\",\n"
      + "\t\t\t\"accrued-at\": 1511169724000,\n"
      + "\t\t\t\"interest-amount\": \"0.001200000000000000\",\n"
      + "\t\t\t\"symbol\": \"ethbtc\",\n"
      + "\t\t\t\"currency\": \"btc\",\n"
      + "\t\t\t\"id\": 390,\n"
      + "\t\t\t\"state\": \"accrual\",\n"
      + "\t\t\t\"account-id\": 123,\n"
      + "\t\t\t\"user-id\": 119910,\n"
      + "\t\t\t\"created-at\": 1511169724530\n"
      + "\t\t},\n"
      + "\t\t{\n"
      + "\t\t\t\"loan-balance\": \"1.100000000000000000\",\n"
      + "\t\t\t\"interest-balance\": \"1.000200000000000000\",\n"
      + "\t\t\t\"interest-rate\": \"1.002000000000000000\",\n"
      + "\t\t\t\"loan-amount\": \"1.110000000000000000\",\n"
      + "\t\t\t\"accrued-at\": 1511169724531,\n"
      + "\t\t\t\"interest-amount\": \"1.001200000000000000\",\n"
      + "\t\t\t\"symbol\": \"ethbtc\",\n"
      + "\t\t\t\"currency\": \"btc\",\n"
      + "\t\t\t\"id\": 391,\n"
      + "\t\t\t\"state\": \"accrual\",\n"
      + "\t\t\t\"account-id\": 456,\n"
      + "\t\t\t\"user-id\": 119911,\n"
      + "\t\t\t\"created-at\": 1511169724531\n"
      + "\t\t}\n"
      + "\t]\n"
      + "}";

  private String dataError = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": [{\n"
      + "\t\t\t\"loan-balance\": \"0.100000000000000000\",\n"
      + "\t\t\t\"interest-balance\": \"0.000200000000000000\",\n"
      + "\t\t\t\"interest-rate\": \"0.002000000000000000\",\n"
      + "\t\t\t\"loan-amount\": \"0.100000000000000000\",\n"
      + "\t\t\t\"accrued-at\": 1511169724000,\n"
      + "\t\t\t\"symbol\": \"ethbtc\",\n"
      + "\t\t\t\"currency\": \"btc\",\n"
      + "\t\t\t\"id\": 390,\n"
      + "\t\t\t\"state\": \"accrual\",\n"
      + "\t\t\t\"account-id\": 17740,\n"
      + "\t\t\t\"user-id\": 119910,\n"
      + "\t\t\t\"created-at\": 1511169724530\n"
      + "\t\t}\n"
      + "\t]\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    testAccount1.setType(AccountType.SPOT);
    testAccount1.setId(123L);
    testAccount2.setType(AccountType.MARGIN);
    testAccount2.setId(456L);
    List<Account> accountList = new LinkedList<>();
    accountList.add(testAccount1);
    accountList.add(testAccount2);
    testUser.setAccounts(accountList);
  }

  @Test
  public void testRequest() {
    LoanOrderRequest loanOrderRequest = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      loanOrderRequest = new LoanOrderRequest(
          "btcusdt",
          sdf.parse("2019-01-02"),
          sdf.parse("2019-02-03"),
          LoanOrderStates.CREATED,
          23456L,
          123L,
          QueryDirection.NEXT);
    } catch (Exception e) {
      fail("data format error");
    }
    RestApiRequest<List<Loan>> restApiRequest = impl.getLoan(loanOrderRequest);
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/v1/margin/loan-orders"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertEquals("2019-01-02", request.url().queryParameter("start-date"));
    assertEquals("2019-02-03", request.url().queryParameter("end-date"));
    assertEquals("created", request.url().queryParameter("states"));
    assertEquals("23456", request.url().queryParameter("from"));
    assertEquals("123", request.url().queryParameter("size"));
    assertEquals("next", request.url().queryParameter("direct"));
  }

  @Test
  public void testRequest_defaultParam() {
    LoanOrderRequest loanOrderRequest = new LoanOrderRequest("btcusdt");
    RestApiRequest<List<Loan>> restApiRequest = impl.getLoan(loanOrderRequest);
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/v1/margin/loan-orders"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertNull(request.url().queryParameter("start-date"));
    assertNull(request.url().queryParameter("end-date"));
    assertNull(request.url().queryParameter("states"));
    assertNull(request.url().queryParameter("from"));
    assertNull(request.url().queryParameter("size"));
    assertNull(request.url().queryParameter("direct"));
  }

  @Test
  public void testResult_normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);

    LoanOrderRequest loanOrderRequest = new LoanOrderRequest("btcusdt");
    RestApiRequest<List<Loan>> restApiRequest = impl.getLoan(loanOrderRequest);
    List<Loan> loan = restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(2, loan.size());
    assertEquals(390L, loan.get(0).getId());
    assertEquals(new BigDecimal("0.1"), loan.get(0).getLoanBalance());
    assertEquals(new BigDecimal("0.0002"), loan.get(0).getInterestBalance());
    assertEquals(new BigDecimal("0.002"), loan.get(0).getInterestRate());
    assertEquals(new BigDecimal("0.11"), loan.get(0).getLoanAmount());
    assertEquals(new BigDecimal("0.0012"), loan.get(0).getInterestAmount());
    assertEquals("ethbtc", loan.get(0).getSymbol());
    assertEquals("btc", loan.get(0).getCurrency());
    assertEquals(LoanOrderStates.ACCRUAL, loan.get(0).getState());
    assertEquals(AccountType.SPOT, loan.get(0).getAccountType());
    assertEquals(119910L, loan.get(0).getUserId());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1511169724000L),
        loan.get(0).getAccruedTimestamp());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1511169724530L),
        loan.get(0).getCreatedTimestamp());

    assertEquals(new BigDecimal("1.1"), loan.get(1).getLoanBalance());
    assertEquals(new BigDecimal("1.0002"), loan.get(1).getInterestBalance());
    assertEquals(new BigDecimal("1.002"), loan.get(1).getInterestRate());
    assertEquals(new BigDecimal("1.11"), loan.get(1).getLoanAmount());
    assertEquals(new BigDecimal("1.0012"), loan.get(1).getInterestAmount());
    assertEquals("ethbtc", loan.get(1).getSymbol());
    assertEquals("btc", loan.get(1).getCurrency());
    assertEquals(LoanOrderStates.ACCRUAL, loan.get(1).getState());
    assertEquals(AccountType.MARGIN, loan.get(1).getAccountType());
    assertEquals(119911L, loan.get(1).getUserId());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1511169724531L),
        loan.get(1).getAccruedTimestamp());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1511169724531L),
        loan.get(1).getCreatedTimestamp());
  }

  @Test
  public void testResult_error() {
    LoanOrderRequest loanOrderRequest = new LoanOrderRequest("btcusdt");
    RestApiRequest<List<Loan>> restApiRequest = impl.getLoan(loanOrderRequest);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(dataError));
  }

  @Test
  public void testInvalidSymbol() {
    LoanOrderRequest loanOrderRequest = new LoanOrderRequest("?");
    thrown.expect(HuobiApiException.class);
    impl.getLoan(loanOrderRequest);
  }
}
