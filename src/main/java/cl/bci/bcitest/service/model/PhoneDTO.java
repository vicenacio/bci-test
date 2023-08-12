package cl.bci.bcitest.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDTO {
  private Long id;

  private String number;
  private String cityCode;
  private String countryCode;

  public PhoneDTO() {}

  public PhoneDTO(Long id, String number, String cityCode, String countryCode) {
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
