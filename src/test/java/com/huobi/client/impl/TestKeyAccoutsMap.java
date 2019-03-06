package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class, RestApiInvoker.class, RestApiRequestImpl.class})
@PowerMockIgnore({"okhttp3.*"})

public class TestKeyAccoutsMap {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test() throws Exception {

    RestApiRequestImpl restApiRequest = PowerMockito.mock(RestApiRequestImpl.class);
    RestApiRequest<List<Account>> restApiJsonParser = new RestApiRequest<>();
    PowerMockito.when(restApiRequest, "getAccounts").thenReturn(restApiJsonParser);

    List<Account> accountList = new LinkedList<>();
    Account account = new Account();
    account.setId(12345L);
    accountList.add(account);
    Map<String, User> userMap = new HashMap<>();
    User user1 = new User();
    List<Account> accountList1 = new LinkedList<>();
    user1.setAccounts(accountList1);

    User user2 = new User();
    List<Account> accountList2 = new LinkedList<>();
    user2.setAccounts(accountList2);

    userMap.put("111", user1);
    userMap.put("222", user2);

    PowerMockito.field(AccountsInfoMap.class, "userMap").set(AccountsInfoMap.class, userMap);

    PowerMockito.mockStatic(RestApiInvoker.class);

    PowerMockito.when(RestApiInvoker.callSync(restApiJsonParser)).thenReturn(accountList);
    AccountsInfoMap.updateUserInfo("333", restApiRequest);
    assertEquals(accountList, AccountsInfoMap.getUser("333").getAccounts());
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Cannot found user by key");
    AccountsInfoMap.getUser("hhh").getAccounts();
    AccountsInfoMap.getUser(null).getAccounts();
    assertEquals(account, AccountsInfoMap.getAccount("333", 12345L));

    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Cannot find the account");
    AccountsInfoMap.getAccount("333", 1345L);
  }
}
