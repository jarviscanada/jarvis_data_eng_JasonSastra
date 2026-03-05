package ca.jrvs.apps.trading.Model;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "trader_id", nullable = false)
  private Trader trader;

  public Account(Trader trader, Double amount) {
    this.trader = trader;
    this.amount = amount;
  }

  public Account() {
  }

  private Double amount;

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public Trader getTrader() {
    return trader;
  }

  public void setTrader(Trader trader) {
    this.trader = trader;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
