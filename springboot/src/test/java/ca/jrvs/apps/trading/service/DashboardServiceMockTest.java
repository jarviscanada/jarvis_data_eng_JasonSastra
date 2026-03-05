package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.PortfolioView;
import ca.jrvs.apps.trading.Model.Position;
import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.AccountJpaRepository;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceMockTest {

  @Mock
  AccountJpaRepository accountDao;

  @Mock
  PositionDao positionDao;

  @InjectMocks
  DashboardService dashboardService;

  @Test
  public void getTraderAccountMockTest() {
    Trader trader = new Trader();

    Account account = new Account();
    account.setTrader(trader);

    DashboardService spyService = Mockito.spy(dashboardService);

    Mockito.doReturn(account)
        .when(spyService)
        .findAccountByTraderId(trader.getId());

    TraderAccountView traderAccountView = spyService.getTraderAccount(trader.getId());

    Assertions.assertThat(traderAccountView).isNotNull();
  }

  @Test
  public void getProfileViewByTraderIdTest() {
    Integer traderId = 1;

    Account account = new Account();
    Integer accountId = account.getId();

    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setAccount(account);

    DashboardService spyService = Mockito.spy(dashboardService);

    Mockito.doReturn(traderAccountView)
        .when(spyService)
        .getTraderAccount(traderId);

    List<Position> positions = new ArrayList<>();
    Mockito.when(positionDao.findAllByAccountId(accountId))
        .thenReturn(positions);

    PortfolioView result = spyService.getProfileViewByTraderId(traderId);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getPositions()).isEqualTo(positions);
    Assertions.assertThat(result.getTraderAccountView()).isEqualTo(traderAccountView);
  }

  @Test
  public void findAccountByTraderIdMock() {
    Account account = Mockito.mock(Account.class);
    Trader trader  = Mockito.mock(Trader.class);
    account.setTrader(trader);
    when(accountDao.getAccountByTraderId(trader.getId())).thenReturn(account);
    Account obtained = dashboardService.findAccountByTraderId(trader.getId());
    Assertions.assertThat(obtained).isNotNull();

  }

}
