package cl.bci.bcitest.repository.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Phone {
    @Id
    @GeneratedValue
    private Long id;

    private String number;
    private String cityCode;
    private String countryCode;

    public Phone() {
    }

    public Phone(Long id, String number, String cityCode, String countrycode) {
        this.id = id;
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countrycode;
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

    public String getCountrycode() {
        return countryCode;
    }
}
