package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.PortfolioView;
import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.service.QuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.HTTP;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = QuoteController.class)
@ContextConfiguration(classes = QuoteController.class)
public class QuoteControllerMockTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  DashboardService dashboardService;
  @MockBean
  QuoteService quoteService;
  @MockBean
  OrderService orderService;

  Quote testQuote;
  @Before
  public void init() {
    testQuote = new Quote();
    testQuote.setTicker("AAPL");
    testQuote.setLastPrice(1.0);
    testQuote.setAskPrice(3.0);
    testQuote.setBidPrice(2.0);
    testQuote.setAskSize(5);
    testQuote.setBidSize(6);

  }
  @Test
  public void getQuoteMock() throws Exception {

    when(quoteService.findQuoteByTicker("AAPL")).thenReturn(testQuote);
    mockMvc.perform(get("/quote/ticker/AAPL"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.symbol").value("AAPL"))
        .andExpect(jsonPath("$.lastPrice").value(1.0))
        .andExpect(jsonPath("$.ask").value(3.0))
        .andExpect(jsonPath("$.bid").value(2.0))
        .andExpect(jsonPath("$.asize").value(5))
        .andExpect(jsonPath("$.bsize").value(6));
  }

  @Test
  public void updateMarketDataMock() throws Exception {
    Mockito.doNothing().when(quoteService).updateMarketData();
    mockMvc.perform(put("/quote/FinnAPI")).andExpect(status().isOk());
  }

  @Test
  public void inputQuoteByTickerMock() throws Exception {
    when(quoteService.inputQuoteByTicker("AAPL")).thenReturn(testQuote);
    mockMvc.perform(post("/quote/ticker/AAPL"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.symbol").value("AAPL"))
        .andExpect(jsonPath("$.lastPrice").value(1.0))
        .andExpect(jsonPath("$.ask").value(3.0))
        .andExpect(jsonPath("$.bid").value(2.0))
        .andExpect(jsonPath("$.asize").value(5))
        .andExpect(jsonPath("$.bsize").value(6));
  }

  @Test
  public void putQuoteMock() throws Exception {
    when(quoteService.saveQuote(Mockito.any(Quote.class))).thenReturn(testQuote);
    ObjectMapper objectMapper = new ObjectMapper();
    mockMvc.perform(post("/quote/ticker/AAPL")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testQuote)))
        .andExpect(status().isOk());
  }

  @Test
  public void getDailyListMock() throws Exception {

    Quote testQuote2 = new Quote();
    testQuote2.setTicker("GOGL");
    testQuote2.setLastPrice(10.0);
    testQuote2.setAskPrice(30.0);
    testQuote2.setBidPrice(20.0);
    testQuote2.setAskSize(50);
    testQuote2.setBidSize(60);
    List<Quote> quotes = new ArrayList<>();
    quotes.add(testQuote);
    quotes.add(testQuote2)







    ;
    when(quoteService.getDailyList()).thenReturn(quotes);
    mockMvc.perform(get("/quote/dailyList"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].symbol").value("AAPL"))
        .andExpect(jsonPath("$[0].lastPrice").value(1.0))
        .andExpect(jsonPath("$[0].bid").value(2.0))
        .andExpect(jsonPath("$[0].bsize").value(6))
        .andExpect(jsonPath("$[0].ask").value(3.0))
        .andExpect(jsonPath("$[0].asize").value(5))
        .andExpect(jsonPath("$[1].symbol").value("GOGL"))
        .andExpect(jsonPath("$[1].lastPrice").value(10.0))
        .andExpect(jsonPath("$[1].bid").value(20.0))
        .andExpect(jsonPath("$[1].bsize").value(60))
        .andExpect(jsonPath("$[1].ask").value(30.0))
        .andExpect(jsonPath("$[1].asize").value(50));
  }
}
