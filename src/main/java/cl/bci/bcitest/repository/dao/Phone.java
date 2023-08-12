package cl.bci.bcitest.repository.dao;

import javax.persistence.*;

@Entity
@Table(name = "app_phone")
public class Phone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "phone_id")
  private Long id;

  private String number;
  private String cityCode;
  private String countryCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Phone() {}

  public Phone(Long id, String number, String cityCode, String countryCode) {
    this.id = id;
    this.number = number;
    this.cityCode = cityCode;
    this.countryCode = countryCode;
  }

  public Long getId() {
    return id;
  }

  public String getNumber() {
    return number;
  }

  public String getCityCode() {
    return cityCode;
  }

  public String getCountryCode() {
    return countryCode;
  }
}
