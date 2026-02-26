package ca.jrvs.apps.trading.Model;

import ca.jrvs.apps.trading.Model.PositionId;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name="position")
@IdClass(PositionId.class)
@Immutable
public class Position {

  @Id
  @Column(name="account_id")
  private Integer account_id;

  @Id
  @Column(name="ticker")
  private String ticker;

  @Column(name="position")
  private Integer position;

  public Position() {}

  public Integer getAccount_id() {
    return account_id;
  }

  public String getTicker() {
    return ticker;
  }

  public Integer getPosition() {
    return position;
  }
}
