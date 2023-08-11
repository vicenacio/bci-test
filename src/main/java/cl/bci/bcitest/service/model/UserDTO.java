package cl.bci.bcitest.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
  private Long id;
  private String name;
  private String email;
  private String password;

  private List<PhoneDTO> phones;

  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;
  private String token;
  private boolean isActive;

  public UserDTO() {}

  public UserDTO(
      Long id,
      String name,
      String email,
      String password,
      List<PhoneDTO> phones,
      LocalDateTime created,
      LocalDateTime modified,
      LocalDateTime lastLogin,
      String token,
      boolean isActive) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.phones = phones;
    this.created = created;
    this.modified = modified;
    this.lastLogin = lastLogin;
    this.token = token;
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public List<PhoneDTO> getPhones() {
    return phones;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public String getToken() {
    return token;
  }

  public boolean isActive() {
    return isActive;
  }
}
