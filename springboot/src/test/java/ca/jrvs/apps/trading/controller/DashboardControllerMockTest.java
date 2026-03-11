package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.PortfolioView;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.service.QuoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DashboardController.class)
@ContextConfiguration(classes = DashboardController.class)
public class DashboardControllerMockTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  DashboardService dashboardService;
  @MockBean
  QuoteService quoteService;
  @MockBean
  OrderService orderService;

  @Test
  public void getPortfolioViewMockTest() throws Exception {

    PortfolioView portfolio = new PortfolioView();
    when(dashboardService.getProfileViewByTraderId(1))
        .thenReturn(portfolio);

    mockMvc.perform(get("/dashboard/portfolio/traderId/1"))
        .andExpect(status().isOk());


    Mockito.verify(dashboardService)
        .getProfileViewByTraderId(1);
  }

  @Test
  public void getAccountMockTest() throws Exception {
    TraderAccountView traderAccountView = new TraderAccountView();

    when(dashboardService.getTraderAccount(1))
        .thenReturn(traderAccountView);

    mockMvc.perform(get("/dashboard/profile/traderId/1"))
        .andExpect(status().isOk());

    Mockito.verify(dashboardService)
        .getTraderAccount(1);
  }
}
