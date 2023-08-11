package cl.bci.bcitest.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDTO {
  private Long id;

  private String number;
  private String citycode;
  private String countrycode;

  public PhoneDTO() {}

  public PhoneDTO(Long id, String number, String citycode, String countrycode) {
    this.id = id;
    this.number = number;
    this.citycode = citycode;
    this.countrycode = countrycode;
  }

  public Long getId() {
    return id;
  }

  public String getNumber() {
    return number;
  }

  public String getCitycode() {
    return citycode;
  }

  public String getCountrycode() {
    return countrycode;
  }
}
