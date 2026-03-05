package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.*;
import ca.jrvs.apps.trading.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OrderController.class)
@ContextConfiguration(classes = OrderController.class)
public class OrderControllerMockTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  OrderService orderService;

  @Test
  public void postMarketOrderMock() throws Exception {
    MarketOrder marketOrder = new MarketOrder("AAPL", 10, 1, MarketOrder.Option.BUY);

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setTicker("AAPL");
    securityOrder.setSize(10);
    when(orderService.executeMarketOrder(Mockito.any(MarketOrder.class))).thenReturn(securityOrder);

    mockMvc.perform(post("/order/marketorder")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(marketOrder)))
        .andExpect(status().isOk());

    Mockito.verify(orderService).executeMarketOrder(Mockito.any(MarketOrder.class));
  }
}
