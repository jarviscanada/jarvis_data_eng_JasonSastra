package ca.jrvs.apps.trading.Model;

import java.io.Serializable;
import java.util.Objects;

public class PositionId implements Serializable {

  private Integer account_id;
  private String ticker;

  public PositionId() {
  }

  public PositionId(Integer accountId, String ticker) {
    this.account_id = accountId;
    this.ticker = ticker;
  }

  public Integer getAccount_id() {
    return account_id;
  }

  public void setAccount_id(Integer account_id) {
    this.account_id = account_id;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PositionId)) return false;
    PositionId that = (PositionId) o;
    return Objects.equals(account_id, that.account_id) &&
        Objects.equals(ticker, that.ticker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account_id, ticker);
  }
}
