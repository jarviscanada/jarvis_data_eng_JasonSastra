package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.service.QuoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuoteServiceIntTest {
  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setup() {
    quoteDao.deleteAll();
  }

  @Test
  public void testFindQuoteByTicker() throws Exception {
    Quote quote = quoteService.findQuoteByTicker("AAPL");
    assertEquals("AAPL", quote.getTicker());
  }

  @Test
  public void testUpdateMarketData() throws Exception {
    Quote quote = new Quote();
    quote.setTicker("AAPL");
    quoteService.saveQuote(quote);
    quoteService.updateMarketData();
    Optional<Quote> updatedQuote = quoteDao.findById(quote.getTicker());
    assertTrue(updatedQuote.isPresent());

    Quote result = updatedQuote.get();
    assertNotNull(result.getLastPrice());
  }


  @Test
  public void testSaveQuote() {
    Quote quote = new Quote();
    quote.setTicker("AAPL");
    quoteService.saveQuote(quote);
    Optional<Quote> updatedQuote = quoteDao.findById(quote.getTicker());
    assertTrue(updatedQuote.isPresent());
  }

  @Test
  public void testInputQuoteByTicker() throws Exception {
    quoteService.inputQuoteByTicker("AAPL");
    Optional<Quote> updatedQuote = quoteDao.findById("AAPL");
    assertTrue(updatedQuote.isPresent());
    Quote result = updatedQuote.get();
    assertNotNull(result.getLastPrice());
  }
}
