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
public class TraderServiceMockTest {

  @Mock
  TraderDao traderDao;

  @Mock
  AccountJpaRepository accountDao;

  @Mock
  SecurityOrderDao securityOrderDao;

  @InjectMocks
  TraderService traderService;

  @Test
  public void createTraderAndAccountMock() {

    assertThrows(IllegalArgumentException.class, () -> {
      traderService.createTraderAndAccount(null);
    });

    Trader trader = new Trader();

    assertThrows(IllegalArgumentException.class, () -> {
      traderService.createTraderAndAccount(trader);
    });
    trader.setFirst_name("Jason");

    assertThrows(IllegalArgumentException.class, () -> {
      traderService.createTraderAndAccount(trader);
    });

    trader.setLast_name("Sastra");

    assertThrows(IllegalArgumentException.class, () -> {
      traderService.createTraderAndAccount(trader);
    });

    trader.setEmail("jason@test.com");

    Account mockAccount = Mockito.mock(Account.class);

    when(traderDao.save(Mockito.any(Trader.class))).thenReturn(trader);
    when(accountDao.save(Mockito.any(Account.class))).thenReturn(mockAccount);

    TraderAccountView saved = traderService.createTraderAndAccount(trader);

    Assertions.assertNotNull(saved);
    Assertions.assertEquals(saved.getTrader(), trader);
    Assertions.assertEquals(saved.getAccount(), mockAccount);


  }

  @Test
  public void deleteTraderByIdMock(){
    Trader trader = new Trader();
    trader.setId(1);
    Account account = new Account(trader, 100.0);
    account.setId(1);

    when(traderDao.findById(account.getId())).thenReturn(null);
    assertThrows(IllegalArgumentException.class, () -> {
      traderService.deleteTraderById(trader.getId());
    });

    when(traderDao.findById(account.getId())).thenReturn(Optional.of(trader));
    when(accountDao.getAccountByTraderId(trader.getId())).thenReturn(account);
    assertThrows(IllegalArgumentException.class, () -> {
      traderService.deleteTraderById(trader.getId());
    });

    account.setAmount(0.0);
    List<SecurityOrder> orders = new ArrayList<>();
    orders.add(Mockito.mock(SecurityOrder.class));
    when(securityOrderDao.findByAccount_id(account.getId())).thenReturn(orders);

    traderService.deleteTraderById(trader.getId());

    Mockito.verify(securityOrderDao)
        .deleteAll(orders);

    Mockito.verify(accountDao)
        .delete(account);

    Mockito.verify(traderDao)
        .delete(trader);
  }

  @Test
  public void depositMock() {
    Trader trader = new Trader();
    trader.setId(1);
    Account account = new Account(trader, 100.0);
    account.setId(1);

    when(traderDao.findById(account.getId())).thenReturn(null);
    assertThrows(IllegalArgumentException.class, () -> {
      traderService.deposit(trader.getId(), 500.0);
    });

    when(traderDao.findById(trader.getId())).thenReturn(Optional.of(trader));
    when(accountDao.getAccountByTraderId(trader.getId())).thenReturn(account);

    Account saved = traderService.deposit(trader.getId(), 500.0);
    Assertions.assertEquals(Double.valueOf(600.0), saved.getAmount());
    Assertions.assertEquals(saved.getId(), account.getId());
    Mockito.verify(accountDao).save(saved);
  }

  @Test
  public void withdrawMock() {
    Trader trader = new Trader();
    trader.setId(1);
    Account account = new Account(trader, 100.0);
    account.setId(1);

    when(traderDao.findById(account.getId())).thenReturn(null);
    assertThrows(IllegalArgumentException.class, () -> {
      traderService.withdraw(trader.getId(), 500.0);
    });

    when(traderDao.findById(trader.getId())).thenReturn(Optional.of(trader));
    when(accountDao.getAccountByTraderId(trader.getId())).thenReturn(account);

    assertThrows(IllegalArgumentException.class, () -> {
      traderService.withdraw(trader.getId(), 500.0);
    });

    account.setAmount(1000.0);
    Account saved = traderService.withdraw(trader.getId(), 500.0);
    Assertions.assertEquals(Double.valueOf(500.0), saved.getAmount());
    Assertions.assertEquals(saved.getId(), account.getId());
    Mockito.verify(accountDao).save(saved);
  }


}
