package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.service.TraderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TraderAccountController.class)
@ContextConfiguration(classes = TraderAccountController.class)
public class TraderAccountControllerMock {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  DashboardService dashboardService;
  @MockBean
  QuoteService quoteService;
  @MockBean
  OrderService orderService;
  @MockBean
  TraderService traderService;

  Account testAccount;

  Trader testTrader;

  TraderAccountView mockTraderAccountView;

  @Before
  public void init() {
    testTrader = new Trader("john", "tester", LocalDate.of(2001,12,13), "Canada", "testerjohn@gmail.com");
    testAccount = new Account(testTrader, 1000.0);
    testTrader.setId(1);
    testAccount.setId(1);
    mockTraderAccountView = new TraderAccountView();
    mockTraderAccountView.setAccount(testAccount);
    mockTraderAccountView.setTrader(testTrader);
  }

  @Test
  public void createTraderMock() throws Exception {
    when(traderService.createTraderAndAccount(Mockito.any(Trader.class))).thenReturn(mockTraderAccountView);
    mockMvc.perform(post("/trader")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testTrader)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.trader.id").value(1))
        .andExpect(jsonPath("$.trader.first_name").value("john"))
        .andExpect(jsonPath("$.trader.last_name").value("tester"))
        .andExpect(jsonPath("$.trader.dob").value("2001-12-13"))
        .andExpect(jsonPath("$.trader.country").value("Canada"))
        .andExpect(jsonPath("$.trader.email").value("testerjohn@gmail.com"))
        .andExpect(jsonPath("$.account.id").value(1))
        .andExpect(jsonPath("$.account.trader.id").value(1))
        .andExpect(jsonPath("$.account.amount").value(1000.0));
  }

  @Test
  public void createTraderPathVariableMock() throws Exception {
    when(traderService.createTraderAndAccount(Mockito.any(Trader.class))).thenReturn(mockTraderAccountView);
    mockMvc.perform(post("/trader/firstname/john/lastname/tester/dob/2012-12-13/country/Canada/email/testerjohn@gmail.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.trader.id").value(1))
        .andExpect(jsonPath("$.trader.first_name").value("john"))
        .andExpect(jsonPath("$.trader.last_name").value("tester"))
        .andExpect(jsonPath("$.trader.dob").value("2001-12-13"))
        .andExpect(jsonPath("$.trader.country").value("Canada"))
        .andExpect(jsonPath("$.trader.email").value("testerjohn@gmail.com"))
        .andExpect(jsonPath("$.account.id").value(1))
        .andExpect(jsonPath("$.account.trader.id").value(1))
        .andExpect(jsonPath("$.account.amount").value(1000.0));
  }

  @Test
  public void deleteTraderMock() throws Exception {
    Mockito.doNothing().when(traderService).deleteTraderById(testTrader.getId());
    mockMvc.perform(delete("/trader/traderId/1")).andExpect(status().isOk());
  }

  @Test
  public void depositFundMock() throws Exception {
    when(traderService.deposit(testTrader.getId(), 1000.0)).thenReturn(testAccount);
    mockMvc.perform(put("/trader/deposit/traderId/1/amount/1000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.trader.first_name").value("john"))
        .andExpect(jsonPath("$.amount").value(Double.valueOf(1000.0)));
  }
  @Test
  public void withdrawFundMock() throws Exception {
    when(traderService.withdraw(testTrader.getId(), 1000.0)).thenReturn(testAccount);
    mockMvc.perform(put("/trader/withdraw/traderId/1/amount/1000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.trader.first_name").value("john"))
        .andExpect(jsonPath("$.amount").value(Double.valueOf(1000.0)));
  }
}
