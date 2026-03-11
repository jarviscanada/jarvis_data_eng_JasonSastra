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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceMockTest {
  @Mock
  AccountJpaRepository accountDao;

  @Mock
  QuoteDao quoteDao;

  @Mock
  PositionDao positionDao;

  @Mock
  SecurityOrderDao securityOrderDao;

  @InjectMocks
  OrderService orderService;

  @Test
  public void executeMarketOrderBuyTest() {

    Trader mockTrader = new Trader();
    mockTrader.setId(1);

    Account mockAccount = new Account();
    mockAccount.setAmount(10000.0);
    mockAccount.setTrader(mockTrader);


    Optional<Quote> mockQuote = Optional.of(new Quote());
    mockQuote.get().setAskPrice(500.0);
    mockQuote.get().setAskSize(10);
    mockQuote.get().setTicker("AAPL");

    MarketOrder orderData = new MarketOrder();
    orderData.setOption(MarketOrder.Option.BUY);
    orderData.setSize(5);
    orderData.setTicker("AAPL");
    orderData.setTraderId(mockTrader.getId());

    when(accountDao.getAccountByTraderId(1)).thenReturn(mockAccount);
    when(quoteDao.findById("AAPL")).thenReturn(mockQuote);

    Mockito.doReturn(mockAccount).when(accountDao).save(Mockito.any());
    Mockito.doReturn(Mockito.mock(SecurityOrder.class)).when(securityOrderDao).save(Mockito.any());

    SecurityOrder securityOrder = orderService.executeMarketOrder(orderData);
    Assertions.assertEquals(Integer.valueOf(5), securityOrder.getSize());
    Assertions.assertEquals("AAPL", securityOrder.getTicker());
    Assertions.assertEquals(Double.valueOf(7500.0), mockAccount.getAmount());
    Assertions.assertEquals(mockAccount, securityOrder.getAccount());
  }

  @Test
  public void ExecuteMarketOrderSellTest() {

    Trader mockTrader = new Trader();
    mockTrader.setId(1);

    Account mockAccount = new Account();
    mockAccount.setAmount(10000.0);
    mockAccount.setTrader(mockTrader);


    Optional<Quote> mockQuote = Optional.of(new Quote());
    mockQuote.get().setBidPrice(500.0);
    mockQuote.get().setBidSize(10);
    mockQuote.get().setTicker("AAPL");

    MarketOrder orderData = new MarketOrder();
    orderData.setOption(MarketOrder.Option.SELL);
    orderData.setSize(5);
    orderData.setTicker("AAPL");
    orderData.setTraderId(mockTrader.getId());

    Optional<Position> mockPosition = Optional.of(new Position());
    mockPosition.get().setPosition(10);

    when(accountDao.getAccountByTraderId(1)).thenReturn(mockAccount);
    when(quoteDao.findById("AAPL")).thenReturn(mockQuote);
    when(positionDao.findById(Mockito.any(PositionId.class))).thenReturn(mockPosition);

    Mockito.doReturn(mockAccount).when(accountDao).save(Mockito.any());
    Mockito.doReturn(Mockito.mock(SecurityOrder.class)).when(securityOrderDao).save(Mockito.any());


    SecurityOrder securityOrder = orderService.executeMarketOrder(orderData);
    Assertions.assertEquals(Integer.valueOf(-5), securityOrder.getSize());
    Assertions.assertEquals("AAPL", securityOrder.getTicker());
    Assertions.assertEquals(Double.valueOf(12500.0), mockAccount.getAmount());
    Assertions.assertEquals(mockAccount, securityOrder.getAccount());
  }
}
