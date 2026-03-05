package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.Model.*;
import ca.jrvs.apps.trading.dao.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceMockTest {

  @Mock
  AccountJpaRepository accountDao;

  @InjectMocks
  AccountService accountService;
  @Test
  public void deleteAccountByTraderIdMock() {
    Trader mockTrader = new Trader();
    mockTrader.setId(1);

    Account mockAccount = new Account();
    mockAccount.setAmount(10000.0);
    mockAccount.setTrader(mockTrader);
    mockAccount.setId(1);
    when(accountDao.getAccountByTraderId(mockTrader.getId())).thenReturn(mockAccount);
    assertThrows(IllegalArgumentException.class, () -> {
      accountService.deleteAccountByTraderId(1);
    });
    mockAccount.setAmount(0.0);
    accountService.deleteAccountByTraderId(1);
    Mockito.verify(accountDao).deleteById(mockAccount.getId());

  }

}
