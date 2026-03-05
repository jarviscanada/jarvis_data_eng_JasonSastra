package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceMockTest {
  @InjectMocks
  private QuoteService quoteService;

  @Mock
  private QuoteDao quoteDao;

  @Mock
  private MarketDataDao marketDataDao;

  @Mock
  private SecurityOrderDao securityOrderDao;


  @Test
  public void testFindQuoteByTicker() throws Exception {

    Quote testQuote = Mockito.mock(Quote.class);

    when(marketDataDao.findById(testQuote.getTicker())).thenReturn(testQuote);

    Quote quote = quoteService.findQuoteByTicker(testQuote.getTicker());

    assertEquals(testQuote.getTicker(), quote.getTicker());
  }

  @Test
  public void testUpdateMarketData() throws Exception {

    QuoteService spyService = Mockito.spy(quoteService);

    Quote q1 = new Quote();
    q1.setTicker("AAPL");


    List<Quote> quotes = Arrays.asList(q1);

    Mockito.when(quoteDao.findAll()).thenReturn(quotes);

    Quote newQuote1 = new Quote();
    newQuote1.setTicker("AAPL");


    Mockito.when(marketDataDao.findById(Mockito.any(String.class))).thenReturn(newQuote1);

    Mockito.doReturn(newQuote1).when(spyService).saveQuote(Mockito.any());

    spyService.updateMarketData();

    Mockito.verify(quoteDao).findAll();
    Mockito.verify(marketDataDao).findById("AAPL");

    Mockito.verify(spyService, Mockito.times(1)).saveQuote(Mockito.any());
  }


  @Test
  public void testSaveQuote() {
    Quote testQuote = Mockito.mock(Quote.class);

    when(quoteDao.save(testQuote)).thenReturn(testQuote);

    Quote savedQuote = quoteService.saveQuote(testQuote);

    Assertions.assertNotNull(savedQuote);
  }

  @Test
  public void testInputQuoteByTicker() throws Exception {

    QuoteService spyService = Mockito.spy(quoteService);

    List<Quote> quotes = new ArrayList<>();
    Mockito.when(quoteDao.findAll()).thenReturn(quotes);

    Quote mockQuote = new Quote();
    mockQuote.setTicker("AAPL");

    Mockito.doReturn(mockQuote)
        .when(spyService)
        .findQuoteByTicker("AAPL");

    Quote result = spyService.inputQuoteByTicker("AAPL");

    Assertions.assertEquals(mockQuote.getTicker(), result.getTicker());

    Mockito.verify(spyService).findQuoteByTicker("AAPL");
  }
}
